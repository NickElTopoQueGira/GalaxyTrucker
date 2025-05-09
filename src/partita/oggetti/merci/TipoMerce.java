package partita.oggetti.merci;

public enum TipoMerce {
	MERCE_ROSSA		(4), 
	MERCE_GIALLA	(3), 
	MERCE_VERDE		(2), 
	MERCE_BLU		(1);

	private final int valore;

	TipoMerce(int valore) {
		this.valore = valore;
	}

	public int getValore() {
		return this.valore;
	}

	@Override
	public String toString() {
		return this.name();
	}
}