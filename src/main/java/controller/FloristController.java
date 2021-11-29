package controller;

import model.Florist;
import model.RepositoryException;

public class FloristController {
	
	public boolean loadFlorist() {
		try {
			Florist florist = RepositoryFactory.getRepository().findFlorist();
			Florist.setInstance(florist);
			return true;			
		} catch (RepositoryException e) {
			return false;
		}
	}
	
	public void createFlorist(String name) throws RepositoryException {
		Florist.getInstance().setName(name);
		RepositoryFactory.getRepository().addFlorist(Florist.getInstance());
	}
	
	public void deleteFlorist() throws RepositoryException {
		RepositoryFactory.getRepository().deleteFlorist();
		Florist.getInstance().emptyFlorist();
	}
	
	public String getFloristName() {
		return Florist.getInstance().getName();
	}

	
}
