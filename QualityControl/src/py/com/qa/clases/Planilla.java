package py.com.qa.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import py.com.qa.configs.Configuracion;

public class Planilla {
	/* CONSTANTES */
	private static final String sqlInsert = "insert into qa_planilla(cod_empresa, cod_sucursal, cod_planilla, descripcion, cod_planilla_padre, orden, estado) values (?,?,?,?,?,?,?)";
	private static final String sqlDelete = "delete from qa_planilla where cod_empresa = ? and cod_sucursal = ? and cod_planilla = ?";
	private static final String sqlUpdate = "update qa_planilla set descripcion = ?, cod_planilla_padre = ?, orden = ?, estado = ? where cod_empresa = ? and cod_sucursal = ? and cod_planilla  = ?";
	private static final String sqlSelectAll = "select cod_empresa, cod_sucursal, cod_planilla, descripcion, cod_planilla_padre, orden, estado from qa_planilla";
	private static final String sqlSelectId = "select cod_empresa, cod_sucursal, cod_planilla, descripcion, cod_planilla_padre, orden, estado from qa_planilla where cod_empresa = ? and cod_sucursal = ? and cod_planilla = ?";
	private static final String sqlSelectMaxId = "select nvl(max(cod_planilla),0) from qa_planilla where cod_empresa = ? and cod_sucursal = ?";
	private static final String sqlSelectDescripcion = "select cod_empresa, cod_sucursal, cod_planilla, descripcion, cod_planilla_padre, orden, estado from qa_planilla where cod_empresa = ? and cod_sucursal = ? and descripcion = ?";

	/* VARIABLES */
	private String codEmpresa;
	private String codSucursal;
	private long codPlanilla;
	private String descripcion;
	private long codPlanillaPadre;
	private String orden;
	private String estado;
	private String consulta;

	private PlanillaConfig configuracion;

	public Planilla(String cod_empresa, String cod_surcursal, long cod_planilla, String descripcion,
			long cod_planilla_padre, String orden, String estado, String consulta) {
		this.codEmpresa = cod_empresa;
		this.codSucursal = cod_surcursal;
		this.codPlanilla = cod_planilla;
		this.descripcion = descripcion;
		this.codPlanillaPadre = cod_planilla_padre;
		this.orden = orden;
		this.estado = estado;
		this.consulta = consulta;
	}

	public Planilla() {

	}

	public long insertar() {
		long codigo = getNextId();
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, Configuracion.CODEMPRESA);
			pstmt.setString(2, this.codSucursal);
			pstmt.setLong(3, codigo);
			pstmt.setString(4, this.descripcion);
			pstmt.setLong(5, this.codPlanillaPadre);
			pstmt.setString(6, this.orden);
			pstmt.setString(7, this.estado);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codigo;
	}

	private long getNextId() {
		long codigo = 0;
		Connection con = Configuracion.CON;
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(sqlSelectMaxId);
			pstmt.setString(1, this.codEmpresa);
			pstmt.setString(2, this.codSucursal);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				codigo += rs.getInt("codigo");
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codigo;
	}

	public void borrar() {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlDelete);
			pstmt.setString(1, this.codEmpresa);
			pstmt.setString(2, this.codSucursal);
			pstmt.setLong(3, this.codPlanilla);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void actualizar() {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlUpdate);
			pstmt.setString(1, this.descripcion);
			pstmt.setLong(2, this.codPlanillaPadre);
			pstmt.setString(3, this.orden);
			pstmt.setString(4, this.estado);
			pstmt.setString(5, this.codEmpresa);
			pstmt.setString(6, this.codSucursal);
			pstmt.setLong(7, this.codPlanilla);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Planilla> getAll() {
		ArrayList<Planilla> planillas = new ArrayList<>();
		try {
			Connection con = Configuracion.CON;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelectAll);
			Planilla p = null;
			while (rs.next()) {
				p = new Planilla(rs.getString("cod_empresa"), rs.getString("cod_sucursal"), rs.getLong("cod_planilla"),
						rs.getString("descripcion"), rs.getLong("cod_planilla_padre"), rs.getString("orden"),
						rs.getString("estado"), "");
				planillas.add(p);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return planillas;
	}

	public Planilla getById(String codEmpresa, String codSucursal, long codPlanilla) {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlSelectId);
			pstmt.setString(1, codEmpresa);
			pstmt.setString(2, codSucursal);
			pstmt.setLong(3, codPlanilla);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				this.setCodEmpresa(rs.getString("cod_empresa"));
				this.setCodSucursal(rs.getString("cod_sucursal"));
				this.setCodPlanilla(rs.getLong("cod_planilla"));
				this.setDescripcion(rs.getString("descripcion"));
				this.setCodPlanillaPadre(rs.getLong("cod_planilla_padre"));
				this.setOrden(rs.getString("orden"));
				this.setEstado(rs.getString("estado"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Planilla getByDescripcion(String descripcion) {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlSelectDescripcion);
			pstmt.setString(1, Configuracion.CODEMPRESA);
			pstmt.setString(2, Configuracion.CODSUCURSAL);
			pstmt.setString(3, descripcion);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				this.setCodEmpresa(rs.getString("cod_empresa"));
				this.setCodSucursal(rs.getString("cod_sucursal"));
				this.setCodPlanilla(rs.getLong("cod_planilla"));
				this.setDescripcion(rs.getString("descripcion"));
				this.setCodPlanillaPadre(rs.getLong("cod_planilla_padre"));
				this.setOrden(rs.getString("orden"));
				this.setEstado(rs.getString("estado"));
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

	public String getCodSucursal() {
		return codSucursal;
	}

	public void setCodSucursal(String codSucursal) {
		this.codSucursal = codSucursal;
	}

	public long getCodPlanilla() {
		return codPlanilla;
	}

	public void setCodPlanilla(long codPlanilla) {
		this.codPlanilla = codPlanilla;
	}

	public long getCodPlanillaPadre() {
		return codPlanillaPadre;
	}

	public void setCodPlanillaPadre(long codPlanillaPadre) {
		this.codPlanillaPadre = codPlanillaPadre;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public PlanillaConfig getConfiguracion() {
		return configuracion;
	}

	public void setConfiguracion(PlanillaConfig configuracion) {
		this.configuracion = configuracion;
	}

}