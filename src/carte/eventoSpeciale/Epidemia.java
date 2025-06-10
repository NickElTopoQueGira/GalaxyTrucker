package carte.eventoSpeciale;

import carte.*;
import eccezioniPersonalizzate.ErroreEquipaggio;
import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.*;
import tessera.*;
import tessera.modulo_passeggeri.*;

public class Epidemia extends EventiSpeciali {

    private ComunicazioneConUtente stampa;

    /**
     * Costruttore Epidemia super -> gli passiamo il lvl della carta e il tipo
     *
     * @param lvl
     */
    public Epidemia(int lvl) {

        super(lvl, TipoCarta.EPIDEMIA);
        stampa = ComunicazioneConUtente.getIstanza();
    }
    
    /**
     * Metodo che completa la carta la quale trova tutti i moduli attaccati tra di loro e ne toglie uno di equipaggio ciascuno
     */
    @Override
    public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {

        for (int x = 0; x < elencoPedine.size(); x++) {

            int contatore = 0;

            boolean[][] isVisitato = new boolean[elencoPedine.get(x).getGiocatore().getNave().getRighe()][elencoPedine.get(x).getGiocatore().getNave().getColonne()];

            for (int i = 0; i < elencoPedine.get(x).getGiocatore().getNave().getRighe(); i++) {
                for (int j = 0; j < elencoPedine.get(x).getGiocatore().getNave().getColonne(); j++) {

                    if ((elencoPedine.get(x).getGiocatore().getNave().getPlanciaDellaNave().get(i).get(j).getTipoTessera() == TipoTessera.MODULO_PASSEGGERI
                            || elencoPedine.get(x).getGiocatore().getNave().getPlanciaDellaNave().get(i).get(j).getTipoTessera() == TipoTessera.CENTRO)
                            && !isVisitato[i][j]) {

                        ArrayList<Tessera> gruppo = new ArrayList<>();
                        trovaModuloAtaccato(i, j, elencoPedine.get(x).getGiocatore().getNave().getPlanciaDellaNave(), isVisitato, gruppo);

                        if (gruppo.size() > 1) {
                            for (Tessera epidemia : gruppo) {

                                if (epidemia.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {

                                    try {
										((ModuloPasseggeri) epidemia).setNumeroCosmonauti(-1);
									} catch (ErroreEquipaggio e) {
										e.printStackTrace();
									}

                                } else if (epidemia.getTipoTessera() == TipoTessera.CENTRO) {

                                    ((Centro) epidemia).rimuoviPasseggeri(-1);
                                }
                                contatore++;
                            }
                        }
                    }
                }
            }
            stampa.println("La nave di " + elencoPedine.get(x).getGiocatore().getNome() + " ha perso " + contatore + " membri dell'equipaggio");
        }
        return elencoPedine;
    }
    
    /**
     * metodo ricorsivo che permette di trovare i moduli che sono attaccati ad altri moduli e li aggiunge
     * in un arreylist per poi poter togliere l'equipaggio
     * 
     * @param i
     * @param j
     * @param nave
     * @param isVisitato
     * @param gruppo
     */
    private void trovaModuloAtaccato(int i, int j, ArrayList<ArrayList<Tessera>> nave, boolean[][] isVisitato, ArrayList<Tessera> gruppo) {

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        TipoLato[] dLato = {TipoLato.UP, TipoLato.RIGHT, TipoLato.DOWN, TipoLato.LEFT};

        isVisitato[i][j] = true;
        gruppo.add(nave.get(i).get(j));

        for (int d = 0; d < 4; d++) { // FOR PER CONTROLLARE TUTTI E 4 I LATI

            int nx = i + dx[d];
            int ny = j + dy[d];

            if (nx >= 0 && ny >= 0 && nx < nave.size() && ny < nave.get(0).size()) { //CONTROLLO SE NON SI ESCE DAL TABELLONE

                if ((nave.get(nx).get(ny).getTipoTessera() == TipoTessera.MODULO_PASSEGGERI
                        || nave.get(nx).get(ny).getTipoTessera() == TipoTessera.CENTRO)
                        && !isVisitato[nx][ny]) {

                    if (controlloConnettoreTraTessere(dLato[d], nave.get(i).get(j), nave.get(nx).get(ny))) {

                        trovaModuloAtaccato(nx, ny, nave, isVisitato, gruppo);
                    }
                }
            }
        }
    }
    /**
     * Metodo specifico per i controlli della carta epidemia che controlla se un lato specifico è collegato ad un altra tessera
     * simile a quello che c'è sulla nave ma ha un compito più specifico
     * 
     * @param dLato
     * @param tesseraP
     * @param tessera2
     * @return se la tessere è collegata al lato richiesto
     */
    private boolean controlloConnettoreTraTessere(TipoLato dLato, Tessera tesseraP, Tessera tessera2) {

        switch (dLato) {
            case TipoLato.UP -> {
                if (tesseraP.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO && tessera2.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO) {
                    if (tesseraP.getLatiTessera().getUp() == TipoConnettoriTessera.TRIPLO || tessera2.getLatiTessera().getDown() == TipoConnettoriTessera.TRIPLO) {

                        return true;

                    } else if (tesseraP.getLatiTessera().getUp() == tessera2.getLatiTessera().getDown()) {

                        return true;
                    } else {
                        stampa.printError("ERROR: COLLEGAMENTO TRA TESSERE IRREGOLARE!!");
                    }
                }
            }
            case TipoLato.RIGHT -> {
                if (tesseraP.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO && tessera2.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO) {
                    if (tesseraP.getLatiTessera().getRight() == TipoConnettoriTessera.TRIPLO || tessera2.getLatiTessera().getLeft() == TipoConnettoriTessera.TRIPLO) {

                        return true;

                    } else if (tesseraP.getLatiTessera().getRight() == tessera2.getLatiTessera().getLeft()) {

                        return true;
                    } else {
                        stampa.printError("ERROR: COLLEGAMENTO TRA TESSERE IRREGOLARE!!");
                    }
                }
            }
            case TipoLato.DOWN -> {
                if (tesseraP.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO && tessera2.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO) {
                    if (tesseraP.getLatiTessera().getDown() == TipoConnettoriTessera.TRIPLO || tessera2.getLatiTessera().getUp() == TipoConnettoriTessera.TRIPLO) {

                        return true;

                    } else if (tesseraP.getLatiTessera().getDown() == tessera2.getLatiTessera().getUp()) {

                        return true;
                    } else {
                        stampa.printError("ERROR: COLLEGAMENTO TRA TESSERE IRREGOLARE!!");
                    }
                }
            }
            case TipoLato.LEFT -> {
                if (tesseraP.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO && tessera2.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO) {
                    if (tesseraP.getLatiTessera().getLeft() == TipoConnettoriTessera.TRIPLO || tessera2.getLatiTessera().getRight() == TipoConnettoriTessera.TRIPLO) {

                        return true;

                    } else if (tesseraP.getLatiTessera().getLeft() == tessera2.getLatiTessera().getRight()) {

                        return true;
                    } else {
                        stampa.printError("ERROR: COLLEGAMENTO TRA TESSERE IRREGOLARE!!");
                    }
                }
            }
            default -> {
            }
        }

        return false;
    }

}
