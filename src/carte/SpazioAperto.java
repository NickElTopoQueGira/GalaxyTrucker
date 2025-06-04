package carte;

import gioco.ComunicazioneConUtente;
import java.util.ArrayList;
import partita.Pedina;

public class SpazioAperto extends Carta {

    private ComunicazioneConUtente stampa;

    /**
     * Costruttore SpazioAperto super -> gli passiamo il lvl della carta e il
     * tipo
     *
     * @param lvl
     */
    public SpazioAperto(int lvl) {

        super(lvl, TipoCarta.SPAZIO_APERTO);
        stampa = ComunicazioneConUtente.getIstanza();
    }

    /**
     * Metodo che in base alla potenza dei motori muovera le pedine di tot
     * caselle in avanti
     *
     * @param elencoPedine
     * @return elencoPedine
     */
    @Override
    public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
        for (int i = 0; i < elencoPedine.size(); i++) {

            int potenzaMotore = elencoPedine.get(i).getGiocatore().getNave().getPotenzaMotori(); //PRENTE LA POTENZA MOTORI

            elencoPedine.get(i).getTabellone().muoviPedina(elencoPedine.get(i), potenzaMotore);  // SOMMA LA POSIZIONE ALLA POTENZA MOTORE E LA IMPOSTA COME NUOVA POSIZIONE

            stampa.println("LA NAVE DI " + elencoPedine.get(i).getGiocatore().getNome() + " HA " + potenzaMotore + " DI POTENZA MOTORE E VA AVANTI DI " + potenzaMotore);
        }
        return elencoPedine;
    }
}
