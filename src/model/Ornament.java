package model;

import java.io.Serializable;

public class Ornament extends Product implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String material;
	
	public Ornament(String name, double price) {
		super(name, price);
		this.material = OrnamentMaterial.getType();
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + this.getName() + ", Precio: " + this.getPrice() + 
				", Material: " + this.getMaterial();
	}
}
