package tessera.merce;

public enum TipoStiva {
	NORMALI 	("\033[1;96m"+"standard"+"\u001B[0m"), //ciano 
	SPECIALI 	("\033[1;91m"+"speciale"+"\u001B[0m"); //rosso
	
	

	private final String nome;

	/**
	 * costruttore
	 * @param tipo
	 */
	TipoStiva(String nome) {
		this.nome = nome;
	}
	
	/**
	 * metodo per ritornare enum in stringa
	 * @return stringa corrispendente all'enum con il rispettivo colore
	 */
	@Override
	public String toString() {
		return nome;
	}
}
