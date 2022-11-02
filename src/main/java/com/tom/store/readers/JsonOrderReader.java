package com.tom.store.readers;



import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Product;

@Component
public class JsonOrderReader {
	
	@Value("Orders/*.json")
	private Resource[] inputResources;
			
	@Bean
	public MultiResourceItemReader<Product> ordersItemReader() {
		MultiResourceItemReader<Product> ordersItemReader = new MultiResourceItemReader<>();
		ordersItemReader.setResources(inputResources);
		System.out.println("Dans le multi ressources");
		ordersItemReader.setDelegate(jsonReader());
	
		
		return ordersItemReader;
	}
	
	@Bean
	public JsonItemReader<Product> jsonReader() {
		JsonItemReader<Product> jsonItemReader = new JsonItemReader<Product>();
		System.out.println("Dans le json");
		
		jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(Product.class));
		
		
		return jsonItemReader;
		
	}
	
	
	
	
	
}
