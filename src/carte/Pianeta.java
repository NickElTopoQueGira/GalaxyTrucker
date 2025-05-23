package carte;

import gioco.ComunicazioneConUtente;
import java.util.*;
import partita.Pedina;
import tessera.merce.*;

public class Pianeta extends Carta {
	
	private int penalitagiorni;
	private final List<Merce> merci = new ArrayList<>();
	private ArrayList<ArrayList<Merce>> pianeti;
	private final ComunicazioneConUtente stampa;
	
	/**
	 * Costruttore Pianeta
	 * super -> gli passiamo il lvl della carta e il tipo
	 * metodo: GeneraValori() per generare i attributi della carta
	 * @param lvl
	 */
	public Pianeta (int lvl) {
		
		super(lvl, TipoCarta.PIANETA);
		stampa= ComunicazioneConUtente.getIstanza();
		pianeti = new ArrayList<>();
		GeneraValori();
	}
	
	/**
	 * Metodo per riodinare le varie creazioni dei attributi
	 * 
	 * 1) genera casualmente il numero dei pianeti
	 * 2) calcola il valore totale dei pianeti e crea tutte le merci
	 * 3) calcola la penalità giorni
	 * 4) divide le merci ai vari pianeti
	 */
	private void GeneraValori() {
		Random random = new Random();
		
		int numpianeti = random.nextInt(3) + 2; // FA UN RANDOM DA UN MINIMO DI 2 PIANETI A UN MASSIMO DI 4
		int valorecarta = SceltaVTDP(numpianeti);
		penalitagiorni = CalcoloPGV(numpianeti, valorecarta);
		AssegnaMerci(numpianeti, valorecarta);
	}
	/**
	 * Metodo che in base al numero dei pianeti che ci sono (numero generato casualmente e passato comeparametro)
	 * genera le merci di tutti i pianeti e ne calcola anche il valore totale
	 * 
	 * @param numpianeti
	 * @return VALORE TOTALE DEI PIANETI
	 */
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
				
			}while((vtdp<25 || vtdp>50) || numMerci > 5*4);
		}
		case 3 ->{
			do {
				r = random.nextInt(7) + 0;
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;

				numMerci = r+g+v+b;
				
			}while((vtdp<15 || vtdp>40) || numMerci > 5*3);
		}
		case 2 ->{
			do {
				r = random.nextInt(7) + 0;
				g = random.nextInt(8) + 0;
				v = random.nextInt(7) + 0;
				b = random.nextInt(8) + 0;
				
				vtdp= r*4+g*3+v*2+b;

				numMerci = r+g+v+b;
				
			}while((vtdp<5 || vtdp>30) || numMerci > 5*2);
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
	
	/**
	 * metodo che in base al numero dei pianeti e al valore totale delle merci
	 * della carta calcola i giorni di penalità del completamento della carta
	 * 
	 * @param numpianeti
	 * @param vtdp
	 * @return PENALITA GIORNI VIAGGIO
	 */
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
	
	/**
	 * Metodo che in base in base ai vari parametri distribuscie le merci
	 * non in maniera equa ma in modo tale che il primo pianeta abbia un 
	 * valore di merce superiore ai pianeti che lo seguono
	 * 
	 * @param numpianeti
	 * @param valorecarta
	 */
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
	
	/**
	 * Metodo che in base alla scelta delgiocatore assegna le merci togliendo 
	 * i giorni di volo oppure nulla se il giocatore sceglie di non atterrare
	 */
	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		
		int elenco = -1;
		do {
			elenco++;
			
			int scelta = sceltaPianeta(elencoPedine.get(elenco));
			
			if( scelta != 0) {
				
				elencoPedine.get(elenco).distribuzioneMerce(this.pianeti.get(scelta));
				
				elencoPedine.get(elenco).getTabellone().muoviPedina(elencoPedine.get(elenco), -penalitagiorni);
				
				this.pianeti.remove(scelta);
			}
			
		}while(elenco<elencoPedine.size() && this.pianeti != null);
		
		return elencoPedine;
	}
	
	/**
	 * metodo che fa scegliere al giocatore in quale pianeta atterrare o non atterrare proprio
	 * 
	 * @param pedina
	 * @return sceltaPianeta
	 */
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
