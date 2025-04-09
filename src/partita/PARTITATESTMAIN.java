package partita;

import partita.giocatore.Colori;

import partita.giocatore.Giocatore;

import java.util.Scanner;

public class PARTITATESTMAIN {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Livelli livelloScelto = Livelli.PRIMO;
        System.out.println("Seleziona un livello:");
        for (int i = 0; i < Livelli.values().length; i++) {
            System.out.println(i + ". " + Livelli.values()[i]);
        }

        System.out.print("Inserisci il numero del livello: ");
        int scelta = scanner.nextInt();

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
