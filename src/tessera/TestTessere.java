package tessera;

import eccezioniPersonalizzate.ErroreRisorse;
import eccezioniPersonalizzate.ErroreRotazione;


import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import gioco.Gioco;
import partita.giocatore.Colori;
import tessera.cannone.Cannone;
import tessera.merce.Merce;
import tessera.merce.Stiva;
import tessera.merce.TipoMerce;

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
		try {
			t3 = new Centro(Colori.BLU, null);
		} catch (ErroreTessera e) {
			e.printStackTrace();
		}
		
		stampa.print(t3.toString());
		
		
		
		
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
	
		stampa.print(t5.toLegenda()+"\n\n\n\n\n");
		
		/*try {
			Tessera test1= new ModuloPasseggeri();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		for(int i=0; i<Tessera.getListaTessere().size(); i++) {
			stampa.println(Tessera.getListaTessere().get(i).toString());
		}
		stampa.print("-----\n");
		Tessera t6=t5;
		Tessera.removeDaListaTessere(t6);
		
		for(int i=0; i<Tessera.getListaTessere().size(); i++) {
			stampa.println(Tessera.getListaTessere().get(i).toString());
		}
		
		
		Tessera stiva = null;
		try {
			stiva = Factory.estraiTipo();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		stampa.println(stiva.toString());
		
		Merce merce1=new Merce(TipoMerce.MERCE_BLU);
		
		try {
			((Stiva)stiva).inserisciMerci(merce1);
		} catch (ErroreRisorse e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		stampa.println(stiva.toString());
		
		stampa.println(((Stiva)stiva).getStiva().get(0).getTipoMerce().toString());
		
		
		
	}	
}
