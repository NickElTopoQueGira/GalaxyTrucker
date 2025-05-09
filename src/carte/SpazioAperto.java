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
        	
			int potenzaMotore = elencoPedine.get(i).getGiocatore().getNave().getPotenzaMotori(); //PRENTE LA POTENZA MOTORI
			
			elencoPedine.get(i).muoviPedina(potenzaMotore);  // SOMMA LA POSIZIONE ALLA POTENZA MOTORE E LA IMPOSTA COME NUOVA POSIZIONE

		}
	}
}
