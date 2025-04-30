package partita;

import java.util.Scanner;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.*;


public class Partita {
	private final ModalitaPartita modalitaPartita;
	private int numeroGiocatori;
	
	
	public Partita(ModalitaPartita modalita, int numeroGiocatori, Scanner scanner){
		this.modalitaPartita=modalita;
		this.numeroGiocatori=numeroGiocatori;
		
		
		inserisciGiocatori(scanner);
		
		selezionaModalita(scanner);
		
	}
	
	
	private void inserisciGiocatori(Scanner scanner){
		
	
		int j=0;
		Colori coloreAttuale= Colori.ROSSO;
		boolean condizione=true;
		do {
			condizione=true;
			System.out.print("Inserisci il nome giocatore "+(j+1)+":");
			String nomeG= scanner.next();

			Giocatore giocatore = null;
			try {
				giocatore = new Giocatore(nomeG, coloreAttuale);
				
				
			} catch (ErroreTessera e) {
				condizione = false;
				e.printStackTrace();
			}
			if(condizione) {
				Giocatore.getGiocatori().add(giocatore);
				j++;
	        	coloreAttuale.next();
			}
				
			
	        
		}while(j<this.numeroGiocatori);
		
		

        
	}
	
	public Livelli selezionaLvL(Scanner scanner) {
		Livelli livelloScelto = null;
        System.out.println("Seleziona un livello:");
        int lvl=1;
        for (int i = 0; i < Livelli.values().length; i++) {
            System.out.println(lvl + ". " + Livelli.values()[i]);
            lvl++;
        }

        System.out.print("Inserisci il numero del livello: ");
        int scelta = scanner.nextInt()-1;

        if (scelta >= 0 && scelta < Livelli.values().length) {
            livelloScelto = Livelli.values()[scelta];
            System.out.println("Hai selezionato: " + livelloScelto);
        } else {
            System.out.println("Scelta non valida!");
        }
        
        return livelloScelto;
	}


	public void selezionaModalita(Scanner scanner) {
		switch (this.modalitaPartita) {
		case ModalitaPartita.SINGOLA: {
			Livelli livelloScelto = selezionaLvL(scanner);
			IniziaPartitaSingola(scanner, livelloScelto);	
			break;
		}
		case ModalitaPartita.MULTIPLA:{
			IniziaPartitaMultipla(scanner);
			break;
		}
		
		
		}
	}


	private void IniziaPartitaMultipla(Scanner scanner) {
		Livelli livelloAttuale= Livelli.PRIMO;
		for(int i=0; i<3; i++) {
			IniziaPartitaSingola(scanner,livelloAttuale);
			livelloAttuale.next();		}
	}

	
	
	private void IniziaPartitaSingola(Scanner scanner, Livelli livelloScelto) {		
        for(int i=0; i<this.numeroGiocatori; i++) {
        	
        	
        	
        }
        for(Giocatore g: Giocatore.getGiocatori()) {
        	g.setLivello(livelloScelto);
        	g.creaNave();
        }
        
		
	}
}
