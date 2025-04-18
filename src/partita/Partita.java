package partita;

import java.util.Scanner;

import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class Partita {
	private final ModalitaPartita modalitaPartita;
	private int numeroGiocatori;
	
	
	
	public Partita(ModalitaPartita modalita, int numeroGiocatori){
		modalitaPartita=modalita;
		this.numeroGiocatori=numeroGiocatori;
		selezionaModalita();
		
	}
	
	
	public void selezionaModalita() {
		
		switch (modalitaPartita) {
		case ModalitaPartita.SINGOLA: {
			IniziaPartitaSingola();	
		}
		case ModalitaPartita.MULTIPLA:{
			IniziaPartitaMultipla();
		}
		
		
		}
	}


	private void IniziaPartitaMultipla() {
		//TO DO
	}


	private void IniziaPartitaSingola() {
		Scanner scanner = new Scanner(System.in);
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

        Giocatore g = new Giocatore("Pippo", Colori.ROSSO);
        g.setLivello(livelloScelto);
        g.creaNave();
		
	}
}
