package model;

import java.io.Serializable;
import java.util.ArrayList;

public class TicketCollection implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Ticket> tickets;
	
	public TicketCollection() {
		this.tickets = new ArrayList<Ticket>();
	}
	
	public void addToTickets(Ticket ticket) {
		this.tickets.add(ticket);
	}
	
	public Ticket getLastTicket() {
		return this.tickets.get(tickets.size()-1);
	}
	
	public double totalAllTickets() {
		double result = 0.0;
		for (Ticket ticket : this.tickets) {
			result += ticket.getTotalTicket();
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "Lista de tickets: ";
		int i = 1;
		for (Ticket ticket : this.tickets) {
			result += "\n\tTicket nº " + i + ":\n";
			result += ticket.toString();
			i++;
		}
		return result;
	}
	
}
