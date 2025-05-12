package partita.configurazione;

import gioco.ComunicazioneConUtente;

import partita.Pedina;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class ConfiguraGiocatore{
    private ComunicazioneConUtente stampa;
    private String nome;
    private Colori colorePedina;

    public ConfiguraGiocatore(){
    	stampa= ComunicazioneConUtente.getIstanza();
    	
    }

    public Giocatore creaGiocatore(){
        configuraGiocatore();
        Pedina pedina = new Pedina(getColorePedina());
        return new Giocatore(this.getNome(), pedina);
    }

    private void configuraGiocatore(){
        this.setNome(stampa.setNomeGiocatore());
        this.setColorePedina(stampa.colorePedina()); 
        
        stampa.println("Nome scelto: "+this.getColorePedina().getCodiceColore()+this.getNome()+"\u001B[0m");
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