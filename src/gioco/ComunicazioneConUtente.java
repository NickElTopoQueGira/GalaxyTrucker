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

import java.util.Scanner;

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
     * Funzione per pulire la console stampando 200 rihe vuote
     */
    public void clear() {
    	for(int i=0; i<200; i++) {
    		System.out.print("\n");
    	}
    	
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
}
