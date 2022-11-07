package com.tom.store.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleItemWriter<StockProduct> implements ItemWriter<StockProduct> { 
    @Override
    public void write(List<? extends StockProduct> items) throws Exception { 
        for (StockProduct item : items) { 
            System.out.println(item); 
        } 
    }


}