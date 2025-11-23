package ar.edu.unlu.zombi.vista.administradores;

import java.util.LinkedHashMap;
import java.util.Map;

import ar.edu.unlu.zombi.interfaces.IControlador;
import ar.edu.unlu.zombi.interfaces.IPanel;
import ar.edu.unlu.zombi.interfaces.IVista;
import ar.edu.unlu.zombi.vista.consola.JFramePrincipal;
import ar.edu.unlu.zombi.vista.consola.paneles.PanelMenuPrincipal;

public class AdministradorVistaConsola implements IVista {
	private final Map<String, Object> panels = new LinkedHashMap<>();
	private IControlador controlador;
    private JFramePrincipal framePrincipal;
    private IPanel panelActual;
    private PanelMenuPrincipal panelMenuPrincipal;
	
    public AdministradorVistaConsola () {
    	this.framePrincipal = new JFramePrincipal();
    	
    	panelMenuPrincipal = new PanelMenuPrincipal(this,framePrincipal);
    	
    	addPanel("Menu Principal",panelMenuPrincipal);
    	
    	showFrame();
    }
 
    public void showFrame() { framePrincipal.showFrame();}
    
    public void addPanel(String nombre, Object panel) {panels.put(nombre, panel);}
    
    public void showPanel(String nombre ) {
    	IPanel panel = (IPanel) panels.get(nombre);
    	panelActual = panel;
    	panel.mostrarPanel();
    }
	
	@Override
	public void setControlador(IControlador controlador) {this.controlador = controlador;	}

	@Override
	public void mostrarPanelMenuPrincipal() {
		System.out.println("mostrarPanelMenuPrincipal");
        showPanel("Menu Principal");
	}

	@Override
	public void IniciarJuego() {
		controlador.iniciarJuego();
	}

	@Override
	public void SalirJuego() {
		System.exit(0);
	}

	@Override
	public void mostrarPanelDefinirCantidadJugadores() {
		
		
	}

	@Override
	public boolean hayPartidaPersistida() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		panelActual.mostrarMensajeError(mensaje);
        panelActual.mostrarPanel();
	}
}
