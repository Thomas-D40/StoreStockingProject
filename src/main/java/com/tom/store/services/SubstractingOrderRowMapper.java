package com.tom.store.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.tom.store.entity.Order;
import com.tom.store.entity.Product;
import com.tom.store.entity.StockProduct;
import com.tom.store.entity.SubstractingOrderPojo;

@Component
public class SubstractingOrderRowMapper implements RowMapper<SubstractingOrderPojo>{

	@Override
	public SubstractingOrderPojo mapRow(ResultSet rs, int rowNum) throws SQLException {
		SubstractingOrderPojo substractingOrderPojo = new SubstractingOrderPojo();
		StockProduct stockProduct = new StockProduct();
		Product product = new Product();
		Order order = new Order();
		
		
		stockProduct.setQuantity(rs.getInt("stock_quantity"));
		stockProduct.setId(rs.getInt("stock_id"));
		
		product.setQuantity(rs.getInt("product_quantity"));
		
		order.setId(rs.getInt("order_id"));
		
		
		product.setOrder(order);	
		
		substractingOrderPojo.setProduct(product);
		substractingOrderPojo.setStockProduct(stockProduct);
		
		return substractingOrderPojo;
	}

}
