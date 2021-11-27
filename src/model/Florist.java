package model;

import java.io.Serializable;

public class Florist implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static Florist instance;
	
	private String name;
	
	private Stock stock;
	
	private TicketCollection ticketCollection;
	
	private Florist() {
		this.stock = new Stock();
		this.ticketCollection = new TicketCollection();
	}
	
	public static Florist getInstance() {
		if (instance == null) {
			instance = new Florist();
		}
		return instance;
	}
	
	public static void setInstance(Florist florist) {
		instance = florist;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public Stock getStock() {
		return this.stock;
	}
	
	public void resetFlorist() {
		this.stock = new Stock();
		this.ticketCollection = new TicketCollection();
	}
	
	public TicketCollection getTicketCollection() {
		return this.ticketCollection;
	}
	
	@Override
	public String toString() {
		return "La floristería " + getName() + " tiene un stock de: \n" + 
				this.stock.toString() + "\tCon un valor total de " + 
				this.stock.getTotalStockValue() + "€";
	}
}
