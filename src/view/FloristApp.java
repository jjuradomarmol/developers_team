package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import controller.CreateFloristController;
import controller.ShowStockController;
import controller.CreateProductController;

public class FloristApp {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		boolean ask = true;
		
		while (ask) {
			System.out.println("Bienvenido/a. ¿Qué desea hacer?\n"
					+ "1. Crear una floristeria\n"
					+ "2. Añadir un producto\n"
					+ "3. Retirar un producto\n"
					+ "4. Ver productos\n"
					+ "5. Ver cantidades disponibles\n"
					+ "6. Ver el valor de la floristeria\n"
					+ "7. Comprar\n"
					+ "8. Ver historial de compras\n"
					+ "9. Ver ganancias\n"
					+ "10. Salir");
			
			int num = selectNumericOption(sc, 1, 10);
			
			switch(num) {
				
				case 1:
					createFlorist(sc);
					break;
				case 2:
					selectProduct();
					setProduct(sc, (selectNumericOption(sc, 1, 3)));
					break;
				case 3:
					selectProduct();
					selectNumericOption(sc, 1, 3);
					break;
				case 4:
					new ShowStockController().showStock();
					break;
				case 5:
					
					break;
				case 6:
					System.out.println("El valor total de la floristeria es de "
							+  " euros.");
					break;
				case 7:
					
					break;
				case 8:
					
					break;
				case 9:
					
					break;
				case 10:
					ask = false;
					break;		
				}
		}
		
		sc.close();
		
	}

	private static void setProduct(Scanner sc, int option) {
		System.out.println("Introduzca el nombre:");
		String name = sc.nextLine();
		System.out.println("Introduzca el precio:");
		double price = Double.parseDouble(sc.nextLine());
		Double height = null;
		String color = null, material = null;
		switch (option) {
			case 1:
				System.out.println("Introduzca la altura:");
				height = Double.parseDouble(sc.nextLine());
			case 2:
				System.out.println("Introduzca el color:");
				color = sc.nextLine();
			case 3:
				System.out.println("Introduzca el material (madera/plástico):");
				String m = sc.nextLine();
				while ((!m.equalsIgnoreCase("madera"))
						&&(!m.equalsIgnoreCase("plástico"))) {
					System.out.println("El material introducido no es válido. "
							+ "Introduzca un material válido "
							+ "(madera/plástico):");
					m = sc.nextLine();
					}
				material = m.toLowerCase();
		}
		try {
			String addedProduct = new CreateProductController()
				.createProduct(name, price, height, color, material);
			System.out.println("Producto añadido correctamente.\n"
					+ addedProduct);
		} catch (Exception e) {
			System.out.println("No se ha podido añadir el producto. "
				+ e.getMessage());
		}
	}

	private static void createFlorist(Scanner sc) {
		System.out.println("Escriba el nombre de la floristeria "
				+ "que desea crear:");
		String name = sc.nextLine();
		new CreateFloristController().createFlorist(name);
		System.out.println("La floristeria " + name + 
				" se ha creado correctamente.");
	}

	private static void selectProduct() {
		System.out.println("Seleccione el tipo de producto:\n"
				+ "1. Árbol\n"
				+ "2. Flor\n"
				+ "3. Adorno");
	}
	
	private static int selectNumericOption(Scanner sc, int min, int max) {
		try {
			int num = Integer.parseInt(sc.nextLine());
			if (!(min <= num && max>= num)) {
				throw new InputMismatchException();
			}
			return num;
		} catch (NumberFormatException e) {
			System.out.println("No ha introducido un número."
					+ " Introduzca un número válido:");
			return selectNumericOption(sc, min, max);
		} catch (InputMismatchException e) {
			System.out.println("No ha introducido un número válido."
					+ " Introduzca el número de una de las opciones:");
			return selectNumericOption(sc, min, max);
		}
	}

}
