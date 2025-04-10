package tessera.motore;

import java.util.Random;


import tessera.Tessera;
import tessera.TipoLato;
import tessera.TipoTessera;
import tessera.cannone.TipoCannone;

public class Motore extends Tessera{

	
	private TipoLato latoMotore=TipoLato.UP;
	private final TipoMotore tipoMotore;
	
	public Motore() {
		super(TipoTessera.MOTORE);
		this.tipoMotore =TipoMotore.values()[RandomTipo()];
		
	}
	

	public TipoLato getLatoMotore() {
		return latoMotore;
	}


	public void setLatoMotore(TipoLato latoMotore) {
		this.latoMotore = latoMotore;
	}



	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoCannone.values().length);
		return pick;
	}


	@Override
	protected void ruotaTessera() {
		this.latoMotore.next();
	}



	


	

	
	
	
	

}
