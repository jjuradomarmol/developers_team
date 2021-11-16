package controller;

import model.Stock;
import model.Tree;
import model.Flower;
import model.Ornament;

public class GetProductListController {
	
	public GetProductListControllerResponse getProductList(int i) {
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
		return new GetProductListControllerResponse(productList, size);
	}

}
