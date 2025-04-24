package carte;

import java.util.*;

public class Carta {
	
	protected int lvl;
	protected TipoCarta tipo;
	
	public Carta(int lvl) {
		this.lvl = lvl;
		this.tipo = null;
	}
	public Carta(int lvl , TipoCarta  tipo) {
		
		this.lvl = lvl;
		this.tipo = tipo;
	}
	
	public void StampaValori() {
		System.out.println("Livello carta:"+this.lvl);
		System.out.println("Tipo carta:"+this.tipo);
		System.out.println();
	}
	public int getLvl() { 
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public TipoCarta getTipo() {
		return tipo;
	}

	public void setTipo(TipoCarta tipo) {
		this.tipo = tipo;
	}
}
