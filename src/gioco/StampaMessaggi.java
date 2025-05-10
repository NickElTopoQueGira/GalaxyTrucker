package gioco;

import carte.Carta;
import partita.ModalitaPartita;
import partita.configurazione.ConfiguraGiocatore;
import partita.configurazione.ConfiguraPartita;
import partita.giocatore.Colori;
import partita.nave.Nave;
import tessera.Tessera;

public class StampaMessaggi {
	
	private static ComunicazioneConUtente msg;
	private static StampaMessaggi istanza;
	
	
	private StampaMessaggi(){
        this.msg = ComunicazioneConUtente.getIstanza();
    }
	
	
	/** 
     * Metodo per 'gettare' l'istanza della classe. 
     * Viene fatto il controllo se e' gia' stata istanziata
     * se si -> viene ritornata l'istanza
     * se no -> viene creata una istanza e viene ritornata
     * 
     * Questo metodo permette di creare una sola istanza della classe
     * 
     * @return StampaMessaggi
     */
	public static StampaMessaggi getIstanza() {
        if(null == istanza){
            istanza = new StampaMessaggi();
        }

        return istanza;
    }
	
	
	//-------------------- GENERICI --------------------
	
	/**
	 * chiama metodo base di ComunicazioneConUtente clear
	 */
	public void clear() {
		msg.clear();
	}
	
	
	/**
	 * chiama metodo base di ComunicazioneConUtente conferma
	 */
	public boolean conferma() {
		return msg.conferma();
	}
	
	/**
	 * errore immissione valore non valido
	 */
	public void ErroreImmissioneValore() {
		msg.printError("Valore immesso non valido!!");
	}
	
	
	
	//-------------------- GIOCATORE --------------------
	
	/**
	 * stampa inserimento giocatore
	 * @return
	 */
	public String setNomeGiocatore(){
        String temp = "";
        msg.print("Inserisci il nome del giocatore (25 caratteri max): ");
        temp = msg.consoleRead();
        if(temp.length() <= 25){
            return temp;
        }
        else{
        	msg.println("Nome troppo lungo");
            return setNomeGiocatore();
        }
    }
	
	/**
	 * stampa riepilogo giocatore
	 * @param g
	 */
	public void nomeGiocatore(ConfiguraGiocatore g) {
		 msg.println("Nome scelto: "+g.getColorePedina().getCodiceColore()+g.getNome()+"\u001B[0m");
	}
	
	
	
	/**
	 * visualizza colori disponibili
	 */
	private void visualizzaColori(){
        msg.println("Colori disponibili: ");
        int i = 0;
        for(Colori c : Colori.values()){
        	msg.println(i+1 + ") " + c.getname());
            i += 1;
        }
    }
	
	
	
	/**
	 * stampa interfaccia utente per scelta colore giocatore
	 * @return c
	 */
	public  Colori colorePedina(){
        Colori c;
        this.visualizzaColori();
        msg.print("Inserisci il numero del colore: ");
        int t = Integer.parseInt(msg.consoleRead());
        
        try{
            c = Colori.coloreSelezionato(t); 
        }catch(IllegalArgumentException iax){
        	msg.println(iax.getMessage().toString());
            return colorePedina();
        }
        return c;
    }
	
	
	
	
	//-------------------- CONF PARTITA --------------------	
	
	
	/**
	 * stampa resoconto configurazione partita
	 */
	public void visualizzaScelte(ConfiguraPartita p){
		msg.clear();
		msg.println("--- Conferma dei valori immessi ---");
		msg.println("Numero giocatori: " + p.getNumeroGiocatori());
		msg.println("Modalita' partita: " + p.getModalitaPartita());
		if(p.getModalitaPartita() == ModalitaPartita.SINGOLA){
			msg.println("Livello partita: " + p.getLivelloPartita());
		}
	}
	
	
	
	/**
	 * stampa interfaccia per modifica conf. partita
	 * @param p
	 * @return 
	 */
	public int menuModificaScelte(ConfiguraPartita p){
		int s=0;
		msg.println("1) Per modificare il numero dei giocatori");
		msg.println("2) Per modificare la modalita' della partita");
		if(p.getModalitaPartita() == ModalitaPartita.SINGOLA){
			msg.println("3) Per modificare la modalita' della partita");
		}
		msg.print("Inserisci la voce: ");
		try{
			s = Integer.parseInt(msg.consoleRead());
		}catch(NumberFormatException nfe){
			this.ErroreImmissioneValore();
		}
		return s;
	}
	
	
	
	/**
	 * stampa interfaccia utente scelta livello partita
	 * @return livello
	 */
	public int livelloPartita() {
		int livello = 0;
		do{
			msg.println("Livelli disponibili: ");
			msg.println("1) Livello 1");
			msg.println("2) Livello 2");
			msg.println("3) Livello 3");
			msg.print("Inserisci il livello: ");
			try{
				livello = Integer.parseInt(msg.consoleRead());
			}catch(NumberFormatException nfe){
				this.ErroreImmissioneValore();
			}
			
		}while(livello < 1 || livello > 3);
		
		return livello;
	}
	
	
	
	/**
	 * stampa interfaccia utente per scelta modalità partita
	 * @return ModalitaPartita
	 */
	public ModalitaPartita modalitaPartita(){
		int mod = 0;
		do{
			msg.println("Modalita' partita disponibile: ");
			msg.println("1) Partita singola");
			msg.println("2) Partita multipla");
			msg.print("Inserisci la modalita' partita: ");
			try{
				mod = Integer.parseInt(msg.consoleRead());
			}catch(NumberFormatException nfe){
				this.ErroreImmissioneValore();
			}
		}while(mod != 1 && mod != 2);

		return (mod == 1)? ModalitaPartita.SINGOLA : ModalitaPartita.MULTIPLA;
	}
	
	
	
	/**
	 * interfaccia utente per inserimento num giocatori in conf partita
	 * @return
	 */
	public int numeroGiocatori(){
		int numeroGiocatori = 0;
		do{
			msg.print("Inserisci il numero dei giocatori (min 2, max 4): ");
			try{
				numeroGiocatori = Integer.parseInt(msg.consoleRead());
			}catch(NumberFormatException nfe){
				this.ErroreImmissioneValore();
			}
		}while(numeroGiocatori < 2 || numeroGiocatori > 4);

		return numeroGiocatori;
	}
	
	
	
	
	//-------------------- NAVE --------------------
	
	/**
	 * stampa nave
	 * @param c
	 */
    public void StampaNave(Nave n) {
		
		msg.println(n.toString());
		
	}
	
	
	
	
	
	//-------------------- CARTA --------------------
	
	/**
	 * stampa carta ed i suoi valori
	 * @param c
	 */
    public void StampaValoriCarta(Carta c) {
		
		msg.println(c.toString());
		
	}
    
    
   //-------------------- TESSERA --------------------
    
    /**
	 * stampa tessera
	 * @param c
	 */
    public void StampaValoriTessera(Tessera t) {
		
		msg.println(t.toString());
		
	}
    

}
