package carte;

import java.util.ArrayList;

import gioco.ComunicazioneConUtente;
import partita.Pedina;
import partita.nave.*;

public class PolvereStellare extends Carta {
	
	private ComunicazioneConUtente stampa;
	
	/**
	 * Costruttore PolvereStellare
	 * super -> gli passiamo il lvl della carta e il tipo
	 * @param lvl
	 */
	public PolvereStellare (int lvl) {
		
		super(lvl, TipoCarta.POLVERE_STELLARE);
	}

	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		for(int i=0; i<elencoPedine.size(); i++) {
			
			int connettoriScoperti = ContaConnettoriScoperti(elencoPedine.get(i).getGiocatore().getNave());
			
			elencoPedine.get(i).getTabellone().muoviPedina(elencoPedine.get(i), -connettoriScoperti);
			
			stampa.println("LA NAVE DI: "+ elencoPedine.get(i).getGiocatore().getNome() +" HA "+connettoriScoperti+" CONNETTORI SCOPERTI");
		}
		return elencoPedine;
	}
	
	private int ContaConnettoriScoperti(Nave nave) {
		int n = 0;
		
		for(int i=0; i<nave.getPlanciaDellaNave().size(); i++) {
			for(int j=0; j<nave.getPlanciaDellaNave().get(i).size(); j++) {
				
				n = n+nave.getNumeroConnettoriScoperti();               
					
			}
		}
		
		return n;
	}
}
