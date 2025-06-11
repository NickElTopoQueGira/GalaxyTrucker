package carte.meteore;

import gioco.ComunicazioneConUtente;
import java.util.Random;

public class Meteorite {

    private PuntiCardinali direzione;
    private TypeMeteora type;
    private int risultatoDado;
    private final Dado dado;
    protected static ComunicazioneConUtente stampa;
    
    /**
     * Costruttore di Meteorite che prende il tipo del meteorite
     * @param tipo meteorite
     */
    public Meteorite(TypeMeteora t) {
        this.dado = new Dado();
        stampa = ComunicazioneConUtente.getIstanza();
        this.direzione = casualDirezione();
        this.risultatoDado = dado.dadiDoppi();
        this.type = t;

    }
    
    /**
     * Costruttore di Meteorite che prende il tipo del meteorite e la direzzione
     * 
     * @param tipo meteorite
     * @param direzione
     */
    public Meteorite(PuntiCardinali direzione, TypeMeteora t) {
        this.dado = new Dado();
        this.direzione = direzione;
        this.risultatoDado = dado.dadiDoppi();
        this.type = t;
        stampa = ComunicazioneConUtente.getIstanza();
    }
    
    /**
     * metodo che genra casualmente la direzzione del meteorite
     * 
     * @return direzzione del meteorite
     */
    private PuntiCardinali casualDirezione() {
        Random random = new Random();
        int x = random.nextInt(4) + 1;

        return switch (x) {
            case 1 ->
                PuntiCardinali.NORD;
            case 2 ->
                PuntiCardinali.EST;
            case 3 ->
                PuntiCardinali.OVEST;
            case 4 ->
                PuntiCardinali.SUD;
            default -> {
                stampa.printError("ERROR: random direzione (class: Meteorite)");
                yield null;
            }
        };
    }
    
    /**
     * Metodo setter per impostare il valore sommato dei due dadi del meteorite
     * @param int valore sommato di due dadi
     */
	public void setRisultatoDado(int risultatoDado) {
		this.risultatoDado = risultatoDado;
	}
	
	/**
     * Metodo getter per prendere la direzzione
     * @return direzzione
     */
	public PuntiCardinali getDirezione() {
        return direzione;
    }

	/**
     * Metodo getter per prendere il tipo
     * @return tipo meteorite
     */
    public TypeMeteora getType() {
        return type;
    }
    /**
     * Metodo getter per prendere il valore sommato dei due dadi del meteorite
     * @return il risultato dei due dadi
     */
	public int getDado() {
		return this.risultatoDado;
	}


}
