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
		try{
			switch (tipo) {
				case TipoTessera.PORTA_MERCI: {
					return estraiTipo();			
				}
				case TipoTessera.SCUDI: {
					return estraiTipo();
				}
				case TipoTessera.TUBI: {
					return estraiTipo();
				}
				case TipoTessera.MODULO_PASSEGGERI: {
					return estraiTipo();
				}
				case TipoTessera.BATTERIA: {
					return estraiTipo();
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
		catch(ErroreTessera eT){
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
