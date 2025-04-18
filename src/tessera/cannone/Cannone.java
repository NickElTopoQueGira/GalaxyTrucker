package tessera.cannone;

import java.util.Random;

import tessera.Tessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Cannone extends Tessera {
	private TipoLato latoCannone = TipoLato.DOWN;

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
	public void ruota() {
		super.ruota();
		this.latoCannone=this.latoCannone.next();
	}

}
