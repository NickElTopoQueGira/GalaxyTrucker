package tabellone;

import carte.Carta;

import carte.Mazzo;
import gioco.ComunicazioneConUtente;
import java.util.ArrayList;
import partita.Livelli;
import partita.Pedina;

public class Tabellone{
	private final ComunicazioneConUtente cns;
	private final ArrayList<Pedina> elencoPedine;
	private ArrayList<Pedina> elencoNaviDistrutte; //PROVVISORIO
	private ArrayList<Pedina> elencoNaviAbbandonate; 
	private ArrayList<Carta> mazzoCarte;
	private ArrayList<Posizione> posizioni;
	private Mazzo generatoreMazzo;
	private int numeroPosizioni;
	private Livelli livello;
	
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
		inizializzaPedine();
		this.mazzoCarte = this.generatoreMazzo.getLista();
	}

	public void gioca(){	
		impostaTabellone();
		
		int i=0;
		while (i < mazzoCarte.size() && !elencoPedine.isEmpty()) {
			//----estrazione carta--------

			cns.println("\n------------------carte rimaste :"+(mazzoCarte.size() - i)+"------------------\n");
			cns.println(mazzoCarte.get(i).toString());
			mazzoCarte.get(i).eseguiCarta(elencoPedine);
			
			//-----controlli e richieste-----
			
			//1) controllo doppiaggio
			this.controlloDoppiaggio();
			
			//controlli singoli
			for(int j=0; j<elencoPedine.size(); j++) {
				
				//2) controllo equipaggio
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
	}
	
	private void impostaTabellone() {
		for(int i=0; i<elencoPedine.size(); i++) {
			
			elencoPedine.get(i).setTabellone(this);
		}
	}
	
	/**
	 * Metodo per aggiungere la pedina sul tabellone
	 * 
	 * @param nuovaPedina
	 */
	public void aggiungiPedina(Pedina nuovaPedina){
		this.elencoPedine.add(nuovaPedina);
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
		for(int i = 0; i < this.elencoPedine.size(); i += 1){
			this.elencoPedine.get(i).setPedinaInGioco();
			this.elencoPedine.get(i).setTabellone(this);
		}
	}

	/**
	 * Metodo per muovere la pedina di x posizioni 
	 * Se posizioni > 0
	 * 	il giocatore avanza di x posizioni
	 * 
	 * Se posizioni < 0
	 * 	il giocatore avanza di |-numeroPosizioni + posizioni|
	 * 
	 * @param pedina
	 * @param posizioni
	 */
	public void muoviPedina(Pedina pedina, int mossa){
		// Recupero la posizione iniziale della pedina
		int posizionePedinaAttuale = pedina.getPosizioneSulTabellone();

		/**
		 * La pedina va avanti fino a quando non finisco le mosse
		 * se la pedina incontra quele ostacolo lungo il suo cammino
		 * lo salta e continua il conteggio dalla prima posizione
		 * libera subito dopo
		 */
		if(mossa > 0){
			while(mossa > 0){
				// il calcolo del resto mi serve per calcolare la posizione 
				// della pedina anche quando sono ai bordi
				posizionePedinaAttuale = (posizionePedinaAttuale + 1) % this.numeroPosizioni;
				
				if(this.posizioni.get(posizionePedinaAttuale).isLibera()){
					// decremento dei passi da fare se e solo se la la posizone dove 
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
	 * @param posizione
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
						this.elencoNaviDistrutte.add(elencoPedine.get(j));
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
	 * metodo toString che ritorna il tabellone di gioco sotto forma di stringa
	 */
	@Override
	public String toString() {
		String temp="";
		ArrayList<String> stringaTabellone= new ArrayList<String>();
		temp+="Tabellone di Gioco:\n";
		for(int j=0; j<this.numeroPosizioni; j++) {
			if(elencoPedine.get(j).getPosizioneSulTabellone()==j) {
				stringaTabellone.add(j, " ("+elencoPedine.get(j).getGiocatore().getColorePedina().getSiglaTabellone()+") ");
			}else {
				stringaTabellone.add(j, " ( ) ");
			}
			
		}
		temp+="\n\n";
		return temp;
		
	}
	
	
	
}