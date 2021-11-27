package controller;

import java.io.IOException;
import model.Florist;
import model.RepositoryException;

public class FloristController {
	
	public void createFlorist(String name)
		throws IOException, RepositoryException {
			Florist.getInstance().setName(name);
			Florist.getInstance().resetFlorist();
			RepositoryFactory.getRepository().addFlorist(Florist.getInstance());
	}
	
	public String getFloristName() {
		return Florist.getInstance().getName();
	}
}
