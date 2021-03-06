package py.com.qa.clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import py.com.qa.configs.Configuracion;

public class PlanillaConfig implements Operacion {
	/* CONSTANTES */
	static final String sqlInsert = "insert into qa_planilla_config(cod_empresa, cod_sucursal, cod_planilla, plc_division, plc_int_div_ini, plc_int_div_fim, plc_nveces, plc_status, plc_tipo, plc_tam_col) values(?,?,?,?,?,?,?,?,?,?)";
	static final String sqlDelete = "delete from qa_planilla_config where cod_empresa = ? and cod_sucursal = ? and cod_planilla = ?";
	static final String sqlUpdate = "update qa_planilla_config set plc_division = ?, plc_int_div_ini = ?, plc_int_div_fim = ?, plc_nveces = ?, plc_status = ?, plc_tipo = ?, plc_tam_col = ? where cod_empresa = ? and cod_sucursal = ? and cod_planilla  = ?";
	static final String sqlSelectAll = "select cod_empresa, cod_sucursal, cod_planilla, plc_division, plc_int_div_ini, plc_int_div_fim, plc_nveces, plc_status, plc_tipo, plc_tam_col from qa_planilla_config";
	static final String sqlSelectId = "select cod_empresa, cod_sucursal, cod_planilla, plc_division, plc_int_div_ini, plc_int_div_fim, plc_nveces, plc_status, plc_tipo, plc_tam_col from qa_planilla_config where cod_empresa = ? and cod_sucursal = ? and cod_planilla = ?";
	/* VARIABLES */
	private String codEmpresa;
	private String codSucursal;
	private long codPlanilla;
	private int plcDivision;
	private Timestamp pclIntDivIni;
	private Timestamp pclIntDivFim;
	private int plcNVeces;
	private int plcStatus;
	private int plcTipo;
	private int plcTamCol;

	public PlanillaConfig(String codEmpresa, String codSucursal, long codPlanilla, int plcDivision,
			Timestamp pclIntDivIni, Timestamp pclIntDivFim, int plcNVeces, int plcStatus, int plcTipo, int plcTamCol) {
		super();
		this.codEmpresa = codEmpresa;
		this.codSucursal = codSucursal;
		this.codPlanilla = codPlanilla;
		this.plcDivision = plcDivision;
		this.pclIntDivIni = pclIntDivIni;
		this.pclIntDivFim = pclIntDivFim;
		this.plcNVeces = plcNVeces;
		this.plcStatus = plcStatus;
		this.plcTipo = plcTipo;
		this.plcTamCol = plcTamCol;
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

	public int getPlcDivision() {
		return plcDivision;
	}

	public void setPlcDivision(int plcDivision) {
		this.plcDivision = plcDivision;
	}

	public Timestamp getPclIntDivIni() {
		return pclIntDivIni;
	}

	public void setPclIntDivIni(Timestamp pclIntDivIni) {
		this.pclIntDivIni = pclIntDivIni;
	}

	public Timestamp getPclIntDivFim() {
		return pclIntDivFim;
	}

	public void setPclIntDivFim(Timestamp pclIntDivFim) {
		this.pclIntDivFim = pclIntDivFim;
	}

	public int getPlcNVeces() {
		return plcNVeces;
	}

	public void setPlcNVeces(int plcNVeces) {
		this.plcNVeces = plcNVeces;
	}

	public int getPlcStatus() {
		return plcStatus;
	}

	public void setPlcStatus(int plcStatus) {
		this.plcStatus = plcStatus;
	}

	public int getPlcTipo() {
		return plcTipo;
	}

	public void setPlcTipo(int plcTipo) {
		this.plcTipo = plcTipo;
	}

	public int getPlcTamCol() {
		return plcTamCol;
	}

	public void setPlcTamCol(int plcTamCol) {
		this.plcTamCol = plcTamCol;
	}

	@Override
	public long insertar() {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlInsert);
			pstmt.setString(1, this.codEmpresa);
			pstmt.setString(2, this.codSucursal);
			pstmt.setLong(3, this.codPlanilla);
			pstmt.setInt(4, this.plcDivision);
			pstmt.setTimestamp(5, this.pclIntDivIni);
			pstmt.setTimestamp(6, this.pclIntDivFim);
			pstmt.setInt(7, this.plcNVeces);
			pstmt.setInt(8, this.plcStatus);
			pstmt.setInt(9, this.plcTipo);
			pstmt.setInt(10, this.plcTamCol);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.codPlanilla;
	}

	@Override
	public long getNextId() {
		return this.codPlanilla;
	}

	@Override
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

	@Override
	public void actualizar() {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt = con.prepareStatement(sqlUpdate);

			pstmt.setInt(1, this.plcDivision);
			pstmt.setTimestamp(2, pclIntDivIni);
			pstmt.setTimestamp(3, pclIntDivFim);
			pstmt.setInt(4, this.plcNVeces);
			pstmt.setInt(5, this.plcStatus);
			pstmt.setInt(6, this.plcTipo);
			pstmt.setInt(7, this.plcTamCol);
			pstmt.setString(8, this.codEmpresa);
			pstmt.setString(9, this.codSucursal);
			pstmt.setLong(10, this.codPlanilla);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public PlanillaConfig getById(String codEmpresa, String codSucursal, long codPlanilla) {
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
				this.setPlcDivision(rs.getInt("plc_division"));
				this.setPclIntDivIni(rs.getTimestamp("plc_int_div_ini"));
				this.setPclIntDivFim(rs.getTimestamp("plc_int_div_fim"));
				this.setPlcNVeces(rs.getInt("plc_nveces"));
				this.setPlcStatus(rs.getInt("plc_status"));
				this.setPlcTipo(rs.getInt("plc_tipo"));
				this.setPlcTamCol(rs.getInt("plc_tam_col"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
}