package partita.nave;

import java.util.ArrayList;

import eccezioniPersonalizzate.ErroreCoordinate;
import eccezioniPersonalizzate.ErroreTessera;
import partita.giocatore.Colori;
import tessera.Coordinate;
import tessera.LatiTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoTessera;
import tessera.cannone.Cannone;

public abstract class Nave {
    protected ArrayList<ArrayList<Tessera>> nave;
    private ArrayList<Tessera> componentiPrenotati;
    private int[][] NAVE_DEF;
    private Coordinate centro;
    private Colori coloreNave;


    /**
     * Metodi astratti, implementati nelle sotto classi, per prendere 
     * i dati statici e privati delle sotto classi
     */
    protected abstract int[][] getMATRIX();
    protected abstract int getRighe();
    protected abstract int getColonne();
    protected abstract Coordinate getCoordinateCentro();

    /**
     * Costruttore della Nave
     * (fa tante cose belle)
     * @param coloreNave
     */
    public Nave(Colori coloreNave){
        this.componentiPrenotati = new ArrayList<Tessera>();
        this.nave = new ArrayList<>();
        this.NAVE_DEF = getMATRIX();
        this.centro = getCoordinateCentro();
        this.coloreNave = coloreNave;
    }
    
    /**
     * Metodo per prenotare le tessere da mettere.
     * Si possono prenotare al massimo 2 tessere
     * 
     * Viene generato un'errore se si vogliono prenotare piu' di 2 tessere
     * 
     * @param tessera
     * @throws ErroreTessera
     */
    public void prenotaTessera(Tessera t) throws ErroreTessera{
        if(this.componentiPrenotati.size() > 2){
            throw new ErroreTessera("Limie massimo di tessere prenotato raggiunto!!");
        }
        else{
            this.componentiPrenotati.add(t);
        }
    }

    /**
     * Metodo per rimuovere una tessera dalle prenotate
     * 
     * @param indice di posizione della tessera che si vuole usare
     * @return tessera selezionata
     * @throws ErroreTessera se si immette un valore non esistente
     */
    public Tessera togliTesseraPrenotata(int index) throws ErroreTessera{
        if(index > 0 && index < 2){
            return this.componentiPrenotati.get(index);
        }
        else{
            throw new ErroreTessera("Tessera specificata non presente!!");
        }
    }

    /**
     * Metodo per la visualizzazione degli elementi presenti 
     * in componenti prenotati
     * 
     * @return messaggio da stampare sulla console
     */
    public String tesserePrenotateToString(){
        String s = "";

        for(int i = 0; i < this.componentiPrenotati.size(); i += 1){
            s += (i + this.componentiPrenotati.get(i).toString() + "\t");
        }
        
        return s;
    }

    /**
     * Metodo per inserire una tessera nella nave durante la fase di creazione della nave
     * 
     * @param coordinata
     * @param tessera
     * @throws ErroreTessera
     * @throws ErroreCoordinate
     */
    public void inserisciTessera(Coordinate coordinata, Tessera tessera) throws ErroreTessera, ErroreCoordinate{
        if(controllaCoordinate(coordinata)){
            // Verifica se la nua tessera viene messa nel centro
            if(coordinata.getX() == centro.getX() && coordinata.getY() == centro.getY()){
                throw new ErroreTessera("Posizione non valida!!");
            }

            // verifica se il pezzo lo si vuole mettere in una posizione non valida
            if(0 == NAVE_DEF[coordinata.getX()][coordinata.getY()]){
                throw new ErroreTessera("Non puoi posizionare il pezzo in questa posizione");
            }

            /**
             * Controlli speciali sulle tessere del tipo:
             * - Cannone: non puo' avere pezzi subito davanti
             * - Motore : non puo' avere pezzi subito dietro
             */
            if(tessera.getTipoTessera() == TipoTessera.CANNONE){
                if(false == verificaInserimetnoCannone(coordinata, tessera)){
                    throw new ErroreTessera("Impossibile aggiungere il cannone in questa posizione");
                }
            }
            
            if(tessera.getTipoTessera() == TipoTessera.MOTORE){
                if(false == verificaInserimentoMotore(coordinata, tessera)){
                    throw new ErroreTessera("Impossibile aggiungere il motore in questa posizione");
                }
            }

            // verifica se il pezzo e' collegato a qualche cosa
            if(!controllaCollegamento(tessera, coordinata)){
                throw new ErroreTessera("Questo pezzo non puo' essere piazzato cosi' come e'");
            }

            // inserimento del pezzo nella nave
            tessera.setCoordinate(coordinata);
            this.nave.get(coordinata.getX()).set(coordinata.getY(), tessera);
        }
        else{
            throw new ErroreCoordinate("Coordinate immesse non valide");
        }
    }

    /**
     * Metodo per controllare se il modulo, nel caso sia un cannone, si possa mettere
     * nella posizione indicata.
     * 
     * I cannoni possono esssere messi in una cella se e solo se la cella nella direzione
     * in cui punta il cannone e' libera
     * 
     * @param coordinate
     * @param tessera
     * @return vero -> il cannone puo' essere posizionato |
     *         falso -> il cannone non puo' essere posizionato
     */
    private boolean verificaInserimetnoCannone(Coordinate coordinate, Tessera tessera) {
        Cannone cannone = (Cannone) tessera;
        // controllo se la cella subito dopo il cannone e' presente un blocco
        try {
            switch (cannone.getLatoCannone()) {
                case UP -> {
                    if (nave.get(coordinate.getX()).get(coordinate.getY() - 1) == null) {
                        return true;
                    }
                }
                case LEFT -> {
                    if (nave.get(coordinate.getX() - 1).get(coordinate.getY()) == null) {
                        return true;
                    }
                }
                case RIGHT -> {
                    if (nave.get(coordinate.getX() + 1).get(coordinate.getY()) == null) {
                        return true;
                    }
                }
                case DOWN -> {
                    if (nave.get(coordinate.getX()).get(coordinate.getY() + 1) == null) {
                        return true;
                    }
                }
            }
        } catch (IndexOutOfBoundsException iobx) {
            // se viene eseguita quesa e' perche' sto facendo un controllo ai margini della nave
            // quindi di default, posso mettere il pezzo
            return true;
        }

        return false;
    }


    /**
     * Metodo per controllare se il modulo, nel caso sia un motore, si possa mettere
     * nella posizione indicata.
     * 
     * I motori possono esssere messi in una cella se e solo se la cella subito sotto e' libera
     * 
     * @param coordinate
     * @param tessera
     * @return vero -> il motore puo' essere posizionato |
     *         falso -> il motore non puo' essere posizionato
     */
    private boolean verificaInserimentoMotore(Coordinate coordinate, Tessera tessera){
        // controllo se il pezzo subito sotto e' libero
        try{
            if(this.nave.get(coordinate.getX() + 1).get(coordinate.getY()) == null){
                return true;
            }
        }catch(IndexOutOfBoundsException iobx){
            // se viene eseguita questa parte e' perche' sto facendo il controllo in fondo alla nave
            // quindi il motore puo' essere sempre piazzato
            return true;
        }
        return false;
    }

    /**
     * Metodo per rimuovere una tessera dalla nave durante la fase di volo
     * 
     * @param coordinate
     * @throws ErroreTessera
     */
    public void rimuoviTessera(Coordinate coordinate) throws ErroreTessera{
        // Verifica delle coordinate
        if(!controllaCoordinate(coordinate)){
            throw new ErroreTessera("Posizione non valida");
        }

        // rimozione tessera
        if(null == this.nave.get(coordinate.getX()).get(coordinate.getY())){
            throw new ErroreTessera("Impossibile rimuovere la tessera nella posizoine specificata");
        }

        // rimozione della tessera
        this.nave.get(coordinate.getX()).set(coordinate.getY(), null);

    }

    /**
     * Metodo per il controllo sulle coordinate immesse dell'utente sono valide
     * 
     * @param coordinate
     * @return vero -> le coordinate sono accettabili |
     *         falso -> le coordinate non sono accettabili
     */
    private boolean controllaCoordinate(Coordinate coordinate){
        if(
            (coordinate.getX() >= 0 && coordinate.getX() < getRighe()) &&
            (coordinate.getY() >= 0 && coordinate.getY() < getColonne())
        ){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Metodo per il controllo dell'integrita' della nave
     * 
     * @return true -> la nave puo' continare con il suo volo | 
     *         false -> la nave non pu' continare con il suo volo
     */
    private boolean controllaIntegritaNave(){
        // la partita e' persa se non si ha piu' il centro
        if(null == this.nave.get(centro.getX()).get(centro.getY())){
            return false;
        }

        return true;
    }
  
    /**
     * Metodo per il controllo sui collegamenti
     * 
     *  TODO: da ripensare
     * 
     * @param tessera
     * @param coordinate
     * @return true -> il pezzo lo si puo' collegare senza problemi | 
     *         false -> il pezzo non lo si puo' collegare
     */
    private boolean controllaCollegamento(Tessera tessera, Coordinate coordinate){
        // true --> il pezzo e' collegato correttamente
        // false --> il pezzo non e' collegato correttamente
        /*
         * Casi controlli
         *             UP
         *   SX    ( k - 1 )   DX
         * (i - 1) |Tessera| (i + 1)
         *         ( k + 1 )
         *             DW
         */

        return controllaCollegamentoSX(tessera, coordinate) ||
                controllaCollegamentoDX(tessera, coordinate) ||
                controllaCollegamentoUP(tessera, coordinate) ||
                controllaCollegamentoDW(tessera, coordinate);    
    }

    /**
     * Metodo per controllare se il pezzo lo si pu' collegare a SX
     * 
     * @param tessera
     * @param coordinate
     * @return si, no
     */
    private boolean controllaCollegamentoSX(Tessera tessera, Coordinate coordinate){
        if(coordinate.getX() - 1 < 0 || null == this.nave.get(coordinate.getX() - 1).get(coordinate.getY())){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getX() - 1).get(coordinate.getY()).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getRight() == tessera.getLatiTessera().getLeft()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getRight() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    /**
     * Metodo per controllare se il pezzo lo si pu' collegare a DX
     * 
     * @param tessera
     * @param coordinate
     * @return si, no
     */
    private boolean controllaCollegamentoDX(Tessera tessera, Coordinate coordinate){
        if(coordinate.getX() + 1 > getRighe() || null == this.nave.get(coordinate.getX() + 1).get(coordinate.getY())){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getX() + 1).get(coordinate.getY()).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getLeft() == tessera.getLatiTessera().getRight()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getLeft() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    /**
     * Metodo per controllare se il pezzo lo si pu' collegare SOPRA
     * 
     * @param tessera
     * @param coordinate
     * @return si, no
     */
    private boolean controllaCollegamentoUP(Tessera tessera, Coordinate coordinate){
        if(coordinate.getY() - 1 < 0 || null == this.nave.get(coordinate.getX()).get(coordinate.getY() - 1)){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getX()).get(coordinate.getY() - 1).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getDown() == tessera.getLatiTessera().getUp()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getDown() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    /**
     * Metodo per controllare se il pezzo lo si pu' collegare a SOTTO
     * 
     * @param tessera
     * @param coordinate
     * @return si, no
     */
    private boolean controllaCollegamentoDW(Tessera tessera, Coordinate coordinate){
        if(coordinate.getY() + 1 > getColonne() || null == this.nave.get(coordinate.getX()).get(coordinate.getY() + 1)){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getX()).get(coordinate.getY() + 1).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getUp() == tessera.getLatiTessera().getDown()){
            return true;
        }

        // controllo sei il ati sono compatibil
        if((latiTesseraNave.getUp() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else{
            return false;
        }
    }

    //-------------------- SETTER - GETTER --------------------
    public ArrayList<ArrayList<Tessera>> getPlanciaDellaNave() { return nave; }

    public Colori getColoreNave() { return this.coloreNave; }


    /**
     * Stampa della nave
     */
    @Override
    public String toString(){
        String stampaNave = "";
        for(int i = 0; i < this.nave.size(); i += 1){
            for(int j = 0; j < this.nave.get(i).size(); j += 1){
            	
            	var temp = this.nave.get(i).get(j);
            	if(null != temp) {
            		temp.stampa(); 
            		//TO-DO fai una matrice non una stringa
            		//stampaNave += temp.getTipoTessera().toString() + "\t";
            	}
            	else {
            		stampaNave += "vuoto \t";
            	}
            }
            stampaNave += "\n";
        }
        return stampaNave;
    }
}
