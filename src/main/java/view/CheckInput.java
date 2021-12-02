package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import model.MaterialTypeException;

public class CheckInput {
	
	protected static String getFloristName(Scanner sc) {
		System.out.println("Escriba el nombre de la nueva floristería:");
		try {
			String name = sc.nextLine();
			if (stringIsValid(name)) {
				return name;
			}
			throw new InputMismatchException("El nombre no puede "
				+ "incluir números o estar vacío.");
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
			return getFloristName(sc);
		}
	}
	
	protected static String getProductName(Scanner sc) {
		System.out.println("Introduzca el nombre:");
		try {
			String name = sc.nextLine();
			if (stringIsValid(name)) {
				return name;
			}
			throw new InputMismatchException("El nombre no puede "
				+ "incluir números o estar vacío.");
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
			return getProductName(sc);
		}
	}
	
	protected static double getProductPrice(Scanner sc) {
		try {
			System.out.println("Introduzca el precio:");		
			double price = Double.parseDouble(sc.nextLine());
			if (0 >= price || price > 1000) {
				throw new InputMismatchException();
			}
			return price;
		} catch (NumberFormatException e) {
			System.out.println("No ha introducido un número."
					+ " Introduzca un número válido:");
		} catch (InputMismatchException e) {
			System.out.println("Ha introducido un precio demasiado bajo"
				+ " o demasiado alto.");
		}
		return getProductPrice(sc);
	}
	
	protected static double getProductHeight(Scanner sc) {
		try {
			System.out.println("Introduzca la altura:");		
			double height = Double.parseDouble(sc.nextLine());
			if (0 >= height || height > 120) {
				throw new InputMismatchException();
			}
			return height;
		} catch (NumberFormatException e) {
			System.out.println("No ha introducido un número."
					+ " Introduzca un número válido:");
		} catch (InputMismatchException e) {
			System.out.println("Un árbol no puede ser tan bajo/alto");
		}
		return getProductHeight(sc);
	}
	
	protected static String getProductColor(Scanner sc) {
		System.out.println("Introduzca el color:");
		try {
			String color = sc.nextLine().toLowerCase();
			if (stringIsValid(color)) {
				return color;
			}
			throw new InputMismatchException("El color introducido "
				+ "no es válido. No puede incluir números o estar vacío.");
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
			return getProductColor(sc);
		}
	}
	
	protected static String getProductMaterial(Scanner sc) {
		System.out.println("Introduzca el material (madera/plástico):");
		try {
			String material = sc.nextLine().toLowerCase();
			if (!stringIsValid(material)) {
				throw new InputMismatchException("El material introducido "
				+ "no es válido");
			}
			if ((!material.equalsIgnoreCase("madera"))
				&&(!material.equalsIgnoreCase("plástico"))) {
				throw new MaterialTypeException(
					"El material " + material + " no es válido.\n"
					+ "Introduzca un material válido (madera/plástico)."
				);
			}
			return material;
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());	
		} catch (MaterialTypeException e) {
			System.out.println(e.getMessage());
		}
		return getProductMaterial(sc);
	}
	
	public static int getQuantityToAdd(Scanner sc, int min, int max) {
		System.out.println("¿Cuántos productos de este tipo desea añadir?");
		try {
			int quantity = Integer.parseInt(sc.nextLine());
			if (min > quantity) {
				throw new NumberException("Introduzca al menos " + min +":");
			} else if (quantity >= max) {
				throw new NumberException("Ha intentado añadir demasiados"
					+ " productos.\nNo se pueden añadir más de " + max 
					+ " productos iguales de una vez.");
			}
			return quantity;
		} catch (NumberFormatException e) {
			System.out.println("No ha introducido un número."
					+ " Introduzca un número válido:");
		} catch (NumberException e) {
			System.out.println(e.getMessage());
		}
		return getQuantityToAdd(sc, min, max);
	}
	
	public static boolean stringIsValid(String s) {
	    char[] string = s.toCharArray();
	    if (s.trim().equals("")) {
	    	return false;
	    }
	    for(char c : string) {
	        if (Character.isDigit(c))
	            return false;
	    }
	    return true;
	}
}
