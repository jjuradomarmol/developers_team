package model;

import java.util.ArrayList;

public class Ticket {
	
	private static Ticket instance;
	
	private ArrayList<Product> purchases;
	
	private Ticket() {
		this.purchases = new ArrayList<Product>();
	}
	
	public static Ticket getInstance() {
		if (instance == null) {
			instance = new Ticket();
		}
		return instance;
	}
	
	public void addProduct(Product product) {
		this.purchases.add(product);
	}
	
	//S'han de poder guardar els tickets a la BBDD

}
