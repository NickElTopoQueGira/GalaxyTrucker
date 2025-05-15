package tessera.modulo_passeggeri;

public enum ColoreAlieni {
	VIOLA	("da fare"),
	MARRONE ("");
	
	
	private final String nome;

	/**
	 * costruttore
	 * @param tipo
	 */
	ColoreAlieni(String nome) {
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
