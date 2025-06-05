package partita.configurazione;

import gioco.ComunicazioneConUtente;
import java.util.ArrayList;
import partita.Livelli;
import partita.ModalitaPartita;
import partita.Partita;

public class ConfiguraPartita{
	private final ComunicazioneConUtente com;	
    private int numeroGiocatori;
    private ModalitaPartita modalitaPartita;
	private Livelli livelloPartita;

    public ConfiguraPartita(){
		this.com = ComunicazioneConUtente.getIstanza();
		this.livelloPartita = Livelli.PRIMO;
    }

	public Partita creaPartita(){
		configuraPartita();
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			return new Partita(this.numeroGiocatori, this.livelloPartita);	
		}	
		return new Partita(this.numeroGiocatori);
    }

	/**
	 * Metodo per configurare la partita
	 */
    private void configuraPartita(){
		this.numeroGiocatori = numeroGiocatori();
		this.modalitaPartita = modalitaPartita();

		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			this.livelloPartita = livelloPartita();
		}
		
		// conferma e modifica scelte
		boolean conferma;
		do{
			visualizzaScelte();
			conferma = this.com.conferma();
			if(!conferma){
				modificaScelte();
				visualizzaScelte();
				conferma = this.com.conferma();
			}

		}while(!conferma);
		this.com.clear();
	}

	/**
	 * Metodo per l'immissione del numero dei giocatori
	 */
	private int numeroGiocatori(){
		int numeroGiocatori = 2;
		boolean uscita = false;
		do{
			this.com.print("Inserisci il numero dei giocatori (min 2, max 4): ");
			try{
				numeroGiocatori = this.com.consoleReadInt();
				if(numeroGiocatori < 2 || numeroGiocatori > 4) {
					this.com.erroreImmissioneValore();
					uscita = false;
				}else {
					uscita = true;
				}
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}			
		}while(false == uscita);
		return numeroGiocatori;
	}

	/**
	 * Metodo per la selezione della modalita' della partita
	 * @return Modalita' della partita
	 */
	private ModalitaPartita modalitaPartita(){
		int mod = 0;
		ArrayList<String> elenco = new ArrayList<>();
		elenco.add("Partita singola");
		elenco.add("Partita multipla");
		boolean uscita = false;
		do{
			this.com.println("Modalita' partita disponibile: ");
			this.com.print(this.com.visualizzaElenco(elenco));
			this.com.print("Inserisci la modalita' partita: ");
			try{
				mod = this.com.consoleReadInt();
				if(mod == 1 || mod == 2){
					uscita = true;
				}else{
					this.com.erroreImmissioneValore();
					uscita = false;
				}
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(false == uscita);

		return ModalitaPartita.toModalitaPartita(mod);
	}

	/**
	 * Metodo per selezionare il metodo della partita
	 * @return Livello della partita
	 */
	private Livelli livelloPartita(){
		int livello = 0;
		ArrayList<String> elenco = new ArrayList<>();
		elenco.add("Livello 1");
		elenco.add("Livello 2");
		elenco.add("Livello 3");

		boolean uscita = false;
		do{
			this.com.println("Livelli disponibili: ");
			this.com.print(this.com.visualizzaElenco(elenco));
			this.com.print("Inserisci il livello: ");
			try{
				livello = this.com.consoleReadInt();
				if(livello == 1 || livello == 2 || livello == 3){
					uscita = true;
				}else{
					this.com.erroreImmissioneValore();
					uscita = false;
				}
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
			
		}while(false == uscita);

		return Livelli.getLivello(livello);
	}

	/**
	 * Metodo per visualizzare il menu
	 */
	private void visualizzaScelte(){
		this.com.clear();
		this.com.println("--- Conferma dei valori immessi ---");
		this.com.println("Numero giocatori: " + this.numeroGiocatori);
		this.com.println("Modalita' partita: " + this.modalitaPartita);
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			this.com.println("Livello partita: " + this.livelloPartita);
		}
	}

	/**
	 * Metodo per modficare le scelte
	 */
	private void modificaScelte(){
		int s = 0;
		ArrayList<String> elenco = new ArrayList<>();
		elenco.add("Per modificare il numero dei giocatori");
		elenco.add("Per modificare la modalita' della partita");
		
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			elenco.add("Per modificare il livello della partita");
		}
		this.com.print(this.com.visualizzaElenco(elenco));
		this.com.print("Inserisci la voce: ");
		try{
			s = this.com.consoleReadInt();
		}catch(NumberFormatException nfe){
			this.com.erroreImmissioneValore();
		}
		
		switch(s){
			case 1 ->{
				this.numeroGiocatori = numeroGiocatori();
			}
			case 2 ->{
				this.modalitaPartita = modalitaPartita();
			}
			case 3 -> {
				if(this.modalitaPartita == ModalitaPartita.SINGOLA){
					this.livelloPartita = livelloPartita();
				}
				else{
					this.com.erroreImmissioneValore();
					modificaScelte();	
				}
			}
			default ->{
				this.com.erroreImmissioneValore();
				modificaScelte();
			}
		}
	}
}
