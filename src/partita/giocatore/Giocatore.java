package partita.giocatore;

import partita.nave.*;
import partita.Livelli;

public class Giocatore {
	private final String nome; 
	private final Colori colorePedina; 
	private int crediti; 
	private int posizioneSulTabellone; 
	private Nave nave;
	private Livelli livello;
	
	public Giocatore(String nome, Colori colorePedina) {
		this.nome = nome;
		this.colorePedina = colorePedina;
		this.crediti = 0; 
		this.posizioneSulTabellone = 0;
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
				System.out.println(nave.toString());
				
				break;
			}
			case SECONDO->{
				this.nave = new NaveLvl2(colorePedina);
				System.out.println(nave.toString());
				
				break;
			}
			case TERZO->{
				this.nave = new NaveLvl3(colorePedina);
				System.out.println(nave.toString());

				break;
			}
			default ->{
				System.err.println("Scelta non valida");
			}
		}
		
		//assemblaNave();
	}

	// private void assemblaNave(){
	// 	this.nave.
	// }
}
