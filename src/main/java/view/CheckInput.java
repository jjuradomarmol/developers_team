package view;

import java.util.InputMismatchException;
import java.util.Scanner;
import model.MaterialTypeException;

public class CheckInput {
	
	protected static String getFloristName(Scanner sc) {
		System.out.println("Escriba el nombre de la nueva florister�a:");
		try {
			String name = sc.nextLine();
			if (stringIsValid(name)) {
				return name;
			}
			throw new InputMismatchException("El nombre no puede "
				+ "incluir n�meros o estar vac�o.");
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
				+ "incluir n�meros o estar vac�o.");
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
			System.out.println("No ha introducido un n�mero."
					+ " Introduzca un n�mero v�lido:");
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
			System.out.println("No ha introducido un n�mero."
					+ " Introduzca un n�mero v�lido:");
		} catch (InputMismatchException e) {
			System.out.println("Un �rbol no puede ser tan bajo/alto");
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
				+ "no es v�lido. No puede incluir n�meros o estar vac�o.");
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
			return getProductColor(sc);
		}
	}
	
	protected static String getProductMaterial(Scanner sc) {
		System.out.println("Introduzca el material (madera/pl�stico):");
		try {
			String material = sc.nextLine().toLowerCase();
			if (!stringIsValid(material)) {
				throw new InputMismatchException("El material introducido "
				+ "no es v�lido");
			}
			if ((!material.equalsIgnoreCase("madera"))
				&&(!material.equalsIgnoreCase("pl�stico"))) {
				throw new MaterialTypeException(
					"El material " + material + " no es v�lido.\n"
					+ "Introduzca un material v�lido (madera/pl�stico)."
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
		System.out.println("�Cu�ntos productos de este tipo desea a�adir?");
		try {
			int quantity = Integer.parseInt(sc.nextLine());
			if (min > quantity) {
				throw new NumberException("Introduzca al menos " + min +":");
			} else if (quantity >= max) {
				throw new NumberException("Ha intentado a�adir demasiados"
						+ " productos.\nNo se pueden a�adir m�s de " + max 
						+ " productos iguales de una vez.");
			}
			return quantity;
		} catch (NumberFormatException e) {
			System.out.println("No ha introducido un n�mero."
					+ " Introduzca un n�mero v�lido:");
		} catch (NumberException e) {
			System.out.println(e.getMessage());
		}
		return getQuantityToAdd(sc, min, max);
	}
	
	public static boolean stringIsValid(String s) {
	    char[] string = s.toCharArray();
	    for(char c : string) {
	        if (Character.isDigit(c))
	            return false;
	    }
	    if (s.trim().equals("")) {
	    	return false;
	    }
	    return true;
	}
}