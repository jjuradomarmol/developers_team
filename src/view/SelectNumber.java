package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import view.NumberException;

public class SelectNumber {
	
	public static int selectNumericOption(Scanner sc, int min, int max) {
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
	
	public static int selectQuantityToAdd(Scanner sc, int min, int max) {
		System.out.println("¿Cuántos productos de este tipo desea añadir?");
		try {
			int num = Integer.parseInt(sc.nextLine());
			if (min >= num) {
				throw new NumberException("Introduzca al menos " + min +":");
			} else if (num >= max) {
				throw new NumberException("Ha intentado añadir demasiados"
						+ " productos.\nNo se pueden añadir más de " + max 
						+ "productos iguales de una vez.");
			}
			return num;
		} catch (NumberFormatException e) {
			System.out.println("No ha introducido un número."
					+ " Introduzca un número válido:");
			return selectQuantityToAdd(sc, min, max);
		} catch (NumberException e) {
			e.getMessage();
			return selectQuantityToAdd(sc, min, max);
		}
	}

}
