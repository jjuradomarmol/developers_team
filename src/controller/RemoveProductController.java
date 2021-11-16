package controller;

import model.Stock;

public class RemoveProductController {

	public void removeProduct(int i, int index) {
		Stock stock = Stock.getInstance();
		if (i == 1) {
			stock.deleteTree(stock.getTreeStock().get(index));
			//c�digo para borrar el �rbol de la base de datos
		} else if (i == 2) {
			stock.deleteFlower(stock.getFlowerStock().get(index));
			//c�digo para borrar la flor de la base de datos
		} else if (i == 3) {
			stock.deleteOrnament(stock.getOrnamentStock().get(index));
			//c�digo para borrar el adorno de la base de datos
		}
	}
	
}
