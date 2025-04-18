package carte.meteore;

import java.util.Random;

public class Meteorite {
	
	private static PuntiCardinali direzione;
	private static int dado;
	
	public Meteorite() {
		 this.direzione = CasualDirezzione();
	}
	public Meteorite(PuntiCardinali direzione, int d) {
		 this.direzione = direzione;
		 this.dado = d;
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
	public static PuntiCardinali getDirezione() {
		return direzione;
	}
	public static void setDirezione(PuntiCardinali direzione) {
		Meteorite.direzione = direzione;
	}
	public static int getDado() {
		return dado;
	}
	public static void setDado(int dado) {
		Meteorite.dado = dado;
	}
	
	

}
