package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;
import tessera.Centro;
import tessera.Coordinate;
import tessera.Tessera;

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
     * 7    1   1   1   2   1   1   1
     * 8    1   1   1   1   1   1   1
     * 9    1   1   1   0   1   1   1
     * */

    private static final int[][] NAVE_DEF = {
        // Colonne
        //   0  1  2  3  4  5  6
            {0, 0, 1, 0, 1, 0, 0},  // riga 0
            {0, 1, 1, 1, 1, 1, 0},  // riga 1
            {1, 1, 1, 2, 1, 1, 1},  // riga 2
            {1, 1, 1, 1, 1, 1, 1},  // riga 3
            {1, 1, 1, 0, 1, 1, 1}   // riga 4

    };

    private static final int numeroRighe        = 5;
    private static final int numeroColonne      = 7;
    private static final Coordinate coordinateCentro = new Coordinate(2, 3);

    public NaveLvl2(Colori coloreNave){
        super(coloreNave);

        // inizializzazione della nave con elementi nulli
        for(int i = 0; i < numeroRighe; i++){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int j = 0; j < numeroColonne; j++){
                if(i == coordinateCentro.getX() && j == coordinateCentro.getY()){
                    try{
                        riga.add(new Centro(coloreNave));
                    }
                    catch(ErroreTessera eT){
                        System.err.println(eT.getMessage());
                    }
                }
                else{
                    riga.add(null);
                }
                
            }
            nave.add(riga);
        }
    }

    @Override
    protected int[][] getMATRIX(){ return NAVE_DEF; }

    @Override
    protected int getRighe(){ return numeroRighe; }

    @Override
    protected int getColonne(){ return numeroColonne; }

    @Override
    protected Coordinate getCoordinateCentro() { 
        return new Coordinate(coordinateCentro.getX(), coordinateCentro.getY()); 
    }
}
