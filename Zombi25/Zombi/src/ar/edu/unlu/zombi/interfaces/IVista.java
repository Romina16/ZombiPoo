package ar.edu.unlu.zombi.interfaces;

public interface IVista {
	void setControlador(IControlador controlador);
    //int obtenerCantidadMinimaJugadores();
    //int obtenerCantidadMaximaJugadores();
    void mostrarPanelMenuPrincipal();
    void IniciarJuego();
    void SalirJuego();
    void mostrarPanelDefinirCantidadJugadores();
    boolean hayPartidaPersistida();
    void mostrarMensajeError(String mensaje);
}
