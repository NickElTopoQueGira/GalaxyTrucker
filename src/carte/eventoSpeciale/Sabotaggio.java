package carte.eventoSpeciale;

import carte.*;
import carte.meteore.Dado;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.Pedina;
import tessera.Coordinate;
import tessera.TipoTessera;

public class Sabotaggio extends EventiSpeciali {

    private ComunicazioneConUtente cns;
    private Dado dado;

    /**
     * Costruttore Sabotaggio super -> gli passiamo il lvl della carta e il tipo
     *
     * @param lvl
     */
    public Sabotaggio(int lvl) {
        super(lvl, TipoCarta.SABOTAGGIO);
    }

    @Override
    public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {

        if (elencoPedine.size() <= 1) {

            cns.println("Il giocatore è da solo, la carta sabotaggio non viene eseguita");

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

                riga = dado.dadiDoppi();
                colonna = dado.dadiDoppi();
                
                cns.println("viene sabotata la nave di:"+ elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNome() +" in posizione ("+riga+", "+colonna+")");

                if (elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getPlanciaDellaNave().get(riga).get(colonna).getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {

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
                }else {
                	cns.println("non è stata sabotata nessuna tessera, tentativi rimasti: "+(contatore-1));
                }
                
            } while (contatore < 3 || !isUnitaAbitativaColpita);
        }
        return elencoPedine;
    }

}
