package controller;

import model.Ticket;

public class GetIncomeController {

	public double getIncome() {
		return Ticket.getInstance().totalTickets();
	}

}
