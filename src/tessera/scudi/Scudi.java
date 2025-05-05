package tessera.scudi;

import eccezioniPersonalizzate.ErroreRotazione;


import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Scudi extends Tessera {

	private static final int massimo = 8;
	private int Rprec1=0;
	private int Cprec1=2;
	private int Rprec2=2;
	private int Cprec2=4;
	private static int contatore = 0;
	
	private TipoLato latoScudo1;
	private TipoLato latoScudo2;
	private String tempStampaCasella1;
	private String tempStampaCasella2;
	
	public Scudi() throws ErroreTessera {
		super(TipoTessera.SCUDI);
		contatore++;
		if (contatore > massimo) {
			throw new ErroreTessera("Numero Elementi Scudi Max"); // Eccezione Numero Massimo di elementi
		}
		this.latoScudo1 = TipoLato.UP;
		this.latoScudo2 = TipoLato.RIGHT;
		this.tempStampaCasella2=super.tessera_Disposizione[Rprec2][Cprec2];
		this.tempStampaCasella1=super.tessera_Disposizione[Rprec1][Cprec1];
		super.tessera_Disposizione[Rprec1][Cprec1]="@";
		super.tessera_Disposizione[Rprec2][Cprec2]="@";
	}

	@Override
	public void ruota() throws ErroreRotazione {
		super.ruota();
		this.latoScudo1 = this.latoScudo1.next();
		this.latoScudo2 = this.latoScudo2.next();
		
        super.tessera_Disposizione[Rprec2][Cprec2]=this.tempStampaCasella2;
        super.tessera_Disposizione[Rprec1][Cprec1]=this.tempStampaCasella1;
		
		switch (this.latoScudo1) {
		case UP: {
			this.Rprec1=0;
			this.Cprec1=2;
			this.Rprec2=2;
			this.Cprec2=4;
			break;
		}
		case RIGHT: {
			this.Rprec1=2;
			this.Cprec1=4;
			this.Rprec2=4;
			this.Cprec2=2;
			break;
		}
		case DOWN: {
			this.Rprec1=4;
			this.Cprec1=2;
			this.Rprec2=2;
			this.Cprec2=0;
			break;
		}
		case LEFT: {
			this.Rprec1=2;
			this.Cprec1=0;
			this.Rprec2=0;
			this.Cprec2=2;
			break;
		}
		default:
			throw new ErroreRotazione("Errore Rotazione Scudi");
		}
		
		this.tempStampaCasella2=super.tessera_Disposizione[Rprec2][Cprec2];
		this.tempStampaCasella1=super.tessera_Disposizione[Rprec1][Cprec1];
		super.tessera_Disposizione[Rprec1][Cprec1]="@";
		super.tessera_Disposizione[Rprec2][Cprec2]="@";
		
	}

}


