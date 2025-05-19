package partita.giocatore;

import eccezioniPersonalizzate.ErroreRisorse;
import partita.Livelli;
import partita.Pedina;
import partita.configurazione.ConfiguraNave;
import partita.nave.Nave;

public class Giocatore {
	private String nome;
	private Pedina pedina = null;
	private int crediti;
	private Nave nave;
	private Colori colorePedina;

	public Giocatore(String nome, Colori colorePedina){
		this.nome = nome;  
		this.crediti = 0;
		this.nave = null;

		if(this.pedina == null){
			this.pedina = new Pedina(this);
		}
	}

	public String getNome(){ return this.nome; }

	public Pedina getPedina(){ return this.pedina; }

	/**
	 * Metodo per aggiornare i crediti del giocatore
	 * +x -> per aggiungere
	 * -x -> per togliere
	 * 
	 * @param crediti
	 */
	public void aggiornaCrediti(int crediti){
		this.crediti += crediti;
	}

	public void setCrediti(int crediti){ this.crediti = crediti; }

	public int getCrediti(){ return this.crediti; }

	public void setNave(Nave nave){ this.nave = nave; }

	/**
	 * Metodo per la creazione della nave
	 * 
	 * @param livello
	 * @throws ErroreRisorse
	 */
	public void creaNave(Livelli livello) throws ErroreRisorse{
		if(this.nave == null){
			ConfiguraNave confNave = new ConfiguraNave(this);
			this.nave = confNave.creaNave(livello);
		}
		else{
			throw new ErroreRisorse("Nave gia' esistente");
		}
	}

	public Nave getNave(){ return this.nave; } 

	/**
	 * Metodo per azzerare la nave.
	 * Utilizzato quando si termina un livello e si passa a quello successivo
	 */
	public void eliminaNave(){ this.nave = null; }

	public Colori getColorePedina(){ return this.colorePedina; }
}
