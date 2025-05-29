package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
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

    public NaveLvl1(Colori coloreNave){
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
    public int[][] getMATRIX(){ return NAVE_DEF; }

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
		return 3;
	}
	
	@Override
	public int getCentroNaveY() {
		return 2;
	}

	
	
	//indici per stampa coordinate nave
	@Override
	public int getConfineNaveX() {
		return 11;
	}


	@Override
	public int getConfineNaveY() {
		return 10;
	}

	@Override
	public int getInizioNaveX() {
		return 4;
	}

	@Override
	public int getInizioNaveY() {
		return 5;
	}   
}
