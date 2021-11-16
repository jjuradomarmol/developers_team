package model;

import java.util.InputMismatchException;

public class ProductFactory {
	
	public Product createProduct(
		ProductFactoryCriteria product
	) throws MaterialTypeException {
		if (product.hasHeight()) {
			return new Tree (
				product.getName(), 
				product.getPrice(), 
				product.getHeight()
			);
		} else if (product.hasColor()) {
			return new Flower (
				product.getName(), 
				product.getPrice(), 
				product.getColor()
			);
		} else if (product.hasMaterial()) {
			return new Ornament (
				product.getName(), 
				product.getPrice(), 
				product.getMaterial()
			);
		} else {
			throw new InputMismatchException("Producto no reconocido.");
		}
	}

}
