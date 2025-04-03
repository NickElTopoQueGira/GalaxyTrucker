package partita.nave;

import Tessera.Tessera;
import java.util.ArrayList;

public abstract class Nave {
    private final ArrayList<Tessera> componentiPrenotati;

    public Nave(){
        this.componentiPrenotati = new ArrayList<Tessera>(2);
    }

    public abstract void assembla();

}
