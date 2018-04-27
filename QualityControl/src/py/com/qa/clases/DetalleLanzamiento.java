package py.com.qa.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import py.com.qa.configs.Configuracion;

public class DetalleLanzamiento {
	/* CONSTANTES */
	private static final String sqlInsert = "insert into qa_lab_mov_deta(cod_empresa, cod_movimiento, cod_variable, mov_columna, mov_valor) values (?,?,?,?,?)";
	// private static final String sqlDelete = "delete from qa_lab_mov where
	// cod_empresa = ? and cod_movimiento = ?";
	// private static final String sqlUpdate = "";
	// private static final String sqlSelectAll = "";
	// private static final String sqlSelectId = "";
	// private static final String sqlSelectMaxId = "";
	// private static final String sqlSelectDescripcion = "";
	private Connection con = Configuracion.CON;

	/* VARIABLES */
	private String codEmpresa;
	private long codMovimiento;
	private long codVariable;
	private int movColumna;
	private String movValor;

	public DetalleLanzamiento(String codEmpresa, long codMovimiento, long codVariable, int movColumna,
			String movValor) {
		super();
		this.codEmpresa = codEmpresa;
		this.codMovimiento = codMovimiento;
		this.codVariable = codVariable;
		this.movColumna = movColumna;
		this.movValor = movValor;
	}

	public void insertar() {
		try {
			PreparedStatement pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, this.codEmpresa);
			pstmt.setLong(2, this.codMovimiento);
			pstmt.setLong(3, this.codVariable);
			pstmt.setInt(4, this.movColumna);
			pstmt.setString(5, this.movValor);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public long getCodMovimiento() {
		return codMovimiento;
	}

	public void setCodMovimiento(long codMovimiento) {
		this.codMovimiento = codMovimiento;
	}

	public long getCodVariable() {
		return codVariable;
	}

	public void setCodVariable(long codVariable) {
		this.codVariable = codVariable;
	}

	public int getMovColumna() {
		return movColumna;
	}

	public void setMovColumna(int movColumna) {
		this.movColumna = movColumna;
	}

	public String getMovValor() {
		return movValor;
	}

	public void setMovValor(String movValor) {
		this.movValor = movValor;
	}
}