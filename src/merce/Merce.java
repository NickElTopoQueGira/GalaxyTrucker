package merce;

public class Merce {
	
	private static TipoMerce tipo;
	private static int valore;
	
	public Merce (TipoMerce tipo) {
		
		this.tipo = tipo;
		AssociaValore();
	}
	void AssociaValore() {
		
		switch(this.tipo) {
		case MERCE_ROSSA ->{
			
			this.valore = 4;
		}
		case MERCE_GIALLA ->{
			
			this.valore = 3;
		}
		case MERCE_VERDE ->{
			
			this.valore = 2;
		}
		case MERCE_BLU ->{
			
			this.valore = 1;
		}
		default ->{
			System.out.println("ERROR: associazione valore della merce (errorTipe: switch) (class: Merce)");
		}
		}
	}
}
