package partita.nave;

public class NaveLvl3 extends Nave {

    /**
     * Plancia nave
     * Legenda:
     * 0 = no pezzo
     * 1 = si pezzo
     * 2 = centro

     *      3   4   5   6   7   8   9   10  11
     * 4    0   0   0   0   1   0   0   0   0
     * 5    0   0   0   1   1   1   0   0   0
     * 6    1   0   1   1   1   1   1   0   1
     * 7    1   1   1   1   2   1   1   1   1
     * 8    1   1   1   1   1   1   1   1   1
     * 9    1   1   0   1   1   1   0   1   1
     * */

    private static final int[][] NAVE_DEF = {
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 0, 1, 1}

    };


    public NaveLvl3(){
        super();
    }

    @Override
    public void assembla() {
        // Implementazione specifica per il livello 1
    }
}
