package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import controller.*;
import model.Florist;
import model.MaterialTypeException;
import model.RepositoryException;

public class FloristApp {

	public static void main(String[] args) throws IOException, RepositoryException {
		
		RepositoryFactory.selectRepository("txt");
		//RepositoryFactory.selectRepository("mySQL");
		//RepositoryFactory.selectRepository("mongoDB");
		
		Scanner sc = new Scanner(System.in);
		boolean ask = true;
		
		
		Florist florist = RepositoryFactory.getRepository().findFlorist();
		if (florist == null) {
			createFlorist(sc);
		} else {
			Florist.setInstance(florist);
		}

		System.out.print("Bienvenido/a. ");
		while (ask) {
			
			System.out.println("¿Qué desea hacer?\n"
					+ "1. Borrar esta floristería y crear otra\n"
					+ "2. Añadir un producto\n"
					+ "3. Retirar un producto\n"
					+ "4. Ver productos\n"
					+ "5. Ver número de productos disponibles por categoría\n"
					+ "6. Ver el valor de la floristería\n"
					+ "7. Comprar\n"
					+ "8. Ver historial de compras\n"
					+ "9. Ver ganancias\n"
					+ "10. Salir");
			
			int num = selectNumericOption(sc, 1, 10);

			switch(num) {
				case 1:
					rewriteFlorist(sc);
					break;
				case 2:
					selectProduct("añadir");
					setProduct(sc, (selectNumericOption(sc, 1, 3)));
					break;
				case 3:
					if (new StockController().checkStockIsEmpty()) {
						System.out.println("Lo sentimos, "
							+ "no hay productos disponibles.");
						return;
					}
					selectProduct("retirar");
					selectProductToRemove(sc);
					break;
				case 4:
					System.out.println(new StockController().showStock());
					break;
				case 5:
					System.out.println(new StockController()
						.showStockQuantity());
					break;
				case 6:
					System.out.println("El valor total de la floristeria es de "
						+ new StockController().showStockValue() + " euros.");
					break;
				case 7:
					createTicket(sc);
					break;
				case 8:
					System.out.println(new TicketController()
						.showTickets());
					break;
				case 9:
					System.out.println("Valor total de las ventas: " 
						+ new TicketController().getIncome() + "€");
					break;
				case 10:
					ask = false;
					break;		
				}
			if (num != 10) {
				ask = stay(sc, "realizando operaciones");
			}
		}
		System.out.println("¡Hasta pronto!");
		sc.close();
	}

	private static void createFlorist(Scanner sc) throws IOException {
		System.out.println("Escriba el nombre de la floristería "
				+ "que desea crear:");
		String name = sc.nextLine();
		boolean newFlorist;
		try {
			newFlorist = new FloristController().createFlorist(name);
			if (newFlorist) {
			System.out.println("La floristería " + name + 
				" se ha creado correctamente.");
			} else {
				System.out.println("No se ha podido crear la floristería " 
				+ name + ".\nYa ha creado una floristería"); 
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void rewriteFlorist(Scanner sc) {
		System.out.println("Escriba el nombre de la nueva floristería");
		String name = sc.nextLine();
		boolean newFlorist;
		try {
			newFlorist = new FloristController().createFlorist(name);
			System.out.println("La floristería " + name + 
				" se ha creado correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void selectProduct(String operation) {
		System.out.println("Seleccione el tipo de producto que desea "
				+ operation + ":\n"
				+ "1. Árbol\n"
				+ "2. Flor\n"
				+ "3. Adorno");
	}
	
	private static void setProduct(Scanner sc, int option) 
		throws FileNotFoundException, IOException {
		String name = SetProductView.getProductName(sc);
		double price = SetProductView.getProductPrice(sc);
		Double height = null;
		String color = null, material = null;
		switch (option) {
			case 1:
				height = SetProductView.getProductHeight(sc);
				break;
			case 2:
				color = SetProductView.getProductColor(sc);
				break;
			case 3:
				material = SetProductView.getProductMaterial(sc);
				break;
		}
		int quantity = SetProductView.getQuantityToAdd(sc, 1, 500);
		try {
			int index = new ProductController()
				.getIndex(name, price, height, color, material, quantity);
			if (index == -1) {
				new ProductController()
					.addProduct(name, price, height, color, material, quantity);
			} else if (index >= 0) {
				new ProductController()
					.updateQuantity(option, index, quantity);
			}
			System.out.println("Producto añadido correctamente.");
		} catch (MaterialTypeException e) {
			System.out.println("No se ha podido añadir el producto. "
				+ e.getMessage());
			setProduct(sc, option);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void createTicket(Scanner sc) {
		boolean buyFlag = true;
		if (new StockController().checkStockIsEmpty()) {
			System.out.println("Lo sentimos, no hay productos disponibles.");
			return;
		}
		new TicketController().addNewTicket();
		while (buyFlag) {
			selectProduct("comprar");
			int num = selectNumericOption(sc, 1, 3);
			ProductListControllerResponse response = 
				new ProductController().getProductList(num);
			if (response.getArrayListSize() == 0) {
				System.out.println("No hay existencias disponibles.");
				continue;
			}
			System.out.println("Estos son los artículos disponibles:");
			System.out.println(response.getListToPrint());
			System.out.println("Introduzca el número del artículo a comprar:");
			int index = (selectNumericOption(
				sc, 
				1, 
				response.getArrayListSize()
			)) - 1;
			try {
				int available = 
					new ProductController().getProductQuantity(num, index);
				System.out.println("Hay " + available + 
					" productos disponibles.\n¿Cuántos desea comprar?");
				int quantityToBuy = selectNumericOption(sc, 1, available);
				if (available == quantityToBuy) {
					new TicketController()
						.addProductToTicket(num, index, quantityToBuy);
					new ProductController().removeProduct(num, index);
				} else if (available > quantityToBuy) {
					new TicketController()
						.addProductToTicket(num, index, quantityToBuy);
					new ProductController()
						.updateQuantity(num, index, quantityToBuy*(-1));
				} else {
					System.out.println("No se puede comprar "
						+ "la cantidad indicada");
					return;
				}
				System.out.println("El producto se ha añadido "
					+ "a la lista de compra correctamente.");
			}  catch (Exception e) {
				System.out.println("No se ha podido añadir el producto "
					+ "a la lista de compra." + e.getMessage());
			}
			buyFlag = stay(sc, "comprando");
			if (buyFlag && new StockController().checkStockIsEmpty()) {
				System.out.println("Lo sentimos, "
					+ "no quedan productos disponibles.");
				buyFlag = false;
			}
		}
		System.out.println("Compra realizada correctamente. "
			+ "Aquí tiene su ticket:");
		try {
			System.out.println(new TicketController().generateTicket());
		} catch (RepositoryException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void selectProductToRemove(Scanner sc) {
		int num = selectNumericOption(sc, 1, 3);
		ProductListControllerResponse response = 
				new ProductController().getProductList(num);
		if (response.getArrayListSize() == 0) {
			System.out.println("No hay existencias disponibles.");
			return;
		}
		System.out.println("Estos son los artículos disponibles:");
		System.out.println(response.getListToPrint());
		System.out.println("Introduzca el número del artículo a retirar:");
		int index = (selectNumericOption(
			sc, 
			1, 
			response.getArrayListSize()
		)) - 1;
		try {
			int available =
				new ProductController().getProductQuantity(num, index);
			System.out.println("Hay " + available + " productos disponibles.\n"
				+ "¿Cuántos desea eliminar?");
			int quantityToRemove = selectNumericOption(sc, 1, available);
			if (available == quantityToRemove) {
				new ProductController().removeProduct(num, index);
			} else if (available > quantityToRemove) {
				new ProductController()
					.updateQuantity(num, index, quantityToRemove*(-1));
			} else {
				System.out.println("No se puede eliminar la cantidad indicada");
				return;
			}
			System.out.println("El producto se ha retirado correctamente.");
		} catch (Exception e) {
			System.out.println("No se ha podido retirar el producto. "
				+ e.getMessage());
		}
	}
	
	public static int selectNumericOption(Scanner sc, int min, int max) {
		try {
			int num = Integer.parseInt(sc.nextLine());
			if (min > num || num > max) {
				throw new InputMismatchException();
			}
			return num;
		} catch (NumberFormatException e) {
			System.out.println("No ha introducido un número."
					+ " Introduzca un número válido:");
		} catch (InputMismatchException e) {
			System.out.println("No ha introducido un número válido."
					+ " Introduzca un número entre " + min + " y " + max + ":");
		}
		return selectNumericOption(sc, min, max);
	}
	
	private static boolean stay(Scanner sc, String s) {
		System.out.println("¿Quiere seguir " + s + "? (sí/no)");
		String option = sc.nextLine();
		if (option.equalsIgnoreCase("sí") || option.equalsIgnoreCase("si")) {
			return true;
		} else if (option.equalsIgnoreCase("no")) {
			return false;
		} else {
			System.out.println("Debe introducir una de las opciones (sí/no).");
			return stay(sc, s);
		}
	}
}