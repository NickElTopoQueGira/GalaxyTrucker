package tessera;

public class Centro extends Tessera{
    public Centro(){
        super(TipoTessera.CENTRO);
    }


	@Override
	public int RandomTipo() { return 0; }


	@Override
	protected void ruotaTessera() {
	}
	
}
