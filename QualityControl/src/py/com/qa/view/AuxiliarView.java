package py.com.qa.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class AuxiliarView {

	private JFrame frame;
	private JTextField descripcionTxt;
	private JTextField inicioTxt;
	private JTextField finTxt;
	private JTextField intervaloTxt;
	private JTextField tamanhoTxt;
	private JTextField codPlanillaTxt;
	private JLabel lblInicio;
	private JLabel finLbl;
	private JLabel intervaloIrregularLbl;
	private JLabel tamanhoLbl;
	private JTextField codPlanillaPadreTxt;
	private JTextField descripcionPadreTxt;

	/**
	 * Create the application.
	 */
	public AuxiliarView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 585, 557);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(70, 39, 458, 269);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel descripcionLbl = new JLabel("Planilla");
		descripcionLbl.setFont(new Font("Verdana", Font.PLAIN, 12));
		descripcionLbl.setBounds(25, 16, 65, 16);
		panel.add(descripcionLbl);

		codPlanillaTxt = new JTextField();
		codPlanillaTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		codPlanillaTxt.setColumns(10);
		codPlanillaTxt.setBounds(102, 13, 48, 22);
		panel.add(codPlanillaTxt);

		descripcionTxt = new JTextField();
		descripcionTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		descripcionTxt.setBounds(150, 13, 279, 22);
		panel.add(descripcionTxt);
		descripcionTxt.setColumns(10);

		intervaloIrregularLbl = new JLabel("Intervalo");
		intervaloIrregularLbl.setFont(new Font("Verdana", Font.PLAIN, 12));
		intervaloIrregularLbl.setBounds(25, 154, 65, 16);
		panel.add(intervaloIrregularLbl);

		JRadioButton intervaloIrregularRdbtn = new JRadioButton("Intervalo Irregular");
		intervaloIrregularRdbtn.setFont(new Font("Verdana", Font.PLAIN, 12));
		intervaloIrregularRdbtn.setSelected(true);
		intervaloIrregularRdbtn.setBounds(102, 79, 160, 25);
		panel.add(intervaloIrregularRdbtn);

		JRadioButton intervaloRegularRdbtn = new JRadioButton("Intervalo Regular");
		intervaloRegularRdbtn.setFont(new Font("Verdana", Font.PLAIN, 12));
		intervaloRegularRdbtn.setBounds(292, 79, 137, 25);
		panel.add(intervaloRegularRdbtn);

		lblInicio = new JLabel("Inicio");
		lblInicio.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblInicio.setBounds(25, 116, 56, 16);
		panel.add(lblInicio);

		inicioTxt = new JTextField();
		inicioTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		inicioTxt.setBounds(102, 113, 116, 22);
		panel.add(inicioTxt);
		inicioTxt.setColumns(10);

		finLbl = new JLabel("Fin");
		finLbl.setFont(new Font("Verdana", Font.PLAIN, 12));
		finLbl.setBounds(245, 113, 56, 16);
		panel.add(finLbl);

		finTxt = new JTextField();
		finTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		finTxt.setColumns(10);
		finTxt.setBounds(313, 113, 116, 22);
		panel.add(finTxt);

		intervaloTxt = new JTextField();
		intervaloTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		intervaloTxt.setColumns(10);
		intervaloTxt.setBounds(102, 151, 48, 22);
		panel.add(intervaloTxt);

		tamanhoLbl = new JLabel("Tama\u00F1o");
		tamanhoLbl.setFont(new Font("Verdana", Font.PLAIN, 12));
		tamanhoLbl.setBounds(25, 189, 56, 16);
		panel.add(tamanhoLbl);

		tamanhoTxt = new JTextField();
		tamanhoTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		tamanhoTxt.setColumns(10);
		tamanhoTxt.setBounds(102, 186, 48, 22);
		panel.add(tamanhoTxt);

		JButton guardarBtt = new JButton("Guardar");
		guardarBtt.setIcon(new ImageIcon(AuxiliarView.class.getResource("/filesave.gif")));
		guardarBtt.setBounds(102, 221, 127, 35);
		panel.add(guardarBtt);

		JButton cancelarBtt = new JButton("Cancelar");
		cancelarBtt.setIcon(new ImageIcon(AuxiliarView.class.getResource("/cancel.gif")));
		cancelarBtt.setBounds(245, 221, 127, 35);
		panel.add(cancelarBtt);

		JCheckBox chckbxActivo = new JCheckBox("Activo?");
		chckbxActivo.setBounds(313, 185, 113, 25);
		panel.add(chckbxActivo);

		codPlanillaPadreTxt = new JTextField();
		codPlanillaPadreTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		codPlanillaPadreTxt.setColumns(10);
		codPlanillaPadreTxt.setBounds(102, 48, 48, 22);
		panel.add(codPlanillaPadreTxt);

		descripcionPadreTxt = new JTextField();
		descripcionPadreTxt.setEnabled(false);
		descripcionPadreTxt.setFont(new Font("Verdana", Font.PLAIN, 12));
		descripcionPadreTxt.setColumns(10);
		descripcionPadreTxt.setBounds(150, 48, 279, 22);
		panel.add(descripcionPadreTxt);

		JLabel codPlanillaPadreLbl = new JLabel("Superior");
		codPlanillaPadreLbl.setToolTipText("Nivel Superior");
		codPlanillaPadreLbl.setFont(new Font("Verdana", Font.PLAIN, 12));
		codPlanillaPadreLbl.setBounds(25, 54, 65, 16);
		panel.add(codPlanillaPadreLbl);
	}
}
