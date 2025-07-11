package carte.meteore;

import gioco.ComunicazioneConUtente;
import java.util.Random;

public class MeteoriteGrande extends Meteorite {
	
	/**
	 * Costruttore di MeteoriteGrande
	 * super -> gli passiamo la direzione casuale (metodo) e il tipo
	 * @param lvl int
	 */
    public MeteoriteGrande(int lvl) {

        super(CasualDirezzione(lvl), TypeMeteora.METEORITE_GRANDE);
        stampa = ComunicazioneConUtente.getIstanza();
    }
    
    /**
     * Metodo che serve essendo un meteorite grande la direzione va in base al livello
     * se livello uno solo da nord
     * livello due anche ovest e est
     * livello 3 da tutte le direzioni
     * 
     * @param lvl
     * @return direzione del meteorite;
     */
    static PuntiCardinali CasualDirezzione(int lvl) {
        PuntiCardinali s = null;
        Random random = new Random();

        int x = 0;
        switch (lvl) {
            case 1 -> {
                x = 1;
            }
            case 2 -> {
                x = random.nextInt(3) + 1;
            }
            case 3 -> {
                x = random.nextInt(4) + 1;
            }
            default -> {
                stampa.printError("ERROR: scelta del random in base al livello della carta (errorTipe: switch) (class: Meteorite)");
            }
        }

        switch (x) {
            case 1 -> {
                return PuntiCardinali.NORD;
            }
            case 2 -> {
                return PuntiCardinali.EST;
            }
            case 3 -> {
                return PuntiCardinali.OVEST;
            }
            case 4 -> {
                return PuntiCardinali.SUD;
            }
            default -> {
                stampa.printError("ERROR: random della direzione del meteorite (errorTipe: switch) (class: Meteorite)");
            }
        }

        return s;
    }
}
