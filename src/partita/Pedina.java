package partita;

import java.util.*;
import eccezioniPersonalizzate.*;
import gioco.ComunicazioneConUtente;
import partita.giocatore.*;
import tabellone.Tabellone;
import tessera.*;
import tessera.merce.*;

public class Pedina{
    private final ComunicazioneConUtente cns;
    private final Colori colorePedina;
    private final Giocatore giocatore;
    private Tabellone tabellone;
    private int posizioneSulTabellone;
    private int numeroGiro;
	private boolean inGioco;
	private boolean isNaveDistrutta;
    
	/**
	 * Costruttore di Pedina 
	 * 
	 * @param giocatore
	 */
	public Pedina(Giocatore giocatore){
		this.cns = ComunicazioneConUtente.getIstanza();
		this.tabellone = null;
		this.giocatore = giocatore;
		this.colorePedina = this.giocatore.getColorePedina();
		this.posizioneSulTabellone = 0;
		this.numeroGiro = 1;
		this.inGioco = true;
		this.isNaveDistrutta = false;
	}

	/**
	 * Metodo per sapere se la nave e' distrutta o meno
	 * @return la nave e' distrutta?
	 * */
	public boolean GetisNaveDistrutta() {
		return isNaveDistrutta;
	}

	/**
	 * Metodo per distruggere la nave durante la partita
	 * @param isNaveDistrutta Boolean
	 *                        ture -> la nave e' distrutta
	 *                        false -> la nave non e' distrutta
	 *  */
	public void setNaveDistrutta(boolean isNaveDistrutta) {
		this.isNaveDistrutta = isNaveDistrutta;
	}
	
	/**
     * Metoto getter per prendere tabellone
     * @return tabellone
     */
    public Tabellone getTabellone(){
		return this.tabellone;
	}
    
    /**
     * Metoto setter per impostare tabellone
     * @param tabellone
     */
	public void setTabellone(Tabellone tabellone){
		this.tabellone = tabellone;
	}
	
	/**
     * Metoto getter per prendere il colore della pedina
     * @return colore della pedina
     */
    public Colori getColorePedina(){ 
        return this.colorePedina; 
    }
    
    /**
     * Metoto setter per impostare tabellone
     * @param tabellone
     */
    public void setPosizioneSulTabellone(int posizioneSulTabellone){
        this.posizioneSulTabellone = posizioneSulTabellone;
    }
    
    /**
     * Metoto getter per prendere la posizione sul tabellone
     * @return posizione sul tabellone
     */
    public int getPosizioneSulTabellone(){ return this.posizioneSulTabellone; }
    
    /**
     * Metoto getter per prendere il giocatore associato a questa pedina
     * @return this.giocatore
     */
	public Giocatore getGiocatore(){ return this.giocatore; }
	
	/**
     * Metoto getter per prendere il numero del giro 
     * @return numero giro
     */
	public int getNumeroGiro(){ return this.numeroGiro; }
	
	/**
     * Metoto setter per impostare tabellone
     * @param tabellone
     */
	public void setNumeroGiro(int numeroGiro){ this.numeroGiro += numeroGiro;}
	
	/**
	 * Metodo che ritorna se la pedina è in gioco o no
	 * @return se la pedina è in gioco
	 */
	public boolean isPedinaInGioco(){ return this.inGioco; }
	
	/**
     * Metoto setter per impostare la pedina fuori dal gioco
     */
	public void setPedinaOutGioco(){ this.inGioco = false; }
	
	/**
     * Metoto setter per impostare la pedina in gioco
     */
	public void setPedinaInGioco(){ this.inGioco = true; }

    /**
     * Metodo che in base al numero di equipaggio che devono venir tolti, 
     * ti mostra tutti i moduli da cui si può rimuovere un componente dell'equipaggio, 
     * e specifica se alieno o no, e ti fa scegliere da dove toglierla
     * 
     * @param elimEquipaggio int numero di equipaggio che verrà tolto
     */
    public void selezionaEquipaggioDaEliminare(int elimEquipaggio) {
    	int caso;
    	
    	while(elimEquipaggio > 0 && this.giocatore.getNave().getEquipaggio() > 0){
    		
    		caso=0;
    		ArrayList<Coordinate> crd = new ArrayList<Coordinate>();
    		
    		crd = this.giocatore.getNave().trova(caso, 4);
    		
    		if(crd == null) { 
    			
    			cns.println("Non ci sono cosmonauti nella nave");
    			break; 
    		}
    		
    		caso = crd.size();

    		
    		int sceltaModulo = 0;
    		
			
    		do {
    			cns.println("Inserire il numero del Modulo da cui togliere 1 componente dell'equipaggio");
    			try {
    				sceltaModulo = Integer.parseInt(this.cns.consoleRead());
				} catch (NumberFormatException e) {
					sceltaModulo=-1;
				}
    			
    			
    			if(sceltaModulo<=0 || sceltaModulo>caso) {
    				cns.erroreImmissioneValore();
    			}
    			
    		}while(sceltaModulo<=0 || sceltaModulo>caso);
    		
    		try {
				this.giocatore.getNave().rimuoviEquipaggio(crd.get(sceltaModulo-1), 1);
			} catch (ErroreCoordinate e) {
				e.printStackTrace();
			} catch (ErroreTessera e) {
				e.printStackTrace();
			}
    		
    		elimEquipaggio--;
    	}
    }

	/**
	 * Metodo per selezionare la merce da togliere dalla nave
	 * @param elimMerce int
	 * */
    public void selezionaMerceDaEliminare(int elimMerce) {
    	
    	int caso;
    	while(elimMerce > 0 && this.giocatore.getNave().controlloPresenzaStiveNonVuote()) {
    		caso=0;
    		ArrayList<Coordinate> crd;
    		
    		crd = this.giocatore.getNave().trova(0, caso);
    		
    		if(crd == null || crd.isEmpty()) { 
    			
    			cns.println("Non ci sono merci nella nave");
    			break; 
    		}
    		
    		caso = crd.size();
    		if(caso>0) {
	    		int sceltaStiva = 0;
	    		int sceltaMerci;
	    		int numeroMerci;
				do {
	    			
	        		do {
	        			cns.println("Inserire il numero della stiva da cui togliere una merce");
		    			
	        			try {
	        				sceltaStiva = Integer.parseInt(this.cns.consoleRead());
						} catch (NumberFormatException e) {
							sceltaStiva = 0;
						}
	        			
	        			if(sceltaStiva<=0 || sceltaStiva>caso) {
	        				cns.erroreImmissioneValore();
	        			}
	        			
	        		}while(sceltaStiva<=0 || sceltaStiva>caso);
	        		
	        		numeroMerci = ((Stiva)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getY()).get(crd.get(sceltaStiva-1).getX())).getStiva().size();
	        		
	        		for(int i=0; i<numeroMerci; i++) {
	        			cns.println(""+(i+1)+") "
	        		+((Stiva)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getY()).get(crd.get(sceltaStiva-1).getX())).getStiva().get(i).getTipoMerce());    		
	        			
	        		}
	        		sceltaMerci = 0;
	        		
	        		do {
	        			cns.println("Inserire la merce che si vuole togliere, selezionare 0 per scegliere un altra stiva");
		    			
	        			try {
	        				sceltaMerci = Integer.parseInt(this.cns.consoleRead());
						} catch (NumberFormatException e) {
							sceltaMerci=1;
						}
	        			
	        			if(sceltaMerci < 0 || sceltaMerci > numeroMerci) {
	        				cns.erroreImmissioneValore();
	        			}
	        			
	        		}while(sceltaMerci < 0 || sceltaMerci > numeroMerci);
	        		
	    		}while(sceltaMerci <= 0 || sceltaMerci > numeroMerci);
	    		
				Merce merce = ((Stiva)this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getY()).get(crd.get(sceltaStiva-1).getX())).getStiva().get(sceltaMerci-1);
	    		
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
    		}else {
    			break;
    		}
    	}
    }

	/**
	 * Metodo per distribuire automaticamente la merce all'interno della nave
	 * @param merci ArrayList<Merce>
	 * */
    public void distribuzioneMerce(ArrayList<Merce> merci) {
    
    	int grandezza = merci.size();
    	int sceltaStiva = 0;
    	boolean sceltoPieno, isSpeciale, scelta, sceltaScambio;
    	
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
        		
        		if(crd.size()<=0) {
        			
        			scelta = false;
        			cns.println("non sono presenti stive speciali");
        		}
        		
    		}else {
    			
    			cns.println("Stive speciali presenti nella nave:");
        		
        		crd = this.giocatore.getNave().trova(0, 3);
        		
        		cns.println("");
        		
        		if(crd.size()<=0) {
        			
        			scelta = false;
        			cns.println("non sono presenti stive");
        		}
    		}
    		
    		if(scelta) {
    			
    			cns.println("Inserire il numero della stiva in cui inserire una merce");

				sceltoPieno = false;
				sceltaScambio = true;
        		do {
        			cns.println("Inserire il numero della stiva da cui inserire una merce");
        			try {
        				sceltaStiva = Integer.parseInt(this.cns.consoleRead());
					} catch (NumberFormatException e) {
						sceltaStiva=0;
					}
        			
        			if(sceltaStiva<=0 || sceltaStiva>crd.size()) {
        				cns.erroreImmissioneValore();
        			}
        			
        		}while(sceltaStiva<=0 || sceltaStiva>crd.size());
        		
        		cns.println("X="+ crd.get(sceltaStiva-1).getY()+" Y="+crd.get(sceltaStiva-1).getX());
        		Stiva tessera= ((Stiva) this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getX()).get(crd.get(sceltaStiva-1).getY()));
        		
        		if(tessera.getStiva().size() >= tessera.getMaxCapienza()){
        			
        			cns.println("La stiva selezionata è piena! se confermi dovrai selezionare una merce da eliminare per liberare spazio");
        			cns.println("(in caso non si conferma si può scegliere un altra stiva)");
        			
        			if(cns.conferma()) {
        				sceltoPieno = true;
        			}
    				sceltaScambio = false;
        		}
            		
        		if(sceltoPieno) {
        			
        			int sceltaEliminare = 0;
        			
        			ArrayList<Merce> merce = ((Stiva) this.giocatore.getNave().getPlanciaDellaNave().get(crd.get(sceltaStiva-1).getY()).get(crd.get(sceltaStiva-1).getX())).getStiva();
        			
        			do {
        				cns.println("Selezionare la mercve che si vuole eliminare per far spazio alla nuova merce:");
            			this.specificaMerci(merce);
        				try {
        					sceltaEliminare = Integer.parseInt(this.cns.consoleRead());
                			
						} catch (NumberFormatException e) {
							sceltaEliminare=0;
						}
        				
            			if(sceltaEliminare<=0 || sceltaEliminare>merce.size()) {
            				cns.println("VALORE IMMESSO NON VALIDO");
            			}
            			
            		}while(sceltaEliminare<=0 || sceltaEliminare>merce.size());
        			
        			try {
        				this.giocatore.getNave().rimuoviMerce(crd.get(sceltaStiva-1), merce.get(sceltaEliminare-1));
        			} catch (ErroreCoordinate e) {
        				e.printStackTrace();
        			} catch (ErroreTessera e) {
        				e.printStackTrace();
        			} catch (ErroreRisorse e) {
        				e.printStackTrace();
        			}
        		}
        		if(sceltaScambio) {
        			try {
        				this.giocatore.getNave().inserisciMerce(crd.get(sceltaStiva-1), merci.get(0));
        				
        			} catch (ErroreCoordinate e) {
        				
        				e.printStackTrace();
        			} catch (ErroreRisorse e) {
        				
        				e.printStackTrace();
        			} catch (ErroreTessera e) {
        				
        				e.printStackTrace();
        			}
        		}
    		}else {
    			
        		cns.println("La merce "+merci.get(0).getTipoMerce()+" è stata distrutta");
    		}
    		merci.remove(0);
    	}
    }

	/**
	 * Metodo per sapere che merci ci sono nella stiva
	 * @param stiva ArrayLists<Merce>
	 * @return String merce presente
	 * */
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
     * LE prossime due funzioni sono equivalenti una per i scudi e una per il cannone doppio
     * 
     * @return false se: nave non ha abbastanza energia / giocatore sceglie di non utilizzare la tessera (scudo o cannone doppio)
     * 
     * @return true giocatore sceglie di utilizzare la tessera (scudo o cannone doppio) e perde 1 di energia
     */
    public Boolean sceltaEpossibilitaUtilizzoScudi( ) {
    	if(this.giocatore.getNave().getEnergiaResidua() > 0) {
    		cns.print("Hai abbastanza energia, vuoi utilizzare lo scudo?");
    		if(cns.conferma()) {
    			try {
					this.giocatore.getNave().utilizzaEnergia();
					return true;
				}catch(ErroreRisorse e) {
					this.cns.printError(e.getMessage());
					return false;
				}
    		}else{
				return  false;
			}
    	}else{
    		cns.println("Non hai abbastanza energia per utilizzare lo scudo");
    		return false;
    	}
    }
    
    
    /**
     * Metodo per chiedere all'utente di utilizzare energia per il cannone doppio
     * @return true se utilizzatta e false se no
     */
    public Boolean sceltaEpossibilitaUtilizzoCannoneDoppio() {
    	if(this.giocatore.getNave().getEnergiaResidua() > 0) {
    		cns.print("Hai abbastanza energia, vuoi utilizzare il cannone doppio?");
    		if(cns.conferma()) {
    			try {
					this.giocatore.getNave().utilizzaEnergia();
					return true;
				} catch (ErroreRisorse e) {
					this.cns.printError(e.getMessage());
					return false;
				}
			}else{
				return false;
			}
    	}else{
    		cns.println("Non hai abbastanza energia per utilizzare il cannone doppio");
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
    	
    	if(this.giocatore.getNave().controlloPresenzaStive()) {
    	
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
    	}else {
    		cns.println("Non hai stive in cui mettere la merce!!");
    	}
    	return false;
    }

	// isGiorni: 1) si scambiano giorni con crediti
    //           0) si scambiano equipaggio con crediti
    public Boolean sceltaScambioCreditiConGiorni(int giorniPersi, int crediti, int equipaggio) {
		if(equipaggio == 0) {
			cns.println("Vuoi perdere "+giorniPersi+" giorni di viaggio  per "+crediti+"\u00A2 (crediti)?");
		}else {
			cns.println("Vuoi perdere "+giorniPersi+" giorni di viaggio e "+equipaggio+" membri dell'equipaggio per "+crediti+"\u00A2 (crediti)?");
		}
    	
    	if(cns.conferma()) {
    		if(equipaggio == 0) {
        		cns.println("Hai ricevuto "+crediti+"\u00A2 (crediti) ma perso "+giorniPersi+" giorni di viaggio");
    		}else {
    			cns.println("Hai ricevuto "+crediti+"\u00A2 (crediti) ma perso "+giorniPersi+" giorni di viaggio e "+equipaggio+" membri dell'equipaggio");
    		}
    		return true;
    	}else {
    		if(equipaggio == 0) {
        		cns.println("Non hai ricevuto nessun credito e non hai perso giorni di viaggio");
    		}else {
    			cns.println("Non hai ricevuto nessun credito e non hai perso ne membri dell'equipaggio ne giorni di viaggio");
    		}
    			
    		return false;
    	}
    }
}
