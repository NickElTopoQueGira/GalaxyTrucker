package partita.Configurazione;

import partita.Input;
import partita.Livelli;
import partita.ModalitaPartita;
import partita.Partita;

public class ConfiguraPartita extends Input{
    private int numeroGiocatori;
    private ModalitaPartita modalitaPartita;
	private Livelli livelloPartita;

    public ConfiguraPartita(){
        super();
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
	}

	private int numeroGiocatori(){
		int numeroGiocatori = 0;
		do{
			System.out.print("Inserisci il numero dei giocatori (min 2, max 4): ");
			try{
				numeroGiocatori = Integer.parseInt(super.input.nextLine());
			}catch(NumberFormatException nfe){
				System.out.println("Inserisci un valore valido!!");
			}
		}while(numeroGiocatori < 2 || numeroGiocatori > 4);

		return numeroGiocatori;
	}

	private ModalitaPartita modalitaPartita(){
		int mod = 0;
		do{
			System.out.println("Modalita' partita disponibile: ");
			System.out.println("1) Partita singola");
			System.out.println("2) Partita multipla");
			System.out.print("Inserisci la modalita' partita: ");
			try{
				mod = Integer.parseInt(super.input.nextLine());
			}catch(NumberFormatException nfe){
				System.out.println("Inserisci un valore valido!!");
			}
		}while(mod != 1 && mod != 2);

		return (mod == 1)? ModalitaPartita.SINGOLA : ModalitaPartita.MULTIPLA;
	}

	private Livelli livelloPartita(){
		int livello = 0;
		do{
			System.out.println("Livelli disponibili: ");
			System.out.println("1) Livello 1");
			System.out.println("2) Livello 2");
			System.out.println("3) Livello 3");
			System.out.print("Inserisci il livello: ");
			try{
				livello = Integer.parseInt(super.input.nextLine());
			}catch(NumberFormatException nfe){
				System.out.println("Inserisci un numero valido!!");
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
				System.out.println("Valore immesso non valido");
				return livelloPartita();
			}
		}
	}

	public void visualizzaScelte(){
		System.out.println("--- Conferma dei valori immessi ---");
		System.out.println("Numero giocatori: " + this.numeroGiocatori);
		System.out.println("Modalita' partita: " + this.modalitaPartita);
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			System.out.println("Livello partita: " + this.livelloPartita);
		}
	}

	private boolean conferma(){
		System.out.print("Vuoi confermare? (s/n): ");
		String t = super.input.nextLine();
		if(t.toUpperCase().charAt(0) == 'S'){
			return true;
		}
		else if(t.toUpperCase().charAt(0) == 'N'){
			return false;
		}
		else{
			System.out.println("Valore immesso non valido");
			return conferma();
		}
	}

	public void modificaScelte(){
		int s = 0;
		System.out.println("1) Per modificare il numero dei giocatori");
		System.out.println("2) Per modificare la modalita' della partita");
		if(this.modalitaPartita == ModalitaPartita.SINGOLA){
			System.out.println("3) Per modificare la modalita' della partita");
		}
		System.out.print("Inserisci la voce: ");
		try{
			s = Integer.parseInt(super.input.nextLine());
		}catch(NumberFormatException nfe){
			System.out.println("Inserisci un valore valido!!");
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
					System.out.println("Valore immesso non valido!!");
					modificaScelte();	
				}
			}
			default ->{
				System.out.println("Valore immesso non valido!!");
				modificaScelte();
			}
		}
	}

}
