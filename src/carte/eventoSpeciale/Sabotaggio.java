package carte.eventoSpeciale;

import java.util.ArrayList;
import java.util.Random;
import carte.*;
import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Giocatore;
import tessera.Coordinate;
import tessera.TipoTessera;

public class Sabotaggio extends EventiSpeciali {

	public Sabotaggio (int lvl) {
		
		super(lvl, TipoCarta.SABOTAGGIO);
	}
	
	private int RisultatiDadi() {
		
		Random random = new Random();
		
		int d1 = random.nextInt(6) + 1;
		int d2 = random.nextInt(6) + 1;
		
		return d1+d2;
	}
	@Override
	public void eseguiCarta(ArrayList<Giocatore> elencoGiocatore) {  //DA RIFARE
		
		int giocatoreMinorEquipaggio = 0;
		
		for(int i=1; i<elencoGiocatore.size(); i++) {
			
			if(elencoGiocatore.get(i).getNave().getEquipaggio() < elencoGiocatore.get(giocatoreMinorEquipaggio).getNave().getEquipaggio()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO
				
				giocatoreMinorEquipaggio = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
			
			}else if(elencoGiocatore.get(i).getNave().getEquipaggio() == elencoGiocatore.get(giocatoreMinorEquipaggio).getNave().getEquipaggio()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO
				
				if(elencoGiocatore.get(i).getPedina().getPosizioneSulTabellone() > elencoGiocatore.get(giocatoreMinorEquipaggio).getPedina().getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE
					
					giocatoreMinorEquipaggio = i;//IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
				}
			}
		}
		
		int contatore = 0, riga, colonna;
		boolean isUnitaAbitativaColpita = false;
		
		do {
			contatore++;
			
			riga = RisultatiDadi();
			colonna = RisultatiDadi();
			
			if(elencoGiocatore.get(giocatoreMinorEquipaggio).getNave().getPlanciaDellaNave().get(colonna).get(riga).getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
				
				try {
					elencoGiocatore.get(giocatoreMinorEquipaggio).getNave().rimuoviTessera(new Coordinate(colonna, riga));
					
					isUnitaAbitativaColpita = true;
				} catch (ErroreTessera err) {
					
					err.printStackTrace();
				}
			}
		}while(contatore < 3 || !isUnitaAbitativaColpita);
		
	}
}
