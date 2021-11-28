package model;

public class ProductFactory {
	
	public Product forgeProduct(
		ProductFactoryCriteria product
	) throws MaterialTypeException, ProductTypeException {
		if (product.hasHeight()) {
			return new Tree (
				product.getName(), 
				product.getPrice(), 
				product.getHeight(),
				product.getQuantity()
			);
		} else if (product.hasColor()) {
			return new Flower (
				product.getName(), 
				product.getPrice(), 
				product.getColor(),
				product.getQuantity()
			);
		} else if (product.hasMaterial()) {
			return new Ornament (
				product.getName(), 
				product.getPrice(), 
				product.getMaterial(),
				product.getQuantity()
			);
		} else {
			throw new ProductTypeException("Producto no reconocido.");
		}
	}

}
