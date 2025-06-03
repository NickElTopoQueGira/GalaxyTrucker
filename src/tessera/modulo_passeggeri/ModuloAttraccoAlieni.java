package tessera.modulo_passeggeri;

import java.util.Random;


import eccezioniPersonalizzate.ErroreTessera;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TessereNormali;
import tessera.TipoTessera;

public class ModuloAttraccoAlieni extends Tessera implements TessereNormali{
	
	private static final int massimo = 12;
	private static int contatore = 0;
	
	private final ColoreAlieni colore;

	/**
	 * costruttore
	 * se viene ecceduto il numero massimo di elementi genera eccezione
	 * @throws ErroreTessera
	 */
	public ModuloAttraccoAlieni() throws ErroreTessera {
		super(TipoTessera.MODULO_ATTRACCO_ALIENI, Posizione.INTERNA);
		contatore++;
		if (contatore <= massimo) {
			this.colore = randomTipo();

		} else {
			throw new ErroreTessera("Numero Elementi Modulo Alieni Max"); // Eccezione Numero Massimo di elementi
		}
	}
	
	/**
	 * random di enum ColoreAlieni
	 * @return enum ColoreAlieni
	 */
	private ColoreAlieni randomTipo() {
		ColoreAlieni[] colori = ColoreAlieni.values();
		return colori[new Random().nextInt(colori.length)];
	}
	
	/**
	 * metodo che ritorna stringa descrittiva della tessera
	 * @return stringa descrittiva
	 */
	@Override
	public String toLegenda() {
		String temp="modulo attracco alieno "+this.colore.toString();
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
