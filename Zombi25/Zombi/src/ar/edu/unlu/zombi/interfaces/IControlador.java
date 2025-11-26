package ar.edu.unlu.zombi.interfaces;

import java.util.List;
import java.util.UUID;

import ar.edu.unlu.zombi.modelo.DTO.CartaDTO;
import ar.edu.unlu.zombi.modelo.DTO.JugadorDTO;

public interface IControlador {
    /**/
    void setVista(IVista vista);
    //inicio del juego
    int obtenerCantidadMinimaJugadores();
    int obtenerCantidadMaximaJugadores();
    void mostrarPanelMenuPrincipal();
    
    void iniciarJuego();
    void mostrarPanelDefinirCantidadJugadores();
    void obtenerDatosCargaCantidadJugadores(String cantidadJugadores);
    void mostrarPanelEsperaJugadores();
    void mostrarPanelCargaNombreJugador();
    void obtenerDatosCargaNombreJugador(String nombreJugador);
    void mostrarPanelNombresJugadoresCargados();
    List<String> obtenerNombresJugadores();
    //inicio de ronda
    void iniciarRonda();
    void mostrarPanelRondaJugador();
    String obtenerNombreJugadorActual();
    List<CartaDTO> obtenerUltimasDosCartasMazoParejas();
    List<CartaDTO> obtenerMazoJugador();
    int obtenerCantidadCartasJugadoresDerecha();
    void obtenerDatosCargaRondaJugadorTurno(String indiceCartaJugadorDerecha);
    //fin del juego
    void mostrarPanelFinalRonda();
    String obtenerNombreJugadorPerdedor();
    void volverAlMenuPrincipal();
    
    //persistencia
    void PersistirPartida();
    void continuarPartidaPersistida();
    void mostrarPanelNombresJugadoresPartidaPersistida();
    void mostrarPanelPartidaPersistida();
    Boolean hayPartidaPersistida();
    List<JugadorDTO> obtenerJugadoresPartidaPersistida();
    void obtenerDatosCargaJugadorPartidaPersistida(UUID id);
 
    
}
