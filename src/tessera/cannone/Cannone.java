package tessera.cannone;

import java.util.Random;

import tessera.Tessera;
import tessera.TipoTessera;

public class Cannone extends Tessera {

	private final TipoCannone tipoCannone;

	public Cannone() {
		super(TipoTessera.CANNONE);
		this.tipoCannone = randomTipo();

	}

	public TipoCannone getTipoCannone() {
		return tipoCannone;
	}

	
	private TipoCannone randomTipo() {
		TipoCannone[] tipiCannone = TipoCannone.values();
		return tipiCannone[new Random().nextInt(tipiCannone.length)];
	}

	@Override
	protected void ruotaTessera() {
		// TODO Auto-generated method stub

	}

}
