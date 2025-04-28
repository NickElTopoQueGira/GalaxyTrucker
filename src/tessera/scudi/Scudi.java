package tessera.scudi;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Scudi extends Tessera{
	
	private TipoLato latoScudo1 = TipoLato.UP;
	private TipoLato latoScudo2 = TipoLato.RIGHT;
	private static int contatore=0;
	private static final int massimo=8;

	public Scudi() throws ErroreTessera {
		super(TipoTessera.SCUDI);
		contatore++;
		if(contatore>massimo) {
			throw new ErroreTessera("Numero Elementi Scudi Max"); //Eccezione Numero Massimo di elementi
		}
	}
	
	
	@Override
	public void ruota() {
		super.ruota();
		this.latoScudo1=this.latoScudo1.next();
		this.latoScudo2=this.latoScudo2.next();
	}

}
