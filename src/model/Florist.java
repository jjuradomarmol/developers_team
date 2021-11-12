package model;

import java.io.Serializable;

public class Florist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Florist instance;
	
	private String name;
	
	private Stock stock;
	
	private Florist() {}
	
	private Florist(String name) {
		this.name = name;
		this.stock = Stock.getInstance();
	}
	
	public static Florist getInstance(String name) {
		if (instance == null) {
			instance = new Florist(name);
		}
		return instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stock getStock() {
		return stock;
	}

	@Override
	public String toString() {
		return "La floristería " + this.getName() + " tiene un stock de: \n" + 
				this.stock.toString() + "Con un valor total de " + 
				this.stock.getTotalStockValue() + "€";
	}
}
