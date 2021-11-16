package controller;

public class GetProductListControllerResponse {
	
	private String listToPrint;
	private int arrayListSize;
	
	public GetProductListControllerResponse(String list, int size) {
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
