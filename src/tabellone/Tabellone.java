package tabellone;

import carte.Carta;

import carte.Mazzo;
import gioco.ComunicazioneConUtente;
import gioco.FineGioco;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import partita.Livelli;
import partita.Pedina;

public class Tabellone{
	private final ComunicazioneConUtente cns;
	private final ArrayList<Pedina> elencoPedine;
	private ArrayList<Pedina> elencoNaviAbbandonate; 
	private ArrayList<Carta> mazzoCarte;
	private final ArrayList<Posizione> posizioni;
	private final Mazzo generatoreMazzo;
	private final int numeroPosizioni;
	private final Livelli livello;
	private FineGioco fineGioco;
	
	public Tabellone(Livelli livello){
		this.cns = ComunicazioneConUtente.getIstanza();
		this.elencoPedine = new ArrayList<>();
		this.generatoreMazzo = new Mazzo(livello.getLivelloNumerico());
		this.mazzoCarte = new ArrayList<>();
		this.posizioni = new ArrayList<>();
		this.livello = livello;
		this.numeroPosizioni = numeroPosizioniLivello();
		this.elencoNaviAbbandonate = new ArrayList<>();
		inizializzaPosizioni();
		this.mazzoCarte = this.generatoreMazzo.getLista();
		this.fineGioco = new FineGioco(this.livello.getLivelloNumerico());
	}
	
	/**
	 * Metodo per il ciclo della seconda fase in cui:
	 * 1- vengono stampate le navi dei giocatori per vedere come sono messe e averle sott'occhio
	 * 2- si vede il tabellone con la posizione delle varie pedine
	 * 3- prima si stampa le caratteristiche della carta e in seguito la si viene eseguita
	 * 4- si fanno tutti i controlli per vedere se la pedina deve o vuole abbandonare il volo
	 * 5- finite le carte oppure tutti i giocatori hanno abbandonato il volo si chiama la classe
	 * 	   fine gioco che stampera i dettagli di come è finito il volo
	 */
	public void gioca(){	
		impostaTabellone();
		
		int i=0;
		while (i < mazzoCarte.size() && !elencoPedine.isEmpty()) {
			this.cns.clear();
			
			//visualizza navi giocatori
			for(int p=0; p<elencoPedine.size(); p++) {
				
				cns.println("\nECCO LA NAVE DI " +elencoPedine.get(p).getGiocatore().getNome()+ ":");
				
				cns.println(elencoPedine.get(p).getGiocatore().getNave().toString());
			}
			//stampa tabellone
			cns.println("\n"+this.toString());
			
			//----estrazione carta--------
			cns.println("\n------------------carte rimaste :"+(mazzoCarte.size() - i)+"------------------\n");
			cns.println(mazzoCarte.get(i).toString());
			mazzoCarte.get(i).eseguiCarta(elencoPedine);
			
			//-----controlli e richieste-----
			
			//1) controllo doppiaggio
			this.controlloDoppiaggio();
			
			//controlli singoli
			for(int j=0; j<elencoPedine.size(); j++) {
				
				//2) controllo se è distrutta la nave
				if(elencoPedine.get(j).GetisNaveDistrutta() || !elencoPedine.get(j).getGiocatore().getNave().controllaEsistenzaNave()) {
					
					elencoPedine.get(j).setNaveDistrutta(true);
					cns.println(elencoPedine.get(j).getGiocatore().getNome()+" ha la nave distrutta ed è obbligato ad abbandonare la nave\n");
					this.elencoNaviAbbandonate.add(elencoPedine.get(j));
					elencoPedine.remove(j);
					j--;
					continue;
				} 
				
				//3) controllo equipaggio
				if(!elencoPedine.get(j).getGiocatore().getNave().controlloSonoPresentiCosmonauti()) {

					cns.println(elencoPedine.get(j).getGiocatore().getNome()+" non ha abbastanza cosmonauti per continuare il volo\n");
					this.elencoNaviAbbandonate.add(elencoPedine.get(j));
					elencoPedine.remove(j);
					j--;
					continue;
				}
				
				//4) Richiesta abbandono nave
				if(cns.richiestaAbbandonaVolo(elencoPedine.get(j).getGiocatore())) {
					
					this.elencoNaviAbbandonate.add(elencoPedine.get(j));
					elencoPedine.remove(j);
					j--;
				}
			}
			i++;
		}
		this.fineGioco = new FineGioco(elencoPedine, elencoNaviAbbandonate, this.livello.getLivelloNumerico());
		this.fineGioco.granFinale();
	}
	/**
	 * metodo per impostare il tabellone
	 */
	private void impostaTabellone() {
		for(Pedina pedina : this.elencoPedine){
			pedina.setTabellone(this);
		}
	}
	
	/**
	 * Metodo per aggiungere le pedine al tabellone
	 * 
	 * @param elencoPedine ArrayList<Pedine>
	 */
	public void aggiungiPedineAlTabellone(ArrayList<Pedina> elencoPedine){
		this.elencoPedine.addAll(elencoPedine);
		inizializzaPedine();
	}
	
	/**
	 * Metodo per impostare il numero di posizioni presenti sul tabellone. 
	 * Le posizioni variano in base al livello selezionato.
	 * 
	 * @return numero di posizioni x livello
	 */
	private int numeroPosizioniLivello(){
		if(this.livello == Livelli.PRIMO){
			return Livelli.getCaselleXLivello(Livelli.PRIMO);
		}
		else if(this.livello == Livelli.SECONDO){
			return Livelli.getCaselleXLivello(Livelli.SECONDO);
		}
		else{
			return Livelli.getCaselleXLivello(Livelli.TERZO);
		}
	}

	/**
	 * Metodo per inizializzazione delle posizioni per livello
	 */
	private void inizializzaPosizioni(){
		for(int i = 0; i < this.numeroPosizioni; i += 1){
			this.posizioni.add(new Posizione(i));
		}
	}

	/**
	 * Metodo per inizializzare le pedine in gioco
	 */
	private void inizializzaPedine(){
		// TODO: da sistemare l'ordine con il quale le pedine vengono aggiunte nel tabellone
		int pos = 1;
		for(Pedina pedina : this.elencoPedine){
			pedina.setPedinaInGioco();
			pedina.setTabellone(this);
			pedina.setPosizioneSulTabellone(pos);
			pedina.setPedinaInGioco();
			this.posizioni.get(pos).occupaPosizione(pedina);
			pos += 1;
		}
	}

	/**
	 * Metodo per muovere la pedina di x posizioni 
	 * Se posizioni > 0
	 * 	il giocatore avanza di x posizioni
	 * Se posizioni < 0
	 * 	il giocatore avanza di |-numeroPosizioni + posizioni|
	 * 
	 * @param pedina Pedina
	 * @param mossa int
	 */
	public void muoviPedina(Pedina pedina, int mossa){
		// Recupero la posizione iniziale della pedina
		int posizionePedinaAttuale = pedina.getPosizioneSulTabellone();

		/*
		 * La pedina va avanti fino a quando non finisco le mosse
		 * se la pedina incontra quale ostacolo lungo il suo cammino
		 * lo salta e continua il conteggio dalla prima posizione
		 * libera subito dopo
		 */
		if(mossa > 0){
			while(mossa > 0){
				// il calcolo del resto mi serve per calcolare la posizione 
				// della pedina anche quando sono ai bordi
				posizionePedinaAttuale = (posizionePedinaAttuale + 1) % this.numeroPosizioni;
				
				if(this.posizioni.get(posizionePedinaAttuale).isLibera()){
					// decremento dei passi da fare se e solo se la posizione dove
					// si trova attualmente la pedina e' libera
					mossa += -1;
				}
			}
			// Occupazione pedina
			this.posizioni.get(posizionePedinaAttuale).occupaPosizione(pedina); 
			pedina.setPosizioneSulTabellone(posizionePedinaAttuale);
		}
		else{
			// mossa < 0 (la pedina retrocede)
			while(mossa < 0){
				posizionePedinaAttuale = (posizionePedinaAttuale - 1 + this.numeroPosizioni) % this.numeroPosizioni;

				if(this.posizioni.get(posizionePedinaAttuale).isLibera()){
					mossa += 1;
				}
			}
			// Occupazione pedina
			this.posizioni.get(posizionePedinaAttuale).occupaPosizione(pedina);
			pedina.setPosizioneSulTabellone(posizionePedinaAttuale);
		}
	}

	/**
	 * Metodo per liberare la posizione 
	 * 
	 * @param posizione int
	 */
	public void liberaPosizione(int posizione){
		if(this.posizioni.get(posizione).isLibera() == false){
			this.posizioni.get(posizione).liberaPosizione();
		}		
	}
	
	
	public void controlloDoppiaggio() { //TODO da usare mentre si completa la carta   !problems		
		int i=0;
		
		do{
			for(int j=i+1; j<elencoPedine.size(); j++){
				if(elencoPedine.get(i).getNumeroGiro() > elencoPedine.get(j).getNumeroGiro()){
					if(elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(j).getPosizioneSulTabellone()){
						
						//TODO elencoPedine.get(j) è stato doppiato
						
						this.elencoNaviAbbandonate.add(elencoPedine.get(j));//TODO
						elencoPedine.remove(j);
					}
				}
			}
			
			i++;
		}while(i<elencoPedine.size()-1);
	}

	public ArrayList<Pedina> getElencoPedine(){
		return elencoPedine;
	}

	public ArrayList<Pedina> getElencoNaviAbbandonate(){
		return elencoNaviAbbandonate;
	}
	
	
	/**
	 * Metodo toString che ritorna il tabellone di gioco sotto forma di stringa
	 */
	@Override
	public String toString() {
		String temp = "Tabellone di gioco\n";

		// scansione le posizioni
		for(int i = 0; i < this.numeroPosizioni; i += 1){
			Posizione pos = this.posizioni.get(i);
			if(!pos.isLibera()){
				// se la posizione e' occupata
				Pedina p = pos.getPedina();	// recupero la pedina che occupa la posizione
				temp += " (" + p.getGiocatore().getColorePedina().getSiglaTabellone() + ") ";
			}else{
				temp += " ( ) ";
			}
		}
		return temp + "\n";
	}
}