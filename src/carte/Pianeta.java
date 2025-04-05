package carte;

import merce.*;
import java.util.*;

public class Pianeta extends Carta {
	
	private int penalitagiorni;
	
	private ArrayList<ArrayList<Merce>> pianeti;
	
	public Pianeta (int lvl) {
		
		super(lvl, TipoCarta.PIANETA);
		
		pianeti = new ArrayList<>();
		GeneraValori();
	}
	
	private void GeneraValori() {
		Random random = new Random();
		
		int numpianeti = random.nextInt(3) + 2; // FA UN RANDOM DA UN MINIMO DI 2 PIANETI A UN MASSIMO DI 4
		int valorecarta = SceltaVTDP(numpianeti);
		penalitagiorni = CalcoloPGV(numpianeti, valorecarta);
		
		
		for(int i=0; i<numpianeti; i++) {                   // DA LAVORARE: SCELTA E DISTRIBUZIONE DELLE MERCI
			ArrayList<Merce> merce = new ArrayList<>();
			
			
			
			for(int j=0; j<5; j++) {
				
				
				
				merce.add(new Merce(TipoMerce.MERCE_ROSSA));
			}
			pianeti.add(merce);
		}
	}
	
	//VALORE TOTALE DEI PIANETI = VTDP / PENALITA GIORNI VIAGGIO = PGV
	int SceltaVTDP(int numpianeti) {
		Random random = new Random();
		int vtdp=0;
		
		switch(numpianeti) {
		case 4 ->{
			vtdp = (random.nextInt(17) + 4) + (random.nextInt(13) + 3) + (random.nextInt(9) + 2) + (random.nextInt(5) + 1);   // MAX 50 / MIN 
		}
		case 3 ->{
			
		}
		case 2 ->{
			
		}
		default ->{
			System.out.println("ERROR: scelta randomica del valore totate dei pianeti della carta (errorTipe: switch) (class: Pianeta)");
		}
		}
		
		return vtdp;
	}
	
	int CalcoloPGV(int numpianeti, int vtdp) {
		int pgv=0;

		switch(numpianeti) {
		case 4 ->{
			pgv = vtdp/4;
			pgv = pgv/2;
		}
		case 3 ->{
			pgv = vtdp/3;
			pgv = pgv/2;
		}
		case 2 ->{
			pgv = vtdp/2;
			pgv = pgv/2;
		}
		default ->{
			System.out.println("ERROR: calcolo dei giorni di penalità della carta (errorTipe: switch) (class: Pianeta)");
		}
		}
		
		return pgv;
	}
}
