package model;

import java.util.ArrayList;

public class Ticket {
	
	private ArrayList<Product> purchase;
	
	public Ticket() {
		this.purchase = new ArrayList<Product>();
	}
	
	public void addProduct(Product product) {
		this.purchase.add(product);
	}
	
	//S'han de poder guardar els tickets a la BBDD

}
