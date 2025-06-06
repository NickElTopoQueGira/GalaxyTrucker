package gioco;

import java.util.ArrayList;
import java.util.Comparator;

import partita.Pedina;
import tessera.Coordinate;
import tessera.Tessera;
import tessera.merce.Merce;
import tessera.merce.Stiva;

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
		for(int i=0; i<pedineVoloCompletato.size(); i++) {
			console.println(pedineVoloCompletato.get(i).getGiocatore().getNome());
			
			pedineVoloCompletato.get(i).getGiocatore().aggiornaCrediti(this.venditaMerci(pedineVoloCompletato.get(i), true));
			
			premio--;
		}
		
		//si vendono le merci di chi ha abbandonato (sempre se la nave non è distrutta)
		for(int i=0; i<pedineVoloAbbandonato.size(); i++) {
			console.println(pedineVoloAbbandonato.get(i).getGiocatore().getNome());
			
			pedineVoloAbbandonato.get(i).getGiocatore().aggiornaCrediti(this.venditaMerci(pedineVoloAbbandonato.get(i), false));
			
			premio--;
		}
		
		//calcolo della nave più bella
		
		
		
		for(int i=0; i<pedineVoloAbbandonato.size(); i++) {//vengono uniti i due array
			
			pedineVoloCompletato.add(pedineVoloAbbandonato.get(i));
		}
		
		//vengono pagate le tessere perse
		

		
		//riordinamento del nuovo array unito per il numero di crediti di ogni giocatore
		this.ordinaPerCreditiDecrescenti(pedineVoloCompletato);
		
		//podio finale
		console.println("- Classifica Finale -");
		for (int i = 0; i < pedineVoloCompletato.size(); i++) {

		    console.println((i + 1) + "° posto: " + pedineVoloCompletato.get(i).getGiocatore().getNome() + " con " + pedineVoloCompletato.get(i).getGiocatore().getCrediti() + " crediti");
		}
	}
	
	/**
	 * inizialmente trova tutte le tessere di tipo stiva e che abbia della merce al suo interno
	 * 
	 * in seguito calcola tutte le merci presenti e in caso qualcuno abbia abbandonato la nave
	 * il valore della merce vale /2 (per eccesso)
	 * 
	 * @param ped
	 * @param caso
	 * @return il valore totale delle merci presenti sulla nave del giuocatore
	 */
	private int venditaMerci(Pedina ped, boolean caso) {
		
		int guadagno = 0;
		
		ArrayList<Coordinate> crd = ped.getGiocatore().getNave().trova(0, 2);
		
		for(int x=0; x<ped.getGiocatore().getNave().getPlanciaDellaNave().size(); x++) {
			
			for(int y=0; y<ped.getGiocatore().getNave().getPlanciaDellaNave().get(x).size(); y++) {
				
				for(int k=0; k<crd.size(); k++) {
					
					if(x == crd.get(k).getX() && y == crd.get(k).getY()) {
						
						Tessera tess = ped.getGiocatore().getNave().getPlanciaDellaNave().get(x).get(y);
						
						ArrayList<Merce> merci = ((Stiva) tess).getStiva();
						
						for(int m=0; m<merci.size(); m++) {
							
							if(caso) {
								
								guadagno += merci.get(m).getTipoMerce().getValore();
							}else {
								
								guadagno += (merci.get(m).getTipoMerce().getValore()+1) / 2;
							}
							
						}
					}
				}
			}
		}
		return guadagno;
	}
	
	/**
	 * grazie al Comparator riordino l'array in base a quanti crediti abbiano
	 * 
	 * @param lista
	 */
	private void ordinaPerCreditiDecrescenti(ArrayList<Pedina> lista) {
		
	    lista.sort(Comparator.comparingInt(p -> ((Pedina) p).getGiocatore().getCrediti()).reversed());
	}
}
