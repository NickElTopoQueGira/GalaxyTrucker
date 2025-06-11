package tessera.merce;

public class Merce {

    private final TipoMerce tipoMerce;

    /**
     * costruttore
     *
     * @param tipoMerce
     */
    public Merce(TipoMerce tipoMerce) {
        this.tipoMerce = tipoMerce;
    }

    /**
     * getter tipo di merce (colore)
     * @return enum TipoMerce
     */
    public TipoMerce getTipoMerce() {
        return this.tipoMerce;
    }
}
