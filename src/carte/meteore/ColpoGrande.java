package carte.meteore;

public class ColpoGrande extends Meteorite {
	
	/**
	 * Costruttore di ColpoGrande
	 * super -> direzione (il colpo grande può arrivare soilo dal basso) e il tipo
	 */
    public ColpoGrande() {
        super(PuntiCardinali.SUD, TypeMeteora.COLPO_GRANDE);
    }
}
