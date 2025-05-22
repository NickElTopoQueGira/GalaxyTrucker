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
	/**
	 * metodo che in base a quanti connettori scoperti ha la nave 
	 * farà tornare indietro di quei giorni di viaggio
	 */
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		for(int i=0; i<elencoPedine.size(); i++) {
			
			int connettoriScoperti = elencoPedine.get(i).getGiocatore().getNave().getNumeroConnettoriScoperti();
			
			elencoPedine.get(i).getTabellone().muoviPedina(elencoPedine.get(i), -connettoriScoperti);
			
			stampa.println("LA NAVE DI: "+ elencoPedine.get(i).getGiocatore().getNome() +" HA "+connettoriScoperti+" CONNETTORI SCOPERTI");
		}
		return elencoPedine;
	}
}
