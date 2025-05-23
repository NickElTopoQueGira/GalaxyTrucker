package carte.eventoSpeciale;

import carte.*;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.Pedina;
import tessera.Coordinate;
import tessera.TipoTessera;

public class Sabotaggio extends EventiSpeciali {

    private ComunicazioneConUtente cns;

    /**
     * Costruttore Sabotaggio super -> gli passiamo il lvl della carta e il tipo
     *
     * @param lvl
     */
    public Sabotaggio(int lvl) {
        super(lvl, TipoCarta.SABOTAGGIO);
    }

    private int RisultatiDadi() {
        Random random = new Random();

        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;

        return d1 + d2;
    }

    @Override
    public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {

        if (elencoPedine.size() <= 1) {

            cns.println("Il giocatore è da solo, la carta Zona di guerra non viene eseguita");

        } else {

            int giocatoreMinorEquipaggio = 0;

            for (int i = 1; i < elencoPedine.size(); i++) {

                if (elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() < elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getEquipaggio()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO

                    giocatoreMinorEquipaggio = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO

                } else if (elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() == elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getEquipaggio()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO

                    if (elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(giocatoreMinorEquipaggio).getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE

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

                if (elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getPlanciaDellaNave().get(colonna).get(riga).getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {

                    try {
                        try {
                            elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().rimuoviTessera(new Coordinate(colonna, riga));
                        } catch (ErroreGiocatore e) {

                            e.printStackTrace();
                        }

                        isUnitaAbitativaColpita = true;
                    } catch (ErroreTessera err) {

                        err.printStackTrace();
                    }
                }
            } while (contatore < 3 || !isUnitaAbitativaColpita);
        }
        return elencoPedine;
    }

}
