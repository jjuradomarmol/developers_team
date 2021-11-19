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
import model.ProductTypeException;
import model.Stock;
import static view.SetProductView.*;

public class FloristApp {

	public static void main(String[] args) throws IOException {
		
		try {
			Stock.getInstance().addOrnament(new Ornament ("Yoyo", 12, "madera", 3));
			Stock.getInstance().addOrnament(new Ornament ("Jarrón", 12, "plástico", 4));
		} catch (MaterialTypeException e) {
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		boolean ask = true;
		
		System.out.print("Bienvenido/a. ");
		while (ask) {
			System.out.println("¿Qué desea hacer?\n"
					+ "1. Crear una floristeria\n"
					+ "2. Añadir un producto\n"
					+ "3. Retirar un producto\n"
					+ "4. Ver productos\n"
					+ "5. Ver número de productos disponibles por categoria\n"
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
					selectProduct("añadir");
					setProduct(sc, (selectNumericOption(sc, 1, 3)));
					break;
				case 3:
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
					createTicket();
					break;
				case 8:
					System.out.println(new TicketController()
						.showTickets());
					break;
				case 9:
					double income = new TicketController().getIncome();
					System.out.println(
						"Valor total de las ventas: " + income + "€"
					);
					break;
				case 10:
					ask = false;
					break;		
				}
			if (num != 10) {
				ask = stay(sc);
			}
		}
		
		sc.close();
	}

	private static void createTicket() throws IOException {
		ProductController productsController = new ProductController();
		
		ArrayList<Product> productos = new ArrayList<>();
		boolean buyFlag = true;
		int categoria;
		String nombre;
		Product product;
		String seguir;
		
		Scanner sc = new Scanner(System.in);
		
		while (buyFlag) {
			System.out.println("Indique 1 para comprar un Ã¡rbol, "
					+ "2 para comprar una flor o 3 para comprar una decoraciÃ³n:");
			categoria = Integer.parseInt(sc.nextLine());
			
			System.out.println("Indique el nombre del producto:");
			nombre = sc.nextLine();
			
			product = productsController.getProduct(categoria, nombre);
			
			if (product != null) {
				productos.add(product);
			}
			
			System.out.println("Â¿Quiere seguir comprando? (sÃ­/no)");
			seguir = sc.nextLine();
			
			if (!seguir.equalsIgnoreCase("sÃ­") || !seguir.equalsIgnoreCase("sÃ­")) {
				buyFlag = false;
			}
		};
		
		new TicketController().addTicket(productos);
		sc.close();
	}
	
	private static void createFlorist(Scanner sc) throws IOException {
		System.out.println("Escriba el nombre de la floristeria "
				+ "que desea crear:");
		String name = sc.nextLine();
		FloristController.createFlorist(name);
		System.out.println("La floristeria " + name + 
				" se ha creado correctamente.");
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
		int quantity = getQuantityToAdd(sc, 1, 500);
		try {
			String addedProduct = new ProductController()
				.createProduct(name, price, height, color, material, quantity);
			System.out.println("Producto añadido correctamente.\n"
				+ addedProduct);
		} catch (MaterialTypeException e) {
			System.out.println("No se ha podido añadir el producto. "
				+ e.getMessage());
			setProduct(sc, option);
		} catch (ProductTypeException e ) {
			System.out.println("No se ha podido añadir el producto. "
				+ e.getMessage());
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
		int available;
		try {
			available = new StockController().getProductQuantity(num, index);
			System.out.println("Hay " + available + " productos disponibles.\n"
				+ "¿Cuántos desea eliminar?");
			int quantityToRemove = selectNumericOption(sc, 1, available);
			if (available == quantityToRemove) {
				new ProductController().removeProduct(num, index);
			} else if (available > quantityToRemove) {
				int newQuantity = available - quantityToRemove;
				new ProductController()
					.updateQuantity(num, index, newQuantity);
			} else {
				System.out.println("No se puede eliminar la cantidad indicada");
				return;
			}
			System.out.println("El producto se ha retirado correctamente.");
		} catch (ProductTypeException e) {
			System.out.println("No se ha podido retirar el producto. "
					+ e.getMessage());
		} catch (Exception e) {
			System.out.println("No se ha podido retirar el producto.");
			e.getStackTrace();
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
			return selectNumericOption(sc, min, max);
		} catch (InputMismatchException e) {
			System.out.println("No ha introducido un número válido."
					+ " Introduzca un número entre " + min + " y " + max + ":");
			return selectNumericOption(sc, min, max);
		}
	}
	
	private static boolean stay(Scanner sc) {
		System.out.println("¿Quiere seguir realizando operaciones? (sí/no)");
		String option = sc.nextLine();
		if (option.equalsIgnoreCase("sí") || option.equalsIgnoreCase("si")) {
			return true;
		} else if (option.equalsIgnoreCase("no")) {
			return false;
		} else {
			System.out.println("Debe introducir una de las opciones (sí/no).");
			return stay(sc);
		}
	}
}