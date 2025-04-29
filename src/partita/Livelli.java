package partita;

public enum Livelli {
    PRIMO, 
    SECONDO, 
    TERZO;
	
	
	public Livelli next() {
		Livelli[] valore = Livelli.values();
		int nextValore = (this.ordinal() + 1) % valore.length;
		return valore[nextValore];

	}
}
