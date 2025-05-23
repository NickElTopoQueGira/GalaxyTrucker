package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import partita.giocatore.Colori;
import tessera.Centro;
import tessera.Coordinate;
import tessera.Tessera;
import tessera.TesseraVuota;

public class NaveLvl1 extends Nave {
	
	private final int numeroRighe        = 5;
    private final int numeroColonne      = 7;
    private final Coordinate coordinateCentro;
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

    public NaveLvl1(Colori coloreNave){
        super(coloreNave);
        inizializzaNave();
        this.coordinateCentro = new Coordinate(3, 2);
        ComunicazioneConUtente com = ComunicazioneConUtente.getIstanza();
        // inizializzazione della nave con elementi TessereVuote
        for(int j = 0; j < numeroRighe; j++){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int i = 0; i < numeroColonne; i++){
                if(i == coordinateCentro.getX() && j == coordinateCentro.getY()){
                    try{
                    	Tessera centro=new Centro(coloreNave);
                        riga.add(centro);
                        this.centro = getCoordinateCentro();
                    }
                    catch(ErroreTessera eT){
                        com.printError(eT.getMessage());
                    }
                }
                else{
                	Tessera vuota= new TesseraVuota(i, j);

                    riga.add(vuota);
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
    protected Coordinate getCoordinateCentro() { 
        return coordinateCentro; 
    }

	@Override
	public int getCentroNaveX() {
		return 4;
	}

	@Override
	public int getConfineNaveX() {
		return 11;
	}

	@Override
	public int getCentroNaveY() {
		return 5;
	}

	@Override
	public int getConfineNaveY() {
		return 10;
	}

    
   
}
