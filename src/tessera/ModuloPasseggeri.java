package tessera;

import java.util.Random;

public class ModuloPasseggeri extends Tessera implements GeneraTessera{
	private final TipoModuloPasseggeri tipoModuloPasseggeri;
	
	
	

	public ModuloPasseggeri(TipoTessera tipotessera) {
		super(tipotessera);
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
	public void stampa() {
		System.out.println(this.getTipoModuloPasseggeri());
		
	}
	

}
