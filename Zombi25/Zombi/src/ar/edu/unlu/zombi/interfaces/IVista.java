package ar.edu.unlu.zombi.interfaces;

import java.util.List;
import java.util.UUID;

import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;

public interface IVista {
	void setControlador(IControlador controlador);
    int obtenerCantidadMinimaJugadores();
    int obtenerCantidadMaximaJugadores();
    void mostrarPanelMenuPrincipal();
    void IniciarJuego();
    void SalirJuego();
    //
    void mostrarPanelDefinirCantidadJugadores();
    void obtenerDatosCargaCantidadJugadores(String cantidadJugadores);
    //
    void mostrarPanelEsperaJugadores();
    //
    void mostrarPanelCargaNombreJugador();
    void obtenerDatosCargaNombreJugador(String nombreJugador);
    void mostrarPanelNombresJugadoresCargados();
    List<String> obtenerNombresJugadores();
    //
    void iniciarRonda();
    void mostrarPanelRondaJugadorTurno();
    void mostrarPanelRondaJugadorObservador();
    String obtenerNombreJugadorActual();
    List<CartaDTO> obtenerUltimasDosCartasMazoParejas();
    List<CartaDTO> obtenerMazoJugador();
    int ObtenerCantidadCartasJugadorDerecha();
    void obtenerDatosCargaRondaJugadorTurno(String indiceCartJugadorDerecha);
    void mostrarPanelFinalRonda();
    String obtenerNombreJugadorPerdedor();
    void volverAlMenuPrincipal();
    //Persistencia
    void persistirPartida();
    void mostrarPanelPartidaPersistida();
    Boolean hayPartidaPersistida();
    void continuarPartidaPersistida();
    void mostrarPanelNombresJugadoresPartidaPersistida();
    List<JugadorDTO> obtenerJugadoresPartidaPersistida();
    void obtenerDatosCargaJugadorPartidaPersistida(UUID id);
    //
    void mostrarMensajeError(String mensaje);
}
