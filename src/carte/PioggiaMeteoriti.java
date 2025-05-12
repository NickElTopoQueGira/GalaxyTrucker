package carte;

import carte.meteore.*;
import eccezioniPersonalizzate.ErroreRisorse;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import partita.Pedina;
import partita.nave.Nave;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoLato;
import tessera.TipoTessera;
import tessera.cannone.Cannone;
import tessera.cannone.TipoCannone;

import java.util.*;

public class PioggiaMeteoriti extends Carta {
	
	public ArrayList<Meteorite> meteoriti;
	private ComunicazioneConUtente stampa;
	
	public PioggiaMeteoriti (int lvl) {
		
		super(lvl, TipoCarta.PIOGGIA_METEORITI);
		stampa= ComunicazioneConUtente.getIstanza();
		meteoriti = new ArrayList<>();
		
		GeneraValori();
	}
	
	private void GeneraValori() {  //VALORI ATTUALMENTE CASUALI E DA RIVEDERE
		
		Random random = new Random();
		int nmeteore, grandezza;
		
		switch(this.lvl) {
		case 1->{
			nmeteore = random.nextInt(4) + 3; // MINIMO 3 MAX 6
			
			for(int i=0; i<nmeteore; i++) {
				
				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		case 2->{
			nmeteore = random.nextInt(4) + 5;  // MINIMO 5 MAX 8
			
			for(int i=0; i<nmeteore; i++) {

				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		case 3->{
			nmeteore = random.nextInt(5) + 6;  // MINIMO 6 MAX 10
			
			for(int i=0; i<nmeteore; i++) {

				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		default ->{
			stampa.printError("ERROR: numerazione meteoriti (errorTipe: switch) (class: PioggiaMeteorite)");
		}
		}
	}
	
	@Override
	public String toString() {
		String temp="";
		temp=temp+"Livello carta:"+this.lvl+"\n"+"Tipo carta:"+this.tipo;
		for(int i=0; i<this.meteoriti.size(); i++) {
			temp=temp+"tipo: "+meteoriti.get(i).getType()+"\n"+
					"   direzione: "+meteoriti.get(i).getDirezione()+"\n"+
					"   dado: "+meteoriti.get(i).getDado()+"\n";
			
		}
		return temp;
	}

	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		for(int i=0; i<this.meteoriti.size(); i++) {
			
			for(int j=0; j<elencoPedine.size(); j++) {
				
				boolean sceltaFermareMeteorite = false;
				
				Tessera colpito = trovaTesseraColpita(this.meteoriti.get(j), elencoPedine.get(j).getGiocatore().getNave());
				 
				if(colpito != null) {
					
					if(this.meteoriti.get(j).getType() == TypeMeteora.METEORITE_PICCOLO) { 
						
						if(controlloLatoIsCoperto(colpito, this.meteoriti.get(j).getDirezione())) {

							stampa.println("METEORITE RIMBALZATO SULLA NAVE");
							
							sceltaFermareMeteorite = true;
							
						}else {
						//TODO :
						// sceltaFermareMeteorite = interazioneConUtente.richiestaUtilizzoScudi(); 
						// 1) controlla se ha scudi
						// 2) controlla la direzione
						// 3) richiese de vuole usare gli scudi
							if(true) {
								
								stampa.println("METEORITE FERMATO DALLO SCUDO");
								sceltaFermareMeteorite = true;
							}
						}
					}else if(this.meteoriti.get(j).getType() == TypeMeteora.METEORITE_PICCOLO) { 
						
						switch(trovaCannone(this.meteoriti.get(j), elencoPedine.get(j).getGiocatore().getNave(), colpito)) {
						case 0->{}
						case 1->{

							stampa.println("METEORITE FERMATO DAL CANNONE DOPPIO");
							
							try {
								elencoPedine.get(j).getGiocatore().getNave().utilizzaEnergia(1);
							} catch (ErroreRisorse e) {
								
								e.printStackTrace();
							}
							
							sceltaFermareMeteorite = true;
						}
						case 2->{

							stampa.println("METEORITE FERMATO DAL CANNONE");
							
							sceltaFermareMeteorite = true;
						}
						default->{}
						}
					}
					
					if(!sceltaFermareMeteorite){
						
						try {
							elencoPedine.get(j).getGiocatore().getNave().rimuoviTessera(colpito.getCoordinate());
							 
						} catch (ErroreTessera e) {
							
							e.printStackTrace();
						}
					}
					
				}else {
					
					stampa.println("METEORITE HA MANCATO LA NAVE");
				}
				
			}
		}
		
		return elencoPedine;
	}
	
	private boolean controlloLatoIsCoperto(Tessera tes, PuntiCardinali p) {
		
		switch(p) {
		case NORD ->{
			if(tes.getLatiTessera().getUp() == TipoConnettoriTessera.NULLO) {
				
				return true;
			}
		}		
		case SUD ->{
			if(tes.getLatiTessera().getDown() == TipoConnettoriTessera.NULLO) {
				
				return true;
			}
		}		
		case EST ->{
			if(tes.getLatiTessera().getRight() == TipoConnettoriTessera.NULLO) {
				
				return true;
			}
		}		
		case OVEST ->{
			if(tes.getLatiTessera().getLeft() == TipoConnettoriTessera.NULLO) {
				
				return true;
			}
		}
		default->{}
		}
		
		return false;
	}
	
	private int sceltaUtilizzoCannone(Cannone cannone, Tessera colpita, Nave nave) {// TODO interazioneConUtente.richiestaUtilizzoCannoni
		
		//TODO bisogna mostrargli la tessera colpita E il tipo di cannone
		
		// case 0 non viene utilizzato il cannone 
					// 1) non c'è nessun cannone disponibile
					// 2) utente non utilizza cannone doppio (scelta sua / mancanza energia)
		// case 1 viene utilizzato il cannone
					// ma a costo di uno di energia   
					// IMPORTANTE GIOCATORE DIRA SI/NO QUINDI UNICHE OPZIONI IN CASO IL GIOCATORE DOVRà SCEGLIERE SARANNO 0 E 1
		// case 2 viene utilizzato il cannone automaticamente 
		
		if(nave.getEnergiaResidua() <= 0) {
			
			return 0;
		}
		
		if(cannone.getTipoCannone() == TipoCannone.SINGOLO) {
			
			if(true) { // TODO richiesta scelta all'utente
				
				return 1;
			}else {
				
				return 0;
			}
		}
		
		if(cannone.getTipoCannone() == TipoCannone.SINGOLO) {
			
			return 2;
		}
		return 0;
	}
	
	private int trovaCannone(Meteorite colpo, Nave nave, Tessera colpita) {
		
		int scelta = 0;
		
		switch(colpo.getDirezione()) {
		case NORD->{
			for(int i=0; i<nave.getRighe(); i++) {
				
				if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.UP) {
					
					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i), colpita, nave);
					
					if(scelta == 2) return scelta;
				}
			}
		}
		case SUD->{
			for(int i=nave.getRighe()-1; i>=0; i--) {
				if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.DOWN) {
					
					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i), colpita, nave);
					
					if(scelta == 2) return scelta;
				}
			}
		}
		case EST->{
			for(int i=nave.getColonne()-1; i>=0; i--) {
				if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.RIGHT) {

					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i), colpita, nave);
					
					if(scelta == 2) return scelta;
				}
			}
		}
		case OVEST->{
			for(int i=0; i<nave.getColonne(); i++) {
				if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.LEFT) {

					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i), colpita, nave);
					
					if(scelta == 2) return scelta;
				}
			}
		}
		default->{}
	}
	
	return scelta;
	}
	
	private Tessera trovaTesseraColpita(Meteorite colpo, Nave nave) {
		
		switch(colpo.getDirezione()) {
			case NORD->{
				for(int i=0; i<nave.getRighe(); i++) {
					if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(colpo.getDado()).get(i);
					}
				}
			}
			case SUD->{
				for(int i=nave.getRighe()-1; i>=0; i--) {
					if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(colpo.getDado()).get(i);
					}
				}
			}
			case EST->{
				for(int i=nave.getColonne()-1; i>=0; i--) {
					if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(i).get(colpo.getDado());
					}
				}
			}
			case OVEST->{
				for(int i=0; i<nave.getColonne(); i++) {
					if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(i).get(colpo.getDado());
					}
				}
			}
			default->{
				return null; //TODO 
			}
		
		}
		
		return null;
	}
	
}
