package carte;

import java.util.ArrayList;
import partita.giocatore.Giocatore;

public class SpazioAperto extends Carta {

	public SpazioAperto (int lvl) {
		
		super(lvl, TipoCarta.SPAZIO_APERTO);
	}

	@Override
	public void eseguiCarta(ArrayList<Giocatore> elencoGiocatore) {  //DA RIFARE
		
																		// DA FARE CONTROLLO 
		// TODO Auto-generated method stub
		
		for(int i=0; i<elencoGiocatore.size(); i++) {
			
			int potenzaMotore = elencoGiocatore.get(i).getNave().getPotenzaMotori(); //PRENTE LA POTENZA MOTORI
			
			elencoGiocatore.get(i).getPedina().muoviPedina(potenzaMotore);  // SOMMA LA POSIZIONE ALLA POTENZA MOTORE E LA IMPOSTA COME NUOVA POSIZIONE
		}
	}

}
