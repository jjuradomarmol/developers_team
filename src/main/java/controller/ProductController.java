package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Florist;
import model.Flower;
import model.MaterialTypeException;
import model.Ornament;
import model.Product;
import model.ProductFactory;
import model.ProductFactoryCriteria;
import model.ProductTypeException;
import model.RepositoryException;
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
	) throws ProductTypeException, MaterialTypeException, RepositoryException {
		Stock stock = Florist.getInstance().getStock();
		Product product = 
			prepareProduct(name, price, height, color, material, quantity);
		if (product instanceof Tree) {
			stock.addTree((Tree) product);
		} else if (product instanceof Flower) {
			stock.addFlower((Flower) product);
		} else if (product instanceof Ornament) {
			stock.addOrnament((Ornament) product);
		} else {
			throw new ProductTypeException("No se ha podido "
				+ "añadir el producto");
		}
		
		RepositoryFactory.getRepository().addProduct(product);
	}

	public ProductListControllerResponse getProductList(int i) {
		Stock stock = Florist.getInstance().getStock();
		String productList = "";
		int size = 0;
		if (i == 1) {
			size = stock.getTreeStock().size();
			for (Tree tree : stock.getTreeStock()) {
				productList = productList + 
				(stock.getTreeStock().indexOf(tree) + 1) + ". " +
				tree.toString() + "\n";
			}
		} else if (i == 2) {
			size = stock.getFlowerStock().size();
			for (Flower flower : stock.getFlowerStock()) {
				productList = productList + 
				(stock.getFlowerStock().indexOf(flower) + 1) +
				". " + flower.toString() + "\n";
			}
		} else if (i == 3) {
			size = stock.getOrnamentStock().size();
			for (Ornament ornament : stock.getOrnamentStock()) {
				productList = productList + 
				(stock.getOrnamentStock().indexOf(ornament) + 1) +
				". " + ornament.toString() + "\n";
			}
		}
		return new ProductListControllerResponse(productList, size);
	}
	
	public void removeProduct(int i, int index) throws FileNotFoundException, 
		IOException, RepositoryException, ProductTypeException {
		Stock stock = Florist.getInstance().getStock();
		Product product = null;
		if (i == 1) {
			product = stock.getTreeStock().get(index);
			stock.deleteTree((Tree)product);
		} else if (i == 2) {
			product = stock.getFlowerStock().get(index);
			stock.deleteFlower((Flower)product);
		} else if (i == 3) {
			product = stock.getOrnamentStock().get(index);
			stock.deleteOrnament((Ornament)product);
		} else {
			throw new ProductTypeException("Producto no reconocido.");
		}
		RepositoryFactory.getRepository().deleteProduct(product);
	}

	public void updateQuantity(int i, int index, int quantity) 
		throws FileNotFoundException, IOException, 
		ProductTypeException, RepositoryException {
		Stock stock = Florist.getInstance().getStock();
		Product product = null;
		if (i == 1) {
			product = stock.getTreeStock().get(index);
		} else if (i == 2) {
			product = stock.getFlowerStock().get(index);
		} else if (i == 3) {
			product = stock.getOrnamentStock().get(index);
		} else {
			throw new ProductTypeException("Producto no reconocido.");
		}
		product.setQuantity(product.getQuantity() + quantity);
		RepositoryFactory.getRepository().updateProduct(product);
	}

	public int getIndex(
		String name,
		double price,
		Double height,
		String color,
		String material,
		int quantity
	) throws ProductTypeException, MaterialTypeException {
		Stock stock = Florist.getInstance().getStock();
		Product product = 
			prepareProduct(name, price, height, color, material, quantity);
		if (product instanceof Tree) {
			for (Tree tree : stock.getTreeStock()) {
				if (tree.getName().equalsIgnoreCase(product.getName())
					&& Math.round(tree.getPrice() * 100.0) / 100.0 == 
					Math.round(product.getPrice() * 100.0) / 100.0
					&& Math.round(tree.getHeight() * 100.0) / 100.0 == 
					Math.round(((Tree) product).getHeight() * 100.0) / 100.0) {
					return stock.getTreeStock().indexOf(tree);
				}
			}				
		} else if (product instanceof Flower) {
			for (Flower flower : stock.getFlowerStock()) {
				if (flower.getName().equalsIgnoreCase(product.getName())
					&& Math.round(flower.getPrice() * 100.0) / 100.0 == 
					Math.round(product.getPrice() * 100.0) / 100.0
					&& flower.getColor()
					.equalsIgnoreCase(((Flower) product).getColor())) {
					return stock.getFlowerStock().indexOf(flower);
				}
			}
		} else if (product instanceof Ornament) {
			for (Ornament ornament : stock.getOrnamentStock()) {
				if (ornament.getName().equalsIgnoreCase(product.getName())
					&& Math.round(ornament.getPrice() * 100.0) / 100.0 == 
					Math.round(product.getPrice() * 100.0) / 100.0
					&& ornament.getMaterial()
					.equalsIgnoreCase(((Ornament) product).getMaterial())) {
					return stock.getOrnamentStock().indexOf(ornament);
				}
			}
		} else {
			throw new ProductTypeException("No se ha reconocido el producto.");
		}
		return -1;
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
