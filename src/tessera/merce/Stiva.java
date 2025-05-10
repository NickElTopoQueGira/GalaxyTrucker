package tessera.merce;

import java.util.ArrayList;
import java.util.Random;

import eccezioniPersonalizzate.ErroreRisorse;
import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoTessera;
import tessera.motore.Motore;
import tessera.motore.TipoMotore;

public class Stiva extends Tessera {

	private static final int massimo = 24;
	private static int contatore = 0;

	private final TipoStiva tipoMerciGenerale;
	private final int MaxCapienza;
	private int valore;
	private int numeroMerciAttuale;
	private ArrayList<Merce>stiva;

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

	public void inserisciMerci(Merce merce ) throws ErroreRisorse {
		TipoMerce tipoSpecifico = merce.getTipoMerce();
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
				
				throw new ErroreRisorse("Errore nell'inserimento della merce (modulo non adatto)");

			} else {
				this.numeroMerciAttuale = this.numeroMerciAttuale + 1;
				stiva.add(merce);
			}

		} else {
			throw new ErroreRisorse("Errore nell'inserimento della merce (limite max di storage raggiunto)");
			
		}
	}
	
	
	/**
	 * Metodo che rimuove dalla tessera stiva l'oggetto merce
	 * @param merce
	 * @throws ErroreRisorse
	 */
	public void rimuoviMerce(Merce merce) throws ErroreRisorse{
		if(this.numeroMerciAttuale > 0){
			this.numeroMerciAttuale -= 1;
			boolean check=false;
			for(Merce temp: stiva) {
				if(temp==merce) {
					check=true;
				}
			}
			if(check) {
				this.setValore(-merce.getTipoMerce().getValore());
				stiva.remove(merce);
			}else {
				throw new ErroreRisorse("non esistono merci di quel tipo in questa stiva");
			}
			
		}
		else{
			throw new ErroreRisorse("non ci sono merci");
		}		
	}

	public int getValore() {
		return valore;
	}
	
	
	/** funzione che modifica il valore della tessera stiva con
	    + oppure - edit
	    es:
		setValore(-tipoMerce1.getvalore());
	 * 
	 * @param edit
	 */
	public void setValore(int edit) { // 
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
