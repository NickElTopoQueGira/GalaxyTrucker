package tabellone;

import eccezioniPersonalizzate.ErrorePedina;
import partita.Pedina;

public class Posizione {
    private int id;
    private boolean occupata;
    private Pedina pedinaGiocatore;

    public Posizione(int id){
        this.id = id;
        this.occupata = false;
        this.pedinaGiocatore = null;
    }

    public Posizione(int id, Pedina pedina){
        this.id = id; 
        this.pedinaGiocatore = pedina;
        this.occupata = false;
    }

    public void occupaPosizione(Pedina pedina) throws ErrorePedina{
        if(this.pedinaGiocatore != null){
            this.pedinaGiocatore = pedina;
            this.occupata = true;
        }
        else{
            throw new ErrorePedina("Posizione occupata");
        }
    }

    public void liberaPosizione(){
        this.pedinaGiocatore = null;
        this.occupata = false;
    }

    public boolean isLibera(){ return this.occupata; }

    public int getId() { return this.id; }
}
