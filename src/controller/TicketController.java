package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.MaterialTypeException;
import model.Product;
import model.ProductTypeException;
import model.Stock;
import model.Ticket;

public class TicketController {
	
	// Save the ticket in the database
	public void saveTicket() throws FileNotFoundException, IOException {
		Ticket ticket = Ticket.getInstance();
        File f = new File("./src/base_datos.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(ticket);
        oos.flush();
        oos.close();
	}

	public void addProductToTempTicket(int i, int index, int quantityToBuy) 
		throws FileNotFoundException, IOException, MaterialTypeException, 
		ProductTypeException {
		Stock stock = Stock.getInstance();
		Ticket ticket = Ticket.getInstance();
		Product product = null;
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
		ArrayList<Product> listToPrint = Ticket.getInstance().generateTicket();
		Ticket.getInstance().clearTempProductList();
		String ticket = "";
		for (Product product : listToPrint) {
			ticket += "\t\tProducto: " + product.getName() + ", cantidad: " +
					product.getQuantity()+ ", precio por unidad: " +
					product.getPrice() + "€, total: " + 
					product.getPrice() * product.getQuantity() + "€.\n";
		}
		return ticket;
		//add ticket to database
	}
	
	public void clearTempProductList() {
		Ticket.getInstance().clearTempProductList();
	}
	
	public String showTickets() {
		return Ticket.getInstance().toString();
	}
	
	public double getIncome() {
		return Ticket.getInstance().totalTickets();
	}
}
