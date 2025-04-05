package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;
import tessera.Centro;
import tessera.TipoTessera;

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
        // colonne
        //   0  1  2  3  4  5  6 
            {0, 0, 0, 1, 0, 0, 0},  // riga 0
            {0, 0, 1, 1, 1, 0, 0},  // riga 1
            {0, 1, 1, 2, 1, 1, 0},  // riga 2
            {0, 1, 1, 1, 1, 1, 0},  // riga 3
            {0, 1, 1, 0, 1, 1, 0}   // riga 4
    };

    private static final int numeroRighe        = 5;
    private static final int numeroColonne      = 7;
    private static final int[] coordinateCentro = {2, 3};

    public NaveLvl1(){
        super();

        // inizializzazione della nave con elementi nulli
        for(int i = 0; i < numeroRighe; i++){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int j = 0; j < numeroColonne; j++){
                if(i == coordinateCentro[0] && j == coordinateCentro[1]){
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
    public void inserisciTessera(int i, int j, Tessera tessera) throws ErroreTessera {
        // Controllo sulla posizione
        if(i >= 0 && i <= numeroRighe){
            if(j >= 0 && j < numeroColonne){
                // controllo della poszione

                // Verifica se e' nel centro
                if(i == coordinateCentro[0] && i == coordinateCentro[1]){
                    throw new ErroreTessera("Posizione non valida!! ");
                }

                // verifca se il pezzo lo si vuole mettere in una posizione non valida
                if(NAVE_DEF[i][j] == 0){
                    throw new ErroreTessera("Non puoi posizionare il pezzo in questa posizione");
                }

                // verifca se il pezzo lo si vuole mettere in una posizione gia' occupata
                if(null == super.nave.get(i).get(j)){
                    throw new ErroreTessera("Posizione gia' occupata");
                }

                // verifica se eil pezzo e' un motore la sua posizione
                /**
                 * Il motore non pue' essere messo:
                 * - sopra il modulo centrale
                 * - direttamente spora un pezzo 
                 * - e' in una posizione non corretta 
                 * - 
                 */
            }
            else{
                throw new ErroreTessera("Posizone asse y non corretta");
            }
        }
        else{
            throw new ErroreTessera("Posizione asse x non corretta");
        }
    }

    @Override 
    public void rimuoviTessera(int i, int j) throws ErroreTessera {

    }

    @Override 
    public void rimuoviRiga(int i) throws ErroreTessera {

    }

    @Override
    public void rimuoviColonna(int i) throws ErroreTessera {

    }

}
