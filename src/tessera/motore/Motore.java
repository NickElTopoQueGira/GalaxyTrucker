package tessera.motore;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Motore extends Tessera {

	private static final int massimo = 30;
	private static int contatore = 0;
	
	private TipoLato latoMotore;
	private final TipoMotore tipoMotore;
	

	public Motore() throws ErroreTessera {
		super(TipoTessera.MOTORE);
		contatore++;
		if (contatore <= massimo) {
			this.tipoMotore = randomTipo();
			this.latoMotore = TipoLato.DOWN;
		} else {
			throw new ErroreTessera("Numero Elementi Motore Max"); // Eccezione Numero Massimo di elementi
		}

	}

	public TipoLato getLatoMotore() {
		return latoMotore;
	}

	public void setLatoMotore(TipoLato latoMotore) {
		this.latoMotore = latoMotore;
	}

	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	private TipoMotore randomTipo() {
		TipoMotore[] tipiMotore = TipoMotore.values();
		return tipiMotore[new Random().nextInt(tipiMotore.length)];
	}

	@Override
	public void ruota() {
		super.ruota();
		this.latoMotore = this.latoMotore.next();
	}

}
