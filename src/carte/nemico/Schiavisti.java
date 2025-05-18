package carte.nemico;

import java.util.*;
import carte.*;
import gioco.ComunicazioneConUtente;
import partita.Pedina;

public class Schiavisti extends Nemici {
	
	private int potenzanecc; // potenza necessaria
	private int penalitaequipaggio; // numeri merci persi
	private int penalitagiorni; // numeri giorni persi
	private int guadagno;
	private ComunicazioneConUtente stampa;
	
	/**
	 * Costruttore Schiavisti
	 * super -> gli passiamo il livello e il tipo di carta SCHIAVISTI
	 * inizializza i valori relativi al nemico tramite il metodo GeneraValori()
	 * @param lvl 
	 */
	public Schiavisti (int lvl) {
		
		super(lvl, TipoCarta.SCHIAVISTI);
		GeneraValori();
		stampa= ComunicazioneConUtente.getIstanza();
	}
	
	/**
     * Metodo di supporto che genera i valori caratteristici della carta:
     * potenza necessaria, penalità in termini di equipaggio e giorni, e guadagno.
     */
	private void GeneraValori() {
		GeneraGuadagno();
		GeneraPotenzaNecessaria();
		GeneraEquipaggioPerso();
		GeneraGiorniPersi();
	}
	
	/**
     * Genera casualmente il numero di crediti che si possono guadagnare
     * in caso di vittoria, in base al livello della carta.
     */
	private void GeneraGuadagno() {
		Random random = new Random();
		
		switch(this.lvl) {
		case 3 ->{
			this.guadagno = random.nextInt(3) + 10; // 10-12
		}
		case 2 ->{
			this.guadagno = random.nextInt(3) + 7; // 7-9
		}
		case 1 ->{
			this.guadagno = random.nextInt(3) + 4; // 4-6
		}
		default ->{
			stampa.printError("ERROR: scelta randomica delle merci (errorTipe: switch) (class: Conmtrabandieri)");
		}
		}
		
	}
	
	/**
     * Genera la potenza necessaria per sconfiggere il nemico,
     * in base al livello della carta.
     */
	private void GeneraPotenzaNecessaria() {
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 1 ->{
			this.potenzanecc = random.nextInt(3) + 4;  //4-6
		}		
		case 2 ->{
			this.potenzanecc = random.nextInt(3) + 6;  //6-8
		}		
		case 3->{
			this.potenzanecc = random.nextInt(3) + 8;  //8-10
		}
		default ->{
			stampa.printError("ERROR: scelta randomica della potenza necessria per lo suconfiggere i contrabbandieri (errorTipe: switch) (class: Contrabbandieri)");
		}
			
		}
	}
	
	/**
	 * Imposta la quantità di membri dell'equipaggio che verranno persi se la nave
	 * non ha potenza sufficiente a sconfiggere i nemici.
	 * Il valore dipende dal livello della carta.
	 */
	private void GeneraEquipaggioPerso() {
		switch(this.lvl) {
		case 3 ->{
			this.penalitaequipaggio = 5;
		}		
		case 2 ->{
			this.penalitaequipaggio = 4;
		}		
		case 1->{
			this.penalitaequipaggio = 3;
		}
		default ->{
			stampa.printError("ERROR: scelta numeri merci perse in caso in cui la potenza non basta (errorTipe: switch) (class: Contrabbandieri)");
		}
			
		}
	}
	
	/**
	 * Genera la penalità in giorni da subire se si desidera ottenere il guadagno in crediti.
	 * Il valore varia in base al livello della carta.
	 */
	private void GeneraGiorniPersi() {
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 3 ->{
			this.penalitagiorni = 2;
		}		
		case 2 ->{
			this.penalitagiorni = random.nextInt(2) + 1;
		}		
		case 1->{
			this.penalitagiorni = 1;
		}
		default ->{
			stampa.printError("ERROR: scelta numeri giorni persi in caso in cui si decide diu prendere le merci (errorTipe: switch) (class: Contrabbandieri)");
		}
			
		}
	}
	
	@Override
	public String toString() {
		String temp="";
		temp=temp+"\nLivello carta:"+this.lvl+
				"\nPotenza necessaria:"+this.potenzanecc+
				"\nPenaltà Equipaggio:"+this.penalitaequipaggio+
				"\nGiorni Penalità:"+this.penalitagiorni+
				"\nGuadagno:"+this.guadagno;
		
		return temp;
	}
	
	/**
	 * Esegue l'effetto della carta Schiavisti. Per ogni giocatore ancora in gara:
	 * - se ha potenza sufficiente, può scegliere se ottenere crediti perdendo giorni;
	 * - se ha potenza insufficiente, perde membri dell'equipaggio.
	 * @param elencoPedine lista delle pedine dei giocatori
	 * @return la lista aggiornata delle pedine dopo l'esecuzione della carta
	 */
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		boolean isCartaCompletata = false;
		int elenco = -1;
		
		do {
			elenco++;
			
			if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() == this.potenzanecc) {
				
				stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+" CON LA POTENZA DI "
						+this.potenzanecc+" PAREGGIA CON LA NAVE NEMICA");
				
			}else if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() > this.potenzanecc) {

				stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+" CON LA POTENZA DI "
				+elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni()+" SCONFIGGE LA NAVE NEMICA");
				
				if(elencoPedine.get(elenco).sceltaScambioCreditiConGiorni(penalitagiorni, guadagno, 0)) {
					
					stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+"AL COSTO DI "+penalitagiorni+" GIORNO HA RICEVO "+this.guadagno+" \u00A2");
					
					elencoPedine.get(elenco).getGiocatore().aggiornaCrediti(this.guadagno);
					
					elencoPedine.get(elenco).getTabellone().muoviPedina(elencoPedine.get(elenco), -this.penalitagiorni);
				}
				
				isCartaCompletata = true;
			}else {
				
				stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+" CON LA POTENZA DI "
						+elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni()+" VIENE SCONFITTA DALLA NAVE NEMICA E PORTANO VIA "
								+this.penalitaequipaggio+" COMPONENTI DELL'EQUIPAGGIO");
				
				elencoPedine.get(elenco).selezionaEquipaggioDaEliminare(this.penalitaequipaggio);
			}
			
			
		}while(!isCartaCompletata || elenco<elencoPedine.size());
		
		return elencoPedine;
	}
}
