package carte;

import java.util.ArrayList;
import java.util.Random;

import carte.meteore.*;

public class ZonaGuerra extends Carta {
	
	private static String[] penalita;
	private static String[] eventi;
	private static String[][] valori;
	public ArrayList<Meteorite> colpi;
	
	public ZonaGuerra (int lvl) {
		
		super(lvl, TipoCarta.ZONA_GUERRA);
		
		colpi = new ArrayList<>();
		
		eventi = new String[] {"CANNONI","EQUIPAGGI","RAZZI"}; 
		penalita = new String[] {"PERDITA_EQUIPAGGIO","PERDITA_GIORNI","PERDITA_MERCE", "CANNONATE"}; 
		
		valori = new String[3][2]; // PRIMA COLONNA (x 1)= EVENTI / SECONDA COLONNA (x 0)= PENALITA'
		
		GeneraValori();
	}
	
	private void GeneraValori() {
		
		Random random = new Random();
		
		valori[2][1] = penalita[3];
		
		int x1 = random.nextInt(3) + 1;    ///// IMPOSTO LE PRIME CHELLANGE E PENALITA'
		valori[0][0] = eventi[x1-1];
		int x2 = random.nextInt(3) + 1;
		valori[0][1] = penalita[x2-1];
		
		
		do {                              ///// IMPOSTO LE SECONDE CHELLANGE E PENALITA' (CON CONTROLLO DI NON RIPETIZIONE)
			x1 = random.nextInt(3) + 1;
			valori[1][0] = eventi[x1-1];
			
		}while(valori[0][0] == eventi[x1-1]);
		
		do {
			x2 = random.nextInt(3) + 1;
			valori[1][1] = penalita[x2-1];
			
		}while(valori[0][1] == penalita[x2-1]);
		
		
		int controllo = -1;                ///// IMPOSTO LA TERZA CHELLANGE(ULTIMO VALORE RIMANENTE), PENALITA' GIA IMPOSTATA DI DEFAULT 
		do {
			controllo++;
			
			if(controllo>=3) {
				System.out.println("ERROR: assegnazione ulrimo valore disponibile alla carta (errorTipe: do_while) (class: ZonaGuerra)");
			}
			valori[2][0] = eventi[controllo];
			
		}while(valori[0][0] == eventi[controllo] || valori[1][0] == eventi[controllo]);
		
		GeneraColpi();
	}
	
	private void GeneraColpi() {
		
		Random random = new Random();
		
		int ncolpi, grandezza;
		
		switch(this.lvl) {
		case 1->{
			ncolpi = random.nextInt(2) + 1; // MINIMO 1 MAX 2
			
			for(int i=0; i<ncolpi; i++) {
				
				grandezza = random.nextInt(3) + 1;
				
				if(grandezza == 1) {
					colpi.add(new ColpoGrande());
				}else {
					colpi.add(new ColpoPiccolo());
				}
			}
		}
		case 2->{
			ncolpi = random.nextInt(2) + 3;  // MINIMO 3 MAX 4
			
			for(int i=0; i<ncolpi; i++) {

				grandezza = random.nextInt(3) + 1;
				
				if(grandezza == 1) {
					colpi.add(new ColpoGrande());
				}else {
					colpi.add(new ColpoPiccolo());
				}
			}
		}
		case 3->{
			ncolpi = random.nextInt(2) + 5;  // MINIMO 5 MAX 6
			
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
			System.out.println("ERROR: numerazione colpi (errorTipe: switch) (class: ZonaGuerra)");
		}
		}		
		
		
	}
	
	int RisultatiDadi() {
		
		Random random = new Random();
		
		int d1 = random.nextInt(6) + 1;
		int d2 = random.nextInt(6) + 1;
		
		return d1+d2;
	}
	@Override
	public String toString() {
		String temp="";
		temp=temp+"\nLivello carta:"+this.lvl+
				"\nTipo carta:"+this.tipo;
		for(int i=0; i<3; i++) {
			for(int j=0; j<2; j++) {
				temp = temp+"- "+valori[i][j]+" - ";
			}
			temp = temp + "\n";
		}
		
		return temp;
	}

	public static String[] getPenalita() {
		return penalita;
	}

	public static void setPenalita(String[] penalita) {
		ZonaGuerra.penalita = penalita;
	}

	public static String[] getEventi() {
		return eventi;
	}

	public static void setEventi(String[] eventi) {
		ZonaGuerra.eventi = eventi;
	}

	public static String[][] getValori() {
		return valori;
	}

	public static void setValori(String[][] valori) {
		ZonaGuerra.valori = valori;
	}
	
	
}
