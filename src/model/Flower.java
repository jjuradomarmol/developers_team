package model;

public class Flower extends Product {
	
	private String color;
	
	public Flower(String name, double price, String color) {
		super(name, price);
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
