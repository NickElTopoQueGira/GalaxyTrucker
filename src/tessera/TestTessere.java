package tessera;

import eccezioniPersonalizzate.ErroreRotazione;

import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;

import partita.giocatore.Colori;
import tessera.cannone.Cannone;

public class TestTessere {
	
	private static ComunicazioneConUtente stampa;	

	public static void main(String[] args) {
		stampa= ComunicazioneConUtente.getIstanza();

		FactoryTessera Factory=new FactoryTessera();
		
		/*//test massimo numero di tessere
		Tessera t1 = null;
		int contatore=0;
		for(int i=0; i<152; i++) {
			
			try {
				t1 = Factory.estraiTipo();
			} catch (ErroreTessera e) {
				e.printStackTrace();
			}
			
			contatore++;
			System.out.println(t1.toString());
			
			
		}
		System.out.println(contatore);
		
		
		*/
		Tessera t1=null;
		try {
			t1 = Factory.estraiTipo();
		} catch (ErroreTessera e) {
			e.printStackTrace();
		}
		Tessera t2 = null;
		try {
			t2= Factory.estraiTipo();
		} catch (ErroreTessera e) {
			e.printStackTrace();
		}
		stampa.print(t2.toString());
		
		try {
			t1.ruota();
		} catch (ErroreRotazione e) {
			e.printStackTrace();
		} //ruota di 90 gradi a dx
		
		stampa.print(t1.toString());
		
		//stampa centro con connettori
		Tessera t3=null;
		t3 = new Centro(Colori.BLU, null);
		
		stampa.print(t3.toString());
		
		
		Tessera t4 = null;
		try {
			t4 = new Cannone();
		} catch (ErroreTessera e) {
			e.printStackTrace();
		}
		
		stampa.print(t4.toString());
		try {
			t4.ruota();
		} catch (ErroreRotazione e) {
			e.printStackTrace();
		}
		
		stampa.print(t4.toString());
		try {
			t4.ruota();
		} catch (ErroreRotazione e) {
			e.printStackTrace();
		}
		
		stampa.print(t4.toString());
		
		
		System.out.println(Tessera.getCurrentSize());
		Tessera t5 = null;
		try {
			t5 = Factory.estraiTipo();
		} catch (ErroreTessera e) {
			e.printStackTrace();
		}
		stampa.print(t5.toString());
		try {
			t5.ruota();
		} catch (ErroreRotazione e) {
			e.printStackTrace();
		}
		stampa.print(t5.toString());
		try {
			t5.ruota();
		} catch (ErroreRotazione e) {
			e.printStackTrace();
		}
		stampa.print(t5.toString());
		
		String test = "";
		for(int i = 0; i <(5*6); i++) {
			test += '-'+"\n";
		}
		
		System.out.println(test);
	
		stampa.print(t5.toLegenda());
	}
	
}
