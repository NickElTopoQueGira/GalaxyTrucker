package partita;

import java.util.*;
import eccezioniPersonalizzate.*;
import gioco.ComunicazioneConUtente;
import partita.giocatore.*;
import tabellone.Tabellone;
import tessera.*;
import tessera.merce.*;

public class Pedina{
    private Colori colorePedina;
    private Giocatore giocatore;
    private Tabellone tabellone;
    private int posizioneSulTabellone;
    private ComunicazioneConUtente cns;
    private int numeroGiro;
	private boolean inGioco;
    
	public Pedina(Giocatore giocatore){
		this.tabellone = null;
		this.giocatore = giocatore;
		this.colorePedina = this.giocatore.getColorePedina();
		this.posizioneSulTabellone = 0;
		this.numeroGiro = 1;
		this.inGioco = true;
	}

    public Pedina(Tabellone tabellone, Giocatore giocatore){
    	this.tabellone = tabellone;
        this.giocatore = giocatore;
		this.colorePedina = this.giocatore.getColorePedina();
        this.posizioneSulTabellone = 0;
        this.numeroGiro = 1;
		this.inGioco = true;
    }
    
    public Pedina(Tabellone tabellone, Giocatore giocatore, int posizione){
    	this.tabellone = tabellone;
        this.giocatore = giocatore;
		this.colorePedina = this.giocatore.getColorePedina();
        this.posizioneSulTabellone = posizione;
        this.numeroGiro = 1;
		this.inGioco = true;
    }
    
    public Tabellone getTabellone() {
		return this.tabellone;
	}

	public void setTabellone(Tabellone tabellone) {
		this.tabellone = tabellone;
	}
	
    public Colori getColorePedina() { 
        return this.colorePedina; 
    }

    public void setPosizioneSulTabellone(int posizioneSulTabellone){
        this.posizioneSulTabellone = posizioneSulTabellone;
    }

    public int getPosizioneSulTabellone(){ return this.posizioneSulTabellone; }

	public Giocatore getGiocatore(){ return this.giocatore; }

	public int getNumeroGiro(){ return this.numeroGiro; }

	public void setNumeroGiro(int numeroGiro){ this.numeroGiro += numeroGiro;}

	public boolean isPedinaInGioco(){ return this.inGioco; }

	public void setPedinaOutGioco(){ this.inGioco = false; }

	public void setPedinaInGioco(){ this.inGioco = true; }

    /**
     * Metodo che in base al numero di equipaggio che devono venir tolti, 
     * ti mostra tutti i moduli da cui si può rimuovere un componente dell'equipaggio, 
     * e specifica se alieno o no, e ti fa scegliere da dove toglierlo
     * 
     * @param elimEquipaggio int numero di equipaggio che verrà tolto
     */

    public void selezionaEquipaggioDaEliminare(int elimEquipaggio) {
    	int caso;
    	do {
    		caso=0;
    		ArrayList<Coordinate> crd = new ArrayList<Coordinate>();
    		
    		crd = this.giocatore.getNave().trova(caso, 4);
    		
    		if(crd == null) { 
    			
    			cns.println("Non ci sono merci nella nave");
    			break; 
    		}
    		
    		caso = crd.size();

    		
    		int sceltaModulo;
    		cns.println("Inserire il numero del Modulo da cui togliere 1 componente dell'equipaggio");
			
    		do {
    			sceltaModulo = Integer.parseInt(cns.consoleRead());
    			
    			if(sceltaModulo<=0 && sceltaModulo>caso) {
    				cns.erroreImmissioneValore();;
    			}
    			
    		}while(sceltaModulo<=0 || sceltaModulo>caso);
    		
    		try {
				this.giocatore.getNave().rimuoviEquipaggio(crd.get(sceltaModulo-1), sceltaModulo);
			} catch (ErroreCoordinate e) {
				e.printStackTrace();
			} catch (ErroreTessera e) {
				e.printStackTrace();
			}
    		
    		elimEquipaggio--;
    	}while(elimEquipaggio > 0 && this.giocatore.getNave().getEquipaggio() > 0);
    }
    
    public void selezionaMerceDaEliminare(int elimMerce) {
    	
    	int caso;
    	do {
    		caso=0;
    		ArrayList<Coordinate> crd;
    		
    		crd = this.giocatore.getNave().trova(0, caso);
    		
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
        				cns.erroreImmissioneValore();
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
        				cns.erroreImmissioneValore();
        			}
        			
        		}while(sceltaMerci < 0 || sceltaMerci > numeroMerci);
        		
    		}while(sceltaMerci <= 0 || sceltaMerci > numeroMerci);
    		
			Merce merce = ((Stiva)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getX()).get(crd.get(sceltaStiva-1).getY())).getStiva().get(sceltaMerci-1);
    		
			try {
				this.giocatore.getNave().rimuoviMerce(crd.get(sceltaStiva-1), merce);
			} catch (ErroreCoordinate e) {
				e.printStackTrace();
			} catch (ErroreTessera e) {
				e.printStackTrace();
			} catch (ErroreRisorse e) {
				e.printStackTrace();
			}
			
    		elimMerce--;
    	}while(elimMerce > 0 && this.giocatore.getNave().getEquipaggio() > 0);
    }
    
    public void distribuzioneMerce(ArrayList<Merce> merci) {
    
    	int grandezza = merci.size();
    	int sceltaStiva;
    	boolean conferma, sceltoPieno, isSpeciale, scelta;
    	
    	for(int i=0; i<grandezza; i++) {
    		
    		if(merci.get(0).getTipoMerce() == TipoMerce.MERCE_ROSSA) {
    			isSpeciale = true;
    		}else {
    			isSpeciale = false;
    		}
    		
    		ArrayList<Coordinate> crd;
    		
    		cns.println("Merci da posizionare: "+this.specificaMerci(merci));
    		
    		cns.println("La merce in N* 1 ->"+merci.get(0).getTipoMerce()+" la vuoi posizionarla sulla nave? (in caso contrario verrà distrutta)");
    		
    		scelta = cns.conferma();
    		
    		if(!isSpeciale) {
    			
    			cns.println("Stive presenti nella nave:");
        		
        		crd = this.giocatore.getNave().trova(0, 2);
        		
        		crd.addAll(this.giocatore.getNave().trova(crd.size(), 1));
        		
        		cns.println("");
        		
    		}else {
    			
    			cns.println("Stive speciali presenti nella nave:");
        		
        		crd = this.giocatore.getNave().trova(0, 3);
        		
        		cns.println("");
    		}
    		
    		if(scelta) {
    			
    			cns.println("Inserire il numero della stiva da cui inserire una merce");
    			do {
    				conferma = true;
    				sceltoPieno = false;
            		do {
            			sceltaStiva = Integer.parseInt(cns.consoleRead());
            			
            			if(sceltaStiva<0 || sceltaStiva>crd.size()) {
            				cns.erroreImmissioneValore();
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
        			this.specificaMerci(merce);
        			
        			do {
        				sceltaEliminare = Integer.parseInt(cns.consoleRead());
            			
            			if(sceltaEliminare<=0 || sceltaEliminare>merce.size()) {
            				cns.println("VALORE IMMESSO NON VALIDO");
            			}
            			
            		}while(sceltaEliminare<=0 || sceltaEliminare>merce.size());
        			
        			try {
        				this.giocatore.getNave().rimuoviMerce(crd.get(sceltaStiva-1), merce.get(sceltaEliminare));
        			} catch (ErroreCoordinate e) {
        				e.printStackTrace();
        			} catch (ErroreTessera e) {
        				e.printStackTrace();
        			} catch (ErroreRisorse e) {
        				e.printStackTrace();
        			}
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
    			
        		cns.println("La merce "+merci.get(i).getTipoMerce()+" è stata distrutta");
    		}
    		merci.remove(0);
    	}
    }
	private String specificaMerci(ArrayList<Merce> stiva) {
    	
    	String txt = "";
    	if(stiva != null) {
    		
    		for(int i=0; i<stiva.size(); i++) {
        		
        		txt = txt +(i+1)+") "+ stiva.get(i).getTipoMerce() +"  ";
        	}
    	}else {
    		
    		txt = "vuota";
    	}
    	return txt;
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
    
}
