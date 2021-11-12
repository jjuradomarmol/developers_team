package view;

import model.*;

import java.io.IOException;

import controller.*;

public class FloristApp {

	public static void main(String[] args) throws IOException, ClassNotFoundException, TipoMaterialException {
		
	Florist miFloristeria = Florist.getInstance("miFloristeria");
	
	Stock stock = Stock.getInstance();
	
	stock.addTree(new Tree("Olivo", 23.3, 1.80));
	stock.addFlower(new Flower("Jazm�n", 7.0, "blanco"));
	stock.addOrnament(new Ornament("M�scara mexicana", 60.0, "wood"));
	
	Writer.writeFlorist(miFloristeria);
	
	System.out.println(Reader.readFlorist().toString());
	
	/*
	System.out.println("\n\n");
	
	stock.deleteTree("Olivo");
	stock.deleteFlower("Jazm�n");
	stock.deleteOrnament("M�scara mexicana");
	
	Writer.writeFlorist(miFloristeria);
	
	System.out.println(Reader.readFlorist().toString());
	}
	*/
	
	}

}
