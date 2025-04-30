package tessera;

public class TesseraVuota extends Tessera{

	public TesseraVuota() {
		super(TipoTessera.VUOTA);
		this.latiTessera.setVuota();
	}

}
