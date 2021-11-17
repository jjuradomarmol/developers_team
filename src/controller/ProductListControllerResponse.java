package controller;

public class ProductListControllerResponse {
	
	private String listToPrint;
	private int arrayListSize;
	
	public ProductListControllerResponse(String list, int size) {
		this.listToPrint = list;
		this.arrayListSize = size;
	}

	public String getListToPrint() {
		return listToPrint;
	}

	public int getArrayListSize() {
		return arrayListSize;
	}
	
}
