package com.tom.store.writer;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Order;
import com.tom.store.entity.Product;

@Component
public class JpaWriters {
	
	@Autowired
	@Qualifier("productEntityManagerFactory")
	private EntityManagerFactory productEntityManagerFactory;
	
	public JpaItemWriter<Order> orderJpaWriter() {
		JpaItemWriter jpaWriter = new JpaItemWriter<Order>();
		jpaWriter.setEntityManagerFactory(productEntityManagerFactory);
		
		return jpaWriter;
	}
	
	

}
