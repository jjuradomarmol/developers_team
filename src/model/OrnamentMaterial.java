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
				instance = new OrnamentMaterial("Plástico");
			}
			else {
				System.out.println("No ha escrito un material de decoración válido");
			}
		}
		return instance;
	}

	public static String getType() {
		return type;
	}
	
}
