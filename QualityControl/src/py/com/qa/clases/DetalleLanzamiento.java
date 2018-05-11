package py.com.qa.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import py.com.qa.configs.Configuracion;

/*
 * CLASE PARA DETALLE DE LANZAMIENTO O MOVIMIENTO DE CONTROL DE CALIDAD
 * */
public class DetalleLanzamiento {
	/* CONSTANTES */
	private static final String sqlInsert = "insert into qa_lab_mov_deta(cod_empresa, cod_movimiento, cod_variable, mov_columna, mov_valor) values (?,?,?,?,?)";
	private static final String sqlUpdate = "update qa_lab_mov_deta set mov_valor = ? where cod_empresa = ? and cod_movimiento = ? and cod_variable = ? and mov_columna = ?";
	private Connection con = Configuracion.CON;

	/* VARIABLES */
	private String codEmpresa;
	private long codMovimiento;
	private long codVariable;
	private String movColumna;
	private String movValor;

	public DetalleLanzamiento(String codEmpresa, long codMovimiento, long codVariable, String movColumna,
			String movValor) {
		super();
		this.codEmpresa = codEmpresa;
		this.codMovimiento = codMovimiento;
		this.codVariable = codVariable;
		this.movColumna = movColumna;
		this.movValor = movValor;
	}

	/***************************************************************/
	/************** INSERTAR DETALLE DE LANZAMIENTO ****************/
	/***************************************************************/
	public void insertar() {
		try {
			PreparedStatement pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, this.codEmpresa);
			pstmt.setLong(2, this.codMovimiento);
			pstmt.setLong(3, this.codVariable);
			pstmt.setString(4, this.movColumna);
			pstmt.setString(5, this.movValor);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***************************************************************/
	/************* ACTUALIZAR DETALLE DE LANZAMIENTO ***************/
	/***************************************************************/
	public void actualizar() {
		try {
			PreparedStatement pstmt = con.prepareStatement(sqlUpdate);
			pstmt.setString(1, this.movValor);
			pstmt.setString(2, this.codEmpresa);
			pstmt.setLong(3, this.codMovimiento);
			pstmt.setLong(4, this.codVariable);
			pstmt.setString(5, this.movColumna);

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

	public String getMovColumna() {
		return movColumna;
	}

	public void setMovColumna(String movColumna) {
		this.movColumna = movColumna;
	}

	public String getMovValor() {
		return movValor;
	}

	public void setMovValor(String movValor) {
		this.movValor = movValor;
	}
}