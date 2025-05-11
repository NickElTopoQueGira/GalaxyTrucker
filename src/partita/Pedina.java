package partita;

import java.util.List;


import eccezioniPersonalizzate.*;
import partita.giocatore.*;
import tessera.Coordinate;
import tessera.merce.Merce;

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
    
    
    
    public void selezionaEquipaggioDaEliminare(int elimEquipaggio) {  ///TODO
    	
    	for(int i=0; i<elimEquipaggio; i++) {
    		
    	}
    }
    public void selezionaMerceDaEliminare(int elimMerce) {///TODO
    	
    	for(int i=0; i<elimMerce; i++) {
    		
    	}
    }
    public void distribuzioneMerce(List<Merce> merci) {///TODO
    	Coordinate coordinate = null;
    	for(int i=0; i<merci.size(); i++) {
    		
    		//TODO il giocatore deve vedere tutte le merci a disposizione 
    		//TODO viene posta la decisione al giocatore se vuole piazzare la merce o no quindi "buttarla"
    		//TODO il giocatore sceglie il in quale possivile stiva posizionare la merce in questione
    		
    		try {
				this.giocatore.getNave().inserisciMerce(coordinate, merci.get(i));
			} catch (ErroreCoordinate e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErroreRisorse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErroreTessera e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    public Boolean sceltaEpossibilitaUtilizzoScudi() {
    	
    	
    	// si prenderà funzione 
    	
    	return false;
    }
    
    //NICK QUA FAMMI UNA ROBA TIPO: 
    /*
     * public int getPosizioneSulTabelloneTotale (){ 
     *    return THIS.posizioneSulTabellone + (numeroPosizioniTabellone * numeroGiri) ; 
     * }
     */
    
}
