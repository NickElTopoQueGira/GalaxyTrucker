package tessera;

import java.util.ArrayList;

public abstract class Tessera {

	protected final TipoTessera tipoTessera;
	protected LatiTessera latiTessera = new LatiTessera();
	private Coordinate coordinate;
	private static int currentSize = 0;
	private static ArrayList<Tessera> lista = new ArrayList<Tessera>();
	private final String[][] tessera_Disposizione = {
			// 0 1 2 3 4 <- colonne
			/* 0 */ { "─", "─", "─", "─", "─" },
			/* 1 */ { "│", " ", " ", " ", "│" },
			/* 2 */ { "│", " ", " ", " ", "│" },
			/* 3 */ { "│", " ", " ", " ", "│" },
			/* 4 */ { "─", "─", "─", "─", "─" },
			};

	public Tessera(TipoTessera tipoTessera) {

		this.tipoTessera = tipoTessera;

		if (this.tipoTessera != TipoTessera.CENTRO) {
			aggiungiTessera();
		} else {
			this.latiTessera.setCentro();
		}

	}

	public LatiTessera getLatiTessera() {
		return latiTessera;
	}

	public TipoTessera getTipoTessera() {
		return tipoTessera;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public ArrayList<Tessera> getBuffer_mazzo() {
		return lista;
	}

	public static int getCurrentSize() {
		return currentSize;
	}

	public static void setCurrentSize(int edit) {
		currentSize = currentSize + edit;
	}

	public void aggiungiTessera() {
		setCurrentSize(+1);
		lista.add(this);

	}

	public void ruota() {
		this.latiTessera.ruotaLati();

	}

	private String stampaUp() {
		switch (this.getLatiTessera().getUp()) {
		case TipoConnettoriTessera.NULLO: {
			return ("0");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("1");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return ("2");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("3");
		}
		}
		return null;
	}

	private String stampaDown() {
		switch (this.getLatiTessera().getDown()) {
		case TipoConnettoriTessera.NULLO: {
			return ("0");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("1");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return ("2");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("3");
		}
		}
		return null;
	}

	private String stampaLeft() {
		switch (this.getLatiTessera().getLeft()) {
		case TipoConnettoriTessera.NULLO: {
			return ("0");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("1");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return ("2");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("3");
		}
		}
		return null;
	}

	private String stampaRight() {
		switch (this.getLatiTessera().getRight()) {
		case TipoConnettoriTessera.NULLO: {
			return ("0");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("1");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return ("2");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("3");
		}
		}
		return null;
	}

	private void generaTessera_Disposizione() {
		this.tessera_Disposizione[2][2] = this.getTipoTessera().getTipo();
		this.tessera_Disposizione[1][2] = stampaUp();
		this.tessera_Disposizione[3][2] = stampaDown();
		this.tessera_Disposizione[2][1] = stampaLeft();
		this.tessera_Disposizione[2][3] = stampaRight();
	}

	public void stampa() {
		generaTessera_Disposizione();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print(this.tessera_Disposizione[i][j]);
			}
			System.out.println("");
		}

	}
}
