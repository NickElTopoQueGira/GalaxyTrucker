package tessera.modulo_passeggeri;

import java.util.Random;



import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoTessera;

public class ModuloPasseggeri extends Tessera {
	private static int contatore = 0;
	private int numeroCosmonauti=2;
	private int numeroAlieniViola = 0; 
	private int numeroAlieniMarroni = 0;
	private final TipoModuloPasseggeri tipoModuloPasseggeri;
	private static final int massimo=15;
	

	public ModuloPasseggeri() throws ErroreTessera {
		super(TipoTessera.MODULO_PASSEGGERI);
		contatore++;
		if(contatore<=massimo) {
			this.tipoModuloPasseggeri = randomTipo();
			setAlieno(this.tipoModuloPasseggeri);
		}else {
			throw new ErroreTessera("Numero Elementi Modulo Paseggeri Max"); //Eccezione Numero Massimo di elementi
		}
		
		

	}

	public TipoModuloPasseggeri getTipoModuloPasseggeri() {
		return tipoModuloPasseggeri;
	}

	
	private TipoModuloPasseggeri randomTipo() {
		TipoModuloPasseggeri[] tipiModuli = TipoModuloPasseggeri.values();
		return tipiModuli[new Random().nextInt(tipiModuli.length)];
	}
	
	protected int RandomTipo() {
		int pick = new Random().nextInt(TipoModuloPasseggeri.values().length);
		return pick;

	}

	public int getNumeroPasseggeri() {
		return numeroCosmonauti;
	}

	public void setNumeroPasseggeri(int edit) {
		this.numeroCosmonauti = this.numeroCosmonauti+edit;
	}

	public void setAlieno(TipoModuloPasseggeri tipoModulo) {
		if(tipoModulo==TipoModuloPasseggeri.MODULO_ALIENO_VIOLA) {
			this.setNumeroAlieniViola(1);
			this.numeroCosmonauti=0;
		}
        if(tipoModulo==TipoModuloPasseggeri.MODULO_ALIENO_MARRONE) {
			this.setNumeroAlieniMarroni(1);
			this.numeroCosmonauti=0;
		}
	}

	public int getNumeroAlieniMarroni() {
		return numeroAlieniMarroni;
	}

	public void setNumeroAlieniMarroni(int numeroAlieniMarroni) {
		this.numeroAlieniMarroni = numeroAlieniMarroni;
	}

	public int getNumeroAlieniViola() {
		return numeroAlieniViola;
	}

	public void setNumeroAlieniViola(int numeroAlieniViola) {
		this.numeroAlieniViola = numeroAlieniViola;
	}

}
