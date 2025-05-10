package carte.nemico;

import java.util.*;
import carte.*;
import carte.meteore.*;
import partita.Pedina;

public class Pirati extends Nemici {
	
	private int potenzanecc; // potenza necessaria
	private int penalitagiorni; // numeri giorni persi
	private int guadagno;
	
	private ArrayList<Meteorite> colpi;
	
	public Pirati (int lvl) {
		
		super(lvl, TipoCarta.PIRATI);
		colpi = new ArrayList<>();
		GeneraValori();
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
			System.out.println("ERROR: scelta randomica delle merci (errorTipe: switch) (class: Conmtrabandieri)");
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
			System.out.println("ERROR: scelta randomica della potenza necessria per lo suconfiggere i contrabbandieri (errorTipe: switch) (class: Contrabbandieri)");
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
			System.out.println("ERROR: generazione colpi casuali (errorTipe: switch) (class: Contrabbandieri)");
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
			System.out.println("ERROR: scelta numeri giorni persi in caso in cui si decide diu prendere le merci (errorTipe: switch) (class: Contrabbandieri)");
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
		
		for(int i=0; i<elencoPedine.size(); i++){
			
			
			if(elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() == this.potenzanecc) {
			}else if(elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() < this.potenzanecc) {
				
				//elencoPedine.get(i).selezionaEquipaggioDaEliminare(this.); //TODO IN SCHIAVISTI
			}else {
				
				
			}
		}
		
		return elencoPedine;
	}
	
}
