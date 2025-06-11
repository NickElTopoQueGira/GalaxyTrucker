package carte.eventoSpeciale;

import carte.*;
import java.util.ArrayList;
import partita.Pedina;

public class EventiSpeciali extends Carta {

    /**
     * Costruttore Nemici super -> gli passiamo il lvl della carta e il tipo
     *
     * @param lvl
     * @param tipo carta
     */
    public EventiSpeciali(int lvl, TipoCarta c) {
        super(lvl, c);
    }
    
    /**
     * Metoto esegui carta che le classi figlie poi eseguiranno cpn azioni specifiche
     */
    @Override
    public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
        return elencoPedine;
    }
}
