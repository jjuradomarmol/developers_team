package model;

import java.io.Serializable;

public class Ornament extends Product implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String material;

	public Ornament(String name, double price, String material) {
		super(name, price);
		this.material = material;
		}
/*
Incorporaré esta parte más adelante.

public Ornament(String name, double price, String material) throws TipoMaterialException {
	super(name, price);
	if (material.equalsIgnoreCase("madera"))
	{
		this.material = "Madera";
	} else if (material.equalsIgnoreCase("plástico")) {
		this.material = "Plástico";
	} else {
		throw new TipoMaterialException("El tipo de material de la decoración no es válido");
	}
*/

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
