package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import model.*;

public class Writer {
	public static void writeFlorist(Florist florist) throws IOException {
        File f = new File("./src/base_datos.txt");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(florist);
        oos.flush();
        oos.close();
    }
}
