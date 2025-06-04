package tessera.cannone;

public enum TipoCannone {
    SINGOLO		("\033[1;95m" + "singolo" + "\u001B[0m"), 	//viola
    DOPPIO		("\033[1;92m" + "doppio" + "\u001B[0m"); 	//verde

    private final String nome;

    /**
     * costruttore
     *
     * @param tipo
     */
    TipoCannone(String nome) {
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
