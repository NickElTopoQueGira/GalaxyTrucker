package partita;

import java.util.*;
import eccezioniPersonalizzate.*;
import gioco.ComunicazioneConUtente;
import partita.giocatore.*;
import tessera.*;
import tessera.merce.Merce;
import tessera.merce.Stiva;
import tessera.merce.TipoMerce;
import tessera.merce.TipoStiva;
import tessera.modulo_passeggeri.ModuloPasseggeri;

public class Pedina{
    private Colori colorePedina;
    private Giocatore giocatore;
    private int posizioneSulTabellone;
    private ComunicazioneConUtente cns;
    private int numeroGiro;  // DA USARE
    
    public Pedina(Giocatore giocatore, Colori colorePedina){
        this.giocatore = giocatore;
		this.colorePedina = colorePedina;
        this.posizioneSulTabellone = 0;
        this.numeroGiro = 1;
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

    /**
     * Metodo che in base al numero di equipaggio che devono venir tolti, 
     * ti mostra tutti i moduli da cui si può rimuovere un componente dell'equipaggio, 
     * e specifica se alieno o no, e ti fa scegliere da dove toglierlo
     * 
     * @param elimEquipaggio int numero di equipaggio che verrà tolto
     */
    private ArrayList<Coordinate> trova(int n, int scelta){
    	
    	int caso = n;
    	
    	ArrayList<Coordinate> crd = new ArrayList<Coordinate>();
    	crd = null;
		
		for(int x=0; x<this.giocatore.getNave().getPlanciaDellaNave().size(); x++) {
			
			for(int y=0; y<this.giocatore.getNave().getPlanciaDellaNave().get(x).size(); y++) {
				
				switch(scelta) {
				case 1: caso = StiveVuote(caso, crd, x, y); break;
					
				case 2: caso = StiveNonVuote(caso, crd, x, y); break;
				
				case 3: caso = StiveSpeciali(caso, crd, x, y); break;
				
				case 4: caso = Modulo(caso, crd, x, y); break;
				
				default:	break;
				}
			}
		}
    	
    	return crd;
    }

    private int StiveVuote(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
    	Tessera tessera = this.giocatore.getNave().getPlanciaDellaNave().get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.PORTA_MERCI) {
		    if (((Stiva) tessera).getNumeroMerciAttuale() == 0) {
		        
		    	caso++;
		    	
		    	cns.println(""+caso+") "+((Stiva) tessera).getTipoMerciGenerale()+" vuota in posizione ("+x+";"+y+")");
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }
    
	private int StiveNonVuote(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
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
		return caso;
    }
    
	private int StiveSpeciali(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
    	Tessera tessera = this.giocatore.getNave().getPlanciaDellaNave().get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.PORTA_MERCI) {
		    if (((Stiva) tessera).getTipoMerciGenerale() == TipoStiva.SPECIALI) {
		        
		    	caso++;
		    	
		    	cns.println(""+caso+") "+((Stiva) tessera).getTipoMerciGenerale()+" vuota in posizione ("+x+";"+y+") e contiente "
		    			+specificaMerci(((Stiva) tessera).getStiva()));
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }
    
	private int Modulo(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
    	Tessera tessera = this.giocatore.getNave().getPlanciaDellaNave().get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.MODULO_PASSEGGERI) {
		    if (((ModuloPasseggeri) tessera).getNumeroCosmonauti() > 0) {
		        
		    	caso++;
	//TODO USA IL TOLEGENDA();    	
		    	cns.println(""+caso+") MODULO PASSEGGERI in posizione ("+x+";"+y+") e contiente "
		    				+specificaEquipaggio(((ModuloPasseggeri) tessera)));
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		} else if (tipo == TipoTessera.CENTRO) {
		    if (((Centro) tessera).getPasseggeriCorrenti() > 0) {

		    	caso++;
	//TODO USA IL TOLEGENDA();	    	
		    	cns.println(""+caso+") CENTRO in posizione ("+x+";"+y+") e contiente "
		    				+((Centro) tessera).getPasseggeriCorrenti()+" cosmonauti");
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }

    public void selezionaEquipaggioDaEliminare(int elimEquipaggio) {
    	int caso;
    	do {
    		caso=0;
    		ArrayList<Coordinate> crd = new ArrayList<Coordinate>();
    		
    		crd = trova(caso, 4);
    		
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
    				cns.ErroreImmissioneValore();;
    			}
    			
    		}while(sceltaModulo<=0 || sceltaModulo>caso);
    		
    		// TODO da completare
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
    
	private String specificaEquipaggio(ModuloPasseggeri mp) { //non serve, c'è gia nel toLegenda() della tessera modulopassggeri
    	
    	String txt = null;
    	
    	switch(mp.getTipoModuloPasseggeri()) {
    	case MODULO_ALIENO_MARRONE:								//togli tutto il metotodo ed utilizza direttamente il tolegenda() a sto punto
			txt = mp.getNumeroCosmonauti() + " alieno marrone"; //txt=mp.toLegenda();  il tutto senza switch, solo questo metodo e fa da solo lui la stampa
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
    		
    		crd = trova(caso, 2);
    		
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
        				cns.ErroreImmissioneValore();
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
        				cns.ErroreImmissioneValore();
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
    		
    		crd = trova(0, 2);
    		
    		crd.addAll(trova(crd.size(), 3));
    		
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
            				cns.ErroreImmissioneValore();
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
