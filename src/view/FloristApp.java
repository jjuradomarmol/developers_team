package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.*;
import model.MaterialTypeException;
import model.Ornament;
import model.Product;
import model.Stock;

public class FloristApp {

	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		boolean ask = true;
		
		
		try {
			Stock.getInstance().addOrnament(new Ornament ("Yoyo", 12, "madera"));
			Stock.getInstance().addOrnament(new Ornament ("Jarr�n", 12, "pl�stico"));
		} catch (MaterialTypeException e) {
			e.printStackTrace();
		}
		
		
		while (ask) {
			System.out.println("Bienvenido/a. �Qu� desea hacer?\n"
					+ "1. Crear una floristeria\n"
					+ "2. A�adir un producto\n"
					+ "3. Retirar un producto\n"
					+ "4. Ver productos\n"
					+ "5. Ver n�mero de productos disponibles por categoria\n"
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
					selectProduct("a�adir");
					setProduct(sc, (selectNumericOption(sc, 1, 3)));
					break;
				case 3:
					selectProduct("retirar");
					selectProductToRemove(sc);
					break;
				case 4:
					System.out.println(new StocksController().showStock());
					break;
				case 5:
					System.out.println(new StocksController()
						.showStockQuantity());
					break;
				case 6:
					System.out.println("El valor total de la floristeria es de "
							+ Stock.getInstance().getTotalStockValue() +  
							" euros.");
					break;
				case 7:
					createTicket();
					break;
				case 8:
					System.out.println(new TicketsController()
							.showTickets());
					break;
				case 9:
					double income = new TicketsController().getIncome();
					System.out.println(
						"Valor total de las ventas: " + income + "�"
					);
					break;
				case 10:
					ask = false;
					break;		
				}
			
			System.out.println("\n");
		}
		
		sc.close();
	}

	// Added method
	private static void createTicket() throws IOException {
		ProductsController productsController = new ProductsController();
		Stock stock = Stock.getInstance();
		
		ArrayList<Product> productos = new ArrayList<>();
		boolean buyFlag = true;
		int categoria;
		String nombre;
		Product product;
		String seguir;
		
		Scanner sc = new Scanner(System.in);
		
		while (buyFlag) {
			System.out.println("Indique 1 para comprar un �rbol, "
					+ "2 para comprar una flor o 3 para comprar una decoraci�n:");
			categoria = Integer.parseInt(sc.nextLine());
			
			System.out.println("Indique el nombre del producto:");
			nombre = sc.nextLine();
			
			product = productsController.getProduct(categoria, nombre);
			
			if (product != null) {
				productos.add(product);
			}
			
			System.out.println("�Quiere seguir comprando? (s�/no)");
			seguir = sc.nextLine();
			
			if (!seguir.equalsIgnoreCase("s�") || !seguir.equalsIgnoreCase("s�")) {
				buyFlag = false;
			}
		};
		
		new TicketsController().addTicket(productos);
	}

	private static void setProduct(Scanner sc, int option) throws FileNotFoundException, IOException {
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
				break;
			case 2:
				System.out.println("Introduzca el color:");
				color = sc.nextLine();
				break;
			case 3:
				System.out.println("Introduzca el material (madera/pl�stico):");
				material = sc.nextLine().toLowerCase();
				break;
		}
		try {
			String addedProduct = new ProductsController()
				.createProduct(name, price, height, color, material);
			System.out.println("Producto a�adido correctamente.\n"
					+ addedProduct);
		} catch (MaterialTypeException e) {
			System.out.println("No se ha podido a�adir el producto. "
				+ e.getMessage());
			setProduct(sc, option);
		} catch (InputMismatchException e ) {
			System.out.println("No se ha podido a�adir el producto. "
				+ e.getMessage());
		}
	}

	private static void createFlorist(Scanner sc) throws IOException {
		System.out.println("Escriba el nombre de la floristeria "
				+ "que desea crear:");
		String name = sc.nextLine();
		new FloristsController().createFlorist(name);
		System.out.println("La floristeria " + name + 
				" se ha creado correctamente.");
	}

	private static void selectProduct(String operation) {
		System.out.println("Seleccione el tipo de producto que desea "
				+ operation + ":\n"
				+ "1. �rbol\n"
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
			System.out.println("No ha introducido un n�mero."
					+ " Introduzca un n�mero v�lido:");
			return selectNumericOption(sc, min, max);
		} catch (InputMismatchException e) {
			System.out.println("No ha introducido un n�mero v�lido."
					+ " Introduzca el n�mero de una de las opciones:");
			return selectNumericOption(sc, min, max);
		}
	}
	
	private static void selectProductToRemove(Scanner sc) {
		int num = selectNumericOption(sc, 1, 3);
		ProductListControllerResponse response = 
				new ProductsController().getProductList(num);
		if (response.getArrayListSize() == 0) {
			System.out.println("No hay existencias disponibles.");
			return;
		}
		System.out.println("Estos son los art�culos disponibles:");
		System.out.println(response.getListToPrint());
		System.out.println("Introduzca el n�mero del art�culo a retirar:");
		int itemNumber = (selectNumericOption(
			sc, 
			1, 
			response.getArrayListSize()
		)) - 1;
		try {
			new ProductsController().removeProduct(num, itemNumber);
			System.out.println("El producto se ha eliminado correctamente.");
		} catch (Exception e) {
			System.out.println("No se ha podido eliminar el producto.");
		}
	}

}
