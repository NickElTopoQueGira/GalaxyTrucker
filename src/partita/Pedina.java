package partita;

import partita.giocatore.Colori;

public class Pedina{
    private Colori colorePedina;
    private int posizioneSulTabellone;
    
    public Pedina(Colori colorePedina){
        this.colorePedina = colorePedina;
        this.posizioneSulTabellone = 0;
    }

    public Colori getColorePedina() { 
        return this.getColorePedina(); 
    }

    public void setPosizioneSulTabellone(int posizioneSulTabellone){
        this.posizioneSulTabellone = posizioneSulTabellone;
    }

    public int getPosizioneSulTabellone(){ 
        return this.posizioneSulTabellone; 
    }
}
