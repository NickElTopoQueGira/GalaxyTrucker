package carte;

import java.util.ArrayList;
import java.util.Random;

import carte.meteore.*;
import partita.Pedina;
import partita.giocatore.Giocatore;

public class ZonaGuerra extends Carta {
	
	private static String[] penalita;
	private static String[] eventi;
	private static String[][] valori;
	private ArrayList<Meteorite> colpi;
	private int equipaggiPersi, giorniPersi, merciPersi;
	
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
		
		GeneraPerdite();
		GeneraColpi();
	}
	private void GeneraPerdite() {
		
		Random random = new Random();
		
		for(int i=0; i<3; i++) {
			
			switch(valori[i][1]) {
				case "PERDITA_EQUIPAGGIO" ->{
					equipaggiPersi = random.nextInt(2) + this.lvl+1; 
				}
				case "PERDITA_GIORNI" ->{
					giorniPersi = random.nextInt(2) + this.lvl+1; 
				}
				case "PERDITA_MERCE" ->{
					merciPersi = random.nextInt(2) + this.lvl+1; 
				}
				default ->{
					
				}
			}
		}
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
			System.out.println("ERROR: numerazione colpi (errorTipe: switch) (class: ZonaGuerra)"); // TODO
		}
		}		
		
		
	}
	
	int RisultatiDadi() {
		
		Random random = new Random();
		
		int d1 = random.nextInt(6) + 1;
		int d2 = random.nextInt(6) + 1;
		
		return d1+d2;
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

	@Override
	public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {
		// TODO Auto-generated method stub
		
		for(int i=0; i<3; i++) {
			
			int pedinaSubisce;
			
			switch(valori[i][0]) {
				case "EQUIPAGGI" ->{
					
					pedinaSubisce = selezionaMinorEquipaggio(elencoPedine);
					
					switch(valori[i][1]) {
						case "PERDITA_EQUIPAGGIO" ->{
							
						}
						case "PERDITA_GIORNI" ->{
							
						}
						case "PERDITA_MERCE" ->{
							
						}
						case "CANNONATE" ->{
							
						}
						default ->{
							
						}
					}
					
				}
				case "RAZZI" ->{
					
					pedinaSubisce = selezionaMinorMotore(elencoPedine);
					
					switch(valori[i][1]) {
						case "PERDITA_EQUIPAGGIO" ->{
							
						}
						case "PERDITA_GIORNI" ->{
							
						}
						case "PERDITA_MERCE" ->{
							
						}
						case "CANNONATE" ->{
							
						}
						default ->{
							
						}
					}
				}
				case "CANNONI" ->{
					
					pedinaSubisce = selezionaMinorCannone(elencoPedine);
					
					switch(valori[i][1]) {
						case "PERDITA_EQUIPAGGIO" ->{
							
						}
						case "PERDITA_GIORNI" ->{
							
						}
						case "PERDITA_MERCE" ->{
							
						}
						case "CANNONATE" ->{
							
						}
						default ->{
							
						}
					}
				}
				default ->{
					
				}
			}
		}
		
		

		return elencoPedine;
	}
	
	private int selezionaMinorEquipaggio(ArrayList<Pedina> elencoPedine) {
		
		int giocatoreMinorEquipaggio = 0;
		
		for(int i=1; i<elencoPedine.size();i++){
			if(elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() < elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getEquipaggio()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO
				
				giocatoreMinorEquipaggio = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
			
			}else if(elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() == elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getEquipaggio()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO
				
				if(elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(giocatoreMinorEquipaggio).getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE
					
					giocatoreMinorEquipaggio = i;//IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
				}
			}
		}
		return giocatoreMinorEquipaggio;
	}
	
	private int selezionaMinorMotore(ArrayList<Pedina> elencoPedine) {
		
		int minorPotenzaMotore = 0;
		
		for(int i=1; i<elencoPedine.size();i++){
			if(elencoPedine.get(i).getGiocatore().getNave().getPotenzaMotori() < elencoPedine.get(minorPotenzaMotore).getGiocatore().getNave().getPotenzaMotori()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO
				
				minorPotenzaMotore = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
			
			}else if(elencoPedine.get(i).getGiocatore().getNave().getPotenzaMotori() == elencoPedine.get(minorPotenzaMotore).getGiocatore().getNave().getPotenzaMotori()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO
				
				if(elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(minorPotenzaMotore).getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE
					
					minorPotenzaMotore = i;//IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
				}
			}
		}
		return minorPotenzaMotore;
	}
	
	private int selezionaMinorCannone(ArrayList<Pedina> elencoPedine) {
		
		int minorPotenzaCannone = 0;
		
		for(int i=1; i<elencoPedine.size();i++){
			if(elencoPedine.get(i).getGiocatore().getNave().getPotenzaCannoni() < elencoPedine.get(minorPotenzaCannone).getGiocatore().getNave().getPotenzaCannoni()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO
				
				minorPotenzaCannone = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
			
			}else if(elencoPedine.get(i).getGiocatore().getNave().getPotenzaCannoni() == elencoPedine.get(minorPotenzaCannone).getGiocatore().getNave().getPotenzaCannoni()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO
				
				if(elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(minorPotenzaCannone).getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE
					
					minorPotenzaCannone = i;//IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
				}
			}
		}
		return minorPotenzaCannone;
	}

	private Pedina perditaEquipaggio(Pedina pedina) {
		
		return pedina;
	}
	private Pedina perditaGiorni(Pedina pedina) {
		
		return pedina;
	}
	private Pedina perditaMerce(Pedina pedina) {
		
		return pedina;
	}
}
