package carte.nemico;

import java.util.*;
import carte.*;
import carte.meteore.*;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import partita.Pedina;
import partita.nave.Nave;
import tessera.Tessera;
import tessera.TipoTessera;

public class Pirati extends Nemici {
	
	private int potenzanecc; // potenza necessaria
	private int penalitagiorni; // numeri giorni persi
	private int guadagno;
	private ComunicazioneConUtente stampa;
	
	private ArrayList<Meteorite> colpi;
	
	public Pirati (int lvl) {
		
		super(lvl, TipoCarta.PIRATI);
		colpi = new ArrayList<>();
		GeneraValori();
		stampa= ComunicazioneConUtente.getIstanza();
	}
	
	private void GeneraValori() {
		GeneraGuadagno();
		GeneraPotenzaNecessaria();
		GeneraColpi();
		GeneraGiorniPersi();
	}
	
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
	
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		// TODO Auto-generated method stub
		
		boolean isCartaCompletata = false;
		int elenco = -1;
		
		do {
			elenco++;
			
			if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() == this.potenzanecc) {
				
				stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+" CON LA POTENZA DI "
						+this.potenzanecc+" PAREGGIA CON LA NAVE NEMICA");
				
			}else if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() > this.potenzanecc) {
				
				if(true) {//TODO comunicazione con giocatore gli viene chiesto se vuole ricevere i crediti in cambio dei giorni persi
					
					stampa.println("LA NAVE DI "+elencoPedine.get(elenco).getGiocatore().getNome()+"AL COSTO DI "+penalitagiorni+" GIORNO HA RICEVO "+this.guadagno+" \u00A2");
					
					elencoPedine.get(elenco).getGiocatore().aggiornaCrediti(this.guadagno);
					
					elencoPedine.get(elenco).muoviPedina(-this.penalitagiorni);
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
							
							
							//TODO :
							// sceltaFermareColpo = interazioneConUtente.richiestaUtilizzoScudi(); 
							// 1) controlla se ha scudi
							// 2) controlla la direzione
							// 3) richiese de vuole usare gli scudi
							
							stampa.println("COLPO FERMATO DALLO SCUDO");
							sceltaFermareColpo = true;
						}
						
						if(!sceltaFermareColpo){
							
							try {
								elencoPedine.get(elenco).getGiocatore().getNave().rimuoviTessera(colpito.getCoordinate());
								
							} catch (ErroreTessera e) {
								
								e.printStackTrace();
							}
						}
						
					}else {
						
						stampa.println("COLPO HA MANCATO LA NAVE");
					}
					
					j++;
				}while(this.colpi.get(j) == null); // TODO || nave is distrutta ||....
			}
			
			
		}while(!isCartaCompletata || elenco<elencoPedine.size());
		
		
		return elencoPedine;
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
