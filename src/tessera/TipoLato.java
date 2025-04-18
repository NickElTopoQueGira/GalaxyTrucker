package tessera;

public enum TipoLato{
	UP, RIGHT, DOWN, LEFT;
	
	
	
	public TipoLato next() {
		TipoLato[] valore = TipoLato.values();
		int nextValore = (this.ordinal() +1)%valore.length;
		return valore[nextValore];
		
		
	}
	

}
