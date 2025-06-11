package partita.configurazione;

import partita.Livelli;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;
import partita.nave.Nave;
import partita.nave.NaveLvl1;
import partita.nave.NaveLvl2;
import partita.nave.NaveLvl3;

public class ConfiguraNave{
    private final Colori colorePedina;
    
    /**
     * Costruttore di ConfiguraNave
     * @param giocatore
     */
    public ConfiguraNave(Giocatore giocatore){
        this.colorePedina = giocatore.getColorePedina();
    }

	/**
	 * Metodo per creare la nave in base al livello
	 * @return nave al giocatore creata in base al livello
	 * */
    public Nave creaNave(Livelli livello){
        Nave nave = null;
        switch(livello){
			case PRIMO-> nave = new NaveLvl1(this.colorePedina);
			case SECONDO-> nave = new NaveLvl2(this.colorePedina);
			case TERZO-> nave = new NaveLvl3(this.colorePedina);
		}
        return nave;
    }
}
