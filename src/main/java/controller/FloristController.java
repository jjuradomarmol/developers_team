package controller;

import model.Florist;
import model.RepositoryException;

public class FloristController {
	
	public boolean loadFlorist() {
		boolean flag;
		try {
			Florist florist = RepositoryFactory.getRepository().findFlorist();
			
			if (florist != null) {
				Florist.setInstance(florist);
				flag = true;
			} else {
				flag = false;
			}
		} catch (RepositoryException e) {
			flag = false;
		}
		
		return flag;
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
