package model;

public class Ornament extends Product {
	
	private OrnamentMaterial material;
	
	public Ornament(String name, double price, OrnamentMaterial material) {
		super(name, price);
		this.material = material;
	}

	public OrnamentMaterial getMaterial() {
		return material;
	}

	public void setMaterial(OrnamentMaterial material) {
		this.material = material;
	}

}
