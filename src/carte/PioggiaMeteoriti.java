package carte;

import carte.meteore.*;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreRisorse;
import eccezioniPersonalizzate.ErroreTessera;
import eccezioniPersonalizzate.FinePartita;
import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.Pedina;
import partita.nave.Nave;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoLato;
import tessera.TipoTessera;
import tessera.cannone.Cannone;
import tessera.cannone.TipoCannone;

public class PioggiaMeteoriti extends Carta {
	
	public ArrayList<Meteorite> meteoriti;
	private final ComunicazioneConUtente stampa;
	
	/**
	 * Costruttore PioggiaMeteoriti
	 * super -> gli passiamo il lvl della carta e il tipo
	 * metodo: GeneraValori() per generare i attributi della carta
	 * @param lvl
	 */
	public PioggiaMeteoriti (int lvl) {
		
		super(lvl, TipoCarta.PIOGGIA_METEORITI);
		stampa= ComunicazioneConUtente.getIstanza();
		meteoriti = new ArrayList<>();
		
		GeneraValori();
	}
	
	/**
	 * Metodo che in base al livello della carta genera casualmente 
	 * i meteoriti e il tipo dei meteoriti 
	 */
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
		temp=temp+"Livello carta:"+this.lvl+"\n"+"Tipo carta:"+this.tipo+"\n";
		for(int i=0; i<this.meteoriti.size(); i++) {
			temp=temp+"tipo: "+meteoriti.get(i).getType()+"\n"+
					"   direzione: "+meteoriti.get(i).getDirezione()+"\n"+
					"   dado: "+meteoriti.get(i).getDado()+"\n";
			
		}
		return temp;
	}
	/**
	 * Metodo che prende tutti i giocatori e per ogni meteorite in base se:
	 * colpisce o manca la nave:
	 * 	-il meteorite è piccolo:
	 * 		-se dove colpisce c'è un connettore scoperto o no
	 * 			- se ha i scudi o no e se vuole  utilizzare o se ha energia
	 * 	-il meteorite è grande:
	 * 		-se nella fila di dove colpisce c'è un cannone
	 */
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		for(int i=0; i<this.meteoriti.size(); i++) {
			
			if(controlloColpoIsDentroDallaNave(this.meteoriti.get(i), elencoPedine.get(0).getGiocatore().getNave())) {
				
				this.meteoriti.get(i).setRisultatoDado(adattaDadiAllArray(this.meteoriti.get(i)));
				
				for(int j=0; j<elencoPedine.size(); j++) {
					
					boolean sceltaFermareMeteorite = false;
					
					Tessera colpito = trovaTesseraColpita(this.meteoriti.get(i), elencoPedine.get(j).getGiocatore().getNave());
					 
					if(colpito != null) {
						
						if(this.meteoriti.get(i).getType() == TypeMeteora.METEORITE_PICCOLO) { 
							
							if(controlloLatoIsCoperto(colpito, this.meteoriti.get(i).getDirezione())) {
	
								stampa.println("METEORITE RIMBALZATO SULLA NAVE");
								
								sceltaFermareMeteorite = true;
								
							}else {
							
								if(elencoPedine.get(j).sceltaEpossibilitaUtilizzoScudi()) {
									
									stampa.println("METEORITE FERMATO DALLO SCUDO");
									sceltaFermareMeteorite = true;
								}
							}
						}else if(this.meteoriti.get(i).getType() == TypeMeteora.METEORITE_PICCOLO) { 
							
							switch(trovaCannone(this.meteoriti.get(i), elencoPedine.get(j).getGiocatore().getNave(), colpito, elencoPedine.get(j))) {
							case 0->{}
							case 1->{
								
								stampa.println("METEORITE FERMATO DAL CANNONE DOPPIO");
								
								try {
									elencoPedine.get(j).getGiocatore().getNave().utilizzaEnergia();
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
								try {
									elencoPedine.get(j).getGiocatore().getNave().rimuoviTessera(colpito.getCoordinate());
								} catch (FinePartita e) {
									
									e.printStackTrace();
								}
								 
							} catch (ErroreTessera e) {
								
								e.printStackTrace();
							}
						}
						
					}else {
						
						stampa.println("METEORITE HA MANCATO LE NAVI ");
					}
					
				}
			}
		}
		
		return elencoPedine;
	}
	
	private boolean controlloColpoIsDentroDallaNave(Meteorite meteorite, Nave n) {
		
		switch(meteorite.getDirezione()) {
			case SUD , NORD ->{
				
				if(meteorite.getDado() < n.getConfineNaveX() && meteorite.getDado() > n.getInizioNaveX()) {
					
					return true;
				}
			}
			case OVEST, EST ->{
				
				if(meteorite.getDado() < n.getConfineNaveY() && meteorite.getDado() > n.getInizioNaveY()) {
					
					return true;
				}
			}
			default->{}
		}
		
		return false;
	}
	
	private int adattaDadiAllArray(Meteorite meteorite) {
		
		switch(meteorite.getDirezione()) {
		case SUD , NORD ->{

			return meteorite.getDado() - 3;
		}	
		case OVEST, EST ->{
			return meteorite.getDado() - 4;
		}
		default->{}
		}
		return 0;
	}
	/**
	 * metodo che in base a dove colpisce la nave ti "dice" se in quel punto
	 * il c'è un connettore scoperto o no
	 * 
	 * @param tes
	 * @param p
	 * @return se il lato ha un connettore scoperto su quel lato -> false 
	 * 			senno -> true
	 */
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
	
	/**
	 * metodo che in base a quale tipologia di cannone è
	 * se doppio  fa scegliere all'utente se usarlo o no
	 * se singolo non chiede 
	 * 
	 * @param cannone
	 * @param colpita
	 * @param nave
	 * @param pedina
	 * @return 0 se non c'è energia
	 * 			1 se il giocatore decide di usare il cannone doppio
	 * 			2 è un cannone singolo
	 */
	private int sceltaUtilizzoCannone(Cannone cannone, Tessera colpita, Nave nave, Pedina pedina) {
		
		if(nave.getEnergiaResidua() <= 0) {
			
			return 0;
		}else if(cannone.getTipoCannone() == TipoCannone.SINGOLO) {
			
			if(pedina.sceltaEpossibilitaUtilizzoCannoneDoppio()) { 
				
				return 1;
			}else {
				
				return 0;
			}
		}else if(cannone.getTipoCannone() == TipoCannone.SINGOLO) {
			
			return 2;
		}
		return 0;
	}
	
	/**
	 * metodo che in base alla direzione del meteorite questo metodo 
	 * trova uno per uno tutti i cannoni (che guardano in direzzione corretta)
	 * che si trovano nella linea di tiro del meteorite e se il cannone è doppio 
	 * lascia che il giocatore decida se utilizzarlo o no
	 * 
	 * @param colpo
	 * @param nave
	 * @param colpita
	 * @param pedina
	 * @return 0 se non ci sono cannoni corretti o il giocatore decide di non usarli o non c'è energia
	 * 			1 se il giocatore decide di usare il cannone doppio
	 * 			2 c'è un cannone singolo
	 */
	private int trovaCannone(Meteorite colpo, Nave nave, Tessera colpita, Pedina pedina) {
		
		int scelta = 0;
		
		switch(colpo.getDirezione()) {
		case NORD->{
			for(int i=0; i<nave.getRighe(); i++) {
				
				if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.UP) {
					
					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(i).get(colpo.getDado()), colpita, nave, pedina);
					
					if(scelta == 2) return scelta;
				}
			}
		}
		case SUD->{
			for(int i=nave.getRighe()-1; i>=0; i--) {
				if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.DOWN) {
					
					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(i).get(colpo.getDado()), colpita, nave, pedina);
					
					if(scelta != 0) return scelta;
				}
			}
		}
		case EST->{
			for(int i=nave.getColonne()-1; i>=0; i--) {
				if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.RIGHT) {

					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i), colpita, nave, pedina);
					
					if(scelta != 0) return scelta;
				}
			}
		}
		case OVEST->{
			for(int i=0; i<nave.getColonne(); i++) {
				if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() == TipoTessera.CANNONE && 
						 ((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i)).getLatoCannone() == TipoLato.LEFT) {

					scelta = sceltaUtilizzoCannone((Cannone) nave.getPlanciaDellaNave().get(colpo.getDado()).get(i), colpita, nave, pedina);
					
					if(scelta != 0) return scelta;
				}
			}
		}
		default->{}
	}
	
	return scelta;
	}
	
	/**
	 * metodo che trova quale tessera viene colpita dal meteorite
	 * 
	 * @param colpo
	 * @param nave
	 * @return la tessera colpita
	 */
	private Tessera trovaTesseraColpita(Meteorite colpo, Nave nave) {
		
		switch(colpo.getDirezione()) {
			case NORD->{
				for(int i=0; i<nave.getRighe(); i++) {
					if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(i).get(colpo.getDado());
					}
				}
			}
			case SUD->{
				for(int i=nave.getRighe()-1; i>=0; i--) {
					if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(i).get(colpo.getDado());
					}
				}
			}
			case EST->{
				for(int i=nave.getColonne()-1; i>=0; i--) {
					if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(colpo.getDado()).get(i);
					}
				}
			}
			case OVEST->{
				for(int i=0; i<nave.getColonne(); i++) {
					if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(colpo.getDado()).get(i);
					}
				}
			}
			default->{
				return null;
			}
		
		}
		
		return null;
	}
	
}
