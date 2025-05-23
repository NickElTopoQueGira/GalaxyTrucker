package carte.nemico;

import carte.*;
import carte.meteore.*;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.Pedina;
import partita.nave.Nave;
import tessera.Tessera;
import tessera.TipoTessera;

public class Pirati extends Nemici {
	
	private int potenzanecc; // potenza necessaria
	private int penalitagiorni; // numeri giorni persi
	private int guadagno;
	private final ComunicazioneConUtente stampa;
	
	private final ArrayList<Meteorite> colpi;
	
	/**
	 * Costruttore Pirati
	 * super -> gli passiamo il lvl della carta e il tipo
	 * metodo: GeneraValori() per generare i attributi della carta
	 * @param lvl
	 */
	public Pirati (int lvl) {
		
		super(lvl, TipoCarta.PIRATI);
		colpi = new ArrayList<>();
		GeneraValori();
		stampa= ComunicazioneConUtente.getIstanza();
	}
	
	/**
     * Metodo di supporto che genera i valori caratteristici della carta:
     * potenza necessaria, penalità in termini di equipaggio e giorni, e guadagno.
     */
	private void GeneraValori() {
		GeneraGuadagno();
		GeneraPotenzaNecessaria();
		GeneraColpi();
		GeneraGiorniPersi();
	}
	
	/**
     * Genera casualmente il numero di crediti che si possono guadagnare
     * in caso di vittoria, in base al livello della carta.
     */
	private void GeneraGuadagno() {
		Random random = new Random();
		
		switch(this.lvl) {
		case 3 ->{
			this.guadagno = random.nextInt(3) + 10; // 10-12
		}
		case 2 ->{
			this.guadagno = random.nextInt(3) + 7; // 7-9
		}
		case 1 ->{
			this.guadagno = random.nextInt(3) + 4; // 4-6
		}
		default ->{
			stampa.printError("ERROR: scelta randomica delle merci (errorTipe: switch) (class: Conmtrabandieri)");
		}
		}
		
	}
	
	/**
     * Genera la potenza necessaria per sconfiggere il nemico,
     * in base al livello della carta.
     */
	private void GeneraPotenzaNecessaria() {
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 1 ->{
			this.potenzanecc = random.nextInt(3) + 4;  //4-6
		}		
		case 2 ->{
			this.potenzanecc = random.nextInt(3) + 6;  //6-8
		}		
		case 3->{
			this.potenzanecc = random.nextInt(3) + 8;  //8-10
		}
		default ->{
			stampa.printError("ERROR: scelta randomica della potenza necessria per lo suconfiggere i contrabbandieri (errorTipe: switch) (class: Contrabbandieri)");
		}
			
		}
	}
	
	/**
	 * metodo che genera i colpi (avviene sempre siccomelultima sfida 
	 * avviene sempre la pioggia di colpi) anchesso in base al lvl della 
	 * carta e anche il tipo del colpo
	 */
	private void GeneraColpi() {
		
		Random random = new Random();
		int ncolpi, grandezza;
		
		switch(this.lvl) {
		case 1 ->{
			
			ncolpi = random.nextInt(2) + 2; // MINIMO 2 MAX 3
			
			for(int i=0; i<ncolpi; i++) {
				
				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					colpi.add(new ColpoGrande());
				}else {
					colpi.add(new ColpoPiccolo());
				}
			}
		}		
		case 2 ->{
			
			ncolpi = random.nextInt(2) + 3; // MINIMO 3 MAX 4
			
			for(int i=0; i<ncolpi; i++) {
				
				grandezza = random.nextInt(2) + 1;
				
				if(grandezza == 1) {
					colpi.add(new ColpoGrande());
				}else {
					colpi.add(new ColpoPiccolo());
				}
			}
		}		
		case 3->{
			
			ncolpi = random.nextInt(2) + 4; // MINIMO 4 MAX 5
			
			for(int i=0; i<ncolpi; i++) {
				
				grandezza = random.nextInt(3) + 1;
				
				if(grandezza == 1) {
					colpi.add(new ColpoGrande());
				}else {
					colpi.add(new ColpoPiccolo());
				}
			}
		}
		default ->{
			stampa.printError("ERROR: generazione colpi casuali (errorTipe: switch) (class: Contrabbandieri)");
		}
			
		}
	}

	/**
	 * Imposta la quantità di membri dell'equipaggio che verranno persi se la nave
	 * non ha potenza sufficiente a sconfiggere i nemici.
	 * Il valore dipende dal livello della carta.
	 */
	private void GeneraGiorniPersi() {
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 3 ->{
			this.penalitagiorni = 2;
		}		
		case 2 ->{
			this.penalitagiorni = random.nextInt(2) + 1;
		}		
		case 1->{
			this.penalitagiorni = 1;
		}
		default ->{
			stampa.printError("ERROR: scelta numeri giorni persi in caso in cui si decide diu prendere le merci (errorTipe: switch) (class: Contrabbandieri)");
		}
			
		}
	}
	@Override
	public String toString() {
		String temp="";
		temp=temp+"\nLivello carta:"+this.lvl+
				"\nTipo carta:"+this.tipo+
				"\nPotenza necessaria:"+this.potenzanecc+
				"\nGuadagno:"+this.guadagno+
				"\nGiorni Penalità:"+this.penalitagiorni+"\n";
		for(int j=0; j<this.colpi.size(); j++) {
			temp=temp+this.colpi.get(j).getType()+" | " + this.colpi.get(j).getDirezione();
		}
		
		return temp;
	}
	
	/**
	 * Esegue l'effetto della carta Schiavisti. Per ogni giocatore ancora in gara:
	 * - se ha potenza sufficiente, può scegliere se ottenere crediti perdendo giorni
	 * - se ha potenza insufficiente, il giocatore riceve una pioggia di colpi.
	 * @param elencoPedine lista delle pedine dei giocatori
	 * @return la lista aggiornata delle pedine dopo l'esecuzione della carta
	 */
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		boolean isCartaCompletata = false;
		int elenco = -1;
		
		do {
			elenco++;
			
			if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() == this.potenzanecc) {
				
				stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+" CON LA POTENZA DI "
						+this.potenzanecc+" PAREGGIA CON LA NAVE NEMICA");
				
			}else if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() > this.potenzanecc) {
				
				stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+" CON LA POTENZA DI "
				+elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni()+" SCONFIGGE LA NAVE NEMICA");
				
				if(elencoPedine.get(elenco).sceltaScambioCreditiConGiorni(penalitagiorni, guadagno, 0)) {
					
					stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+"AL COSTO DI "+penalitagiorni+" GIORNO HA RICEVO "+this.guadagno+" \u00A2");
					
					elencoPedine.get(elenco).getGiocatore().aggiornaCrediti(this.guadagno);
					
					elencoPedine.get(elenco).getTabellone().muoviPedina(elencoPedine.get(elenco), -this.penalitagiorni);
				}
				
				isCartaCompletata = true;
			}else { // EFFETTO COLLATERALE CARTA ////////////////
				
				stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+" CON LA POTENZA DI "
						+elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni()+" VIENE SCONFITTA DALLA NAVE NEMICA "
								+ "E SPARA VARI COLPI ALLA NAVE");
				
				int j = 0;
				do {

					boolean sceltaFermareColpo = false;
					
					Tessera colpito = trovaTesseraColpita(this.colpi.get(j), elencoPedine.get(elenco).getGiocatore().getNave());
					 
					if(colpito != null) {
						
						if(this.colpi.get(j).getType() == TypeMeteora.COLPO_PICCOLO) {
							
							if(elencoPedine.get(elenco).sceltaEpossibilitaUtilizzoScudi()) {
								
								stampa.println("METEORITE FERMATO DALLO SCUDO");
								sceltaFermareColpo = true;
							}
						}
						
						if(!sceltaFermareColpo){
							
							try {
								try {
									elencoPedine.get(elenco).getGiocatore().getNave().rimuoviTessera(colpito.getCoordinate());
								} catch (ErroreGiocatore e) {
									
									e.printStackTrace();
								}
								
							} catch (ErroreTessera e) {
								
								e.printStackTrace();
							}
						}
						
					}else {
						
						stampa.println("COLPO HA MANCATO LA NAVE");
					}
					//TODO controllo integrita nave
					j++;
				}while(j < this.colpi.size()); 
			}
			
			
		}while(!isCartaCompletata && elenco < elencoPedine.size());
		
		
		return elencoPedine;
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
				return null;
			}
		
		}
		
		return null;
	}
	
}
