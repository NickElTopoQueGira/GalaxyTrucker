package carte.meteore;

import java.util.Random;

public class Meteorite {
	
	private static PuntiCardinali direzione;
	
	public Meteorite() {
		 direzione = CasualDirezzione();
	}
	
	PuntiCardinali CasualDirezzione() {
		PuntiCardinali s = null;
		Random random = new Random();
		
		int x = random.nextInt(4) + 1;
		
		switch(x) {
		case 1->{
			
		}
		case 2->{
			
		}
		case 3->{
			
		}
		case 4->{
			
		}
		default->{
			System.out.println("ERROR: random della direzione del meteorite (errorTipe: switch) (class: Meteorite)");
		}
		}
		
		return s;
	}
	
	

}
