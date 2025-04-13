package tessera;

import java.util.Random;

import tessera.cannone.Cannone;
import tessera.motore.Motore;
import tessera.modulo_passeggeri.ModuloPasseggeri;

public class FactoryTessera //implements GeneraTessera 
{

	public Tessera estraiTipo() {

		TipoTessera tipo = randomTipo();
		switch (tipo) {
		case TipoTessera.PORTA_MERCI: {

		}
		case TipoTessera.SCUDI: {

		}

		case TipoTessera.TUBI: {

		}

		case TipoTessera.MODULO_PASSEGGERI: {

		}

		case TipoTessera.BATTERIA: {

		}

		case TipoTessera.CANNONE: {
			return new Cannone();
		}

		case TipoTessera.MOTORE: {
			return new Motore();
		}

		default: {
			return new ModuloPasseggeri();
		}
		}
	}

	public TipoTessera randomTipo() {
		TipoTessera tipiTessera[] = TipoTessera.values();
		// viene fatta una random della lunghezza -1 per escludere il tipo centro
		return tipiTessera[new Random().nextInt(tipiTessera.length - 1)];
	}
}
