package com.tom.store.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Order;
import com.tom.store.entity.Product;

import net.bytebuddy.asm.Advice.This;

@Component
public class OrderSavingProcessor implements ItemProcessor<Order, Order> {

	private StepExecution stepExecution;
	
	private ArrayList<Order> orders = new ArrayList<>();

	@Override
	public Order process(Order item) throws Exception {
		System.out.println(item);
		List<Product> products = item.getProducts();
		for (Product product : products) {
			product.setOrder(item);
		}
		
		orders.add(item);

		return item;
	}

	@BeforeStep
	public void saveStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	@AfterStep
	public void transferProducts() {
		ExecutionContext stepContext = this.stepExecution.getExecutionContext();
		//System.out.println(orders);

		stepContext.put("products", orders);

	}

}
