package partita.nave;

public class NaveLvl2 extends Nave {

    /**
     * Plancia nave
     * Legenda:
     * 0 = no pezzo
     * 1 = si pezzo
     * 2 = centro

     *      4   5   6   7   8   9   10
     * 5    0   0   1   0   1   0   0
     * 6    0   1   1   1   1   1   0
     * 7    1   1   1   1   1   1   1
     * 8    1   1   1   1   1   1   1
     * 9    1   1   1   0   1   1   1
     * */

    private static final int[][] NAVE_DEF = {
            {0, 0, 1, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1}

    };

    public NaveLvl2(){
        super();
    }

    @Override
    public void assembla() {
        // Implementazione specifica per il livello 1
    }
}
