package carte.meteore;

import java.util.Random;

public class Meteorite {
	
	private PuntiCardinali direzione;
	private TypeMeteora type;
	private int dado;
	
	public Meteorite(TypeMeteora t) {
		this.direzione = casualDirezione();
		this.dado = RisultatiDadi();
		this.type = t;
		
	}
	
	public Meteorite(PuntiCardinali direzione, TypeMeteora t) {
		this.direzione = direzione;
		this.dado = RisultatiDadi();
		this.type = t;
	}
	
	private PuntiCardinali casualDirezione() {
		Random random = new Random();
		int x = random.nextInt(4) + 1;
		
		return switch (x) {
			case 1 -> PuntiCardinali.NORD;
			case 2 -> PuntiCardinali.EST;
			case 3 -> PuntiCardinali.OVEST;
			case 4 -> PuntiCardinali.SUD;
			default -> {
				System.out.println("ERROR: random direzione (class: Meteorite)");
				yield null;
			}
		};
	}
	protected int RisultatiDadi() {          //// da modificare tuuutto
		
		Random random = new Random();
		
		int d1 = random.nextInt(6) + 1;
		int d2 = random.nextInt(6) + 1;
		
		return d1+d2;
	}

	public PuntiCardinali getDirezione() {
		return direzione;
	}

	public void setDirezione(PuntiCardinali direzione) {
		this.direzione = direzione;
	}

	public TypeMeteora getType() {
		return type;
	}

	public void setType(TypeMeteora type) {
		this.type = type;
	}

	public int getDado() {
		return dado;
	}

	public void setDado(int dado) {
		this.dado = dado;
	}


	
}
