package tabellone;

import java.util.*;

import carte.Carta;

public class Tabellone {
	
	private int lvl, ngiocatori, nposizioni;
	private static Carta crt = new Carta(1);
	private List<Carta> buffer = new ArrayList<>();
	
	public Tabellone (int lvl, int ngiocatori) {
		this.lvl = lvl;
		this.ngiocatori = ngiocatori;
		ImpostaNumPosizioni(lvl);
	}
	
	void ImpostaNumPosizioni(int lvl) {
		switch(lvl) {
		case 1:
			this.nposizioni = 18;
		break;
		case 2:
			this.nposizioni = 24;
		break;
		case 3:
			this.nposizioni = 34;
		break;
		default:
			System.out.println("ERROR: inserimento numero posizione (errorTipe: switch) (class: Tabellone)");
		break;
		}	
	}
	
	void CreaMazzo(int lvl) {
		switch(lvl) {
		case 1:
			for(int i=0; i<8;  i++) {
				buffer.add(crt.CreaCartaRandom(1));
			}
		break;
		case 2:
			for(int i=0; i<4;  i++) {
				buffer.add(crt.CreaCartaRandom(1));
			}
			for(int i=0; i<8;  i++) {
				buffer.add(crt.CreaCartaRandom(2));
			}
		break;
		case 3:
			for(int i=0; i<4;  i++) {
				buffer.add(crt.CreaCartaRandom(1));
			}
			for(int i=0; i<4;  i++) {
				buffer.add(crt.CreaCartaRandom(2));
			}
			for(int i=0; i<8;  i++) {
				buffer.add(crt.CreaCartaRandom(3));
			}
		break;
		default:
			System.out.println("ERROR: creazione mazzo (errorTipe: switch) (class: Tabellone)");
		break;
		}
		
	}
}
