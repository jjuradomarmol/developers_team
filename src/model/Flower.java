package model;

import java.io.Serializable;

public class Flower extends Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String color;
	
	public Flower(String name, double price, String color, int quantity) {
		super(name, price, quantity);
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return this.getName() + " || precio: " + this.getPrice() + 
				"€ || color: " + this.getColor();
	}
	
}
