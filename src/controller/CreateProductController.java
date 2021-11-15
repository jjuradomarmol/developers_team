package controller;

import model.Product;
import model.ProductFactory;
import model.ProductFactoryCriteria;
import model.Stock;
import model.Tree;
import model.Flower;
import model.MaterialTypeException;
import model.Ornament;

public class CreateProductController {

	public String createProduct(
			String name,
			double price,
			Double height,
			String color,
			String material
	) throws MaterialTypeException {
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
		return product.toString();
	}
}
