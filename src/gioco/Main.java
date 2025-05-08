package gioco;

public class Main {
	public static void main(String[] args){
		// Inizializzazione delle comunicazioni con l'utente
		ComunicazioneConUtente comu = ComunicazioneConUtente.getIstanza();
		
		comu.println("\033[1;95m"+"GALAXY TRUCKER\n"+"\u001B[0m");
		Gioco gioco=new Gioco();
		
		
	}	
}
