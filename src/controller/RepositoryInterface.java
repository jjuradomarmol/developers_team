package controller;

import model.Florist;
import model.Product;
import model.RepositoryException;
import model.Ticket;

public interface RepositoryInterface {
	
	Florist findFlorist() throws RepositoryException;
	
	void addFlorist(Florist florist) throws RepositoryException;
	
	void addProduct(Product product) throws RepositoryException;
	
	void updateProduct(Product product) throws RepositoryException;
	
	void deleteProduct(Product product) throws RepositoryException;
	
	void addTicket(Ticket ticket) throws RepositoryException;

}
