package tessera;

import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.StampaMessaggi;
import partita.giocatore.Colori;
import tessera.cannone.Cannone;
import tessera.motore.Motore;
import tessera.scudi.Scudi;
import tessera.tubi.Tubi;

public class TestTessere {
	
	private static StampaMessaggi stampa;

	public static void main(String[] args) {
		stampa= StampaMessaggi.getIstanza();

		FactoryTessera Factory=new FactoryTessera();
		
		/*//test massimo numero di tessere
		Tessera t1 = null;
		int contatore=0;
		for(int i=0; i<152; i++) {
			
			try {
				t1 = Factory.estraiTipo();
			} catch (ErroreTessera e) {
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tessera t2 = null;
		try {
			t2= Factory.estraiTipo();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stampa.StampaValoriTessera(t2);
		
		try {
			t1.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //ruota di 90 gradi a dx
		
		stampa.StampaValoriTessera(t1);
		
		//stampa centro con connettori
		Tessera t3=null;
		try {
			t3 = new Centro(Colori.BLU);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stampa.StampaValoriTessera(t3);
		
		
		Tessera t4 = null;
		try {
			t4 = new Cannone();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stampa.StampaValoriTessera(t4);
		try {
			t4.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stampa.StampaValoriTessera(t4);
		try {
			t4.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stampa.StampaValoriTessera(t4);
		
		
		System.out.println(Tessera.getCurrentSize());
		Tessera t5 = null;
		try {
			t5 = Factory.estraiTipo();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stampa.StampaValoriTessera(t5);
		try {
			t5.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stampa.StampaValoriTessera(t5);
		try {
			t5.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stampa.StampaValoriTessera(t5);
		
		String test = "";
		for(int i = 0; i <(5*6); i++) {
			test += '-'+"\n";
		}
		
		System.out.println(test);
	
	}
	
}
