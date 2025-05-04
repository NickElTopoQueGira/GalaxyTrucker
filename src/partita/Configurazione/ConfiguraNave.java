package partita.Configurazione;

import gioco.ComunicazioneConUtente;
import partita.Livelli;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;
import partita.nave.Nave;
import partita.nave.NaveLvl1;
import partita.nave.NaveLvl2;
import partita.nave.NaveLvl3;

public class ConfiguraNave{
    private ComunicazioneConUtente com;
    private Livelli livello;
    private Colori colorePedina;
    
    public ConfiguraNave(Giocatore giocatore){
        com = ComunicazioneConUtente.getIstanza();
        this.colorePedina = giocatore.getPedina().getColorePedina();
    }

    public Nave creaNave(Livelli livello){
        Nave nave = null;
        switch(this.livello){
			case PRIMO->{
				nave = new NaveLvl1(this.colorePedina);
			}
			case SECONDO->{
				nave = new NaveLvl2(this.colorePedina);
			}
			case TERZO->{
				nave = new NaveLvl3(this.colorePedina);
			}
		}

        return nave;
    }
}
