package partita.nave;

public class NaveLvl1 extends Nave {

    /**
     * Plancia nave
     * Legenda:
     * 0 = no pezzo
     * 1 = si pezzo
     * 2 = centro

     *      4   5   6   7   8   9   10
     * 5    0   0   0   1   0   0   0
     * 6    0   0   1   1   1   0   0
     * 7    0   1   1   2   1   1   0
     * 8    0   1   1   1   1   1   0
     * 9    0   1   1   0   1   1   0
     * */

    private static final int[][] NAVE_DEF = {
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 1, 1, 0, 0},
            {0, 1, 1, 2, 1, 1, 0},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 1, 1, 0, 1, 1, 0}
    };

    public NaveLvl1(){
        super();
    }

    @Override
    public void assembla() {
        // Implementazione specifica per il livello 1
    }
}
