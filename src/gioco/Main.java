package gioco;

public class Main {
	public static void main(String[] args){
		// Inizializzazione delle comunicazioni con l'utente
		ComunicazioneConUtente comu = ComunicazioneConUtente.getIstanza();
		comu.inizioGame();

		new Gioco().gioca();;
	}	
}
