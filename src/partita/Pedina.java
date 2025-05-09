package partita;

import partita.giocatore.*;

public class Pedina{
    private Colori colorePedina;
    private Giocatore giocatore;
    private int posizioneSulTabellone;
    private int numeroGiro;  // DA USARE
    
    public Pedina(Colori colorePedina){
        this.colorePedina = colorePedina;
        this.posizioneSulTabellone = 0;
        this.numeroGiro = 1;
    }
    
   public void controlloPosizioneSulTabellone() {
	 /*
	  * NICK QUA FAMMI UNA ROBA TIPO: if(this.posizioneSulTabellone > numeroPosizioniTabellone) {
	  * 									this.posizioneSulTabellone = this.posizioneSulTabellone - numeroPosizioniTabellone; 
	  * 									numeroGiri++;
	  * 								}
	  */
																						
   }

    public Colori getColorePedina() { 
        return this.colorePedina; 
    }

    public void muoviPedina(int posizioni){
        this.posizioneSulTabellone += posizioni;
    }

    public void setPosizioneSulTabellone(int posizioneSulTabellone){
        this.posizioneSulTabellone = posizioneSulTabellone;
    }

    public int getPosizioneSulTabellone(){ 
        return this.posizioneSulTabellone; 
    }
    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public Giocatore getGiocatore() {
        return this.giocatore;
    }
    		
    
    //NICK QUA FAMMI UNA ROBA TIPO: 
    /*
     * public int getPosizioneSulTabelloneTotale (){ 
     *    return THIS.posizioneSulTabellone + (numeroPosizioniTabellone * numeroGiri) ; 
     * }
     */
    
}
