package tessera;

import eccezioniPersonalizzate.ErroreTessera;

public class TesseraVuota extends Tessera{

	public TesseraVuota() throws ErroreTessera{
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
