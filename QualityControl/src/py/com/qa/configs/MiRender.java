package py.com.qa.configs;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class MiRender extends javax.swing.table.DefaultTableCellRenderer implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NumberFormat nf = NumberFormat.getInstance();

	public boolean isSelected(int row, int column) {
		setBackground(Color.lightGray);
		return true;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		// SI ES FORMULA PINTAR CAMPO
		JFormattedTextField campoTexto = new JFormattedTextField();

		if (table.getValueAt(row, Configuracion.POSCOLESFORMULA).toString().equals("S")) {
			this.setOpaque(true);
			this.setBackground(Color.YELLOW);
			this.setForeground(Color.BLACK);
		} else {
			this.setOpaque(false);
			this.setBackground(Color.WHITE);
			this.setForeground(Color.BLACK);
		}
		// SETEAR FORMATO DE CAMPO
		if (column > Configuracion.POSCOLMEDIDA) {
			if (table.getValueAt(row, Configuracion.POSCOLTIPOVAR).toString().equals("0")) {
				this.setHorizontalAlignment(SwingConstants.RIGHT);
				if (value instanceof BigDecimal || value == null) {
					campoTexto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
							new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.00"))));
					campoTexto.setValue(value);
				} else if (value instanceof Integer) {
					campoTexto.setText("" + (Integer) value);
				}
			} else if (table.getValueAt(row, Configuracion.POSCOLTIPOVAR).toString().equals("1")) {
				this.setHorizontalAlignment(SwingConstants.LEFT);
				if (value instanceof Date) {
					// DateFormat formatter = DateFormat.getDateInstance();
					SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
					value = f.format(value);
					Date parsed = (Date) value;
					try {
						parsed = (Date) f.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					value = parsed.toString();
				}
			} else {
				this.setHorizontalAlignment(SwingConstants.RIGHT);
				if (value instanceof String) {
					campoTexto.setText((String) value);
				}
			}
		}

		return this;
	}

	/*
	 * public void tableChanged(TableModelEvent e) { DefaultTableModel modelo =
	 * (DefaultTableModel) e.getSource(); int row = e.getFirstRow(); int column =
	 * e.getColumn(); String cellValue = String.valueOf(sqlData.getValueAt(row,
	 * column)); System.out.println("Value at (" + row + "," + column +
	 * ") changed to " + "\'" + cellValue + "\'"); }
	 * 
	 */
}
