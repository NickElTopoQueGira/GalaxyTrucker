package partita.configurazione;

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
		com = ComunicazioneConUtente.getIstanza();
		this.livelloPartita = Livelli.PRIMO;
    }

	public Partita creaPartita(){
		configuraPartita();
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			return new Partita(this.numeroGiocatori, this.livelloPartita);	
		}	
		return new Partita(this.numeroGiocatori, this.modalitaPartita);
    }

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
			conferma = conferma();
			if(!conferma){
				modificaScelte();
				visualizzaScelte();
				conferma = conferma();
			}

		}while(!conferma);
		com.clear();
	}

	private int numeroGiocatori(){
		int numeroGiocatori = 0;
		do{
			com.print("Inserisci il numero dei giocatori (min 2, max 4): ");
			try{
				numeroGiocatori = Integer.parseInt(com.consoleRead());
			}catch(NumberFormatException nfe){
				com.println("Inserisci un valore valido!!");
			}
		}while(numeroGiocatori < 2 || numeroGiocatori > 4);

		return numeroGiocatori;
	}

	private ModalitaPartita modalitaPartita(){
		int mod = 0;
		do{
			com.println("Modalita' partita disponibile: ");
			com.println("1) Partita singola");
			com.println("2) Partita multipla");
			com.print("Inserisci la modalita' partita: ");
			try{
				mod = Integer.parseInt(com.consoleRead());
			}catch(NumberFormatException nfe){
				com.println("Inserisci un valore valido!!");
			}
		}while(mod != 1 && mod != 2);

		return (mod == 1)? ModalitaPartita.SINGOLA : ModalitaPartita.MULTIPLA;
	}

	private Livelli livelloPartita(){
		int livello = 0;
		do{
			com.println("Livelli disponibili: ");
			com.println("1) Livello 1");
			com.println("2) Livello 2");
			com.println("3) Livello 3");
			com.print("Inserisci il livello: ");
			try{
				livello = Integer.parseInt(com.consoleRead());
			}catch(NumberFormatException nfe){
				com.println("Inserisci un numero valido!!");
			}
			
		}while(livello < 1 || livello > 3);

		switch(livello){
			case 1 ->{
				return Livelli.PRIMO;
			}
			case 2 ->{
				return Livelli.SECONDO;
			}
			case 3 ->{
				return Livelli.TERZO;
			}
			default ->{
				com.println("Valore immesso non valido");
				return livelloPartita();
			}
		}
	}

	public void visualizzaScelte(){
		com.clear();
		com.println("--- Conferma dei valori immessi ---");
		com.println("Numero giocatori: " + this.numeroGiocatori);
		com.println("Modalita' partita: " + this.modalitaPartita);
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			com.println("Livello partita: " + this.livelloPartita);
		}
	}

	private boolean conferma(){
		com.print("Vuoi confermare? (s/n): ");
		String t = com.consoleRead();
		if(t.toUpperCase().charAt(0) == 'S'){
			return true;
		}
		else if(t.toUpperCase().charAt(0) == 'N'){
			return false;
		}
		else{
			com.println("Valore immesso non valido");
			return conferma();
		}
	}

	public void modificaScelte(){
		int s = 0;
		com.println("1) Per modificare il numero dei giocatori");
		com.println("2) Per modificare la modalita' della partita");
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			com.println("3) Per modificare la modalita' della partita");
		}
		com.print("Inserisci la voce: ");
		try{
			s = Integer.parseInt(com.consoleRead());
		}catch(NumberFormatException nfe){
			com.println("Inserisci un valore valido!!");
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
					com.println("Valore immesso non valido!!");
					modificaScelte();	
				}
			}
			default ->{
				com.println("Valore immesso non valido!!");
				modificaScelte();
			}
		}
	}

}
