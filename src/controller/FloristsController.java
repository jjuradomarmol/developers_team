package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Florist;

public class FloristsController {
	
	public static Florist readFlorist() throws IOException, ClassNotFoundException {
        File f = new File("./src/base_datos.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Florist florist = (Florist) ois.readObject();
        ois.close();
        return florist;
    }
	
	public static void createFlorist(String name) throws IOException {
		Florist florist = Florist.getInstance(name);
        File f = new File("./src/base_datos.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(florist);
        oos.flush();
        oos.close();
    }
	
}
