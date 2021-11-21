package controller;

import model.Florist;
import model.ProductTypeException;
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
	
	public int getProductQuantity(int i, int index) 
		throws ProductTypeException {
		Stock stock = Florist.getInstance().getStock();
		if (i == 1) {
			return stock.getTreeStock().get(index).getQuantity();
		} else if (i == 2) {
			return stock.getFlowerStock().get(index).getQuantity();
		} else if (i == 3) {
			return stock.getOrnamentStock().get(index).getQuantity();
		}
		throw new ProductTypeException("Producto no reconocido.");
	}

}
