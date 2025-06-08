package tessera.cannone;

import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import java.util.Random;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TessereNormali;
import tessera.TipoConnettoriTessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Cannone extends Tessera implements TessereNormali {

    private static final int MASSIMO = 36;
    private int rprec = 0;
    private int cprec = 2;
    private static int contatore = 0;
    private final TipoCannone tipoCannone;
    private TipoLato latoCannone;
    private String tempStampaCasella;
    private final static String CANNA = "\033[0;31m" + "!" + "\033[0m";

    /**
     * costruttore
     * se viene ecceduto il numero massimo di elementi genera
     * eccezione. verifica in maniera ricorsiva nel caso il lato cannone (di
     * default up; sovrascirve nella matrice di tessera il carattere "!")
     * settato come Nullo, non corrisponda con l'unico lato connettore presente
     * originariamente diverso da Nullo
     *
     * @throws ErroreTessera
     */
    public Cannone() throws ErroreTessera {
        super(TipoTessera.CANNONE, Posizione.INTERNA);
        contatore++;
        if (contatore <= MASSIMO) {
        	
            this.tipoCannone = randomTipo();
            this.latoCannone = TipoLato.UP;
            this.tempStampaCasella = super.tessera_Disposizione[rprec][cprec];
            super.tessera_Disposizione[rprec][cprec] = CANNA;
            this.latiTessera.setUp(TipoConnettoriTessera.NULLO);
            while (!this.latiTessera.verificaTessera()) {
                this.latiTessera.GeneraLatiTessera();
                this.latiTessera.setUp(TipoConnettoriTessera.NULLO);
            }

        } else {
            throw new ErroreTessera("Numero Elementi Cannone Max"); // Eccezione Numero Massimo di elementi
        }

    }

    public TipoCannone getTipoCannone() {
        return this.tipoCannone;
    }

    public TipoLato getLatoCannone() {
        return this.latoCannone;
    }

    /**
     * random di enum TipoCannone
     *
     * @return enum di TipoCannone (doppio o singolo)
     */
    private TipoCannone randomTipo() {
        TipoCannone[] tipiCannone = TipoCannone.values();
        return tipiCannone[new Random().nextInt(tipiCannone.length)];
    }

    /**
     * calcola valore del cannone in relazione tipoCannone ed alla direzione
     *
     * @return float del valore del cannone
     */
    public float calcolaValore() {
        float valore = 0;
        int tipo = 1;
        if (this.tipoCannone == TipoCannone.DOPPIO) {
            tipo = 2;
        }
        switch (this.latoCannone) {
            case UP -> {
                valore++;
            }
            case RIGHT -> {
                valore = (float) (valore + (tipo / 2.0));
            }
            case LEFT -> {
                valore = (float) (valore + (tipo / 2.0));
            }
            case DOWN -> {
                valore = (float) (valore + (tipo / 2.0));
            }
            default -> {
            }

        }

        return valore;

    }

    /**
     * ruota canna del cannone di 90 gradi in senso orario e sovrascrive nella
     * matrice di tessera il carattere "!"
     */
    @Override
    public void ruota() throws ErroreRotazione {
        super.ruota();
        this.latoCannone = this.latoCannone.next();
        super.tessera_Disposizione[rprec][cprec] = this.tempStampaCasella;

        switch (this.latoCannone) {
            case UP -> {
                this.rprec = 0;
                this.cprec = 2;
            }
            case RIGHT -> {
                this.rprec = 2;
                this.cprec = 4;
            }
            case DOWN -> {
                this.rprec = 4;
                this.cprec = 2;
            }
            case LEFT -> {
                this.rprec = 2;
                this.cprec = 0;
            }
            default ->
                throw new ErroreRotazione("Errore: Rotazione");

        }

        this.tempStampaCasella = super.tessera_Disposizione[rprec][cprec];
        super.tessera_Disposizione[rprec][cprec] = CANNA;
    }

    /**
     * metodo che ritorna stringa descrittiva della tessera
     *
     * @return stringa descrittiva
     */
    @Override
    public String toLegenda() {
        String temp = "modulo cannone " + this.tipoCannone.toString();
        return temp;
    }

    /**
     * decrementa contatore di 1
     */
    @Override
    public void decrementaNumeroCorrente() {
        contatore = contatore - 1;

    }
}
