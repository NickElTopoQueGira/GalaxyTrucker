package carte;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import partita.giocatore.Giocatore;
import tessera.merce.TipoMerce;

public class StazioneAbbandonata extends Carta {
	
	private int giocatorinecessari;
	private int penalitagiorni;
	private List<TipoMerce> merci;
	
	public StazioneAbbandonata (int lvl) {
		
		super(lvl, TipoCarta.STAZIONE_ABBANDONATA);
		merci =  new ArrayList<>();
		GeneraValori();
	}
	
	private void GeneraValori() {
		GeneraGiocatoriNecessari();
		SceltaGiorniPenalita();
		GeneraMerce();
	}
	private void GeneraGiocatoriNecessari() {

		Random random = new Random();
		
		int scelta = random.nextInt(2) + 0;
		
		switch(this.lvl) {
		case 1:
			this.giocatorinecessari = 5+scelta;
		break;
		case 2:
			this.giocatorinecessari = 7+scelta;
		break;
		case 3:
			this.giocatorinecessari = 9+scelta;
		break;
		default:
			System.out.println("ERROR: scelta casuale numero equipaggio necessario al completamento (errorTipe: switch) (class: StazioneAbbandonata)");
		break;
		}
	}
	
	private void SceltaGiorniPenalita() {
		
		
		if(this.giocatorinecessari <8) {
			
			this.penalitagiorni = 1;
		}else {
			this.penalitagiorni = 2;
		}
	}
	
	private void GeneraMerce() {
		
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
				
			}while(vtdp<5 || vtdp>8);
		}
		case 2 ->{
			do {
				r = random.nextInt(2) + 0;
				g = random.nextInt(3) + 0;
				v = random.nextInt(3) + 0;
				b = random.nextInt(7) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<7 || vtdp>10);
		}
		case 3 ->{
			do {
				r = random.nextInt(3) + 0;
				g = random.nextInt(4) + 0;
				v = random.nextInt(5) + 0;
				b = random.nextInt(7) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
			}while(vtdp<9 || vtdp>12);
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

	public int getGiocatorinecessari() {
		return giocatorinecessari;
	}

	public void setGiocatorinecessari(int giocatorinecessari) {
		this.giocatorinecessari = giocatorinecessari;
	}

	public int getPenalitagiorni() {
		return penalitagiorni;
	}

	public void setPenalitagiorni(int penalitagiorni) {
		this.penalitagiorni = penalitagiorni;
	}

	public List<TipoMerce> getMerci() {
		return merci;
	}

	public void setMerci(List<TipoMerce> merci) {
		this.merci = merci;
	}
	
	@Override
	public String toString() {
		String temp="";
		temp=temp+"\nLivello carta:"+this.lvl+
				"\nTipo carta:"+this.tipo+
				"\nGiorni Penalità:"+this.penalitagiorni+"\n"+
				"\nEquipaggio Necessario:"+this.giocatorinecessari+"\n";
				for(int i=0; i<this.merci.size(); i++) {
					temp=temp+this.merci.get(i).name()+" | ";
				}
				temp=temp+"\n"; 

		
		return temp;
	}

	@Override
	public void eseguiCarta(ArrayList<Giocatore> elencoGiocatore) {
		// TODO Auto-generated method stub
		
	}

}
