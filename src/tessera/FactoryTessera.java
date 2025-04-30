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
	private static int numeroTessere=0;

	public Tessera estraiTipo() throws ErroreTessera {
		numeroTessere=numeroTessere+1;
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
			numeroTessere=numeroTessere-1;
			if(numeroTessere<=152) {
				return estraiTipo();
			}else {
				throw new ErroreTessera("Numero Elementi Tessera Max"); // Eccezione Numero Massimo di elementi
			}
			
			
		}
	}

	public TipoTessera randomTipo() {
		TipoTessera tipiTessera[] = TipoTessera.values();
		// viene fatta una random della lunghezza -1 per escludere il tipo centro
		return tipiTessera[new Random().nextInt(tipiTessera.length - 1)];
	}
}
