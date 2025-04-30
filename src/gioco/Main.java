package gioco;

import partita.ConfiguraPartita;
import partita.Partita;

public class Main {
	public static void main(String[] args){
		System.out.println("GALAXY TRUCKER\n");
		
		ConfiguraPartita conf = new ConfiguraPartita();
		Partita p = conf.creaPartita();

		System.out.println(p);
		
	}	
}
