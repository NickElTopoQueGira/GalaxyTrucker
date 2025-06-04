package tessera.modulo_passeggeri;

public enum ColoreAlieni {
    VIOLA("\033[1;95m" + "viola" + "\u001B[0m"), //viola
    MARRONE("\033[1;93m" + "marrone" + "\u001B[0m"); //giallo

    private final String nome;

    /**
     * costruttore
     *
     * @param tipo
     */
    ColoreAlieni(String nome) {
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
