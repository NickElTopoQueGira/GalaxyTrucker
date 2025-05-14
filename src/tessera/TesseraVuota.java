package tessera;

public class TesseraVuota extends Tessera{

	public TesseraVuota() {
		super(TipoTessera.VUOTA);
		this.latiTessera.setVuota();
	}

	/**
	 * metodo che ritorna stringa descrittiva della tessera
	 * @return ""
	 */
	@Override
	public String toLegenda() {
		return "";
	}
	
	

}
