package ar.edu.unlu.zombi.modelo;

import java.util.List;
import java.util.NoSuchElementException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.UUID;

import ar.edu.unlu.zombi.interfaces.IModelo;
import ar.edu.unlu.zombi.modelo.entidades.Carta;
import ar.edu.unlu.zombi.modelo.entidades.Jugador;
import ar.edu.unlu.zombi.modelo.entidades.Mazo;
import ar.edu.unlu.zombi.modelo.enumerados.EventoGeneral;
import ar.edu.unlu.zombi.modelo.enumerados.EventoJugador;
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
	public void agregarObservador(IObservadorRemoto observadorRemoto) throws RemoteException {
		this.observadores.add((IObservadorRemoto) observadorRemoto);
	}
	
	@Override
	public void notificarObservadores(Object objeto) throws RemoteException {
		for(IObservadorRemoto observadorRemoto: observadores) {
			observadorRemoto.actualizar(null, objeto);
		}
	}
	
	@Override
	public java.util.List<Jugador> obtenerJugadores() throws RemoteException {
		return this.jugadores;
	}

	@Override
	public int obtenerPosicionJugadorActual() throws RemoteException {
		return this.posicionJugadorActual;
	}

	@Override
	public Stack<Carta> obtenerMazoPaarejas() throws RemoteException {
		return this.mazoParejas;
	}

	@Override
	public int obtenerCantidadMinimaJugadores() throws RemoteException {
		return Modelo.MINIMO_JUGADORES;
	}

	@Override
	public int obtenerCantidadMaximaJugadores() throws RemoteException {
		return Modelo.MAXIMO_JUGADORES;
	}

	@Override
	public Boolean esCantidadJugadoresDefinida() throws RemoteException {
		return !(cantidadJugadoresActuales ==-1);
	}

	@Override
	public UUID obtenerJugadorActualId() throws RemoteException {
		return jugadores.get(posicionJugadorActual).getID();
	}
	
	private Jugador obtenerJugadorActual() {
		return jugadores.get(posicionJugadorActual);
	}

	@Override
	public Mensaje definirCantidadJugadoresMaximo(Integer cantidadJugadores) throws RemoteException {
		if(cantidadJugadores < MINIMO_JUGADORES){
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.ERROR_LIMITE_MINIMO_JUGADORES)
					.build();}
		
		if(cantidadJugadores > MAXIMO_JUGADORES){
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.ERROR_LIMITE_MAXIMO_JUGADORES)
					.build();}
		
		this.cantidadJugadoresActuales = cantidadJugadores;
		
		return new Mensaje
				.Builder()
				.put("EventoJugador", EventoJugador.MOSTRAR_PANTALLA_CARGA_NOMBRE_JUGADOR)
				.build();
		
		}

	/*METODOS PARA agregarNuevoJugador*/
	private Boolean validarNombreJugador(String nombreNuevoJugador) {
		if(nombreNuevoJugador == null || nombreNuevoJugador.isBlank()) {
			return false;
		}
		return jugadores
		        .stream()//la lista de jugadores se cvuelve un flujo
		        .map(jugador -> jugador.getNombre().trim().toLowerCase())
		        .noneMatch(nombre -> nombre.equals(nombreNuevoJugador));// si machea devuelve FALSE
	}
	
	private void repartirCartas() {
		while(!this.mazo.esVacio()) {
			for (Jugador jugador :jugadores) {
				if(this.mazo.esVacio()) {
					break;
				}
				jugador.agregarCarta(mazo.getCartaTope());
			}
		}
	}
	
	private void asignarManosAJugadores() {
		LinkedList<LinkedList<Carta>> listaDeManos;
		listaDeManos = mazo.repartirCartas(cantidadJugadoresActuales);
		
		for(int i = 0; i < cantidadJugadoresActuales ;i++) {
			Jugador jugadorActual = jugadores.get(i);
			List<Carta> manoAsignada = listaDeManos.get(i);
			jugadorActual.recibirMano(manoAsignada);
		}	
	}
	
	private void descarteinicialJugadores() {
		for(Jugador jugador : jugadores) {
			mazoParejas.addAll(jugador.descartar());
		}
	}
	//INICIO
	//Controla la cantidad de jugadores, si es la correcta arranca el juego
	@Override
	public Mensaje agregarNuevoJugador(String nombreNuevoJugador) throws RemoteException {
		if (this.jugadores.size() == Modelo.MAXIMO_JUGADORES) {
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.ERROR_LIMITE_MAXIMO_JUGADORES)
					.build();
		}
		
		if (!this.validarNombreJugador(nombreNuevoJugador)) {
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.ERROR_VALIDACION_NOMBRE_JUGADOR)
					.build();
		}
		Jugador jugador = new Jugador(nombreNuevoJugador);
		jugadores.add(jugador);
		//CASO MENSAJE
		if (this.jugadores.size() < cantidadJugadoresActuales) {
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.MOSTRAR_PANTALLA_ESPERA_JUGADORES)
					.put("id", jugador.getID())
					.build();
		}
		
		//ACA ARRANCA EL JUEGO
		mazo = new Mazo();
		mazoParejas = new Stack();
		repartirCartas();
		//asignarManosAJugadores(); respetando encapsulamiento
		
		descarteinicialJugadores();
		this.notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_NOMBRES_JUGADORES_CARGADOS);
		return new Mensaje
				.Builder()
				.put("EventoJugador", EventoJugador.EVENTO_GLOBAL)
				.put("id", jugador.getID())
				.build();
	}

	@Override
	public Mensaje iniciarRonda() throws RemoteException {
		if((jugadoresEnEspera+1) < cantidadJugadoresActuales) {
			jugadoresEnEspera++;
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.MOSTRAR_PANTALLA_ESPERA_JUGADORES)
					.build();
		}
		jugadoresEnEspera = 0;
		this.notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_RONDA_JUGADORES);
		return new Mensaje
				.Builder()
				.put("EventoJugador", EventoJugador.EVENTO_GLOBAL)
				.build();
	}

	@Override
	public java.util.List<String> obtenerNombresJugadores() throws RemoteException {
		return jugadores
		        .stream()//la lista de jugadores se cvuelve un flujo
		        .map(jugador -> jugador.getNombre())
				.toList();
	}

	@Override
	public String obtenerNombreJugadorActual() throws RemoteException {
		return obtenerJugadorActual().getNombre();
	}

	@Override
	public java.util.List<Carta> obtenerUltimasDosCartasMazoParejas() throws RemoteException {
		if (mazoParejas.size() >= 2) {
			Carta ultimaCarta = mazoParejas.getLast();
			Carta penultimaCarta = mazoParejas.get(mazoParejas.size()-1);
			return List.of(ultimaCarta,penultimaCarta);
		}
		return List.of();
	}
	
	private Jugador obtenerJugador(UUID id) {
		return jugadores.stream()
				.filter(jugador -> jugador.getID().equals(id))
				.findFirst()
				.orElseThrow(() ->
					new NoSuchElementException("No existe jugador con id"+id)
				);			
	}
	
	@Override
	public java.util.List<Carta> obtenerMazoJugador(UUID id) throws RemoteException {
		return obtenerJugador(id).getMazo();
	}
	
	//Cantidad
	private int posicionJugadorDerecha() {
		int posicionJugadorDerecha = ((posicionJugadorActual - 1) != -1)? (posicionJugadorActual - 1): (cantidadJugadoresActuales - 1);
		
		while(!jugadores.get(posicionJugadorDerecha).getEsActivo()) {
			posicionJugadorDerecha = ((posicionJugadorActual - 1) != -1)? (posicionJugadorActual - 1): (cantidadJugadoresActuales - 1);
		}
		return posicionJugadorDerecha;
	}
	//OBTENER JUGADOR DERECHA
	private Jugador obtenerJugadorDerecha() {
		return jugadores.get(posicionJugadorDerecha());
	}
	
	@Override
	public int ObtenerCantidadCartasJugadorDerecha() throws RemoteException {
		return obtenerJugadorDerecha().getMazo().size();
	}
	
	//QUITAR CARTA A JUGADOR DERECHA 
	private Integer siguientePosicionJugadorActivo(Integer posicionActual) {
		Integer siguientePosicion = (posicionActual + 1) % cantidadJugadoresActuales;
		while (!jugadores.get(siguientePosicion).getEsActivo()) {
			siguientePosicion = ((posicionActual + 1) % cantidadJugadoresActuales);
		}
		return siguientePosicion;
	}
	
	private Boolean hayMasDeUnJugadorActivo() {
		long activos = jugadores.stream()
				.filter(Jugador::getEsActivo)
				.count();
		return activos > 1;
	}
	
	
	@Override
	public Mensaje tomarCartaJugadorDerecha(int indiceCartaJugadorDerecha) throws RemoteException {
		Carta cartaAQuitar = obtenerJugadorDerecha().getMazo().get(indiceCartaJugadorDerecha-1);
		obtenerJugadorDerecha().quitarCarta(cartaAQuitar);//jugador de la derecha se deshace de la carta
		
		obtenerJugadorActual().agregarCarta(cartaAQuitar);//jugador actual se queda con la carta
		mazoParejas.addAll(obtenerJugadorActual().descartar());// descartar puede retornar null o las 2 cartas descartadas
		
		if(obtenerJugadorActual().getMazo().isEmpty()) { //GANA JUGADOR ACTUAL
			obtenerJugadorActual().setEsActivo(false);
		}
		
		if(hayMasDeUnJugadorActivo()) {//busco si quedan mas jugadores en juego para no terminar la ronda
			this.posicionJugadorActual = siguientePosicionJugadorActivo(posicionJugadorActual);
			notificarObservadores(EventoGeneral.CONTINUAR_SIGUIENTE_TURNO_RONDA);
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.EVENTO_GLOBAL)
					.build();
		}
		
		notificarObservadores(EventoGeneral.FINAL_RONDA); //SI LLEGO ACA ES PORQUE Termino la ronda
		return new Mensaje
				.Builder()
				.put("EventoJugador", EventoJugador.EVENTO_GLOBAL)
				.build();
	}
	//FINAL DE RONDA
	
	//PERDEDOR
	@Override
	public String obtenerNombreJugadorPerdedor() throws RemoteException {
		Jugador ultimoJugadorActivo = jugadores.stream()
				.filter(Jugador::getEsActivo)
				.findFirst()
				.orElseThrow(() ->
				new NoSuchElementException("No hay jugadores activos"));
		return ultimoJugadorActivo.getNombre();
	}
	
	private void resetearJuego() throws RemoteException {
		this.cantidadJugadoresActuales = -1;
		this.jugadores.clear();
		this.mazoParejas.clear();
		this.posicionJugadorActual = 0;
		this.jugadoresEnEspera = 0;
		if(hayPartidaPersistida()) {
			this.administradorSerializacion.eliminarPartida();
		}				
	}
	
	@Override
	public Mensaje finalizarJuego() throws RemoteException {
		if ((jugadoresEnEspera ++) < cantidadJugadoresActuales) {//REVANCHA
			jugadoresEnEspera ++;
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.MOSTRAR_PANTALLA_ESPERA_JUGADORES)
					.build();
		}
		jugadoresEnEspera = 0;//FIN DEL JUEGO IR A MENU PRINCIPAL
		resetearJuego();
		this.notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_MENU_PRINCIPAL);
		return new Mensaje
				.Builder()
				.put("EventoJugador", EventoJugador.EVENTO_GLOBAL)
				.build();
	}
	
	//SERIALIZACION
	
	@Override
	public Boolean hayPartidaPersistida() throws RemoteException {
		return this.administradorSerializacion.hayPartidaGuardada();
	}

	@Override
	public void persistirPartida() throws RemoteException {
		if(this.administradorSerializacion.persistirPartida(this)) {
			this.notificarObservadores(EventoGeneral.PARTIDA_PERSISTIDA);
		}else {
			this.notificarObservadores(EventoGeneral.ERROR_PARTIDA_PERSISTIDA);
		}
	}
	
	private void recuperarPartida() throws RemoteException {
		IModelo partidaRecuperada = this.administradorSerializacion.recuperarPartida();
	
		if (partidaRecuperada == null) {
			this.notificarObservadores(EventoGeneral.ERROR_PARTIDA_RECUPERADA);
			return;
		}
		this.jugadores = partidaRecuperada.obtenerJugadores();
		this.jugadoresAReasignar =this.jugadores;
		this.cantidadJugadoresActuales = this.jugadores.size();
		this.posicionJugadorActual = partidaRecuperada.obtenerPosicionJugadorActual();
		this.jugadoresEnEspera = 0;
		mazoParejas = partidaRecuperada.obtenerMazoPaarejas();
	}
	
	@Override
	public Mensaje continuarPartidaPersistida() throws RemoteException {
		if (!isPartidaRecuperada) {
			recuperarPartida();
		}
		isPartidaRecuperada = true;
		
		if((jugadoresEnEspera++)< cantidadJugadoresActuales) {
			jugadoresEnEspera++;
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.MOSTRAR_PANTALLA_ESPERA_JUGADORES)
					.build();
		}
		jugadoresEnEspera = 0;
		this.notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_RONDA_JUGADORES);
		return new Mensaje
				.Builder()
				.put("EventoJugador", EventoJugador.EVENTO_GLOBAL)
				.build();
	}

	@Override
	public Mensaje reasignarJugadoresPartidaPersistida(UUID id) throws RemoteException {
		Jugador jugadorAEliminar = jugadores.stream()
				.filter(jugador -> jugador.getID().equals(id))
				.findFirst()
				.orElse(null);
		
		jugadoresAReasignar.remove(jugadorAEliminar);
		
		if (!jugadoresAReasignar.isEmpty()) {
			this.notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_NOMBRES_JUGADORES_PARTIDA_RECUPERADA);
			return new Mensaje
					.Builder()
					.put("EventoJugador", EventoJugador.MOSTRAR_PANTALLA_CARGA_NOMBRE_JUGADOR)
					.build();
		}
		
		this.notificarObservadores(EventoGeneral.MOSTRAR_PANTALLA_RONDA_JUGADORES);
		return new Mensaje
				.Builder()
				.put("EventoJugador", EventoJugador.EVENTO_GLOBAL)
				.build();
	}

	@Override
	public List<Jugador> obtenerJugadoresPartidaPersistida() throws RemoteException {
		return this.jugadoresAReasignar;
	}
	
	
}
