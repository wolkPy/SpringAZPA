package py.com.qa.main;

import java.awt.EventQueue;

import py.com.qa.configs.Configuracion;
import py.com.qa.view.LoginView;

public class QASystem {
	public static void main(String[] args) {
		if (args.length != 0) {
			if (args[0] != null) {
				Configuracion.CODEMPRESA = args[0];
			}
			if (args[1] != null) {
				Configuracion.CODSUCURSAL = args[1];
			}	
		}
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unused")
			private LoginView window;

			public void run() {
				try {
					window = new LoginView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}