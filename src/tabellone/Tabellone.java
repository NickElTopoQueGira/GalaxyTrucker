package tabellone;

import carte.Carta;

import carte.Mazzo;
import gioco.ComunicazioneConUtente;
import gioco.FineGioco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
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
	 * 	   fine gioco che stampera' i dettagli di come è finito il volo
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
			
			//1) controllo se dopo spazio aperto la nave si è mossa
			for(int j=0; j<elencoPedine.size(); j++) {
				
				if(!elencoPedine.get(j).isPedinaInGioco()) {
					this.elencoNaviAbbandonate.add(elencoPedine.get(j));
					elencoPedine.remove(j);
					j--;
				}
			}
			
			//2) controllo doppiaggio
			this.controlloDoppiaggio();
			
			//controlli singoli
			for(int j=0; j<elencoPedine.size(); j++) {
				
				//3) controllo se è distrutta la nave
				if(elencoPedine.get(j).GetisNaveDistrutta() || !elencoPedine.get(j).getGiocatore().getNave().controllaEsistenzaNave()) {
					
					elencoPedine.get(j).setNaveDistrutta(true);
					cns.println(elencoPedine.get(j).getGiocatore().getNome()+" ha la nave distrutta ed è obbligato ad abbandonare la nave\n");
					this.elencoNaviAbbandonate.add(elencoPedine.get(j));
					elencoPedine.remove(j);
					j--;
					continue;
				} 
				
				//4) controllo equipaggio
				if(!elencoPedine.get(j).getGiocatore().getNave().controlloSonoPresentiCosmonauti()) {

					cns.println(elencoPedine.get(j).getGiocatore().getNome()+" non ha abbastanza cosmonauti per continuare il volo\n");
					this.elencoNaviAbbandonate.add(elencoPedine.get(j));
					elencoPedine.remove(j);
					j--;
					continue;
				}
				
				//5) Richiesta abbandono nave
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
	 * Metodo per passare alle pedine il tabellone
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
		// l'elenco delle pedine, nel momento dell'inizializzazione delle stesse, viene girato
		// facendolo partire dal fondo, questo perche' noi vogliamo che il primo giocatore che
		// ha finito la navi sia nella posizione + a destra, mentre il secondo + a sinistra e
		// cosi' via, fino ad arrivare all'ultimo giocatore
		ArrayList<Pedina> elencoPedineRev = new ArrayList<>(this.elencoPedine.reversed());

		int pos = 1;
		for(Pedina pedina : elencoPedineRev){
			pedina.setPedinaInGioco();
			pedina.setTabellone(this);
			pedina.setPosizioneSulTabellone(pos);
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
		int posizionePedinaDiPartenza = posizionePedinaAttuale;

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
			// libero la posizione di partenza
			liberaPosizione(posizionePedinaDiPartenza);
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
			// libero la posizione di partenza
			liberaPosizione(posizionePedinaDiPartenza);
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

	/**
	 * Metodo per il controllo sul doppiaggio della pedina
	 */
	public void controlloDoppiaggio() {
	    List<Pedina> doppiate = new ArrayList<>();

	    for (int i = 0; i < elencoPedine.size(); i++) {
	        Pedina p1 = elencoPedine.get(i);
	        for (int j = 0; j < elencoPedine.size(); j++) {
	            if (i == j) continue;

	            Pedina p2 = elencoPedine.get(j);

	            boolean p1HaDoppiatoP2 =
	                p1.getNumeroGiro() > p2.getNumeroGiro() &&
	                p1.getPosizioneSulTabellone() > p2.getPosizioneSulTabellone();

	            if (p1HaDoppiatoP2 && !doppiate.contains(p2)) {
	                elencoNaviAbbandonate.add(p2);
	                doppiate.add(p2);
	            }
	        }
	    }

	    elencoPedine.removeAll(doppiate);
	}

	/**
	 * Metodo per prendere l'elenco delle pedine
	 * @return ArrayList<Pedina>
	 */
	public ArrayList<Pedina> getElencoPedine(){
		return elencoPedine;
	}

	/**
	 * Metodo per prendere l'elenco delle navi abbandonante dalle pedine
	 * @return ArrayList<Pedina> elenco navi abbandonate
	 */
	public ArrayList<Pedina> getElencoNaviAbbandonate(){
		return elencoNaviAbbandonate;
	}

	/**
	 * Metodo toString che ritorna il tabellone di gioco sotto forma di stringa //
		sb.append("╔══════════════ TABELLONE DI GIOCO ══════════════╗\n\n");
		
	 */
	@Override
	public String toString() {
	    int a = 20; //semiasse orizzontale (larghezza)
	    int b = 3;  //semiasse verticale (altezza)

	    int larghezza = 2 * a + 3;
	    int altezza = 2 * b + 3;

	    //cambiato da char[][] a String[][]
	    String[][] griglia = new String[altezza][larghezza];
	    for (int i = 0; i < altezza; i++) {
	        Arrays.fill(griglia[i], " ");
	    }

	    int n = this.numeroPosizioni;
	    String[] simboli = new String[n];
	    for (int i = 0; i < n; i++) {
	        Posizione pos = this.posizioni.get(i);
	        if (!pos.isLibera()) {
	            Pedina p = pos.getPedina();
	            simboli[i] = p.getGiocatore().getColorePedina().getSiglaTabellone();
	        } else {
	            simboli[i] = "□";
	        }
	    }

	    //posizionamento con rotazione (un po' di matematica:-)
	    for (int i = 0; i < n; i++) {
	        double theta = 2 * Math.PI * i / n + Math.PI;
	        int x = (int) Math.round(a * Math.cos(theta)) + a + 1;
	        int y = (int) Math.round(b * Math.sin(theta)) + b + 1;

	        griglia[y][x] = simboli[i];
	    }

	    //costruzione stringa finale
	    StringBuilder sb = new StringBuilder();
	    sb.append("╔══════════════ TABELLONE DI GIOCO ══════════════╗\n\n");
	    for (int i = 0; i < altezza; i++) {
	        for (int j = 0; j < larghezza; j++) {
	            sb.append(griglia[i][j]);
	        }
	        sb.append("\n");
	    }
	    return sb.toString();
	}
}