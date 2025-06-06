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
			if(this.modalitaPartita == ModalitaPartita.MULTIPLA &&
				this.livelloPartita == Livelli.TERZO){
					partitaInCorso = false;
			}
		}
	}

	/**
	 * Metodo per la gestione delle opzioni svolte dal giocatore sulle tessere in fase di conf della nave
	 * @param g Giocatore
	 */
	private void turno(Giocatore g){
		this.com.clear();
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
			Tessera tesseraSelezionata = selezionaTesseraDalMazzo();
			
			if(tesseraSelezionata != null && tesseraSelezionata.getTipoTessera() != TipoTessera.VUOTA){
				this.azioneAssemblaggio(tesseraSelezionata, g);
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
		this.azioneAssemblaggio(usaTesseraPrenotata(g), g);
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
	        boolean almenoUnoHaAgito = false;

	        for(Giocatore g : giocatori){
	            if (g.isNaveFinita()) continue;

	            // Se countdown è attivo e il giocatore ha esaurito i turni, salta
	            if(countdownAttivo && turniResidui.getOrDefault(g, 0) <= 0){
	                continue;
	            }

	            this.com.println("\n\nTurno del giocatore: " + g.getNome());
	            visualizzaNave(g);

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
	 * @return  tessera
	 */
	private Tessera selezionaTesseraDalMazzo(){
		int selezione;
		Tessera t = null;
		boolean pass = false;
		do{ 
			try{
				this.com.println("Inserisci il numero della tessera che si vuole inserire (inserire 0 per tornare al menu): ");
				selezione = this.com.consoleReadInt()-1;
				
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
		int numero = -1;
		boolean pass = false;
		do{ 
			try{
				numero = this.com.consoleReadInt()-1;
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
	 * Chiede all'utente se vuole ruotare o inserire tessera nella nave
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
			this.com.println(this.com.visualizzaElenco(elenco));
			int val = this.com.consoleReadInt();
			if(val==1 || val==2) {
				condizione=true;
				if(val==2) {
					check=inserisciTesseraNellaNave(giocatore, tessera);
				}else if(val==1) {
					condizione=false;
					check=false;
					try {
						tessera.ruota();
					} catch (ErroreRotazione e) {
					}
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
		elenco.add("Per utilizzare una tessera dal mazzo");
		elenco.add("Per generare una nuova tessera");
		elenco.add("Per utilizzare una tessera prenotata");
		
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
		Iterator<Giocatore> giocatoreIterator = this.giocatori.iterator();
		while(giocatoreIterator.hasNext()){
			Giocatore g = giocatoreIterator.next();

			this.com.println("Il giocatore: " + g.getNome() + " deve imbarcare l'equipaggio");
			riepilogoEquipaggio(g);

			// carico tutti i moduli equipaggio umano con 2 cosmonauti
			g.getNave().setCosmonauti();

			GestioneEquipaggio equipaggio = g.getNave().getModuliEquipaggio();
			equipaggio.setNumeroCosmonauti(g.getNave().getCosmonauti());			

			if(equipaggio.getNumeroModuliAlini() > 0){
				this.com.print("Attualmente sulla nave ci sono " + equipaggio.getNumeroCosmonauti() + " cosmonauti");
				this.com.println(" attualmente disposti su " + String.valueOf(equipaggio.getNumeroModuliCosmonauti() + 1) + " moduli (centro incluso)");

				this.com.println("Sulla nave ci sono " + equipaggio.getNumeroModuliAlini() + " moduli predisposti per ospitare gli alieni, di cui");
				this.com.println("- " + equipaggio.getNumeroModuliAlieniMarroni() + " moduli per alieni marroni");
				this.com.println("- " + equipaggio.getNumeroModuliAlieniViola() + " moduli per alieni viola");
								
				this.com.println("Vuoi aggiungere l'equipaggio non terrestre al tuo vascello intergalattico? ");
				if(this.com.conferma()){
					imbarcaAlieniSullaNave(g, equipaggio);
				}else{
					this.com.println("Il tuo rimorchiatore cargo intergalattico partia' senza equipaggio marziano");
				}
			}else{
				this.com.println("Non ci sono moduli adibiti al trasporto validi all'interno della tua nave");
			}
			this.com.println("premi invio per continuare...");
			this.com.consoleRead();
			this.com.clear();
			this.com.println("La tua nave partira' con il seguente equipaggio: ");
			riepilogoEquipaggio(g);
			this.com.println("premi invio per continuare...");
			this.com.consoleRead();
			this.com.clear();
		}
	}

	/**
	* Metodo per imbarcare gli alieni sulla nave
	*/
	private void imbarcaAlieniSullaNave(Giocatore g, GestioneEquipaggio equipaggio){
		// marroni
		if(equipaggio.getNumeroModuliAlieniMarroni() > 0){
			int scelta = menuImbarco("Inserimento alieni marroni");
			imbarca(scelta, g, equipaggio, 1);
		}

		// viola
		if(equipaggio.getNumeroModuliAlieniViola() > 0){
			int scelta = menuImbarco("Inserimento alieni viola");
			imbarca(scelta, g, equipaggio, 2);
		}
	}

	/**
	 * Menu di scelta per l'imbarco degli alieni
	 * @param messaggio String
	 * @return risp int
	 */
	private int menuImbarco(String messaggio){
		ArrayList<String> elenco = new ArrayList<>();
		elenco.add("Per aggiungere tutti gli alieni");
		elenco.add("Per aggiungere un numero personalizzato di alieni");

		int risp = 0;
		boolean pass = false; 
		do{
			this.com.println(messaggio);
			try{
				risp = this.com.consoleReadInt();
				if(risp < 1 || risp > 2){
					pass = false;
					this.com.erroreImmissioneValore();
				}else{
					pass = true;
				}
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
			pass = true;
		}while(false == pass);

		return risp;
	}

	/**
	 * Metodo per imbarcare gli alieni sulla nave
	 * 
	 * @param scelta int
	 * @param g	Giocatore
	 * @param ge GestioneEquipaggio
	 * @param id int
	 */
	private void imbarca(int scelta, Giocatore g, GestioneEquipaggio ge, int id){
		switch(scelta){
			case 1 ->{
				// tutti gli alieni
				if(id == 1){
					// alieni marroni
					this.com.println("Dopo questa operazione ti rimarrebbero " + String.valueOf(ge.getNumeroCosmonauti() - (2)*ge.getNumeroModuliAlieniMarroni()) + " cosmonauti");
					if(this.com.conferma()){
						g.getNave().setAlieniMarroni(ge.getNumeroModuliAlieniMarroni());
					}else{
						menuImbarco("Inserimento alieni marroni");
					}
				}else if(id == 2){
					// alieni viola
					this.com.println("Dopo questa operazione ti rimarrebbero " + String.valueOf(ge.getNumeroCosmonauti() - (2)*ge.getNumeroModuliAlieniViola()) + " cosmonauti");
					if(this.com.conferma()){
						g.getNave().setAlieniViola(ge.getNumeroModuliAlieniViola());
					}else{
						menuImbarco("Inserimento alieni viola");
					}
				}
			}
			case 2 ->{
				if(id == 1){
					// marroni
					this.com.println("Inserisci il numero di alieni marroni che vuoi immettere nella tua nave: ");
					int numeroAlieni = numeroAlieniCustom(id, ge);
					
					this.com.println("Dopo questa operazione ti rimarrebbero " + String.valueOf(ge.getNumeroCosmonauti() - (2)*numeroAlieni) + " cosmonauti");
					if(this.com.conferma()){
						g.getNave().setAlieniMarroni(ge.getNumeroModuliAlieniMarroni());
					}else{
						menuImbarco("Inserimento alieni marroni");
					}
				}else if(id == 2){
					// viola
					this.com.println("Inserisci il numero di alieni viola che vuoi immettere nella tua nave: ");
					int numeroAlieni = numeroAlieniCustom(id, ge);
					
					this.com.println("Dopo questa operazione ti rimarrebbero " + String.valueOf(ge.getNumeroCosmonauti() - (2)*numeroAlieni) + " cosmonauti");
					if(this.com.conferma()){
						g.getNave().setAlieniViola(ge.getNumeroModuliAlieniMarroni());
					}else{
						menuImbarco("Inserimento alieni viola");
					}
				}
			}
		}
	}

	/**
	 * Metodo per specificare il numero di alieni che si vogliamo imbarcare sulla nave
	 * 
	 * @param id int
	 * @param ge GestioneEquipaggio
	 * @return numero di alieno
	 */
	private int numeroAlieniCustom(int id, GestioneEquipaggio ge){
		int numero = 0;
		boolean pass = false; 
		do{
			try{
				numero = this.com.consoleReadInt();
				if(numero < 0){
					this.com.erroreImmissioneValore();
				}

				switch(id){
					case 1 ->{
						if(numero > ge.getNumeroModuliAlieniMarroni()){
							this.com.erroreImmissioneValore();
						}else{
							pass = true;
						}
					}
					case 2 ->{
						if(numero > ge.getNumeroModuliAlieniViola()){
							this.com.erroreImmissioneValore();
						}else{
							pass = true;
						}
					}
				}

			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(false == pass);
		return numero;
	}

	/**
	 * Metodo per riepilogare l'equipaggio presente sulla nave
	*/
	private void riepilogoEquipaggio(Giocatore g){
		this.com.println("Equipaggio attualmente presente sulla nave: ");
		this.com.println("Numero cosmonauti: " + g.getNave().getCosmonauti());
		this.com.println("Numero alieni marroni: " + g.getNave().getAlieniMarrone());
		this.com.println("Numero alieni viola: " + g.getNave().getAlieniViola());
		this.com.println("\npremi invio per continuare...");
		this.com.consoleRead();
		this.com.clear();
	}

	@Override
	public String toString(){
		return "Numero giocatori: " + this.numeroGiocatori + "\n" +
				"Modalita' partita: " + this.modalitaPartita;
	}
}
