package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Florist;
import model.Product;
import model.RepositoryException;
import model.Ticket;

public class TxtRepository implements RepositoryInterface {

	public Florist findFlorist() throws RepositoryException {
		try {
			File f = new File("./src/database.txt");
			ObjectInputStream ois =
				new ObjectInputStream(new FileInputStream(f));
			Florist florist = (Florist) ois.readObject();
			ois.close();
			return florist;
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
				+ "al intentar recuperar la floristeria de la base de datos.");
		}
	}

	public void addFlorist(Florist florist) throws RepositoryException {
		try {
			writeFlorist();
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
				+ "al intentar crear la floristeria en la base de datos."); 
		}
	}

	public void addProduct(Product product) throws RepositoryException {
		try {
			writeFlorist();
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
				+ "al intentar añadir el producto a la base de datos."); 
		}
	}

	public void updateProduct(Product product) throws RepositoryException {
		try {
			writeFlorist();
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
				+ "al intentar actualizar el producto en la base de datos."); 
		}
	}

	public void deleteProduct(Product product) throws RepositoryException {
		try {
			writeFlorist();
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
				+ "al intentar eliminar el producto de la base de datos."); 
		}
	}

	public void addTicket(Ticket ticket) throws RepositoryException {
		try {
			writeFlorist();
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
				+ "al intentar añadir el ticket a la base de datos."); 
		}
	}
	
	private void writeFlorist() throws IOException {
		Florist florist = Florist.getInstance();
		File f = new File("./src/database.txt");
		ObjectOutputStream oos = 
			new ObjectOutputStream(new FileOutputStream(f));
		oos.writeObject(florist);
		oos.flush();
		oos.close();
	}

	public void deleteFlorist() throws RepositoryException {
		try {
			new FileOutputStream("./src/database.txt").close();
		} catch (Exception e) {
			throw new RepositoryException("Ha ocurrido un error "
				+ "al intentar limpiar la base de datos."); 
		}
	}

}
