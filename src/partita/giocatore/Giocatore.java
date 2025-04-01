package partita.giocatore;

public class Giocatore {
	private final String nome; 
	private final Colori colorePedina; 
	private int crediti; 
	private int posizioneSulTabellone; 
	// private final Nave nave;
	
	public Giocatore(String nome, Colori colorePedina) {
		this.nome = nome;
		this.colorePedina = colorePedina;
		this.crediti = 0; 
		this.posizioneSulTabellone = 0;
	}
	
	public void aggiornaCrediti(int crediti) {
		this.crediti += crediti;
	}
	
	public int getCrediti() { return this.crediti; }
	
	// TODO: muovi, risolvi carta
	
}
