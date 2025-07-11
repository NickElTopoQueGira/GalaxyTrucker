package tessera.modulo_passeggeri;

import eccezioniPersonalizzate.ErroreEquipaggio;
import eccezioniPersonalizzate.ErroreTessera;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TessereNormali;
import tessera.TipoTessera;

public class ModuloPasseggeri extends Tessera implements TessereNormali {

    private static final int MASSIMO = 17;
    private static int contatore = 0;

    private int numeroCosmonauti;
    private int numeroAlieniViola;
    private int numeroAlieniMarroni;
    private int equipaggio;
    private int equipaggioMax;

    private TipoModuloPasseggeri tipoModuloPasseggeri;

    /**
     * costruttore se viene ecceduto il numero massimo di elementi genera
     * eccezione
     *
     * @throws ErroreTessera
     */
    public ModuloPasseggeri() throws ErroreTessera {
        super(TipoTessera.MODULO_PASSEGGERI, Posizione.INTERNA);
        contatore++;
        if (contatore <= MASSIMO) {
            this.tipoModuloPasseggeri = TipoModuloPasseggeri.MODULO_EQUIPAGGIO;
            this.equipaggioMax = 2;
            this.numeroCosmonauti = 0;
            this.numeroAlieniMarroni = 0;
            this.numeroAlieniViola = 0;

            this.setEquipaggio();
        } else {
            throw new ErroreTessera("Numero Elementi Modulo Paseggeri Max"); // Eccezione Numero Massimo di elementi
        }
    }

    /**
     * set del tipoModulo Passeggeri con rispettivo settaggio dell'equipaggio
     *
     * @param nuovo tipoModuloPasseggeri
     */
    public void setTipoModuloPasseggeri(TipoModuloPasseggeri tipoModuloPasseggeri) {
        this.tipoModuloPasseggeri = tipoModuloPasseggeri;
        setEquipaggio();
    }

    /**
     * set equipaggio in base al tipomodulopasseggeri
     */
    private void setEquipaggio() {
        switch (this.tipoModuloPasseggeri) {
            case MODULO_ALIENO_MARRONE:
                this.equipaggioMax = 1;
                this.setNumeroAlieniMarroni(1);
                this.setNumeroAlieniViola(0);
                this.numeroCosmonauti = 0;

                break;
            case MODULO_ALIENO_VIOLA:
                this.equipaggioMax = 1;
                this.setNumeroAlieniMarroni(0);
                this.setNumeroAlieniViola(1);
                this.numeroCosmonauti = 0;

                break;
            case MODULO_EQUIPAGGIO:
                this.equipaggioMax = 2;
                this.setNumeroAlieniMarroni(0);
                this.setNumeroAlieniViola(0);

                try {
                    this.setNumeroCosmonauti(2);
                } catch (ErroreEquipaggio e) {

                }

            default:
                break;
        }
    }

    /**
     * getter del tipo di modulo passeggero (alieno marrone/viola, cosmonauti)
     * @return enum TipoModuloPasseggeri
     */
    public TipoModuloPasseggeri getTipoModuloPasseggeri() {
        return this.tipoModuloPasseggeri;
    }

    /**
     * getter numero di cosmonauti attualmente a bordo
     * @return numero di cosmonauti
     */
    public int getNumeroCosmonauti() {
        return this.numeroCosmonauti;
    }

    /**
     * Modifica numero di cosmonauti tramite edit (positivo o negativo), se non
     * è possibile settare numero cosmonauti genera eccezione
     *
     * @param edit
     * @throws ErroreEquipaggio
     */
    public void setNumeroCosmonauti(int edit) throws ErroreEquipaggio {
        // no interfaccia con centro perchè l'utente
        // può cambiare piu volte il numero passeggeri in fase di costruzione nave in base
        // a se
        // utilizza alieni o meno
        if ((this.numeroCosmonauti + edit) <= this.equipaggioMax && (this.numeroCosmonauti + edit) >= 0) {
            this.numeroCosmonauti = this.numeroCosmonauti + edit;
        } else {
            throw new ErroreEquipaggio("Azione non possibile");

        }

    }

    /**
     * getter numero di alieni marroni a bordo
     * @return numero di alieni marroni
     */
    public int getNumeroAlieniMarroni() {
        return this.numeroAlieniMarroni;
    }

    /**
     * getter numero di alieni viola a bordo
     * @return numero di alieni viola
     */
    public int getNumeroAlieniViola() {
        return this.numeroAlieniViola;
    }

    /**
     * setter numero di alieni marroni a bordo
     * @param nuovo numeroAlieniMarroni
     */
    public void setNumeroAlieniMarroni(int numeroAlieniMarroni) {
        this.numeroAlieniMarroni = numeroAlieniMarroni;
    }

    /**
     * setter numero di alieni viola a bordo
     * @param nuovo numeroAlieniViola
     */
    public void setNumeroAlieniViola(int numeroAlieniViola) {
        this.numeroAlieniViola = numeroAlieniViola;
    }

    /**
     * in base al tipo del modulo automaticamente va a togliere l'equipaggio
     * corretto dalla tessera
     */
    public void rimuoviEquipaggio() {

        switch (this.tipoModuloPasseggeri) {
            case MODULO_ALIENO_MARRONE:
                this.setNumeroAlieniViola(0);
                break;
            case MODULO_ALIENO_VIOLA:
                this.setNumeroAlieniMarroni(0);
                break;
            case MODULO_EQUIPAGGIO:
                try {
                    this.setNumeroCosmonauti(-1);
                } catch (ErroreEquipaggio e) {
                    e.printStackTrace();
                }
            default:
                break;
        }
    }

    /**
     * metodo per il calcolo dell'equipaggio totale (alieni + cosmonuati)
     *
     * @return numero passeggeri totale
     */
    public int getEquipaggio() {
        this.equipaggio = (this.numeroAlieniMarroni + this.numeroAlieniViola + this.numeroCosmonauti);

        return this.equipaggio;
    }

    /**
     * metodo che ritorna stringa descrittiva della tessera
     *
     * @return stringa descrittiva
     */
    @Override
    public String toLegenda() {
        String temp = "modulo equipaggio " + this.tipoModuloPasseggeri.toString() + " " + this.getEquipaggio() + "/" + this.equipaggioMax;
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
