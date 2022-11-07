package com.tom.store.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Order;
import com.tom.store.entity.Product;
import com.tom.store.entity.StockProduct;
import com.tom.store.listener.OrderHandlerListener;
import com.tom.store.listener.OrderHandlerStepsListener;
import com.tom.store.processor.OrderSavingProcessor;
import com.tom.store.processor.SubstractingOrderFromStockProcessor;
import com.tom.store.readers.JpaProductsInStockReader;
import com.tom.store.readers.JsonOrderReader;
import com.tom.store.tasklet.CheckingFileExtensionTasklet;
import com.tom.store.tasklet.CheckingJsonFiles;
import com.tom.store.writer.ConsoleItemWriter;
import com.tom.store.writer.JpaWriters;

@Component
public class HandleOrderJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	
	
	@Autowired
	private CheckingFileExtensionTasklet checkingFileTasklet;
	
	@Autowired
	private CheckingJsonFiles checkingJsonFiles;
	
	@Autowired
	private JsonOrderReader jsonOrderReader;

	@Autowired
	private OrderSavingProcessor simpleItemProcessor;
	
		
	
	@Autowired
	private JpaWriters jpaWriters;
	
	@Autowired
	private JpaProductsInStockReader jpaProductsInStockReader;
	
	@Autowired
	private SubstractingOrderFromStockProcessor subtractingOrderFromStockProcessor;
	
	@Autowired
	private ConsoleItemWriter<StockProduct> consoleItemWriter;
	
	@Autowired
	private JpaTransactionManager jpaTransactionManager;
	
	@Autowired
	private OrderHandlerListener orderHandlerListener;
	
	@Autowired 
	private OrderHandlerStepsListener stepsListener;

	@Bean
	public Job OrderHandlerJob() {
		return (jobBuilderFactory.get("Handle Order Job").incrementer(new RunIdIncrementer()))
				.start(checkingFileStep())
				.next(checkingJsonDataStep())
				.next(retrievingFromJSONStep())
				.next(checkingInStockProduct())
				.listener(orderHandlerListener)
				.listener(promotionListener())
				.build();
	}

	@Bean
	private Step checkingFileStep() {
		return stepBuilderFactory.get("Checking File Step").tasklet(checkingFileTasklet)
				.listener(stepsListener).build();
	}
	
	@Bean
	private Step checkingJsonDataStep() {
		return stepBuilderFactory.get("Checking Json Data Step").tasklet(checkingJsonFiles).listener(stepsListener).build();
	}

	@Bean
	private Step retrievingFromJSONStep() {
		return stepBuilderFactory.get("Traitement des commandes JSON").<Order, Order>chunk(3)
				.reader(jsonOrderReader.ordersItemReader())
				.processor(simpleItemProcessor)
				.writer(jpaWriters.orderJpaWriter())
				.transactionManager(jpaTransactionManager)
				.listener(stepsListener)
				.listener(promotionListener())
				.build();
	}
	
	@Bean
	private Step checkingInStockProduct() {
		return stepBuilderFactory.get("Récupération des produits en stock").<StockProduct, StockProduct>chunk(3)
				.reader(jpaProductsInStockReader.stockProjectJpaItemReader())
				.processor(subtractingOrderFromStockProcessor)
				.writer(consoleItemWriter)
				.listener(stepsListener)
				.build();
	}

	@Bean
	public ExecutionContextPromotionListener promotionListener() {
		ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();

		listener.setKeys(new String[] {"products"});

		return listener;
	}

}
