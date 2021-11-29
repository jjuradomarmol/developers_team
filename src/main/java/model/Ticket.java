package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Ticket implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Product> purchasedProducts;
	
	public Ticket() {
		this.purchasedProducts = new ArrayList<Product>();
	}
	
	public ArrayList<Product> getPurchasedProducts() {
		return purchasedProducts;
	}
	
	public void addToPurchasedProducts(Product product) {
		this.purchasedProducts.add(product);
	}
	
	public double getTotalTicket() {
		double total = 0;
		for (Product product : this.purchasedProducts) {
			total += product.price * product.quantity;
		}
		return total;
	}
	
	@Override
	public String toString() {
		String ticketToPrint = "";
		for (Product product : this.purchasedProducts) {
			ticketToPrint += "\t\tProducto: " + 
			product.getName() + ", cantidad: " +
			product.getQuantity() + ", precio por unidad: " +
			product.getPrice() + "€, total: " + 
			product.getPrice() * product.getQuantity() + "€.\n";
		}
		ticketToPrint += "\t\tTotal: " + this.getTotalTicket() + "€.\n";
		return ticketToPrint;
	}
}
