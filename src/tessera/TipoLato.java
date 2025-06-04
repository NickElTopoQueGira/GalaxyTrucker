package tessera;

public enum TipoLato {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    /**
     * metodo per scorrere l'enum di una variabile in maniera ciclica ritornando
     * il successivo
     *
     * @return enum successivo
     */
    public TipoLato next() {
        TipoLato[] valore = TipoLato.values();
        int nextValore = (this.ordinal() + 1) % valore.length;
        return valore[nextValore];

    }

}
