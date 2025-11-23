package ar.edu.unlu.zombi.modelo.entidades;

import ar.edu.unlu.zombi.modelo.enumerados.TipoCarta;

public class Carta {
    private TipoCarta tipo;
    private Integer numero;

    public Carta(TipoCarta tipoCarta, Integer numero) {
        this.tipo = tipoCarta;
        this.numero = numero;
    }

    public TipoCarta getTipoCarta() {
        return tipo;
    }

    public Integer getNumero() {
        return numero;
    }

    public String valorCarta() {
        return(this.tipo == null) ? ("Comodin") : ("Numero "+ this.numero.toString() + " Tipo: " + this.tipo.toString());
    }

    public boolean esComodin() {
        return (this.tipo == TipoCarta.COMODIN);
    }

    public boolean esPareja(Carta otraCarta) {
        return (this.numero == otraCarta.numero);
    }

}
