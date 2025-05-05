package tabellone;

import java.util.ArrayList;

import carte.Carta;
import eccezioniPersonalizzate.LivelloErrato;
import gioco.ComunicazioneConUtente;
import partita.Livelli;
import partita.Pedina;

public class Tabellone{
	private final ComunicazioneConUtente com;
	private final Livelli livello;
	private ArrayList<Pedina> elencoPedine;
	private ArrayList<Carta> mazzo;
	private int numeroCaselle;

	public Tabellone(Livelli livello){
		com = ComunicazioneConUtente.getIstanza();
		this.livello = livello;
		this.elencoPedine = new ArrayList<Pedina>();
		this.mazzo = new ArrayList<Carta>();
		
		try{
			this.numeroCaselle = getNumeroCaselleE();
		}catch(LivelloErrato le){
			com.printError(le.getMessage());
		}
	}

	public void aggiungiPedina(Pedina pedina){
		this.elencoPedine.add(pedina);
	}

	public int getNumeroCaselle() { return this.numeroCaselle; }

	private int getNumeroCaselleE() throws LivelloErrato{
		switch(this.livello){
			case PRIMO ->{
				return Livelli.getCaselleXLivello(Livelli.PRIMO);
			}
			case SECONDO ->{
				return Livelli.getCaselleXLivello(Livelli.SECONDO);
			}
			case TERZO ->{
				return Livelli.getCaselleXLivello(Livelli.TERZO);
			}
			default ->{
				throw new LivelloErrato("Livello immesso errato");
			}
		}
	}

	//---------------------------------- MAZZO ----------------------------------
	// TODO: chiedere
}
