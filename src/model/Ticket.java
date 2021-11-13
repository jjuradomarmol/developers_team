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
	private static HashMap<Integer, ArrayList<Product>> tickets = new HashMap<Integer, ArrayList<Product>>();
	private static int count = 0;
	
	private Ticket() {
	}
	
	public static Ticket getInstance() {
		if (instance == null) {
			instance = new Ticket();
		}
		return instance;
	}
	
	public static void addTicket(ArrayList<Product> productos) throws IOException {
		// Aumenta en 1 el id
		Ticket.id = setCount(getCount() + 1);
		// Añade los productos al ticket
		Ticket.tickets.put(id, productos);
		// Elimina los productos del stock
		for(Product producto : productos) {
			if (producto instanceof Flower) {
				stock.deleteFlower(producto.getName());
			} else if (producto instanceof Ornament) {
				stock.deleteOrnament(producto.getName());
			} else if (producto instanceof Tree) {
				stock.deleteTree(producto.getName());
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
	

	public String printTickets() {
		String result = "";
		for (int i : Ticket.tickets.keySet()) {
			  result += "Ticket nº " + i + ":\n";
		for (Product product : Ticket.tickets.get(i)) {
			result += "Producto: " + product.getName() + ", "
					+ "Precio: " + product.getPrice() + "€\n";
		}
		result += "\n";
		}
		return result;
	}

}
