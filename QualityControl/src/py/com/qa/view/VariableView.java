package py.com.qa.view;

import py.com.qa.configs.Configuracion;

public class VariableView extends AbstractInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VariableView() {
		super("Variable");
		// initialize();

		// ...Create the GUI and put it in the window...

		// ...Then set the window size or call pack...
		setSize(500, 500);

		// Set the window's location.
		setLocation(Configuracion.XOFFSET * Configuracion.OPENFRAMECOUNT,
				Configuracion.YOFFSET * Configuracion.OPENFRAMECOUNT);
	}

}
