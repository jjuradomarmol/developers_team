package controller;

import model.Florist;
import model.MaterialTypeException;
import model.Product;
import model.ProductTypeException;
import model.RepositoryException;
import model.Stock;
import model.Ticket;

public class TicketController {

	public void addNewTicket() {
		Ticket ticket = new Ticket();
		Florist.getInstance().getTicketCollection().addToTickets(ticket);
	}
	
	public void addProductToTicket(int i, int index, int quantityToBuy)
			throws MaterialTypeException, ProductTypeException {
		Stock stock = Florist.getInstance().getStock();
		Ticket ticket =
			Florist.getInstance().getTicketCollection().getLastTicket();
		Product product;
		if (i == 1) {
			product =
				stock.getTreeStock().get(index).getProductCopy(quantityToBuy);
		} else if (i == 2) {
			product =
				stock.getFlowerStock().get(index).getProductCopy(quantityToBuy);
		} else if (i == 3) {
			product = stock.getOrnamentStock().get(index)
				.getProductCopy(quantityToBuy);
		} else {
			throw new ProductTypeException(
				"No se ha podido añadir el producto al ticket de compra."
			);
		}
		ticket.addToPurchasedProducts(product);
	}
	
	public String generateTicket() throws RepositoryException {
		Ticket ticket =
			Florist.getInstance().getTicketCollection().getLastTicket();
		RepositoryFactory.getRepository().addTicket(ticket);
		return ticket.toString();
	}
	
	public String showTickets() {
		return Florist.getInstance().getTicketCollection().toString();
	}
	
	public double getIncome() {
		return Florist.getInstance().getTicketCollection().totalAllTickets();
	}
}
