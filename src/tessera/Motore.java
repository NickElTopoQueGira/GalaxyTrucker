package tessera;

import java.util.Random;

public class Motore extends Tessera implements GeneraTessera{

	private final TipoMotore tipoMotore;
	
	public Motore(int posizioneX, int posizioneY) {
		super(posizioneX, posizioneY);
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

	@Override
	public void setLati() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	

}
