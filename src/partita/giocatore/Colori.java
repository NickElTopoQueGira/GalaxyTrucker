package partita.giocatore;

public enum Colori {
	ROSSO 	(1), 
	GIALLO 	(2), 
	VERDE 	(3), 
	BLU 	(4);
	
	private final int idColore;

	private Colori(int idColore){
		this.idColore = idColore;
	}

	public int getIdColore(){
		return this.idColore;
	}

	public static Colori coloreSelezionato(int numeroColore) {
		for(Colori colori : Colori.values()){
			if(colori.getIdColore() == numeroColore){
				return colori;
			}
		}
		throw new IllegalArgumentException("Il del colore non valido!!");
	}

	public Colori next() {
		Colori[] valore = Colori.values();	
		int nextValore = (this.ordinal() + 1) % valore.length;
		return valore[nextValore];

	}
}
