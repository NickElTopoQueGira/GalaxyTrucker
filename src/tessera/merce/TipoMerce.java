package tessera.merce;

public enum TipoMerce {
	MERCE_ROSSA		(4), 
	MERCE_GIALLA	(3), 
	MERCE_VERDE		(2), 
	MERCE_BLU		(1);

	private final int valore;

	/**
	 * costruttore
	 * @param valore
	 */
	TipoMerce(int valore) {
		this.valore = valore;
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