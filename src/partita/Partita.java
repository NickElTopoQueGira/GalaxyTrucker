package partita;

import eccezioniPersonalizzate.ErroreAggiuntaTessera;
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
import partita.nave.GestioneEquipaggio;
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
	 * @param numeroGiocatori Int
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
	 * @param numeroGiocatori int
	 * @param livelloPartita Livelli
	 */
	public Partita(int numeroGiocatori, Livelli livelloPartita){
		this.com = ComunicazioneConUtente.getIstanza();
		this.giocatori = new LinkedHashSet<>();
		this.modalitaPartita = ModalitaPartita.SINGOLA;
		this.numeroGiocatori = numeroGiocatori;
		this.livelloPartita = livelloPartita;
		this.tabellone = null;
	}
	
	// ----------------- GIOCATORI E PEDINE----------------- 
	/**
	 * Metodo per aggiungere i giocatori alla partita
	 * @param elencoGiocatori Set<Giocatore>
	 */
	public void aggiungiGiocatori(Set<Giocatore> elencoGiocatori){
		this.giocatori.addAll(elencoGiocatori);
	}

	public int getNumeroGiocatori(){ return this.numeroGiocatori; }

	// ----------------- TABELLONE - LIVELLI - MODALITA' -----------------
	/**
	 * Metodo per la generazione del tabellone
	 * Le pedine non vengono aggiunte qui al tabellone.
	 * Vengono aggiunte quando il giocatore avra' finito di
	 * creare la sua nave
	 */
	private void generaTabellone(){
		
		if(this.tabellone == null){
			this.tabellone = new Tabellone(livelloPartita);
		}
		else{
			// se il tabellone e' gia' creato
			
			if(this.livelloPartita != Livelli.TERZO){
				// se non sono all'ultimo livello
				this.tabellone = null;
				// tabellone del livello successivo
				this.livelloPartita = livelloPartita.next();
				this.tabellone = new Tabellone(livelloPartita);
			}
		}
	}

	public void setLivelloPartita(Livelli livelloPartita) { this.livelloPartita = livelloPartita;}

	public Livelli getLivelloPartita(){ return this.livelloPartita; }

	public ModalitaPartita getModalitaPartita(){ return this.modalitaPartita; }

	// ----------------- GIOCO - TURNO ----------------- 
	/**
	 * GIOCA LA PARTITA
	 */
	public void gioca(){
		boolean partitaInCorso = true;
		
		while(partitaInCorso){
			// -------------- INIZIO DELLA PARTITA --------------
			// azzero le navi ai giocatori
			azzera();
			// generazione del tabellone
			generaTabellone();

			// -------------- FASE DI ASSEMBLAGGIO DELLE NAVI --------------
			creaNavi();
			assemblaNavi();
			aggiungiEquipaggio();

			// -------------- FASE DI GIOCO (ESECUZIONE DELLE CARTE) --------------		
			tabellone.gioca();

			// -------------- FINE DELLA PARTITA --------------
			/*
			 * Se la partita e' singola, si esce nel loop di gioco
			 * altrimenti se la partita e' multipla si rimane nel loop
			 */
			if(this.modalitaPartita == ModalitaPartita.SINGOLA){
				partitaInCorso = false;
			}

			/*
			 * Controllo se la partita e' multipla ed e' arrivata al terzo livello
			 * se e' al terzo livello allora la partita termina
			 * altrimenti rimane nel loop
			 */
			if(this.modalitaPartita == ModalitaPartita.MULTIPLA){
				if(this.livelloPartita == Livelli.TERZO){
					partitaInCorso = false;
				}
			}
		}
		this.com.print("Autori adattamento gioco originale:");
		this.com.print("Shvets Andriy");
		this.com.print("Sesana Niccolo");
		this.com.print("Poli Matteo");
		this.com.print("Grazie per aver giocato!");
	}

	private void azzera(){
		// rimuovo la nave al giocatore
		for(Giocatore giocatore : this.giocatori){
			giocatore.azzeraNave();
		}
	}


	/**
	 * Metodo per la gestione delle opzioni svolte dal giocatore sulle tessere in fase di conf della nave
	 * @param g Giocatore
	 */
	private void turno(Giocatore g){
		
		// visualizzazione della nave
		visualizzaNave(g);
		
		// azioneCarta verifica gia' a monte se si puo' fare 
		// una determinata azione
		int azioneCarta = azioneCarta(g);
		
		switch(azioneCarta){
			case 1 ->{
				// per aggiungere una tessera gia' generata al menu'
				azioneAssemblaggio1(g);
			}
			case 2 ->{	
				// per generare una nuova tessera	
				azioneAssemblaggio2(g);
			}
			case 3 ->{
				// per utilizzare una tessera prenotata
				azioneAssemblaggio3(g);
			}
		}
		this.com.clear();
	}
	
	/**
	 * Metodo che esegue la scelta utente per usare, prenotare o scartare la tessera
	 * @param tessera Tessera
	 * @param g Giocatore
	 */
	private void azioneAssemblaggio(Tessera tessera, Giocatore g) {
		
		boolean condizione=false;
		do {
			//visualizzazione della nave
			this.com.clear();
			this.com.println(g.getNave().toString());
			
			// visualizzazione della tessera
			this.com.println("Tessera:");
			this.com.print(tessera.toString());
			
			switch(menuScelte()){
				case 1->{
					// prenotazione della tessera
					condizione=prenotaTessera(g, tessera);
				}
				case 2->{
					// inserimento della tessera nella nave
					condizione=usaTessera(g, tessera);
					
				}
				case 3->{
					// scarta tessera
					try {
						tessera.aggiungiTessera();
					}catch(ErroreAggiuntaTessera eAt){
						this.com.printError(eAt.getMessage());
					}
					condizione=true;
				}
				
			}
		}while(condizione==false);
	}

	/**
	 * Metodo per usare una tessera del mazzo
	 * @param g Giocatore
	 */
	private void azioneAssemblaggio1(Giocatore g){
		//se lista=vuota allora richiama turno
		if(Tessera.getListaTessere().isEmpty()){
			turno(g);
		}else{
			// per utilizzare una tessera gia' estratta e salvata nel mazzo degli scarti
			visualizzaElencoTessere(Tessera.getListaTessere());
			Tessera tesseraSelezionata = selezionaTesseraDalMazzo(g);
			
			if(tesseraSelezionata != null && tesseraSelezionata.getTipoTessera() != TipoTessera.VUOTA){
				this.azioneAssemblaggio(tesseraSelezionata, g);
			}else {
				turno(g);
			}
		}
	}

	/**
	 * Metodo per usare una tessera generata
	 * @param g Giocatore
	 */
	private void azioneAssemblaggio2(Giocatore g){
		Tessera tessera = nuovaTesseraRandom();

		this.azioneAssemblaggio(tessera, g);
	}

	/**
	 * Metodo per usare una tessera prenotata
	 * @param g Giocatore
	 */
	private void azioneAssemblaggio3(Giocatore g){
		Tessera t=usaTesseraPrenotata(g);
		if(t!=null) {
			this.azioneAssemblaggio(t, g);
		}else {
			turno(g);
		}
		
	}
	
	// ----------------- NAVE -----------------
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
	 * Metodo per assemblare la nave a turno
	 */
	private void assemblaNavi(){
	    ArrayList<Giocatore> giocatori = new ArrayList<>(this.giocatori);
	    ArrayList<Pedina> elencoPedineFinite = new ArrayList<>();
		Map<Giocatore, Integer> turniResidui = new HashMap<>();
	    final int TURNI_EXTRA = 10;
	    boolean countdownAttivo = false;
	    int contatoreFinale = 1;

	    // inizializza turniResidui a 0 per tutti
	    for(Giocatore g : giocatori){
	        turniResidui.put(g, 0);
	    }

	    while(true){

	        for(Giocatore g : giocatori){
	            if (g.isNaveFinita()) continue;

	            // Se countdown è attivo e il giocatore ha esaurito i turni, salta
	            if(countdownAttivo && turniResidui.getOrDefault(g, 0) <= 0){
	                continue;
	            }

	            this.com.println("\n\nTurno del giocatore: " + g.getNome());

	            if(countdownAttivo){
	                int residui = turniResidui.getOrDefault(g, 0);
	                this.com.println("Numero mosse ancora disponibili: " + residui);
	            }

				turno(g);

	            // Ricontrolla se ha completato la nave
	            if(g.isNaveFinita() || naveFinita(g)){
	                g.naveFinita();
	                //TODO controlla la posizione di pedina
	                g.getPedina().setPosizioneSulTabellone(contatoreFinale++);
					// aggiunta della pedina in un elenco temporaneo
					if(!elencoPedineFinite.contains(g.getPedina())) {
						// se la pedina non e' gia' nell'elenco, allora viene aggiunta
						elencoPedineFinite.add(g.getPedina());
					}
	                this.com.clear();
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

	    		this.com.clear();
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

		this.tabellone.aggiungiPedineAlTabellone(elencoPedineFinite);
	}

	/**
	 * Metodo per chiedere conferma se la nave e' finita
	 * @param giocatore Giocatore
	 * @return true se finita e false se il contrario
	 */
	private boolean naveFinita(Giocatore giocatore){
		this.com.clear();
		// visualizzazione della nave
		this.com.println("Nave di "+ giocatore.getNome());
		visualizzaNave(giocatore);

		this.com.println("Hai finito la nave? ");
		return this.com.conferma();
	}

	/**
	 * Metodo per visualizzare la nave del giocatore
	 * @param g Giocatore
	 */
	private void visualizzaNave(Giocatore g){
		this.com.println(g.getNave().toString());
	}

	// ----------------- TESSERE -----------------
	/**
	 * Metodo per generare una nuova tessera a random
	 * @return true se andata buon fine e false se il contrario
	 */
	private Tessera nuovaTesseraRandom(){
		FactoryTessera ft = new FactoryTessera();
		Tessera t = null;
		boolean pass = false;
		do{
			try{
				t = ft.estraiTipo();
			}catch(ErroreTessera et){
				this.com.printError(et.getMessage());
			}

			if(null != t){
				pass = true;
			}
		}while(false == pass);
		return t;
	}

	/**
	 * Metodo per selezionare la tessera dal mazzo
	 * @param g 
	 * @return  tessera
	 */
	private Tessera selezionaTesseraDalMazzo(Giocatore g){
		int selezione;
		Tessera t = null;
		boolean pass = false;
		do{ 
			try{
				this.com.println("Inserisci il numero della tessera che si vuole inserire (inserire 0 per tornare al menu): ");
				selezione = this.com.consoleReadInt()-1;
				
				// ritorno al menu
				if(-1 == selezione) {
					return null;
				}

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
			return selezionaTesseraDalMazzo(g);
		}		
		return t;
	}

	/**
	 * Metodo per visualizzare l'elenco delle tessere
	 * 
	 * @param tessere ArrayList<Tessera>
	 */
	private void visualizzaElencoTessere(ArrayList<Tessera> tessere){
		for(int i = 0; i < tessere.size(); i+= 1){
			this.com.println("Tessera " + (i+1));
			this.com.println(tessere.get(i).toString());
		}
		
	}

	// ----------------- TESSERE PRENOTATE -----------------
	/**
	 * Metodo per prenotare la tessera
	 * @param giocatore Giocatore
	 * @param tessera Tessera
	 * @return true se andato a buon fine e false se il contrario
	 */
	private boolean prenotaTessera(Giocatore giocatore, Tessera tessera){
		try{
			giocatore.getNave().prenotaTessera(tessera);
			this.com.println("Hai prenotato la tessera!!");
			Tessera.removeDaListaTessere(tessera);
		}catch(ErroreTessera et){
			this.com.printError(et.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Metodo per rimuovere la tessera prenotata
	 * @param giocatore Giocatore
	 * @return tessera Tessera
	 */
	private Tessera usaTesseraPrenotata(Giocatore giocatore){
		this.com.println(giocatore.getNave().tesserePrenotateToString());
		
		this.com.println("Inserisci il numero della tessera che vuoi utilizzare: ");
		this.com.println("(premere 0 per tornare indietro)\n");
		int numero = -1;
		boolean pass = false;
		do{ 
			try{
				numero = this.com.consoleReadInt()-1;
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
			if(numero == -1){
				return null;
			}
			if(numero < -1){
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

	// ----------------- TESSERA NAVE -----------------
	/**
	 * Metodo per inserire la tessera nella nave
	 * 
	 * @param giocatore Giocatore
	 * @param tessera Tessera
	 * @return false se non fatto e true se fatto
	 */
	private boolean inserisciTesseraNellaNave(Giocatore giocatore, Tessera tessera){
		
		this.com.clear();
		visualizzaNave(giocatore);
		this.com.println("Tessera da inserire");
		this.com.println(tessera.toString());

		int x = 0, y = 0;
		try{
			this.com.println("[Inserisci (x)=0 e (y)=0 per uscire]");
			this.com.println("Inserisci la coordinata x: ");
			x = this.com.consoleReadInt()-giocatore.getNave().getInizioNaveX();
			this.com.println("Inserisci la coordinata y: ");
			y = this.com.consoleReadInt()-giocatore.getNave().getInizioNaveY();
			
			//condizione per far tornare al menu
			if(x+giocatore.getNave().getInizioNaveX() == 0 && y+giocatore.getNave().getInizioNaveY() == 0) {
				return false;
			}
		}catch(NumberFormatException nfe){
			this.com.erroreImmissioneValore();
			return inserisciTesseraNellaNave(giocatore, tessera);
		}

		Coordinate c = new Coordinate(x, y);
		if(aggiungiTesseraDentroNave(giocatore, tessera, c)){
			this.com.println("Tessera aggiunta alla nave con successo!");
			Tessera.removeDaListaTessere(tessera);
			return true;
		}else{
			return inserisciTesseraNellaNave(giocatore, tessera);
		}
	}

	/**
	 * Metodo per aggiungere la tessera alla nave
	 * @return true -> ok
	 * 			false -> no
	 */
	private boolean aggiungiTesseraDentroNave(Giocatore giocatore, Tessera tessera, Coordinate coordinate){
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
	 * Metodo che chiede all'utente se vuole ruotare o inserire tessera nella nave
	 * @param giocatore Giocatore
	 * @param tessera Tessera
	 * @return true se inserimento tessera andato a buon fine, false se il contrario
	 */
	private boolean usaTessera(Giocatore giocatore, Tessera tessera) {
		
		boolean condizione = false;
		boolean check = false;
		do {
			//stampa nave e tessera
			this.com.clear();
			visualizzaNave(giocatore);
			this.com.println("Tessera da inserire");
			this.com.println(tessera.toString());
			
			condizione=false;
			check = false;
			
			ArrayList<String> elenco = new ArrayList<>();
			this.com.println("Scegli cosa fare:");
			elenco.add("Ruota a dx di 90 gradi");
			elenco.add("Inserire la tessera nella Nave");
			elenco.add("Torna indietro");
			this.com.println(this.com.visualizzaElenco(elenco));
			int val = this.com.consoleReadInt();
			if(val==1 || val==2|| val==3) {
				condizione=true;
				if(val==2) {
					check=inserisciTesseraNellaNave(giocatore, tessera);
				}
				if(val==1) {
					condizione=false;
					check=false;
					try {
						tessera.ruota();
					} catch (ErroreRotazione e) {
					}
				}
				if(val==3) {
					azioneAssemblaggio(tessera, giocatore);
					check=true;
				}
			}
			
		}while(condizione==false|| check==false);
		return check;
	}

	// ----------------- MENU' -----------------
	/**
	 * Metodo per selezionare se si vuole 
	 * pescare dal mazzo o utilizzare la tessera
	 * @param g Giocatore
	 * @return scelta
	 */
	private int azioneCarta(Giocatore g){
		ArrayList<String> elenco = new ArrayList<>();
		elenco.add("Per utilizzare una tessera dalla pila degli scarti del mazzo");
		elenco.add("Per pescare una nuova tessera dal mazzo");
		elenco.add("Per utilizzare una tessera dalla pila delle tessere riserva");
		
		int risposta = 0;
		boolean pass = false;
		do{
			this.com.println("Selezionare azione:");
			this.com.println(this.com.visualizzaElenco(elenco));
			this.com.println("Inserisci il numero dell'azione desiderata: ");
			try{
				risposta = this.com.consoleReadInt();
				if(risposta < 1 || risposta > 3) {
					pass = false; 
					this.com.erroreImmissioneValore();
				}else{
					pass = true;
				}
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(false == pass);
		

        // controllo se la selezione fatta e' possibile 
		switch(risposta) {
			case 1 ->{
				// controllo se ci sono tessere tessere gia' estratte nel mazzo
				if(Tessera.getListaTessere().isEmpty()){
					this.com.printError("Non ci sono tessere estratte");
					return azioneCarta(g);
				}
			}
			case 2 ->{
				// controllo se si e' raggiunto il massimo delle tessere generabili
				if(FactoryTessera.getNumeroTessere() >= FactoryTessera.getNumeroTessereMax()){
					this.com.printError("Numero massimo di tessere estratte raggiunto");
					return azioneCarta(g);
				}
			}
			case 3 ->{
				// controllo se si hanno tessere prenotate
				if(g.getNave().isComponentiPrenotatiEmpty()){
					this.com.printError("Non hai tessere prenotate da usare");
					return azioneCarta(g);
				}
			}
		}
		
		return risposta;
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
		azioni.add("Scarta tessera");

		int scelta = 0;
		boolean pass = false;
		do{
			this.com.println(this.com.visualizzaElenco(azioni));
			try{
				scelta = this.com.consoleReadInt();
				if(scelta == 1 || scelta == 2 || scelta == 3){
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

	// ----------------- NAVE: AGGIUNTA EQUIPAGGIO -----------------
	/**
	 * Metodo per aggiungere l'equipaggio alla nave
	 */
	private void aggiungiEquipaggio(){
		for(Giocatore giocatore : this.giocatori){
			GestioneEquipaggio ge = new GestioneEquipaggio(giocatore);
			ge.aggiungiEquipaggio();
		}
	}

	@Override
	public String toString(){
		return "Numero giocatori: " + this.numeroGiocatori + "\n" +
				"Modalita' partita: " + this.modalitaPartita;
	}
}
