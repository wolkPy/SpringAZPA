package py.com.qa.clases;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import py.com.qa.configs.Configuracion;

public class Lanzamiento {
	/* CONSTANTES */
	private static final String sqlInsert = "insert into qa_lab_mov(cod_empresa, cod_movimiento, fecha, cod_planilla) values (?,?,?,?)";
	private Connection con = Configuracion.CON;

	/* VARIABLES */
	private String codEmpresa;
	private long codMovimiento;
	private String fecha;
	private long codPlanilla;

	public Lanzamiento(String codEmpresa, long codMovimiento, String fecha, long codPlanilla) {
		super();
		if (codEmpresa != null) {
			this.codEmpresa = codEmpresa;
		} else {
			this.codEmpresa = Configuracion.CODEMPRESA;
		}

		this.codMovimiento = codMovimiento;
		this.fecha = fecha;
	}

	public Lanzamiento() {
		super();
	}

	/***************************************************************/
	/************** INSERTAR DETALLE DE LANZAMIENTO ****************/
	/***************************************************************/
	public long insertar() {
		CallableStatement sentencia;
		/* SE RECUPERA EL SIGUIENTE NUMERO DE MOVIMIENTO */
		try {
			sentencia = con.prepareCall("{?=call fnc_devu_lab_mov(?)}");
			sentencia.registerOutParameter(1, Types.NUMERIC);
			sentencia.setString(2, this.codEmpresa);
			sentencia.executeQuery();
			this.codMovimiento = sentencia.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			PreparedStatement pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, this.codEmpresa);
			pstmt.setLong(2, this.codMovimiento);
			pstmt.setString(3, this.fecha);
			pstmt.setLong(4, this.codPlanilla);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.codMovimiento;
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

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public long getCodPlanilla() {
		return codPlanilla;
	}

	public void setCodPlanilla(long codPlanilla) {
		this.codPlanilla = codPlanilla;
	}
}
