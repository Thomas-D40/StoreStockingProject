package com.tom.store.readers;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tom.store.entity.StockProduct;

@Component
public class JpaProductsInStockReader{
	
	@Autowired
	@Qualifier("productEntityManagerFactory")
	private EntityManagerFactory productEntityManagerFactory;
	
	public JpaCursorItemReader<StockProduct> stockProjectJpaItemReader() {
		JpaCursorItemReader<StockProduct> jpaCursorItemReader = new JpaCursorItemReader<>();
		
		jpaCursorItemReader.setEntityManagerFactory(productEntityManagerFactory);
		jpaCursorItemReader.setQueryString("From StockProduct");
		
		
		return jpaCursorItemReader;
	}
	
	

}
