package partita;

import java.util.Scanner;

import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class PARTITATESTMAIN {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Livelli livelloScelto = Livelli.PRIMO;
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

        Giocatore g;
		try {
			g = new Giocatore("Pippo", Colori.ROSSO);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        g.setLivello(livelloScelto);
        g.creaNave();
        

    }
}
