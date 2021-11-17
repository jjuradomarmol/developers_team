package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.Stock;

public class StocksController {
	
	// Save the stock in the database
	public void saveStock() throws FileNotFoundException, IOException {
		Stock stock = Stock.getInstance();
        File f = new File("./src/base_datos.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(stock);
        oos.flush();
        oos.close();
	}

	public String showStock() {
		return Stock.getInstance().toString();
	}
	
	public String showStockQuantity() {
		Stock stock = Stock.getInstance();
		return "Árboles: " + stock.getTreeStock().size() + "\n"
			+ "Flores: " + stock.getFlowerStock().size() + "\n"
			+ "Adornos: " + stock.getOrnamentStock().size();
	}
	
	public void showStockValue() {
		Stock.getInstance().getTotalStockValue();
	}

}
