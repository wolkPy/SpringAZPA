package py.com.qa.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import py.com.qa.configs.Configuracion;

public class Cierre {
	/* CONSTANTES */
	private static final String sqlUpdate = "update qa_planilla set ind_cerrado = ? where cod_empresa = ? and fecha = ?";
	private static final String sqlSelectAll = "select cod_empresa, fecha, ind_cerrado, cod_usr_cierre, fecha_cierre from qa_planilla where cod_empresa = ? order by fecha desc";
	private static final String sqlSelectId = "select cod_empresa, fecha, ind_cerrado, cod_usr_cierre, fecha_cierre from qa_planilla where cod_empresa = ? and fecha = ?";
	/* VARIABLES */
	private String codEmpresa;
	private String fecha;
	private String indCerrado;
	private String codUserCierre;
	private Timestamp fechaCierre;

	public Cierre(String codEmpresa, String fecha, String indCerrado, String codUserCierre, Timestamp fechaCierre) {
		super();
		this.codEmpresa = codEmpresa;
		this.fecha = fecha;
		this.indCerrado = indCerrado;
		this.codUserCierre = codUserCierre;
		this.fechaCierre = fechaCierre;
	}

	public void actualizar() {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlUpdate);
			pstmt.setString(1, this.indCerrado);
			pstmt.setString(2, this.codEmpresa);
			pstmt.setString(3, this.fecha);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Cierre> getAll(String codEmpresa) {
		ArrayList<Cierre> cierres = new ArrayList<>();
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlSelectAll);
			pstmt.setString(1, codEmpresa);
			ResultSet rs = pstmt.executeQuery();
			Cierre c = null;
			while (rs.next()) {
				c = new Cierre(rs.getString("cod_empresa"), rs.getString("fecha"), rs.getString("ind_cerrado"),
						rs.getString("cod_usr_cierre"), rs.getTimestamp("fecha_cierre"));
				cierres.add(c);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cierres;
	}

	public Cierre getById(String codEmpresa, String fecha) {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlSelectId);
			pstmt.setString(1, codEmpresa);
			pstmt.setString(2, fecha);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				this.setCodEmpresa(rs.getString("cod_empresa"));
				this.setFecha(rs.getString("fecha"));
				this.setIndCerrado(rs.getString("ind_cerrado"));
				this.setCodUserCierre(rs.getString("cod_usr_cierre"));
				this.setFechaCierre(rs.getTimestamp("fecha_cierre"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(String codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getIndCerrado() {
		return indCerrado;
	}

	public void setIndCerrado(String indCerrado) {
		this.indCerrado = indCerrado;
	}

	public String getCodUserCierre() {
		return codUserCierre;
	}

	public void setCodUserCierre(String codUserCierre) {
		this.codUserCierre = codUserCierre;
	}

	public Timestamp getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Timestamp fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
}
