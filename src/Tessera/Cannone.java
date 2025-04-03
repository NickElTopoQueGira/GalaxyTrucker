package Tessera;

import java.util.Random;

public class Cannone extends Tessera implements GeneraTessera{
	
	private TipoCannone tipoCannone;

	public Cannone() {
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
