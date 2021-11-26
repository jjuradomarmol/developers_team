package controller;

import java.io.IOException;
import model.Florist;
import model.RepositoryException;

public class FloristController {
	
	public boolean createFlorist(String name)
		throws IOException, RepositoryException {
			Florist.getInstance().setName(name);
			Florist.getInstance().resetFlorist();
			RepositoryFactory.getRepository().addFlorist(Florist.getInstance());
			return true;
	}
	
	public String getFloristName() {
		return Florist.getInstance().getName();
	}
}
