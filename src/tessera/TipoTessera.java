package tessera;

public enum TipoTessera {
	PORTA_MERCI("M"),
	SCUDI("S"),
	TUBI("T"),
	MODULO_PASSEGGERI("P"),
	BATTERIA("B"), 
	CANNONE("C"), 
	MOTORE("M"),
	CENTRO("X");
	
    private final String tipo;
	
    TipoTessera(String tipo) {
        this.tipo = tipo;
    }

	public String getTipo() {
		return tipo;
	}
    
    
	
}
