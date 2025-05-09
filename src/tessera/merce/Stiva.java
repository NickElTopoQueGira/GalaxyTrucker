package tessera.merce;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import partita.oggetti.merci.TipoMerce;
import tessera.Tessera;
import tessera.TipoTessera;

public class Stiva extends Tessera {

	private static final int massimo = 24;
	private static int contatore = 0;

	private final TipoStiva tipoMerciGenerale;
	private final int MaxCapienza;
	private int valore;
	private int numeroMerciAttuale;

	public Stiva() throws ErroreTessera {
		super(TipoTessera.PORTA_MERCI);
		contatore++;
		if (contatore <= massimo) {
			this.tipoMerciGenerale = randomTipo(); // specifica il tipo se normale o speciale

			this.MaxCapienza = new Random().nextInt(2) + 1;
			this.valore = 0;
			this.numeroMerciAttuale = 0;
		} else {
			throw new ErroreTessera("Numero Elementi Merci Max"); // Eccezione Numero Massimo di elementi
		}

	}

	public void inserisciMerci(TipoMerce tipoSpecifico) {
		if (this.numeroMerciAttuale < this.MaxCapienza) {

			int temp = this.valore;
			if (this.tipoMerciGenerale == TipoStiva.NORMALI) {
				if (tipoSpecifico == TipoMerce.MERCE_GIALLA || tipoSpecifico == TipoMerce.MERCE_VERDE
						|| tipoSpecifico == TipoMerce.MERCE_BLU) {
					setValore(tipoSpecifico.getValore());
				}
			}
			if (this.tipoMerciGenerale == TipoStiva.SPECIALI && tipoSpecifico == TipoMerce.MERCE_ROSSA) {
				setValore(tipoSpecifico.getValore());
			}

			if (this.valore == temp) { // errore quando provo ad inserire merce speciale in modulo normale o viceversa
				System.out.println("Errore nell'inserimento della merce (modulo non adatto)");
			} else {
				this.numeroMerciAttuale = this.numeroMerciAttuale + 1;
			}

		} else {
			System.out.println("Errore nell'inserimento della merce (limite max di storage raggiunto)");
		}
	}

	public int getValore() {
		return valore;
	}

	public void setValore(int edit) { // quando chiami questa funzione metterai nelle parentesi
		// + oppure - edit (ovvero: "nome variabile".getValore()) che serve. es:
		// setValore(-tipoMerce1.getvalore()); PER AGGIUNGERE C'è LA FUNZIONE APPOSTA
		if (this.valore + edit >= 0) {
			this.valore = this.valore + edit;
		} else {
			this.valore = 0;
		}

	}

	public TipoStiva getTipoMerciGenerale() {
		return tipoMerciGenerale;
	}

	private TipoStiva randomTipo() {
		TipoStiva[] tipoMerceGenerale = TipoStiva.values();
		return tipoMerceGenerale[new Random().nextInt(tipoMerceGenerale.length)];
	}
}
