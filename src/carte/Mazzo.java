package carte;

import java.util.*;

import carte.nemico.*;
import carte.eventoSpeciale.*;

public class Mazzo {
	
	protected static ArrayList<Carta> lista;
	private int[][] conteggio;
	
	public Mazzo(int lvl) {
		
		this.lista = new ArrayList<>();
		
		conteggio = new int[][] {
		    {1, 1, 3, 4, 1, 1, 1, 2, 2, 4, 0, 0}, // livello 1
		    {1, 1, 3, 3, 1, 1, 1, 2, 2, 4, 1, 0},  // livello 2
		    {0, 1, 3, 3, 1, 1, 1, 2, 2, 4, 1, 1}   // livello 3
		};
		CreaMazzo(lvl);
	}
	
	public void RigeneraMazzo(int lvl) { 
		
		AzzeraMazzo();
		CreaMazzo(lvl);
	}
	
	private void AzzeraMazzo() {  
		
		this.lista.clear();
	}
	
	public void CreaMazzo(int lvl) {  
		
		switch(lvl) {
		case 1:
			for(int i=0; i<8;  i++) {
				lista.add(this.CreaCartaRandom(1));
			}
		break;
		case 2:
			for(int i=0; i<4;  i++) {
				lista.add(this.CreaCartaRandom(1));
			}
			for(int i=0; i<8;  i++) {
				lista.add(this.CreaCartaRandom(2));
			}
		break;
		case 3:
			for(int i=0; i<4;  i++) {
				lista.add(this.CreaCartaRandom(1));
			}
			for(int i=0; i<4;  i++) {
				lista.add(this.CreaCartaRandom(2));
			}
			for(int i=0; i<8;  i++) {
				lista.add(this.CreaCartaRandom(3));
			}
		break;
		default:
			System.out.println("ERROR: creazione mazzo (errorTipe: switch) (class: Carta)");
		break;
		}
		
	}
	
	private Carta CreaCartaRandom(int lvl){  // va nel mazzo
		
		int x = 0;
		Random random = new Random();
		Carta crt = null;
		
		if(lvl == 3) {
			x = random.nextInt(11) + 2;
		}else if(lvl == 2){
			x = random.nextInt(11) + 1;
		}else {
			x = random.nextInt(10) + 1;
		}
		
		switch(x) {
		case 1: // POLVERE_STELLARE,
			crt = new PolvereStellare(lvl);
		break;
		case 2: // ZONA_GUERRA,
			crt = new ZonaGuerra(lvl);
		break;
		case 3: // PIOGGIA_METEORITI,
			crt = new PioggiaMeteoriti(lvl);
		break;
		case 4: // SPAZIO_APERTO,
			crt = new SpazioAperto(lvl);
		break;
		case 5: // SCHIAVISTI,
			crt = new Schiavisti(lvl);
		break;
		case 6: // CONTRABBANDIERI,
			crt = new Contrabbandieri(lvl);
		break;
		case 7: // PIRATI,
			crt = new Pirati(lvl);
		break;
		case 8: // STAZIONE_ABBANDONATA,
			crt = new StazioneAbbandonata(lvl);
		break;
		case 9: // NAVE_ABBANDONATA,
			crt = new NaveAbbandonata(lvl);
		break;
		case 10: // PIANETA,
			crt = new Pianeta(lvl);
		break;
		case 11: // EPIDEMIA,
			crt = new Epidemia(lvl);
		break;
		case 12: // SABOTAGGIO,
			crt = new Sabotaggio(lvl);
		break;
		default:
			System.out.println("ERROR: creazione carta avventura del mazzo (errorTipe: switch) (class: Carta)");
		break;
		}
		
		return crt;
	}
	
	@Override
	public String toString() {
		String temp="";
		for(int i=0; i<lista.size(); i++) {
			
			temp=temp+lista.get(i).toString()+"\n\n";
			
		}
		return temp;
		
	}

	public ArrayList<Carta> getLista() {
		return lista;
	}

	public void setLista(ArrayList<Carta> buffer) {
		this.lista = buffer;
	}
}
