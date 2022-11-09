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
import com.tom.store.entity.SubstractingOrderPojo;

@Component
public class SubstractingOrderFromStockProcessor implements ItemProcessor<SubstractingOrderPojo, StockProduct> {



	@Override
	public StockProduct process(SubstractingOrderPojo item) throws Exception {
		System.out.println("Dans le process de substract");
		System.out.println(item);
		StockProduct stockProduct = new StockProduct();
		stockProduct.setId(item.getStockProduct().getId());
		int newQuantity = item.getStockProduct().getQuantity() - item.getProduct().getQuantity();
		if (newQuantity < 0) {
			throw new Exception ("Pas assez de produits en stock");
		}
		stockProduct.setQuantity(newQuantity);
		return stockProduct;
	}	

}
