package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreTessera;
import tessera.Tessera;

public abstract class Nave {
    protected final ArrayList<Tessera> componentiPrenotati;
    protected ArrayList<ArrayList<Tessera>> nave;

    public Nave(){
        this.componentiPrenotati = new ArrayList<Tessera>(2);
        this.nave = new ArrayList<>();
    }

    public abstract void inserisciTessera(int i, int j, Tessera tessera) throws ErroreTessera;
    public abstract void rimuoviTessera(int i, int j) throws ErroreTessera;
    public abstract void rimuoviRiga(int i) throws ErroreTessera;
    public abstract void rimuoviColonna(int i) throws ErroreTessera;

    @Override
    public String toString(){
        String stampaNave = "";
        for(int i = 0; i < this.nave.size(); i += 1){
            for(int j = 0; j < this.nave.get(i).size(); j += 1){
                stampaNave += this.nave.get(i).get(j) + "\t";
            }
            stampaNave += "\n";
        }
        return stampaNave;
    }
}
