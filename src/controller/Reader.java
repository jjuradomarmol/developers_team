package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import model.Florist;

public class Reader {

	public static Florist readFlorist() throws IOException, ClassNotFoundException {
        File f = new File("./src/base_datos.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Florist miFloristeria = (Florist) ois.readObject();
        ois.close();
        return miFloristeria;
    }
	
}
