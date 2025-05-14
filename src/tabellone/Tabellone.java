package tabellone;

import java.util.ArrayList;

import carte.Carta;
import partita.Livelli;
import partita.Pedina;
import partita.giocatore.Colori;

public class Tabellone{
	private ArrayList<Pedina> elencoPedine;
	private ArrayList<Carta> mazzoCarte;
	private ArrayList<Posizione> posizioni;
	private int numeroPosizioni;
	private Livelli livello;
	
	public static void main(String[] args){
		Tabellone t = new Tabellone(Livelli.PRIMO);
		
		Pedina p = new Pedina(Colori.BLU);
		
		p.setPosizioneSulTabellone(0);
		System.out.println(p.getPosizioneSulTabellone());
		
		t.muoviPedina(p, 2);
		System.out.println(p.getPosizioneSulTabellone());
	}


	public Tabellone(Livelli livello){
		this.elencoPedine = new ArrayList<Pedina>();
		this.mazzoCarte = new ArrayList<Carta>();
		this.posizioni = new ArrayList<Posizione>();
		this.livello = livello;
		this.numeroPosizioni = numeroPosizioniLivello();

		inizializzaPosizioni();
	}

	/**
	 * Funzione per aggiungere la pedina sul tabellone
	 * 
	 * @param nuovaPedina
	 */
	public void aggiungiPedina(Pedina nuovaPedina){
		this.elencoPedine.add(nuovaPedina);
	}

	/**
	 * Funzione per impostare il numero di posizioni presenti sul tabellone. 
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
				posizionePedinaAttuale += 1;
				
				if(this.posizioni.get(posizionePedinaAttuale).isLibera()){
					// decremento dei passi da fare se e solo se la la posizone dove 
					// si trova attualmente la pedina e' libera
					mossa += -1;
				}
				else{
					// occupata
				}
			}
			this.posizioni.get(posizionePedinaAttuale).occupaPosizione(pedina);
			pedina.setPosizioneSulTabellone(posizionePedinaAttuale);
		}
		else{
			// mossa < 0 (la pedina retrocede)
			int passiFatti = 0; 
			while (passiFatti < mossa){
				
			}
		}
	}

	
	public void liberaPosizione(int posizione){
		if(this.posizioni.get(posizione).isLibera() == false){
			this.posizioni.get(posizione).liberaPosizione();
		}		
	}


	
}