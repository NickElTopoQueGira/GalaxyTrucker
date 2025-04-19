package tessera.cannone;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Cannone extends Tessera {
	private static int contatore = 0;
	private TipoLato latoCannone = TipoLato.DOWN;
	private final TipoCannone tipoCannone;
	private final int massimo=36;

	public Cannone() throws ErroreTessera {
		super(TipoTessera.CANNONE);
		contatore++;
		if(contatore<=massimo) {
			this.tipoCannone = randomTipo();
		}else {
			throw new ErroreTessera("Numero Elementi Cannone Max"); //Eccezione Numero Massimo di elementi
		}
		

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
