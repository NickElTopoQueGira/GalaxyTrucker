package eccezioniPersonalizzate;

public class ErroreAggiuntaTessera extends Exception{
	
	/**
	 * Metodo Messaggio di errore
	 * @param msg
	 */
    public ErroreAggiuntaTessera(String msg){
        super(msg);
    }
}