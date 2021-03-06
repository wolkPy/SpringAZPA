package py.com.qa.view;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import py.com.qa.configs.Configuracion;

public abstract class AbstractInternalFrame extends JInternalFrame implements InternalFrameListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractInternalFrame(String nameFrame) {
		super(nameFrame, true, // resizable
				true, // closable
				true, // maximizable
				true);// iconifiable

		// ...Then set the window size or call pack...
		addInternalFrameListener(this);
		setSize(900, 800);
		// Set the window's location.
		this.toFront();
		setLocation(Configuracion.XOFFSET * Configuracion.OPENFRAMECOUNT,
				Configuracion.YOFFSET * Configuracion.OPENFRAMECOUNT);
		this.setFrameIcon(
				new ImageIcon(this.getClass().getResource("/images/logo" + Configuracion.CODEMPRESA + ".jpg")));
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		++Configuracion.OPENFRAMECOUNT;
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		if (Configuracion.OPENFRAMECOUNT > 0) {
			--Configuracion.OPENFRAMECOUNT;
		}
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

}