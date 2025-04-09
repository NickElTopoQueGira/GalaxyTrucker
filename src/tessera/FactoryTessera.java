package tessera;

import java.util.Random;


import tessera.cannone.Cannone;
import tessera.motore.Motore;
import tessera.modulo_passeggeri.ModuloPasseggeri;

public class FactoryTessera implements GeneraTessera{
	
	
public Tessera estraiTipo() {
		

	    int tipo= RandomTipo();
		switch(tipo) {
		
		case 0:
			return new Cannone();

		case 1:
			return new Motore();
		
		case 2:
			return new ModuloPasseggeri();

		case 3:
			return new ModuloPasseggeri();
		case 4:
			return new ModuloPasseggeri();
		case 5:
			return new ModuloPasseggeri();
		case 6:
			return new ModuloPasseggeri();
		default:
			return new ModuloPasseggeri();

		}	
	}
	
	
	public int RandomTipo(){
		int pick= new Random().nextInt(TipoTessera.values().length-1);
		return pick;
	}
}
