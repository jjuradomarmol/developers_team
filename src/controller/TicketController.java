package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import model.Florist;
import model.MaterialTypeException;
import model.Product;
import model.ProductTypeException;
import model.Stock;
import model.Ticket;

public class TicketController {

	public void addProductToTempTicket(int i, int index, int quantityToBuy) 
		throws FileNotFoundException, IOException, MaterialTypeException, 
		ProductTypeException {
		Stock stock = Florist.getInstance().getStock();
		Ticket ticket = Florist.getInstance().getTicket();
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
		ticket.addProductToTempList(product);
	}
	
	public String generateTicket() throws IOException {
		Ticket ticket = Florist.getInstance().getTicket();
		ArrayList<Product> listToPrint = ticket.generateTicket();
		ticket.clearTempProductList();
		String ticketToPrint = "";
		double total = 0;
		for (Product product : listToPrint) {
			ticketToPrint += "\t\tProducto: " + 
			product.getName() + ", cantidad: " +
			product.getQuantity()+ ", precio por unidad: " +
			product.getPrice() + "€, total: " + 
			product.getPrice() * product.getQuantity() + "€.\n";
			total += product.getPrice() * product.getQuantity();
		}
		ticketToPrint += "\t\tTotal: " + total + "€.\n";
		//RepositoryFactory.getRepository().addTicket();
		return ticketToPrint;
	}
	
	public void clearTempProductList() {
		Florist.getInstance().getTicket().clearTempProductList();
	}
	
	public String showTickets() {
		return Florist.getInstance().getTicket().toString();
	}
	
	public double getIncome() {
		return Florist.getInstance().getTicket().totalTickets();
	}
}
