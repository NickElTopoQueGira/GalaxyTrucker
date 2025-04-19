package tessera;

import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;

public class Centro extends Tessera{
	private final int massimo=4;
	private int contatore=0;
	private final Colori colore;
	
    public Centro(Colori colore) throws ErroreTessera{
        super(TipoTessera.CENTRO);
        contatore++;
		if(contatore<=massimo) {
			this.colore=colore;
		}else {
			throw new ErroreTessera("Numero Elementi Cannone Max"); //Eccezione Numero Massimo di elementi
		}
    }

	public Colori getColore() {
		return colore;
	}
	
}
