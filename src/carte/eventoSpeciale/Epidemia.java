package carte.eventoSpeciale;

import java.util.ArrayList;

import carte.*;
import partita.Pedina;

public class Epidemia extends EventiSpeciali {
	
	public Epidemia (int lvl) {
		
		super(lvl, TipoCarta.EPIDEMIA);
	}
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		// TODO Auto-generated method stub
		
		return elencoPedine;
	}
}
