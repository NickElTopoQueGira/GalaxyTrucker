package carte.meteore;

import gioco.ComunicazioneConUtente;
import java.util.Random;

public class Meteorite {

    private PuntiCardinali direzione;
    private TypeMeteora type;
    private int risultatoDado;
    private final Dado dado;
    protected static ComunicazioneConUtente stampa;

    public Meteorite(TypeMeteora t) {
        this.dado = new Dado();
        stampa = ComunicazioneConUtente.getIstanza();
        this.direzione = casualDirezione();
        this.risultatoDado = dado.dadiDoppi();
        this.type = t;

    }

    public Meteorite(PuntiCardinali direzione, TypeMeteora t) {
        this.dado = new Dado();
        this.direzione = direzione;
        this.risultatoDado = dado.dadiDoppi();
        this.type = t;
        stampa = ComunicazioneConUtente.getIstanza();
    }

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

	public void setRisultatoDado(int risultatoDado) {
		this.risultatoDado = risultatoDado;
	}

	public PuntiCardinali getDirezione() {
        return direzione;
    }

    public void setDirezione(PuntiCardinali direzione) {
        this.direzione = direzione;
    }

    public TypeMeteora getType() {
        return type;
    }

    public void setType(TypeMeteora type) {
        this.type = type;
    }

	public int getDado() {
		return this.risultatoDado;
	}


}
