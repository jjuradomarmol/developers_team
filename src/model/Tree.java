package model;

import java.io.Serializable;

public class Tree extends Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double height;
	
	public Tree(String name, double price, Double height) {
		super(name, price);
		this.height = height;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + this.getName() + ", Precio: " + this.getPrice() + 
				", Altura: " + this.getHeight();
	}
	
}
