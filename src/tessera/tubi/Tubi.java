package tessera.tubi;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoTessera;

public class Tubi extends Tessera{

	private static int contatore=0;
	private static final int massimo=8;

	public Tubi() throws ErroreTessera {
		super(TipoTessera.TUBI);
		contatore++;
		if(contatore>massimo) {
			throw new ErroreTessera("Numero Elementi Tubi Max"); //Eccezione Numero Massimo di elementi
		}
	}

}
