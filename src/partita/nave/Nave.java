package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreCoordinate;
import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;
import tessera.Coordinate;
import tessera.LatiTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoTessera;

public abstract class Nave {
    protected ArrayList<ArrayList<Tessera>> nave;
    private ArrayList<Tessera> componentiPrenotati;
    private int[][] NAVE_DEF;
    private Coordinate centro;
    private int numeroCosmonauti;
    private int numeroAlieniRossi;
    private int numeroAlieniMarroni;
    private Colori coloreNave;

    protected abstract int[][] getMATRIX();
    protected abstract int getRighe();
    protected abstract int getColonne();
    protected abstract Coordinate getCoordinateCentro();

    public Nave(Colori coloreNave){
        this.componentiPrenotati = new ArrayList<Tessera>();
        this.nave = new ArrayList<>();
        this.NAVE_DEF = getMATRIX();
        this.centro = getCoordinateCentro();
        this.coloreNave = coloreNave;

        // di default la nave ha 2 cosmonauti
        this.numeroCosmonauti = 2;
        this.numeroAlieniRossi = 0; 
        this.numeroAlieniMarroni = 0;
    }
    
    public void prenotaTessera(Tessera t) throws ErroreTessera{
        if(this.componentiPrenotati.size() > 2){
            throw new ErroreTessera("Limie massimo di tessere prenotato raggiunto!!");
        }
        else{
            this.componentiPrenotati.add(t);
        }
    }

    public Tessera togliTesseraPrenotata(int index) throws ErroreTessera{
        if(index > 0 && index < 2){
            return this.componentiPrenotati.get(index);
        }
        else{
            throw new ErroreTessera("Tessera specificata non presente!!");
        }
    }

    public String tesserePrenotateToString(){
        String s = "";

        for(int i = 0; i < this.componentiPrenotati.size(); i += 1){
            s += (i + this.componentiPrenotati.get(i).toString() + "\t");
        }
        
        // Controllo sulla posizione
        if(i >= 0 && i <= getRighe()){
            if(j >= 0 && j < getColonne()){
                // controllo della poszione

                // Verifica se e' nel centro
                if(i == centro.getX() && j == centro.getY()){
                    throw new ErroreTessera("Posizione non valida!! ");
                }

                // verifca se il pezzo lo si vuole mettere in una posizione non valida
                if(NAVE_DEF[i][j] == 0){
                    throw new ErroreTessera("Non puoi posizionare il pezzo in questa posizione");
                }

                // verifca se il pezzo lo si vuole mettere in una posizione gia' occupata
                if(null != this.nave.get(i).get(j)){
                    throw new ErroreTessera("Posizione gia' occupata");
                }

                // controllo se e' collegato a qualche cosa
                if(!controllaCollegamento(tessera, i, j)){
                    throw new ErroreTessera("La tessera non e' collegata a niente");
                }

                // verifica se il pezzo e' un motore la sua posizione
                /**
                 * Il motore non pue' essere messo:
                 * - sopra il modulo centrale
                 * - direttamente spora un pezzo
                 * 
                 * I motori per loro costruzione non possono essere girati
                */
                if(TipoTessera.MOTORE == tipoDellaTessera){
                    // se e' sopra il centro
                    if(i == centro.getY() - 1){
                        throw new ErroreTessera("I motori non possono essere messi sopra il centro");
                    }

                    // se e' direttamente sopra un pezzo
                    if(this.nave.get(i - 1).get(j) != null){
                        throw new ErroreTessera("I motori non possono essere messi direttamente sopra un pezzo");
                    }
                }

                // verifica se il pesso e' un cannone e la sua posizione
                /**
                 * Il cannone non pue' essere messo:
                 * - sotto il modulo centrale
                 * - se dal lato della canna ha attaccato un pezzo
                 * 
                 * I cannoni possono essere girati
                */
                if(TipoTessera.CANNONE == tipoDellaTessera){
                    // se e' direttamente sotto il centro
                    if(i == centro.getY() + 1){
                        throw new ErroreTessera("I cannoni non possono essere messi sotto il centro");
                    }

                    // se dal lato della canna ha attaccato un pezzo
                }


                // se tutto va a buon fine, inserisci il pezzo
                tessera.setCoordinate(new Coordinate(i, j));        // assegno le coordinate al pezzo
                this.nave.get(i).set(j, tessera);                   // inserisco il pezzo nella nave
            }
            else{
                throw new ErroreTessera("Posizone asse y non corretta");
            }
        }
        else{
            throw new ErroreTessera("Posizione asse x non corretta");
        }
    }

    public void rimuoviTessera(Coordinate coordinate) throws ErroreTessera{
        // Verifica delle coordinate
        if(!controllaCoordinate(coordinate)){
            throw new ErroreTessera("Posizione non valida");
        }

        // rimozione tessera
        if(null == this.nave.get(coordinate.getX()).get(coordinate.getY())){
            throw new ErroreTessera("Impossibile rimuovere la tessera nella posizoine specificata");
        }

        // rimozione della tessera
        this.nave.get(coordinate.getX()).set(coordinate.getY(), null);

    }

    private boolean controllaCoordinate(Coordinate coordinate){
        if(
            (coordinate.getX() >= 0 && coordinate.getX() < getRighe()) &&
            (coordinate.getY() >= 0 && coordinate.getY() < getColonne())
        ){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean controllaIntegritaNave(){
        // la partita e' persa se non si ha piu' il centro
        if(null == this.nave.get(centro.getX()).get(centro.getY())){
            return false;
        }

        return true;
    }

    
    private boolean controllaCollegamento(Tessera tessera, int i, int k){
        // true --> il pezzo e' collegato correttamente
        // false --> il pezzo non e' collegato correttamente
        /*
         * Casi controlli
         *             UP
         *   SX    ( k - 1 )   DX
         * (i - 1) |Tessera| (i + 1)
         *         ( k + 1 )
         *             DW
         */

        return controllaCollegamentoSX(tessera, i, k) ||
                controllaCollegamentoDX(tessera, i, k) ||
                controllaCollegamentoUP(tessera, i, k) ||
                controllaCollegamentoDW(tessera, i, k);    
    }

    private boolean controllaCollegamentoSX(Tessera tessera, int i, int k){
        if(i - 1 < 0 || null == this.nave.get(i - 1).get(k)){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(i - 1).get(k).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getRight() == tessera.getLatiTessera().getLeft()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getRight() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    private boolean controllaCollegamentoDX(Tessera tessera, int i, int k){
        if(i + 1 > getRighe() || null == this.nave.get(i + 1).get(k)){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(i + 1).get(k).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getLeft() == tessera.getLatiTessera().getRight()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getLeft() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    private boolean controllaCollegamentoUP(Tessera tessera, int i, int k){
        if(k - 1 < 0 || null == this.nave.get(i).get(k - 1)){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(i).get(k - 1).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getDown() == tessera.getLatiTessera().getUp()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getDown() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    private boolean controllaCollegamentoDW(Tessera tessera, int i, int k){
        if(k + 1 > getColonne() || null == this.nave.get(i).get(k + 1)){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(i).get(k + 1).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getUp() == tessera.getLatiTessera().getDown()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getUp() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    public void setCosmonauti(int numeroCosmonauti) { this.numeroCosmonauti = numeroCosmonauti; }

    public void updateNumeroCosmonauti(int numeroCosmonauti) { this.numeroCosmonauti += numeroCosmonauti; }

    public int getNumeroCosmonauti() { return this.numeroCosmonauti; }

    public void setNumeroAlieniRossi(int numeroAlieniRossi) { this.numeroAlieniRossi = numeroAlieniRossi; }

    public void updateNumeroAlieniRossi(int numeroAlieniRossi) { this.numeroAlieniRossi += numeroAlieniRossi; }

    public int getNumeroAlieniRossi() { return this.numeroAlieniRossi; }

    public void setNumeroAlieniMarroni(int numeroAlieniMarroni) { this.numeroAlieniMarroni = numeroAlieniMarroni; }

    public void updateNumeroAlieniMarroni(int numeroAlieniMarroni) { this.numeroAlieniMarroni += numeroAlieniMarroni; }

    public int getNumeroAlieniMarroni() { return this.numeroAlieniMarroni; }

    public ArrayList<ArrayList<Tessera>> getPlanciaDellaNave() { return nave; }

    public Colori getColoreNave() { return this.coloreNave; }

    @Override
    public String toString(){
        String stampaNave = "";
        for(int i = 0; i < this.nave.size(); i += 1){
            for(int j = 0; j < this.nave.get(i).size(); j += 1){
            	
            	var temp = this.nave.get(i).get(j);
            	if(null != temp) {
            		stampaNave += temp.getTipoTessera().toString() + "\t";
            	}
            	else {
            		stampaNave += "vuoto \t";
            	}
            }
            stampaNave += "\n";
        }
        return stampaNave;
    }
}
