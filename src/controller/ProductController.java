package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import model.Flower;
import model.MaterialTypeException;
import model.Ornament;
import model.Product;
import model.ProductFactory;
import model.ProductFactoryCriteria;
import model.ProductTypeException;
import model.Stock;
import model.Tree;

public class ProductController {
	
	private Product prepareProduct(
		String name,
		double price,
		Double height,
		String color,
		String material,
		int quantity
	) throws MaterialTypeException, ProductTypeException {
		ProductFactoryCriteria productCriteria = 
			new ProductFactoryCriteria(name, price, quantity);
		productCriteria.setHeight(height);
		productCriteria.setColor(color);
		productCriteria.setMaterial(material);
		productCriteria.setQuantity(quantity);
		return new ProductFactory().forgeProduct(productCriteria);
	}
	
	public void addProduct (
		String name,
		double price,
		Double height,
		String color,
		String material,
		int quantity
	) throws ProductTypeException, MaterialTypeException {
		Product product = 
			prepareProduct(name, price, height, color, material, quantity);
		if (product instanceof Tree) {
			Stock.getInstance().addTree((Tree) product);
			// add Tree
		} else if (product instanceof Flower) {
			Stock.getInstance().addFlower((Flower) product);
			// add Flower
		} else if (product instanceof Ornament) {
			Stock.getInstance().addOrnament((Ornament) product);
			// Add Ornament
		} else {
			throw new ProductTypeException("No se ha podido "
				+ "añadir el producto");
		}
		// Save the stock in the database after creating product
		//add try and catch
		//new StocksController().saveStock();
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
	
	public void removeProduct(int i, int index) 
		throws FileNotFoundException, IOException {
		Stock stock = Stock.getInstance();
		if (i == 1) {
			stock.deleteTree(stock.getTreeStock().get(index));
			//remove Tree
		} else if (i == 2) {
			stock.deleteFlower(stock.getFlowerStock().get(index));
			//remove Flower
		} else if (i == 3) {
			stock.deleteOrnament(stock.getOrnamentStock().get(index));
			//remove Ornament
		}
		
		// Save the stock in the database after deleting product
		new StockController().saveStock();
	}

	public void updateQuantity(int i, int index, int quantity) 
		throws FileNotFoundException, IOException {
		Stock stock = Stock.getInstance();
		if (i == 1) {
			Tree tree = stock.getTreeStock().get(index);
			tree.setQuantity(tree.getQuantity() + quantity);
			//update Tree
		} else if (i == 2) {
			Flower flower = stock.getFlowerStock().get(index);
			flower.setQuantity(flower.getQuantity() + quantity);
			//update Flower
		} else if (i == 3) {
			Ornament ornament = stock.getOrnamentStock().get(index);
			ornament.setQuantity(ornament.getQuantity() + quantity);
			//update Ornament
		}
		// Save the stock in the database after updating quantity
		new StockController().saveStock();
	}

	public int checkExistance(
		String name,
		double price,
		Double height,
		String color,
		String material,
		int quantity
	) throws ProductTypeException, MaterialTypeException {
		Product product = 
			prepareProduct(name, price, height, color, material, quantity);
		if (product instanceof Tree) {
			for (Tree tree : Stock.getInstance().getTreeStock()) {
				if (tree.getName().equalsIgnoreCase(product.getName())
					&& tree.getPrice() == product.getPrice()
					&& tree.getHeight() == ((Tree) product).getHeight()) {
					return Stock.getInstance().getTreeStock().indexOf(tree);
				}
			}				
		} else if (product instanceof Flower) {
			for (Flower flower : Stock.getInstance().getFlowerStock()) {
				if (flower.getName().equalsIgnoreCase(product.getName())
					&& flower.getPrice() == product.getPrice()
					&& flower.getColor()
					.equalsIgnoreCase(((Flower) product).getColor())) {
					return Stock.getInstance().getFlowerStock().indexOf(flower);
				}
			}
		} else if (product instanceof Ornament) {
			for (Ornament ornament : Stock.getInstance().getOrnamentStock()) {
				if (ornament.getName().equalsIgnoreCase(product.getName())
					&& ornament.getPrice() == product.getPrice()
					&& ornament.getMaterial()
					.equalsIgnoreCase(((Ornament) product).getMaterial())) {
					return Stock.getInstance().getOrnamentStock()
						.indexOf(ornament);
				}
			}
		} else {
			throw new ProductTypeException("No se ha reconocido el producto.");
		}
		return -1;
	}
	
}
