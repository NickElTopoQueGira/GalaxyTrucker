package partita.giocatore;

public enum Colori {
	ROSSO, 
	GIALLO, 
	VERDE, 
	BLU;
	
	public Colori next() {
		Colori[] valore = Colori.values();	
		int nextValore = (this.ordinal() + 1) % valore.length;
		return valore[nextValore];

	}
}


