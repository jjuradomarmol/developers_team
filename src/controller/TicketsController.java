package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Product;
import model.Ticket;

public class TicketsController {
	
	// Save the ticket in the database
	public void saveTicket() throws FileNotFoundException, IOException {
		Ticket ticket = Ticket.getInstance();
        File f = new File("./src/base_datos.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(ticket);
        oos.flush();
        oos.close();
	}

	public void addTicket(ArrayList<Product> products) throws IOException {
		Ticket.getInstance();
		Ticket.addTicket(products);
		
		// Save the ticket in the database
		saveTicket();
	}
	
	public String showTickets() {
		return Ticket.getInstance().toString();
	}
	
	public double getIncome() {
		return Ticket.getInstance().totalTickets();
	}
}
