package tessera.modulo_passeggeri;

import java.util.Random;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoTessera;

public class ModuloPasseggeri extends Tessera {
	private static int contatore = 0;
	private static final int massimo = 29;

	private int numeroCosmonauti;
	private int numeroAlieniViola;
	private int numeroAlieniMarroni;
	
	private final TipoModuloPasseggeri tipoModuloPasseggeri;
	

	public ModuloPasseggeri() throws ErroreTessera {
		super(TipoTessera.MODULO_PASSEGGERI);
		contatore++;
		if (contatore <= massimo) {
			this.tipoModuloPasseggeri = randomTipo();

			this.numeroCosmonauti = 0; 
			this.numeroAlieniMarroni = 0; 
			this.numeroAlieniViola = 0;

			setEquipaggio();
		} else {
			throw new ErroreTessera("Numero Elementi Modulo Paseggeri Max"); // Eccezione Numero Massimo di elementi
		}
	}

	private TipoModuloPasseggeri randomTipo() {
		TipoModuloPasseggeri[] tipiModuli = TipoModuloPasseggeri.values();
		return tipiModuli[new Random().nextInt(tipiModuli.length)];
	}

	private void setEquipaggio() {
		switch (this.tipoModuloPasseggeri) {
			case MODULO_ALIENO_MARRONE:
				this.setNumeroAlieniViola(1);	
				break;
			case MODULO_ALIENO_VIOLA:
				this.setNumeroAlieniMarroni(1);
				break;
			case MODULO_EQUIPAGGIO:
				this.setNumeroCosmonauti(+2);
			default:
				break;
		}
	}

	public TipoModuloPasseggeri getTipoModuloPasseggeri() {
		return this.tipoModuloPasseggeri;
	}

	public int getNumeroCosmonauti() {
		return this.numeroCosmonauti;
	}

	public void setNumeroCosmonauti(int edit) {
		// no interfaccia con centro perchè l'utente
		// può cambiare piu volte il numero paseggeri in fase di cstruzione nave in base
		// a se
		// utiliazza alieni o meno
		this.numeroCosmonauti = this.numeroCosmonauti + edit;
	}

	public int getNumeroAlieniMarroni() {
		return this.numeroAlieniMarroni;
	}

	public int getNumeroAlieniViola() {
		return this.numeroAlieniViola;
	}

	public void setNumeroAlieniMarroni(int numeroAlieniMarroni) {
		this.numeroAlieniMarroni = numeroAlieniMarroni;
	}

	public void setNumeroAlieniViola(int numeroAlieniViola) {
		this.numeroAlieniViola = numeroAlieniViola;
	}

}
