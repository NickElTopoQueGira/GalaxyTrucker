package tessera.tubi;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TessereNormali;
import tessera.TipoConnettoriTessera;
import tessera.TipoTessera;

public class Tubi extends Tessera implements TessereNormali {

    private static final int MASSIMO = 8;
    private static int contatore = 0;

    /**
     * costruttore
     * se viene ecceduto il numero massimo di elementi genera
     * eccezione. verifica ricorsivamente che non ci siano connettori di tipo Nullo,
     * in caso rigenera connettori.
     *
     * @throws ErroreTessera
     */
    public Tubi() throws ErroreTessera {
        super(TipoTessera.TUBI, Posizione.INTERNA);
        contatore++;
        if (contatore > MASSIMO) {
            throw new ErroreTessera("Numero Elementi Tubi Max"); // Eccezione Numero Massimo di elementi
        }
        
        boolean condizione = true;
        while (condizione) {
            if (this.latiTessera.getUp() == TipoConnettoriTessera.NULLO
                    || this.latiTessera.getRight() == TipoConnettoriTessera.NULLO
                    || this.latiTessera.getDown() == TipoConnettoriTessera.NULLO
                    || this.latiTessera.getLeft() == TipoConnettoriTessera.NULLO) {

                this.latiTessera.GeneraLatiTessera();
                
            } else {
                condizione = false;
            }
        }

    }

    /**
     * metodo che ritorna stringa descrittiva della tessera
     *
     * @return stringa descrittiva
     */
    @Override
    public String toLegenda() {
        String temp = "modulo tubi";
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
