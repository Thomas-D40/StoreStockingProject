package com.tom.store.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleItemWriter<Product> implements ItemWriter<Product> { 
    @Override
    public void write(List<? extends Product> items) throws Exception { 
        for (Product item : items) { 
            System.out.println(item); 
        } 
    }


}