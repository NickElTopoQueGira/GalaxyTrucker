package partita.giocatore;

public enum Colori {
	ROSSO 	(1, "\033[1;91m"), 
	GIALLO 	(2, "\033[1;93m"),
	VERDE 	(3, "\033[1;92m"), 
	BLU 	(4 ,"\033[1;94m");
	
	private final int idColore;
	private final String codiceColore;

	private Colori(int idColore, String codiceColore){
		this.idColore = idColore;
		this.codiceColore=codiceColore;
	}

	public int getIdColore(){
		return this.idColore;
	}
	
	public String getname(){
		String temp=this.codiceColore+this.toString()+"\u001B[0m";
		return temp;
	}
	
	public String getCodiceColore() {
		return this.codiceColore;
		
	}
	
	public static Colori coloreSelezionato(int numeroColore) {
		for(Colori colori : Colori.values()){
			if(colori.getIdColore() == numeroColore){
				return colori;
			}
		}
		throw new IllegalArgumentException("Il numero del colore non è valido");
	}

	public Colori next() {
		Colori[] valore = Colori.values();	
		int nextValore = (this.ordinal() + 1) % valore.length;
		return valore[nextValore];

	}
}
