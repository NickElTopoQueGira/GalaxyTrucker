package tessera;

import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;
import tessera.cannone.Cannone;

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
			t1.stampa();
			
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
		
		//stampa connettori di t1
		System.out.println(t1.getTipoTessera().toString());
		System.out.println("\t"+t1.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t1.getLatiTessera().getLeft().toString()+"\t\t"+t1.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t1.getLatiTessera().getDown().toString()+"\t\n");
		
		t1.ruota(); //ruota di 90 gradi a dx
		//stampa connettori di t1
		System.out.println(t1.getTipoTessera().toString());
		System.out.println("\t"+t1.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t1.getLatiTessera().getLeft().toString()+"\t\t"+t1.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t1.getLatiTessera().getDown().toString()+"\t\n");
		
		
		
		
		//stampa centro con connettori
		Tessera t3=null;
		try {
			t3 = new Centro(Colori.BLU);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(t3.getTipoTessera().toString());
		System.out.println("\t"+t3.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t3.getLatiTessera().getLeft().toString()+"\t\t"+t3.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t3.getLatiTessera().getDown().toString()+"\t\n");
		
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
		t4.stampa();
		t4.ruota();
		t4.stampa();
		t4.ruota();
		t4.stampa();
		
		
		System.out.println(Tessera.getCurrentSize());
		Tessera t5 = null;
		try {
			t5 = Factory.estraiTipo();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t5.stampa();
		t5.ruota();
		t5.stampa();
		t5.ruota();
		t5.stampa();
		
		String test = "";
		for(int i = 0; i <(5*6); i++) {
			test += '-'+"\n";
		}
		
		System.out.println(test);
	
	}
	
}
