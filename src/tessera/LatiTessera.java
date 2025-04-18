package tessera;

import java.util.Random;

public class LatiTessera{

	private TipoConnettoriTessera up;
	private TipoConnettoriTessera left;
	private TipoConnettoriTessera right;
	private TipoConnettoriTessera down;

	public LatiTessera() {
		GeneraLatiTessera();
	}

	public void GeneraLatiTessera() {
		this.up 	= randomTipo();
		this.down 	= randomTipo();
		this.left 	= randomTipo();
		this.right 	= randomTipo();

		if (this.verificaTessera()) {
			return;
		} else {
			GeneraLatiTessera();
		}

	}

	private boolean verificaTessera() {

		if (up == down && left == right && right == down && down == TipoConnettoriTessera.NULLO) {
			return false;
		}
		return true;
	}

	public TipoConnettoriTessera getUp() {
		return up;
	}

	public void setUp(TipoConnettoriTessera up) {
		this.up = up;
	}

	public TipoConnettoriTessera getLeft() {
		return left;
	}

	public void setLeft(TipoConnettoriTessera left) {
		this.left = left;
	}

	public TipoConnettoriTessera getRight() {
		return right;
	}

	public void setRight(TipoConnettoriTessera right) {
		this.right = right;
	}

	public TipoConnettoriTessera getDown() {
		return down;
	}

	public void setDown(TipoConnettoriTessera down) {
		this.down = down;
	}

	private TipoConnettoriTessera randomTipo() {
		TipoConnettoriTessera[] t = TipoConnettoriTessera.values();
		return t[new Random().nextInt(t.length)];
	}

	public void ruotaLati() {
		TipoConnettoriTessera temp = this.up;
		this.up = this.left;
		this.left = this.down;
		this.down = this.right;
		this.right = temp;
	}

	public void setCentro() {
		this.up 	= TipoConnettoriTessera.TRIPLO;
		this.down 	= TipoConnettoriTessera.TRIPLO;
		this.left 	= TipoConnettoriTessera.TRIPLO;
		this.right 	= TipoConnettoriTessera.TRIPLO;
	}

}
