package gioco;

import java.util.Scanner;
import partita.ModalitaPartita;
import partita.Partita;

public class Main {

	

	public static void main(String[] args) {
		System.out.println("GALAXY TRUCKER\n");
		
		ConfiguraPartita();
		
	}
	
	
	
	public static void ConfiguraPartita() {
		int NumeroGiocatori;
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("Inserisci Numero Giocatori (Max = 4):\n");
			NumeroGiocatori = scanner.nextInt();
	        
			
		}while(1>NumeroGiocatori || NumeroGiocatori>4);
		int Modalita;
		do {
			System.out.println("Inserisci Modalita: \n"
					+ "1) Partita Singola\n"
					+ "2) Partita Multipla\n");
			Modalita = scanner.nextInt();
		}while(1>Modalita || Modalita>2);
		
		ModalitaPartita ModalitaEnum;
		if(Modalita==1) {
			ModalitaEnum=ModalitaPartita.SINGOLA;
		}else {
			ModalitaEnum=ModalitaPartita.MULTIPLA;
		}
		
		
		Partita partita=new Partita(ModalitaEnum, NumeroGiocatori);
		
		
		
	}
	
	
	
	
	
	

}
