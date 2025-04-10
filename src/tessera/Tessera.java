package tessera;

import java.util.Arrays;

public abstract class Tessera implements GeneraTessera{
	
	protected final TipoTessera tipoTessera;
	protected LatiTessera latiTessera= new LatiTessera();
	private Coordinate coordinate;
	private static int currentSize=0;
	private static Tessera[] buffer_tessere_generate = new Tessera[0];

	
	public Tessera(TipoTessera tipoTessera) {

		this.tipoTessera = tipoTessera;
		
		if(this.tipoTessera!=TipoTessera.CENTRO) {
			aggiungiTessera();
		}else {
			this.latiTessera.setCentro();
		}
		
		
	}
	

	
	public LatiTessera getLatiTessera() {
		return latiTessera;
	}

	public TipoTessera getTipoTessera() {
		return tipoTessera;
	}
		
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	public Tessera[] getBuffer_mazzo() {
		return buffer_tessere_generate;
	}
	
	public static int getCurrentSize() {
		return currentSize;
	}
	public static void setCurrentSize(int edit) {
		currentSize = currentSize+edit;
	}



	public void aggiungiTessera() {
		setCurrentSize(+1);
		buffer_tessere_generate= Arrays.copyOf(buffer_tessere_generate, currentSize);
		buffer_tessere_generate[currentSize-1]= this;
	}
	
	
	public void ruota() {
		this.latiTessera.ruotaLati();
		this.ruotaTessera();
	}
	
	
	protected abstract void ruotaTessera();

}