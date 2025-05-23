package carte;

import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.Pedina;

public class NaveAbbandonata extends Carta {
	
	private int guadagno, giornipersi, perditaequipaggio;
	private final ComunicazioneConUtente stampa;
	
	/**
	 * Costruttore NaveAbbandonata
	 * super -> gli passiamo il lvl della carta e il tipo
	 * metodo: GeneraValori() per generare i attributi della carta
	 * @param lvl
	 */
	public NaveAbbandonata (int lvl) {
		
		super(lvl, TipoCarta.NAVE_ABBANDONATA);
		GeneraValori();
		stampa= ComunicazioneConUtente.getIstanza();
		
	}
	
	/**
	 * Metodo per riodinare le varie creazioni dei attributi
	 * e chiama i metodi:
	 * -GeneraGuadagno()
	 * -GeneraPerditaEquipaggio()
	 * -GeneraGiorniPersi()
	 */
	private void GeneraValori() {
		GeneraGuadagno();
		GeneraPerditaEquipaggio();
		GeneraGiorniPersi();
	}
	
	/**
	 * Metodo che in base al livello della carta genera in maniera 
	 * random l'attribbuto guadagno della carta 
	 * this.guadagno -> se il giocatore completa la carta guadagnerà
	 * 					ciceverà 'this.guadagno' crediti cosmici
	 */
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
	
	/**
	 * Metodo che in base al livello della carta e al numero di 'this.guadagno' 
	 * genera in maniera random l'attribbuto numero dell'equipaggio che si perderà
	 * this.perditaequipaggio -> il giocatore per completare la carta (e avere i crediti)
	 * 							 dovra perdere 'this.perditaequipaggio' mebri dell'equipaggio
	 */
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
	
	/**
	 * Metodo  che in base al numero di 'this.guadagno' 
	 * genera in maniera random l'attribbuto numero dei giorni 
	 * di viaggio persi 
	 * 
	 * this.giornipersi -> al completamento dellla carta il giocatore
	 * 					   perderà 'this.giornipersi' giorni di volo
	 */
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
	/**
	 * esegue la carta:
	 * 
	 * fa sceliere uno ad uno se vuole completare la carta (solo se ha i parametri corretti)
	 * e consegna la ricompensa tgliendo i giorni di volo
	 */
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) { 
		
		boolean isCartaCompletata = false;
		int elenco = -1;
		
		do {
			elenco++;
			
			if(elencoPedine.get(elenco).getGiocatore().getNave().getEquipaggio() >= this.perditaequipaggio) { // CONTROLLA SE HA ABBASTANZA NUMERO DI EQUIPAGGIO
				
				if(elencoPedine.get(elenco).sceltaScambioCreditiConGiorni(giornipersi, guadagno, perditaequipaggio)) { // FA SCEGLIERE AL GIOCATORE SE VUOLE COMPLETARE LA CARTYA
					
					elencoPedine.get(elenco).selezionaEquipaggioDaEliminare(this.perditaequipaggio);
					
					elencoPedine.get(elenco).getGiocatore().aggiornaCrediti(this.guadagno);
					
					elencoPedine.get(elenco).getTabellone().muoviPedina(elencoPedine.get(elenco), -this.giornipersi);
					
					isCartaCompletata = true;
				}
			}else {
				
				stampa.println("LA NAVE NON HA ABBASTANZA EQUIPAGGIO ");
			}
			
		}while(!isCartaCompletata && elenco<elencoPedine.size()); //CONTROLLO SE LA CARTA è STATA COMPLETATA O SE NON CI SONO ALTRI GIOCATORI
		
		return elencoPedine;
	}
}
