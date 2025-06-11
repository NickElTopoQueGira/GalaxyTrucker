package gioco;

import java.util.ArrayList;
import java.util.Comparator;
import partita.Pedina;
import partita.nave.Nave;
import tessera.Coordinate;
import tessera.Tessera;
import tessera.merce.Merce;
import tessera.merce.Stiva;

public class FineGioco {
	
	private final ComunicazioneConUtente console;
	private ArrayList<Pedina> pedineVoloCompletato;
	private ArrayList<Pedina> pedineVoloAbbandonato;
	private int livello;
	
	/**
     * Costruttore FineGioco
     */
	public FineGioco(int lvl) {
		
		this.console = ComunicazioneConUtente.getIstanza();
		this.pedineVoloCompletato = null;
		this.pedineVoloAbbandonato = null;
		this.livello = lvl;
	}
	
	/**
     * Costruttore FineGioco
     */ 
	public FineGioco(ArrayList<Pedina> pvc, ArrayList<Pedina> pva, int lvl) {
		
		this.console = ComunicazioneConUtente.getIstanza();
		this.pedineVoloCompletato = pvc;
		this.pedineVoloAbbandonato = pva;
		this.livello = lvl;
	}
	
	/**
	 * Metodo che mostra tutte le caratteristiche e i valori di come è finito il volo
	 */
	public void granFinale() {
		
		console.clear();
		//faccio vedere chi è riuscito a completato la corsa
		int premio = 4;
		
		if(pedineVoloCompletato.size()>0) {

			console.println("\nELENCO GIOCATORI CHE SONO RIUSCITI A COMPLETARE LA CORSA:");
		}else {
			
			console.println("\nNESSUN GIOCATORE E' RIUSCITO A COMPLETARE LA CORSA:");
		}
		for(int i=0; i<pedineVoloCompletato.size(); i++) {
			console.println((i+1)+") "+pedineVoloCompletato.get(i).getGiocatore().getNome()+" e guadagna "+premio*this.livello+"\u00A2 (crediti) per la posizione");
			
			pedineVoloCompletato.get(i).getGiocatore().aggiornaCrediti(premio*this.livello);
			
			premio--;
		}
		
		//faccio vedere chi ha abbandonato la corsa
		if(pedineVoloAbbandonato.size()>0) {

			console.println("\nELENCO GIOCATORI CHE HANNO DOVUTO ABBANDONARE LA CORSA:");
		}else {
			
			console.println("\nNESSUN GIOCATORE HA DOVUTO ABBANDONARE LA CORSA:");
		}
		for(int i=0; i<pedineVoloAbbandonato.size(); i++) {
			console.println((i+1)+") "+pedineVoloAbbandonato.get(i).getGiocatore().getNome());
		}	
		
		//si vendono le merci di chi è arrivato
		if(pedineVoloCompletato.size()>0) {

			console.println("\nORA I GIOCATORI CHE HANNO COMPLETATO LA CORSA VENDERANNO LA LORO MERCE:");
		}else {
			
			console.println("\nNON CI SONO GIOCATORI CHE HANNO COMPLETATO LA CORSA PER VENDERE LA MERCE:");
		}
		for(int i=0; i<pedineVoloCompletato.size(); i++) {
			
			int valore = this.venditaMerci(pedineVoloCompletato.get(i), true);
			
			console.println(pedineVoloCompletato.get(i).getGiocatore().getNome()+" il valore totale della merce presente sulla nave è pari a "+valore+"\u00A2 (crediti)");
			
			pedineVoloCompletato.get(i).getGiocatore().aggiornaCrediti(valore);
			
			premio--;
		}
		
		//si vendono le merci di chi ha abbandonato (sempre se la nave non è distrutta)
		if(pedineVoloAbbandonato.size()>0) {

			console.println("\nORA I GIOCATORI CHE HANNO ABBANDONATO LA CORSA VENDERANNO LA LORO MERCE (ogni merce varrà la metà):");
		}else {
			
			console.println("\nNON CI SONO GIOCATORI CHE HANNO ABBANDONATO LA CORSA PER VENDERE LA MERCE:");
		}
		
		for(int i=0; i<pedineVoloAbbandonato.size(); i++) {

			int valore = this.venditaMerci(pedineVoloAbbandonato.get(i), false);
			
			console.println(pedineVoloAbbandonato.get(i).getGiocatore().getNome()+" il valore totale della merce presente sulla nave è pari a "+valore+"\u00A2 (crediti)");
			
			pedineVoloAbbandonato.get(i).getGiocatore().aggiornaCrediti(valore);
			
			premio--;
		}
		
		//calcolo della nave più bella 
		this.sceltaDellaNavePiuBella();
		
		for(int i=0; i<pedineVoloAbbandonato.size(); i++) {//vengono uniti i due array
			
			pedineVoloCompletato.add(pedineVoloAbbandonato.get(i));
		}
		
		//vengono pagate le tessere perse
		console.println("\nORA I GIOCATORI DOVRANNO PAGARE PER I COMPONENTI PERSI:");
		
		for(int i=0; i<pedineVoloAbbandonato.size(); i++) {

			int reso = this.pedineVoloCompletato.get(i).getGiocatore().getNave().getNumeroPezziNaveDaRipagare();
			
			console.println(pedineVoloCompletato.get(i).getGiocatore().getNome()+" il valore totale delle tessere da ripagare equivale a "+reso+"\u00A2 (crediti)");
			
			pedineVoloCompletato.get(i).getGiocatore().aggiornaCrediti(-reso);
			
			premio--;
		}
		
		//riordinamento del nuovo array unito per il numero di crediti di ogni giocatore
		this.ordinaPerCreditiDecrescenti(pedineVoloCompletato); 
		
		//podio finale
		console.println("\n- CLASSIFICA FINALE -");
		for (int i = 0; i < pedineVoloCompletato.size(); i++) {

		    console.println((i + 1) + "° posto: " + pedineVoloCompletato.get(i).getGiocatore().getNome() + " con " + pedineVoloCompletato.get(i).getGiocatore().getCrediti() + "\u00A2 (crediti)");
		}
		
	}
	
	/**
	 * Metodo che inizialmente trova tutte le tessere di tipo stiva e che abbia della merce al suo interno
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
	
	/**
	 * Metodo per decidere in base chi ha meno connettori esposti chi ha la nave più bella
	 * 
	 * @return indice giocatori con la nave più bella
	 */
	private void sceltaDellaNavePiuBella() {
		
		int minorScoperti = 0;
		ArrayList<Integer> minori = new ArrayList<>();
		minori.addFirst(0);
		
		for(int i=1; i<pedineVoloCompletato.size(); i++) {
			
			Nave n1 = pedineVoloCompletato.get(minorScoperti).getGiocatore().getNave();
			n1.connettoriScoperti();
			Nave n2 = pedineVoloCompletato.get(i).getGiocatore().getNave();
			n2.connettoriScoperti();
			
			if(n2.getNumeroConnettoriScoperti() < n1.getNumeroConnettoriScoperti()) {
				minori.clear();
				minori.add(i);
				
			}else if(n2.getNumeroConnettoriScoperti() == n1.getNumeroConnettoriScoperti()) {
				minori.add(i);
			}
		}
		if(pedineVoloCompletato.size() > 0) {
			
			if(minori.size() > 1) {
				
				console.println("\nI GIOCATORI CON LA NAVE PIU' BELLA SONO:");
			} else {
				
				console.println("\nIL GIOCATORE CON LA NAVE PIU' BELLA E':");
			}
			for(int i=0; i<minori.size(); i++) {
				console.println(pedineVoloCompletato.get(i).getGiocatore().getNome()+" ha vinto il premio di "+2*this.livello+"\u00A2 (crediti)");
				
				pedineVoloCompletato.get(i).getGiocatore().aggiornaCrediti(2*this.livello);
			}
		}else {
			
			console.println("\nNON CI SONO NAVI DA GIUDICARE (nessuno ha completato il volo!)");
		}
		
	}
}
