package model;

public class Florist {
	
	private String name;
	
	private Stock stock;
	
	public Florist(String name) {
		this.name = name;
		this.stock = new Stock();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Stock getStock() {
		return stock;
	}

}
