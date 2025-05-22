package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import partita.giocatore.Colori;
import tessera.Centro;
import tessera.Coordinate;
import tessera.Tessera;
import tessera.TesseraVuota;

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

    private final int[][] NAVE_DEF = {
        // Colonne
        //   0  1  2  3  4  5  6
            {0, 0, 1, 0, 1, 0, 0},  // riga 0
            {0, 1, 1, 1, 1, 1, 0},  // riga 1
            {1, 1, 1, 2, 1, 1, 1},  // riga 2
            {1, 1, 1, 1, 1, 1, 1},  // riga 3
            {1, 1, 1, 0, 1, 1, 1}   // riga 4

    };

    private final int numeroRighe        = 5;
    private final int numeroColonne      = 7;
    private final Coordinate coordinateCentro = new Coordinate(3, 2);

    public NaveLvl2(Colori coloreNave){
        super(coloreNave);
        ComunicazioneConUtente com = ComunicazioneConUtente.getIstanza();
        // inizializzazione della nave con elementi TessereVuote
        for(int i = 0; i < numeroRighe; i++){
            ArrayList<Tessera> riga = new ArrayList<>();
            for(int j = 0; j < numeroColonne; j++){
                if(i == coordinateCentro.getX() && j == coordinateCentro.getY()){
                    try{
                    	Tessera centro=new Centro(coloreNave);
                        riga.add(centro);
                    }
                    catch(ErroreTessera eT){
                    	com.printError(eT.getMessage());
                    }
                }
                else{
                	Tessera vuota = new TesseraVuota(i, j);
                    riga.add(vuota);
                }
                
            }
            nave.add(riga);
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
        return new Coordinate(coordinateCentro.getX(), coordinateCentro.getY()); 
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
