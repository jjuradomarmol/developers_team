package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Flower;
import model.MaterialTypeException;
import model.Ornament;
import model.Product;
import model.ProductFactory;
import model.ProductFactoryCriteria;
import model.Stock;
import model.Tree;

public class ProductsController {
	
	public String createProduct(
			String name,
			double price,
			Double height,
			String color,
			String material
	) throws MaterialTypeException, FileNotFoundException, IOException {
		ProductFactoryCriteria productCriteria = 
				new ProductFactoryCriteria(name, price);
		productCriteria.setHeight(height);
		productCriteria.setColor(color);
		productCriteria.setMaterial(material);
		Product product = new ProductFactory().createProduct(productCriteria);
		
		if (product instanceof Tree) {
			Stock.getInstance().addTree((Tree) product);
			// código para añadir árbol a la base de datos
		} else if (product instanceof Flower) {
			Stock.getInstance().addFlower((Flower) product);
			// código para añadir flor a la base de datos
		} else if (product instanceof Ornament) {
			Stock.getInstance().addOrnament((Ornament) product);
			// código para añadir adorno a la base de datos
		}
		
		// Save the stock in the database after creating product
		new StocksController().saveStock();
		
		return product.toString();
	}
	
	public Product getProduct(int categoria, String nombre) {
		Product product;
		if (categoria == 1) {
			product = Stock.getInstance().getTree(nombre);
		} else if (categoria == 2) {
			product = Stock.getInstance().getFlower(nombre);
		} else if (categoria == 3) {
			product = Stock.getInstance().getOrnament(nombre);
		} else {
			product = null;
		}
		
		return product;
	}

	public ProductListControllerResponse getProductList(int i) {
		String productList = "";
		int size = 0;
		if (i == 1) {
			size = Stock.getInstance().getTreeStock().size();
			for (Tree tree : Stock.getInstance().getTreeStock()) {
				productList = productList + 
				(Stock.getInstance().getTreeStock().indexOf(tree) + 1) + ". " +
				tree.toString() + "\n";
			}
		} else if (i == 2) {
			size = Stock.getInstance().getFlowerStock().size();
			for (Flower flower : Stock.getInstance().getFlowerStock()) {
				productList = productList + 
				(Stock.getInstance().getFlowerStock().indexOf(flower) + 1) +
				". " + flower.toString() + "\n";
			}
		} else if (i == 3) {
			size = Stock.getInstance().getOrnamentStock().size();
			for (Ornament ornament : Stock.getInstance().getOrnamentStock()) {
				productList = productList + 
				(Stock.getInstance().getOrnamentStock().indexOf(ornament) + 1) +
				". " + ornament.toString() + "\n";
			}
		}
		return new ProductListControllerResponse(productList, size);
	}
	
	public void removeProduct(int i, int index) throws FileNotFoundException, IOException {
		Stock stock = Stock.getInstance();
		if (i == 1) {
			stock.deleteTree(stock.getTreeStock().get(index));
			//código para borrar el árbol de la base de datos
		} else if (i == 2) {
			stock.deleteFlower(stock.getFlowerStock().get(index));
			//código para borrar la flor de la base de datos
		} else if (i == 3) {
			stock.deleteOrnament(stock.getOrnamentStock().get(index));
			//código para borrar el adorno de la base de datos
		}
		
		// Save the stock in the database after deleting product
		new StocksController().saveStock();
	}
}
