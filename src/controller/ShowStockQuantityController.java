package controller;

import model.Stock;

public class ShowStockQuantityController {

	public String showStockQuantity() {
		Stock stock = Stock.getInstance();
		return "Árboles: " + stock.getTreeStock().size() + "\n"
			+ "Flores: " + stock.getFlowerStock().size() + "\n"
			+ "Adornos: " + stock.getOrnamentStock().size();
	}
	
}
