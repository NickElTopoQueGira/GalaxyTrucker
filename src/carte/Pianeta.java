package carte;

import merce.*;
import java.util.*;

public class Pianeta extends Carta {
	
	private ArrayList<ArrayList<Merce>> pianeti;
	
	public Pianeta (int lvl) {
		
		super(lvl, TipoCarta.PIANETA);
		
		pianeti = new ArrayList<>();
	}
	
	private void GeneraValori() {
		Random random = new Random();
		
		int numpianeti = random.nextInt(4) + 1;
		
		for(int i=0; i<numpianeti; i++) {                   // DA LAVORARE: SCELTA E DISTRIBUZIONE DELLE MERCI
			ArrayList<Merce> merce = new ArrayList<>();
			
			
			
			for(int j=0; j<5; j++) {
				
				
				
				merce.add(new Merce(TipoMerce.MERCE_ROSSA));
			}
			pianeti.add(merce);
		}
	}
}
