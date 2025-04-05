package tessera;

import java.util.Random;

public class Motore extends Tessera implements GeneraTessera{

	private final TipoMotore tipoMotore;
	
	public Motore(TipoTessera tipotessera) {
		super(tipotessera);
		this.tipoMotore =TipoMotore.values()[RandomTipo()];
		
	}

	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	
	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoMotore.values().length);
		return pick;
		
		
	}
	
	@Override
	public void stampa() {
		System.out.println(this.getTipoMotore());
		
	}
	

}
