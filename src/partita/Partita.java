package partita;

import eccezioniPersonalizzate.ErroreCoordinate;
import eccezioniPersonalizzate.ErroreRisorse;
import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import partita.giocatore.Giocatore;
import tabellone.Tabellone;
import tessera.Coordinate;
import tessera.FactoryTessera;
import tessera.Tessera;
import tessera.TipoTessera;

public class Partita{
	private final ComunicazioneConUtente com;
	private final int numeroGiocatori;
	private final ModalitaPartita modalitaPartita;
	private final Set<Giocatore> giocatori;
	private Livelli livelloPartita;
	private Tabellone tabellone;
	
	/**
	 * Partita multipla
	 * @param numeroGiocatori
	 */
	public Partita(int numeroGiocatori){
		
		this.com = ComunicazioneConUtente.getIstanza();
		this.numeroGiocatori = numeroGiocatori;
		this.modalitaPartita = ModalitaPartita.MULTIPLA;
		this.giocatori = new LinkedHashSet<>();
		this.tabellone = null;
		this.livelloPartita = Livelli.PRIMO;
	}

	/**
	 * Partita singola
	 * @param numeroGiocatori
	 * @param livelloPartita
	 */
	public Partita(int numeroGiocatori, Livelli livelloPartita){
		this.com = ComunicazioneConUtente.getIstanza();
		this.giocatori = new LinkedHashSet<>();
		this.modalitaPartita = ModalitaPartita.SINGOLA;
		this.numeroGiocatori = numeroGiocatori;
		this.livelloPartita = livelloPartita;
		this.tabellone = null;
	}
	
	/**
	 * Metodo per aggiungere i giocatori alla partita
	 * @param elencogiocatori
	 */
	public void aggiungiGiocatori(Set<Giocatore> elencogiocatori){
		this.giocatori.addAll(elencogiocatori);
	}

	/**
	 * GIOCA LA PARTITA
	 */
	public void gioca(){
		boolean partitaInCorso = true;
		
		while(partitaInCorso){
			// -------------- INIZIO DELLA PARTITA --------------
			// generazione del tabellone
			generaTabellone();

			// -------------- FASE DI ASSEMBLAGGIO DELLE NAVI --------------
			creaNavi();
			assemblaNavi();

			// -------------- FASE DI GIOCO (ESECUZIONE DELLE CARTE) --------------		
			tabellone.gioca();

			// -------------- FINE DELLA PARTITA --------------
			/**
			 * Se la partita e' singola, si esce nel loop di gioco
			 * altrimenti se la partita e' multima si rimane nel loop 
			 */
			if(this.modalitaPartita == ModalitaPartita.SINGOLA){
				partitaInCorso = false;
			}

			/**
			 * Controllo se la partita e' multila ed e' arrivata al terzo lievello
			 * se e' al terzo livello allora la partita termina
			 * altrimenti rimane nel loop
			 */
			if(this.modalitaPartita == ModalitaPartita.MULTIPLA &&
				this.livelloPartita == Livelli.TERZO){
					partitaInCorso = false;
			}
		}
	}

	/**
	 * Memtodo per la generazione del tabellone
	 */
	private void generaTabellone(){
		
		if(this.tabellone == null){
			this.tabellone = new Tabellone(livelloPartita);
			// aggiunta delle pedine al tabellone
			aggiungiPedineAlTabellone();
		}
		else{
			// se il tabellone e' gia' creato
			
			if(this.livelloPartita != Livelli.TERZO){
				// se non sono all'utlimo livello 
				this.tabellone = null;
				// tabellone del livello successivo
				this.livelloPartita = livelloPartita.next();
				this.tabellone = new Tabellone(livelloPartita);
				
				// aggiunta delle pedine al tabellone
				aggiungiPedineAlTabellone();
			}
		}
	}

	/**
	 * Metodo per aggiungere le pedine al tabellone
	*/
	private void aggiungiPedineAlTabellone(){
		for(Giocatore giocatore : this.giocatori){
			this.tabellone.aggiungiPedina(giocatore.getPedina());
		}
	}

	/**
	 * Metodo per la creazione delle navi
	 */
	private void creaNavi(){
		Iterator<Giocatore> giocatiIterator = this.giocatori.iterator();
		
		// creazione della nave:
		while(giocatiIterator.hasNext()){
			Giocatore g = giocatiIterator.next();
			if(this.giocatori.contains(g)){
				try{
					g.creaNave(livelloPartita);
				}catch(ErroreRisorse er){
					this.com.printError(er.getMessage());
				}
			}
		}
	}

	/**
	 * Meto per assemblare la nave a turno
	 */
	private void assemblaNavi(){
	    ArrayList<Giocatore> giocatori = new ArrayList<>(this.giocatori);
	    Map<Giocatore, Integer> turniResidui = new HashMap<>();
	    final int TURNI_EXTRA = 1;
	    boolean countdownAttivo = false;
	    int contatoreFinale = 1;

	    // inizializza turniResidui a 0 per tutti
	    for(Giocatore g : giocatori){
	        turniResidui.put(g, 0);
	    }

	    while(true){
	        boolean almenoUnoHaAgito = false;

	        for(Giocatore g : giocatori){
	            if (g.isNaveFinita()) continue;

	            // Se countdown è attivo e il giocatore ha esaurito i turni, salta
	            if(countdownAttivo && turniResidui.getOrDefault(g, 0) <= 0){
	                continue;
	            }

	            this.com.println("\n\nTurno del giocatore: " + g.getNome());
	            this.com.println(g.getNave().toString());

	            if(countdownAttivo){
	                int residui = turniResidui.getOrDefault(g, 0);
	                this.com.println("Numero mosse ancora disponibili: " + residui);
	            }

	            this.com.println("Vuoi modificare la nave?");
	            if(this.com.conferma()){
	                turno(g);
	                almenoUnoHaAgito = true;
	            }

	            // Ricontrolla se ha completato la nave
	            if(g.isNaveFinita() || naveFinita(g)){
	                g.naveFinita();
	                g.getPedina().setPosizioneSulTabellone(contatoreFinale++);
	                tabellone.aggiungiPedina(g.getPedina());
	                this.com.println("Il giocatore " + g.getNome() + " ha finito la nave.");

	                // Se è il primo a finirla, attiva countdown per gli altri
	                if(!countdownAttivo){
	                    this.com.println("Inizio conto alla rovescia di " + TURNI_EXTRA + " turni per gli altri giocatori.");
	                    for(Giocatore altro : giocatori){
	                        if(!altro.equals(g) && !altro.isNaveFinita()){
	                            turniResidui.put(altro, TURNI_EXTRA);
	                        }
	                    }
	                    countdownAttivo = true;
	                }
	            }else if(countdownAttivo){
	                // Se il countdown è attivo, decrementa
	                int residui = turniResidui.getOrDefault(g, 0);
	                turniResidui.put(g, residui - 1);
	            }
	        }

	        // Se nessuno ha potuto agire, termina
	        if(!almenoUnoHaAgito){
	            this.com.println("Nessun altro giocatore può più modificare la nave.");
	            break;
	        }

	     // Condizione di uscita
	        boolean fine = true;

	        for (Giocatore g : giocatori) {
	            if (!g.isNaveFinita()) {
	                if (!countdownAttivo) {
	                    // Se countdown non è attivo, almeno uno non ha ancora finito: non si esce
	                    fine = false;
	                    break;
	                } else if (turniResidui.getOrDefault(g, 0) > 0) {
	                    // Se countdown è attivo ma ha ancora turni, non si esce
	                    fine = false;
	                    break;
	                }
	            }
	        }

	        if (fine) {
	            this.com.println("Tutti i giocatori hanno finito di assemblare la nave (o esaurito i turni).");
	            break;
	        }
	    }
	}

	/**
	 * motodo per la gestione delle opzioni svolte dal giocatore sulle tessere in fase di conf della nave
	 */
	private void turno(Giocatore g){
		// azioneCarta verifica gia' a monte se si puo' fare 
		// una determinata azione
		int azioneCarta = azioneCarta(g);
							
		switch(azioneCarta){
			case 1 ->{ //	TODO DA SISTEMARE!
				//se lista=vuota allora richiama turno
				if(Tessera.getListaTessere().isEmpty()){
					turno(g);
				}else {
					// per utilizzare una tessera gia' estratta e salvata nel mazzo degli scarti
					visualizzaElencoTessere(Tessera.getListaTessere());
					Tessera tesseraSelezionata = selezionaTesseraDalMazzo();
					
					
					if(tesseraSelezionata != null && tesseraSelezionata.getTipoTessera() != TipoTessera.VUOTA){
						this.com.println("Vuoi prenotare la tessera (altrimenti la tessera verrà inserita in nave)?");
						if(this.com.conferma()){
							prenotaTessera(g, tesseraSelezionata);
						}else{
							inserisciTesseraNellaNave(g, tesseraSelezionata);
							
						}
					}
				}
				
			}
			case 2 ->{	
				// per generare una nuova tessera	
				Tessera tessera = nuovaTesseraRandom();
				boolean ciclo = true;
				do {
					this.com.println(g.getNave().toString());
					this.com.println("Tessera estratta: ");
					this.com.print(tessera.toString());
					int scelta = menuScelte();
					switch(scelta){
						case 1 ->{
							prenotaTessera(g, tessera);
							ciclo = false;
							break;
						}
						case 2 ->{
							inserisciTesseraNellaNave(g, tessera);
							ciclo = false;
							break;
						}
						case 3 ->{
							try{
								tessera.ruota();
							}catch(ErroreRotazione e){
								this.com.printError(e.getMessage());
							}
							break;
						}					
						case 4 ->{
							//non tocco nulla perchè ogni tessera generata è già in listaTessere
							ciclo = false;
							break;
						}
					}
				}while(ciclo);
			}
			case 3 ->{
				// per utilizzare una tessera prenotata
				inserisciTesseraNellaNave(g, usaTesseraPrenotata(g));
			}
		}
		this.com.clear();
	}
	
	/**
	 * Metodo per chiedere conferma se la nave e' finita
	 */
	private boolean naveFinita(Giocatore giocatore){
		this.com.clear();
		// visualizzazione della nave
		this.com.println("Nave di "+ giocatore.getNome());
		this.com.println(giocatore.getNave().toString());

		this.com.println("Hai finito la nave? ");
		return this.com.conferma();
	}

	/**
	 * Metodo per selezionare la tessera dal mazzo
	 * 
	 * @return  tessera
	 */
	private Tessera selezionaTesseraDalMazzo(){
		int selezione;
		Tessera t = null;
		boolean pass = false;
		do{ 
			try{
				this.com.println("Inserisci il numero della tessera che si vuole inserire (inserire 0 per tornare al menu): ");
				selezione = Integer.parseInt(this.com.consoleRead())-1;
				
				// ritorno al menu
				if(-1 == selezione) return null;

				if(Tessera.getListaTessere().contains(Tessera.getListaTessere().get(selezione))){
					this.com.print("Tessera selezionata: \n");
					t = Tessera.getListaTessere().get(selezione);
					this.com.println(t.toString());
					pass = true;
				}
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}catch(IndexOutOfBoundsException ioobe){
				this.com.printError("Tessera selezionata non presente");
			}
		}while(false == pass);

		if(t == null){
			return selezionaTesseraDalMazzo();
		}		
		return t;
	}

	/**
	 * Metodo per visualizzare l'elenco delle tessere
	 * 
	 * @param tessere
	 */
	private void visualizzaElencoTessere(ArrayList<Tessera> tessere){
		for(int i = 0; i < tessere.size(); i+= 1){
			this.com.println("Tessera " + (i+1));
			this.com.println(tessere.get(i).toString());
		}
	}

	/**
	 * Metodo per inserire la tessera nella nave
	 * 
	 * @param giocatore
	 * @param tessera
	 */
	private void inserisciTesseraNellaNave(Giocatore giocatore, Tessera tessera){
		this.com.println(giocatore.getNave().toString());
		this.com.println("Tessera da inserire");
		this.com.println(tessera.toString());
		int x = 0, y = 0;
		try{
			this.com.println("[Inserisci (x)=0 e (y)=0 per uscire]");
			this.com.println("Inserisci la coordinata x: ");
			x = Integer.parseInt(this.com.consoleRead())-giocatore.getNave().getInizioNaveX();
			this.com.println("Inserisci la coordinata y: ");
			y = Integer.parseInt(this.com.consoleRead())-giocatore.getNave().getInizioNaveY();
			
			if(x+giocatore.getNave().getInizioNaveX() == 0 && y+giocatore.getNave().getInizioNaveY() == 0) {
				return;
			}
		}catch(NumberFormatException nfe){
			this.com.erroreImmissioneValore();
			inserisciTesseraNellaNave(giocatore, tessera);
		}

		Coordinate c = new Coordinate(x, y);
		if(aggiungiTesseraNellanave(giocatore, tessera, c)){
			this.com.println("Tessera aggiunta alla nave con successo!");
			Tessera.removeDaListaTessere(tessera);
		}else{
			inserisciTesseraNellaNave(giocatore, tessera);
		}
	}

	/**
	 * Metodo per aggiungere la tessera alla nave
	 * @return true -> ok
	 * 			false -> no
	 */
	private boolean aggiungiTesseraNellanave(Giocatore giocatore, Tessera tessera, Coordinate coordinate){
		try {
			giocatore.getNave().inserisciTessera(coordinate, tessera);
			return true;
		}catch(ErroreTessera et){
			this.com.printError(et.getMessage());
		}catch(ErroreCoordinate ec){
			this.com.printError(ec.getMessage());
		}
		return false;
	}

	/**
	 * Metodo per prenotare la tessera
	 */
	private void prenotaTessera(Giocatore giocatore, Tessera tessera){
		try{
			giocatore.getNave().prenotaTessera(tessera);
			this.com.println("Hai prenotato la tessera!!");
			Tessera.removeDaListaTessere(tessera);
		}catch(ErroreTessera et){
			this.com.printError(et.getMessage());
		}
	}

	/**
	 * Metodo per rimuovere la tessera prenotata
	 * @return tessera
	 */
	private Tessera usaTesseraPrenotata(Giocatore giocatore){
		this.com.println(giocatore.getNave().tesserePrenotateToString());
		
		this.com.println("Inserisci il numero della tessera che vuoi utilizzare: ");
		int numero = -1;
		boolean pass = false;
		do{ 
			try{
				numero = Integer.parseInt(this.com.consoleRead())-1;
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
			if(numero == -1){
				this.com.erroreImmissioneValore();
			}else{
				pass = true;
			}
		}while(false == pass);
		

		Tessera t;
		try{
			t = giocatore.getNave().togliTesseraPrenotata(numero);
		}catch(ErroreTessera et){
			this.com.printError(et.getMessage());
			return usaTesseraPrenotata(giocatore);
		}

		return t;
	}

	/**
	 * Metodo per selezionare se si vuole 
	 * pescare dal mazzo o utilizzare la tessera
	 * 
	 * @return scelta
	 */
	private int azioneCarta(Giocatore g){
		ArrayList<String> elenco = new ArrayList<>();
		elenco.add("Per utilizzare una tessera dal mazzo");
		elenco.add("Per generare una nuova tessera");
		elenco.add("Per utilizzare una tessera prenotata");
		this.com.println(this.com.visualizzaElenco(elenco));
		
		int rispota = 0;
		do{			
			try{
				rispota = Integer.parseInt(this.com.consoleRead());
				this.com.clear();
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(rispota < 1 || rispota > 3);


        // controllo se la selezione fatta e' possibile 
		switch(rispota) {
			case 1 ->{
				if(Tessera.getListaTessere().isEmpty()){
					this.com.printError("Non ci sono tessere estratte");
					return azioneCarta(g);
				}
			}
			case 3 ->{
				if(g.getNave().isComponentiPrenotatiEmpty()){
					this.com.printError("Non hai tessere prenotate da usare");
					return azioneCarta(g);
				}
			}
		}
		
		return rispota;
	}

	/**
	 * Metodo per generare una nuova tessera a random
	 * @return
	 */
	private Tessera nuovaTesseraRandom(){
		FactoryTessera ft = new FactoryTessera();
		Tessera t = null;
		boolean pass = false;
		do{
			try{
				t = ft.estraiTipo();
			}catch(ErroreTessera et){
				//eccezione catturata: troppe tessere generate
				
				//TODO gestire la logica di gioco nel caso sia stato estratto il numero massimo di tessere generabili
				this.com.printError(et.getMessage());
			}

			if(null != t){
				pass = true;
			}
		}while(false == pass);
		return t;
	}

	/**
	 * Metodo di menu' delle scelte
	 * Utilizzato quando si genera una nuova tessera random
	 * 
	 * @return scelta fatta
	 */
	private int menuScelte(){
		ArrayList<String> azioni = new ArrayList<>();
		this.com.println("Inserire il numero dell'azione desiderata: ");
		azioni.add("Prenotare la tessera");
		azioni.add("Inserire la tessera");
		azioni.add("Ruota la tessera");
		azioni.add("Scarta tessera");

		int scelta = 0;
		boolean pass = false;
		do{
			this.com.println(this.com.visualizzaElenco(azioni));
			try{
				scelta = Integer.parseInt(this.com.consoleRead());
				if(scelta == 1 || scelta == 2 || scelta == 3 || scelta == 4){
					pass = true;
				}else{
					this.com.erroreImmissioneValore();
					pass = false;
				}
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(false == pass);

		return scelta;
	}

	public void setLivelloPartita(Livelli livelloPartita) { this.livelloPartita = livelloPartita;}
	public Livelli getLivelloPartita(){ return this.livelloPartita; }

	public ModalitaPartita getModalitaPartita(){ return this.modalitaPartita; }

	public int getNumeroGiocatori(){ return this.numeroGiocatori; }

	@Override
	public String toString(){
		return "Numero giocatori: " + this.numeroGiocatori + "\n" +
				"Modalita' partita: " + this.modalitaPartita;
	}
}
