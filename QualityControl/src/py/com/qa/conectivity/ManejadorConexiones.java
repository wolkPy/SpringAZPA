package py.com.qa.conectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;

public class ManejadorConexiones {

	public static Connection obtenerConexionORA(String user, String pass) throws SQLException {
		return obtenerConexion("oracle", user, pass);
	}

	private static Connection obtenerConexion(String fileName, String user, String pass) throws SQLException {
		PropertyResourceBundle prop = (PropertyResourceBundle) PropertyResourceBundle.getBundle(fileName);
		Connection con = null;

		String url = prop.getString("url");
		// String uname = prop.getString("usuario");
		// String passwd = prop.getString("clave");

		// con = DriverManager.getConnection(url, uname, passwd);
		con = DriverManager.getConnection(url, user, pass);

		System.out.println("Estamos conectados al: " + con.getMetaData().getDatabaseProductName() + " "
				+ con.getMetaData().getDatabaseProductVersion());
		return con;
	}
}