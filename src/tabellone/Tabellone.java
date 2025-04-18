package tabellone;

import java.util.*;


import carte.Carta;

public class Tabellone {
	
	private int lvl, ngiocatori, nposizioni;
	private static Carta crt;   // da cancellare
	private ArrayList<Carta> buffer;   // va nel mazzo
	
	public Tabellone (int lvl, int ngiocatori) {
		
		this.lvl = lvl;
		this.ngiocatori = ngiocatori;
		ImpostaNumPosizioni(lvl);
		this.crt = new Carta(lvl); // da cancelllare
		this.buffer = new ArrayList<>();   // va nel mazzo
		GeneraMazzo();   // va nel mazzo
		
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
	
	void GeneraMazzo() {   // va nel mazzo
		this.buffer = this.crt.getBuffer();
	}
	
	void GeneraMazzo(int lvl) {   // va nel mazzo
		
		this.crt.RigeneraMazzo(lvl);
		this.buffer = this.crt.getBuffer();
	}
	

}
