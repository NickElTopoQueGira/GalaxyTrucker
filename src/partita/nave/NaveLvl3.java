package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;
import tessera.Centro;
import tessera.Coordinate;
import tessera.Posizione;
import tessera.Tessera;
import tessera.TesseraVuota;

public class NaveLvl3 extends Nave{
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

    private final int[][] NAVE_DEF ={
        // Colonne
        //   0  1  2  3  4  5  6  7  8
            {0, 0, 0, 0, 1, 0, 0, 0, 0},    // riga 0
            {0, 0, 0, 1, 1, 1, 0, 0, 0},    // riga 1
            {1, 0, 1, 1, 1, 1, 1, 0, 1},    // riga 2
            {1, 1, 1, 1, 2, 1, 1, 1, 1},    // riga 3
            {1, 1, 1, 1, 1, 1, 1, 1, 1},    // riga 4
            {1, 1, 0, 1, 1, 1, 0, 1, 1}     // riga 5

    };

    private final int numeroRighe        = 6;
    private final int numeroColonne      = 9;
    private Coordinate coordinateCentro;

    public NaveLvl3(Colori coloreNave){
        super(coloreNave);
        for(int i = 0; i < this.numeroRighe; i += 1){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int j = 0; j < this.numeroColonne; j += 1){
                if(NAVE_DEF[i][j] == 2){
                    // creo il centro
                    this.coordinateCentro = new Coordinate(j, i);
                    try {
						riga.add(new Centro(coloreNave, coordinateCentro));
					} catch (ErroreTessera e) {
						e.printStackTrace();
					}
                }else{
                	// tessera vuota
                	if(NAVE_DEF[i][j] == 0) {
                		try {
							riga.add(new TesseraVuota(j, i, Posizione.ESTRENA ));
						} catch (ErroreTessera e) {
							e.printStackTrace();
						}    
                	}else if(NAVE_DEF[i][j] == 1){
                		try {
							riga.add(new TesseraVuota(j, i, Posizione.INTERNA ));
						} catch (ErroreTessera e) {
							e.printStackTrace();
						}                       	
                	}
                }
            }
            this.nave.add(riga);
        }
    }

    @Override
    protected int[][] getMATRIX(){ return NAVE_DEF; }

    @Override
    public int getRighe(){ return numeroRighe; }

    @Override
    public int getColonne(){ return numeroColonne; }

    @Override
    protected Coordinate getCoordinateCentro() { 
        return coordinateCentro;
    }
    
	
	@Override
	public int getCentroNaveX() {
		return 4;
	}
	
	@Override
	public int getCentroNaveY() {
		return 3;
	}

	
	//indici per stampa coordinate nave
	@Override
	public int getConfineNaveX() {
		return 12;
	}


	@Override
	public int getConfineNaveY() {
		return 10;
	}

	@Override
	public int getInizioNaveX() {
		return 3;
	}

	@Override
	public int getInizioNaveY() {
		return 4;
	} 
}
