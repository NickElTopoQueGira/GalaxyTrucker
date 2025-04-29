package tessera;

import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;

public class Centro extends Tessera {
	private final int massimo = 4;
	private int contatore = 0;

	private int passeggeriCorrenti = 2;
	private final Colori colore;

	public Centro(Colori colore) throws ErroreTessera {
		super(TipoTessera.CENTRO);
		contatore++;
		if (contatore <= massimo) {
			this.colore = colore;
		} else {
			throw new ErroreTessera("Numero Elementi Cannone Max"); // Eccezione Numero Massimo di elementi
		}
	}

	public Colori getColore() {
		return colore;
	}

	public int getPasseggeriCorrenti() {
		return passeggeriCorrenti;
	}

	public void rimuoviPasseggeri(int edit) {
		if (this.passeggeriCorrenti - edit >= 0) {
			this.passeggeriCorrenti = this.passeggeriCorrenti + edit;
		} else {
			this.passeggeriCorrenti = 0;
		}

	}

}
