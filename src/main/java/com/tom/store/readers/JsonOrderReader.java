package com.tom.store.readers;



import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Order;
import com.tom.store.entity.Product;

@Component
public class JsonOrderReader {
	
	@Value("Orders/*.json")
	private Resource[] inputResources;
			
	@Bean
	public MultiResourceItemReader<Order> ordersItemReader() {
		MultiResourceItemReader<Order> ordersItemReader = new MultiResourceItemReader<>();
		ordersItemReader.setResources(inputResources);
		System.out.println("Dans le multi ressources");
		ordersItemReader.setDelegate(jsonReader());
	
		
		return ordersItemReader;
	}
	
	@Bean
	public JsonItemReader<Order> jsonReader() {
		JsonItemReader<Order> jsonItemReader = new JsonItemReader<Order>();
		System.out.println("Dans le json");
		
		jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(Order.class));
		
		
		return jsonItemReader;
		
	}
	
	
	
	
	
}
