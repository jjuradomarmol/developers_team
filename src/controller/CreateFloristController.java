package controller;

import model.Florist;

public class CreateFloristController {

	public void createFlorist(String name) {
		Florist florist = Florist.getInstance(name);
		florist.setName(name);
		//C�digo para crear la floristeria en la base de datos
	}
	
}
