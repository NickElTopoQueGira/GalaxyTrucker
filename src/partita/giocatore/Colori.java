package partita.giocatore;

public enum Colori {
	ROSSO 	(1, "\033[1;91m","\033[1;91m"+"R"+"\u001B[0m"), 
	GIALLO 	(2, "\033[1;93m","\033[1;93m"+"G"+"\u001B[0m"),
	VERDE 	(3, "\033[1;92m","\033[1;92m"+"V"+"\u001B[0m"), 
	BLU 	(4 ,"\033[1;94m","\033[1;94m"+"B"+"\u001B[0m");
	
	private final int idColore;
	private final String codiceColore;
	private final String siglaTabellone;

	/**
	 * costruttore
	 * @param idColore
	 * @param codiceColore
	 * @param siglaTabellone
	 */
	private Colori(int idColore, String codiceColore, String siglaTabellone){
		this.idColore = idColore;
		this.codiceColore=codiceColore;
		this.siglaTabellone=siglaTabellone;
	}

	public int getIdColore(){
		return this.idColore;
	}
	
	/**
	 * metodo per il nome del colore colorato
	 * @return la stringa corrispondente al colore
	 */
	public String getname(){
		String temp=this.codiceColore+this.toString()+"\u001B[0m";
		return temp;
	}
	
	public String getCodiceColore() {
		return this.codiceColore;
		
	}
	
	/**
	 * metodo per la selezione tramite intero del colore corrispondente
	 * @param numeroColore
	 * @return enum colore selezionato
	 */
	public static Colori coloreSelezionato(int numeroColore) {
		for(Colori colori : Colori.values()){
			if(colori.getIdColore() == numeroColore){
				return colori;
			}
		}
		throw new IllegalArgumentException("Il numero del colore non è valido");
	}
	
	public String getSiglaTabellone() {
		return this.siglaTabellone;
		
	}

	/**
	 * quando chiamato fornisce il colore successivo di quello chiamato
	 * @return enum colore successivo
	 */
	public Colori next() {
		Colori[] valore = Colori.values();	
		int nextValore = (this.ordinal() + 1) % valore.length;
		return valore[nextValore];

	}
}
