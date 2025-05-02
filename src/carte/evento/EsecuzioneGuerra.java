package carte.evento;

import java.util.*;
import partita.nave.*;
import carte.*;
import partita.giocatore.*;

public class EsecuzioneGuerra {
	
	private ZonaGuerra crt;
	private ArrayList<Nave> navig;
	
	public EsecuzioneGuerra(ZonaGuerra crt, ArrayList<Nave> navig) {
		
		this.crt = crt;
		this.navig = navig;
		
	}
	
	public void EsecuzioneCarta() {
		
		String v[][] = crt.getValori();
		
		for(int i=0; i<3; i++) {
			
			switch(v[i][0]) {
			case "CANNONI"->{
				
			}
			case "EQUIPAGGI"->{
				
			}
			case "RAZZI"->{
				
			}
			}
			
			switch(v[i][1]) {
			case "PERDITA_EQUIPAGGIO"->{
				
			}
			case "PERDITA_GIORNI"->{
				
			}
			case "PERDITA_MERCE"->{
				
			}			
			case "CANNONATE"->{
				
			}
			}
			
			
			
		}

	}
	
	private void ControlloCannoni() {
		
	}
	private void ControlloRazzi() {
		
	}
	private void ControlloEquipaggio() {
		
	}
	private void PerditaEquipaggio(Nave n) {
		
	}
	private void PerditaGiorni(Giocatore g) {
		
	}
	private void SubisciColpi(Nave n) {
		
	}
	
	
	
	
	
	
	
	
	
	
}
