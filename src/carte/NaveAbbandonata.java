package carte;

import java.util.*;

import gioco.ComunicazioneConUtente;
import partita.Pedina;

public class NaveAbbandonata extends Carta {
	
	private int guadagno, giornipersi, perditaequipaggio;
	private ComunicazioneConUtente stampa;
	
	public NaveAbbandonata (int lvl) {
		
		super(lvl, TipoCarta.NAVE_ABBANDONATA);
		GeneraValori();
		stampa= ComunicazioneConUtente.getIstanza();
		
	}
	
	private void GeneraValori() {
		GeneraGuadagno();
		GeneraPerditaEquipaggio();
		GeneraGiorniPersi();
	}
	
	private void GeneraGuadagno() {
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 1->{
			this.guadagno = random.nextInt(3) + 3;  //3-5
		}
		case 2->{
			this.guadagno = random.nextInt(3) + 6;  //6-8
		}
		case 3->{
			this.guadagno = random.nextInt(3) + 9;  //9-11
		}
		default->{
			stampa.printError("ERROR: quantita ghuadagno della carta (errorTipe: switch) (class: NaveAbbandonata)");
		}
		}
	}
	
	private void GeneraPerditaEquipaggio() {
		
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 1->{
			if(this.guadagno == 3) {
				this.perditaequipaggio = 2;
				
			}else if(this.guadagno == 5) {
				this.perditaequipaggio = 3;
				
			}else {
				this.perditaequipaggio = random.nextInt(2) + 2;
			}
		}
		case 2->{
			if(this.guadagno == 6) {
				this.perditaequipaggio = 4;
				
			}else if(this.guadagno == 8) {
				this.perditaequipaggio = 5;
				
			}else {
				this.perditaequipaggio = random.nextInt(2) + 4;
			}
		}
		case 3->{
			if(this.guadagno == 9) {
				this.perditaequipaggio = 6;
				
			}else if(this.guadagno == 11) {
				this.perditaequipaggio = 7;
				
			}else {
				this.perditaequipaggio = random.nextInt(2) + 6;
			}
		}
		default->{
			stampa.printError("ERROR: calcolo numero dei equipaggi persi (errorTipe: switch) (class: NaveAbbandonata)");
		}
		}
	}
	
	private void GeneraGiorniPersi() {
		
		if(this.guadagno < 8) {
			
			this.giornipersi = 1;
		}else {
			this.giornipersi = 2;
		}
	}

	public int getGuadagno() {
		return guadagno;
	}

	public void setGuadagno(int guadagno) {
		this.guadagno = guadagno;
	}

	public int getGiornipersi() {
		return giornipersi;
	}

	public void setGiornipersi(int giornipersi) {
		this.giornipersi = giornipersi;
	}

	public int getPerditaequipaggio() {
		return perditaequipaggio;
	}

	public void setPerditaequipaggio(int perditaequipaggio) {
		this.perditaequipaggio = perditaequipaggio;
	}
	@Override
	public String toString() {
		String temp="";
		temp=temp+"\nLivello carta:"+this.lvl+
				"\nTipo carta:"+this.tipo+
				"\nGiorni Penalità:"+this.giornipersi+"\n"+
				"\nEquipaggio Penalità:"+this.perditaequipaggio+"\n"+
				"\nGuadagno:"+this.guadagno+"\n"; //guadagno, giornipersi, perditaequipaggio;

		
		return temp;
	}

	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		boolean isCartaCompletata = false;
		int elenco = -1;
		
		do {
			elenco++;
			
			if(elencoPedine.get(elenco).getGiocatore().getNave().getEquipaggio() >= this.perditaequipaggio) {
				
				if(elencoPedine.get(elenco).sceltaScambioCreditiConGiorni(giornipersi, guadagno, perditaequipaggio)) {
					
					elencoPedine.get(elenco).selezionaEquipaggioDaEliminare(this.perditaequipaggio);
					
					elencoPedine.get(elenco).getGiocatore().aggiornaCrediti(this.guadagno);
					
					elencoPedine.get(elenco).muoviPedina(-this.giornipersi);
					
					isCartaCompletata = true;
				}
			}else {
				
				stampa.println("LA NAVE NON HA ABBASTANZA EQUIPAGGIO ");
			}
			
		}while(!isCartaCompletata && elenco<elencoPedine.size());
		
		return elencoPedine;
	}

	


	
}
