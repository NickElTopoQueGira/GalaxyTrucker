package partita.configurazione;

import gioco.StampaMessaggi;
import partita.Pedina;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class ConfiguraGiocatore{
    private StampaMessaggi stampa;
    private String nome;
    private Colori colorePedina;

    public ConfiguraGiocatore(){
    	stampa= StampaMessaggi.getIstanza();
    }

    public Giocatore craGiocatore(){
        configuraGiocatore();
        Pedina pedina = new Pedina(getColorePedina());
        return new Giocatore(this.getNome(), pedina);
    }

    private void configuraGiocatore(){
        this.setNome(stampa.setNomeGiocatore());
        this.setColorePedina(stampa.colorePedina()); 
        
        stampa.nomeGiocatore(this);
    }

	public Colori getColorePedina() {
		return colorePedina;
	}

	public void setColorePedina(Colori colorePedina) {
		this.colorePedina = colorePedina;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}   
}