package tessera;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import eccezioniPersonalizzate.ErroreAggiuntaTessera;
import eccezioniPersonalizzate.ErroreRotazione;
import eccezioniPersonalizzate.ErroreTessera;
import java.util.Set;

public abstract class Tessera {

    protected final TipoTessera tipoTessera;
    protected LatiTessera latiTessera = new LatiTessera();

    private Coordinate coordinate = new Coordinate();

    
    private final Posizione posizione;
    private static Set<Tessera> set = new LinkedHashSet<Tessera>();
    private boolean isDistrutta=false;
    private final int id;
    private static int NumeroTessereGenerate = 0;

    protected String[][] tessera_Disposizione = {
        //Righe V  0    1    2    3    4    <- colonne
        /* 0 */ {"┌", "─", "─", "─", "┐"},
        /* 1 */ {"│", " ", " ", " ", "│"},
        /* 2 */ {"│", " ", " ", " ", "│"},
        /* 3 */ {"│", " ", " ", " ", "│"},
        /* 4 */ {"└", "─", "─", "─", "┘"}
        };

    /**
     * Costruttore
     *
     * @param tipoTessera TipoTessera
     * @param posizione Posizione
     */
    public Tessera(TipoTessera tipoTessera, Posizione posizione) {
        this.tipoTessera = tipoTessera;
        NumeroTessereGenerate = NumeroTessereGenerate + 1;
        this.id = NumeroTessereGenerate;
        this.posizione = posizione;
    }

    /**
     * getter Posizione (interna o esterna alla nave)
     * @return posizione enum
     */
    public Posizione getPosizione() {
        return this.posizione;

    }

    /**
     * getter oggetto latiTessera contenente i connettori
     * @return latitessera
     */
    public LatiTessera getLatiTessera() {
        return latiTessera;
    }

    /**
     * getter tipo tessera
     * @return enum TipoTessera
     */
    public TipoTessera getTipoTessera() {
        return tipoTessera;
    }

    /**
     * getter coordinate della tessera
     * @return oggetto coordinate
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * setter coordinate della tessera
     * @param oggetto coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Metodo che trasforma il set delle tessere in un arraylist per essere
     * ritornato
     *
     * @return lista delle tessere generate
     */
    public static ArrayList<Tessera> getListaTessere() {
        ArrayList<Tessera> listaA = new ArrayList<>(set);
        return listaA;

    }

    /**
     * rimuove dal set delle tessere generate la tessera t
     *
     * @param t Tessera da rimuovere
     */
    public static void removeDaListaTessere(Tessera t) {

        if (!set.isEmpty()) {
            set.remove(t);
        }

    }

    /**
     * Metodo per aggiunge al set di tessere generate la tessera, svolge anche controllo
     * duplicazione e in caso genera eccezione
     *
     * @throws ErroreAggiuntaTessera errore aggiunta tessera
     */
    public void aggiungiTessera() throws ErroreAggiuntaTessera {
        if (!set.add(this)) {
            throw new ErroreAggiuntaTessera("");
        }
    }

    /**
     * Metodo per ruotare i connettori della tessera tramite 'latitessera'
     *
     * @throws ErroreRotazione errore di rotazione
     */
    public void ruota() throws ErroreRotazione {
        this.latiTessera.ruotaLati();
    }

    /**
     * Set dei caratteri di stampa della tessera sul lato up in base ai
     * connettori
     *
     * @return Stringa connettore
     */
    private String stampaUp() {
        switch (this.getLatiTessera().getUp()) {
            case TipoConnettoriTessera.NULLO -> {
                return (" ");
            }
            case TipoConnettoriTessera.SINGOLO -> {
                return ("|");
            }
            case TipoConnettoriTessera.DOPPIO -> {
                return ("v");
            }
            case TipoConnettoriTessera.TRIPLO -> {
                return ("#");
            }
        }
        return null;
    }

    /**
     * Set dei caratteri di stampa della tessera sul lato down in base ai
     * connettori
     *
     * @return Stringa connettore
     */
    private String stampaDown() {
        switch (this.getLatiTessera().getDown()) {
            case TipoConnettoriTessera.NULLO -> {
                return (" ");
            }
            case TipoConnettoriTessera.SINGOLO -> {
                return ("|");
            }
            case TipoConnettoriTessera.DOPPIO -> {
                return ("^");
            }
            case TipoConnettoriTessera.TRIPLO -> {
                return ("#");
            }
        }
        return null;
    }

    /**
     * Set dei caratteri di stampa della tessera sul lato left in base ai
     * connettori
     *
     * @return Stringa connettore
     */
    private String stampaLeft() {
        switch (this.getLatiTessera().getLeft()) {
            case TipoConnettoriTessera.NULLO -> {
                return (" ");
            }
            case TipoConnettoriTessera.SINGOLO -> {
                return ("-");
            }
            case TipoConnettoriTessera.DOPPIO -> {
                return (">");
            }
            case TipoConnettoriTessera.TRIPLO -> {
                return ("#");
            }
        }
        return null;
    }

    /**
     * Set dei caratteri di stampa della tessera sul lato right in base ai
     * connettori
     *
     * @return Stringa connettore
     */
    private String stampaRight() {
        switch (this.getLatiTessera().getRight()) {
            case TipoConnettoriTessera.NULLO -> {
                return (" ");
            }
            case TipoConnettoriTessera.SINGOLO -> {
                return ("-");
            }
            case TipoConnettoriTessera.DOPPIO -> {
                return ("<");
            }
            case TipoConnettoriTessera.TRIPLO -> {
                return ("#");
            }
        }
        return null;
    }

    /**
     * Unisce i vari lati per stampa di tessera e aggiunge il carattere
     * centrale identificativo del Tipotessera
     */
    private void generaTessera_Disposizione() {
        this.tessera_Disposizione[2][2] = this.getTipoTessera().getTipo();
        this.tessera_Disposizione[1][2] = stampaUp();
        this.tessera_Disposizione[3][2] = stampaDown();
        this.tessera_Disposizione[2][1] = stampaLeft();
        this.tessera_Disposizione[2][3] = stampaRight();
    }

    /**
     * Metodo to string della tessera
     *
     * @return stringa da poter essere stampata rappresentate la tessera in char
     * art
     */
    @Override
    public String toString() {
        String temp = "";
        if (this.posizione == Posizione.ESTRENA) {
            temp += "\033[1;94m" + "\u001B[48m";
        }

        generaTessera_Disposizione();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                temp = temp + this.tessera_Disposizione[i][j];
            }
            temp += "\n";
        }
        temp += "\u001B[0m";
        temp += this.toLegenda() + "\n\n";

        return temp;

    }

    /**
     * Metodo che fornita la riga della nave in j, restituisce la stringa temp
     * in cui è presente la prima riga di caratteri della matrice di Tessera
     *
     * @param j int
     * @return stringa da stampare
     */
    public String getriga(int j) {
        generaTessera_Disposizione();
        String temp = "";
        if (this.posizione == Posizione.ESTRENA) {
            temp += "\033[1;94m" + "\u001B[48m";
        }

        temp = temp + this.tessera_Disposizione[j][0] + this.tessera_Disposizione[j][1]
                + this.tessera_Disposizione[j][2] + this.tessera_Disposizione[j][3]
                + this.tessera_Disposizione[j][4];
        temp += "\u001B[0m";

        return temp;
    }

    /**
     * getter id univoco della tessera
     * @return id (int)
     */
    public int getId() {
        return id;
    }
    
    /**
     * Metodo che specifica se la tessera è stat distrutta
     * @return true se tessera distrutta e false se non lo è
     */
    public boolean isDistrutta() {
		return isDistrutta;
	}

    /**
     * setter boolean isDistrutta per rendere la tessera distrutta (true) o meno (false)
     * @param isDistrutta
     */
	public void setDistrutta(boolean isDistrutta) {
		this.isDistrutta = isDistrutta;
	}
    

    /**
     * Metodo che crea la descrizione della tessera
     *
     * @return stringa descrittiva della tessera
     */
    public abstract String toLegenda();

    // Hashcode ed equals gestiscono il tutto tramite id tessera
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tessera other = (Tessera) obj;
        return id == other.id;
    }

	

}
