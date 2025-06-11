package tessera;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreCoordinate;
import eccezioniPersonalizzate.ErroreEquipaggio;
import eccezioniPersonalizzate.ErroreTessera;
import eccezioniPersonalizzate.FinePartita;
import gioco.ComunicazioneConUtente;
import partita.giocatore.Colori;
import partita.nave.NaveLvl1;
import tabellone.Tabellone;
import tessera.batteria.Batteria;
import tessera.modulo_passeggeri.ModuloPasseggeri;
import tessera.tubi.Tubi;

public class TestTessere {
	
	public static void main(String[] args) {
		ComunicazioneConUtente com = ComunicazioneConUtente.getIstanza();
		FactoryTessera f= new FactoryTessera();
		
		/*
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
		*/
		
		
		NaveLvl1 n= new NaveLvl1(Colori.BLU);
		
		
		Tessera t1 = null;
		try {
			t1 = new Batteria();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Coordinate coordinate1= new Coordinate();
		coordinate1.setX(2);
		coordinate1.setY(2);
		t1.latiTessera.setUp(TipoConnettoriTessera.TRIPLO);
		t1.latiTessera.setDown(TipoConnettoriTessera.TRIPLO);
		t1.latiTessera.setLeft(TipoConnettoriTessera.TRIPLO);
		t1.latiTessera.setRight(TipoConnettoriTessera.TRIPLO);
		
		t1.setCoordinate(coordinate1);
		try {
			n.inserisciTessera(t1.getCoordinate(), t1);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroreCoordinate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Tessera t2 = null;
		try {
			t2 = new Batteria();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Coordinate coordinate2= new Coordinate();
		coordinate2.setX(1);
		coordinate2.setY(2);
		t2.latiTessera.setUp(TipoConnettoriTessera.TRIPLO);
		t2.latiTessera.setDown(TipoConnettoriTessera.TRIPLO);
		t2.latiTessera.setLeft(TipoConnettoriTessera.TRIPLO);
		t2.latiTessera.setRight(TipoConnettoriTessera.TRIPLO);
		
		t2.setCoordinate(coordinate2);
		try {
			n.inserisciTessera(t2.getCoordinate(), t2);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroreCoordinate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Tessera t3 = null;
		try {
			t3 = new Batteria();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Coordinate coordinate3= new Coordinate();
		coordinate3.setX(4);
		coordinate3.setY(2);
		t3.latiTessera.setUp(TipoConnettoriTessera.TRIPLO);
		t3.latiTessera.setDown(TipoConnettoriTessera.TRIPLO);
		t3.latiTessera.setLeft(TipoConnettoriTessera.TRIPLO);
		t3.latiTessera.setRight(TipoConnettoriTessera.TRIPLO);
		
		t3.setCoordinate(coordinate3);
		try {
			n.inserisciTessera(t3.getCoordinate(), t3);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroreCoordinate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Tessera t4 = null;
		try {
			t4 = new Batteria();
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Coordinate coordinate4= new Coordinate();
		coordinate4.setX(5);
		coordinate4.setY(2);
		t4.latiTessera.setUp(TipoConnettoriTessera.TRIPLO);
		t4.latiTessera.setDown(TipoConnettoriTessera.TRIPLO);
		t4.latiTessera.setLeft(TipoConnettoriTessera.TRIPLO);
		t4.latiTessera.setRight(TipoConnettoriTessera.TRIPLO);
		
		t4.setCoordinate(coordinate4);
		try {
			n.inserisciTessera(t4.getCoordinate(), t4);
		} catch (ErroreTessera e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErroreCoordinate e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((Batteria)t4).decrese();
		com.println(n.toString());
		
		
		Coordinate coordinate= new Coordinate();
		coordinate.setX(3);
		coordinate.setY(2);
		try {
			n.rimuoviTessera(coordinate);
		} catch (ErroreTessera | FinePartita e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		com.println(n.toString());
		
		com.printNumber(n.getCoordinateCentro().getX());
		com.printNumber(n.getCoordinateCentro().getY());
		
		
		com.printNumber(n.getNumeroPezziNaveDaRipagare());
		
		
	}

}
