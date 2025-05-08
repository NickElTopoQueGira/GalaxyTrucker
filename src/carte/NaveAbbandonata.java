package carte;

import java.util.*;
import partita.giocatore.Giocatore;

public class NaveAbbandonata extends Carta {
	
	private int guadagno, giornipersi, perditaequipaggio;
	
	public NaveAbbandonata (int lvl) {
		
		super(lvl, TipoCarta.NAVE_ABBANDONATA);
		GeneraValori();
		
	}
	
	private void GeneraValori() {
		GeneraGuadagno();
		GeneraPerditaEquipaggio();
		GeneraGiorniPersi();
	}
	
	private void GeneraGuadagno() {
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 1->{
			this.guadagno = random.nextInt(3) + 3;  //3-5
		}
		case 2->{
			this.guadagno = random.nextInt(3) + 6;  //6-8
		}
		case 3->{
			this.guadagno = random.nextInt(3) + 9;  //9-11
		}
		default->{
			System.out.println("ERROR: quantita ghuadagno della carta (errorTipe: switch) (class: NaveAbbandonata)");
		}
		}
	}
	
	private void GeneraPerditaEquipaggio() {
		
		
		Random random = new Random();
		
		switch(this.lvl) {
		case 1->{
			if(this.guadagno == 3) {
				this.perditaequipaggio = 2;
				
			}else if(this.guadagno == 5) {
				this.perditaequipaggio = 3;
				
			}else {
				this.perditaequipaggio = random.nextInt(2) + 2;
			}
		}
		case 2->{
			if(this.guadagno == 6) {
				this.perditaequipaggio = 4;
				
			}else if(this.guadagno == 8) {
				this.perditaequipaggio = 5;
				
			}else {
				this.perditaequipaggio = random.nextInt(2) + 4;
			}
		}
		case 3->{
			if(this.guadagno == 9) {
				this.perditaequipaggio = 6;
				
			}else if(this.guadagno == 11) {
				this.perditaequipaggio = 7;
				
			}else {
				this.perditaequipaggio = random.nextInt(2) + 6;
			}
		}
		default->{
			System.out.println("ERROR: calcolo numero dei equipaggi persi (errorTipe: switch) (class: NaveAbbandonata)");
		}
		}
	}
	
	private void GeneraGiorniPersi() {
		
		if(this.guadagno < 8) {
			
			this.giornipersi = 1;
		}else {
			this.giornipersi = 2;
		}
	}

	public int getGuadagno() {
		return guadagno;
	}

	public void setGuadagno(int guadagno) {
		this.guadagno = guadagno;
	}

	public int getGiornipersi() {
		return giornipersi;
	}

	public void setGiornipersi(int giornipersi) {
		this.giornipersi = giornipersi;
	}

	public int getPerditaequipaggio() {
		return perditaequipaggio;
	}

	public void setPerditaequipaggio(int perditaequipaggio) {
		this.perditaequipaggio = perditaequipaggio;
	}
	@Override
	public String toString() {
		String temp="";
		temp=temp+"\nLivello carta:"+this.lvl+
				"\nTipo carta:"+this.tipo+
				"\nGiorni Penalità:"+this.giornipersi+"\n"+
				"\nEquipaggio Penalità:"+this.perditaequipaggio+"\n"+
				"\nGuadagno:"+this.guadagno+"\n"; //guadagno, giornipersi, perditaequipaggio;

		
		return temp;
	}

	@Override
	public void eseguiCarta(ArrayList<Giocatore> elencoGiocatore) {
		// TODO Auto-generated method stub
		
	}


	
}
