package gioco;

import partita.Partita;

import partita.configurazione.ConfiguraPartita;

public class Gioco {
	
	public Gioco() {
		ConfiguraPartita configurazione = new ConfiguraPartita();
		Partita partita = configurazione.creaPartita();
		partita.aggiungiGiocatori();
		
	}
	
	
	
	
	
}
