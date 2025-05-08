package partita;

import java.util.Set;
import java.util.LinkedHashSet;

import eccezioniPersonalizzate.ErroreGiocatore;
import gioco.ComunicazioneConUtente;
import partita.configurazione.ConfiguraGiocatore;
import partita.giocatore.Giocatore;

public class Partita {
	private final int numeroGiocatori;
	private final ModalitaPartita modalitaPartita;
	private final Set<Giocatore> giocatori;
	private Livelli livelloPartita;

	public Partita(int numeroGiocatori, ModalitaPartita modalitaPartita){
		this.giocatori = new LinkedHashSet<Giocatore>();
		this.modalitaPartita = modalitaPartita;
		this.numeroGiocatori = numeroGiocatori;
	}

	public Partita(int numeroGiocatori, Livelli livelloPartita){
		this.giocatori = new LinkedHashSet<Giocatore>();
		this.modalitaPartita = ModalitaPartita.SINGOLA;
		this.numeroGiocatori = numeroGiocatori;
		this.livelloPartita = livelloPartita;
	}
	
	public void aggiungiGiocatori(){
		ConfiguraGiocatore configuraGiocatore = new ConfiguraGiocatore();
		for(int i = 0; i < this.numeroGiocatori; i += 1){
			Giocatore nuovoGiocatore = creaGiocatore(configuraGiocatore);
			this.giocatori.add(nuovoGiocatore);
		}
		
		
		//stampa riepilogo giocatori
		ComunicazioneConUtente com = ComunicazioneConUtente.getIstanza();
		com.clear();
		com.print("--- Riepilogo Giocatori ---\n");
		for(Giocatore giocatoreElenco : giocatori){
			com.print("-) "+giocatoreElenco.getPedina().getColorePedina().getCodiceColore()+
					giocatoreElenco.getNome()+"\u001B[0m"+"\n");
		}
		com.print("premere invio per continuare...");
		com.consoleRead();
		com.clear();
	}

	public Giocatore creaGiocatore(ConfiguraGiocatore configuraGiocatore){
		Giocatore nuovoGiocatore = null;

		try{
			nuovoGiocatore = configuraGiocatore.craGiocatore();
			verificaDuplicati(nuovoGiocatore); 
		}catch(ErroreGiocatore eg){
			System.out.println(eg.getMessage().toString());
			return creaGiocatore(configuraGiocatore);
		}

		return nuovoGiocatore;
	}

	private void verificaDuplicati(Giocatore nuovoGiocatore) throws ErroreGiocatore{
		// verifica se il giocatore non e' gia' presente
		for(Giocatore giocatoreElenco : giocatori){
			if(giocatoreElenco.equals(nuovoGiocatore)){
				throw new ErroreGiocatore("Giocatore gia' esistente");
			}
		}
	}

	// livelli

	public Livelli getLivelloPartita(){ return this.livelloPartita; }

	public void setLivelloPartita(Livelli livelloPartita) { this.livelloPartita = livelloPartita;}

	@Override
	public String toString(){
		return "Numero giocatori: " + this.numeroGiocatori + "\n" +
				"Modalita' partita: " + this.modalitaPartita;
	}
}
