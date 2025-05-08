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
			
			// 	prendi numero potenza motori
			// elencoGiocatore.get(i).getNave().get...
			int potenzaMotore = 0;
			int posizionePedina = elencoGiocatore.get(i).getPedina().getPosizioneSulTabellone();
			elencoGiocatore.get(i).getPedina().setPosizioneSulTabellone(posizionePedina+potenzaMotore);      
			
		}
	}

}
