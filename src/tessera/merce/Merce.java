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

    public TipoMerce getTipoMerce() {
        return this.tipoMerce;
    }
}
