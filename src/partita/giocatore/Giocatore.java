package partita.giocatore;

import partita.nave.*;

import java.util.LinkedHashSet;
import java.util.Set;

import eccezioniPersonalizzate.ErroreTessera;
import partita.Livelli;

public class Giocatore {
	private final String nome; 
	private final Colori colorePedina; 
	private int crediti; 
	private int posizioneSulTabellone; 
	private Nave nave;
	private Livelli livello;
	private static Set<Giocatore> giocatori = new LinkedHashSet<>(); //set ordinato
	
	
	public Giocatore(String nome, Colori colorePedina) throws ErroreTessera {
		boolean condizione=true;
		for(Giocatore g: Giocatore.getGiocatori()) {
        	if(g.nome.equals(nome)||nome.length()>20){
        		condizione=false;
        		break;
        	}
        }
		if (condizione) {
			this.nome = nome;
			this.colorePedina = colorePedina;
			this.crediti = 0; 
			this.posizioneSulTabellone = 0;
		} else {
			throw new ErroreTessera("Errore Inserimento nome giocatore"); // Eccezione Numero Massimo di elementi
		}
		
	}
	
	public void aggiornaCrediti(int crediti) {
		this.crediti += crediti;
	}
	
	public int getCrediti() { return this.crediti; }

	public void setLivello(Livelli livello) { this.livello = livello; }

	public void creaNave(){
		System.out.println("Schema nave giocatore: "+this.colorePedina.toString());
		switch(this.livello){
			case PRIMO->{
				this.nave = new NaveLvl1(colorePedina);
				nave.stampaNave();
				
				break;
			}
			case SECONDO->{
				this.nave = new NaveLvl2(colorePedina);
				nave.stampaNave();				
				break;
			}
			case TERZO->{
				this.nave = new NaveLvl3(colorePedina);
				nave.stampaNave();
				break;
			}
			default ->{
				System.err.println("Scelta non valida");
			}
		}
		
		//assemblaNave();
	}

	public static Set<Giocatore> getGiocatori() {
		return giocatori;
	}

	public static void setGiocatori(Set<Giocatore> giocatori) {
		Giocatore.giocatori = giocatori;
	}

	// private void assemblaNave(){
	// 	this.nave.
	// }
}
