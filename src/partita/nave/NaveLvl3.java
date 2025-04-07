package partita.nave;

import java.util.ArrayList;

import tessera.Centro;
import tessera.Coordinate;
import tessera.Tessera;

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
        // Colonne
        //   0  1  2  3  4  5  6  7  8
            {0, 0, 0, 0, 1, 0, 0, 0, 0},    // riga 0
            {0, 0, 0, 1, 1, 1, 0, 0, 0},    // riga 1
            {1, 0, 1, 1, 1, 1, 1, 0, 1},    // riga 2
            {1, 1, 1, 1, 2, 1, 1, 1, 1},    // riga 3
            {1, 1, 1, 1, 1, 1, 1, 1, 1},    // riga 4
            {1, 1, 0, 1, 1, 1, 0, 1, 1}     // riga 5

    };

    private static final int numeroRighe        = 6;
    private static final int numeroColonne      = 9;
    private static final Coordinate coordinateCentro = new Coordinate(4, 3);

    public NaveLvl3(){
        super();

        // inizializzazione della nave con elementi nulli
        for(int i = 0; i < numeroRighe; i++){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int j = 0; j < numeroColonne; j++){
                if(i == coordinateCentro.getX() && j == coordinateCentro.getY()){
                    riga.add(new Centro());
                }
                else{
                    riga.add(null);
                }
                
            }
            nave.add(riga);
        }
    }

    @Override
    public int[][] getMATRIX(){ return NAVE_DEF; }

    @Override
    public int getRighe(){ return numeroRighe; }

    @Override
    public int getColonne(){ return numeroColonne; }

    @Override
    public Coordinate getCoordinateCentro() { 
        return new Coordinate(coordinateCentro.getX(), coordinateCentro.getX()); 
    }
}
