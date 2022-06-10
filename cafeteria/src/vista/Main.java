package vista;

import java.util.Scanner;

public class Main {

	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {

		int option = -1;
		do {
			try {
				System.out.println("Seleccione una opción del menú: ");
				System.out.println("1. Menú Pizzería.");
				System.out.println("0. Volver.");
				option = Integer.parseInt(sc.nextLine());
				switch (option) {
				case 1:
					VistaArticulo.menu();
					break;
				case 0:
					System.out.println("Fin del programa.");
					break;
				default:
					System.out.println("Por favor, seleccione una opción disponible.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Debe introducir un número.");
			}
		} while (option != 0);
	}
}