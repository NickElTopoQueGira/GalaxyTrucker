package gioco;

import partita.Partita;
import partita.configurazione.ConfiguraPartita;

public class Gioco {
	
	public Gioco() {
		ConfiguraPartita conf = new ConfiguraPartita();
		Partita p = conf.creaPartita();
		p.aggiungiGiocatori();
		
	}
	
	
	
	
	
}
