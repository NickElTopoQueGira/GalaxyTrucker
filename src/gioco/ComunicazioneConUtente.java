/**
 * Questa classe e' utilizzata per gestire la comunicazione con l'utente sull'interfaccia console. 
 * 
 * Questa classe serve per istanziare solo una volta lo scanner per la lettura 
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
import java.util.NoSuchElementException;
import java.util.Scanner;
import partita.giocatore.Giocatore;

public class ComunicazioneConUtente {
    private final Scanner input;
    private static ComunicazioneConUtente istanza = null;
    
    /**
     * Costruttore ComunicazioneConUtente
     */
    private ComunicazioneConUtente(){
        this.input = new Scanner(System.in);
    }
    
    /** 
     * Metodo per 'gettare' l'istanza della classe. 
     * Viene fatto il controllo se e' gia' stata istanziata
     * se si -> viene ritornata l'istanza
     * se no -> viene creata una istanza e viene ritornata
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
     * Metodo per stampare sulla console un messaggio per l'utente
     * Questa Metodo stampa senza l'acapo in automatico
     *  
     * @param msg -> messaggio che si vuole stampare sulla console
     */
    public void print(String msg){
        System.out.print(msg);
    }

    /**
     * Metodo per stampare sulla console un messaggio per l'utente
     * Questa Metodo stampa l'acapo in automatico
     *  
     * @param msg -> messaggio che si vuole stampare sulla console
     */
    public void println(String msg){
        System.out.println(msg);
    }
        
    /**
     * Metodo per stampare sulla console valori interi
     * @param t Int numero da stampare sulla console
     */
  	public void printNumber(int t) {
  		System.out.println(t);
  	}

    /**
     * Metodo per stampare sulla console un errore per l'utente
     * Richiama la: System.err.println()
     *  
     * @param error -> errore che si vuole stampare sulla console
     */
    public void printError(String error){
        System.out.println("\u001B[31m" + error + "\u001B[0m");
    }

    /**
     * Metodo per leggere l'input dell'utente sulla console.
     * @return input.nextLine() -> stringa letta dalla console
     */
    public String consoleRead(){
		return this.input.nextLine();
    }
    
    /**
     * Metodo per leggere un'intero immesso dall'utente.
	 * Questo metodo comprende nativamente la gestione dell'inserimento
	 * del valore sbagliato (white space, char, null value)
	 *
     * @return intero letto
     */
    public int consoleReadInt(){
		int valore = 0;
		boolean pass = false;
		do{
			try{
				valore = Integer.parseInt(this.input.nextLine());
				pass = true;
			}catch(NumberFormatException | NoSuchElementException err){
				erroreImmissioneValore();
			}
		}while(!pass);
        return valore;
    }

    /**
     * Metodo per pulire la console stampando 200 righe vuote
     */
    public void clear(){
    	for(int i = 0; i < 100; i+= 1){
    		System.out.println();
    	}
    	System.out.flush();
    }
    
	/**
	 * Stampa di inizio game
	 */
	public void inizioGame() {
        this.println("\033[1;95m");
		this.println("  /$$$$$$            /$$                               /$$$$$$$$                               /$$                           ");
        this.println(" /$$__  $$          | $$                              |__  $$__/                              | $$                           ");
        this.println("| $$  \\__/  /$$$$$$ | $$  /$$$$$$  /$$   /$$ /$$   /$$   | $$     /$$$$$$  /$$   /$$  /$$$$$$$| $$   /$$  /$$$$$$   /$$$$$$  ");
        this.println("| $$ /$$$$ |____  $$| $$ |____  $$|  $$ /$$/| $$  | $$   | $$    /$$__  $$| $$  | $$ /$$_____/| $$  /$$/ /$$__  $$ /$$__  $$ ");
        this.println("| $$|_  $$  /$$$$$$$| $$  /$$$$$$$ \\  $$$$/ | $$  | $$   | $$   | $$  \\__/| $$  | $$| $$      | $$$$$$/ | $$$$$$$$| $$  \\__/ ");
        this.println("| $$  \\ $$ /$$__  $$| $$ /$$__  $$  >$$  $$ | $$  | $$   | $$   | $$      | $$  | $$| $$      | $$_  $$ | $$_____/| $$       ");
        this.println("|  $$$$$$/|  $$$$$$$| $$|  $$$$$$$ /$$/\\  $$|  $$$$$$$   | $$   | $$      |  $$$$$$/|  $$$$$$$| $$ \\  $$|  $$$$$$$| $$       ");
        this.println(" \\______/  \\_______/|__/ \\_______/|__/  \\__/ \\____  $$   |__/   |__/       \\______/  \\_______/|__/  \\__/ \\_______/|__/       ");
        this.println("                                             /$$  | $$                                                                       ");
        this.println("                                            |  $$$$$$/                                                                       ");
        this.println("                                             \\______/                                                                        ");
		this.println("\u001B[0m");
		this.print("Premi invio per iniziare...");
		this.consoleReadInvio();
		this.clear();
	}
	
	
	/**
	 * Legge un invio
	 */
	public void consoleReadInvio() {
		this.input.nextLine();
	}
        
    /**
	 * Errore immissione valore non valido
	 */
	public void erroreImmissioneValore() {
		this.printError("Valore immesso non valido");
	}

    /**
     * Semplice menu' di conferma (S/N)
     * 
     * @return true -> SI
     *         false -> NO
     */
    public boolean conferma(){
		boolean pass = false;
		boolean conferma = false;
		do{
			this.print("\ninserire risposta (s/n): ");
			String t = this.consoleRead();
			if(t.isBlank() || t.isEmpty()){
				pass = false;
			}else{
				switch(t.toUpperCase().charAt(0)){
					case 'S'-> {
						conferma = true;
						pass = true;
					}
					case 'N'-> {
						conferma = false;
						pass = true;
					}
					default->{
						erroreImmissioneValore();
						pass = false;
					}
				}
			}
		}while(!pass);

		return  conferma;
	}
    
    /**
     * Metodo come conferma ma in aggiunta di un commento specifico scelto in base al caso
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
	 * Metodo il quale ritorna una stringa con elenco puntato degli elementi tipo stringa della lista
	 * @param lista ArrayList<Stringa>
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
	 * Metodo per la richiesta di abbandono del volo
	 * @param giocatore Giocatore
	 * @return conferma (true o false)
	 */
	public boolean richiestaAbbandonaVolo(Giocatore giocatore) {
		
		this.println(giocatore.getNome()+" vuoi abbanonare il volo?");
		
		return this.conferma();
	}
}
