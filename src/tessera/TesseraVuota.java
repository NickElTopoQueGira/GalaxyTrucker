package tessera;

import eccezioniPersonalizzate.ErroreTessera;

public class TesseraVuota extends Tessera{

	public TesseraVuota(int x, int y, Posizione posizione) throws ErroreTessera{
		super(TipoTessera.VUOTA, posizione);
		this.latiTessera.setVuota();
		super.getCoordinate().setX(x);
		super.getCoordinate().setY(y);
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
