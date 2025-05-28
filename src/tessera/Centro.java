package tessera;

import partita.giocatore.Colori;

public class Centro extends Tessera{
	private int passeggeriCorrenti = 2;
	private final Colori colore;
	private final Coordinate coordinate;
	
	public Centro(Colori colore, Coordinate coordinate){
		super(TipoTessera.CENTRO, Posizione.INTERNA);
		this.colore = colore;
		this.coordinate = coordinate;
		this.getLatiTessera().setUp    (TipoConnettoriTessera.TRIPLO);
		this.getLatiTessera().setDown  (TipoConnettoriTessera.TRIPLO);
		this.getLatiTessera().setRight (TipoConnettoriTessera.TRIPLO);
		this.getLatiTessera().setLeft  (TipoConnettoriTessera.TRIPLO);
	}

	public Colori getColore(){
		return colore;
	}

	public int getPasseggeriCorrenti(){
		return passeggeriCorrenti;
	}
	
	@Override
	public Coordinate getCoordinate(){ return this.coordinate; }

	/**
	 * rimuove i passeggeri di un edit intero, se edit>passeggeriCorrenti
	 * imposta passeggeriCorrenti a 0
	 * @param edit
	 */
	public void rimuoviPasseggeri(int edit) {
		if (this.passeggeriCorrenti - edit >= 0) {
			this.passeggeriCorrenti = this.passeggeriCorrenti + edit;
		} else {
			this.passeggeriCorrenti = 0;
		}
	}
	
	/**
	 * metodo che ritorna stringa descrittiva della tessera
	 * @return "centro cosmonauti" + il numero di cosmonauti presenti
	 */
	@Override
	public String toLegenda(){
		return "centro cosmonauti: "+this.getPasseggeriCorrenti()+"/2";
	}
	

}
