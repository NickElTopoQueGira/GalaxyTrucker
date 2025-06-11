package carte;

import java.util.ArrayList;
import partita.Pedina;

public abstract class Carta {

    protected int lvl;
    protected TipoCarta tipo;

    /**
     * Costruttore di Carta
     *
     * @param lvl
     */
    public Carta(int lvl) {
        this.lvl = lvl;
        this.tipo = null;
    }

    /**
     * Costruttore di Carta
     *
     * @param lvl
     * @param tipo
     */
    public Carta(int lvl, TipoCarta tipo) {

        this.lvl = lvl;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        String temp = "";
        temp = temp + "\nLivello carta:" + this.lvl
                + "\nTipo carta:" + this.tipo;

        return temp;
    }
    
    /**
     * Metodo getter del livello della carta
     * @return livello carta
     */
    public int getLvl() {
        return lvl;
    }

    /**
     * Metodo setter del livello della carta
     * @param livello carta
     */
    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    /**
     * Metodo getter del tipo della carta
     * @return tipo carta
     */
    public TipoCarta getTipo() {
        return tipo;
    }
    
    /**
     * Metodo setter del tipo della carta
     * @param tipo carta
     */
    public void setTipo(TipoCarta tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Metodo astratto dell'esecuzione della carta 
     * 
     * @param elencoPedine
     * @return elenco delle pedine dopo esecuzione della carta
     */
    public abstract ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine);
}
