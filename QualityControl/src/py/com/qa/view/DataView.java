package py.com.qa.view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import py.com.qa.clases.DetalleLanzamiento;
import py.com.qa.clases.ExcelWriter;
import py.com.qa.clases.Lanzamiento;
import py.com.qa.clases.Planilla;
import py.com.qa.configs.Configuracion;

public class DataView extends AbstractInternalFrame implements KeyListener {
	/* CONSTANTES */
	private static final String sqlSelectPlanillaDescripcion = "select a.cod_empresa,a.cod_sucursal,a.cod_planilla,a.descripcion,a.cod_planilla_padre,a.orden,a.estado,nvl(b.plc_tam_col,0) plc_tam_col from qa_planilla a, qa_planilla_config b where a.cod_empresa = b.cod_empresa(+) and a.cod_sucursal = b.cod_sucursal(+) and a.cod_planilla = b.cod_planilla(+) and a.cod_empresa = ? and a.cod_sucursal = ? and a.descripcion = ? and a.cod_planilla_padre <> 0 order by to_number(orden)";
	private static final String sqlSelectMovimiento = "select cod_movimiento from qa_lab_mov where cod_empresa = ? and cod_planilla = ? and fecha = ?";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* VARIABLES */
	private static String sql;
	private static ExcelWriter ew;
	private Connection con = Configuracion.CON;
	private String descripcion;
	private JTable sqlData;
	private int tamanhoCol;
	private Planilla planilla;
	/******************************************************/
	/************ PARSEAR DE DATE A STRING ****************/
	/******************************************************/
	private String formato;
	private SimpleDateFormat sdf;
	private Date date;
	private String fecha;
	/******************************************************/
	/****************** MOVIMIENTO ************************/
	/******************************************************/
	Lanzamiento lan;

	public DataView(String nombrePlanilla) {
		super(nombrePlanilla);
		this.descripcion = nombrePlanilla;
		/************ PARSEAR DE DATE A STRING ****************/
		formato = Configuracion.FILTER_DATE_CHOOSER.getDateFormatString();
		sdf = new SimpleDateFormat(formato);
		date = Configuracion.FILTER_DATE_CHOOSER.getDate();
		fecha = sdf.format(date);

		/*******************************************************/
		/******** RECUPERAMOS EL MODELO DE LA PLANILLA *********/
		/*******************************************************/
		initialize();
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		createAndAddComponentToPrincipalPane(this);

	}

	@SuppressWarnings("static-access")
	private void initialize() {
		planilla = null;
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt;
			pstmt = con.prepareStatement(sqlSelectPlanillaDescripcion);
			pstmt.setString(1, Configuracion.CODEMPRESA);
			pstmt.setString(2, Configuracion.CODSUCURSAL);
			pstmt.setString(3, this.descripcion);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				planilla = new Planilla(rs.getString("cod_empresa"), rs.getString("cod_sucursal"),
						rs.getLong("cod_planilla"), rs.getString("descripcion"), rs.getLong("cod_planilla_padre"),
						rs.getString("orden"), rs.getString("estado"), "");
				this.tamanhoCol = rs.getInt("plc_tam_col");
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		/*
		 * 
		 * 
		 * 
		 * 
		 * COMENTAR
		 * 
		 */
		/* SE DEBE TENER LOS FILTROS PARA EL QUERY */
		String codEmpresa = Configuracion.CODEMPRESA;
		String codSucursal = Configuracion.CODSUCURSAL;
		/* VERIFICAMOS SI EXISTE PLANILLA CARGADA EN FECHA SELECCIONADA */
		verificarPlanillaCargada(codEmpresa, planilla.getCodPlanilla(), this.fecha);
		/******************************************************/
		/***** EN CASO QUE CODIGO DE MOVIMIENTO NO SE HAYA ****/
		/************ ENCONTRADO SIGUE PROCESO NORMAL *********/
		/******************************************************/
		if (this.lan == null) {
			if (planilla != null) {
				CallableStatement sentencia;
				/******************************************************/
				/*** SE RECUPERA QUERY CON FUNCION DE BASE DE DATOS ***/
				/******************************************************/
				try {
					sentencia = con.prepareCall("{?=call fnc_devu_qa_columnas(?,?,?)}");
					sentencia.registerOutParameter(1, Types.VARCHAR);
					sentencia.setString(2, codEmpresa);
					sentencia.setString(3, codSucursal);
					sentencia.setLong(4, planilla.getCodPlanilla());
					sentencia.executeQuery();
					this.sql = sentencia.getString(1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			/***************************************************************************/
			/** EN CASO QUE ENCUENTRE MOVIMIENTO SE DEBE CARGAR DATOS YA ALMACENADOS. **/
			/****************** SE CAMBIA LA SENTENCIA DE CONSULTA *********************/
			/***************************************************************************/
			if (planilla != null) {
				CallableStatement sentencia;
				/****************************************************/
				/** SE RECUPERA QUERY CON FUNCION DE BASE DE DATOS **/
				/****************************************************/
				try {
					sentencia = con.prepareCall("{?=call fnc_devu_qa_lab_mov(?,?,?,?)}");
					sentencia.registerOutParameter(1, Types.VARCHAR);
					sentencia.setString(2, codEmpresa);
					sentencia.setString(3, codSucursal);
					sentencia.setLong(4, planilla.getCodPlanilla());
					sentencia.setLong(5, lan.getCodMovimiento());
					sentencia.executeQuery();
					this.sql = sentencia.getString(1);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void verificarPlanillaCargada(String codEmpresa, long codPlanilla, String fecha) {
		try {
			Connection con = Configuracion.CON;
			PreparedStatement pstmt;
			pstmt = con.prepareStatement(sqlSelectMovimiento);
			pstmt.setString(1, codEmpresa);
			pstmt.setLong(2, codPlanilla);
			pstmt.setString(3, fecha);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				lan = new Lanzamiento(codEmpresa, rs.getLong("cod_movimiento"), fecha, codPlanilla);
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void createAndAddComponentToPrincipalPane(DataView dataView) {
		/** Principal Panel **/
		JPanel principalPane = new JPanel();
		principalPane.setLayout(new BorderLayout());

		JScrollPane centerPane = null;

		sqlData = null;
		/*
		 * DEPENDIENDO SI ENCONTRO O NO LA PLANILLA REGISTRADA SE CARGA O NO UNA NUEVA
		 * PLANILLA
		 */
		System.out.println("sql = " + sql);
		ew = new ExcelWriter(sql);
		boolean isVisible = false;
		if (ew.getData().length != 0 && ew.getColumnNames().length != 0) {
			sqlData = new JTable(ew.getData(), ew.getColumnNames()) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int rowIndex, int colIndex) {
					return colIndex != 0 && colIndex != 1 && colIndex != 2 && colIndex != 3 && colIndex != 4
							&& colIndex != 5;
				}

				/**/
				@Override
				public void valueChanged(ListSelectionEvent e) {
					super.valueChanged(e);
					checkSelection(false);
				}
			};
			sqlData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//
			sqlData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			//
			centerPane = new JScrollPane(sqlData);
			JViewport viewport = new JViewport();
			viewport.setView(sqlData);
			viewport.setPreferredSize(sqlData.getPreferredSize());
			centerPane.setRowHeaderView(viewport);
			centerPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, sqlData.getTableHeader());
			//
			principalPane.add(centerPane);
		}
		/* RECORRER TABLA */
		for (int k = 0; k < sqlData.getRowCount(); k++) {
			for (int i = 0; i < sqlData.getColumnCount(); i++) {
				/***********************************************/
				/* SETEAR DE ACUERDO AL NOMBRE DE LAS COLUMNAS */
				/**** OCULTAR CAMPOS QUE NO SE DEBEN MOSTRAR ***/
				/***********************************************/

				/***********************************************/
				/* MIENTRAS NO LLEGUE A VAR_ORDEN SE OCULTARAN */
				/************ TODAS LAS COLUMNAS ***************/
				if (sqlData.getColumnName(i).toUpperCase().equals("ORDEN")) {
					isVisible = true;
				}
				if (!isVisible) {
					sqlData.getColumn(sqlData.getColumnName(i)).setWidth(0);
					sqlData.getColumn(sqlData.getColumnName(i)).setMinWidth(0);
					sqlData.getColumn(sqlData.getColumnName(i)).setMaxWidth(0);
				}
				// else {
				/*****************************/
				/* SETEAR TAMA�O DE COLUMNAS */
				/*****************************/
				// sqlData.getColumn(sqlData.getColumnName(i)).setWidth(this.tamanhoCol);
				// sqlData.getColumn(sqlData.getColumnName(i)).setMinWidth(30);
				// sqlData.getColumn(sqlData.getColumnName(i)).setMaxWidth(30);
				// }
			}
		}
		centerPane.addKeyListener(this);
		sqlData.addKeyListener(this);
		principalPane.addKeyListener(this);

		getContentPane().addKeyListener(this);

		this.add(principalPane, BorderLayout.CENTER);
	}

	private void checkSelection(boolean isFixedTable) {
		int fixedSelectedIndex = sqlData.getSelectedRow();
		int selectedIndex = sqlData.getSelectedRow();
		if (fixedSelectedIndex != selectedIndex) {
			if (isFixedTable) {
				sqlData.setRowSelectionInterval(fixedSelectedIndex, fixedSelectedIndex);
			} else {
				sqlData.setRowSelectionInterval(selectedIndex, selectedIndex);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped" + e.getKeyCode());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed" + e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_F10) {
			System.out.println("1");
			/*******************************************************/
			/************** VERIFICAR LANZAMIENTO ******************/
			/*******************************************************/
			verificarPlanillaCargada(Configuracion.CODEMPRESA, planilla.getCodPlanilla(), this.fecha);
			System.out.println("2");
			/*******************************************************/
			/************ INSERTAR NUEVO LANZAMIENTO ***************/
			/*************** EN CASO DE QUE NO EXISTA **************/
			/*******************************************************/
			if (lan == null) {
				System.out.println("3");
				lan = new Lanzamiento();
				lan.setCodEmpresa(Configuracion.CODEMPRESA);
				lan.setCodPlanilla(planilla.getCodPlanilla());
				lan.setFecha(sdf.format(date));
				/**********************************************************/
				/****** AL INSERTAR RETORNA EL CODIGO DEL MOVIMIENTO ******/
				/**********************************************************/
				lan.setCodPlanilla(lan.insertar());
				/**********************************************************/
				/************ RECORRER LOS REGISTROS - GUARDAR ************/
				/**********************************************************/
				System.out.println("4");
				DetalleLanzamiento dLan = null;
				for (int k = 0; k < sqlData.getRowCount(); k++) {
					boolean isOk = false;
					long codVariable;
					String value = null;
					int movCol;
					int indiceCodVariable = 0;

					for (int i = 0; i < sqlData.getColumnCount(); i++) {
						System.out.println(sqlData.getColumnName(i).toString());
						/******************************************************************/
						/** AL ENCONTRAR LA COLUMNA 1 EMPIEZA A INSERTAR LOS MOVIMIENTOS **/
						/******************************************************************/
						if (sqlData.getColumnName(i).toString().equals("1")) {
							isOk = true;
						}
						if (sqlData.getColumnName(i).toString().equals("RESULTADO")) {
							isOk = false;
						}
						if (sqlData.getColumnName(i).toString().equals("COD_VARIABLE")) {
							indiceCodVariable = i;
						}
						if (isOk) {
							if (sqlData.getValueAt(k, i) == null) {
								value = "";
							} else {
								System.out.println(sqlData.getValueAt(k, i).toString());
								value = sqlData.getValueAt(k, i).toString();
							}
							codVariable = Long.parseLong(sqlData.getValueAt(k, indiceCodVariable).toString());
							movCol = Integer.parseInt(sqlData.getColumnName(i).toString());
							dLan = new DetalleLanzamiento(lan.getCodEmpresa(), lan.getCodMovimiento(), codVariable,
									movCol, value.replace(".", ","));
							dLan.insertar();
						}
					}
				}
			} else {
				System.out.println("SE DEBE ACTUALIZAR-.....");
				/*******************************************************/
				/** EN CASO DE QUE EXISTA ENTONCES SE DEBE ACTUALIZAR **/
				/*******************************************************/
				System.out.println("5");
				DetalleLanzamiento dLan = null;
				for (int k = 0; k < sqlData.getRowCount(); k++) {
					boolean isOk = false;
					long codVariable;
					String value = null;
					int movCol;
					int indiceCodVariable = 0;

					for (int i = 0; i < sqlData.getColumnCount(); i++) {
						/******************************************************************/
						/** AL ENCONTRAR LA COLUMNA 1 EMPIEZA A INSERTAR LOS MOVIMIENTOS **/
						/******************************************************************/
						if (sqlData.getColumnName(i).toString().equals("1")) {
							isOk = true;
						}
						if (sqlData.getColumnName(i).toString().equals("RESULTADO")) {
							isOk = false;
						}
						if (sqlData.getColumnName(i).toString().equals("COD_VARIABLE")) {
							indiceCodVariable = i;
						}
						if (isOk) {
							if (sqlData.getValueAt(k, i) == null) {
								value = "";
							} else {
								System.out.println(sqlData.getValueAt(k, i).toString());
								value = sqlData.getValueAt(k, i).toString();
							}
							System.out.println(Long.parseLong(sqlData.getValueAt(k, indiceCodVariable).toString()));
							codVariable = Long.parseLong(sqlData.getValueAt(k, indiceCodVariable).toString());
							System.out.println(Integer.parseInt(sqlData.getColumnName(i).toString()));
							movCol = Integer.parseInt(sqlData.getColumnName(i).toString());

							dLan = new DetalleLanzamiento(lan.getCodEmpresa(), lan.getCodMovimiento(), codVariable,
									movCol, value.replace(".", ","));
							dLan.actualizar();
						}
					}
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_F12) {
			System.out.println("keyPressed" + e.getKeyCode());
			/* ANTES DE CERRAR SE DEBE PREGUNTAR */
			/* SI SE DESEA GUARDAR LOS REGISTROS. */
			this.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("keyPressed" + e.getKeyCode());
	}

	public int getTamanhoCol() {
		return tamanhoCol;
	}

	public void setTamanhoCol(int tamanhoCol) {
		this.tamanhoCol = tamanhoCol;
	}
}