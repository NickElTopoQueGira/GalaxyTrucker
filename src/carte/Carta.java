package carte;

import java.util.*;

public class Carta {
	public int lvl;
	public TipoCarta tipo;
	
	public Carta(int lvl) {
		this.lvl = lvl;
	}
	
	public Carta CreaCartaRandom(int lvl){
		
		int x = 0;
		Random random = new Random();
		Carta crt = new Carta(lvl);
		
		if(lvl == 3) {
			x = random.nextInt(12) + 1;
		}else {
			x = random.nextInt(10) + 1;
		}
		
		switch(x) {
		case 1: // POLVERE_STELLARE,
			
		break;
		case 2: // ZONA_GUERRA,
			
		break;
		case 3: // PIOGGIA_METEORITI,
			
		break;
		case 4: // SPAZIO_APERTO,
		
		break;
		case 5: // SCHIAVISTI,
			
		break;
		case 6: // CONTRABBANDIERI,
			
		break;
		case 7: // PIRATI,
		
		break;
		case 8: // STAZIONE_ABBANDONATA,
			
		break;
		case 9: // NAVE_ABBANDONATA,
			
		break;
		case 10: // PIANETA,
		
		break;
		case 11: // EPIDEMIA,
			
		break;
		case 12: // SABOTAGGIO,
			
		break;
		default:
			System.out.println("ERROR: creazione carta avventura del mazzo (errorTipe: switch) (class: Carta)");
		break;
		}
		
		
		
		return crt;
	}
}
