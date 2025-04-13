package tessera.motore;

import java.util.Random;

<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
import tessera.Tessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Motore extends Tessera {

	private TipoLato latoMotore = TipoLato.UP;
	private final TipoMotore tipoMotore;

	public Motore() {
		super(TipoTessera.MOTORE);
		this.tipoMotore = randomTipo();

	}

	public TipoLato getLatoMotore() {
		return latoMotore;
	}

	public void setLatoMotore(TipoLato latoMotore) {
		this.latoMotore = latoMotore;
	}

	public TipoMotore getTipoMotore() {
		return tipoMotore;
	}

	private TipoMotore randomTipo() {
		TipoMotore[] tipiMotore = TipoMotore.values();
		return tipiMotore[new Random().nextInt(tipiMotore.length)];
	}

	@Override
	public void ruota() {
		super.ruota();
		this.latoMotore=this.latoMotore.next();
	}

}
