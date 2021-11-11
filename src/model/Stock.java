package model;

import java.util.ArrayList;

public class Stock {
	
	public static Stock instance;
	
	private ArrayList<Tree> treeStock;
	private ArrayList<Flower> flowerStock;
	private ArrayList<Ornament> ornamentStock;
	
	private Stock() {
		this.treeStock = new ArrayList<Tree>();
		this.flowerStock = new ArrayList<Flower>();
		this.ornamentStock = new ArrayList<Ornament>();
	}
	
	public static Stock getInstance() {
		if (instance == null) {
			instance = new Stock();
		}
		return instance;
	}
	
	public double getTotalStockValue() {
		double total = 0.0;
		for (Tree tree : treeStock) {
			total = total + tree.getPrice();			
		}
		for (Flower flower : flowerStock) {
			total = total + flower.getPrice();			
		}
		for (Ornament ornament : ornamentStock) {
			total = total + ornament.getPrice();			
		}
		return total;
	}
	
}
