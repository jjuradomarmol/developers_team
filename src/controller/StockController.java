package controller;

import model.Florist;
import model.Stock;

public class StockController {
	
	public String showStock() {
		return Florist.getInstance().getStock().toString();
	}
	
	public String showStockQuantity() {
		Stock stock = Florist.getInstance().getStock();
		return "Árboles: " + stock.getTreeStock().size() + "\n"
			+ "Flores: " + stock.getFlowerStock().size() + "\n"
			+ "Adornos: " + stock.getOrnamentStock().size();
	}
	
	public double showStockValue() {
		return Florist.getInstance().getStock().getTotalStockValue();
	}
	
	public boolean checkStockIsEmpty() {
		Stock stock = Florist.getInstance().getStock();
		if (stock.getTreeStock().isEmpty()
			&& stock.getFlowerStock().isEmpty()
			&& stock.getOrnamentStock().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}
