package carte;

import java.util.*;
import tessera.merce.*;

public class Contrabbandieri extends Nemici {
	
	private int potenzanecc; // potenza necessaria
	private int penalitamerci; // numeri merci persi
	private int penalitagiorni; // numeri giorni persi
	private List<TipoMerce> merci;

	public Contrabbandieri (int lvl) {
		
		super(lvl, TipoCarta.CONTRABBANDIERI);
		merci =  new ArrayList<>();
		GeneraValori();
	}
	
	private void GeneraValori() {
		GeneraMerci();
		GeneraPotenzaNecessaria();
		GeneraMercePersa();
		GeneraGiorniPersi();
	}
	
	private void GeneraMerci() {
		Random random = new Random();
		int vtdp=0;
		int r=0, g=0, v=0, b=0;
		
		switch(this.lvl) {
		case 1 ->{
			do {
				r = random.nextInt(2) + 0;  
				g = random.nextInt(3) + 0;
				v = random.nextInt(4) + 0;
				b = random.nextInt(5) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<5 || vtdp>9);
		}
		case 2 ->{
			do {
				r = random.nextInt(2) + 0;
				g = random.nextInt(3) + 0;
				v = random.nextInt(3) + 0;
				b = random.nextInt(7) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<10 || vtdp>14);
		}
		case 3 ->{
			do {
				r = random.nextInt(3) + 0;
				g = random.nextInt(4) + 0;
				v = random.nextInt(5) + 0;
				b = random.nextInt(7) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<15 || vtdp>19);
		}
		default ->{
			System.out.println("ERROR: scelta randomica delle merci (errorTipe: switch) (class: Conmtrabandieri)");
		}
		}

		for(int i=0; i<r; i++) {
			merci.add(TipoMerce.MERCE_ROSSA);
		}
		for(int i=0; i<g; i++) {
			merci.add(TipoMerce.MERCE_GIALLA);
		}
		for(int i=0; i<v; i++) {
			merci.add(TipoMerce.MERCE_VERDE);
		}
		for(int i=0; i<b; i++) {
			merci.add(TipoMerce.MERCE_BLU);
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
	
	private void GeneraMercePersa() {
		switch(this.lvl) {
		case 3 ->{
			this.penalitamerci = 4;
		}		
		case 2 ->{
			this.penalitamerci = 3;
		}		
		case 1->{
			this.penalitamerci = 2;
		}
		default ->{
			System.out.println("ERROR: scelta numeri merci perse in caso in cui la potenza non basta (errorTipe: switch) (class: Contrabbandieri)");
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
				"\nMerci Penalità:"+this.penalitamerci+
				"\nGiorni Penalità:"+this.penalitagiorni;
		for(int j=0; j<this.merci.size(); j++) {
			temp=temp+this.merci.get(j).name()+" | ";
		}
		
		return temp;
	}
}
