package carte;

import java.util.*;

public class Carta {
	
	public int lvl;
	public TipoCarta tipo;
	private ArrayList<Carta> buffer; // mazzo
	
	public Carta(int lvl) {
		this.lvl = lvl;
		this.tipo = null;
		this.buffer = new ArrayList<>(); // nuovo mazzo
		CreaMazzo(lvl);   // va nel mazzo
	}
	public Carta(int lvl , TipoCarta  tipo) {  // va nel mazzo
		
		this.lvl = lvl;
		this.tipo = tipo;
	}
	
	public void RigeneraMazzo(int lvl) {  // va nel mazzo
		
		AzzeraMazzo();
		CreaMazzo(lvl);
	}
	
	private void AzzeraMazzo() {  // va nel mazzo
		
		this.buffer.clear();
	}
	
	public void CreaMazzo(int lvl) {  // va nel mazzo
		
		switch(lvl) {
		case 1:
			for(int i=0; i<8;  i++) {
				buffer.add(this.CreaCartaRandom(1));
			}
		break;
		case 2:
			for(int i=0; i<4;  i++) {
				buffer.add(this.CreaCartaRandom(1));
			}
			for(int i=0; i<8;  i++) {
				buffer.add(this.CreaCartaRandom(2));
			}
		break;
		case 3:
			for(int i=0; i<4;  i++) {
				buffer.add(this.CreaCartaRandom(1));
			}
			for(int i=0; i<4;  i++) {
				buffer.add(this.CreaCartaRandom(2));
			}
			for(int i=0; i<8;  i++) {
				buffer.add(this.CreaCartaRandom(3));
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
		Carta crt = new Carta(lvl);
		
		if(lvl == 3) {
			x = random.nextInt(12) + 1;
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

	public int getLvl() { 
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public TipoCarta getTipo() {
		return tipo;
	}

	public void setTipo(TipoCarta tipo) {
		this.tipo = tipo;
	}

	public ArrayList<Carta> getBuffer() {   // va nel mazzo
		return buffer;
	}

	public void setBuffer(ArrayList<Carta> buffer) {   // va nel mazzo
		this.buffer = buffer;
	}
	
	
}
