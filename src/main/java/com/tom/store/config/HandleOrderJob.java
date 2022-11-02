package com.tom.store.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Product;
import com.tom.store.processor.SimpleItemProcessor;
import com.tom.store.readers.JsonOrderReader;
import com.tom.store.tasklet.CheckingFileExtensionTasklet;
import com.tom.store.tasklet.CheckingJsonFiles;
import com.tom.store.writer.ConsoleItemWriter;

@Component
public class HandleOrderJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JsonOrderReader jsonOrderReader;
	
	@Autowired
	private CheckingFileExtensionTasklet checkingFileTasklet;
	
	@Autowired
	private CheckingJsonFiles checkingJsonFiles;

	@Autowired
	private SimpleItemProcessor simpleItemProcessor;

	@Autowired
	private ConsoleItemWriter<Product> consoleItemWriter;

	@Bean
	public Job OrderHandlerJob() {
		return (jobBuilderFactory.get("Handle Order Job").incrementer(new RunIdIncrementer())).start(checkingFileStep())
				.next(checkingJsonFileStep()).next(treatmentStep()).build();
	}

	@Bean
	private Step checkingFileStep() {
		return stepBuilderFactory.get("Checking File Step").tasklet(checkingFileTasklet).build();
	}
	
	@Bean
	private Step checkingJsonFileStep() {
		return stepBuilderFactory.get("Checking Json File Step").tasklet(checkingJsonFiles).build();
	}

	@Bean
	private Step treatmentStep() {
		return stepBuilderFactory.get("Traitement des commandes").<Product, Product>chunk(3)
				.reader(jsonOrderReader.ordersItemReader()).processor(simpleItemProcessor).writer(consoleItemWriter)
				.build();
	}

	@Bean
	public ConsoleItemWriter<Product> writer() {
		return new ConsoleItemWriter<Product>();
	}

}
