package com.tom.store.processor;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Order;
import com.tom.store.entity.Product;

@Component
public class SimpleItemProcessor implements ItemProcessor<Order, Order> {

	@Override
	public Order process(Order item) throws Exception {
		System.out.println(item);		
		List<Product> products = item.getProducts();
		for (Product product : products) {
			product.setOrder(item);			
		}

		return item;
	}

}
