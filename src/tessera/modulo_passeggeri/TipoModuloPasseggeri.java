package tessera.modulo_passeggeri;

public enum TipoModuloPasseggeri {
    MODULO_EQUIPAGGIO("\033[1;96m" + "cosmonauti" + "\u001B[0m"), //ciano
    MODULO_ALIENO_VIOLA("\033[1;95m" + "alieno viola" + "\u001B[0m"), //viola
    MODULO_ALIENO_MARRONE("\033[1;93m" + "alieno marrone" + "\u001B[0m"); //giallo;

    private final String nome;

    /**
     * costruttore
     *
     * @param tipo
     */
    TipoModuloPasseggeri(String nome) {
        this.nome = nome;
    }

    /**
     * metodo per ritornare enum in stringa
     *
     * @return stringa corrispendente all'enum con il rispettivo colore
     */
    @Override
    public String toString() {
        return nome;
    }
}
