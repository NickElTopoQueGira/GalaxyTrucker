package partita.nave;

import java.util.ArrayList;


import eccezioniPersonalizzate.ErroreTessera;
import eccezioniPersonalizzate.FinePartita;

import tessera.Coordinate;
import tessera.LatiTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoTessera;

public abstract class Nave {
    protected final ArrayList<Tessera> componentiPrenotati;
    protected ArrayList<ArrayList<Tessera>> nave;
    private int[][] NAVE_DEF;
    private Coordinate centro;
    private int numeroCosmonauti;
    private int numeroAlieniRossi;
    private int numeroAlieniMarroni;

    public Nave(){
        this.componentiPrenotati = new ArrayList<Tessera>(2);
        this.nave = new ArrayList<>();
        this.NAVE_DEF = getMATRIX();
        this.centro = getCoordinateCentro();

        // di default la nave ha 2 cosmonauti
        this.numeroCosmonauti = 2;
        this.numeroAlieniRossi = 0; 
        this.numeroAlieniMarroni = 0;
    }

    protected abstract int[][] getMATRIX();
    protected abstract int getRighe();
    protected abstract int getColonne();
    protected abstract Coordinate getCoordinateCentro(); 

    public void inserisciTessera(int i, int j, Tessera tessera) throws ErroreTessera{
        TipoTessera tipoDellaTessera = tessera.getTipoTessera();
        
        // Controllo sulla posizione
        if(i >= 0 && i <= getRighe()){
            if(j >= 0 && j < getColonne()){
                // controllo della poszione

                // Verifica se e' nel centro
                    throw new ErroreTessera("Posizione non valida!! ");
                }

                // verifca se il pezzo lo si vuole mettere in una posizione non valida
                if(NAVE_DEF[i][j] == 0){
                    throw new ErroreTessera("Non puoi posizionare il pezzo in questa posizione");
                }

                // verifca se il pezzo lo si vuole mettere in una posizione gia' occupata
                if(null == this.nave.get(i).get(j)){
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
                if(TipoTessera.CANNONE == tipoDellaTessera){
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

    public void rimuoviTessera(int i, int j) throws ErroreTessera{
        // Verifica delle coordinate
        if(!controllaCoodinate(i, j)){
            throw new ErroreTessera("Posizione non valida");
        }

        // rimozione tessera
        if(null == this.nave.get(i).get(j)){
            throw new ErroreTessera("Impossibile rimuovere la tessera nella posizoine specificata");
        }

        // rimozione della tessera
        this.nave.get(i).set(j, null);

    }

    public void rimuoviRiga(int i) throws ErroreTessera, FinePartita{
        if(!controllaCoodinateRrighe(i)){
            throw new ErroreTessera("La riga non esiste!!");
        }

        // rimozione della riga
        for(int index = 0; index < getColonne(); index += 1){
            System.out.println("Tessera rimossa: " + this.nave.get(i).get(index).toString());
            this.nave.get(i).set(index, null);
        }
        

        // controllo integrita' della nave
        if(controllaIntegritaNave()){
            System.out.println("Non hai subito danni importanti!");
        }
        else{
            throw new FinePartita("La nave non e' piu' dotata di nucleo!!");
        }
    }

    public void rimuoviColonna(int j) throws ErroreTessera, FinePartita{
        if(!controllaCoodinateColonne(j)){
            throw new ErroreTessera("La colonna non esiste!!");
        }

        // rimozione della colonna
        for(int index = 0; index < getRighe(); index += 1){
            System.out.println("Tessera rimossa: " + this.nave.get(index).get(j));
            this.nave.get(index).set(j, null);
        }

        // controllo integrita' della nave
        if(controllaIntegritaNave()){
            System.out.println("Non hai subito danni importanti!");
        }
        else{
            throw new FinePartita("La nave non e' piu' dotata di nucleo!!");
        }

    }

    private boolean controllaCoodinate(int i, int j){
        if(
            (i >= 0 && i <= getRighe()) &&
            (j >= 0 && j <= getColonne())
        ){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean controllaCoodinateRrighe(int i){
        return (i >= 0 && i <= getRighe());
    }

    private boolean controllaCoodinateColonne(int j){
        return (j >= 0 && j <= getRighe());
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

    public boolean controllaCollegamentoSX(Tessera tessera, int i, int k){
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

    public boolean controllaCollegamentoDX(Tessera tessera, int i, int k){
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

    public boolean controllaCollegamentoUP(Tessera tessera, int i, int k){
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

    public boolean controllaCollegamentoDW(Tessera tessera, int i, int k){
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
