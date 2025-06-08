package tessera.scudi;

import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TessereNormali;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Scudi extends Tessera implements TessereNormali {

    private static final int MASSIMO = 8;
    private int rprec1 = 0;
    private int cprec1 = 2;
    private int rprec2 = 2;
    private int cprec2 = 4;
    private static int contatore = 0;

    private TipoLato latoScudo1;
    private TipoLato latoScudo2;
    private String tempStampaCasella1;
    private String tempStampaCasella2;

    /**
     * costruttore
     * se viene ecceduto il numero massimo di elementi genera
     * eccezione. sovrascrive in matrice di tessera il carattere "@" per
     * indicare gli scudi di deafult poszionati in up e right.
     *
     * @throws ErroreTessera
     */
    public Scudi() throws ErroreTessera {
        super(TipoTessera.SCUDI, Posizione.INTERNA);
        contatore++;
        if (contatore > MASSIMO) {
            throw new ErroreTessera("Numero Elementi Scudi Max"); // Eccezione Numero Massimo di elementi
        }
        this.latoScudo1 = TipoLato.UP;
        this.latoScudo2 = TipoLato.RIGHT;
        this.tempStampaCasella2 = super.tessera_Disposizione[rprec2][cprec2];
        this.tempStampaCasella1 = super.tessera_Disposizione[rprec1][cprec1];
        super.tessera_Disposizione[rprec1][cprec1] = "@";
        super.tessera_Disposizione[rprec2][cprec2] = "@";
        
    }

    /**
     * ruota gli scudi in matrice tessera ed in caso genera eccezione
     */
    @Override
    public void ruota() throws ErroreRotazione {
        super.ruota();
        this.latoScudo1 = this.latoScudo1.next();
        this.latoScudo2 = this.latoScudo2.next();

        super.tessera_Disposizione[rprec2][cprec2] = this.tempStampaCasella2;
        super.tessera_Disposizione[rprec1][cprec1] = this.tempStampaCasella1;

        switch (this.latoScudo1) {
            case UP -> {
                this.rprec1 = 0;
                this.cprec1 = 2;
                this.rprec2 = 2;
                this.cprec2 = 4;
            }
            case RIGHT -> {
                this.rprec1 = 2;
                this.cprec1 = 4;
                this.rprec2 = 4;
                this.cprec2 = 2;
            }
            case DOWN -> {
                this.rprec1 = 4;
                this.cprec1 = 2;
                this.rprec2 = 2;
                this.cprec2 = 0;
            }
            case LEFT -> {
                this.rprec1 = 2;
                this.cprec1 = 0;
                this.rprec2 = 0;
                this.cprec2 = 2;
            }
            default ->
                throw new ErroreRotazione("Errore Rotazione Scudi");
        }

        this.tempStampaCasella2 = super.tessera_Disposizione[rprec2][cprec2];
        this.tempStampaCasella1 = super.tessera_Disposizione[rprec1][cprec1];
        super.tessera_Disposizione[rprec1][cprec1] = "@";
        super.tessera_Disposizione[rprec2][cprec2] = "@";

    }

    /**
     * metodo che ritorna stringa descrittiva della tessera
     *
     * @return stringa descrittiva
     */
    @Override
    public String toLegenda() {
        String temp = "modulo scudo";
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
