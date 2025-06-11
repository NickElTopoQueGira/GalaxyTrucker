package carte.nemico;

import carte.*;
import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.Pedina;

public class Schiavisti extends Nemici {
	
	private int potenzanecc; // potenza necessaria
	private int penalitaequipaggio; // numeri merci persi
	private int penalitagiorni; // numeri giorni persi
	private int guadagno;
	private final ComunicazioneConUtente stampa;
	
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
		temp=temp+"\nLivello carta:"+this.lvl+"\n"+"Tipo carta:"+this.tipo+
				"\nPotenza necessaria:"+this.potenzanecc+
				"\nPenaltà Equipaggio:"+this.penalitaequipaggio+
				"\nGiorni Penalità:"+this.penalitagiorni+
				"\nGuadagno:"+this.guadagno;
		
		return temp;
	}
	
	/**
	 * Esegue l'effetto della carta Schiavisti. Per ogni giocatore ancora in gara:
	 * - se ha potenza sufficiente, può scegliere se ottenere crediti perdendo giorni,
	 * 	 e risolve la carta anche per gli altri giocatori
	 * - se ha potenza insufficiente, perde membri dell'equipaggio.
	 * @param elencoPedine lista delle pedine dei giocatori
	 * @return la lista aggiornata delle pedine dopo l'esecuzione della carta
	 */
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		boolean cartaCompletata = false; 
		for(Pedina pedina : elencoPedine){
			stampa.println("TURNO DI "+pedina.getGiocatore().getNome());
			// se la carta e' stata risolta si continua
			if(cartaCompletata){
				continue;
			}

			float potenzaCannoni = pedina.getGiocatore().getNave().getPotenzaCannoni(); 
			String nomeGiocatore = pedina.getGiocatore().getNome();

			// se la nave ha la stessa potenza della nave nevica
			if(potenzaCannoni == this.potenzanecc){
				stampa.println("LA NAVE DI "+ nomeGiocatore +" CON LA POTENZA DI "
						+this.potenzanecc+" PAREGGIA CON LA NAVE NEMICA");
			}
			// se la nave ha piu' potenza della nave nemica
			else if(potenzaCannoni > this.potenzanecc){
				stampa.println("LA NAVE DI "+ nomeGiocatore +" CON LA POTENZA DI "
						+ potenzaCannoni +" SCONFIGGE LA NAVE NEMICA");
			
				if(pedina.sceltaScambioCreditiConGiorni(this.penalitagiorni, this.guadagno, 0)){
					stampa.println("LA NAVE DI " + nomeGiocatore + " AL COSTO DI " 
							+ this.penalitagiorni + " GIORNI HA RICEVUTO " + this.guadagno + " \u00A2");

					pedina.getGiocatore().aggiornaCrediti(this.guadagno);
					pedina.getTabellone().muoviPedina(pedina, -this.penalitagiorni);
				}
				cartaCompletata = true;
			}
			// se la nave ha meno potenza della nave nemica
			else{
				stampa.println("LA NAVE DI "+ nomeGiocatore +" CON LA POTENZA DI "
						+ potenzaCannoni +" VIENE SCONFITTA DALLA NAVE NEMICA E PORTANO VIA "
						+ this.penalitaequipaggio +" COMPONENTI DELL'EQUIPAGGIO");

				pedina.selezionaEquipaggioDaEliminare(penalitaequipaggio);
			}

		}

		return elencoPedine;
	}
}
