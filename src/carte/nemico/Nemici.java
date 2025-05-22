package carte.nemico;

import java.util.ArrayList;
import carte.*;
import partita.Pedina;
import partita.giocatore.Giocatore;

public class Nemici extends Carta {
	
	/**
	 * Costruttore Nemici
	 * super -> gli passiamo il lvl della carta e il tipo
	 * @param lvl
	 * @param tipo carta
	 */
	public Nemici (int lvl, TipoCarta c) {
		
		super(lvl, c);
	}

	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		// TODO Auto-generated method stub
		
		return elencoPedine;
	}

	


	
}
