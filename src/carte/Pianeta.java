package carte;

// import merce.*;
import tessera.merce.*;


import java.util.*;

import gioco.ComunicazioneConUtente;
import partita.Pedina;

public class Pianeta extends Carta {
	
    private ComunicazioneConUtente cns;
	
	private int penalitagiorni;
	private List<Merce> merci = new ArrayList<>();
	private ArrayList<ArrayList<Merce>> pianeti;
	private ComunicazioneConUtente stampa;
	
	public Pianeta (int lvl) {
		
		super(lvl, TipoCarta.PIANETA);
		stampa= ComunicazioneConUtente.getIstanza();
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
		int numMerci = 0;
		int r=0, g=0, v=0, b=0;
		
		switch(numpianeti) {
		case 4 ->{
			do {
				r = random.nextInt(7) + 0;  
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;
				
				numMerci = r+g+v+b;
				
			}while((vtdp<25 || vtdp>50) || numMerci > 5);
		}
		case 3 ->{
			do {
				r = random.nextInt(7) + 0;
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;

				numMerci = r+g+v+b;
				
			}while((vtdp<15 || vtdp>40) || numMerci > 5);
		}
		case 2 ->{
			do {
				r = random.nextInt(7) + 0;
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;

				numMerci = r+g+v+b;
				
			}while((vtdp<5 || vtdp>30) || numMerci > 5);
		}
		default ->{
			stampa.printError("ERROR: scelta randomica del valore totate dei pianeti della carta (errorTipe: switch) (class: Pianeta)");
		}
		}
		
		for(int i=0; i<r; i++) {
			merci.add(new Merce(TipoMerce.MERCE_ROSSA));
		}
		for(int i=0; i<g; i++) {
			merci.add(new Merce(TipoMerce.MERCE_GIALLA));
		}
		for(int i=0; i<v; i++) {
			merci.add(new Merce(TipoMerce.MERCE_VERDE));
		}
		for(int i=0; i<b; i++) {
			merci.add(new Merce(TipoMerce.MERCE_BLU));
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
			stampa.printError("ERROR: calcolo dei giorni di penalità della carta (errorTipe: switch) (class: Pianeta)");
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
            	stampa.printError("ERROR: calcolo delle percentuali in base al pianeta (errorTipe: switch) (class: Pianeta)");
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

        merci.sort((a, b) -> b.getTipoMerce().getValore() - a.getTipoMerce().getValore());

        for (Merce m : merci) {
        	
            boolean assegnata = false;
            
            for (int i = 0; i < numpianeti; i++) {
            	
                if (valoreCorrente[i] + m.getTipoMerce().getValore() <= targetPerPianeta[i]) {
                	
                    pianeti.get(i).add(m);
                    valoreCorrente[i] += m.getTipoMerce().getValore();
                    assegnata = true;
                    
                    break;
                }
            }
            
            if (!assegnata) {								// CONTROLLO DI SICUREZZA FINALE
            	
            	pianeti.get(0).add(m);
            	valoreCorrente[0] += m.getTipoMerce().getValore();
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
				temp=temp+this.pianeti.get(i).get(j).getTipoMerce().name()+" ";
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

	public ArrayList<ArrayList<Merce>> getPianeti() {
		return pianeti;
	}

	public void setPianeti(ArrayList<ArrayList<Merce>> pianeti) {
		this.pianeti = pianeti;
	}

	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		int elenco = -1;
		do {
			elenco++;
			
			int scelta = sceltaPianeta(elencoPedine.get(elenco));
			
			if( scelta != (Integer) null) {
				
				elencoPedine.get(elenco).distribuzioneMerce(this.pianeti.get(scelta));
				
				elencoPedine.get(elenco).muoviPedina(-penalitagiorni);
				
				this.pianeti.remove(scelta);
			}
			
		}while(elenco<elencoPedine.size() && this.pianeti != null);
		
		return elencoPedine;
	}

	private int sceltaPianeta(Pedina pedina) {
		
		int sceltaPianeta = 0;
		
		for(int i=0; i<this.pianeti.size(); i++) {
			
			stampa.print("PIANETA "+(i+1)+") ");
			
			for(int j=0; j<this.pianeti.get(i).size(); j++) {
				
				stampa.print("{"+this.pianeti.get(i).get(j).getTipoMerce()+"} ");
			}
			stampa.println("");
		}
		do {
			stampa.println("Inserire il numero del pianeta per scegliere di atterrarci");
			stampa.println("In caso non si volesse scegliere nessuna dei pianeti inserire -> 0");
			
			do {
				sceltaPianeta = Integer.parseInt(stampa.consoleRead());
				
				if(sceltaPianeta >= 0 && sceltaPianeta < this.pianeti.size()+1) {
					stampa.println("VALORE IMMESSO NON VALIDO");
				}
				
			}while(sceltaPianeta < 0 || sceltaPianeta > this.pianeti.size()+1);
			
		}while(sceltaPianeta != 0 && !pedina.sceltaScambioMerciConGiorni(this.penalitagiorni, this.merci));
		
		return sceltaPianeta;
	}

	
}
