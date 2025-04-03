package Tessera;

import java.util.Random;

public class Motore extends Tessera{

	private TipoMotore tipoMotore;
	
	public Motore() {
		
		this.tipoMotore =TipoMotore.values()[RandomTipo()];
		
	}

	public TipoMotore getTipomotore() {
		return tipoMotore;
	}

	
	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoMotore.values().length);
		return pick;
		
		
	}
	

}
