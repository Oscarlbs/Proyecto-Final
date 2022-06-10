package bbdd;

import java.io.BufferedWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BDConnection {

	private static Connection conexion;

	private static String user, pass, url, port;

	public BDConnection() {
		startConnection();
	}

	public void startConnection() {
		conexion = null;
		try {
			property();
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			escribirErrBD(e);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void endConnection() {
		try {
			if (conexion != null) {
				conexion.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			escribirErrBD(ex);
		}
	}

	public static void property() {
		Properties leyendo = new Properties();
		try {
			leyendo.load(new FileInputStream("configuracion.props"));
			user = leyendo.getProperty("username");
			pass = leyendo.getProperty("password");
			url = leyendo.getProperty("url") + ":" + leyendo.getProperty("port") + "/" +leyendo.getProperty("name");
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	public static void escribirErrBD(SQLException e) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter("BD_err.log", true);
			bw = new BufferedWriter(fw);
			bw.write("\n" + e.getMessage());
			bw.flush();
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		} finally {
			try {
				if (fw != null) {
					fw.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public Connection getConexion() {
		return conexion;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String user) {
		BDConnection.user = user;
	}

	public static String getPass() {
		return pass;
	}

	public static void setPass(String pass) {
		BDConnection.pass = pass;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		BDConnection.url = url;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		BDConnection.port = port;
	}

}