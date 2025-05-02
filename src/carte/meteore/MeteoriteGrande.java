package carte.meteore;

import java.util.Random;

public class MeteoriteGrande extends Meteorite {

	
	public MeteoriteGrande(int lvl) {
		
		super(CasualDirezzione(lvl), TypeMeteora.METEORITE_GRANDE);
	}
	
	
	static PuntiCardinali CasualDirezzione(int lvl) {
		PuntiCardinali s = null;
		Random random = new Random();
		
		int x = 0;
		switch(lvl) {
		case 1->{
			x=1;
		}
		case 2->{
			x = random.nextInt(3) + 1;
		}
		case 3->{
			x = random.nextInt(4) + 1;
		}
		default->{
			System.out.println("ERROR: scelta del random in base al livello della carta (errorTipe: switch) (class: Meteorite)");
		}
		}
		
		switch(x) {
		case 1->{
			return PuntiCardinali.NORD;
		}
		case 2->{
			return PuntiCardinali.EST;
		}
		case 3->{
			return PuntiCardinali.OVEST;
		}
		case 4->{
			return PuntiCardinali.SUD;
		}
		default->{
			System.out.println("ERROR: random della direzione del meteorite (errorTipe: switch) (class: Meteorite)");
		}
		}
		
		return s;
	}
}
