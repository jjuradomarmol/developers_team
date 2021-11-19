package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.ProductTypeException;
import model.Stock;

public class StockController {
	
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
	
	public double showStockValue() {
		return Stock.getInstance().getTotalStockValue();
	}
	
	public int getProductQuantity(int i, int index) 
		throws ProductTypeException {
		Stock stock = Stock.getInstance();
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
