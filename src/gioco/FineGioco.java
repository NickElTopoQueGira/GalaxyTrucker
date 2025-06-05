package gioco;

import java.util.ArrayList;

import partita.Pedina;

public class FineGioco {
	
	private ComunicazioneConUtente console;
	private ArrayList<Pedina> pedineVoloCompletato;
	private ArrayList<Pedina> pedineVoloAbbandonato;
	private int livello;
	
	public FineGioco(ArrayList<Pedina> pvc, ArrayList<Pedina> pva, int lvl) {
		
		this.console = ComunicazioneConUtente.getIstanza();
		this.pedineVoloCompletato = pvc;
		this.pedineVoloAbbandonato = pva;
		this.livello = lvl;
	}
	
	public void granFinale() {
		
		
		//faccio vedere chi è riuscito a completato la corsa
		int premio = 4;
		for(int i=0; i<pedineVoloCompletato.size(); i++) {
			console.println(pedineVoloCompletato.get(i).getGiocatore().getNome());
			
			pedineVoloCompletato.get(i).getGiocatore().aggiornaCrediti(premio*this.livello);
			
			premio--;
		}
		
		//faccio vedere chi ha abbandonato la corsa
		for(int i=0; i<pedineVoloAbbandonato.size(); i++) {
			console.println(pedineVoloAbbandonato.get(i).getGiocatore().getNome());
		}	
		
		//si vendono le merci di chi è arrivato
		
		
		//si vendono le merci di chi ha abbandonato (sempre se la nave non è distrutta)
		
		
		//primo confronto con i crediti
		
		
		//calcolo della nave più bella
		
		
		//vengono pagate le tessere perse
		
		
		//podio finale
	}
}
