package com.tom.store.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.tom.store.entity.StockProduct;

@Component
public class StockJdbcWriter {
	
	@Autowired
	@Qualifier("productDataSource")
	private DataSource productDataSource;
	
	@Bean
	public JdbcBatchItemWriter<StockProduct> stockJdbcBatchItemWriter() {
		System.out.println("Dans le jdbc writer");
		JdbcBatchItemWriter<StockProduct> jdbcBatchItemWriter = new JdbcBatchItemWriter<StockProduct>();
		
		jdbcBatchItemWriter.setDataSource(productDataSource);
		
		jdbcBatchItemWriter.setSql("update stock_products set quantity = ? where id = ?");
		
		jdbcBatchItemWriter.setItemPreparedStatementSetter(new ItemPreparedStatementSetter<StockProduct>() {

			@Override
			public void setValues(StockProduct item, PreparedStatement ps) throws SQLException {
				ps.setInt(1, item.getQuantity());
				ps.setLong(2, item.getId());
				
			}
		});	
		
		
		return jdbcBatchItemWriter;
	}

}
