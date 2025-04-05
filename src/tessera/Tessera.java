package tessera;

import java.util.Arrays;
import java.util.Random;



public class Tessera implements GeneraTessera{
	
	private final TipoTessera tipoTessera;
	private static int currentSize=0;
	public static Tessera[] buffer_tessere_generate = new Tessera[0];
	protected int posizioneX;
	protected int posizioneY;
	
	
	public Tessera(int x, int y) {
		
		
		posizioneX=x;
		posizioneY=y;
		
		this.tipoTessera = TipoTessera.values()[RandomTipo()];
		estraiTipo();
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
		return tipoTessera;
	}
	
private void estraiTipo() {
		
	    Tessera t = this;
		switch(this.tipoTessera) {
		
		case CANNONE:
			t =new Cannone(this.posizioneX, this.posizioneY);
			((Cannone) t).stampa();
			
			break;

		case MOTORE:
			t=new Motore(this.posizioneX, this.posizioneY);
			((Motore) t).stampa();
				
			break;
		
		case MODULO_PASSEGGERI:
			t=new ModuloPasseggeri(this.posizioneX, this.posizioneY);
			((ModuloPasseggeri) t).stampa();
				
			break;
			
		case BATTERIA:
			break;
		case PORTA_MERCI:
			break;
		case SCUDI:
			break;
		case TUBI:
			break;

		}
		
		
		t.aggiungiTessera();
		
	}
	
	
	public void aggiungiTessera() {
		this.setCurrentSize(+1);
		buffer_tessere_generate= Arrays.copyOf(buffer_tessere_generate, currentSize);
		buffer_tessere_generate[currentSize-1]= this;
	}
	
	@Override
	public int RandomTipo(){
		int pick= new Random().nextInt(TipoTessera.values().length);
		return pick;
	}
	@Override
	public void stampa() {
		System.out.println(this.getTipo().toString());
		
	}


	




	@Override
	public void setLati() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void ruota() {
		// TODO Auto-generated method stub
		
	}




	
	
	
	
	
	

}