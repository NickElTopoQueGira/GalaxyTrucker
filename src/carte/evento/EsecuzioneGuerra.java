package carte.evento;

import java.util.*;
import partita.nave.*;
import carte.*;

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
			}
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
