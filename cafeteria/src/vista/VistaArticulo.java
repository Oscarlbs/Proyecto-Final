package vista;

import java.sql.SQLException;
import java.util.Scanner;

import controlador.ArticuloController;
import modelo.Bebidas;
import modelo.Pizzas;
import modelo.Postres;

public class VistaArticulo {

	private static ArticuloController controlador;
	private static Scanner sc = new Scanner(System.in);
	
	public static void menu () {
		
		controlador = new ArticuloController();
		
		int option = -1;
		do {
			try {
				System.out.println("BIENVENIDO AL MENÚ DE LA PIZZERÍA LAS DIVINAS: ");
				System.out.println("0. Salir del programa.");
				System.out.println("1. Añadir nuevos artículos.");
				System.out.println("2. Modificar artículos existentes.");
				System.out.println("3. Listar los artículos");
				System.out.println("4. Ver el resumen de los tickets usando la vista de la BD.");
				System.out.println("5. Ver el resumen de los artículos más vendidos.");
				System.out.println("6. Borrar articulo.");
				option = Integer.parseInt(sc.nextLine());
				switch (option) {
				case 0:
					System.out.println("Fin del programa.");
					break;
				case 1:
					crearArtic();
					break;
				case 2:
					controlador.listarArts();
					modifArtic();
					break;
				case 3:
					controlador.listarArts();
					break;
				case 4:
					try {
						controlador.verTickets();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				case 5: 
					System.out.println("==== Artículos más vendidos ====");
					try {
						controlador.topCincoArts();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println("================================");
					break;
				case 6:
					int cod;
					controlador.listarArts();
					do {
					System.out.println("Introduce el codigo del artículo que quieres borrar");
					cod = Integer.parseInt(sc.nextLine());
					if(controlador.comprobarId(cod)==false) {
						System.out.println("Artículo mal seleccionado, inténtelo de nuevo");
					}
					}while (controlador.comprobarId(cod)==false);
					controlador.borrarArt(cod);
					System.out.println("Artículo borrado exitósamente, volviendo al menú");
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
	private static void modifArtic() {
		
		int id,pos;
		do {
		System.out.println("Introduce el Id del artículo que desea modificar");
		id = Integer.parseInt(sc.nextLine());
		if(controlador.comprobarId(id)==false) System.out.println("Id fuera de rango, inténtelo de nuevo");
		}while(controlador.comprobarId(id)==false);
		
		controlador.mostrarArt(id);
		
		int num = controlador.devolverObjArt(controlador.rellenarArt(id));
		pos = controlador.devolverPos(id);
		
		switch (num) {
		case 1: 
			Pizzas aux = new Pizzas();
			String num1;
			String espec, tamanho, ingreds="";
			int stock, numIngreds=0;
			float precio;
			boolean rep1 = false;
			System.out.println("Empezamos la modificación de la Pizza");
			espec = modifEspecialidad();
			tamanho = modifTamanho();
			stock = modifStock();
			precio = modifPrecio();
			
			switch(espec) {
			case "cuatro_quesos": 
					ingreds = "mozzarella, queso azul, queso parmesano, queso ricotta";
					numIngreds = 4;
				break;
			case "mexicana":
					ingreds = "cebolla, pimenton dulce, mozzarella, aguacate, limon, chili, tabasco";
					numIngreds = 7;
				break;
			case "espanhola":
					ingreds = "salami, chorizo, bacon, tomate, mozzarella, pimienta, orégano";
					numIngreds = 7;
				break;
			case "carbonara":
					ingreds = "mozzarella, nata, champinhones, pimienta negra, bacon";
					numIngreds = 5;
				break;
			}
			aux.setEspecialidad(espec);
			aux.setPrecio(precio);
			aux.setStock(stock);
			aux.setTamanho(tamanho);
			aux.setCod_art(id);
			aux.setIngredientes(ingreds);
			aux.setNum_ingredientes(numIngreds);
			
			controlador.intercambioPizzas(aux, pos);
		break;
		
		
		case 2: System.out.println("Empezamos la modificación de la Bebida");
		
		Bebidas b = new Bebidas();
		int stock_b = 0;
		float precio_b = 0;
		String bebida,descr;
		boolean rep;
		
		stock = modifStock();
		precio = modifPrecio();
		
		System.out.println("Elige la bebida");
		
		do {
			System.out.println("Introduce el número de la nueva bebida");
			System.out.println("1. Agua");
			System.out.println("2. Coca cola");
			System.out.println("3. Sprint");
			System.out.println("4. Fanta");
			System.out.println("5. Cerveza");
			System.out.println("6. Nestea");
			System.out.println("7. Aquarius");
			System.out.println("8. Gaseosa");
			System.out.println("9. Limonada");
			System.out.println("10. Batido");
			bebida = sc.nextLine();

			switch(bebida) {
			
			case "1": bebida = "agua";
			rep = false;
				break;
			case "2": bebida = "coca_cola";
			rep = false;
				break;
			case "3": bebida = "sprint";
			rep = false;
				break;
			case "4": bebida = "fanta";
			rep = false;
				break;
			case "5": bebida = "cerveza";
			rep = false;
				break;
			case "6": bebida = "nestea";
			rep = false;
				break;
			case "7": bebida = "aquarius";
			rep = false;
				break;
			case "8": bebida = "gaseosa";
			rep = false;
				break;
			case "9": bebida = "limonada";
			rep = false;
				break;
			case "10":bebida = "batido";
			rep = false;
				break;
			default: System.out.println("Opción mal introducida, inténtelo de nuevo");
			rep = true;
				break;
			}
			}while(rep == true);
		
		System.out.println("Escribe la descripción de tu bebida");
		descr = sc.nextLine();
		
		b.setCod_art(id);
		b.setDescr(descr);
		b.setPrecio(precio_b);
		b.setStock(stock_b);
		b.setTip_bebida(bebida);
		b.setCod_art(id);
		
		controlador.intercambioBebida(b, pos);
		
		break;
		
		
		case 3: System.out.println("Elige lo que desea modificar de su Postre");
		
		Postres p2 = new Postres();
		int stock_p = 0;
		float precio_p = 0;
		String postre,descr_p,num_postre;
		boolean rep_p;
		
		stock_p = modifStock();
		precio_p = modifPrecio();
		
		do {
			System.out.println("Introduce el número del nuevo postre");
			System.out.println("1. Helado");
			System.out.println("2. Tarta");
			System.out.println("3. Café");
			System.out.println("4. Brownie");
			System.out.println("5. Gofre");
			System.out.println("6. Montadito");
			System.out.println("7. Melocoton");
			System.out.println("8. Yogur");
			System.out.println("9. Manzana");
			System.out.println("10. Flan");
			num_postre = sc.nextLine();
			
			switch(num_postre) {
			
			case "1": postre = "helado";
			rep_p = false;
				break;
			case "2": postre = "tarta";
			rep_p = false;
				break;
			case "3": postre = "cafe";
			rep_p = false;
				break;
			case "4": postre = "brownie";
			rep_p = false;
				break;
			case "5": postre = "gofre";
			rep_p = false;
				break;
			case "6": postre = "montadito";
			rep_p = false;
				break;
			case "7": postre = "melocoton";
			rep_p = false;
				break;
			case "8": postre = "yogur";
			rep_p = false;
				break;
			case "9": postre = "manzana";
			rep_p = false;
				break;
			case "10":postre = "flan";
			rep_p = false;
				break;
			default: System.out.println("Opción mal introducida, inténtelo de nuevo");
			rep_p = true;
				break;
			}
			}while(rep_p == true);
		
		System.out.println("Escribe la descripción de tu bebida");
		descr_p = sc.nextLine();
		
		p2.setDescr(descr_p);
		p2.setPrecio(precio_p);
		p2.setStock(stock_p);
		p2.setTip_postre(num_postre);
		
		controlador.intercambioPostre(p2, pos);
		break;
		}
	}
	private static float modifPrecio() {
		
		float precio;
		do {
		System.out.println("Introduce el precio por unidad de tu Articulo");
		precio = Integer.parseInt(sc.nextLine());
		if(igual_cero(precio)==false || negativo(precio)==false) System.out.println("Número fuera del rango");
		}while(igual_cero(precio)==false || negativo(precio)==false);
		return precio;	
	}
	private static int modifStock() {
	
		int stock;
		do {
		System.out.println("Introduce el stock de tu Articulo");
		stock = Integer.parseInt(sc.nextLine());
		if(igual_cero(stock)==false || negativo(stock)==false) System.out.println("Número fuera del rango");
		}while(igual_cero(stock)==false || negativo(stock)==false);
		return stock;
		
	}
	private static String modifTamanho() {
		
		String num,tamanho=null;
		boolean rep = false;
		System.out.println("Introduce el tamaño de tu pizza");
		System.out.println("1. Pequeña");
		System.out.println("2. Mediana");
		System.out.println("3. Familiar");
		num = sc.nextLine();
		switch(num) {
		case "1": tamanho = "pequenia";
			rep = false;
			break;
		case "2": tamanho = "mediana";
			rep = false;
			break;
		case "3": tamanho = "familiar";
			rep = false;
			break;
		default: System.out.println("Opción mal introducida, intételo de nuevo");
		rep = true;
		break;
		}		
		return tamanho;
	}
	private static String modifEspecialidad() {
		
		String num,espec=null;
		boolean rep = false;
		do {
		System.out.println("Elige la especialidad");
		System.out.println("1. Cuatro Quesos");
		System.out.println("2. Mexicana");
		System.out.println("3. Española");
		System.out.println("4. Carbonara");
		num = sc.nextLine();
		switch(num) {
		case "1": espec = "cuatro_quesos";;
		rep = false;
			break;
		case "2": espec = "mexicana";
			break;
		case "3": espec = "espanhola";
			break;
		case "4": espec = "carbonara";
			break;
		default: System.out.println("Opción mal introducida, inténtelo de nuevo");
		rep = true;
		break;
		}
		}while(rep == true);
		return espec;
		
	}
	private static void crearArtic() {
		String art;
		boolean rep = true;
		do {
		System.out.println("Que artículo quiere insertar?");
		System.out.println("1. Pizza");
		System.out.println("2. Bebida");
		System.out.println("3. Postre");
		System.out.println("4. Volver al menú");
		
		art = sc.nextLine();
		
		switch(art) {
		case "1": crearPizza();
			break;
		case "2": crearBebida();
			break;
		case "3": crearPostre();
			break;
		case "4": System.out.println("Volviendo al menú");
		System.out.println("==================");
		rep=false;
			break;
		default: System.out.println("Opción mal introducida, inténtelo de nuevo");
			break;
		}
		}while(rep==true);
		
	}
	private static void crearPostre() {
		
		String name,postre = null,descr;
		int stock,id;
		float price;
		boolean rep = false;
		
		do {
		System.out.println("Introduce el número del nuevo postre");
		System.out.println("1. Helado");
		System.out.println("2. Tarta");
		System.out.println("3. Café");
		System.out.println("4. Brownie");
		System.out.println("5. Gofre");
		System.out.println("6. Montadito");
		System.out.println("7. Melocoton");
		System.out.println("8. Yogur");
		System.out.println("9. Manzana");
		System.out.println("10. Flan");
		name = sc.nextLine();
		
		switch(name) {
		
		case "1": postre = "helado";
		rep = false;
			break;
		case "2": postre = "tarta";
		rep = false;
			break;
		case "3": postre = "cafe";
		rep = false;
			break;
		case "4": postre = "brownie";
		rep = false;
			break;
		case "5": postre = "gofre";
		rep = false;
			break;
		case "6": postre = "montadito";
		rep = false;
			break;
		case "7": postre = "melocoton";
		rep = false;
			break;
		case "8": postre = "yogur";
		rep = false;
			break;
		case "9": postre = "manzana";
		rep = false;
			break;
		case "10":postre = "flan";
		rep = false;
			break;
		default: System.out.println("Opción mal introducida, inténtelo de nuevo");
		rep = true;
			break;
		}
		}while(rep == true);
		
		do {
		System.out.println("Indique cuanto stock existe del postre");
		stock = Integer.parseInt(sc.nextLine());
		
		if(igual_cero(stock)==false || negativo(stock)==false){
			System.out.println("Stock mal introducido, inténtelo de nuevo");
		}
		}while(igual_cero(stock)==false || negativo(stock)==false);
		
		do {
		System.out.println("Indique cuantos euros vale cada unidad de su postre");
		price = Float.parseFloat(sc.nextLine());
		if (igual_cero(price)==false || negativo(price)==false) {
			System.out.println("Precio mal introducido, inténtelo de nuevo");
		}
		}while(igual_cero(price)==false || negativo(price)==false);
		
		System.out.println("Introduce la descripción de su postre");
		descr = sc.nextLine();
		
		
		controlador.crearPostreController(stock,price,postre,descr);
		
		System.out.println("Artículo añadido correctamente");
	}
	private static void crearBebida() {
	
		String name,bebida = null,descr;
		int stock,id;
		float price;
		boolean rep = false;
		
		do {
		System.out.println("Introduce el número de la nueva bebida");
		System.out.println("1. Agua");
		System.out.println("2. Coca cola");
		System.out.println("3. Sprint");
		System.out.println("4. Fanta");
		System.out.println("5. Cerveza");
		System.out.println("6. Nestea");
		System.out.println("7. Aquarius");
		System.out.println("8. Gaseosa");
		System.out.println("9. Limonada");
		System.out.println("10. Batido");
		bebida = sc.nextLine();

		switch(bebida) {
		
		case "1": bebida = "agua";
		rep = false;
			break;
		case "2": bebida = "coca_cola";
		rep = false;
			break;
		case "3": bebida = "sprint";
		rep = false;
			break;
		case "4": bebida = "fanta";
		rep = false;
			break;
		case "5": bebida = "cerveza";
		rep = false;
			break;
		case "6": bebida = "nestea";
		rep = false;
			break;
		case "7": bebida = "aquarius";
		rep = false;
			break;
		case "8": bebida = "gaseosa";
		rep = false;
			break;
		case "9": bebida = "limonada";
		rep = false;
			break;
		case "10":bebida = "batido";
		rep = false;
			break;
		default: System.out.println("Opción mal introducida, inténtelo de nuevo");
		rep = true;
			break;
		}
		}while(rep == true);
		
		do {
		System.out.println("Indique cuanto stock existe de la bebida");
		stock = Integer.parseInt(sc.nextLine());
		
		if(igual_cero(stock)==false || negativo(stock)==false){
			System.out.println("Stock mal introducido, inténtelo de nuevo");
		}
		}while(igual_cero(stock)==false || negativo(stock)==false);
		
		do {
		System.out.println("Indique cuantos euros vale cada unidad de su bebida");
		price = Float.parseFloat(sc.nextLine());
		if (igual_cero(price)==false || negativo(price)==false) {
			System.out.println("Precio mal introducido, inténtelo de nuevo");
		}
		}while(igual_cero(price)==false || negativo(price)==false);
		
		System.out.println("Introduce la descripción de su bebida");
		descr = sc.nextLine();
		
		controlador.crearBebidaController(stock,price,bebida,descr);
		
		System.out.println("Artículo añadido correctamente");
	}
	private static void crearPizza() {
	
		String especialidad="",tamanho="",ingreds="",num=null;
		int stock=0,id,num_ingredientes=0;
		float price=0;
		boolean rep = false;
		do {
		System.out.println("Introduce el tipo de pizza que quieres");
		System.out.println("1. Cuatro Quesos");
		System.out.println("2. Carbonara");
		System.out.println("3. Española");
		System.out.println("4. Mexicana");
		num = sc.nextLine();
		
		switch(num) {
		
		case "1": especialidad = "cuatro_quesos";
		ingreds = "mozzarella, queso azul, queso parmesano, queso ricotta";
		num_ingredientes = 4;
		rep = false;
			break;
		case "2": especialidad = "carbonara";
		ingreds = "mozzarella, nata, champinhones, pimienta negra, bacon";
		num_ingredientes = 5;
		rep = false;
			break;
		case "3": especialidad = "espanhola";
		ingreds = "salami, chorizo, bacon, tomate, mozzarella, pimienta, orégano";
		num_ingredientes = 7;
		rep = false;
			break;
		case "4": especialidad = "mexicana";
		ingreds = "cebolla, pimenton dulce, mozzarella, aguacate, limon, chili, tabasco";
		num_ingredientes = 7;
		rep = false;
			break;
		default: System.out.println("Opción mal introducida, inténtelo de nuevo");
		rep = true;
			break;
		}
		}while(rep==true);
		
		String num2;
		
		System.out.println("Introduce el tamaño de tu pizza");
		System.out.println("1. Pequeña");
		System.out.println("2. Mediana");
		System.out.println("3. Familiar");
		num2 = sc.nextLine();
		
		switch(num2) {
		
		case "1": tamanho = "pequenia";
		rep = false;
			break;
		case "2": tamanho = "mediana";
		rep = false;
			break;
		case "3": tamanho = "familiar";
		rep = false;
			break;
		default: System.out.println("Opción mal introducida, inténtelo de nuevo");
		rep = true;
			break;
		}
		do {
		System.out.println("Indique cuanto stock existe de la pizza");
		stock = Integer.parseInt(sc.nextLine());
		
		if(igual_cero(stock)==false || negativo(stock)==false){
			System.out.println("Stock mal introducido, inténtelo de nuevo");
		}
		}while(igual_cero(stock)==false || negativo(stock)==false);
		
		do {
		System.out.println("Indique cuantos euros vale cada unidad de su pizza");
		price = Float.parseFloat(sc.nextLine());
		if (igual_cero(price)==false || negativo(price)==false) {
			System.out.println("Precio mal introducido, inténtelo de nuevo");
		}
		}while(igual_cero(price)==false || negativo(price)==false);
				
		controlador.crearPizzaController(stock, price, tamanho, ingreds, num_ingredientes, especialidad);
	}
	public static boolean igual_cero (int valor) {
		if(valor==0) return false;
		else return true;
	}	
	public static boolean negativo (int valor) {
		if(valor<0) return false;
		else return true;
	}
	public static boolean igual_cero (float valor) {
		if(valor==0) return false;
		else return true;
	}
	public static boolean negativo (float valor) {
		if(valor<0) return false;
		else return true;
	}	
}