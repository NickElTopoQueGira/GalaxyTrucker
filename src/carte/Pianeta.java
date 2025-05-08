package carte;

// import merce.*;
import tessera.merce.*;
import java.util.*;

public class Pianeta extends Carta {
	
	private int penalitagiorni;
	private List<TipoMerce> merci = new ArrayList<>();
	private ArrayList<ArrayList<TipoMerce>> pianeti;
	
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
		AssegnaMerci(numpianeti, valorecarta);
	}
	
	//VALORE TOTALE DEI PIANETI = VTDP / PENALITA GIORNI VIAGGIO = PGV
	private int SceltaVTDP(int numpianeti) {
		
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
		
		return vtdp;
	}
	
	private int CalcoloPGV(int numpianeti, int vtdp) {
		
		int pgv=0;
		float temp;
		switch(numpianeti) {
		case 4 ->{
			temp = vtdp/4;
			pgv = Math.round(temp/2);
		}
		case 3 ->{
			temp = vtdp/3;
			pgv = Math.round(temp/2);
		}
		case 2 ->{
			temp = vtdp/2;
			pgv = Math.round(temp/2);
		}
		default ->{
			System.out.println("ERROR: calcolo dei giorni di penalità della carta (errorTipe: switch) (class: Pianeta)");
		}
		}
		
		return pgv;
	}
	
	private void AssegnaMerci(int numpianeti, int valorecarta) {
		
		double[] percentuali = null;
		
        switch (numpianeti) {
            case 2 -> {
            	percentuali = new double[]{0.6, 0.4};
            }
            case 3 -> {
            	percentuali = new double[]{0.5, 0.3, 0.2};
            }
            case 4 -> {
            	percentuali = new double[]{0.4, 0.3, 0.2, 0.1};
            }
            default -> {
            	System.out.println("ERROR: calcolo delle percentuali in base al pianeta (errorTipe: switch) (class: Pianeta)");
            }
        }

        int[] targetPerPianeta = new int[numpianeti];
        
        for (int i = 0; i < numpianeti; i++) {
        	
            targetPerPianeta[i] = (int) Math.round(valorecarta * percentuali[i]); 
        }

        int[] valoreCorrente = new int[numpianeti];
        
        for (int i = 0; i < numpianeti; i++) {
        	
        	pianeti.add(new ArrayList<>());
        }

        merci.sort((a, b) -> b.getValore() - a.getValore());

        for (TipoMerce m : merci) {
        	
            boolean assegnata = false;
            
            for (int i = 0; i < numpianeti; i++) {
            	
                if (valoreCorrente[i] + m.getValore() <= targetPerPianeta[i]) {
                	
                    pianeti.get(i).add(m);
                    valoreCorrente[i] += m.getValore();
                    assegnata = true;
                    
                    break;
                }
            }
            
            if (!assegnata) {								// CONTROLLO DI SICUREZZA FINALE
            	
            	pianeti.get(0).add(m);
            	valoreCorrente[0] += m.getValore();
            }
        }
	}
	@Override
	public String toString() {
		String temp="";
		temp=temp+"\nLivello carta:"+this.lvl+
				"\nTipo carta:"+this.tipo+
				"\nGiorni Penalità:"+this.penalitagiorni+"\n";
		for(int i=0; i<this.pianeti.size(); i++) {
			temp=temp+"PAINETA"+i+" - ";
			
			for(int j=0; j<this.pianeti.get(i).size(); j++) {
				temp=temp+this.pianeti.get(i).get(j).name()+" | ";
			}
			temp=temp+"\n";
		}
		
		return temp;
	}
	
	public int getPenalitagiorni() {
		return penalitagiorni;
	}

	public void setPenalitagiorni(int penalitagiorni) {
		this.penalitagiorni = penalitagiorni;
	}

	public ArrayList<ArrayList<TipoMerce>> getPianeti() {
		return pianeti;
	}

	public void setPianeti(ArrayList<ArrayList<TipoMerce>> pianeti) {
		this.pianeti = pianeti;
	}
	
}
