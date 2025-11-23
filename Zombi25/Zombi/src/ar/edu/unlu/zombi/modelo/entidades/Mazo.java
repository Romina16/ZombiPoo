package ar.edu.unlu.zombi.modelo.entidades;

import ar.edu.unlu.zombi.modelo.enumerados.TipoCarta;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Stack;

public class Mazo {
	private static final int VALOR_MIN = 1;
    private static final int VALOR_MAX = 13;
    private Stack<Carta> mazo = new Stack<Carta>();

    public Mazo() {
        this.setearMazo();
        this.mezclarMazo();
    }

    public void setearMazo(){
        for (TipoCarta palo : EnumSet.complementOf(EnumSet.of(TipoCarta.COMODIN ))){
            for (int valor =  VALOR_MIN; valor <= VALOR_MAX; valor++){
                mazo.add(new Carta(palo, valor));
            }
        }
        mazo.add(new Carta(TipoCarta.COMODIN, 0));
    }

    public void mezclarMazo() {
        Collections.shuffle(this.mazo);
    }

    public Boolean esVacio() {
        return mazo.isEmpty();
    }

    public Carta getCartaTope() {
        return mazo.pop();
    }

    public String getMazoString() {
        StringBuilder resultado = new StringBuilder();

        for (Carta carta : this.mazo) {
            resultado.append(carta.toString()).append(", ");
        }

        return resultado.toString();
    }
}
