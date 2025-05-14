package tabellone;

import partita.Pedina;

public class Posizione {
    private int id;
    private boolean libera;
    private Pedina pedinaGiocatore;

    public Posizione(int id){
        this.id = id;
        this.libera = true;
        this.pedinaGiocatore = null;
    }

    public Posizione(int id, Pedina pedina){
        this.id = id; 
        this.pedinaGiocatore = pedina;
        this.libera = false;
    }

    public void occupaPosizione(Pedina pedina){
        if(this.pedinaGiocatore != null){
            this.pedinaGiocatore = pedina;
            this.libera = false;
        }
    }

    public void liberaPosizione(){
        this.pedinaGiocatore = null;
        this.libera = true;
    }

    public boolean isLibera(){ return this.libera; }

    public int getId() { return this.id; }
}
