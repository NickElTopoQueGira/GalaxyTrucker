package tessera;

import java.util.Arrays;
import java.util.Random;

public abstract class Tessera implements GeneraTessera{
	
	private final TipoTessera tipoTessera;
	private static int currentSize=0;
	public static Tessera[] buffer_tessere_generate = new Tessera[0];
	
	
	
	
	
	public Tessera(TipoTessera tipotessera) {
		this.setCurrentSize(+1);
		buffer_tessere_generate= Arrays.copyOf(buffer_tessere_generate, currentSize);
		buffer_tessere_generate[currentSize-1]= this;
		
		this.tipoTessera = TipoTessera.values()[RandomTipo()];
		
		
		switch(this.tipoTessera) {
		
		case CANNONE:
			Cannone cannone=new Cannone(tipoTessera);
			break;
			
		case MOTORE:
			Motore motore =new Motore(tipoTessera);
			break;
		
		case MODULO_PASSEGGERI:
			ModuloPasseggeri moduloPasseggeri=new ModuloPasseggeri(tipoTessera);
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
	
	@Override
	public int RandomTipo(){
		int pick= new Random().nextInt(TipoTessera.values().length);
		return pick;
	}
	
	
	
	

}