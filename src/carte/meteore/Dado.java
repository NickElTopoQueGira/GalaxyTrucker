package carte.meteore;

import java.util.Random;

public class Dado {
	
	public Dado() {
	}
	public int dadoSingolo() {
		
		Random random = new Random();
		
		int dado = random.nextInt(6) + 1;
		
		return dado;
	}
	public int dadiDoppi() {
		
		int d1 = this.dadoSingolo();
		int d2 = this.dadoSingolo();
		
		return d1+d2;
	}
}
