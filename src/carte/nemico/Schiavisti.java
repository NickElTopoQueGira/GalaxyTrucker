package carte.nemico;

import java.util.*;
import carte.*;
import gioco.ComunicazioneConUtente;
import partita.Pedina;

public class Schiavisti extends Nemici {
	
	private int potenzanecc; // potenza necessaria
	private int penalitaequipaggio; // numeri merci persi
	private int penalitagiorni; // numeri giorni persi
	private int guadagno;
	private ComunicazioneConUtente stampa;
	
	public Schiavisti (int lvl) {
		
		super(lvl, TipoCarta.SCHIAVISTI);
		GeneraValori();
		stampa= ComunicazioneConUtente.getIstanza();
	}
	
	private void GeneraValori() {
		GeneraGuadagno();
		GeneraPotenzaNecessaria();
		GeneraEquipaggioPerso();
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
	
	private void GeneraEquipaggioPerso() {
		switch(this.lvl) {
		case 3 ->{
			this.penalitaequipaggio = 5;
		}		
		case 2 ->{
			this.penalitaequipaggio = 4;
		}		
		case 1->{
			this.penalitaequipaggio = 3;
		}
		default ->{
			stampa.printError("ERROR: scelta numeri merci perse in caso in cui la potenza non basta (errorTipe: switch) (class: Contrabbandieri)");
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
				"\nPotenza necessaria:"+this.potenzanecc+
				"\nPenaltà Equipaggio:"+this.penalitaequipaggio+
				"\nGiorni Penalità:"+this.penalitagiorni+
				"\nGuadagno:"+this.guadagno;
		
		return temp;
	}
	
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		// TODO Auto-generated method stub
		boolean isCartaCompletata = false;
		int elenco = -1;
		
		do {
			elenco++;
			
			if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() == this.potenzanecc) {
				
				//TODO pareggio
				
			}else if(elencoPedine.get(elenco).getGiocatore().getNave().getPotenzaCannoni() > this.potenzanecc) {
				
				if(true) {//TODO comunicazione con giocatore gli viene chiesto se vuole ricevere i crediti in cambio dei giorni persi
					
					elencoPedine.get(elenco).getGiocatore().aggiornaCrediti(this.guadagno);
					
					elencoPedine.get(elenco).muoviPedina(-this.penalitagiorni);
				}
				
				isCartaCompletata = true;
			}else {
				
				elencoPedine.get(elenco).selezionaEquipaggioDaEliminare(this.penalitaequipaggio);
			}
			
			
		}while(!isCartaCompletata || elenco<elencoPedine.size());
		
		return elencoPedine;
	}
}
