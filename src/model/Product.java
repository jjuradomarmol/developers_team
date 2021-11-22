package model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String name;
	protected double price;
	protected String id;
	protected int quantity;
	
	public Product(String name, double price, int quantity) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}	
	
	public String getId() {
		return id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
