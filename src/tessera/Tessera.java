package tessera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.function.IntFunction;

import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Giocatore;

public abstract class Tessera {


	protected final TipoTessera tipoTessera;
	protected LatiTessera latiTessera = new LatiTessera();
	private Coordinate coordinate=new Coordinate();
	private static int currentSize = 0;
	private final Posizione posizione;
	private static LinkedHashSet<Tessera> set = new LinkedHashSet<Tessera>();
	private static ArrayList<Tessera> listaA;
	private int id=0;
	private static int contatore=0;
	
	protected String[][] tessera_Disposizione = {
			//Righe V  0    1    2    3    4    <- colonne
			/* 0 */ { "┌", "─", "─", "─", "┐" },
			/* 1 */ { "│", " ", " ", " ", "│" },
			/* 2 */ { "│", " ", " ", " ", "│" },
			/* 3 */ { "│", " ", " ", " ", "│" },
			/* 4 */ { "└", "─", "─", "─", "┘" },
			};
	

	
	/**
	 * Costruttore
	 * @param tipoTessera
	 * @throws ErroreTessera 
	 */
	public Tessera(TipoTessera tipoTessera,Posizione posizione) throws ErroreTessera{

		this.tipoTessera = tipoTessera;
		this.id=contatore+1;
		if (this.tipoTessera != TipoTessera.CENTRO && this.tipoTessera != TipoTessera.VUOTA) {
				
				aggiungiTessera();
			
		}
		this.posizione = posizione;

	}

	public Posizione getPosizione() {
		return this.posizione;
		
	}

	public LatiTessera getLatiTessera() {
		return latiTessera;
	}

	public TipoTessera getTipoTessera() {
		return tipoTessera;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public static ArrayList<Tessera> getListaTessere() {
		return listaA= new ArrayList<Tessera>(set);

	}
	
	/**
	 * rimuove dal set la tessera t
	 * @param tessera
	 */
	public static void removeDaListaTessere(Tessera t) {
		if(set.contains(t)) {
			set.remove(t);
			listaA= new ArrayList<Tessera>(set);
			setCurrentSize(-1);
		}
		

	}
	
	
	

	public static int getCurrentSize() {
		return currentSize;
	}

	public static void setCurrentSize(int edit) {
		currentSize = currentSize + edit;
	}

	
	/**
	 * aggiunge al set di tessere la tessera ed incrementa la size di 1, svolge anche controllo duplicazione
	 * ed in caso genera eccezione
	 * @throws ErroreTessera 
	 */
	private void aggiungiTessera() throws ErroreTessera{
		if(isTesseraDuplicata()) {
			throw new ErroreTessera("");
		}else {
			set.add(this);
			listaA= new ArrayList<Tessera>(set);

			setCurrentSize(+1);
		}
		
		
	}
	
	/**
	 * Verifica se la tessera e' duplicata
	 * 
	 * 
	 * @return true se lo è e false se non lo è
	 */
	private boolean isTesseraDuplicata(){
		if(!set.isEmpty()) {
			for(Tessera t : set) {
				if(t.equals(this)){
					return true;
				}	
			}
		}
		return false;
		
	}

	/**
	 * ruota i connettori della tessera tramite latitessera
	 * @throws ErroreRotazione
	 */
	public void ruota() throws ErroreRotazione {
		this.latiTessera.ruotaLati();

	}
	
	/**
	 * set dei caratteri di stampa della tessera sul lato up in base ai connettori
	 * @return Stringa connettore
	 */
	private String stampaUp() {
		switch (this.getLatiTessera().getUp()) {
		case TipoConnettoriTessera.NULLO: {
			return (" ");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("|");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return ("v");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("#");
		}
		}
		return null;
	}

	/**
	 * set dei caratteri di stampa della tessera sul lato down in base ai connettori
	 * @return Stringa connettore
	 */
	private String stampaDown() {
		switch (this.getLatiTessera().getDown()) {
		case TipoConnettoriTessera.NULLO: {
			return (" ");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("|");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return ("^");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("#");
		}
		}
		return null;
	}
	
	/**
	 * set dei caratteri di stampa della tessera sul lato left in base ai connettori
	 * @return Stringa connettore
	 */
	private String stampaLeft() {
		switch (this.getLatiTessera().getLeft()) {
		case TipoConnettoriTessera.NULLO: {
			return (" ");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("-");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return (">");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("#");
		}
		}
		return null;
	}
	
	/**
	 * set dei caratteri di stampa della tessera sul lato right in base ai connettori
	 * @return Stringa connettore
	 */
	private String stampaRight() {
		switch (this.getLatiTessera().getRight()) {
		case TipoConnettoriTessera.NULLO: {
			return (" ");
		}
		case TipoConnettoriTessera.SINGOLO: {
			return ("-");
		}
		case TipoConnettoriTessera.DOPPIO: {
			return ("<");
		}
		case TipoConnettoriTessera.TRIPLO: {
			return ("#");
		}
		}
		return null;
	}
	
	/**
	 * unisce i vari lati per stampa di tessera ed aggiunge il carattere centrale
	 * identificativo del Tipotessera
	 */
	private void generaTessera_Disposizione() {
		this.tessera_Disposizione[2][2] = this.getTipoTessera().getTipo();
		this.tessera_Disposizione[1][2] = stampaUp();
		this.tessera_Disposizione[3][2] = stampaDown();
		this.tessera_Disposizione[2][1] = stampaLeft();
		this.tessera_Disposizione[2][3] = stampaRight();
	}

	/**
	 * to string della tessera
	 * @return temp
	 */
	@Override
	public String toString() {
		String temp="";
		if(this.posizione==Posizione.ESTRENA) {
			temp+="\033[1;94m"+"\u001B[48m";
		}
		
		generaTessera_Disposizione();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				temp=temp+this.tessera_Disposizione[i][j];
			}
			temp+="\n";
		}
		temp+="\u001B[0m";
		temp+=this.toLegenda()+"\n\n";
		
		return temp;

	}

	
	/**
	 * fornita la riga della nave in j, restituisce la stringa temp in cui è presente
	 * la prima riga di caratteri della matrice di Tessera
	 * @param j
	 * @return temp
	 */
	public String getriga(int j) {
		generaTessera_Disposizione();
		String temp="";
		if(this.posizione==Posizione.ESTRENA) {
			temp+="\033[1;94m"+"\u001B[48m";
		}
		
		temp=temp+this.tessera_Disposizione[j][0]+this.tessera_Disposizione[j][1]
				+this.tessera_Disposizione[j][2]+this.tessera_Disposizione[j][3]
				+this.tessera_Disposizione[j][4];
		temp+="\u001B[0m";	
			
		return temp;
	}
	
	public abstract String toLegenda();




	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tessera other = (Tessera) obj;
		return id == other.id;
	}
	

	
	
	
}
