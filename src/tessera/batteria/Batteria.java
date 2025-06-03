package tessera.batteria;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TessereNormali;
import tessera.TipoTessera;

public class Batteria extends Tessera implements TessereNormali{
	private static final int massimo = 17;
	private static int contatore = 0;
	
	private final int capacity;
	private int energiaAttuale;
	
	/**
	 * costruttore
	 * se viene ecceduto il numero massimo di elementi genera eccezione
	 * @param  
	 * @throws ErroreTessera
	 */
	public Batteria() throws ErroreTessera {
		super(TipoTessera.BATTERIA, Posizione.INTERNA);
		contatore++;
		if (contatore <= massimo) {
			this.capacity = RandomTipo();
			this.energiaAttuale = capacity;
		} else {
			throw new ErroreTessera("Numero Elementi Batteria Max"); // Eccezione Numero Massimo di elementi
		}

	}

	/**
	 * genera random un intero per determinare la capacità
	 * @return
	 */
	private int RandomTipo() {
		return new Random().nextInt(1) + 2;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getEnergiaAttuale() {
		return energiaAttuale;
	}

	
	/**
	 * decrementa di 1 il valore di energiaAttuale
	 * @return false se non è stato possibile farlo, true se operazione eseguita correttamente
	 */
	public boolean decrese() {
		if (this.energiaAttuale > 0) {
			this.energiaAttuale = energiaAttuale - 1;
			return true;
		}
		return false;
	}

	/**
	 * metodo che ritorna stringa descrittiva della tessera
	 * @return stringa descrittiva
	 */
	@Override
	public String toLegenda() {
		String temp="modulo energia "+this.energiaAttuale+"/"+this.capacity;
		return temp;
	}

	/**
	 * decrementa contatore di 1
	 */
	@Override
	public void decrementaNumeroCorrente() {
		contatore=contatore-1;
		
	}

}
