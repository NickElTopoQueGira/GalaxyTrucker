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
	
	private void StampaValori() {
		
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
