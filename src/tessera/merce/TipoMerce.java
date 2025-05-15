package tessera.merce;

public enum TipoMerce {
	MERCE_ROSSA		(4,"\033[1;91m"+"\254"+"\u001B[0m"),	//rosso, 
	MERCE_GIALLA	(2,"\033[1;93m"+"\254"+"\u001B[0m"), 	//giallo 
	MERCE_VERDE		(3,"\033[1;92m"+"\254"+"\u001B[0m"), 	//verde 
	MERCE_BLU		(1,"\033[1;94m"+"\254"+"\u001B[0m");	//blu

	private final int valore;
	private final String name;

	/**
	 * costruttore
	 * @param valore
	 * @param name
	 */
	TipoMerce(int valore, String name) {
		this.valore = valore;
		this.name = name;
		
	}

	public int getValore() {
		return this.valore;
	}

	/**
	 * ritorna il valore dell'enum corrispondente al colore della merce
	 */
	@Override
	public String toString() {
		return this.name();
	}
}