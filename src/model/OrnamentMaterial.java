package model;

public class OrnamentMaterial {
	
	private static OrnamentMaterial instance;
	public static String type;
	
	private OrnamentMaterial (String type){
		OrnamentMaterial.type = type;
	}
	
	public static OrnamentMaterial getInstance(String type) {
		if (instance == null) {
			if (type.equalsIgnoreCase("wood")) {
				instance = new OrnamentMaterial("Madera");
			} else if (type.equalsIgnoreCase("plastic")) {
				instance = new OrnamentMaterial("Pl�stico");
			}
			else {
				System.out.println("No ha escrito un material de decoraci�n v�lido");
			}
		}
		return instance;
	}

	public static String getType() {
		return type;
	}
	
}
