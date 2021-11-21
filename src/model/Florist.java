package model;

import java.io.Serializable;

public class Florist implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static Florist instance;
	
	private String name;
	
	private Stock stock;
	
	private Ticket ticket;
	
	private Florist() {
		this.stock = new Stock();
		this.ticket = new Ticket();
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stock getStock() {
		return stock;
	}
	
	public Ticket getTicket() {
		return ticket;
	}
	
	@Override
	public String toString() {
		return "La floristería " + this.getName() + " tiene un stock de: \n" + 
				this.stock.toString() + "\tCon un valor total de " + 
				this.stock.getTotalStockValue() + "€";
	}
}
