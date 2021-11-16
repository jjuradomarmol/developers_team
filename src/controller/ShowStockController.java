package controller;

import model.Stock;

public class ShowStockController {
	
	public String showStock() {
		return Stock.getInstance().toString();
	}

}
