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
		int nmeteore, grandezza;
		
		switch(this.lvl) {
		case 1->{
			nmeteore = random.nextInt(4) + 3; // MINIMO 3 MAX 6
			
			for(int i=0; i<nmeteore; i++) {
				
				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		case 2->{
			nmeteore = random.nextInt(4) + 5;  // MINIMO 5 MAX 8
			
			for(int i=0; i<nmeteore; i++) {

				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		case 3->{
			nmeteore = random.nextInt(5) + 6;  // MINIMO 6 MAX 10
			
			for(int i=0; i<nmeteore; i++) {

				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		default ->{
			System.out.println("ERROR: numerazione meteoriti (errorTipe: switch) (class: PioggiaMeteorite)");
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
