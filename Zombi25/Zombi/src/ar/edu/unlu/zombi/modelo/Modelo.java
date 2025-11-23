package ar.edu.unlu.zombi.modelo;

import java.util.List;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

import ar.edu.unlu.zombi.interfaces.IModelo;
import ar.edu.unlu.zombi.modelo.entidades.Carta;
import ar.edu.unlu.zombi.modelo.entidades.Jugador;
import ar.edu.unlu.zombi.modelo.entidades.Mazo;
import ar.edu.unlu.zombi.modelo.serializacion.AdministradorSerializacion;
import ar.edu.unlu.zombi.recursos.Mensaje;
import ar.edu.unlu.rmimvc.observer.IObservadorRemoto;
import ar.edu.unlu.rmimvc.observer.ObservableRemoto;


public class Modelo extends ObservableRemoto implements IModelo,Serializable{
	private static final long serialVersionUID = 1L;
	private static final int MINIMO_JUGADORES = 2;
	private static final int MAXIMO_JUGADORES = 4;
	
	private final ArrayList<IObservadorRemoto> observadores;
	
	private int cantidadJugadoresActuales = -1;
	private List<Jugador> jugadores;
	
	private Mazo mazo;
	private Stack<Carta> mazoParejas;
	private Integer posicionJugadorActual = 0;
	
	private int jugadoresEnEspera = 0;
	
	private AdministradorSerializacion administradorSerializacion;
	private boolean isPartidaRecuperada;
	private List<Jugador> jugadoresAReasignar;
	
	public Modelo() {
		this.observadores = new ArrayList<>();
		this.jugadores = new ArrayList<Jugador>();
		this.administradorSerializacion = new AdministradorSerializacion();
	}
	
	@Override
	public java.util.List<Jugador> obtenerJugadores() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int obtenerPosicionJugadorActual() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Stack<Carta> obtenerMazoPaarejas() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int obtenerCantidadMinimaJugadores() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int obtenerCantidadMaximaJugadores() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean esCantidadJugadoresDefinida() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UUID obtenerJugadorActualId() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mensaje definirCantidadJugadoresMaximo(Integer cantidadJugadores) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mensaje agregarNuevoJugador(String nombreNuevoJugador) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mensaje iniciarRonda() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<String> obtenerNombresJugadores() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerNombreJugadorActual() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<Carta> obtenerUltimasDosCartasMazoParejas() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.util.List<Carta> obtenerMazoJugador(UUID id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ObtenerCantidadCartasJugadorDerecha() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Mensaje tomarCartaJugadorDerecha(int indiceCartaJugadorDerecha) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenerNombreJugadorPerdedor() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mensaje finalizarJuego() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hayPartidaPersistida() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void persistirPartida() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mensaje continuarPartidaPersistida() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mensaje reasignarJugadoresPartidaPersistida(UUID id) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
