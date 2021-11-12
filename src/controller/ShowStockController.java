package controller;

import model.Stock;

public class ShowStockController {
	
	public void showStock() {
		Stock.getInstance().toString();
	}

}
