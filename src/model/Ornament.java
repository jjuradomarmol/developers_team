package model;

import java.io.Serializable;

public class Ornament extends Product implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String material;

	public Ornament(
		String name,
		double price,
		String material
	) throws MaterialTypeException {
		super(name, price);
		this.checkMaterial(material);
		this.material = material;
	}
	
	private void checkMaterial(String material) throws MaterialTypeException {
		if ((!material.equalsIgnoreCase("madera"))
				&&(!material.equalsIgnoreCase("pl�stico"))) {
			throw new MaterialTypeException(
				"El material " + material + " no es v�lido. "
				+ "Introduzca un material v�lido "
				+ "(madera/pl�stico):"
			);
		}		
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	@Override
	public String toString() {
		return this.getName() + " || precio: " + this.getPrice() + 
				"� || material: " + this.getMaterial();
	}
}
