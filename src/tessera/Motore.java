package tessera;

import java.util.Random;

public class Motore extends Tessera{

	private final TipoMotore tipoMotore;
	
	public Motore(int posizioneX, int posizioneY) {
		super(posizioneX, posizioneY,TipoTessera.MOTORE);
		this.tipoMotore =TipoMotore.values()[RandomTipo()];
		
	}

	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoTessera.values().length);
		return pick;
	}

	@Override
	public void generacasualmente() {
		// TODO Auto-generated method stub
		
	}


	

	
	
	
	

}
