package ar.edu.unlu.zombi.modelo.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Jugador {
	//private UUID id;
    private String nombre;
    private List<Carta> mano;
    private Boolean esActivo;

    public Jugador(String nombre) {
        //this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.mano = new ArrayList<Carta>();
        this.esActivo = true;
    }

    public String getNombre() {
        return this.nombre;
    }

    public List<Carta> getMazo() {
        return this.mano;
    }

    public Boolean getEsActivo() {
        return this.esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }
    //agregar carta
    public void agregarCarta(Carta carta) {
        this.mano.add(carta);
    }
    //metodos sobrecargados
    //descartar carta de una posicion. Se usa cuando te la saca un jugador
    public Carta quitarCarta(int posicion) {
        return mano.remove(posicion);
    }
    //descartar carta.
    public void quitarCarta(Carta carta) {
        mano.remove(carta);
    }

    public Boolean soloQuedaComodinEnMazo() {
        return (this.mano.size() == 1) && (this.mano.getFirst().esComodin());
    }

    //Descarte, decuelve lista de cartas parejas
    public List<Carta> descartar() {
        if (mano.isEmpty()) {
            return Collections.emptyList();
        }

        List<Carta> manoAuxiliar = new ArrayList<Carta>(mano);//copia de mazo
        manoAuxiliar.sort(Comparator.comparingInt(Carta::getNumero));//ordeno la lista menor a mayor

        List<Carta> parejas = new ArrayList<Carta>();//lista de cartas parejas a retornar

        int i = 0;
        while (i < manoAuxiliar.size() - 1) {
            Carta actual = manoAuxiliar.get(i);
            if (actual.esComodin()) {
                i++;
                continue;
            }
            Carta siguiente = manoAuxiliar.get(i + 1);
            //si no es Comodin y la carta siguiente tiene el mismo numero
            if (!siguiente.esComodin() && actual.getNumero().equals(siguiente.getNumero())) {
                parejas.add(actual);
                parejas.add(siguiente);//las sumo a la lista de parejas
                i += 2;// incremento i para que no tome la posicion de 'siguiente'
            } else {
                i++;//si es comodin o no tienen el mismo numero sigo
            }
        }
        //descarto todas las cartas parejas de mi mano
        mano.removeAll(parejas);
        //devuelvo las parejas que me descarte
        return parejas;
    }
}
