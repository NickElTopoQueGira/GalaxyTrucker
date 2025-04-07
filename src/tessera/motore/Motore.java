package tessera.motore;

import java.util.Random;

import tessera.Tessera;
import tessera.TipoTessera;
import tessera.cannone.TipoCannone;

public class Motore extends Tessera{

	
	
	private final TipoMotore tipoMotore;
	
	public Motore() {
		super(TipoTessera.MOTORE);
		this.tipoMotore =TipoMotore.values()[RandomTipo()];
		
	}


	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoCannone.values().length);
		return pick;
	}


	

	
	
	
	

}
