package model;

public class ProductFactoryCriteria {
	
	private String name;
	private double price;
	private Double height;
	private String color;
	private String material;
	
	public ProductFactoryCriteria(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public boolean hasHeight() {
		return this.height != null;
	}
	
	public boolean hasColor() {
		return this.color != null;
	}
	
	public boolean hasMaterial() {
		return this.material != null;
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

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

}
