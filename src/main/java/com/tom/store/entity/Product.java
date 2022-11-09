package com.tom.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name = "ref_number")
	private long refNumber;
	
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name= "order_id", nullable = false)
	private Order order;
	
	
	public long getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(long refNumber) {
		this.refNumber = refNumber;
	}

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", refNumber=" + refNumber + ", quantity=" + quantity + ", order=" + order + "]";
	}

	
	

	
	
	
	

}
