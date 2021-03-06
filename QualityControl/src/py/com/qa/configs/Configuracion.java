package py.com.qa.configs;

import java.sql.Connection;

import com.toedter.calendar.JDateChooser;

public abstract class Configuracion {
	/*
	 * @autor = lolmedo
	 * 
	 * @fecha = 15/04/2018
	 * 
	 * @NOMBRESISTEMA = nombre del sistema
	 * 
	 * @QAICON = direccion del icono del sistema
	 * 
	 * @CON = variable global para guardar la conexi�n al sistema
	 * 
	 */
	public static String CODEMPRESA;
	public static String CODSUCURSAL;
	public final static String NOMBRESISTEMA = "Sistema de Control de Calidad - Versi�n 1.0";
	public final static String QAICON = "/images/logo" + CODEMPRESA + ".jpg";

	// System.getenv("QA_HOME"); "/Buscar.gif"
	public static Connection CON;
	// Se guarda usuario y contrase�a para hacer las actualizaciones y recorridos
	// por tablas.
	public static String USUARIO;
	public static String PASS;
	public static String TIPMODULO = "DESIGN"; // al arrancar el sistema es Definicion
	// NEW = CARGA DE NUEVOS MOVIMIENTOS DIARIOS
	// QUERY = CONSULTA
	// CHANGED = MODIFICACION
	public static String QUERY_PLANILLA = "SELECT cod_empresa,\r\n" + "       cod_sucursal,\r\n"
			+ "       cod_planilla,\r\n" + "       descripcion,\r\n" + "       cod_planilla_padre,\r\n"
			+ "       orden,\r\n" + "       estado,\r\n" + "       decode((select count(*)\r\n"
			+ "                from qa_planilla b\r\n" + "               where b.cod_empresa = a.cod_empresa\r\n"
			+ "                 and b.cod_sucursal = a.cod_sucursal\r\n"
			+ "                 and b.cod_planilla_padre = a.cod_planilla),\r\n" + "              0,\r\n"
			+ "              'S',\r\n" + "              'N') consulta\r\n" + "  FROM qa_planilla a\r\n"
			+ "  WHERE a.cod_empresa = ? and a.estado = 'A' and a.cod_planilla_padre = 0 \r\n"
			+ " START WITH a.cod_planilla_padre = 0 \r\n" + "CONNECT BY PRIOR a.cod_planilla = a.cod_planilla_padre\r\n"
			+ " ORDER SIBLINGS BY to_number(a.orden) \r\n";
	/* CON ESTA CONSTANTE SE SABE CUANTAS VENTANAS INTERNAS ESTAN ABIERTAS. */
	public static int OPENFRAMECOUNT = 0;
	/* POSICION EN EL DESKTOP */
	public static final int XOFFSET = 30, YOFFSET = 30;
	public static JDateChooser FILTER_DATE_CHOOSER;
	/* POSICION EN JTABLE */

	public static int POSCOLTIPOVAR;
	public static int POSCOLESFORMULA;
	public static int POSCOLMEDIDA;
	public static int POSCOLORDEN;

}