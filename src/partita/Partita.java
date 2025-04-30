package partita;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import partita.giocatore.*;


public class Partita {
	private final ModalitaPartita modalitaPartita;
	private int numeroGiocatori;
	private static Set<Giocatore> giocatori = new LinkedHashSet<>(); //set ordinato
	
	
	public Partita(ModalitaPartita modalita, int numeroGiocatori, Scanner scanner){
		this.modalitaPartita=modalita;
		this.numeroGiocatori=numeroGiocatori;
		
		inserisciGiocatori(scanner);
		selezionaModalita(scanner);
		
	}
	
	
	private void inserisciGiocatori(Scanner scanner) {
		
	
		int j=0;
		Colori coloreAttuale= Colori.ROSSO;
		do {
			System.out.print("Inserisci il nome giocatore "+(j+1)+":");
			String nomeG= scanner.next();
	        
	        if(nomeG.length()<=20) { //20 caratteri massimo
	        	Giocatore giocatore= new Giocatore(nomeG, coloreAttuale);
	        	
	        	giocatori.add(giocatore);
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
        for(Giocatore g: giocatori) {
        	g.setLivello(livelloScelto);
        	g.creaNave();
        }
        
		
	}
}
