package partita.nave;

import java.util.ArrayList;
import partita.giocatore.Colori;
import tessera.Centro;
import tessera.Coordinate;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TesseraVuota;

public class NaveLvl1 extends Nave{
	
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

    private final int[][] NAVE_DEF = {
        // colonne
        //   0  1  2  3  4  5  6 
            {0, 0, 0, 1, 0, 0, 0},  // riga 0
            {0, 0, 1, 1, 1, 0, 0},  // riga 1
            {0, 1, 1, 2, 1, 1, 0},  // riga 2
            {0, 1, 1, 1, 1, 1, 0},  // riga 3 
            {0, 1, 1, 0, 1, 1, 0}   // riga 4
    };

	private final int numeroRighe        = 5;
    private final int numeroColonne      = 7;
    private Coordinate coordinateCentro;

    /**
     * Costruttore della nave
     * La nave viene creata in base al livello della partita
     * */
    public NaveLvl1(Colori coloreNave){
        super(coloreNave);
        
        for(int i = 0; i < this.numeroRighe; i += 1){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int j = 0; j < this.numeroColonne; j += 1){
                if(NAVE_DEF[i][j] == 2){
                    // creo il centro
                    this.coordinateCentro = new Coordinate(j, i);
                    riga.add(new Centro(coloreNave, coordinateCentro));
                }else{
                    // tessera vuota
                	if(NAVE_DEF[i][j] == 0) {
                		riga.add(new TesseraVuota(j, i, Posizione.ESTRENA ));    
                	}else if(NAVE_DEF[i][j] == 1){
                		riga.add(new TesseraVuota(j, i, Posizione.INTERNA ));                       	
                	}
                }
            }
            this.nave.add(riga);
            
        }
    }

    /**
     * Metodo per acquisire la matrice delle posizioni
     *
     * @return matrice della nave int[][]
     * */
    @Override
    public int[][] getMATRIX(){ return NAVE_DEF; }

    /**
     * Metodo per acquisire il numero di righe della nave
     *
     * @return int numero di righe nella nave
     * */
    @Override
    public int getRighe(){ return numeroRighe; }

    /**
     * Metodo per acquisire il numero di colonne della nave
     *
     * @return int numero di colonne nella nave
     **/
    @Override
    public int getColonne(){ return numeroColonne; }

    /**
     * Metodo per acquisire le coordinare del centro
     *
     * @return Coordinare del centro
     **/
    @Override
    public Coordinate getCoordinateCentro() { 
        return coordinateCentro; 
    }

    /**
     * Metodo per acquisire il confine della nave nell'asse x
     * Utilizzato nella stampa, nei controlli e nei calcoli
     *
     * @return int confine x
     * */
	@Override
	public int getConfineNaveX() {
		return 10;
	}

    /**
     * Metodo per acquisire il confine iniziale della nave nell'asse x
     *
     * @return int confine x
     * */
	@Override
	public int getInizioNaveX() {
		return 4;
	}

    /**
     * Metodo per acquisire il confine della nave nell'asse y
     * Utilizzato nella stampa, nei controlli e nei calcoli
     *
     * @return int confine y
     * */
	@Override
	public int getInizioNaveY() {
		return 5;
	}

    /**
     * Metodo per acquisire il confine iniziale della nave nell'asse y
     *
     * @return int confine x
     * */
	@Override
	public int getConfineNaveY() {
		return 9;
	}   
}
