package tessera;

import java.util.Arrays;



public abstract class Tessera implements GeneraTessera{
	
	protected final TipoTessera tipoTessera;
	
	protected int posizioneX;
	protected int posizioneY;
	private static int currentSize=0;
	private static Tessera[] buffer_tessere_generate = new Tessera[0];

	
	public Tessera(int x, int y,TipoTessera tipoTessera) {

		this.tipoTessera = tipoTessera;
		posizioneX=x;
		posizioneY=y;
		aggiungiTessera();
		
	}
	
	public int getPosizioneX() {
		return posizioneX;
	}
	public void setPosizioneX(int posizioneX) {
		this.posizioneX = posizioneX;
	}
	public int getPosizioneY() {
		return posizioneY;
	}
	public void setPosizioneY(int posizioneY) {
		this.posizioneY = posizioneY;
	}
	
	public void aggiungiTessera() {
		setCurrentSize(+1);
		buffer_tessere_generate= Arrays.copyOf(buffer_tessere_generate, currentSize);
		buffer_tessere_generate[currentSize-1]= this;
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


	
	private void Ruota() {
	
		TipoLatoCannone newdown=this.right;
		TipoLatoCannone newleft=this.down;
		TipoLatoCannone newup=this.left;
		TipoLatoCannone newright=this.up;
		this.down=newdown;
		this.left=newleft;
		this.up=newup;
		this.right=newright;
		
	
	}
	
	
	
	

}