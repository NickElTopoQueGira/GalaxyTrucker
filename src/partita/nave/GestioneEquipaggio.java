package partita.nave;

import eccezioniPersonalizzate.ErroreEquipaggio;
import gioco.ComunicazioneConUtente;
import partita.giocatore.Giocatore;
import tessera.Coordinate;
import tessera.Tessera;
import tessera.TipoTessera;
import tessera.modulo_passeggeri.ModuloPasseggeri;
import tessera.modulo_passeggeri.TipoModuloPasseggeri;

import java.util.ArrayList;

public class GestioneEquipaggio{
    private final ComunicazioneConUtente com = ComunicazioneConUtente.getIstanza();
    private final Giocatore giocatore;

    public GestioneEquipaggio(Giocatore giocatore){
        this.giocatore = giocatore;
    }

    /**
     * Modulo per aggiungere l'equipaggio alla nave
     */
    public void aggiungiEquipaggio(){
        this.com.println("Il giocatore: " + this.giocatore.getNome() + " deve imbarcare l'equipaggio");
        this.com.println("SU OGNI NAVE CI PUO' STARE MASSIMO UN ALIENO PER TIPO");
        riepilogoEquipaggio();

        // di default carico tutti i moduli equipaggio con 2 cosmonauti
        impostaEquipaggioUmano();

        // conto quanti moduli alieni ci sono
        int moduliAlieniMarroneValidi = numeroModuliAlienoMarroniValidi();
        int moduliAlieniViolaValidi = numeroModuliAlienoViolaValidi();

        // alieni marroni
        if(moduliAlieniMarroneValidi > 0){
            this.com.println("Hai a disposizione: " + moduliAlieniMarroneValidi + " moduli per l'alieno marrone validi");
            this.com.println("Vuoi aggiungere un alieno marrone alla tua nave? ");
            if(this.com.conferma()){
                // inserisci alieno
                inserisciAlieno(1);
            }else{
                this.com.println("La tua attraversata intergalattica iniziera' senza alieni marroni");
            }
        }else{
            this.com.println("Non hai moduli validi per ospitare l'alieno marrone");
        }

        // alieni viola
        if(moduliAlieniViolaValidi > 0){
            this.com.println("Hai a disposizione: " + moduliAlieniViolaValidi + " moduli per l'alieno viola validi");
            this.com.println("Vuoi aggiungere un alieno viola alla tua nave? ");
            if(this.com.conferma()){
                inserisciAlieno(2);
            }else{
                this.com.println("La tua attraversata intergalattica iniziera' senza alieni viola");
            }
        }else{
            this.com.println("Non hai moduli validi per ospitare l'alieno viola");
        }

        // riepilogo equipaggio
        riepilogoEquipaggio();
    }

    /**
     * Metodo per inserire gli alieni all'interno della nave
     * @param id int
     *           1 -> alieno marrone
     *           2 -> alieno viola
     */
    private void inserisciAlieno(int id){
        Coordinate coordinateTesseraAlieno = richiestaCoordinateModuloAlieno();
        Tessera tesseraAlienoSelezionata = this.giocatore.getNave().getTessera(coordinateTesseraAlieno);

        Tessera tesseraEquipaggioAdiacente = null;

        // verifico se la tessera e' di tipo alieno
        if(tesseraAlienoSelezionata.getTipoTessera() == TipoTessera.MODULO_ATTRACCO_ALIENI){
            // verifico se e' valida
            if(verificaTesseraAdiacente(tesseraAlienoSelezionata)){
                tesseraEquipaggioAdiacente = tesseraEquipaggioAdiacente(tesseraAlienoSelezionata);
                if(null == tesseraEquipaggioAdiacente){
                    this.com.printError("Impossibile trovare un modulo equipaggio adiacente alla tessera alieno selezionata");
                    inserisciAlieno(id);
                }
            }else{
                this.com.printError("La tessera selezionata non abilita al trasporto degli alieni");
                inserisciAlieno(id);
            }
        }else{
            this.com.printError("La tessera selezionata non e' una tessera alieno");
            inserisciAlieno(id);
        }


        switch(id){
            // alieni marroni
            case 1 ->{
                try{
                    ModuloPasseggeri moduloPasseggeri = ((ModuloPasseggeri)tesseraEquipaggioAdiacente);
                    if(null != moduloPasseggeri){
                        moduloPasseggeri.setNumeroCosmonauti(-2);
                        moduloPasseggeri.setNumeroAlieniMarroni(1);
                    }else{
                        this.com.printError("Errore modulo passeggeri, riprovare");
                        inserisciAlieno(id);
                    }
                }catch(ErroreEquipaggio eE){
                    this.com.printError(eE.getMessage());
                }
            }
            // alieni viola
            case 2->{
                try{
                    ModuloPasseggeri moduloPasseggeri = ((ModuloPasseggeri)tesseraEquipaggioAdiacente);
                    if(null != moduloPasseggeri){
                        moduloPasseggeri.setNumeroCosmonauti(-2);
                        moduloPasseggeri.setNumeroAlieniViola(1);
                    }else{
                        this.com.printError("Errore modulo passeggeri, riprovare");
                        inserisciAlieno(id);
                    }
                }catch(ErroreEquipaggio eE){
                    this.com.printError(eE.getMessage());
                }
            }
        }
    }

    /**
     * Metodo per aggiungere l'equipaggio di default nella nave
     */
    private void impostaEquipaggioUmano(){
        for(ArrayList<Tessera> riga : this.giocatore.getNave().getPlanciaDellaNave()){
            for(Tessera tessera : riga){
                if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                    try{
                        if(((ModuloPasseggeri) tessera).getTipoModuloPasseggeri() == TipoModuloPasseggeri.MODULO_EQUIPAGGIO){
                            ((ModuloPasseggeri) tessera).setNumeroCosmonauti(2);
                        }
                    }catch(ErroreEquipaggio er){
                        this.com.printError(er.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Metodo per contare il numero di tessere adibite per il trasporto agli alieni marroni
     * @return numero (int)
    */
    private int numeroModuliAlienoMarroniValidi(){
        int numero = 0;
        for(ArrayList<Tessera> riga : this.giocatore.getNave().getPlanciaDellaNave()){
            for(Tessera tessera : riga){
                if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                    if(((ModuloPasseggeri)tessera).getTipoModuloPasseggeri() == TipoModuloPasseggeri.MODULO_ALIENO_MARRONE){
                        // verifica della tessera adiacente
                        // ricerco di una tessera x umani
                        if(verificaTesseraAdiacente(tessera))
                            numero += 1;
                    }
                }
            }
        }

        return numero;
    }

    /**
     * Metodo per contare il numero di tessere adibite per il trasporto agli alieni viola
     * @return numero (int)
     */
    private int numeroModuliAlienoViolaValidi(){
        int numero = 0;
        for(ArrayList<Tessera> riga : this.giocatore.getNave().getPlanciaDellaNave()){
            for(Tessera tessera : riga){
                if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                    if(((ModuloPasseggeri)tessera).getTipoModuloPasseggeri() == TipoModuloPasseggeri.MODULO_ALIENO_VIOLA){
                        // verifica della tessera adiacente
                        // ricerco di una tessera x umani
                        if(verificaTesseraAdiacente(tessera))
                            numero += 1;
                    }
                }
            }
        }

        return numero;
    }

    /**
     * Metodo per verificare se la tessera adiacente e' del tipo
     * TipoTessera.MODULO_PASSEGGERI
     */
    private boolean verificaTesseraAdiacente(Tessera tessera){
        Tessera tesseraAdiacente;
        Coordinate coordinateTesseraAdiacente = new Coordinate();

        // sopra
        coordinateTesseraAdiacente.setX(tessera.getCoordinate().getX());
        coordinateTesseraAdiacente.setY(tessera.getCoordinate().getY() - 1);
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return true;
            }
        }

        // sotto
        coordinateTesseraAdiacente.setX(tessera.getCoordinate().getX());
        coordinateTesseraAdiacente.setY(tessera.getCoordinate().getY() + 1);
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return true;
            }
        }

        // destra
        coordinateTesseraAdiacente.setX(tessera.getCoordinate().getX() + 1);
        coordinateTesseraAdiacente.setY(tessera.getCoordinate().getY());
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return true;
            }
        }

        // sinistra
        coordinateTesseraAdiacente.setX(tessera.getCoordinate().getX() - 1);
        coordinateTesseraAdiacente.setY(tessera.getCoordinate().getY());
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return true;
            }
        }

        return false;
    }

    /**
     * Metodo per recuperare la tessera equipaggio adiacente a quella dell'alieno
     * @param tesseraAlieno Tessera
     * @return tessera equipaggio
     */
    private Tessera tesseraEquipaggioAdiacente(Tessera tesseraAlieno){
        Tessera tesseraAdiacente;
        Coordinate coordinateTesseraAdiacente = new Coordinate();

        // sopra
        coordinateTesseraAdiacente.setX(tesseraAlieno.getCoordinate().getX());
        coordinateTesseraAdiacente.setY(tesseraAlieno.getCoordinate().getY() - 1);
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return tesseraAdiacente;
            }
        }

        // sotto
        coordinateTesseraAdiacente.setX(tesseraAlieno.getCoordinate().getX());
        coordinateTesseraAdiacente.setY(tesseraAlieno.getCoordinate().getY() + 1);
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return tesseraAdiacente;
            }
        }

        // destra
        coordinateTesseraAdiacente.setX(tesseraAlieno.getCoordinate().getX() + 1);
        coordinateTesseraAdiacente.setY(tesseraAlieno.getCoordinate().getY());
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return tesseraAdiacente;
            }
        }

        // sinistra
        coordinateTesseraAdiacente.setX(tesseraAlieno.getCoordinate().getX() - 1);
        coordinateTesseraAdiacente.setY(tesseraAlieno.getCoordinate().getY());
        if(this.giocatore.getNave().controllaCoordinate(coordinateTesseraAdiacente)){
            tesseraAdiacente = this.giocatore.getNave().getTessera(coordinateTesseraAdiacente);
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                return tesseraAdiacente;
            }
        }

        return null;
    }

    /**
     * Metodo per chiedere all'utente su quale modulo vuole mettere l'alieno
     */
    private Coordinate richiestaCoordinateModuloAlieno(){
        Coordinate coordinate = new Coordinate();
        boolean pass = false;
        visualizzaNave();
        do{
            this.com.println("Inserisci le coordinate del modulo alieno: ");
            this.com.println("Inserisci coordinata x: ");
            coordinate.setX(this.com.consoleReadInt());
            this.com.println("Inserisci coordinata y: ");
            coordinate.setY(this.com.consoleReadInt());
            if(false == this.giocatore.getNave().controllaCoordinate(coordinate)){
                this.com.erroreImmissioneValore();
            }else{
                pass = true;
            }
        }while(!pass);

        return coordinate;
    }

    /**
     * Metodo per riepilogare l'equipaggio presente sulla nave
     */
    private void riepilogoEquipaggio(){
        this.com.println("Equipaggio attualmente presente sulla nave: ");
        this.com.println("Numero cosmonauti: " + giocatore.getNave().getCosmonauti());
        this.com.println("Numero alieni marroni: " + giocatore.getNave().getAlieniMarrone());
        this.com.println("Numero alieni viola: " + giocatore.getNave().getAlieniViola());

        this.com.println("\npremi invio per continuare...");
        this.com.consoleRead();
        this.com.clear();
    }

    /**
     * Metodo per visualizzare la nave
     */
    private void visualizzaNave(){
        this.com.println(this.giocatore.getNave().toString());
    }
}
