package controller;

import java.io.IOException;

import model.Florist;
import model.RepositoryException;

public class FloristController {
	
	public boolean createFlorist(String name)
		throws IOException, RepositoryException {
		try {
			Florist florist = RepositoryFactory.getRepository().findFlorist();
			Florist.setInstance(florist);
			return false;			
		} catch (Exception e) {
			Florist.getInstance().setName(name);
			RepositoryFactory.getRepository().addFlorist(Florist.getInstance());
			return true;
		}
	}
	
	public String getFloristName() {
		return Florist.getInstance().getName();
	}
}
