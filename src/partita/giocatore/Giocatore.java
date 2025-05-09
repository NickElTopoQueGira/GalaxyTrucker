package partita.giocatore;

import partita.Pedina;
import partita.nave.Nave;

public class Giocatore {
	private String nome;
	private Pedina pedina;
	private int crediti;
	private Nave nave;

	public Giocatore(String nome, Pedina pedina){
		this.nome = nome; 
		this.pedina = pedina; 
		this.crediti = 0;
		 pedina.setGiocatore(this);
	}

	public String getNome(){ return this.nome; }

	public Pedina getPedina(){ return this.pedina; }

	public void aggiornaCrediti(int crediti){
		this.crediti += crediti;
	}

	public void setCrediti(int crediti){ this.crediti = crediti; }

	public int getCrediti(){ return this.crediti; }

	public void setNave(Nave nave){ this.nave = nave; }

	public Nave getNave(){ return this.nave; } 

}
