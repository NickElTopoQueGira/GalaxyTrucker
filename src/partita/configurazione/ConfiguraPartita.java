package partita.configurazione;

import gioco.ComunicazioneConUtente;


import partita.Livelli;
import partita.ModalitaPartita;
import partita.Partita;

public class ConfiguraPartita{
	private ComunicazioneConUtente stampa;	
    private int numeroGiocatori;
    private ModalitaPartita modalitaPartita;
	private Livelli livelloPartita;

    public ConfiguraPartita(){
    	stampa= ComunicazioneConUtente.getIstanza();
		this.setLivelloPartita(Livelli.PRIMO);
    }

	public Partita creaPartita(){
		configuraPartita();
		if(this.getModalitaPartita() == ModalitaPartita.SINGOLA){
			return new Partita(this.getNumeroGiocatori(), this.getLivelloPartita());	
		}	
		return new Partita(this.getNumeroGiocatori(), this.getModalitaPartita());
    }

    private void configuraPartita(){
		this.numeroGiocatori=stampa.numeroGiocatori();
		this.modalitaPartita=stampa.modalitaPartita();

		if(this.getModalitaPartita() == ModalitaPartita.SINGOLA){
			this.setLivelloPartita(this.livelloPartita());
		}
		
		this.ConfermaModificheSceltePartita();
		
		
	}
    
    /**
     * modifica scelte in conf. partita
     */
    private void modificaScelte(){
		
		int s=stampa.menuModificaScelte(this);
		
		switch(s){
			case 1 ->{
				this.numeroGiocatori=stampa.numeroGiocatori();
			}
			case 2 ->{
				this.modalitaPartita=stampa.modalitaPartita();
			}
			case 3 -> {
				if(stampa.modalitaPartita() == ModalitaPartita.SINGOLA){
					this.livelloPartita=livelloPartita();
				}
				else{
					stampa.ErroreImmissioneValore();
					this.modificaScelte();	
				}
			}
			default ->{
				stampa.ErroreImmissioneValore();
				this.modificaScelte();
			}
			
			
		}
	}
    
    /**
	 * conferma e modifica scelte Partita
	 * 
	 */
    private void ConfermaModificheSceltePartita() {
				boolean conferma = true;
				do{
					stampa.visualizzaScelte(this);
					conferma = stampa.conferma();

					if(!conferma){
						this.modificaScelte();
						stampa.visualizzaScelte(this);
						conferma = stampa.conferma();
					}

				}while(!conferma);
				stampa.clear();
	}
	
	
	
	/**
	 * funzione per scelta livello
	 * @return Livelli
	 */
	private Livelli livelloPartita(){
		int livello=stampa.livelloPartita();
		switch(livello){
			case 1 ->{
				return Livelli.PRIMO;
			}
			case 2 ->{
				return Livelli.SECONDO;
			}
			case 3 ->{
				return Livelli.TERZO;
			}
			default ->{
				stampa.ErroreImmissioneValore();
				return livelloPartita();
			}
		}
	}

	

	public ModalitaPartita getModalitaPartita() {
		return modalitaPartita;
	}

	public void setModalitaPartita(ModalitaPartita modalitaPartita) {
		this.modalitaPartita = modalitaPartita;
	}

	public int getNumeroGiocatori() {
		return numeroGiocatori;
	}

	public void setNumeroGiocatori(int numeroGiocatori) {
		this.numeroGiocatori = numeroGiocatori;
	}

	public Livelli getLivelloPartita() {
		return livelloPartita;
	}

	public void setLivelloPartita(Livelli livelloPartita) {
		this.livelloPartita = livelloPartita;
	}

	

	

	

	

	

}
