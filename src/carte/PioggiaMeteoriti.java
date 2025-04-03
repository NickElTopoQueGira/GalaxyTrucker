package carte;

import carte.meteore.*;
import java.util.*;

public class PioggiaMeteoriti extends Carta {
	
	public ArrayList<Meteorite> meteoriti;
	
	public PioggiaMeteoriti (int lvl) {
		
		super(lvl, TipoCarta.PIOGGIA_METEORITI);
	}
	
	void GeneraValori() {  //VALORI ATTUALMENTE CASUALI E DA RIVEDERE
		
		Random random = new Random();
		int nmeteore;
		
		switch(this.lvl) {
		case 1->{
			nmeteore = random.nextInt(4) + 3;
			
			for(int i=0; i<nmeteore; i++) {
				meteoriti.add(new Meteorite());
			}
		}
		case 2->{
			nmeteore = random.nextInt(8) + 5;
			
			for(int i=0; i<nmeteore; i++) {
				meteoriti.add(new Meteorite());
			}
		}
		case 3->{
			nmeteore = random.nextInt(10) + 1;
			
			for(int i=0; i<nmeteore; i++) {
				meteoriti.add(new Meteorite());
			}
		}
		default ->{
			
		}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	int RisultatiDadi() {
		
		Random random = new Random();
		
		int d1 = random.nextInt(6) + 1;
		int d2 = random.nextInt(6) + 1;
		
		return d1+d2;
	}
}
