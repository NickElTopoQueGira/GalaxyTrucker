package partita.configurazione;

import java.util.ArrayList;

import gioco.ComunicazioneConUtente;
import partita.Livelli;
import partita.ModalitaPartita;
import partita.Partita;

public class ConfiguraPartita{
	private ComunicazioneConUtente com;	
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
		return new Partita(this.numeroGiocatori, this.modalitaPartita);
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
		boolean conferma = true;
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
		int numeroGiocatori = 0;
		do{
			this.com.print("Inserisci il numero dei giocatori (min 2, max 4): ");
			try{
				numeroGiocatori = Integer.parseInt(this.com.consoleRead());
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(numeroGiocatori < 2 || numeroGiocatori > 4);

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
		do{
			this.com.println("Modalita' partita disponibile: ");
			this.com.print(this.com.visualizzaElenco(elenco));
			this.com.print("Inserisci la modalita' partita: ");
			try{
				mod = Integer.parseInt(this.com.consoleRead());
			}catch(NumberFormatException nfe){
				this.com.erroreImmissioneValore();
			}
		}while(mod != 1 && mod != 2);

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

		do{
			this.com.println("Livelli disponibili: ");
			this.com.print(this.com.visualizzaElenco(elenco));
			this.com.print("Inserisci il livello: ");
			try{
				livello = Integer.parseInt(this.com.consoleRead());
			}catch(NumberFormatException nfe){
				this.com.println("Inserisci un numero valido!!");
			}
			
		}while(livello < 1 || livello > 3);

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
		ArrayList<String> elenco = new ArrayList<String>();
		elenco.add("Per modificare il numero dei giocatori");
		elenco.add("Per modificare la modalita' della partita");
		
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			elenco.add("Per modificare il livello della partita");
		}
		this.com.print(this.com.visualizzaElenco(elenco));
		this.com.print("Inserisci la voce: ");
		try{
			s = Integer.parseInt(this.com.consoleRead());
		}catch(NumberFormatException nfe){
			this.com.println("Inserisci un valore valido!!");
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
