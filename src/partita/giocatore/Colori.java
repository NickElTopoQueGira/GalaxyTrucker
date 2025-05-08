package partita.giocatore;

public enum Colori {
	ROSSO 	(1, "\033[1;91m"+"Rosso"+"\u001B[0m"), 
	GIALLO 	(2,"\033[1;93m"+"Giallo"+"\u001B[0m"),
	VERDE 	(3,"\033[1;92m"+"Verde"+"\u001B[0m"), 
	BLU 	(4 ,"\033[1;94m"+"Blu"+"\u001B[0m");
	
	private final int idColore;
	private final String name;

	private Colori(int idColore, String nome){
		this.idColore = idColore;
		this.name=nome;
	}

	public int getIdColore(){
		return this.idColore;
	}
	
	public String getname(){
		return this.name;
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
