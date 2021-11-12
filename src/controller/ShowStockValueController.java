package controller;

import model.Stock;

public class ShowStockValueController {
	
	public void showStockValue() {
		Stock.getInstance().getTotalStockValue();
	}

}
