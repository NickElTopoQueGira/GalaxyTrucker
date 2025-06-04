package tessera.motore;

public enum TipoMotore {
    SINGOLO("\033[1;93m" + "singolo" + "\u001B[0m"), //giallo
    DOPPIO("\033[1;92m" + "doppio" + "\u001B[0m"); 	//verde

    private final String nome;

    /**
     * costruttore
     *
     * @param tipo
     */
    TipoMotore(String nome) {
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
