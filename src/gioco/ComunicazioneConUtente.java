/**
 * Questa classe e' utilizzata per gestire la comunicazione con l'utente 

 * sull'interfaccia console. 
 * 
 * In questa classe serve per istanziare solo una volta lo scanner per la lettura 
 * dei valori sulla console. 
 * 
 * Per realizzare questa classe mi sono ispirato al design pattern Singleton. 
 * -> https://en.wikipedia.org/wiki/Singleton_pattern
 * 
 * Utilizzando questo pattern, per creare questa classe, si assicura che ci sia 
 * solo un'istanza della stessa. Questo mi permette di evitare la creazione di 
 * diversi Scanner, sparsi per il programma, per la lettura dei valori 
 * imessi dall'utente su console. 
 * 
 * Un altro vantaggio e' che (in tutto il codide) non ho chiamate dirette 
 * alla console per stampare i messagi.
 */

package gioco;

import java.util.ArrayList;
import java.util.Scanner;

import partita.ModalitaPartita;
import partita.configurazione.ConfiguraPartita;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class ComunicazioneConUtente {
    private Scanner input;
    private static ComunicazioneConUtente istanza = null;

    private ComunicazioneConUtente(){
        this.input = new Scanner(System.in);
    }
    
    /** 
     * Metodo per 'gettare' l'istanza della classe. 
     * Viene fatto il controllo se e' gia' stata istanziata
     * se si -> viene ritornata l'istanza
     * se no -> viene creata una istanza e viene ritornata
     * 
     * Questo metodo permette di creare una sola istanza della classe
     * 
     * @return ComunicazioneConUtente
     */
    public static ComunicazioneConUtente getIstanza() {
        if(null == istanza){
            istanza = new ComunicazioneConUtente();
        }

        return istanza;
    }

    /**
     * Funzione per stampare sulla console un messaggio per l'utente
     * Questa funzione stampa senza l'acapo in automatico
     *  
     * @param msg -> messaggio che si vuole stampare sulla console
     */
    public void print(String msg){
        System.out.print(msg);
    }

    /**
     * Funzione per stampare sulla console un messaggio per l'utente
     * Questa funzione stampa l'acapo in automatico
     *  
     * @param msg -> messaggio che si vuole stampare sulla console
     */
    public void println(String msg){
        System.out.println(msg);
    }
        
    /**
     * Funzione per stampare sulla console valori interi
     * @param numero
     */
  	public void printNumber(int t) {
  		System.out.println(t);
  	}

    /**
     * Funzione per stampare sulla console un errore per l'utente
     * Richiama la: System.err.println()
     *  
     * @param error -> errore che si vuole stampare sulla console
     */
    public void printError(String error){
        System.err.println(error);
    }

    /**
     * Funzione per leggere l'input dell'utente sulla console. 
     * Questa funzione ritorna una stringa, sta al metodo che la chiama 
     * fare la conversione sul dato che viene immesso. 
     * 
     * Es. (si vuole leggere un numero)
     * Integer.parseInt(<ComunicazioneConUtente>.consoleRead())
     *  
     * @return input.nextLine() -> stringa letta dalla console
     */
    public String consoleRead(){
        return input.nextLine();
    }

    /**
     * Funzione per pulire la console stampando 200 righe vuote
     */
    public void clear() {
    	for(int i=0; i<200; i++) {
    		System.out.print("\n");
    	}
    	
    }
    
public void inizioGame() {
	this.println("\033[1;95m"+"GALAXY TRUCKER\n"+"\u001B[0m");
	this.print("Premi invio per iniziare...");
	this.consoleRead();
	this.clear();
}
    
    
    /**
	 * stampa nome giocatore colorato
	 * @param g
	 */
	public void nomeGiocatore(Giocatore g) {
		 this.println(g.getPedina().getColorePedina().getCodiceColore()+ g.getNome()+"\u001B[0m");
	}
    
    /**
	 * errore immissione valore non valido
	 */
	public void ErroreImmissioneValore() {
		this.printError("Valore immesso non valido!!");
	}

    /**
     * Semplice menu' di conferma (S/N)
     * 
     * @return true -> SI
     *         false -> NO
     */
    public boolean conferma(){
		this.print("Vuoi confermare? (s/n): ");
		String t = this.consoleRead();
		if(t.toUpperCase().charAt(0) == 'S'){
			return true;
		}
		else if(t.toUpperCase().charAt(0) == 'N'){
			return false;
		}
		else{
			this.println("Valore immesso non valido");
			return conferma();
		}
	}
    
    /**
     * come conferma ma in aggiunta di un commento specifico scelto in base al caso
     * 
     * @return true -> SI
     *         false -> NO
     */
    public boolean confermaSpecifica(String stringa) {
    	
		if(this.conferma()) {
			
			this.println(stringa+" utilizzato");
			
			return true;
		}else {
			
			this.println(stringa+" non utilizzzato");
			
			return false;
		}
    }
    
    /**
	 * stampa inserimento giocatore
	 * @return
	 */
	public String setNomeGiocatore(){
        String temp = "";
        this.print("Inserisci il nome del giocatore (25 caratteri max): ");
        temp = this.consoleRead();
        if(temp.length() <= 25){
            return temp;
        }
        else{
        	this.println("Nome troppo lungo");
            return setNomeGiocatore();
        }
    }
	
	
	/**
	 * stampa interfaccia utente per scelta colore giocatore
	 * @return c
	 */
	public  Colori colorePedina(){
        Colori c;
        ArrayList<String> lista=new ArrayList<String>();
        lista.add(Colori.ROSSO.getname());
        lista.add(Colori.GIALLO.getname());
        lista.add(Colori.VERDE.getname());
        lista.add(Colori.BLU.getname());
        this.print(this.visualizzaElenco(lista));
        this.print("Inserisci il numero del colore: ");
        int t = Integer.parseInt(this.consoleRead());
        
        try{
            c = Colori.coloreSelezionato(t); 
        }catch(IllegalArgumentException iax){
        	this.println(iax.getMessage().toString());
            return colorePedina();
        }
        return c;
    }
	
	
	
	/**
	 * stampa interfaccia per modifica conf. partita
	 * @param p
	 * @return 
	 */
	public int menuModificaScelte(ConfiguraPartita p){
		int s=0;
		ArrayList<String> lista=new ArrayList<String>();
        lista.add("Per modificare il numero dei giocatori");
        lista.add("Per modificare la modalita' della partita");
		if(p.getModalitaPartita() == ModalitaPartita.SINGOLA){
			lista.add("Per modificare la modalita' della partita");
		}
		this.print(this.visualizzaElenco(lista));
		
		this.print("Inserisci la voce: ");
		try{
			s = Integer.parseInt(this.consoleRead());
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
		ArrayList<String> lista=new ArrayList<String>();
        lista.add("Primo Livello");
        lista.add("Secondo Livello");
        lista.add("Terzo Livello");
		do{
			this.println("Livelli disponibili: ");
			
			this.print(this.visualizzaElenco(lista));
			this.print("Inserisci il livello: ");
			try{
				livello = Integer.parseInt(this.consoleRead());
			}catch(NumberFormatException nfe){
				this.ErroreImmissioneValore();
			}
			
		}while(livello < 1 || livello > 3);
		
		return livello;
	}
	
	
	/**
	 * interfaccia utente per inserimento num giocatori in conf partita
	 * @return
	 */
	public int numeroGiocatori(){
		int numeroGiocatori = 0;
		do{
			this.print("Inserisci il numero dei giocatori (min 2, max 4): ");
			try{
				numeroGiocatori = Integer.parseInt(this.consoleRead());
			}catch(NumberFormatException nfe){
				this.ErroreImmissioneValore();
			}
		}while(numeroGiocatori < 2 || numeroGiocatori > 4);

		return numeroGiocatori;
	}
	
	
	
	/**
	 * stampa interfaccia utente per scelta modalità partita
	 * @return ModalitaPartita
	 */
	public ModalitaPartita modalitaPartita(){
		int mod = 0;
		ArrayList<String> lista=new ArrayList<String>();
        lista.add("Partita singola");
        lista.add("Partita multipla");
		do{
			this.println("Modalita' partita disponibile: ");
			this.print(this.visualizzaElenco(lista));
			this.print("Inserisci la modalita' partita: ");
			try{
				mod = Integer.parseInt(this.consoleRead());
			}catch(NumberFormatException nfe){
				this.ErroreImmissioneValore();
			}
		}while(mod != 1 && mod != 2);

		return (mod == 1)? ModalitaPartita.SINGOLA : ModalitaPartita.MULTIPLA;
	}
	
	
	/**
	 * stampa elenco puntato degli elementi tipo stringa della lista
	 * @param lista
	 */
	public String visualizzaElenco(ArrayList<String> lista) {
		String temp="";
		int i=1;
		for(String stringa : lista) {
			temp+=i+") "+stringa+"\n";
			i++;
		}
		return temp;
		
	}
	
	
	/**
	 * stampa resoconto configurazione partita
	 */
	public void visualizzaScelte(ConfiguraPartita p){
		this.clear();
		this.println("--- Conferma dei valori immessi ---");
		this.println("Numero giocatori: " + p.getNumeroGiocatori());
		this.println("Modalita' partita: " + p.getModalitaPartita());
		if(p.getModalitaPartita() == ModalitaPartita.SINGOLA){
			this.println("Livello partita: " + p.getLivelloPartita());
		}
	}

	
}
