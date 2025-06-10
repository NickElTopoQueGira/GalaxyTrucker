package tessera;

import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import tabellone.Tabellone;

public class TestTessere {
	
	public static void main(String[] args) {
		ComunicazioneConUtente com = ComunicazioneConUtente.getIstanza();
		FactoryTessera f= new FactoryTessera();
		
		
		for(int i=0; i<f.getNumeroTessereMax();i++) {
			Tessera tessera=null;
			try {
				tessera= f.estraiTipo();
			} catch (ErroreTessera e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			com.println(tessera.toString());
			if(tessera.getListaTessere().contains(tessera)) {
				com.println("---"+tessera.toString());
				com.printNumber(tessera.getId());
			}
			
			com.printNumber(tessera.getListaTessere().size());
		}
		
		
		
		
	}

}
