package controlador;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import bbdd.BDConnection;
import modelo.Articulo;
import modelo.Bebidas;
import modelo.Pizzas;
import modelo.Postres;

public class ArticuloController {

	private static ArrayList<Articulo> articulos;
	private static BDConnection conexion;
	private static Scanner sc = new Scanner(System.in);
	
	public ArticuloController() {
		conexion = new BDConnection();
		articulos = new ArrayList<Articulo>();
		try {
			recuperarArticulosBD();
		} catch (SQLException e) {
			System.out.println("Problema al recuperar los datos del alumnado.");
		}
	}
	
	private static void recuperarArticulosBD() throws SQLException {
		
		Statement s = conexion.getConexion().createStatement();
		Statement s2 = conexion.getConexion().createStatement();
		Statement s3 = conexion.getConexion().createStatement();
		
		String queryPizzas  = "select * from pizzas p inner join articulo a on p.art_p = a.cod_art";
		String queryBebidas = "select * from bebidas b inner join articulo a on b.art_b = a.cod_art" ;
		String queryPostres = "select * from postres p2 inner join articulo a on p2.art_p2 = a.cod_art";
		
		ResultSet rsPizzas = s.executeQuery(queryPizzas);
		ResultSet rsBebidas = s2.executeQuery(queryBebidas);
		ResultSet rsPostres = s3.executeQuery(queryPostres);
		
		while (rsPizzas.next()) {
		
		Pizzas p = new Pizzas(rsPizzas.getInt(1), rsPizzas.getInt(7), rsPizzas.getFloat(8),
				rsPizzas.getString(4), rsPizzas.getString(2), rsPizzas.getString(3), rsPizzas.getInt(5));
		articulos.add(p);
		}
		rsPizzas.close();
		
		while (rsBebidas.next()) {
			
		Bebidas b = new Bebidas(rsBebidas.getInt(1), rsBebidas.getInt(5), rsBebidas.getFloat(6),
				rsBebidas.getString(2), rsBebidas.getString(3));
		articulos.add(b);
		}
		while (rsPostres.next()) {
			
		Postres p2 = new Postres(rsPostres.getInt(1), rsPostres.getInt(5), rsPostres.getFloat(6),
				rsPostres.getString(2), rsPostres.getString(3));
		articulos.add(p2);
		}
	}
	public void verTickets () throws SQLException{
		Statement s = conexion.getConexion().createStatement();
		String queryTicket = "select * from resum_tickets";
		ResultSet rsTicket= s.executeQuery(queryTicket);
		System.out.println("======================= TICKETS =========================");
		while(rsTicket.next()) {
		System.out.println("Código del ticket: "+rsTicket.getInt(1)+ " Fecha y Hora: "+rsTicket.getTimestamp(2));
		}
		System.out.println("=========================================================");
	}
	
	public void topCincoArts() throws SQLException{
		
		Statement s = conexion.getConexion().createStatement();
		String queryTop  = "select * from resum_art";
		ResultSet rsTop = s.executeQuery(queryTop);
		Bebidas b = new Bebidas();
		Postres p2 = new Postres();
		
		
		while(rsTop.next()) {
			if(isNumeric2(rsTop.getInt(2))==true) {
				System.out.println("Id: "+rsTop.getInt(1)+", Pizza "+rsTop.getString(4)+", Uds Vendidas: "+rsTop.getInt(13));
				
			}else if (isNumeric2(rsTop.getInt(7))==true) {
				System.out.println("Id: "+rsTop.getInt(7)+", Bebida: "+rsTop.getString(8)+" "+rsTop.getString(9)+" Id:"+rsTop.getInt(7)+", Uds Vendidas: "+rsTop.getInt(13));
				
			}else if (isNumeric2(rsTop.getInt(10))==true) {
				System.out.println("Id: "+rsTop.getInt(10)+", Postre: "+rsTop.getString(11)+" "+rsTop.getString(12)+", Uds Vendidas: "+rsTop.getInt(13));
				
			}
		}
	}
	public static boolean isNumeric2(int valor) {
		boolean result;
		if(valor == 0) result = false;
		else result = true;
		return result;
	}
 	public void listarArts () {
		
		for(Articulo articulo : articulos) {
		System.out.println(articulo.toString());
		}
	}
	public void crearPostreController(int stock, float price, String name, String descr) {
		
		Postres p;
		p = new Postres(0,stock,price,name,descr);
		articulos.add(p);
		
		try {
			PreparedStatement ps = conexion.getConexion().prepareStatement("INSERT INTO articulo (cod_art, stock, precio) VALUES (?,?,?)");
			ps.setInt(1, p.getCod_art());
			ps.setInt(2, p.getStock());
			ps.setFloat(3, p.getPrecio());
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps2 = conexion.getConexion().prepareStatement("INSERT INTO postres (art_p2, tip_postre, postr_descript) VALUES (?,?,?)");
			ps2.setInt(1, p.getCod_art());
			ps2.setString(2, p.getTip_postre());
			ps2.setString(3, p.getDescr());
			ps2.executeUpdate();
			ps2.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			BDConnection.escribirErrBD(e);
		}
	}

	public void crearBebidaController(int stock, float price, String name, String descr) {
		
		Bebidas b;
		b = new Bebidas(0,stock,price,name,descr);
		articulos.add(b);
		
		try {
			PreparedStatement ps = conexion.getConexion().prepareStatement("INSERT INTO articulo (cod_art, stock, precio) VALUES (?,?,?)");
			ps.setInt(1, b.getCod_art());
			ps.setInt(2, b.getStock());
			ps.setFloat(3, b.getPrecio());
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps2 = conexion.getConexion().prepareStatement("INSERT INTO bebidas (art_b, tip_bebidas,bebid_descript) VALUES (?,?,?)");
			ps2.setInt(1, b.getCod_art());
			ps2.setString(2, b.getTip_bebida());
			ps2.setString(3, b.getDescr());
			ps2.executeUpdate();
			ps2.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			BDConnection.escribirErrBD(e);
		}
	}

	public void crearPizzaController(int stock,float price, String tamanho, String ingreds, int num_ingreds, String espc) {
		
		Pizzas p;
		p = new Pizzas(0,stock,price,ingreds,tamanho,espc,num_ingreds);
		articulos.add(p);
		
		try {
			PreparedStatement ps = conexion.getConexion().prepareStatement("INSERT INTO articulo (cod_art, stock, precio) VALUES (?,?,?)");
			ps.setInt(1, p.getCod_art());
			ps.setInt(2, p.getStock());
			ps.setFloat(3, p.getPrecio());
			ps.executeUpdate();
			ps.close();
			
			PreparedStatement ps2 = conexion.getConexion().prepareStatement("INSERT INTO pizzas (art_p,tamanho,especialidad,ingredientes,num_ingredientes) VALUES (?,?,?,?,?)");
			ps2.setInt(1, p.getCod_art());
			ps2.setString(2, p.getTamanho());
			ps2.setString(3, p.getEspecialidad());
			ps2.setString(4, p.getIngredientes());
			ps2.setInt(5, p.getNum_ingredientes());
			ps2.executeUpdate();
			ps2.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			BDConnection.escribirErrBD(e);
		}
	}
	
	public boolean borrarArt(int cod) {
	
		for( Articulo articulo: articulos) {
			if(articulo.getCod_art() == cod) {
				try {
					Statement s = conexion.getConexion().createStatement();
					s.executeUpdate("DELETE FROM articulo a WHERE a.cod_art=" + cod);
					s.close();
					articulos.remove(articulo);
					return true;
				} catch (SQLException e) {
					BDConnection.escribirErrBD(e);
				}
			}
		}
		return false;
	}
	public boolean comprobarId(int valor) {
		
		if(valor>articulos.size() || valor <= 0) return false;
		else return true;	
	}
	public void mostrarArt(int valor) {

		for( Articulo articulo: articulos) {
			if (articulo.getCod_art()==valor) {
				System.out.println("ARTICULO SELECCIONADO");
				System.out.println(articulo.toString());
			}
		}
	}
	public int devolverObjArt(Articulo art) {
		
		if(art instanceof Pizzas) {
			return 1;
		}else if (art instanceof Bebidas) {
			return 2;
		}else if (art instanceof Postres) {
			return 3;
		}
		return 0;
	}
	
	public Articulo rellenarArt(int valor) {
		
		for( Articulo articulo: articulos) {
			
			if (articulo.getCod_art()==valor) {
				return articulo;
			}
		}
		return null;
		
	}
	public void intercambioPostre(Postres p, int pos) {
		
		articulos.set(pos, p);
		
		try {
			Statement s = conexion.getConexion().createStatement();
			s.executeUpdate("update articulo a set a.stock = "+p.getStock()+" where a.cod_art = "+p.getCod_art());
			s.executeUpdate("update articulo a set a.precio = "+p.getPrecio()+" where a.cod_art = "+p.getCod_art());
			s.close();
			
			Statement s1 = conexion.getConexion().createStatement();
			s1.executeUpdate("update postres p set p.tip_postre  = \""+p.getTip_postre() +"\" where p.art_p2= "+p.getCod_art());
			s1.executeUpdate("update postres p set p.postr_descript  = \""+p.getTip_postre() +"\" where p.art_p2= "+p.getCod_art());
			s1.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			BDConnection.escribirErrBD(e);
		}
		
	}
	public void intercambioBebida(Bebidas b, int pos) {
		
		articulos.set(pos, b);
		
		try {
			Statement s = conexion.getConexion().createStatement();
			s.executeUpdate("update articulo a set a.stock = "+b.getStock()+" where a.cod_art = "+b.getCod_art());
			s.executeUpdate("update articulo a set a.precio = "+b.getPrecio()+" where a.cod_art = "+b.getCod_art());
			s.close();
			
			Statement s1 = conexion.getConexion().createStatement();
			s1.executeUpdate("update bebidas b set b.tip_bebidas = \""+b.getTip_bebida() +"\" where b.art_b ="+b.getCod_art());
			s1.executeUpdate("update bebidas b set b.bebid_descript = \""+b.getDescr() +"\" where b.art_b ="+b.getCod_art());
			s1.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			BDConnection.escribirErrBD(e);
		}
	}
	public void intercambioPizzas(Pizzas p, int pos) {
		
		articulos.set(pos, p);
		
		try {
			Statement s = conexion.getConexion().createStatement();
			s.executeUpdate("update articulo a set a.stock = "+p.getStock()+" where a.cod_art = "+p.getCod_art());
			s.executeUpdate("update articulo a set a.precio = "+p.getPrecio()+" where a.cod_art = "+p.getCod_art());
			s.close();
			
			Statement s1 = conexion.getConexion().createStatement();
			s1.executeUpdate("update pizzas p set p.tamanho = \""+p.getTamanho()+"\" where p.art_p = "+p.getCod_art());
			s1.executeUpdate("update pizzas p set p.especialidad = \""+p.getEspecialidad()+"\" where p.art_p = "+p.getCod_art());
			s1.executeUpdate("update pizzas p set p.ingredientes = \""+p.getIngredientes()+"\" where p.art_p = "+p.getCod_art());
			s1.executeUpdate("update pizzas p set p.num_ingredientes = "+p.getNum_ingredientes()+" where p.art_p = "+p.getCod_art());
			s1.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			BDConnection.escribirErrBD(e);
		}
	}
	public int devolverPos(int id) {
		
		int i;
		for(i = 0; i<=articulos.size(); i++) {
			if(articulos.get(i).getCod_art() == id) return i;
		}
		return -1;
	}
	public static ArrayList<Articulo> getArticulos() {
		return articulos;
	}

	public static void setArticulos(ArrayList<Articulo> articulos) {
		ArticuloController.articulos = articulos;
	}

	public static BDConnection getConexion() {
		return conexion;
	}

	public static void setConexion(BDConnection conexion) {
		ArticuloController.conexion = conexion;
	}
}
