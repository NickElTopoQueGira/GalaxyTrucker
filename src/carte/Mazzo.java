package carte;

import carte.eventoSpeciale.*;
import carte.nemico.*;
import gioco.ComunicazioneConUtente;
import java.util.*;

public class Mazzo {

    protected static ArrayList<Carta> lista;
    private int[][] conteggio;
    private final ComunicazioneConUtente stampa;

    /**
     * costruttore di mazzo metodo: GeneraConteggio() per generare la matrice
     * conteggio metodo: CreaMazzo(@param lvl) per generare il mazzo (tutte le
     * carte)
     *
     * @param lvl
     */
    public Mazzo(int lvl) {
        stampa = ComunicazioneConUtente.getIstanza();
        lista = new ArrayList<>();

        generaConteggio();
        CreaMazzo(lvl);
    }

    /**
     * metodo che genera i valori della matrice conteggio che ha l'utiltà di
     * tenere il conteggio delle carte e non avere un eccessio di una tipologia
     * di carta
     */
    private void generaConteggio() {

        conteggio = new int[][]{
            {1, 0, 3, 0, 1, 1, 1, 0, 0, 4, 0, 0}, // livello 1
            {1, 0, 3, 0, 1, 1, 1, 0, 0, 4, 0, 0}, // livello 2
            {0, 0, 3, 0, 0, 1, 1, 0, 0, 4, 0, 1} // livello 3
            
            /*
             * 1 polvere stellare
             * 2 zona guerra          1->0
             * 3 PIOGGIA_METEORITI,
             * 4 SPAZIO_APERTO,		  3->0 (lvl.1 4->0)
             * 5 SCHIAVISTI,          1->0
             * 6 CONTRABBANDIERI,    
             * 7 PIRATI,
             * 8 STAZIONE_ABBANDONATA,2->0
             * 9 NAVE_ABBANDONATA,	  2->0
             * 10 PIANETA,
             * 11 EPIDEMIA,			  1->0 (lvl.1 0->0)
             * 12 SABOTAGGIO
             */
        };
    }

    /**
     * metodo per rigenrerare il mazzo da 0
     *
     * @param lvl
     */
    public void RigeneraMazzo(int lvl) {

        AzzeraMazzo();
        generaConteggio();
        CreaMazzo(lvl);
    }
    
    /**
     * Metodo per eliminare l'array delle carte
     */
    private void AzzeraMazzo() {
		lista.clear();
    }
    
    /**
     * Metodo che in base al livello genera un numero specifico di carte e pure il numero di livello di 
     * ogni carta è in base al livello
     * 
     * @param lvl
     */
    public void CreaMazzo(int lvl) {

        switch (lvl) {
            case 1:
                for (int i = 0; i < 8; i++) {
                    lista.add(this.CreaCartaRandom(1));
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++) {
                    lista.add(this.CreaCartaRandom(1));
                }
                for (int i = 0; i < 8; i++) {
                    lista.add(this.CreaCartaRandom(2));
                }
                break;
            case 3:
                for (int i = 0; i < 4; i++) {
                    lista.add(this.CreaCartaRandom(1));
                }
                for (int i = 0; i < 4; i++) {
                    lista.add(this.CreaCartaRandom(2));
                }
                for (int i = 0; i < 8; i++) {
                    lista.add(this.CreaCartaRandom(3));
                }
                break;
            default:
                stampa.printError("ERROR: creazione mazzo (errorTipe: switch) (class: Carta)");
                break;
        }

        this.shiffleMazzo();
    }
    
    /**
     * Metodo che in base al livello della carta che viene richiesto crea il tipo di carta in maniera randomica
     * ma in maniera controllata in modo date la bilanciare il gioco e non esagera con la creazione di un tipo di carta
     * 
     * @param lvl
     * @return nuova carta
     */
    private Carta CreaCartaRandom(int lvl) {  // va nel mazzo

        int x = 0;
        Carta crt = null;

        do {
            Random random = new Random();

            if (lvl == 3) {
                x = random.nextInt(11) + 2;
            } else if (lvl == 2) {
                x = random.nextInt(11) + 1;
            } else {
                x = random.nextInt(10) + 1;
            }

        } while (conteggio[lvl - 1][x - 1] <= 0);

        conteggio[lvl - 1][x - 1]--;

        switch (x) {
            case 1: // POLVERE_STELLARE,
                crt = new PolvereStellare(lvl);
                break;
            case 2: // ZONA_GUERRA,
                crt = new ZonaGuerra(lvl);
                break;
            case 3: // PIOGGIA_METEORITI,
                crt = new PioggiaMeteoriti(lvl);
                break;
            case 4: // SPAZIO_APERTO,
                crt = new SpazioAperto(lvl);
                break;
            case 5: // SCHIAVISTI,
                crt = new Schiavisti(lvl);
                break;
            case 6: // CONTRABBANDIERI,
                crt = new Contrabbandieri(lvl);
                break;
            case 7: // PIRATI,
                crt = new Pirati(lvl);
                break;
            case 8: // STAZIONE_ABBANDONATA,
                crt = new StazioneAbbandonata(lvl);
                break;
            case 9: // NAVE_ABBANDONATA,
                crt = new NaveAbbandonata(lvl);
                break;
            case 10: // PIANETA,
                crt = new Pianeta(lvl);
                break;
            case 11: // EPIDEMIA,
                crt = new Epidemia(lvl);
                break;
            case 12: // SABOTAGGIO,
                crt = new Sabotaggio(lvl);
                break;
            default:
                stampa.printError("ERROR: creazione carta avventura del mazzo (errorTipe: switch) (class: Carta)");
                break;
        }

        return crt;
    }

    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < lista.size(); i++) {

            temp = temp + lista.get(i).toString() + "\n\n";

        }
        return temp;
    }

    public void shiffleMazzo() {

        Collections.shuffle(lista);
    }

    public ArrayList<Carta> getLista() {
        return lista;
    }
}
