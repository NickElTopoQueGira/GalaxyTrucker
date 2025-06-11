package tessera;

import java.util.Objects;

public class Coordinate {


	private int x;
    private int y;

    /**
     * costruttore vuoto
     */
    public Coordinate() {
    }

    /**
     * costruttore per fissare anche i valori
     * @param x
     * @param y
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter coordinata x
     * @return coordinata x (int)
     */
    public int getX() {
        return this.x;
    }

    /**
     * getter coordinata y
     * @return coordinata y (int)
     */
    public int getY() {
        return this.y;
    }

    /**
     * setter coordinata x
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * setter coordinata y
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * metodo che ritorna le coordinate adiacenti nella direzione specificata
     *
     * @param direzione (enum TipoLato)
     * @return
     */
    public Coordinate adiacente(TipoLato dir) {
        Coordinate adiacente = new Coordinate();
        switch (dir) {
            case UP -> {
                adiacente.setX(this.getX());
                adiacente.setY(this.getY() - 1);
            }
            case LEFT -> {
                adiacente.setX(this.getX() - 1);
                adiacente.setY(this.getY());
            }
            case DOWN -> {
                adiacente.setX(this.getX());
                adiacente.setY(this.getY() + 1);

            }
            case RIGHT -> {
                adiacente.setX(this.getX() + 1);
                adiacente.setY(this.getY());

            }
            default ->
                throw new IllegalArgumentException("Unexpected value: " + dir);
        }
        return adiacente;
    }
    

    /**
     * hascode che lavora su x e y
     */
    @Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

    /**
     * equals che lavora su x e y
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Coordinate))
			return false;
		Coordinate other = (Coordinate) obj;
		return x == other.x && y == other.y;
	}
    

}
