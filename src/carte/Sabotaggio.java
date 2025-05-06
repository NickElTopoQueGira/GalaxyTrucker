package carte;

import java.util.Random;

public class Sabotaggio extends EventiSpeciali {

	public Sabotaggio (int lvl) {
		
		super(lvl, TipoCarta.SABOTAGGIO);
	}
	
	public int RisultatiDadi() {
		
		Random random = new Random();
		
		int d1 = random.nextInt(6) + 1;
		int d2 = random.nextInt(6) + 1;
		
		return d1+d2;
	}
}
