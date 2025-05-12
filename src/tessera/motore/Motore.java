package tessera.motore;

import java.util.Random;

import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoTessera;

public class Motore extends Tessera {
	private static final int massimo = 30;
	private static int contatore = 0; 

	private final TipoMotore tipoMotore;
	

	/**
	 * costruttore
	 * se viene ecceduto il numero massimo di elementi genera eccezione.
	 * verifica in maniera ricorsiva nel caso il lato motore (di default down;
	 * sovrascirve nella matrice di tessera il carattere "§")
	 * settato come Nullo, non corrisponda con 
	 * l'unico lato connettore presente originariamente diverso da Nullo  
	 * @throws ErroreTessera
	 */
	public Motore() throws ErroreTessera {
		super(TipoTessera.MOTORE);
		contatore++;
		if (contatore <= massimo) {
			this.tipoMotore = randomTipo();
			super.tessera_Disposizione[4][2]="\033[0;31m"+"§"+"\033[0m";
			this.latiTessera.setDown(TipoConnettoriTessera.NULLO);
			while(!this.latiTessera.verificaTessera()) {
				this.latiTessera.GeneraLatiTessera();
				this.latiTessera.setDown(TipoConnettoriTessera.NULLO);
			}
		} else {
			throw new ErroreTessera("Numero Elementi Motore Max"); // Eccezione Numero Massimo di elementi
		}

	}


	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	/**
	 * random di enum tipomotore
	 * @return enum di tipomotore
	 */
	private TipoMotore randomTipo() {
		TipoMotore[] tipiMotore = TipoMotore.values();
		return tipiMotore[new Random().nextInt(tipiMotore.length)];
	}
	
	/**
	 * genera eccezione in rotazione del motore piochè non concessa
	 */
	@Override
	public void ruota() throws  ErroreRotazione{
		throw new ErroreRotazione("Errore: Rotazione non valida"); // Eccezione: il motore non ruota
		
	}

}
