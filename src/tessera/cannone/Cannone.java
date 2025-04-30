package tessera.cannone;

import java.util.Random;


import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Cannone extends Tessera {
	private static final int massimo = 36;
	private int Rprec = 0;
	private int Cprec = 2;
	private static int contatore = 0;
    private final TipoCannone tipoCannone;
	private TipoLato latoCannone;
	private String tempStampaCasella;
	
	public Cannone() throws ErroreTessera {
		super(TipoTessera.CANNONE);
		contatore++;
		if (contatore <= massimo) {
			this.tipoCannone = randomTipo();
			this.latoCannone = TipoLato.UP;
			this.tempStampaCasella=super.tessera_Disposizione[Rprec][Cprec];
			super.tessera_Disposizione[Rprec][Cprec]="!";
			this.latiTessera.setUp(TipoConnettoriTessera.NULLO);
			while(!this.latiTessera.verificaTessera()) {
				this.latiTessera.GeneraLatiTessera();
				this.latiTessera.setUp(TipoConnettoriTessera.NULLO);
			}
			
		} else {
			throw new ErroreTessera("Numero Elementi Cannone Max"); // Eccezione Numero Massimo di elementi
		}

	}

	public TipoCannone getTipoCannone() {
		return this.tipoCannone;
	}

	public TipoLato getLatoCannone() {
		return this.latoCannone;
	}

	private TipoCannone randomTipo() {
		TipoCannone[] tipiCannone = TipoCannone.values();
		return tipiCannone[new Random().nextInt(tipiCannone.length)];
	}

	public float calcolaValore() {
		float valore=0;
		int tipo=1;
		if(this.tipoCannone== TipoCannone.DOPPIO) {
			tipo=2;
		}
		switch (this.latoCannone) {
		case UP: {
			valore++;
			break;
		}
		case RIGHT: {
			valore=(float) (valore+(tipo/2.0));
			break;
		}
		case LEFT: {
			valore=(float) (valore+(tipo/2.0));
			break;
		}
		default:
			
		}
		
		return valore;
		
	}
	
	@Override
	public void ruota() {
		super.ruota();
		this.latoCannone = this.latoCannone.next();
		super.tessera_Disposizione[Rprec][Cprec]=this.tempStampaCasella;
		
		switch (this.latoCannone) {
		case UP: {
			this.Rprec=0;
			this.Cprec=2;
			break;
		}
		case RIGHT: {
			this.Rprec=2;
			this.Cprec=4;		
			break;
		}
		case DOWN: {
			this.Rprec=4;
			this.Cprec=2;
			break;
		}
		case LEFT: {
			this.Rprec=2;
			this.Cprec=0;
			break;
		}
		default:
			System.out.println("errore di rotazione");
		}
		
		this.tempStampaCasella=super.tessera_Disposizione[Rprec][Cprec];
		super.tessera_Disposizione[Rprec][Cprec]="!";
	}


}
