package view;

import model.*;
import java.io.IOException;
import java.util.ArrayList;

import controller.*;

public class FloristApp {

	public static void main(String[] args) throws IOException, ClassNotFoundException, TipoMaterialException {
	
	Florist miFloristeria = Florist.getInstance("miFloristeria");
	
	Stock stock = Stock.getInstance();
	
	Tree olivo = new Tree("Olivo", 23.3, 1.80);
	Tree naranjo = new Tree("Naranjo", 19.90, 1.80);
	stock.addTree(olivo);
	stock.addTree(naranjo);
	Flower jazmin = new Flower("Jazmín", 7.0, "blanco");
	Flower rosa = new Flower("Rosa", 4.0, "rojo");
	stock.addFlower(jazmin);
	stock.addFlower(rosa);
	Ornament mascara = new Ornament("Máscara mexicana", 60.0, "madera");
	Ornament funkoPop = new Ornament("Funko Baby Yoda", 17, "plástico");
	stock.addOrnament(mascara);
	stock.addOrnament(funkoPop);
	
	FloristRepository.writeFlorist(miFloristeria);
	
	System.out.println(FloristRepository.readFlorist().toString());
	
	System.out.println("\n\n");
	
	stock.deleteTree("Naranjo");
	stock.deleteFlower("Rosa");
	stock.deleteOrnament("Funko Baby Yoda");
	
	System.out.println("Tres productos borrados");
	
	System.out.println("\n\n");
	
	FloristRepository.writeFlorist(miFloristeria);
	
	System.out.println(FloristRepository.readFlorist().toString());
	
	ArrayList<Product> compra1 = new ArrayList<>();
	compra1.add(olivo);
	compra1.add(mascara);
	Ticket.addTicket(compra1);
	
	ArrayList<Product> compra2 = new ArrayList<>();
	compra2.add(jazmin);
	Ticket.addTicket(compra2);
	
	FloristRepository.writeFlorist(miFloristeria);
	
	System.out.println("\n\n\n");
	
	System.out.println(FloristRepository.readFlorist().printTickets());
	
	System.out.println(FloristRepository.readFlorist().getTicket().totalTickets());
	
	}

}
