package tessera.cannone;

import java.util.Random;

import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoLato;
import tessera.TipoTessera;

public class Cannone extends Tessera {
	private static final int massimo = 36;
	private int rprec = 0;
	private int cprec = 2;
	private static int contatore = 0;
    private final TipoCannone tipoCannone;
	private TipoLato latoCannone;
	private String tempStampaCasella;
	private final static String canna="\033[0;31m"+"!"+"\033[0m";
	
	public Cannone() throws ErroreTessera {
		super(TipoTessera.CANNONE);
		contatore++;
		if (contatore <= massimo) {
			this.tipoCannone = randomTipo();
			this.latoCannone = TipoLato.UP;
			this.tempStampaCasella=super.tessera_Disposizione[rprec][cprec];
			super.tessera_Disposizione[rprec][cprec]=canna;
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
	public void ruota() throws ErroreRotazione {
		super.ruota();
		this.latoCannone = this.latoCannone.next();
		super.tessera_Disposizione[rprec][cprec]=this.tempStampaCasella;
		
		switch (this.latoCannone) {
		case UP: {
			this.rprec=0;
			this.cprec=2;
			break;
		}
		case RIGHT: {
			this.rprec=2;
			this.cprec=4;		
			break;
		}
		case DOWN: {
			this.rprec=4;
			this.cprec=2;
			break;
		}
		case LEFT: {
			this.rprec=2;
			this.cprec=0;
			break;
		}
		default:
			System.out.println("errore di rotazione");
		}
		
		this.tempStampaCasella=super.tessera_Disposizione[rprec][cprec];
		super.tessera_Disposizione[rprec][cprec]=canna;
	}


}
