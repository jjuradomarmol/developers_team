package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Stock implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	public ArrayList<Tree> getTreeStock() {
		return treeStock;
	}

	public ArrayList<Flower> getFlowerStock() {
		return flowerStock;
	}

	public ArrayList<Ornament> getOrnamentStock() {
		return ornamentStock;
	}
	
	public Tree getTree(String nombre) {
		Iterator<Tree> it = treeStock.iterator();
		while (it.hasNext()) {
			Tree tree = it.next();
			if (tree.getName().equalsIgnoreCase(nombre)) {
				return tree;
			}
		}
		
		return null;
	}
	
	public Flower getFlower(String nombre) {
		Iterator<Flower> it = flowerStock.iterator();
		while (it.hasNext()) {
			Flower flower = it.next();
			if (flower.getName().equalsIgnoreCase(nombre)) {
				return flower;
			}
		}
		
		return null;
	}
	
	public Ornament getOrnament(String nombre) {
		Iterator<Ornament> it = ornamentStock.iterator();
		while (it.hasNext()) {
			Ornament ornament = it.next();
			if (ornament.getName().equalsIgnoreCase(nombre)) {
				return ornament;
			}
		}
		
		return null;
	}
	
	public void addTree(Tree tree) {
		treeStock.add(tree);
	}
	
	public void deleteTree(Tree treeToFind) {
		Iterator<Tree> it = treeStock.iterator();
		while (it.hasNext()) {
			Tree tree = it.next();
			if (tree.equals(treeToFind)) {
				it.remove();
				break;
			}
		}
	}
	
	public void addFlower(Flower flower) {
		flowerStock.add(flower);
	}
	
	public void deleteFlower(Flower flowerToFind) {
		Iterator<Flower> it = flowerStock.iterator();
		while (it.hasNext()) {
			Flower flower = it.next();
			if (flower.equals(flowerToFind)) {
				it.remove();
				break;
			}
		}
	}
	
	public void addOrnament(Ornament ornament) {
		ornamentStock.add(ornament);
	}
	
	public void deleteOrnament(Ornament ornamentToFind) {
		Iterator<Ornament> it = ornamentStock.iterator();
		while (it.hasNext()) {
			Ornament ornament = it.next();
			if (ornament.equals(ornamentToFind)) {
				it.remove();
				break;
			}
		}
	}
	
	public double getTotalStockValue() {
		double total = 0.0;
		for (Tree tree : treeStock) {
			total += tree.getPrice();			
		}
		for (Flower flower : flowerStock) {
			total += flower.getPrice();			
		}
		for (Ornament ornament : ornamentStock) {
			total += ornament.getPrice();			
		}
		return total;
	}
	
	@Override
	public String toString() {
		String totalArrays = "";
		totalArrays += "\n\t¡rboles:\n";
		for (Tree tree : treeStock) {
			totalArrays += "\t\t" + tree.toString() + "\n";
		}
		totalArrays += "\n\tFlores:\n";
		for (Flower flower : flowerStock) {
			totalArrays += "\t\t" + flower.toString() + "\n";
		}
		totalArrays += "\n\tDecoraciones:\n";
		for (Ornament ornament : ornamentStock) {
			totalArrays += "\t\t" + ornament.toString() + "\n";
		}
		totalArrays += "\n";
		return totalArrays;
	}

}
