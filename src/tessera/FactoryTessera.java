package tessera;

import java.util.Random;

public class FactoryTessera {
	
	
public Tessera estraiTipo(int posizioneX, int posizioneY) {
		

	    int tipo= RandomTipo();
		switch(tipo) {
		
		case 0:
			return new Cannone(posizioneX, posizioneY);

		case 1:
			return new Motore(posizioneX, posizioneY);
		
		case 2:
			return new ModuloPasseggeri(posizioneX, posizioneY);

		case 3:
			return new ModuloPasseggeri(posizioneX, posizioneY);
		case 4:
			return new ModuloPasseggeri(posizioneX, posizioneY);
		case 5:
			return new ModuloPasseggeri(posizioneX, posizioneY);
		case 6:
			return new ModuloPasseggeri(posizioneX, posizioneY);
		default:
			return new ModuloPasseggeri(posizioneX, posizioneY);

		}	
	}
	
	
	private int RandomTipo(){
		int pick= new Random().nextInt(TipoTessera.values().length);
		return pick;
	}
}
