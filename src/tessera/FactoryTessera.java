package tessera;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.cannone.Cannone;
import tessera.motore.Motore;
import tessera.modulo_passeggeri.ModuloPasseggeri;

public class FactoryTessera //implements GeneraTessera 
{

	public Tessera estraiTipo() {

		TipoTessera tipo = randomTipo();
		switch (tipo) {
		case TipoTessera.PORTA_MERCI: {
			try {
				return new Cannone();
			} catch (ErroreTessera e) {
				estraiTipo();
			}

		}
		case TipoTessera.SCUDI: {
			try {
				return new Cannone();
			} catch (ErroreTessera e) {
				estraiTipo();
			}

		}

		case TipoTessera.TUBI: {
			try {
				return new Cannone();
			} catch (ErroreTessera e) {
				estraiTipo();
			}

		}

		case TipoTessera.MODULO_PASSEGGERI: {
			try {
				return new Cannone();
			} catch (ErroreTessera e) {
				estraiTipo();
			}

		}

		case TipoTessera.BATTERIA: {
			try {
				return new Cannone();
			} catch (ErroreTessera e) {
				estraiTipo();
			}

		}

		case TipoTessera.CANNONE: {
			
			try {
				return new Cannone();
			} catch (ErroreTessera e) {
				estraiTipo();
			}
		}

		case TipoTessera.MOTORE: {
			try {
				return new Motore();
			} catch (ErroreTessera e) {
				estraiTipo();
			}
		}

		default: {
			try {
				return new ModuloPasseggeri();
			} catch (ErroreTessera e) {
				estraiTipo();
			}
		}
		
		}
		return null;
	}

	public TipoTessera randomTipo() {
		TipoTessera tipiTessera[] = TipoTessera.values();
		// viene fatta una random della lunghezza -1 per escludere il tipo centro
		return tipiTessera[new Random().nextInt(tipiTessera.length - 1)];
	}
}
