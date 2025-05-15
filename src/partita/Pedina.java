package partita;

import java.util.*;
import eccezioniPersonalizzate.*;
import gioco.ComunicazioneConUtente;
import partita.giocatore.*;
import tessera.*;
import tessera.merce.Merce;
import tessera.merce.Stiva;
import tessera.merce.TipoMerce;
import tessera.modulo_passeggeri.ModuloPasseggeri;

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
    /**
     * funzione che in base al numero di equipaggio che devono venir tolti, 
     * ti mostra tutti i moduli da cui si può rimuovere un componente dell'equipaggio, 
     * e specifica se alieno o no, e ti fa scegliere da dove toglierlo
     * 
     * @param elimEquipaggio int numero di equipaggio che verrà tolto
     */
    public void selezionaEquipaggioDaEliminare(int elimEquipaggio) {
    	int caso;
    	do {
    		caso=0;
    		ArrayList<Coordinate> crd = new ArrayList();
    		
    		for(int x=0; x<this.giocatore.getNave().getPlanciaDellaNave().size(); x++) {
    			
    			for(int y=0; y<this.giocatore.getNave().getPlanciaDellaNave().get(x).size(); y++) {
    				
    				Tessera tessera = this.giocatore.getNave().getPlanciaDellaNave().get(x).get(y);
    				TipoTessera tipo = tessera.getTipoTessera();

    				if (tipo == TipoTessera.MODULO_PASSEGGERI) {
    				    if (((ModuloPasseggeri) tessera).getNumeroCosmonauti() > 0) {
    				        
    				    	caso++;
    				    	
    				    	cns.println(""+caso+") MODULO PASSEGGERI in posizione ("+x+";"+y+") e contiente "
    				    				+specificaEquipaggio(((ModuloPasseggeri) tessera)));
    				    	
    				    	crd.add(new Coordinate(x, y));
    				    }
    				} else if (tipo == TipoTessera.CENTRO) {
    				    if (((Centro) tessera).getPasseggeriCorrenti() > 0) {

    				    	caso++;
    				    	
    				    	cns.println(""+caso+") CENTRO in posizione ("+x+";"+y+") e contiente "
    				    				+((Centro) tessera).getPasseggeriCorrenti()+" cosmonauti");
    				    	
    				    	crd.add(new Coordinate(x, y));
    				    }
    				}
    			}
    		}
    		int sceltaModulo;
    		cns.println("Inserire il numero del Modulo da cui togliere 1 componentye dell'equipaggio");
			
    		do {
    			sceltaModulo = Integer.parseInt(cns.consoleRead());
    			
    			if(sceltaModulo<=0 && sceltaModulo>caso) {
    				cns.println("VALORE IMMESSO NON VALIDO");
    			}
    			
    		}while(sceltaModulo<=0 || sceltaModulo>caso);
    		
    		if(this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaModulo-1).getX()).get(crd.get(sceltaModulo-1).getY()).getTipoTessera() 
    				== TipoTessera.MODULO_PASSEGGERI) {
    			
    			((ModuloPasseggeri)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaModulo-1).getX()).get(crd.get(sceltaModulo-1).getY())).rimuoviEquipaggio();
    		}else {//TODO rifare togli equipaggio
    			
    			((Centro)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaModulo-1).getX()).get(crd.get(sceltaModulo-1).getY())).rimuoviPasseggeri(-1);
    		}
    		
    		elimEquipaggio--;
    	}while(elimEquipaggio > 0 && this.giocatore.getNave().getEquipaggio() > 0);
    }
    private String specificaEquipaggio(ModuloPasseggeri mp) {
    	
    	String txt = null;
    	
    	switch(mp.getTipoModuloPasseggeri()) {
    	case MODULO_ALIENO_MARRONE:
			txt = mp.getNumeroCosmonauti() + " alieno marrone";
			break;
		case MODULO_ALIENO_VIOLA:
			txt = mp.getNumeroCosmonauti() + " alieno viola";
			break;
		case MODULO_EQUIPAGGIO:
			txt = mp.getNumeroCosmonauti() + " cosmonauta";
		default:
			break;
		}
    	
    	return txt;
    }
    
    public void selezionaMerceDaEliminare(int elimMerce) {
    	
    	int caso;
    	do {
    		caso=0;
    		ArrayList<Coordinate> crd = new ArrayList();
    		
    		crd = trovaStiveNonVuote();
    		
    		if(crd == null) { 
    			
    			cns.println("Non ci sono merci nella nave");
    			break; 
    		}
    		
    		caso = crd.size();
    		
    		int sceltaStiva;
    		int sceltaMerci;
    		int numeroMerci;
			do {
    			
        		cns.println("Inserire il numero della stiva da cui togliere una merce");
    			
        		do {
        			sceltaStiva = Integer.parseInt(cns.consoleRead());
        			
        			if(sceltaStiva<0 || sceltaStiva>caso) {
        				cns.println("VALORE IMMESSO NON VALIDO");
        			}
        			
        		}while(sceltaStiva<=0 || sceltaStiva>caso);
        		
        		numeroMerci = ((Stiva)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getX()).get(crd.get(sceltaStiva-1).getY())).getStiva().size();
        		
        		for(int i=0; i<numeroMerci; i++) {
        			cns.println(""+(i+1)+") "
        		+((Stiva)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getX()).get(crd.get(sceltaStiva-1).getY())).getStiva().get(i).getTipoMerce());    		
        			
        			
        		}
        		sceltaMerci = 0;
        		
        		cns.println("Inserire la merce che si vuole togliere, selezionare 0 per scegliere un altra stiva");
    			
        		do {
        			sceltaMerci = Integer.parseInt(cns.consoleRead());
        			
        			if(sceltaMerci>0 && sceltaMerci<numeroMerci) {
        				cns.println("VALORE IMMESSO NON VALIDO");
        			}
        			
        		}while(sceltaMerci < 0 || sceltaMerci > numeroMerci);
        		
    		}while(sceltaMerci <= 0 || sceltaMerci > numeroMerci);
    		
			Merce merce = ((Stiva)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getX()).get(crd.get(sceltaStiva-1).getY())).getStiva().get(sceltaMerci-1);
    		
			try {
				this.giocatore.getNave().rimuoviMerce(crd.get(sceltaStiva-1), merce);
			} catch (ErroreCoordinate e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErroreTessera e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ErroreRisorse e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		elimMerce--;
    	}while(elimMerce > 0 && this.giocatore.getNave().getEquipaggio() > 0);
    }
    
    private String specificaMerci(ArrayList<Merce> stiva) {
    	
    	String txt = "";
    	if(stiva != null) {
    		
    		for(int i=0; i<stiva.size(); i++) {
        		
        		txt = txt +(i+1)+") "+ stiva.get(i).getTipoMerce() +"  ";
        	}
    		
    	}else {
    		
    		txt = "nulla";
    	}
    	
    	return txt;
    }
    
    private ArrayList<Coordinate> trovaStiveNonVuote(boolean isSpeciale){
    	int caso = 0;
    	ArrayList<Coordinate> crd = new ArrayList();
    	crd = null;
		
		for(int x=0; x<this.giocatore.getNave().getPlanciaDellaNave().size(); x++) {
			
			for(int y=0; y<this.giocatore.getNave().getPlanciaDellaNave().get(x).size(); y++) {
				
				Tessera tessera = this.giocatore.getNave().getPlanciaDellaNave().get(x).get(y);
				TipoTessera tipo = tessera.getTipoTessera();
				
				if (tipo == TipoTessera.PORTA_MERCI) {
				    if (((Stiva) tessera).getNumeroMerciAttuale() > 0) {
				        
				    	caso++;
				    	
				    	cns.println(""+caso+") "+((Stiva) tessera).getTipoMerciGenerale()+" in posizione ("+x+";"+y+") e contiente "
				    				+specificaMerci(((Stiva) tessera).getStiva()));
				    	
				    	crd.add(new Coordinate(x, y));
				    }
				}
			}
		}
    	return crd;
    }
    
    public void distribuzioneMerce(ArrayList<Merce> merci) {///TODO CONTROLLO STIVA SPECIALE E NON
    
    	int grandezza = merci.size();
    	int sceltaStiva;
    	boolean conferma, sceltoPieno, isSpeciale;
    	
    	for(int i=0; i<grandezza; i++) {
    		
    		if(merci.get(0).getTipoMerce() == TipoMerce.MERCE_ROSSA) {
    			isSpeciale = true;
    		}else {
    			isSpeciale = false;
    		}
    		
    		ArrayList<Coordinate> crd = new ArrayList();
    		
    		cns.println("Merci da posizionare: "+specificaMerci(merci));
    		
    		cns.println("Stive presenti nella nave:");
    		
    		crd = trovaStiveNonVuote();
    		
    		crd.addAll(trovaStiveVuote(crd.size()));
    		
    		cns.println("");
    		
    		cns.println("La merce in N* 1 ->"+merci.get(0).getTipoMerce()+" la vuoi posizionarla sulla nave? (in caso contrario verrà distrutta)");
    		
    		
    		if(cns.conferma()) {
    			
    			cns.println("Inserire il numero della stiva da cui inserire una merce");
    			do {
    				conferma = true;
    				sceltoPieno = false;
            		do {
            			sceltaStiva = Integer.parseInt(cns.consoleRead());
            			
            			if(sceltaStiva<0 || sceltaStiva>crd.size()) {
            				cns.println("VALORE IMMESSO NON VALIDO");
            			}
            			
            		}while(sceltaStiva<=0 || sceltaStiva>crd.size());
            		
            		if(((Stiva) this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getX()).get(crd.get(sceltaStiva-1).getY())).getStiva().size() < 1){
            			
            			cns.println("La stiva selezionata è piena! se confermi dovrai selezionare una merce da eliminare per liberare spazio");
            			cns.println("(in caso non si conferma si può scegliere un altra stiva)");
            			
            			if(cns.conferma()) {
            				conferma = false;
            				sceltoPieno = true;
            			}
            		}
            		
    			}while(!conferma);
    			
        		if(sceltoPieno) {
        			
        			int sceltaEliminare = 0;
        			
        			ArrayList<Merce> merce = ((Stiva) this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getX()).get(crd.get(sceltaStiva-1).getY())).getStiva();
        			
        			cns.println("Selezionare la mercve che si vuole eliminare per far spazio alla nuova merce:");
        			specificaMerci(merce);
        			
        			do {
        				sceltaEliminare = Integer.parseInt(cns.consoleRead());
            			
            			if(sceltaEliminare<=0 || sceltaEliminare>merce.size()) {
            				cns.println("VALORE IMMESSO NON VALIDO");
            			}
            			
            		}while(sceltaEliminare<=0 || sceltaEliminare>merce.size());
        			
        			try {
        				this.giocatore.getNave().rimuoviMerce(crd.get(sceltaStiva-1), merce.get(sceltaEliminare));
        			} catch (ErroreCoordinate e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (ErroreTessera e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			} catch (ErroreRisorse e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			
        			try {
        				this.giocatore.getNave().inserisciMerce(crd.get(sceltaStiva-1), merci.get(0));
        				
        			} catch (ErroreCoordinate e) {
        				
        				e.printStackTrace();
        			} catch (ErroreRisorse e) {
        				
        				e.printStackTrace();
        			} catch (ErroreTessera e) {
        				
        				e.printStackTrace();
        			}
        		}else {
        			
        			try {
        				this.giocatore.getNave().inserisciMerce(crd.get(sceltaStiva), merci.get(0));
        				
        			} catch (ErroreCoordinate e) {
        				
        				e.printStackTrace();
        			} catch (ErroreRisorse e) {
        				
        				e.printStackTrace();
        			} catch (ErroreTessera e) {
        				
        				e.printStackTrace();
        			}
        		}
    		}else {
    			
        		cns.println("La merce "+merci.get(i).getTipoMerce()+" è stata distrutta");
    		}
    		merci.remove(0);
    	}
    }
    
    private ArrayList<Coordinate> trovaStiveVuote(int n, boolean isSpeciale){
    	
    	int caso = n;
    	
    	ArrayList<Coordinate> crd = new ArrayList();
    	crd = null;
		
		for(int x=0; x<this.giocatore.getNave().getPlanciaDellaNave().size(); x++) {
			
			for(int y=0; y<this.giocatore.getNave().getPlanciaDellaNave().get(x).size(); y++) {
				
				Tessera tessera = this.giocatore.getNave().getPlanciaDellaNave().get(x).get(y);
				TipoTessera tipo = tessera.getTipoTessera();

				if (tipo == TipoTessera.PORTA_MERCI) {
				    if (((Stiva) tessera).getNumeroMerciAttuale() == 0) {
				        
				    	caso++;
				    	
				    	cns.println(""+caso+") "+((Stiva) tessera).getTipoMerciGenerale()+" vuota in posizione ("+x+";"+y+")");
				    	
				    	crd.add(new Coordinate(x, y));
				    }
				}
			}
		}
    	
    	return crd;
    }
    
    /**
     * LE prossime due funzioni sono equvalenti una per i scudi e una per il cannone doppio
     * 
     * @return false se: nave non ha abbastanza energia / giocatore sceglie di non utilizzare la tessera (scudo o cannone doppio)
     * 
     * @return true giocatore sceglie di utilizzare la tessera (scudo o cannone doppio) e perde 1 di energia
     */
    public Boolean sceltaEpossibilitaUtilizzoScudi( ) {
    	
    	if(this.giocatore.getNave().getEnergiaResidua() > 0) {
    		
    		cns.print("Hai abbastanza energia, vuoi utilizzare lo scudo?");
    		
    		if(cns.confermaSpecifica("Scudo")) {
    			
    			try {
					this.giocatore.getNave().utilizzaEnergia();
				} catch (ErroreRisorse e) {
					
					e.printStackTrace();
				}
    			
    			return true;
    		}else {
    			
    			try {
					this.giocatore.getNave().utilizzaEnergia();
				} catch (ErroreRisorse e) {
					
					e.printStackTrace();
				}
    			
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
    		
    		if(cns.confermaSpecifica("Cannone doppio")) {
    			
    			try {
					this.giocatore.getNave().utilizzaEnergia();
				} catch (ErroreRisorse e) {
					
					e.printStackTrace();
				}
    			
    			return true;
    		}else {
    			
    			try {
					this.giocatore.getNave().utilizzaEnergia();
				} catch (ErroreRisorse e) {
					
					e.printStackTrace();
				}
    			
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
    		
    		cns.println((i+1)+") "+merci.get(i).getTipoMerce());
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
