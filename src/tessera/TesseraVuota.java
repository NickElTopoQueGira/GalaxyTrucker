package tessera;

public class TesseraVuota extends Tessera{

	public TesseraVuota(int x, int y, Posizione posizione){
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
