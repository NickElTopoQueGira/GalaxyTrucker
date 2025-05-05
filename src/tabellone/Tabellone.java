package tabellone;

import java.util.*;


import carte.*;
import partita.Pedina;

public class Tabellone {
	
	private int lvl, numero_giocatori;
	private static ArrayList<Pedina> pedine;
	
	private static ArrayList<Carta> mazzo;  
	
	public Tabellone (int lvl, int ngiocatori) {
		
		this.lvl = lvl;
		this.numero_giocatori = ngiocatori;
		ImpostaNumPosizioni(lvl);
	
		this.mazzo = new ArrayList<>();   // va nel mazzo
		
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
	private void AssegnaMazzo() {
		
		this.mazzo = this.mazz.getLista();
	}
	
	private void NuovoMazzo(int lvlv) {
		
		this.mazz.RigeneraMazzo(lvlv);
		AssegnaMazzo();
		
	}

	
	
	
	
	
	
	
	
	
	
	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public int getNgiocatori() {
		return numero_giocatori;
	}

	public void setNgiocatori(int ngiocatori) {
		this.numero_giocatori = ngiocatori;
	}

	public int getNposizioni() {
		return nposizioni;
	}

	public void setNposizioni(int nposizioni) {
		this.nposizioni = nposizioni;
	}

	public static Mazzo getMazz() {
		return mazz;
	}

	public static void setMazz(Mazzo mazz) {
		Tabellone.mazz = mazz;
	}

	public ArrayList<Carta> getMazzo() {
		return mazzo;
	}

	public void setMazzo(ArrayList<Carta> mazzo) {
		this.mazzo = mazzo;
	}
}
