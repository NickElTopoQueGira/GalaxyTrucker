package gioco;

public class Main {
	public static void main(String[] args){
		// Inizializzazione delle comunicazioni con l'utente
		ComunicazioneConUtente comu = ComunicazioneConUtente.getIstanza();
		
		comu.println("\033[1;95m"+"GALAXY TRUCKER\n"+"\u001B[0m");
		comu.print("Premi invio per iniziare...");
		comu.consoleRead();
		comu.clear();

		Gioco gioco=new Gioco();
		
		
	}	
}
