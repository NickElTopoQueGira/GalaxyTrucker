package carte;

import java.util.ArrayList;

import partita.Pedina;
import partita.nave.*;
import tessera.Tessera;

public class PolvereStellare extends Carta {
	
	
	public PolvereStellare (int lvl) {
		
		super(lvl, TipoCarta.POLVERE_STELLARE);
	}

	
	
	private int ContaConnettoriScoperti(Nave nave) {
		int n = 0;
		
		for(int i=0; i<nave.getPlanciaDellaNave().size(); i++) {
			for(int j=0; j<nave.getPlanciaDellaNave().get(i).size(); j++) {
				
				n = n+nave.conteggioConettoriEsposti(nave.getPlanciaDellaNave().get(i).get(i), 
											nave.getPlanciaDellaNave().get(i).get(i).getCoordinate());               
					
			}
		}
		
		return n;
	}



	@Override
	public void eseguiCarta(ArrayList<Pedina> elencoPedine) {
		for(int i=0; i<elencoPedine.size(); i++) {
			
			int connettoriScoperti = ContaConnettoriScoperti(elencoPedine.get(i).getGiocatore().getNave());
			
			elencoPedine.get(i).muoviPedina(connettoriScoperti);     
			
		}
		
	}

}
