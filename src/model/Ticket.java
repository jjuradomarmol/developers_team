package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Ticket implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Ticket instance;
	private HashMap<Integer, ArrayList<Product>> tickets;
	private ArrayList<Product> tempProductList;
	
	private Ticket() {
		this.tickets = new HashMap<Integer, ArrayList<Product>>();
		this.tempProductList = new ArrayList<Product>();
	}
	
	public static Ticket getInstance() {
		if (instance == null) {
			instance = new Ticket();
		}
		return instance;
	}
	
	public ArrayList<Product> generateTicket() throws IOException {
		int id = this.tickets.keySet().size() + 1;
		this.tickets.put(id, this.tempProductList);
		return this.tempProductList;
	}
	
	public void clearTempProductList() {
		this.tempProductList = new ArrayList<Product>();
	}
	
	public double totalTickets() {
		double result = 0.0;
		for (int i : this.tickets.keySet()) {
			for (Product product : this.tickets.get(i)) {
				result += product.getPrice() * product.getQuantity();
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "Lista de tickets: ";
		for (int i : this.tickets.keySet()) {
			  result += "\n\tTicket nº " + i + ":";
		for (Product product : this.tickets.get(i)) {
			result += "\n\t\tProducto: " + product.getName() + ", cantidad: " +
					product.getQuantity()+ ", precio por unidad: " +
					product.getPrice() + "€, total: " + 
					product.getPrice() * product.getQuantity() + "€.";
		}
		result += "\n";
		}
		return result;
	}

	public void addProductToTempList(Product product) {
		this.tempProductList.add(product);
	}

}
