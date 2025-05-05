package tessera.tubi;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoTessera;

public class Tubi extends Tessera {

	private static final int massimo = 8;
	private static int contatore = 0;
	
	public Tubi() throws ErroreTessera {
		super(TipoTessera.TUBI);
		contatore++;
		if (contatore > massimo) {
			throw new ErroreTessera("Numero Elementi Tubi Max"); // Eccezione Numero Massimo di elementi
		}
		boolean condizione=true;
		while(condizione) {
			if(this.latiTessera.getUp()==TipoConnettoriTessera.NULLO ||
			   this.latiTessera.getRight()==TipoConnettoriTessera.NULLO ||	
			   this.latiTessera.getDown()==TipoConnettoriTessera.NULLO ||
			   this.latiTessera.getLeft()==TipoConnettoriTessera.NULLO) {
				
				this.latiTessera.GeneraLatiTessera();

			}else {
				condizione=false;
			}
		}
		
	}

}
