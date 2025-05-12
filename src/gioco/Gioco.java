package gioco;

import partita.ModalitaPartita;

import partita.Partita;

import partita.configurazione.ConfiguraPartita;
import tabellone.Tabellone;

public class Gioco {
	
	public Gioco() {
		ConfiguraPartita configurazione = new ConfiguraPartita();
		Partita partita = configurazione.creaPartita();
		partita.aggiungiGiocatori();
		
		int counter;
		if(partita.getModalitaPartita()==ModalitaPartita.SINGOLA) {
			counter=1;
		}else {
			counter=3;
			
			
		}
		
		for(int i=0; i<counter; i++) {

			//creazioni nave ecc..
			
			
			
			
			//fase 2
			Tabellone tabellone= new Tabellone(partita.getLivelloPartita());
			partita.setLivelloPartita(partita.getLivelloPartita().next());
		}
		
		
	}
	
	
	
	
	
}
