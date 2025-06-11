package tabellone;

import partita.Pedina;

public class Posizione {
    private final int id;
    private boolean libera;
    private Pedina pedinaGiocatore;

    /**
     * Costruttore dell'oggetto posizione
     * Questo oggetto e' l'astrazione delle caselle
     * dei classici tabelloni da gioco.
     *
     * @param id int numero della posizione
     * */
    public Posizione(int id){
        this.id = id;
        this.libera = true;
        this.pedinaGiocatore = null;
    }

    /**
     * Costruttore per creare una posizione gia' occupata da una pedina
     *
     * @param id int numero della posizione
     * @param pedina Pedina che occupa la posizione
     * */
    public Posizione(int id, Pedina pedina){
        this.id = id; 
        this.pedinaGiocatore = pedina;
        this.libera = false;
    }

    /**
     * Metodo per occupare la posizione
     *
     * @param pedina Pedina che occupa la posizione
     * */
    public void occupaPosizione(Pedina pedina){
        if(this.pedinaGiocatore == null){
            this.pedinaGiocatore = pedina;
            this.libera = false;
        }
    }

    /**
     * Metodo per liberare la posizione
     * */
    public void liberaPosizione(){
        this.pedinaGiocatore = null;
        this.libera = true;
    }

    /**
     * Metodo per sapere se la posione e' libera
     *
     * @return status della posizione
     *          true -> libera
     *          false -> occupata
     * */
    public boolean isLibera(){ return this.libera; }

    /**
     * Metodo per sapere l'id della posizione
     * */
    public int getId() { return this.id; }

    /**
     * Metodo per acquisire il giocatore attualmente presente sulla posizione
     *
     * @return Giocatore attualmente presente sulla posizione
     *          se viene ritornato null, non c'e' nessun giocatore ad'occupare la posizione
     * */
    public Pedina getPedina(){ return  this.pedinaGiocatore; }
}
