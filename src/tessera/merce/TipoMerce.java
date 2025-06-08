package tessera.merce;

public enum TipoMerce {
    MERCE_ROSSA		(4, "\033[1;91m" + "[]" + "\u001B[0m"), // rosso 
    MERCE_GIALLA	(2, "\033[1;93m" + "[]" + "\u001B[0m"), // giallo 
    MERCE_VERDE		(3, "\033[1;92m" + "[]" + "\u001B[0m"), // verde 
    MERCE_BLU		(1, "\033[1;94m" + "[]" + "\u001B[0m");	// blu

    private final int valore;
    private final String nome;

    /**
     * costruttore
     *
     * @param valore
     * @param name
     */
    TipoMerce(int valore, String nome) {
        this.valore = valore;
        this.nome = nome;
    }

    public int getValore() {
        return this.valore;
    }

    /**
     * ritorna il valore dell'enum corrispondente al colore della merce rappresentato con simbolo []
     */
    @Override
    public String toString() {
        return this.nome;
    }
}
