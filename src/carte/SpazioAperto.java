package carte;

import java.util.ArrayList;

import partita.Pedina;
import partita.giocatore.Giocatore;

public class SpazioAperto extends Carta {

	public SpazioAperto (int lvl) {
		
		super(lvl, TipoCarta.SPAZIO_APERTO);
	}

	@Override
	public void eseguiCarta(ArrayList<Pedina> elencoPedine) {
        for(int i=0; i<elencoPedine.size(); i++) {
			
			int potenzaMotore = elencoPedine.get(i).getNave().getPotenzaMotori(); //PRENTE LA POTENZA MOTORI
			
			int posizionePedina = elencoPedine.get(i).getPosizioneSulTabellone(); //PRENTE LA POSIZIONE DELLE PEDINE
			
			elencoPedine.get(i).setPosizioneSulTabellone(posizionePedina+potenzaMotore);  // SOMMA LA POSIZIONE ALLA POTENZA MOTORE E LA IMPOSTA COME NUOVA POSIZIONE
		}
		
	}

	

}
