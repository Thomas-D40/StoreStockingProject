package com.tom.store;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.tom.store.config","com.tom.store.tasklet","com.tom.store.readers","com.tom.store.writer","com.tom.store.processor","com.tom.store.listener","com.tom.store.services"})
public class StoreStockingApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreStockingApplication.class, args);
	}

}
