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
			crt = new PolvereStellare();
		break;
		case 2: // ZONA_GUERRA,
			crt = new ZonaGuerra();
		break;
		case 3: // PIOGGIA_METEORITI,
			crt = new PioggiaMeteoriti();
		break;
		case 4: // SPAZIO_APERTO,
			crt = new SpazioAperto();
		break;
		case 5: // SCHIAVISTI,
			crt = new Schiavisti();
		break;
		case 6: // CONTRABBANDIERI,
			crt = new Contrabbandieri();
		break;
		case 7: // PIRATI,
			crt = new Pirati();
		break;
		case 8: // STAZIONE_ABBANDONATA,
			crt = new StazioneAbbandonata();
		break;
		case 9: // NAVE_ABBANDONATA,
			crt = new NaveAbbandonata();
		break;
		case 10: // PIANETA,
			crt = new Pianeta();
		break;
		case 11: // EPIDEMIA,
			crt = new Epidemia();
		break;
		case 12: // SABOTAGGIO,
			crt = new Sabotaggio();
		break;
		default:
			System.out.println("ERROR: creazione carta avventura del mazzo (errorTipe: switch) (class: Carta)");
		break;
		}
		
		
		
		return crt;
	}
}
