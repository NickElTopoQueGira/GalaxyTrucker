package tessera;

import java.util.Random;

public class LatiTessera implements GeneraTessera{
	
	private TipoConnettoriTessera up;
	private TipoConnettoriTessera left;
	private TipoConnettoriTessera right;
	private TipoConnettoriTessera down;
	
	public LatiTessera() {
		GeneraLatiTessera();
	}

	public void GeneraLatiTessera() {
		this.up=TipoConnettoriTessera.values()[RandomTipo()];
		this.down=TipoConnettoriTessera.values()[RandomTipo()];
		this.left=TipoConnettoriTessera.values()[RandomTipo()];
		this.right=TipoConnettoriTessera.values()[RandomTipo()];
		if(this.verificaTessera()) {
			return;
		}else {
			GeneraLatiTessera();
		}
		
	}
	
	
	private boolean verificaTessera() {
		if(up==down && left==right && right==down && down == TipoConnettoriTessera.NULLO) {
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
	
	
	@Override
	public int RandomTipo() {
		int pick= new Random().nextInt(TipoConnettoriTessera.values().length);
		return pick;
	}
	
}
