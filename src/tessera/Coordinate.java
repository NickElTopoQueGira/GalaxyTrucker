package tessera;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(){}

    public Coordinate(int x, int y){
        this.x = x; 
        this.y = y;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

	public Coordinate adiacente(TipoLato dir) {
		Coordinate adiacente=new Coordinate();
		switch (dir) {
		case UP: {
			adiacente.setX(this.getX());
			adiacente.setY(this.getY()-1);
			break;
		}
		case LEFT: {
			adiacente.setX(this.getX()-1);
			adiacente.setY(this.getY());
			break;		
				}
		case DOWN: {
			adiacente.setX(this.getX());
			adiacente.setY(this.getY()+1);
			break;
			
		}
		case RIGHT: {
			adiacente.setX(this.getX()+1);
			adiacente.setY(this.getY());
			break;
			
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + dir);
		}
		return adiacente;
	}

	
    
}
