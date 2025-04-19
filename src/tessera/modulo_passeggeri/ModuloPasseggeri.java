package tessera.modulo_passeggeri;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoTessera;

public class ModuloPasseggeri extends Tessera {
	private static int contatore = 0;
	private final TipoModuloPasseggeri tipoModuloPasseggeri;
	private final int massimo=15;
	

	public ModuloPasseggeri() throws ErroreTessera {
		super(TipoTessera.MODULO_PASSEGGERI);
		contatore++;
		if(contatore<=massimo) {
			this.tipoModuloPasseggeri = randomTipo();
		}else {
			throw new ErroreTessera("Numero Elementi Modulo paseggeri Max"); //Eccezione Numero Massimo di elementi
		}
		
		

	}

	public TipoModuloPasseggeri getTipoModuloPasseggeri() {
		return tipoModuloPasseggeri;
	}

	
	private TipoModuloPasseggeri randomTipo() {
		TipoModuloPasseggeri[] tipiModuli = TipoModuloPasseggeri.values();
		return tipiModuli[new Random().nextInt(tipiModuli.length)];
	}
	
	protected int RandomTipo() {
		int pick = new Random().nextInt(TipoModuloPasseggeri.values().length);
		return pick;

	}

	

}
