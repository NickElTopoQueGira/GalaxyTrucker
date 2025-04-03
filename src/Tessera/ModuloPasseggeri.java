package Tessera;

import java.util.Random;

public class ModuloPasseggeri extends Tessera implements GeneraTessera{
	private TipoModuloPasseggeri tipoModuloPasseggeri;
	
	
	

	public ModuloPasseggeri() {
		
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
	

}
