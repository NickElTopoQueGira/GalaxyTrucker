package tessera;

public class MotoreSingolo extends Motore{
	protected TipoLatoCannone up=TipoLatoCannone.CANNA;
	protected TipoLatoCannone left=TipoLatoCannone.LISCIO;
	protected TipoLatoCannone right=TipoLatoCannone.LISCIO;
	protected TipoLatoCannone down=TipoLatoCannone.LISCIO;

	public MotoreSingolo(int posizioneX, int posizioneY) {
		super(posizioneX, posizioneY);
		
	}

	@Override
	public void ruota() {
		TipoLatoCannone newdown=this.right;
		TipoLatoCannone newleft=this.down;
		TipoLatoCannone newup=this.left;
		TipoLatoCannone newright=this.up;
		this.down=newdown;
		this.left=newleft;
		this.up=newup;
		this.right=newright;
		
	}

	public TipoLatoCannone getUp() {
		return up;
	}

	public void setUp(TipoLatoCannone up) {
		this.up = up;
	}

	public TipoLatoCannone getLeft() {
		return left;
	}

	public void setLeft(TipoLatoCannone left) {
		this.left = left;
	}

	public TipoLatoCannone getRight() {
		return right;
	}

	public void setRight(TipoLatoCannone right) {
		this.right = right;
	}

	public TipoLatoCannone getDown() {
		return down;
	}

	public void setDown(TipoLatoCannone down) {
		this.down = down;
	}
	
	

}
