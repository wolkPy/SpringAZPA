package py.com.qa.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import py.com.qa.conectivity.ManejadorConexiones;
import py.com.qa.configs.Configuracion;
import java.awt.Toolkit;

public class LoginView implements ActionListener {

	private JFrame loginFrm;

	/**
	 * Create the application.
	 */
	public LoginView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		loginFrm = new JFrame();
		loginFrm.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(LoginView.class.getResource("/images/logo" + Configuracion.CODEMPRESA + ".jpg")));

		loginFrm.setAutoRequestFocus(false);
		loginFrm.setTitle(Configuracion.NOMBRESISTEMA);
		loginFrm.setResizable(false);
		loginFrm.setBounds(100, 100, 330, 183);
		loginFrm.setLocationRelativeTo(null);
		loginFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// panel

		JPanel panel = new JPanel();
		loginFrm.getContentPane().add(panel);
		inicializarComponentes(panel);

		loginFrm.setVisible(true);
	}

	/*
	 * Initialize the contents of the panel.
	 */
	private void inicializarComponentes(JPanel panel) {
		panel.setLayout(null);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		// etiqueta usuario
		JLabel userLbl = new JLabel("Usuario");
		userLbl.setBounds(40, 10, 80, 25);
		panel.add(userLbl);

		// campo usuario
		JTextField userTxt = new JTextField(20);
		userTxt.setBounds(128, 10, 160, 25);
		panel.add(userTxt);

		// etiqueta contrase�a
		JLabel passLbl = new JLabel("Contrase�a");
		passLbl.setBounds(40, 47, 80, 25);
		panel.add(passLbl);
		// campo contrase�a
		JPasswordField passTxt = new JPasswordField(20);
		passTxt.setBounds(128, 47, 160, 25);
		panel.add(passTxt);
		// boton Ingresar
		JButton loginBtt = new JButton("Ingresar");
		loginBtt.setBounds(30, 80, 120, 25);
		panel.add(loginBtt);
		// loginBtt.addActionListener(new Action("LOGIN"));
		loginBtt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				/*
				 * SE INTENTA CREAR LA CONEXI�N A BD.
				 */
				try {
					char[] arrayC = passTxt.getPassword();
					String pass = new String(arrayC);
					Configuracion.CON = ManejadorConexiones.obtenerConexionORA(userTxt.getText(), pass);
					Configuracion.USUARIO = userTxt.getText().toUpperCase();
					Configuracion.PASS = pass;
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(panel, "Nombre de usuario/contase�a no v�lidos", "Conexi�n Denegada",
							JOptionPane.ERROR_MESSAGE);
				}
				if (Configuracion.CON != null) {
					loginFrm.setVisible(false);
					JFrame.setDefaultLookAndFeelDecorated(true);
					// Create and set up the window.
					PrincipalView frame = new PrincipalView();
					frame.setIconImage(Toolkit.getDefaultToolkit()
							.getImage(LoginView.class.getResource("/images/logo" + Configuracion.CODEMPRESA + ".jpg")));
					frame.setAutoRequestFocus(false);
					frame.setTitle(Configuracion.NOMBRESISTEMA);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}
			}
		});

		// boton salir (cancelar)
		JButton salirBtt = new JButton("Cancelar");
		salirBtt.setBounds(170, 80, 120, 25);
		salirBtt.setActionCommand("SALIR");
		salirBtt.addActionListener(this);
		panel.add(salirBtt);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("SALIR".equals(e.getActionCommand())) {
			quit();
		}
	}

	private void quit() {
		System.exit(0);
	}
}