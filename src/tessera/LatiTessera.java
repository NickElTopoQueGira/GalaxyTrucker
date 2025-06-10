package tessera;

import java.util.Random;

public class LatiTessera {

    private TipoConnettoriTessera up;
    private TipoConnettoriTessera left;
    private TipoConnettoriTessera right;
    private TipoConnettoriTessera down;

    /**
     * costruttore
     */
    public LatiTessera() {
        GeneraLatiTessera();
    }

    /**
     * genera i lati tessera tramite delle random
     */
    public void GeneraLatiTessera() {
        do {
            this.up = randomTipo();
            this.down = randomTipo();
            this.left = randomTipo();
            this.right = randomTipo();
        } while (!this.verificaTessera());
    }

    /**
     * verifica se tutti i connettori sono nulli, in tal caso restituisce false
     *
     * @return false se tutti i connettori della tessera sono tipo NULLO
     */
    public boolean verificaTessera() {

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

    /**
     * metodo per impostare tutti i lati della tessera in connettore Nullo
     */
    public void setVuota() {
        this.up = TipoConnettoriTessera.NULLO;
        this.left = TipoConnettoriTessera.NULLO;
        this.down = TipoConnettoriTessera.NULLO;
        this.right = TipoConnettoriTessera.NULLO;
    }

    /**
     * random per il lato con enum TipoConnettoriTessera
     *
     * @return connettore
     */
    private TipoConnettoriTessera randomTipo() {
        TipoConnettoriTessera[] t = TipoConnettoriTessera.values();
        return t[new Random().nextInt(t.length)];
    }

    /**
     * ruota i connettori di 90 gradi in senso orario
     */
    public void ruotaLati() {
        TipoConnettoriTessera temp = this.up;
        this.up = this.left;
        this.left = this.down;
        this.down = this.right;
        this.right = temp;
    }

}
