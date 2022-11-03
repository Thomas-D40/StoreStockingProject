package com.tom.store.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Product;

@Component
public class SimpleItemProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {
		System.out.println(item);
		// TODO Auto-generated method stub
		return item;
	}

}
