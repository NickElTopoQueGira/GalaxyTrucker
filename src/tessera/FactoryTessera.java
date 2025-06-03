package tessera;

import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import java.util.Random;
import tessera.batteria.Batteria;
import tessera.cannone.Cannone;
import tessera.merce.Stiva;
import tessera.modulo_passeggeri.ModuloAttraccoAlieni;
import tessera.modulo_passeggeri.ModuloPasseggeri;
import tessera.motore.Motore;
import tessera.scudi.Scudi;
import tessera.tubi.Tubi;

public class FactoryTessera {

    private static int numeroTessere = 0;
    private static final int NUMERO_TESSERE_MAX = 252;
    private ComunicazioneConUtente stampa;

    public static int getNumeroTessere() {
        return numeroTessere;
    }

    public static int getNumeroTessereMax() {
        return NUMERO_TESSERE_MAX;
    }

    /**
     * metodo per la creazione di una tessera specifica random. Genera eccezione
     * se superato numero di tessere massimo per ogni tipo. (totale max=152
     * tessere)
     *
     * @return tessera specifica
     * @throws ErroreTessera
     */
    public Tessera estraiTipo() throws ErroreTessera {

        stampa = ComunicazioneConUtente.getIstanza();
        numeroTessere = numeroTessere + 1;
        TipoTessera tipo = randomTipo();
        try {
            switch (tipo) {
                case TipoTessera.PORTA_MERCI -> {
                    return new Stiva();
                }
                case TipoTessera.SCUDI -> {
                    return new Scudi();
                }
                case TipoTessera.TUBI -> {
                    return new Tubi();
                }
                case TipoTessera.MODULO_PASSEGGERI -> {
                    return new ModuloPasseggeri();
                }
                case TipoTessera.MODULO_ATTRACCO_ALIENI -> {
                    return new ModuloAttraccoAlieni();
                }
                case TipoTessera.BATTERIA -> {
                    return new Batteria();
                }
                case TipoTessera.CANNONE -> {
                    return new Cannone();
                }
                case TipoTessera.MOTORE -> {
                    return new Motore();
                }
                default -> {
                    return estraiTipo();
                }

            }

        } catch (ErroreTessera eT) {
            stampa.printError(eT.getMessage());
            numeroTessere = numeroTessere - 1;
            if (numeroTessere <= NUMERO_TESSERE_MAX) {
                return estraiTipo();
            } else {
                throw new ErroreTessera("Numero Elementi Tessera Max"); // Eccezione Numero Massimo di elementi
			}
        }
    }

    /**
     * viene fatta una random della lunghezza -2 per escludere il tipotessera
     * centro e tipotessera vuota
     *
     * @return
     */
    private TipoTessera randomTipo() {
        TipoTessera tipiTessera[] = TipoTessera.values();

        return tipiTessera[new Random().nextInt(tipiTessera.length - 2)];
    }
}
