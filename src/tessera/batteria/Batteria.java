package tessera.batteria;

import java.util.Random;


import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoTessera;

public class Batteria extends Tessera{
	private final int capacity;
	private int energiaAttuale;
	private static int contatore=0;
	private static final int massimo=10;

	public Batteria() throws ErroreTessera {
		super(TipoTessera.BATTERIA);
		contatore++;
		if(contatore<=massimo) {
			this.capacity = RandomTipo();
			this.energiaAttuale=capacity;
		}else {
			throw new ErroreTessera("Numero Elementi Batteria Max"); //Eccezione Numero Massimo di elementi
		}
		
		
		
	}

	private int RandomTipo() {
		return new Random().nextInt(1)+2;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getEnergiaAttuale() {
		return energiaAttuale;
	}

	public boolean decrese() {
		if(this.energiaAttuale>0) {
			this.energiaAttuale = energiaAttuale-1;
			return true;
		}
		return false;
	}

}
