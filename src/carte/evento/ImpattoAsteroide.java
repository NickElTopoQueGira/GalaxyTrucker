package carte.evento;

import java.util.ArrayList;

import carte.meteore.*;
import partita.nave.*;
import tessera.*;

public class ImpattoAsteroide {
	
	private Meteorite met;
	private ArrayList<ArrayList<Tessera>> nave;
	
	public ImpattoAsteroide (Meteorite m, Nave n) {
		this.met = m;
		this.nave = n.getPlanciaDellaNave();
	}
	
	public Coordinate ControlloDistruzione () {
		
		return null;
	}
	
	private Coordinate ControllaImpatto (PuntiCardinali pc) {
		
		switch(pc) {
		case NORD ->{
			
			if(this.met.getDado()== 2 || this.met.getDado()== 12) {
				return null;
			}
			for(int j=0; j<12; j++ ) {
				
				if(this.nave.get(this.met.getDado()).get(j) != null) {
					
					Coordinate c = new Coordinate(this.met.getDado(), j);
					return c;
				}
			}
			return null;
		}
		case SUD ->{

			if(this.met.getDado()== 2 || this.met.getDado()== 12) {
				return null;
			}
			for(int j=12; j>0; j-- ) {
				
				if(this.nave.get(this.met.getDado()).get(j) != null) {
					
					Coordinate c = new Coordinate(this.met.getDado(), j);
					return c;
				}
			}
			return null;
		}
		case EST ->{
			
			if(this.met.getDado() < 4 || this.met.getDado() > 9) {
				return null;
			}
			for(int i=0; i<12; i++ ) {
				
				if(this.nave.get(i).get(this.met.getDado()) != null) {
					
					Coordinate c = new Coordinate(i, this.met.getDado());
					return c;
				}
			}
			return null;
		}
		case OVEST ->{
			
			if(this.met.getDado() < 4 || this.met.getDado() > 9) {
				return null;
			}
			for(int i=12; i>0; i-- ) {
				
				if(this.nave.get(i).get(this.met.getDado()) != null) {
					
					Coordinate c = new Coordinate(i, this.met.getDado());
					return c;
				}
			}
			return null;
		}
		default ->{
			System.out.println("ERROR: ricerca impatto del meteorite con nave (errorTipe: switch) (class: ImpattoAsteroide)");
		}
		}
		
		return null;
	}
}
