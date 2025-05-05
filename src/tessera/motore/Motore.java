package tessera.motore;

import java.util.Random;

import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoTessera;

public class Motore extends Tessera {
	private static final int massimo = 30;
	private static int contatore = 0; 

	private final TipoMotore tipoMotore;
	

	public Motore() throws ErroreTessera {
		super(TipoTessera.MOTORE);
		contatore++;
		if (contatore <= massimo) {
			this.tipoMotore = randomTipo();
		} else {
			throw new ErroreTessera("Numero Elementi Motore Max"); // Eccezione Numero Massimo di elementi
		}

	}


	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	private TipoMotore randomTipo() {
		TipoMotore[] tipiMotore = TipoMotore.values();
		return tipiMotore[new Random().nextInt(tipiMotore.length)];
	}
	
	@Override
	public void ruota() throws  ErroreRotazione{
		throw new ErroreRotazione("Errore: Rotazione non valida"); // Eccezione: il motore non ruota
		
	}

}
