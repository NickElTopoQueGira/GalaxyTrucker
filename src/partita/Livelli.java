package partita;

public enum Livelli {
    PRIMO	(1, 18, 8, 0, 0), 
    SECONDO (2, 24, 4, 8, 0), 
    TERZO	(3, 34, 4, 4, 8);

	private final int livelloNumerico;
	private final int numeroCaselle; 
	private final int numeroCartePrimoLivello;
	private final int numeroCarteSecondoLivello;
	private final int numeroCarteTesrzoLivello;

	/**
	 * Costruttore privato di livelli
	 * @param livelloNumerico int numero livello
	 * @param numeroCaselle int numero delle caselli presenti sul livello
	 * @param numeroCartePrimoLivello int numero di carte al primo livello
	 * @param numeroCarteSecondoLivello int numero di carte al secondo livello
	 * @param numeroCarteTesrzoLivello int numero di carta al terzo livello
	 * */
	private Livelli(int livelloNumerico, int numeroCaselle, int numeroCartePrimoLivello, 
					int numeroCarteSecondoLivello, int numeroCarteTesrzoLivello){
		
		this.livelloNumerico = livelloNumerico;
		this.numeroCaselle = numeroCaselle;
		this.numeroCartePrimoLivello   = numeroCartePrimoLivello;
		this.numeroCarteSecondoLivello = numeroCarteSecondoLivello;
		this.numeroCarteTesrzoLivello  = numeroCarteTesrzoLivello;
	}

	/**
	 * Metodo per prendere il numero del livello
	 *
	 * @return int numero del livello
	 * */
	public int getLivelloNumerico(){ return this.livelloNumerico; }

	/**
	 * Metodo pre prendere il numero di caselle
	 *
	 * @return int numero di caselle presenti
	 * */
	public int getNumeroCaselle(){ return this.numeroCaselle; }

	/**
	 * Metodo per prendere il numero di carte al primo livello
	 *
	 * @return int numero di carte al primo livello
	 * */
	public int getNumeroCartePrimoLivello(){ return this.numeroCartePrimoLivello; }

	/**
	 * Metodo per prendere il numero di carte al secondo livello
	 *
	 * @return int numero di carte al secondo livello
	 * */
	public int getNumeroCarteSecondoLivello(){ return this.numeroCarteSecondoLivello; }

	/**
	 * Metodo per prendere il numero di carte al terzo livello
	 *
	 * @return int numero di carte al terzo livello
	 * */
	public int getNumeroCarteTerzoLivello(){ return this.numeroCarteTesrzoLivello; }

	/**
	 * Metodo che restituisce il numero di caselle presenti per il livello specificato
	 * in input al metodo
	 *
	 * @param livello Livelli
	 * @return int numero di caselle presenti al livello selezionati
	 * */
	public static int getCaselleXLivello(Livelli livello){
		return livello.getNumeroCaselle();
	}

	/**
	 * Metodo che restituisce il valore enum corrispondente al valore dato in input
	 *
	 * @param livello int livello da convertire
	 * @return Livelli livello corrispondente al livello dato per immissione alla funzione
	 * */
	public static Livelli getLivello(int livello){
		for(Livelli l : Livelli.values()){
			if(l.getLivelloNumerico() == livello){
				return l;
			}
		}
		return null;
	}

	/**
	 * Metodo che restituisce il livello successivo a quello in essere
	 *
	 * @return Livelli livello successivo
	 * */
	public Livelli next() {
		Livelli[] valore = Livelli.values();
		int nextValore = (this.ordinal() + 1) % valore.length;
		return valore[nextValore];

	}
}
