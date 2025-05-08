package carte;

import carte.meteore.*;
import partita.giocatore.Giocatore;
import partita.nave.Nave;
import carte.evento.ImpattoAsteroide;
import java.util.*;

public class PioggiaMeteoriti extends Carta {
	
	public ArrayList<Meteorite> meteoriti;
	
	public PioggiaMeteoriti (int lvl) {
		
		super(lvl, TipoCarta.PIOGGIA_METEORITI);
		
		meteoriti = new ArrayList<>();
		
		GeneraValori();
	}
	
	private void GeneraValori() {  //VALORI ATTUALMENTE CASUALI E DA RIVEDERE
		
		Random random = new Random();
		int nmeteore, grandezza;
		
		switch(this.lvl) {
		case 1->{
			nmeteore = random.nextInt(4) + 3; // MINIMO 3 MAX 6
			
			for(int i=0; i<nmeteore; i++) {
				
				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		case 2->{
			nmeteore = random.nextInt(4) + 5;  // MINIMO 5 MAX 8
			
			for(int i=0; i<nmeteore; i++) {

				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		case 3->{
			nmeteore = random.nextInt(5) + 6;  // MINIMO 6 MAX 10
			
			for(int i=0; i<nmeteore; i++) {

				grandezza = random.nextInt(4) + 1;
				
				if(grandezza == 1) {
					meteoriti.add(new MeteoriteGrande(this.lvl));
				}else {
					meteoriti.add(new MeteoritePiccolo());
				}
			}
		}
		default ->{
			System.out.println("ERROR: numerazione meteoriti (errorTipe: switch) (class: PioggiaMeteorite)");
		}
		}
	}
	
	public void AzzioneCarta (Nave n) {    //da usare in game
		
		for(int i=0; i<this.meteoriti.size(); i++) {
			
			ImpattoAsteroide ia = new ImpattoAsteroide(this.meteoriti.get(i), n);
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
		temp=temp+"Livello carta:"+this.lvl+"\n"+"Tipo carta:"+this.tipo;
		for(int i=0; i<this.meteoriti.size(); i++) {
			temp=temp+"tipo: "+meteoriti.get(i).getType()+"\n"+
					"   direzione: "+meteoriti.get(i).getDirezione()+"\n"+
					"   dado: "+meteoriti.get(i).getDado()+"\n";
			
		}
		
		
		return temp;
	}

	@Override
	public void eseguiCarta(ArrayList<Giocatore> elencoGiocatore) {
		// TODO Auto-generated method stub
		
	}


	
}
