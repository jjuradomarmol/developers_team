package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Florist;

public class FloristRepository {
	
	/* Los nombres de las variables deberían estar en inglés 
	y el código no debería traspasar la marca de fin de línea*/
	
	public static Florist readFlorist() throws IOException, ClassNotFoundException {
        File f = new File("./src/base_datos.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
        Florist miFloristeria = (Florist) ois.readObject();
        ois.close();
        return miFloristeria;
    }
	
	public static void writeFlorist(Florist florist) throws IOException {
        File f = new File("./src/base_datos.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(florist);
        oos.flush();
        oos.close();
    }
	
}
