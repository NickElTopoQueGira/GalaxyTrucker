package partita.nave;

import java.util.ArrayList;


import eccezioniPersonalizzate.ErroreTessera;
import eccezioniPersonalizzate.FinePartita;

import tessera.Coordinate;
import tessera.Tessera;
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
                // controllo se e' collegato a qualche cosa
                // TODO: agg. i controlli necessari

                // controllo della poszione

                // Verifica se e' nel centro
                if(i == centro.getX() && i == centro.getY()){
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
