package py.com.qa.clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import py.com.qa.configs.Configuracion;

/**
 *   
 * @author lolmedo
 * @fecha 27/04/2018
 */
public class ExcelWriter {
	private int numeroFilas;
	private int numeroColumnas;
	private String[] columnNames;
	private Object[][] data;

	public ExcelWriter(String sql) {
		super();
		try {
			Connection con = Configuracion.CON;
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			/***************************************************************/
			/***************** CARGAR NOMBRE DE COLUMNAS *******************/
			/***************************************************************/
			ResultSetMetaData rsmd = rs.getMetaData();
			columnNames = new String[rsmd.getColumnCount()];
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				columnNames[i - 1] = rsmd.getColumnName(i);
			}
			/***************************************************************/
			/***** IR A ULTIMO REGISTRO PARA SABER CUANTAS FILAS TIENE *****/
			/***************************************************************/
			rs.last();
			numeroFilas = rs.getRow();
			numeroColumnas = rsmd.getColumnCount();
			data = new Object[numeroFilas][numeroColumnas];
			/***************************************************************/
			/** REGRESA A REGISTRO 0, PARA PODER RECORRER DESDE EL PRIMER **/
			/*********** REGISTRO, O SINO RECORRE DESDE EL SEGUNDO *********/
			/***************************************************************/
			rs.beforeFirst();
			int j = 0;
			while (rs.next()) {
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					data[j][i - 1] = rs.getObject(i);
				}
				j++;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getNumeroFilas() {
		return numeroFilas;
	}

	public void setNumeroFilas(int numeroFilas) {
		this.numeroFilas = numeroFilas;
	}

	public int getNumeroColumnas() {
		return numeroColumnas;
	}

	public void setNumeroColumnas(int numeroColumnas) {
		this.numeroColumnas = numeroColumnas;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
}