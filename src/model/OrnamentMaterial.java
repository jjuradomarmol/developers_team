package model;

import java.util.HashMap;

public class OrnamentMaterial {
	
	private static HashMap<String, OrnamentMaterial> instance =
		new HashMap<String, OrnamentMaterial>();

	private String type;
	public final static String WOOD = "WOOD";
	public final static String PLASTIC = "PLASTIC";
	
	private OrnamentMaterial (String type){
		this.type = type;
	}
	
	public static OrnamentMaterial getInstance(String type) {
		if (!instance.containsKey(type)) {
			instance.put(type, new OrnamentMaterial(type));
		}
		return instance.get(type);
	}

	public String getType() {
		return type;
	}
	
}
