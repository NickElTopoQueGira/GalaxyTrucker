package carte;

import java.util.Random;

public class ZonaGuerra extends Carta {
	
	private static String[] penalita;
	private static String[] eventi;
	private static String[][] valori;
	
	public ZonaGuerra (int lvl) {
		
		super(lvl, TipoCarta.ZONA_GUERRA);
		eventi = new String[] {"CANNONI","EQUIPAGGI","RAZZI"}; 
		penalita = new String[] {"PERDITA_EQUIPAGGIO","PERDITA_GIORNI","PERDIOTA_MERCE", "CANNONATE"}; 
		
		valori = new String[3][2]; // PRIMA COLONNA (x 1)= EVENTI / SECONDA COLONNA (x 0)= PENALITA'
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
			valori[1][0] = penalita[x2-1];
			
		}while(valori[0][1] == penalita[x1-1]);
		
		
		int controllo = -1;                ///// IMPOSTO LA TERZA CHELLANGE(ULTIMO VALORE RIMANENTE), PENALITA' GIA IMPOSTATA DI DEFAULT 
		do {
			controllo++;
			
			if(controllo>=3) {
				System.out.println("ERROR: assegnazione ulrimo valore disponibile alla carta (errorTipe: do_while) (class: ZonaGuerra)");
			}
			valori[2][0] = eventi[controllo];
			
		}while(valori[0][0] == eventi[controllo] || valori[1][0] == eventi[controllo]);
	}
	
	private void ControlloEquipaggio() {
		
	}
	
	private void ControlloRazzi() {
		
	}
	
	private void ControlloCannoni() {
		
	}
}
