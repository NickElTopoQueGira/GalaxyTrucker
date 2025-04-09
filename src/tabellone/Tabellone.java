package tabellone;

import java.util.*;


import carte.Carta;

public class Tabellone {
	
	private int lvl, ngiocatori, nposizioni;
	private static Carta crt;
	private ArrayList<Carta> buffer;
	
	public Tabellone (int lvl, int ngiocatori) {
		
		this.lvl = lvl;
		this.ngiocatori = ngiocatori;
		ImpostaNumPosizioni(lvl);
		this.crt = new Carta(lvl);
		this.buffer = new ArrayList<>();
		GeneraMazzo();
		
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
	
	void GeneraMazzo() {
		this.buffer = this.crt.getBuffer();
	}
	
	void GeneraMazzo(int lvl) {
		
		this.crt.RigeneraMazzo(lvl);
		this.buffer = this.crt.getBuffer();
	}
	

}
