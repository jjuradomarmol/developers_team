package model.ticketMemento;

import java.util.ArrayList;

import model.*;

public class Ticket {
	
	private int id;	
	private ArrayList<Product> purchases = new ArrayList<>();
	private Stock stock = Stock.getInstance();
	private int count = 0;
	
	public Ticket(ArrayList<Product> productos) {
		this.addProducts(productos);
	}
	
	public void addProducts(ArrayList<Product> productos) {
		// Aumenta en 1 el id
		this.id = ++count;
		// Añade los productos al ticket
		this.purchases.addAll(productos);
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

}
