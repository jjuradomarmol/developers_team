package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Ticket implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private HashMap<Integer, ArrayList<Product>> tickets;

	private ArrayList<Product> tempProductList;
	
	public Ticket() {
		this.tickets = new HashMap<Integer, ArrayList<Product>>();
		this.tempProductList = new ArrayList<Product>();
	}
	
	public HashMap<Integer, ArrayList<Product>> getTickets() {
		return tickets;
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
			double total = 0;
			result += "\n\tTicket nº " + i + ":";
			for (Product product : this.tickets.get(i)) {
				result += "\n\t\tProducto: " + product.getName() + 
				", cantidad: " + product.getQuantity()+ 
				", precio por unidad: " + product.getPrice() + "€, total: " 
				+ product.getPrice() * product.getQuantity() + "€.";
				total += product.getPrice() * product.getQuantity();
			}
			result += "\n\t\tTotal: " + total + "€.\n";
		}
		return result;
	}

	public void addProductToTempList(Product product) {
		this.tempProductList.add(product);
	}

}
