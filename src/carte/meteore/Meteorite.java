package carte.meteore;

import java.util.Random;

public class Meteorite {
	
	private static PuntiCardinali direzione;
	
	public Meteorite() {
		 this.direzione = CasualDirezzione();
	}
	public Meteorite(PuntiCardinali direzione) {
		 this.direzione = direzione;
	}
	
	PuntiCardinali CasualDirezzione() {
		PuntiCardinali s = null;
		Random random = new Random();
		
		int x = random.nextInt(4) + 1;
		
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
