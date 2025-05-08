package carte;

import java.util.ArrayList;
import partita.giocatore.Giocatore;
import partita.nave.*;
import tessera.Tessera;

public class PolvereStellare extends Carta {
	
	
	public PolvereStellare (int lvl) {
		
		super(lvl, TipoCarta.POLVERE_STELLARE);
	}

	@Override
	public void eseguiCarta(ArrayList<Giocatore> elencoGiocatore) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<elencoGiocatore.size(); i++) {
			
			int connettoriScoperti = ContaConnettoriScoperti(elencoGiocatore.get(i).getNave().getPlanciaDellaNave());
			
			
			int posizionePedina = elencoGiocatore.get(i).getPedina().getPosizioneSulTabellone();
			elencoGiocatore.get(i).getPedina().setPosizioneSulTabellone(posizionePedina - connettoriScoperti);      
			
		}
	}
	
	private int ContaConnettoriScoperti(ArrayList<ArrayList<Tessera>> nave) {
		int n = 0;
		
		//for(int i=0; i<nave.ge)
		
		return n;
	}

}
