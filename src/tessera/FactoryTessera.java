package tessera;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.batteria.Batteria;
import tessera.cannone.Cannone;
import tessera.merce.Merce;
import tessera.motore.Motore;
import tessera.scudi.Scudi;
import tessera.tubi.Tubi;
import tessera.modulo_passeggeri.ModuloPasseggeri;

public class FactoryTessera {

	public Tessera estraiTipo() {

		TipoTessera tipo = randomTipo();
		try {
			switch (tipo) {
			case TipoTessera.PORTA_MERCI: {
				return new Merce();
			}
			case TipoTessera.SCUDI: {
				return new Scudi();
			}
			case TipoTessera.TUBI: {
				return new Tubi();
			}
			case TipoTessera.MODULO_PASSEGGERI: {
				return new ModuloPasseggeri();
			}
			case TipoTessera.BATTERIA: {
				return new Batteria();
			}
			case TipoTessera.CANNONE: {
				return new Cannone();
			}
			case TipoTessera.MOTORE: {
				return new Motore();
			}
			default: {
				return estraiTipo();
			}

			}
		} catch (ErroreTessera eT) {
			System.err.println(eT.getMessage());
			return estraiTipo();
		}
	}

	public TipoTessera randomTipo() {
		TipoTessera tipiTessera[] = TipoTessera.values();
		// viene fatta una random della lunghezza -1 per escludere il tipo centro
		return tipiTessera[new Random().nextInt(tipiTessera.length - 1)];
	}
}
