package carte.evento;

import java.util.ArrayList;

import carte.meteore.*;
import partita.nave.*;
import tessera.*;

public class ImpattoAsteroide {
	
	private Meteorite met;
	private Nave nave;
	
	public ImpattoAsteroide (Meteorite m, Nave n) {
		this.met = m;
		this.nave = n.getNave();
	}
	
	public Coordinate ControllaImpatto () {
		
		
		
		
		return null;
	}
	
	private Coordinate TrovaTessera (PuntiCardinali pc) {
		
		switch(pc) {
		case NORD ->{
			
			if(this.met.getDado()== 2 || this.met.getDado()== 12) {
				return null;
			}
			for(int j=0; j<12; j++ ) {
				
				
				
				if(this.nave != null) {
					// return cordinate tessera i j
				}
			}
			return null;
		}
		case SUD ->{
			
		}
		case EST ->{
			
		}
		case OVEST ->{
			
		}
		default ->{
			
		}
		}
		
		return null;
	}
}
