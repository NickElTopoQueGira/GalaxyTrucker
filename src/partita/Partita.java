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
		}
		else{
			// se il tabellone e' gia' creato
			
			if(this.livelloPartita != Livelli.TERZO){
				// se non sono all'utlimo livello 
				this.tabellone = null;
				// tabellone del livello successivo
				this.livelloPartita = livelloPartita.next();
				this.tabellone = new Tabellone(livelloPartita);
			}
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
		final int TURNI_EXTRA = 10;
		boolean assemblaggioInCorso = true;
		ArrayList<Tessera> elencoTessere = new ArrayList<>();

		// per controllare se qualche giocatore ha finito per primo la nave
		boolean finito = false; 
		int contatoreFinale = 1;

		while(assemblaggioInCorso){
			for(int i = 0; i < this.numeroGiocatori; i += 1){
				Giocatore g = giocatori.get(i);
				this.com.println("");
				this.com.println("");
				/**
				 * Verifica nel caso in cui il giocatore non abbia finito 
				 * pre primo l'assemblaggio della nave, quanti turni 
				 * gli rimangono per finire. 
				 * Se il giocatore ha 0 turni residui, allora si continua a quello dopo 
				 */
				if(finito){
					if(g.isNaveFinita() || turniResidui.get(g) <= 0){
						continue;
					}
					this.com.print("Numero mosse ancora disponibili: ");
					this.com.printNumber(turniResidui.get(g));
					this.com.println("");
				}
				
				this.com.println("Turno del giocatore: " + g.getNome());
				this.com.println(g.getNave().toString());
				this.com.println("Vuoi modificare la nave?");
				if(this.com.conferma()){
					// modifica la nave
					turno(g, elencoTessere);
				}

				/**
				 * Guardare condizione a riga 168
				 * "=^.^="
				 */

				// controllo se la nave e' finita
				if(g.isNaveFinita()){
					// il giocatore ha finito la nave
					this.com.println("Il giocatore " + g.getNome() + " ha finito la nave");
					finito = true;
				}else{
					// chiedo se ha finito la nave
					if(naveFinita(g)){
						g.naveFinita();
						this.com.println("Il giocatore " + g.getNome() + " ha finito la nave");
						finito = true;
					}
				}

				if(finito){
					// posizione sul tabellone
					g.getPedina().setPosizioneSulTabellone(contatoreFinale);
					contatoreFinale += 1;

					this.com.println("Inizio conto alla rovescia di " + TURNI_EXTRA);
					finito = false;
					// inizializzazione conto alla rovescia
					for(Giocatore gg : giocatori){
						if(gg.equals(g) == false && gg.isNaveFinita() == false){
							turniResidui.put(gg, TURNI_EXTRA);
						}
					}
				}

				// decremento se il conto alla rovescia e' attivo
				if(finito == false && g.isNaveFinita() == false){
					turniResidui.put(g, turniResidui.getOrDefault(g, TURNI_EXTRA) - 1);
				}
			}
			
			
			
			// verifico se tutti i giocatori hanno finito
			boolean tuttiHannoFinito = false;
			for(Giocatore gg : giocatori){
				if(gg.isNaveFinita() == false){
					if(finito == false){
						tuttiHannoFinito = false;
					}else if(turniResidui.getOrDefault(gg, 1) > 0){
						tuttiHannoFinito = false;
						break;
					}
				}
			}
			if(tuttiHannoFinito){
				assemblaggioInCorso = false;
				this.com.println("Tutti i giocatori hanno finto di assemblare la nave");
			}
		}
	}

	/**
	 * motodo per la gestione delle opzioni svolte dal giocatore sulle tessere in fase di conf della nave
	 */
	private void turno(Giocatore g, ArrayList<Tessera> elencoTessere){
		// azioneCarta verifica gia' a monte se si puo' fare 
		// una determinata azione
		int azioneCarta = azioneCarta(g, elencoTessere);
							
		switch(azioneCarta){
			case 1 ->{
				// per utilizzare una tessera gia' estratta e salvata nel mazzo degli scarti
				visualizzaElencoTessere(elencoTessere);
				Tessera tesseraSelezionata = selezionaTesseraDalMazzo(elencoTessere);
				if(null == tesseraSelezionata){
					turno(g, elencoTessere);
				}
				
				if(tesseraSelezionata != null && tesseraSelezionata.getTipoTessera() != TipoTessera.VUOTA){
					this.com.println("Vuoi prenotare la tessera (altrimenti la tessera verrà inserita in nave)?");
					if(this.com.conferma()){
						prenotaTessera(g, tesseraSelezionata);
					}else{
						inserisciTesseraNellaNave(g, tesseraSelezionata);
						elencoTessere.remove(tesseraSelezionata);
						
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
					}
					case 2 ->{
						inserisciTesseraNellaNave(g, usaTesseraPrenotata(g));
						ciclo = false;
					}
					case 3 ->{
						try {
							tessera.ruota();
						} catch (ErroreRotazione e) {
							e.printStackTrace();
						}
					}					
					case 4 ->{
						elencoTessere.add(tessera);
						ciclo = false;
					}
					}
				}while(ciclo);
			}
			case 3 ->{
				// per utilizzare una tessera prenotata
				//inserisciTessera(g, usaTesseraPrenotata(g));
				inserisciTesseraNellaNave(g, usaTesseraPrenotata(g));
				
			}
		}	
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
	private Tessera selezionaTesseraDalMazzo(ArrayList<Tessera> elencoTessere){
		int selezione;
		Tessera t = null;
		boolean pass = false;
		do{ 
			try{
				this.com.println("Inserisci il numero della tessera che si vuole inserire (inserire 0 per tornare al menu): ");
				selezione = Integer.parseInt(this.com.consoleRead())-1;
				
				// ritorno al menu
				if(-1 == selezione) return null;

				if(elencoTessere.contains(elencoTessere.get(selezione))){
					this.com.print("Tessera selezionata: \n");
					t = elencoTessere.get(selezione);
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
			return selezionaTesseraDalMazzo(elencoTessere);
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
			/*finally{
				Coordinate c = new Coordinate(x, y);
				try{
					this.com.println("XY:("+x+"; "+y+") {debug}");
					giocatore.getNave().inserisciTessera(c, tessera);
					
				}catch(ErroreTessera et){
					this.com.printError(et.getMessage());
					
				}catch(ErroreCoordinate ec){
					this.com.printError(ec.getMessage());
					
				}finally{
					//this.com.print("Pezzo inserito correttamente"); // TODO ta togliere o spostare 
				}
				
			*/
			//TODO il codice è duplicato... una parte va cancellata
		
		Coordinate c = new Coordinate(x, y);
		if(aggiungiTesseraNellanave(giocatore, tessera, c)){
			this.com.println("Tessera aggiunta alla nave con successo!");
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
	private int azioneCarta(Giocatore g, ArrayList<Tessera> elencoTessereEstratte){
		ArrayList<String> elenco = new ArrayList<>();
		elenco.add("Per utilizzare una tessera dal mazzo");
		elenco.add("Per generare una nuova tessera");
		elenco.add("Per utilizzare una tessera prenotata");
		this.com.println(this.com.visualizzaElenco(elenco));
		
		int rispota = 0;
		do{			
			try{
				rispota = Integer.parseInt(this.com.consoleRead());
				
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(rispota < 1 || rispota > 3);


        // controllo se la selezione fatta e' possibile 
		switch(rispota) {
			case 1 ->{
				if(elencoTessereEstratte.isEmpty()){
					this.com.printError("Non ci sono tessere estratte");
					return azioneCarta(g, elencoTessereEstratte);
				}
			}
			case 3 ->{
				if(g.getNave().isComponentiPrenotatiEmpty()){
					this.com.printError("Non hai tessere prenotate da usare");
					return azioneCarta(g, elencoTessereEstratte);
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
