package carte;

import java.util.ArrayList;
import partita.giocatore.Giocatore;

public class SpazioAperto extends Carta {

	public SpazioAperto (int lvl) {
		
		super(lvl, TipoCarta.SPAZIO_APERTO);
	}

	@Override
	public void eseguiCarta(ArrayList<Giocatore> elencoGiocatore) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<elencoGiocatore.size(); i++) {
			
			int potenzaMotore = elencoGiocatore.get(i).getNave().getPotenzaMotori(); //PRENTE LA POTENZA MOTORI
			
			int posizionePedina = elencoGiocatore.get(i).getPedina().getPosizioneSulTabellone(); //PRENTE LA POSIZIONE DELLE PEDINE
			
			elencoGiocatore.get(i).getPedina().setPosizioneSulTabellone(posizionePedina+potenzaMotore);  // SOMMA LA POSIZIONE ALLA POTENZA MOTORE E LA IMPOSTA COME NUOVA POSIZIONE
		}
	}

}
