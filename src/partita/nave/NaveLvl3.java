package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
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
            {0, 0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 1, 1, 1, 0, 0, 0},
            {1, 0, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 2, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 0, 1, 1}

    };

    private static final int numeroRighe    = 6;
    private static final int numeroColonne  = 9;

    public NaveLvl3(){
        super();

        // inizializzazione della nave con elementi nulli
        for(int i = 0; i < numeroRighe; i++){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int j = 0; j < numeroColonne; j++){
                riga.add(null);
            }
            nave.add(riga);
        }
    }

    @Override
    public void inserisciTessera(int i, int k, Tessera tessera) throws ErroreTessera {

    }

    @Override 
    public void rimuoviTessera(int i, int k) throws ErroreTessera {

    }

    @Override 
    public void rimuoviRiga(int i) throws ErroreTessera {

    }

    @Override
    public void rimuoviColonna(int i) throws ErroreTessera {

    }


}
