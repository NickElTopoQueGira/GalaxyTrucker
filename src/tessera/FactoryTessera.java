package tessera;

import eccezioniPersonalizzate.ErroreAggiuntaTessera;
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
    private static final int NUMERO_TESSERE_MAX = 152;
    private ComunicazioneConUtente stampa;

    /**
     * getter numero delle tessere generate
     * @return numero tessere
     */
    public static int getNumeroTessere() {
        return numeroTessere;
    }

    /**
     * getter numero massimo di tessere generabili
     * @return numero massimo di tessere generabili
     */
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
                    Tessera t = new Stiva();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((Stiva) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                case TipoTessera.SCUDI -> {
                    Tessera t = new Scudi();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((Scudi) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                case TipoTessera.TUBI -> {
                    Tessera t = new Tubi();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((Tubi) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                case TipoTessera.MODULO_PASSEGGERI -> {
                    Tessera t = new ModuloPasseggeri();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((ModuloPasseggeri) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                case TipoTessera.MODULO_ATTRACCO_ALIENI -> {
                    Tessera t = new ModuloAttraccoAlieni();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((ModuloAttraccoAlieni) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                case TipoTessera.BATTERIA -> {
                    Tessera t = new Batteria();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((Batteria) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                case TipoTessera.CANNONE -> {
                    Tessera t = new Cannone();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((Cannone) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                case TipoTessera.MOTORE -> {
                    Tessera t = new Motore();
                    try {
                        t.aggiungiTessera();
                    } catch (ErroreAggiuntaTessera e) {
                        ((Motore) t).decrementaNumeroCorrente();

                    }
                    return t;
                }
                default -> {
                    return estraiTipo();

                }

            }

        } catch (ErroreTessera eT) {
            stampa.printError(eT.getMessage());
            numeroTessere = numeroTessere - 1;
            if (numeroTessere < NUMERO_TESSERE_MAX) {
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
