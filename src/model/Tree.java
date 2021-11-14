package model;

import java.io.Serializable;

public class Tree extends Product implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double height;
	
	public Tree(String name, double price, double height) {
		super(name, price);
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + this.getName() + ", Precio: " + this.getPrice() + 
				", Altura: " + this.getHeight();
	}
	
}
