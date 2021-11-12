package view;

import model.*;
import model.ticketMemento.*;

import java.io.IOException;
import java.util.ArrayList;

import controller.*;

public class FloristApp {

	public static void main(String[] args) throws IOException, ClassNotFoundException, TipoMaterialException {
		
	Florist miFloristeria = Florist.getInstance("miFloristeria");
	
	Stock stock = Stock.getInstance();
	
	Tree olivo = new Tree("Olivo", 23.3, 1.80);
	stock.addTree(olivo);
	Flower jazmin = new Flower("Jazm�n", 7.0, "blanco");
	stock.addFlower(jazmin);
	Ornament mascara = new Ornament("M�scara mexicana", 60.0, "pl�stico");
	stock.addOrnament(mascara);
	
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
	
	ArrayList<Product> compra1 = new ArrayList<>();
	compra1.add(olivo);
	compra1.add(mascara);
	Ticket ticket1 = new Ticket(compra1);
	
	
	Caretaker caretaker = new Caretaker();
	Originator originator = new Originator();
	
	originator.setState(ticket1);
	caretaker.addMemento(originator.saveStateToMemento());
	
	// Falta por recuperar un ticket, pero cuando se a�adan varios, se puede recuperar 
	// por la id con originator.getStateFromMemento(caretaker.getMemento(numeroDeTicket));
	
	Writer.writeFlorist(miFloristeria);
	
	System.out.println("\n\n\n");
	System.out.println(Reader.readFlorist().toString());
	
	}

}
