package carte;

import merce.*;
import java.util.*;

public class Pianeta extends Carta {
	
	private int penalitagiorni;
	private int nmercerossa, nmercegialla, nmerceverde, nmerceblu;
	
	private ArrayList<ArrayList<Merce>> pianeti;
	
	public Pianeta (int lvl) {
		
		super(lvl, TipoCarta.PIANETA);
		
		pianeti = new ArrayList<>();
		GeneraValori();
	}
	
	private void GeneraValori() {
		Random random = new Random();
		
		int numpianeti = random.nextInt(3) + 2; // FA UN RANDOM DA UN MINIMO DI 2 PIANETI A UN MASSIMO DI 4
		int valorecarta = SceltaVTDP(numpianeti);
		penalitagiorni = CalcoloPGV(numpianeti, valorecarta);
		
		for(int i=0; i<numpianeti; i++) {                   // DA LAVORARE: DISTRIBUZIONE DELLE MERCI
			ArrayList<Merce> merce = new ArrayList<>();
			
			
			
			pianeti.add(merce);
		}
	}
	
	//VALORE TOTALE DEI PIANETI = VTDP / PENALITA GIORNI VIAGGIO = PGV
	int SceltaVTDP(int numpianeti) {
		Random random = new Random();
		int vtdp=0;
		int r=0, g=0, v=0, b=0;
		
		switch(numpianeti) {
		case 4 ->{
			do {
				r = random.nextInt(7) + 0;  
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<25 || vtdp>50);
		}
		case 3 ->{
			do {
				r = random.nextInt(7) + 0;
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<15 || vtdp>40);
		}
		case 2 ->{
			do {
				r = random.nextInt(7) + 0;
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<5 || vtdp>30);
		}
		default ->{
			System.out.println("ERROR: scelta randomica del valore totate dei pianeti della carta (errorTipe: switch) (class: Pianeta)");
		}
		}
		
		this.setNmercerossa(r);
		this.setNmercegialla(g);
		this.setNmerceverde(v);
		this.setNmerceblu(b);
		return vtdp;
	}
	
	int CalcoloPGV(int numpianeti, int vtdp) {
		int pgv=0;

		switch(numpianeti) {
		case 4 ->{
			pgv = vtdp/4;
			pgv = pgv/2;
		}
		case 3 ->{
			pgv = vtdp/3;
			pgv = pgv/2;
		}
		case 2 ->{
			pgv = vtdp/2;
			pgv = pgv/2;
		}
		default ->{
			System.out.println("ERROR: calcolo dei giorni di penalità della carta (errorTipe: switch) (class: Pianeta)");
		}
		}
		
		return pgv;
	}

	public int getPenalitagiorni() {
		return penalitagiorni;
	}

	public void setPenalitagiorni(int penalitagiorni) {
		this.penalitagiorni = penalitagiorni;
	}

	public int getNmercerossa() {
		return nmercerossa;
	}

	public void setNmercerossa(int nmercerossa) {
		this.nmercerossa = nmercerossa;
	}

	public int getNmercegialla() {
		return nmercegialla;
	}

	public void setNmercegialla(int nmercegialla) {
		this.nmercegialla = nmercegialla;
	}

	public int getNmerceverde() {
		return nmerceverde;
	}

	public void setNmerceverde(int nmerceverde) {
		this.nmerceverde = nmerceverde;
	}

	public int getNmerceblu() {
		return nmerceblu;
	}

	public void setNmerceblu(int nmerceblu) {
		this.nmerceblu = nmerceblu;
	}

	public ArrayList<ArrayList<Merce>> getPianeti() {
		return pianeti;
	}

	public void setPianeti(ArrayList<ArrayList<Merce>> pianeti) {
		this.pianeti = pianeti;
	}
	
}
