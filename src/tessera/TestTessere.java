package tessera;

import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;
import tessera.cannone.Cannone;
import tessera.motore.Motore;
import tessera.scudi.Scudi;
import tessera.tubi.Tubi;

public class TestTessere {

	public static void main(String[] args) {

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
		System.out.println(t2.toString());
		
		try {
			t1.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //ruota di 90 gradi a dx
		
		System.out.println(t1.toString());
		
		//stampa centro con connettori
		Tessera t3=null;
		try {
			t3 = new Centro(Colori.BLU);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(t3.toString());
		
		
		Tessera t4 = null;
		try {
			t4 = new Cannone();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(t4.getTipoTessera().toString());
		System.out.println("\t"+t4.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t4.getLatiTessera().getLeft().toString()+"\t\t"+t4.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t4.getLatiTessera().getDown().toString()+"\t\n");
		
		//System.out.println(((Motore)t4).getLatoMotore().toString());
		System.out.println(t4.toString());
		try {
			t4.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(t4.toString());
		try {
			t4.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(t4.toString());
		
		
		System.out.println(Tessera.getCurrentSize());
		Tessera t5 = null;
		try {
			t5 = Factory.estraiTipo();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(t5.toString());
		try {
			t5.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(t5.toString());
		try {
			t5.ruota();
		} catch (ErroreRotazione e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(t5.toString());
		
		String test = "";
		for(int i = 0; i <(5*6); i++) {
			test += '-'+"\n";
		}
		
		System.out.println(test);
	
	}
	
}
