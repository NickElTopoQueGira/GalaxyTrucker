package eccezioniPersonalizzate;

public class ErroreEquipaggio extends Exception{
	
	/**
	 * Metodo Messaggio di errore
	 * @param msg
	 */
    public ErroreEquipaggio(String msg){
        super(msg);
    }
}