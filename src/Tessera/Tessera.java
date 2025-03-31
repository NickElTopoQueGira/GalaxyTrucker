package Tessera;

import java.util.Random;

public class Tessera {
	public final TipoTessera tipo;
	public static Tessera buffer_tessere_generate[];
	private static int currentSize=0;
	
	
	
	
	public Tessera() {
		

		this.tipo = RandomTipo();
		this.setCurrentSize(+1);
		buffer_tessere_generate[currentSize]=Tessera.this;
		
		
	}
	public Tessera[] getBuffer_mazzo() {
		return buffer_tessere_generate;
	}
	
	public int getCurrentSize() {
		return currentSize;
	}
	public void setCurrentSize(int edit) {
		currentSize = currentSize+edit;
	}
	public TipoTessera getTipo() {
		return tipo;
	}
	
	private TipoTessera RandomTipo(){
		int pick= new Random().nextInt(TipoTessera.values().length);
		TipoTessera tipo=TipoTessera.values()[pick];
		return tipo;
		
	}
	
	/*protected abstract void RandomTipoMerci();
	protected abstract void RandomTipoModuloPasseggeri();
	protected abstract void RandomTipoCannone();
	protected abstract void RandomTipoMotore();
	*/
	
	

}
