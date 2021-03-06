package py.com.qa.clases;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	private JTable sqlData;

	public ExcelWriter(JTable sqlData) {
		super();
		this.sqlData = sqlData;
	}

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

	public void writeExcelTabla() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		Row row;
		Cell cell;
		// Exportar primeramente los titulos de columnas
		row = sheet.createRow(0);
		for (int k = 1; k <= sqlData.getColumnCount() - Configuracion.POSCOLORDEN; k++) {
			cell = row.createCell(k - 1);
			cell.setCellValue(sqlData.getColumnName(Configuracion.POSCOLORDEN + k - 1));
		}

		/** a partir de la fila 2 escribe los datos. **/
		BigDecimal bd;
		String bdS;
		for (int j = 1; j <= sqlData.getRowCount(); j++) {
			row = sheet.createRow(j);
			for (int k = 1; k <= sqlData.getColumnCount() - Configuracion.POSCOLORDEN; k++) {
				cell = row.createCell(k - 1);
				if (sqlData.getValueAt(j - 1, Configuracion.POSCOLORDEN + k - 1) != null) {
					if (sqlData.getValueAt(j - 1, Configuracion.POSCOLORDEN + k - 1) instanceof java.math.BigDecimal) {
						bd = (BigDecimal) sqlData.getValueAt(j - 1, Configuracion.POSCOLORDEN + k - 1);
						bdS = String.valueOf(bd.doubleValue());
						cell.setCellValue(bdS);
					} else if (sqlData.getValueAt(j - 1, Configuracion.POSCOLORDEN + k - 1) instanceof String) {
						cell.setCellValue((String) sqlData.getValueAt(j - 1, Configuracion.POSCOLORDEN + k - 1));
					} else if (sqlData.getValueAt(j - 1, Configuracion.POSCOLORDEN + k - 1) instanceof Integer) {
						cell.setCellValue((Integer) sqlData.getValueAt(j - 1, Configuracion.POSCOLORDEN + k - 1));
					}
				}
			}
		}
		try {
			JFileChooser selector = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
			selector.addChoosableFileFilter(filter);

			int op = selector.showSaveDialog(new JFrame());
			if (op == JFileChooser.APPROVE_OPTION) {
				File file = selector.getSelectedFile();
				FileOutputStream fos = new FileOutputStream(file + ".xlsx");
				workbook.write(fos);
				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeExcel() {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		Row row;
		Cell cell;
		// Exportar primeramente los titulos de columnas
		row = sheet.createRow(0);
		for (int k = 1; k <= numeroColumnas; k++) {
			cell = row.createCell(k - 1);
			cell.setCellValue(columnNames[k - 1]);
		}

		/** a partir de la fila 2 escribe los datos. **/
		BigDecimal bd;
		String bdS;
		for (int j = 1; j <= numeroFilas; j++) {
			row = sheet.createRow(j);
			for (int k = 1; k <= numeroColumnas; k++) {
				cell = row.createCell(k - 1);
				if (data[j - 1][k - 1] != null) {
					if (data[j - 1][k - 1] instanceof java.math.BigDecimal) {
						bd = (BigDecimal) data[j - 1][k - 1];
						bdS = String.valueOf(bd.doubleValue());
						cell.setCellValue(bdS);
					} else if (data[j - 1][k - 1] instanceof String) {
						cell.setCellValue((String) data[j - 1][k - 1]);
					} else if (data[j - 1][k - 1] instanceof Integer) {
						cell.setCellValue((Integer) data[j - 1][k - 1]);
					}
				}
			}
		}
		try {
			JFileChooser selector = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
			selector.addChoosableFileFilter(filter);

			int op = selector.showSaveDialog(new JFrame());
			if (op == JFileChooser.APPROVE_OPTION) {
				File file = selector.getSelectedFile();
				FileOutputStream fos = new FileOutputStream(file + ".xlsx");
				workbook.write(fos);
				workbook.close();
			}
		} catch (IOException e) {
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