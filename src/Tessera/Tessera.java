package Tessera;

import java.util.Random;

public class Tessera implements GeneraTessera{
	private final TipoTessera tipoTessera;
	public static Tessera buffer_tessere_generate[];
	private static int currentSize=0;
	
	
	
	
	public Tessera() {

		this.tipoTessera = TipoTessera.values()[RandomTipo()];
		this.setCurrentSize(+1);
		buffer_tessere_generate[currentSize]=Tessera.this;
		
		switch(this.tipoTessera) {
		
		case CANNONE:
			Cannone cannone=new Cannone();
			break;
			
		case MOTORE:
			Motore motore =new Motore();
			break;
		
		case MODULO_PASSEGGERI:
			ModuloPasseggeri moduloPasseggeri=new ModuloPasseggeri();
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
	
	
	public int RandomTipo(){
		int pick= new Random().nextInt(TipoTessera.values().length);
		return pick;
	}
	
	

}
