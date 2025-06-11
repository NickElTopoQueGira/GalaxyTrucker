package partita.giocatore;

import eccezioniPersonalizzate.ErroreRisorse;
import java.util.Objects;
import partita.Livelli;
import partita.Pedina;
import partita.configurazione.ConfiguraNave;
import partita.nave.Nave;

public class Giocatore {
	private final String nome;
	private Pedina pedina = null;
	private int crediti;
	private Nave nave;
	private final Colori colorePedina;
	private boolean isNaveFinita;

	/**
	 * Costruttore di Giocatore
	 * 
	 * @param nome
	 * @param colorePedina
	 */
	public Giocatore(String nome, Colori colorePedina){
		this.nome = nome;  
		this.crediti = 0;
		this.nave = null;
		this.isNaveFinita = false;
		this.colorePedina = colorePedina;

		if(this.pedina == null){
			this.pedina = new Pedina(this);
		}
	}
	
	/**
	 * Metodo per acquisire il nome del giocatore colorato
	 * 
	 * @return nome del giocatore
	 * */
	public String getNome(){ return this.colorePedina.getCodiceColore()+this.nome+"\u001B[0m"; }

	//------------ CREDITI ------------
	/**
	 * Metodo per aggiornare i crediti del giocatore
	 * +x -> per aggiungere
	 * -x -> per togliere
	 * 
	 * @param crediti int
	 */
	public void aggiornaCrediti(int crediti){
		this.crediti += crediti;
	}
	
	/**
	 * Metodo getter per prendere il numero dei crediti del giocatori
	 * @return numero crediti
	 */
	public int getCrediti(){ return this.crediti; }

	//------------ PEDINA ------------
	/**
	 * Metodo per acquisire la pedina del giocatore
	 *
	 * @return Pedina del giocatore
	 * */
	public Pedina getPedina(){ return this.pedina; }
	
	/**
	 * Metodo getter per prendere il colore della pedina del giocatore
	 * @return colore pedina 
	 */
	public Colori getColorePedina(){ return this.colorePedina; }

	//------------ GESTIONE DELLA NAVE ------------
	/**
	 * Metodo per la creazione della nave
	 *
	 * @param livello Livelli
	 * @throws ErroreRisorse nave gia' esistente
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

	/**
	 * Metodo per azzerare la nave "=^.^="
	 */
	public void azzeraNave(){
		this.nave = null;
		this.isNaveFinita = false;
	}
	
	/**
	 * Metodo setter per impostare la nave del giocatore
	 * @param nave
	 */
	public void setNave(Nave nave){ this.nave = nave; }
	
	/**
	 * Metodo getter per prendere la nave del giocatore
	 * @return nave
	 */
	public Nave getNave(){ return this.nave; }
	
	/**
	 * Metodo da utilizzare per impostare e indicare che la
	 * nave è stata completata
	 */
	public void naveFinita(){ this.isNaveFinita = true; }
	
	/**
	 * Metodo getter per vedere se la nave è completata
	 * @return se la nave è finita
	 */
	public boolean isNaveFinita(){ return this.isNaveFinita; }

	//------------ HASHCODE - EQUALS ------------

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(nome, other.nome);
	}
}
