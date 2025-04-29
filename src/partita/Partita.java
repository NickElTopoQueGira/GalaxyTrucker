package partita;

import java.util.ArrayList;
import java.util.Scanner;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class Partita {
	private final ModalitaPartita modalitaPartita;
	private int numeroGiocatori;
	private static ArrayList<Giocatore> lista = new ArrayList<Giocatore>(); 
	
	
	public Partita(ModalitaPartita modalita, int numeroGiocatori){
		modalitaPartita=modalita;
		this.numeroGiocatori=numeroGiocatori;
		Scanner scanner = new Scanner(System.in);
		inserisciGiocatori(scanner);
		selezionaModalita(scanner);
		
	}
	
	
	private void inserisciGiocatori(Scanner scanner) {
		boolean condizione=false;
		int i=0;
		Colori colore= Colori.BLU;
		do {
			System.out.print("Inserisci il nome giocatore "+(i+1)+":");
	        String nome = scanner.next();
	        
	        if(nome=="valido") { //sistemare correzione
	        	Giocatore giocatore= new Giocatore(nome, colore);
	        	lista.add(giocatore);
	        	condizione=true;
	        	i++;
	        	colore.next();
	        }
			
		}while(i<this.numeroGiocatori || condizione==false);
		
	}


	public void selezionaModalita(Scanner scanner) {
		
		switch (modalitaPartita) {
		case ModalitaPartita.SINGOLA: {
			IniziaPartitaSingola(scanner);	
		}
		case ModalitaPartita.MULTIPLA:{
			IniziaPartitaMultipla(scanner);
		}
		
		
		}
	}


	private void IniziaPartitaMultipla(Scanner scanner) {
		for(int i=0; i<3; i++) {
			IniziaPartitaSingola(scanner);
		}
	}


	private void IniziaPartitaSingola(Scanner scanner) {		
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

        scanner.close();
        
        for(int i=0; i<this.numeroGiocatori; i++) {
        	lista.get(i).setLivello(livelloScelto);
        	lista.get(i).creaNave();
        }
        
		
	}
}
