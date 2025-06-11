package eccezioniPersonalizzate;

public class EccezioneGioco extends Exception{
	
	/**
	 * Metodo Messaggio di errore
	 * @param msg
	 */
    public EccezioneGioco(String msg){
        super(msg);
    }
}
