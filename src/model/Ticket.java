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
	/**
	 * 
	 */
	private static Ticket instance;
	private static int id;
	private static Stock stock = Stock.getInstance();
	private static HashMap<Integer, ArrayList<Product>> tickets = 
		new HashMap<Integer, ArrayList<Product>>();
	private static int count = 0;
	
	private Ticket() {
	}
	
	public static Ticket getInstance() {
		if (instance == null) {
			instance = new Ticket();
		}
		return instance;
	}
	
	public static void addTicket(ArrayList<Product> products) 
			throws IOException {
		Ticket.id = setCount(getCount() + 1);
		Ticket.tickets.put(id, products);
		for(Product product : products) {
			if (product instanceof Flower) {
				stock.deleteFlower((Flower) product);
			} else if (product instanceof Ornament) {
				stock.deleteOrnament((Ornament) product);
			} else if (product instanceof Tree) {
				stock.deleteTree((Tree) product);
			}
		}
	}
	
	public double totalTickets() {
		double result = 0.0;
		for (int i : Ticket.tickets.keySet()) {
			for (Product product : Ticket.tickets.get(i)) {
				result += product.getPrice();
			}
		}
		return result;
	}

	public static int getCount() {
		return count;
	}

	public static int setCount(int count) {
		Ticket.count = count;
		return count;
	}
	
	@Override
	public String toString() {
		String result = "Lista de tickets: ";
		for (int i : Ticket.tickets.keySet()) {
			  result += "\n\tTicket n� " + i + ":";
		for (Product product : Ticket.tickets.get(i)) {
			result += "\n\t\tProducto: " + product.getName() + ", "
					+ "Precio: " + product.getPrice() + "�";
		}
		result += "\n";
		}
		return result;
	}

}
