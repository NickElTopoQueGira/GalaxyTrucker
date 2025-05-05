package partita;

public enum Livelli {
    PRIMO	(18, 8, 0, 0), 
    SECONDO (24, 4, 8, 0), 
    TERZO	(34, 4, 4, 8);
	
	private final int numeroCaselle; 
	private final int numeroCartePrimoLivello;
	private final int numeroCarteSecondoLivello;
	private final int numeroCarteTesrzoLivello;

	private Livelli(int numeroCaselle, int numeroCartePrimoLivello, 
					int numeroCarteSecondoLivello, int numeroCarteTesrzoLivello){
						
		this.numeroCaselle = numeroCaselle;
		this.numeroCartePrimoLivello   = numeroCartePrimoLivello;
		this.numeroCarteSecondoLivello = numeroCarteSecondoLivello;
		this.numeroCarteTesrzoLivello  = numeroCarteTesrzoLivello;
	}

	public int getNumeroCaselle(){ return this.numeroCaselle; }

	public int getNumeroCartePrimoLivello(){ return this.numeroCartePrimoLivello; }
	public int getNumeroCarteSecondoLivello(){ return this.numeroCarteSecondoLivello; }
	public int getNumeroCarteTerzoLivello(){ return this.numeroCarteTesrzoLivello; }

	public static int getCaselleXLivello(Livelli livello){
		return livello.getNumeroCaselle();
	}

	public Livelli next() {
		Livelli[] valore = Livelli.values();
		int nextValore = (this.ordinal() + 1) % valore.length;
		return valore[nextValore];

	}
}
