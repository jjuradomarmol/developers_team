package model;

public interface RepositoryInterface {
	
	Florist findFlorist() throws RepositoryException;
	
	void addFlorist(Florist florist) throws RepositoryException;
	
	void addProduct(Product product) throws RepositoryException;
	
	void updateProduct(Product product) throws RepositoryException;
	
	void deleteProduct(Product product) throws RepositoryException;
	
	void addTicket(Ticket ticket) throws RepositoryException;

}
