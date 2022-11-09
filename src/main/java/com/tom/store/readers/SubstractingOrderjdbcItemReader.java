package com.tom.store.readers;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Component;

import com.tom.store.entity.SubstractingOrderPojo;
import com.tom.store.services.SubstractingOrderRowMapper;

@Component
public class SubstractingOrderjdbcItemReader {
	
	@Autowired
	@Qualifier("productDataSource")
	private DataSource productDataSource;
	
	@Autowired
	private SubstractingOrderRowMapper substractingOrderRowMapper;
	
	public JdbcCursorItemReader<SubstractingOrderPojo> substractingItemReader() {
		JdbcCursorItemReader<SubstractingOrderPojo> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		
		jdbcCursorItemReader.setDataSource(productDataSource);
		jdbcCursorItemReader.setSql("select stock_products.quantity as stock_quantity, stock_products.id as stock_id, order_id, sum(products.quantity) as product_quantity "
				+ "from stock_products inner join products on stock_products.ref_number = products.ref_number group by stock_products.ref_number");
		
		jdbcCursorItemReader.setRowMapper(substractingOrderRowMapper);
		
		
		return jdbcCursorItemReader;
	}

}
