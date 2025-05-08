package partita;

import java.util.Scanner;

import eccezioniPersonalizzate.ErroreTessera;
import partita.configurazione.ConfiguraGiocatore;
import partita.configurazione.ConfiguraNave;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;
import partita.nave.Nave;

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

        

        ConfiguraGiocatore confG= new ConfiguraGiocatore();
        Giocatore g=confG.craGiocatore();
        ConfiguraNave conf=new ConfiguraNave(g);
        Nave nave= conf.creaNave(Livelli.TERZO);
        System.out.println(nave.getEquipaggio());
        System.out.println(nave.toString());
		// try {
		// 	g = new Giocatore("Pippo", Colori.ROSSO);
		// 	g.setLivello(livelloScelto);
	    //     g.creaNave();
		
		// } catch (ErroreGiocatore e) {
		// 	e.printStackTrace();
		// }
    }
}
