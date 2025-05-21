package gioco;

import java.util.LinkedHashSet;
import java.util.Set;

import eccezioniPersonalizzate.ErroreGiocatore;
import partita.Partita;
import partita.configurazione.ConfiguraGiocatore;
import partita.configurazione.ConfiguraPartita;
import partita.giocatore.Giocatore;

public class Gioco{
	private ComunicazioneConUtente com;
	private Partita partita;
	private Set<Giocatore> elencoGiocatori;
		
	public Gioco(){
		com = ComunicazioneConUtente.getIstanza();

		// Creazione della partita
		ConfiguraPartita configuraPartita = new ConfiguraPartita();
		this.partita = configuraPartita.creaPartita();

		// Creazione dei giocatori
		this.elencoGiocatori = new LinkedHashSet<>();
		inserimentoGiocatore();		

		// Aggiunta dei giocatori alla partita
		this.partita.aggiungiGiocatori(this.elencoGiocatori);
	}

	// --------------------------- GESTIONE GIOCATORI ---------------------------
	private void inserimentoGiocatore(){
		for(int i = 0; i < this.partita.getNumeroGiocatori(); i += 1){
			this.elencoGiocatori.add(creaGiocatore());
		}
	}

	private void riepilogoGiocatori(){
		com.clear();
		com.print("--- Riepilogo Giocatori ---\n");
		for(Giocatore giocatoreElenco : this.elencoGiocatori){
			com.print("-) "+giocatoreElenco.getPedina().getColorePedina().getCodiceColore()+
					giocatoreElenco.getNome()+"\u001B[0m"+"\n");
		}
		com.print("premere invio per continuare...");
		com.consoleRead();
		com.clear();
	}

	/**
	 * Creazione del giocatore
	 * 
	 * @return nuovo gicatore
	 */
	private Giocatore creaGiocatore(){
		ConfiguraGiocatore conf = new ConfiguraGiocatore();
		Giocatore giocatore = conf.craGiocatore();
		try{
			isGiocatoreDuplicato(giocatore);
		}catch(ErroreGiocatore eg){
			com.printError(eg.getMessage());
			return creaGiocatore();
		}
		
		return giocatore;
	}

	/**
	 * Verifica se il giocatore e' duplicato
	 * 
	 * @param giocatore
	 * @return
	 */
	private void isGiocatoreDuplicato(Giocatore giocatore)throws ErroreGiocatore{
		for(Giocatore g : this.elencoGiocatori){
			if(g.equals(giocatore)){
				throw new ErroreGiocatore("Giocatore gia' esistente");
			}	
		}
	}

	// --------------------------- GIOCO ---------------------------
	public void gioca(){
		riepilogoGiocatori();
		this.partita.gioca();
	}
}