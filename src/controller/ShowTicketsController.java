package controller;

import model.Ticket;

public class ShowTicketsController {
	
	public String showTickets() {
		return Ticket.getInstance().toString();
	}
}
