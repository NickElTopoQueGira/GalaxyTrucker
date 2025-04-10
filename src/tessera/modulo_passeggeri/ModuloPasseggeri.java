package tessera.modulo_passeggeri;

import java.util.Random;


import tessera.Tessera;
import tessera.TipoTessera;

public class ModuloPasseggeri extends Tessera{
	private final TipoModuloPasseggeri tipoModuloPasseggeri;
	
	
	

	public ModuloPasseggeri() {
		super(TipoTessera.MODULO_PASSEGGERI);
		this.tipoModuloPasseggeri =TipoModuloPasseggeri.values()[RandomTipo()];
		
	}




	public TipoModuloPasseggeri getTipoModuloPasseggeri() {
		return tipoModuloPasseggeri;
	}

	
	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoModuloPasseggeri.values().length);
		return pick;
		
		
	}




	@Override
	protected void ruotaTessera() {
		// TODO Auto-generated method stub
		
	}



}
