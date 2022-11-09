package com.tom.store.entity;

import java.util.List;

public class SubstractingOrderPojo {
	
	private StockProduct stockProduct;
	
	private Product product;

	public StockProduct getStockProduct() {
		return stockProduct;
	}

	public void setStockProduct(StockProduct stockProduct) {
		this.stockProduct = stockProduct;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "SubstractingOrderPojo [stockProduct=" + stockProduct + ", product=" + product + "]";
	}
	
	
	

}
