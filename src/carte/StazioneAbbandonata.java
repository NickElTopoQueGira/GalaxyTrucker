package carte;

import java.util.Random;

public class StazioneAbbandonata extends Carta {
	
	private int giocatorinecessari;
	private int penalitagiorni;
	
	public StazioneAbbandonata (int lvl) {
		
		super(lvl, TipoCarta.STAZIONE_ABBANDONATA);
		GeneraValori(lvl);
	}
	
	private void GeneraValori(int lvl) {
		this.giocatorinecessari = GeneraGiocatoriNecessari(lvl);
		
	}
	private int GeneraGiocatoriNecessari(int lvl) {

		Random random = new Random();
		
		int scelta = random.nextInt(2) + 0;
		int ng = 0;
		
		switch(lvl) {
		case 1:
			ng = 5+scelta;
		break;
		case 2:
			ng = 7+scelta;
		break;
		case 3:
			ng = 9+scelta;
		break;
		default:
			System.out.println("ERROR: scelta casuale numero equipaggio necessario al completamento (errorTipe: switch) (class: StazioneAbbandonata)");
		break;
		}
		return ng;
	}
	
	private int SceltaGiorniPenalita() {
		
		
		return 0;
	}
	
	private void GeneraMerce() {
		
	}
}
