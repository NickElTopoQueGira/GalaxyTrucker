package partita;

import java.util.Scanner;

public class ConfiguraPartita {
    private Scanner input;
    private int numeroGiocatori;
    private ModalitaPartita modalitaPartita;

    public ConfiguraPartita(){
        this.input = new Scanner(System.in);
        configuraPartita();
    }

    private void configuraPartita(){
		this.numeroGiocatori = numeroGiocatori();
		this.modalitaPartita = modalitaPartita();
		
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

	public void visualizzaScelte(){
		System.out.println("--- Conferma dei valori immessi ---");
		System.out.println("Numero giocatori: " + this.numeroGiocatori);
		System.out.println("Modalita partita: " + this.modalitaPartita);
	}

	public void modificaScelte(){
		int s = 0;
		System.out.println("1) Per modificare il numero dei giocatori");
		System.out.println("2) Per modificare la modalita' della partita");
		System.out.print("Inserisci la voce: ");
		try{
			s = Integer.parseInt(this.input.nextLine());
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
			default ->{
				System.out.println("Valore immesso non valido!!");
				modificaScelte();
			}
		}
	}

	private boolean conferma(){
		System.out.print("Vuoi confermare? (s/n): ");
		String t = this.input.nextLine();
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

	private int numeroGiocatori(){
		int numeroGiocatori = 0;
		do{
			System.out.print("Inserisci il numero dei giocatori (min 2, max 4): ");
			try{
				numeroGiocatori = Integer.parseInt(this.input.nextLine());
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
				mod = Integer.parseInt(this.input.nextLine());
			}catch(NumberFormatException nfe){
				System.out.println("Inserisci un valore valido!!");
			}
		}while(mod != 1 && mod != 2);

		return (mod == 1)? ModalitaPartita.SINGOLA : ModalitaPartita.MULTIPLA;
	}

    public Partita creaPartita(){
        return new Partita(this.numeroGiocatori, this.modalitaPartita);
    }
}
