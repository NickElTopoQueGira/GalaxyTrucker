package tessera.cannone;

import java.util.Random;


import tessera.Tessera;
import tessera.TipoTessera;

public class Cannone extends Tessera{
	
	private final TipoCannone tipoCannone;
	

	public Cannone() {
		super(TipoTessera.CANNONE);
		this.tipoCannone = TipoCannone.values()[RandomTipo()];
		
	}

	

	public TipoCannone getTipoCannone() {
		return tipoCannone;
	}


	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoCannone.values().length);
		
		return pick;
		
	}


	
	
	
	

	

	

	
	
	
	

}
