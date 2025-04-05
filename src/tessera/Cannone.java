package tessera;

import java.util.Random;

public class Cannone extends Tessera implements GeneraTessera{
	
	private final TipoCannone tipoCannone;

	public Cannone(TipoTessera tipotessera) {
		super(tipotessera);
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



	@Override
	public void stampa() {
		System.out.println(this.getTipoCannone());
		
	}

	
	
	

	

	

	
	
	
	

}
