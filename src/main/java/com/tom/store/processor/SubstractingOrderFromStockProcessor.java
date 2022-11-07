package com.tom.store.processor;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Product;
import com.tom.store.entity.StockProduct;

@Component
public class SubstractingOrderFromStockProcessor implements ItemProcessor<StockProduct, StockProduct> {

	private String products;

	@Override
	public StockProduct process(StockProduct item) throws Exception {
		System.out.println(products);
		return item;
	}

	@BeforeStep
	public void retrieveInterstepData(StepExecution stepExecution) {
		System.out.println("Dans le beforeStep de la soustraction");
		
		JobExecution jobExecution = stepExecution.getJobExecution();
		ExecutionContext jobContext = jobExecution.getExecutionContext();
		this.products = (String) jobContext.get("products");
	}

	

}
