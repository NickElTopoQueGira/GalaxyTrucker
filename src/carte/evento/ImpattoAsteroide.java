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
	
	private boolean ControlloCannone() {
		// 1) for in cui controllo se c'è la tessera cannone (controlla tutta la riga in caso c'è più di un cannone)
		// 2) controllo in quale direzione è puntato il cannone
		// 3) richiesta al giocatore se vuole utilizzare uno di energia o subire il colpo (nel caso del cannone doppio)
		// 4) se si utilizza energia o c'è cannone normale (entrambi puntati correttamente) >RETURN TRUE<		
		
		return false;
	}
	private boolean ControlloConnettoreEsposto() {
		// 1) in base della direzione del meteorite si controlla il lato della tessera
		// 2) se c'è un componente esposto >RETURN TRUE<
		
		return false;
	}
	private boolean ControlloScudo() { 
		// 1) for in cui controllo se c'è la tessera scudo
		// 2) controllo in quale direzione è "puntato" lo scudo
		// 3) richiesta al giocatore se vuole utilizzare uno di energia o subire il colpo
		// 4) se si utilizza energia >RETURN TRUE<
		
		return false;
	}
	
	
}
