package partita;

import java.util.List;


import eccezioniPersonalizzate.*;
import gioco.ComunicazioneConUtente;
import partita.giocatore.*;
import tessera.Coordinate;
import tessera.merce.Merce;

public class Pedina{
    private Colori colorePedina;
    private Giocatore giocatore;
    private int posizioneSulTabellone;
    private ComunicazioneConUtente cns;
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
    /**
     * LE prossime due funzioni sono equvalenti una per i scudi e una per il cannone doppio
     * 
     * @return false se: nave non ha abbastanza energia / giocatore sceglie di non utilizzare la tessera (scudo o cannone doppio)
     * 
     * @return true giocatore sceglie di utilizzare la tessera (scudo o cannone doppio) e perde 1 di energia
     */
    public Boolean sceltaEpossibilitaUtilizzoScudi() {
    	
    	if(this.giocatore.getNave().getEnergiaResidua() > 0) {
    		
    		cns.print("Hai abbastanza energia, vuoi utilizzare lo scudo?");
    		
    		if(cns.conferma()) {
    			
    			cns.println("Scudo utilizzato");
    			
    			return true;
    		}else {
    			
    			cns.println("Scudo non utilizzato");
    			
    			return false;
    		}
    		
    	}else {
    		
    		cns.println("Non hai abbastanza enegia per utilizzare lo scudo");
    		return false;
    	}
    }
    public Boolean sceltaEpossibilitaUtilizzoCannoneDoppio() {
    	
    	if(this.giocatore.getNave().getEnergiaResidua() > 0) {
    		
    		cns.print("Hai abbastanza energia, vuoi utilizzare il cannone doppio?");
    		
    		if(cns.conferma()) {
    			
    			cns.println("Cannone doppio utilizzato");
    			
    			return true;
    		}else {
    			
    			cns.println("Cannone doppio non utilizzato");
    			
    			return false;
    		}
    		
    	}else {
    		
    		cns.println("Non hai abbastanza enegia per utilizzare il cannone doppio");
    		return false;
    	}
    }
    /**
     * LE prossime due funzioni sono equvalenti una per ricevere crediti in cambio di giorni e laltra per le merci
     * 
     * @return false se si sceglie di non accettare le merci o i crediti in cambio dei giorni 
     * 
     * @return true se si sceglie di accettare le merci o i crediti in cambio dei giorni 
     */
    public Boolean sceltaScambioMerciConGiorni(int giorniPersi, List<Merce> merci) {
    	
    	cns.println("Vuoi perdere "+giorniPersi+" giorni di viaggio per le seguenti merci?");
    	for(int i=0; i<merci.size(); i++) {
    		
    		cns.println(i+") "+merci.get(i).getTipoMerce());
    	}
    		
    	if(cns.conferma()) {
    			
    		cns.println("Hai ricevuto le merci ma perso "+giorniPersi+" giorni di viaggio");
    			
    		return true;
    	}else {
    			
    		cns.println("Non hai ricevuto nessuna merce e non hai perso giorni di viaggio");
    			
    		return false;
    	}
    }
    // isGiorni: 1) si scambiano giorni con crediti
    //           0) si scambiano equipaggio con crediti
    public Boolean sceltaScambioCreditiConGiorni(int giorniPersi, int crediti, int equipaggio) {
    	
		if(equipaggio == 0) {
			cns.println("Vuoi perdere "+giorniPersi+" giorni di viaggio  per "+crediti+"\u00A2 (crediti)?");
		}else {
			cns.println("Vuoi perdere "+giorniPersi+" giorni di viaggio e "+giorniPersi+" mebri dell'equipaggio per "+crediti+"\u00A2 (crediti)?");
		}
    	
    	if(cns.conferma()) {
    		
    		if(equipaggio == 0) {
        		cns.println("Hai ricevuto "+crediti+"\u00A2 (crediti) ma perso "+giorniPersi+" giorni di viaggio");
    		}else {
    			cns.println("Hai ricevuto "+crediti+"\u00A2 (crediti) ma perso "+giorniPersi+" giorni di viaggio e "+equipaggio+" mebri dell'equipaggio");
    		}
    		return true;
    	}else {
    		
    		if(equipaggio == 0) {
        		cns.println("Non hai ricevuto nessun credito e non hai perso giorni di viaggio");
    		}else {
    			cns.println("Non hai ricevuto nessun credito e non hai perso ne mebri dell'equipaggio ne giorni di viaggio");
    		}
    			
    		return false;
    	}
    }
    
    //NICK QUA FAMMI UNA ROBA TIPO: 
    /*
     * public int getPosizioneSulTabelloneTotale (){ 
     *    return THIS.posizioneSulTabellone + (numeroPosizioniTabellone * numeroGiri) ; 
     * }
     */
    
}
