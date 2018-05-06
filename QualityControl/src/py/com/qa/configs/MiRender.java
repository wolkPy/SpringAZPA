package py.com.qa.configs;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.JTable;

public class MiRender extends javax.swing.table.DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NumberFormat nf = NumberFormat.getInstance();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		// SI ES FORMULA PINTAR CAMPO
		if (table.getValueAt(row, 6).toString().equals("S")) {
			this.setOpaque(true);
			this.setBackground(Color.RED);
			this.setForeground(Color.YELLOW);
		} else {
			this.setOpaque(false);
			this.setBackground(Color.WHITE);
			this.setForeground(Color.BLACK);
		}
//		if (table.getValueAt(row, 7).toString().equals("0")) {
//			nf.format(this);
//		}
		
		return this;
	}
}