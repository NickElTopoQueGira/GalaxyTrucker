package partita.nave;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import eccezioniPersonalizzate.ErroreCoordinate;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import eccezioniPersonalizzate.ErroreRisorse;
import partita.giocatore.Colori;
import tessera.Centro;
import tessera.Coordinate;
import tessera.LatiTessera;
import tessera.Tessera;
import tessera.TesseraVuota;
import tessera.TipoConnettoriTessera;
import tessera.TipoLato;
import tessera.TipoTessera;
import tessera.batteria.Batteria;
import tessera.cannone.Cannone;
import tessera.cannone.TipoCannone;
import tessera.merce.Merce;
import tessera.merce.Stiva;
import tessera.merce.TipoStiva;
import tessera.modulo_passeggeri.ModuloPasseggeri;
import tessera.motore.Motore;
import tessera.motore.TipoMotore;

public abstract class Nave {
    protected ArrayList<ArrayList<Tessera>> nave;
    private ArrayList<Tessera> componentiPrenotati;
    private int[][] NAVE_DEF;
    private Coordinate centro;
    private Colori coloreNave;
    private int energiaResidua;
    private int numeroConnettoriScoperti;
    private ComunicazioneConUtente stampa;
	private ArrayList<ArrayList<Tessera>> parteRestante;
	private final int inizioNaveO;
	private final int fineNaveO;
	private final int inizioNaveV;
	private final int fineNaveV;
    
    /**
     * Metodi astratti, implementati nelle sotto classi, per prendere 
     * i dati statici e privati delle sotto classi
     */
    protected abstract int[][] getMATRIX();
    public abstract int getRighe();
    public abstract int getColonne();
    protected abstract Coordinate getCoordinateCentro();

    /**
     * Costruttore della Nave
     * (fa tante cose belle) :D
     * @param coloreNave
     */
    public Nave(Colori coloreNave){
    	stampa= ComunicazioneConUtente.getIstanza();
        this.componentiPrenotati = new ArrayList<Tessera>();
        this.nave = new ArrayList<>();
        this.NAVE_DEF = getMATRIX();
        this.centro = getCoordinateCentro();
        this.coloreNave = coloreNave;
        this.fineNaveO = getConfineNaveX();
        this.inizioNaveO = getCentroNaveX();
        this.fineNaveV = getConfineNaveY();
        this.inizioNaveV = getCentroNaveY();
        this.energiaResidua = 0;
        this.numeroConnettoriScoperti = 0;
    }

	
    public abstract int getCentroNaveY();
    public abstract int getConfineNaveY();
	public abstract int getCentroNaveX();
	public abstract int getConfineNaveX();
	
	/**
=======
    // ---------------------------- TESSERE PRENOTATE ---------------------------- 
    
    /**
>>>>>>> 4a04235649a083f4ca95175e3468ae437d7a9509
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
            throw new ErroreTessera("Limite massimo di tessere prenotato raggiunto!!");
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
            throw new ErroreTessera("Tessera specificata non presente!");
        }
    }

    /**
     * Metodo per sapere se la collezione di componenti prenotati 
     * e' vuota o e' piena
     * 
     * @return true -> vuota 
     * 		   false -> piena
     */
    public boolean isComponentiPrenotatiEmpty(){
    	return this.componentiPrenotati.isEmpty();
    }

    // ---------------------------- INSERIMENTO TESSERE NELLA NAVE ----------------------------
    
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
            // Verifica se la sua tessera viene messa nel centro
            if(coordinata.getX() == centro.getX() && coordinata.getY() == centro.getY()){
                throw new ErroreTessera("Posizione non valida!");
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
                if(false == verificaInserimentoCannone(coordinata, tessera)){
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
    private boolean verificaInserimentoCannone(Coordinate coordinate, Tessera tessera) {
        
    	Cannone cannone = (Cannone) tessera;
        Tessera vuota= new TesseraVuota(coordinate.getX(),coordinate.getY());
		
        // controllo se la cella subito dopo il cannone e' presente un blocco
        try {
            switch (cannone.getLatoCannone()) {
                case UP -> {
                    if (nave.get(coordinate.getX()).get(coordinate.getY() - 1) == vuota) {
                        return true;
                    }
                }
                case LEFT -> {
                    if (nave.get(coordinate.getX() - 1).get(coordinate.getY()) == vuota) {
                        return true;
                    }
                }
                case RIGHT -> {
                    if (nave.get(coordinate.getX() + 1).get(coordinate.getY()) == vuota) {
                        return true;
                    }
                }
                case DOWN -> {
                    if (nave.get(coordinate.getX()).get(coordinate.getY() + 1) == vuota) {
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
    	
    	Tessera vuota= new TesseraVuota(coordinate.getX(),coordinate.getY());
		
        // controllo se il pezzo subito sotto e' libero
        try{
            if(this.nave.get(coordinate.getX() + 1).get(coordinate.getY()) == vuota){
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
     * @throws ErroreGiocatore 
     */
    public void rimuoviTessera(Coordinate coordinate) throws ErroreTessera, ErroreGiocatore{
    	Tessera vuota=new TesseraVuota(coordinate.getX(), coordinate.getY());
    	
    	// Verifica delle coordinate
        if(!controllaCoordinate(coordinate)){
            throw new ErroreTessera("Posizione non valida");
        }

        // rimozione tessera
        if(vuota == this.nave.get(coordinate.getX()).get(coordinate.getY())){
            throw new ErroreTessera("Impossibile rimuovere la tessera nella posizoine specificata");
        }

        // rimozione della tessera
        
        this.nave.get(coordinate.getX()).set(coordinate.getY(), vuota);
        
        //controlla se esiste ancora la nave ed in caso chiama getTroncamento
        if(this.controllaEsistenzaNave()) {
        	this.nave = this.getTroncamentoNave();
        }else {
        	throw new ErroreGiocatore("La nave è stata totalmete distrutta");
            
        }
    }
    
    /**
     * metodo che crea una lista di tronconiNave e fa scegliere all'utente quale tenere
     * @return troncone di nave scelta
     */
    private ArrayList<ArrayList<Tessera>> getTroncamentoNave() {
    	Set<ArrayList<ArrayList<Tessera>>> troncamentiNave =new LinkedHashSet<ArrayList<ArrayList<Tessera>>>();
    	
    	
    	
    	//controlla esista ancora il centro
    	if(this.controllaPresenzaCentro()) {
        	troncamentiNave.add(this.distruggiNave(centro, true));
    	}else {
    		//scorre nave e utilizza la prima tessera non vuota come centroRamificazione in distruggiNave
    		for(ArrayList<Tessera> colonne : this.nave) {
    			for(Tessera tessera : colonne) {
    				if(tessera.getTipoTessera()!=TipoTessera.VUOTA) {
    					troncamentiNave.add(this.distruggiNave(tessera.getCoordinate(), true));
    					break;
    				}
    				
    			}
        	}
    	}

    	
    	//scorre nave e utilizza ogni tessera come centroRamificazione in distruggiNave e poi mette i tronconi nel set
		for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				if(this.parteRestante.contains(tessera)) {
					troncamentiNave.add(this.distruggiNave(tessera.getCoordinate(),false));
				}
			}
    	}

    	Object[] opzioni=troncamentiNave.toArray();
    	
		return (ArrayList<ArrayList<Tessera>>) opzioni[stampa.scegliTroncamenti(opzioni)];
	}
    
    
	/**
     * distruzione nave. distrugge le tessere non collegate al centroRamificazione e le rimpiazza
     * con oggetti TesseraVuota in nave. se centroRamificazione=centro genera anche ParteRestante 
	 * @param isCentro 
	 * @param centroRamificazione
     * @return nave
     */
    public ArrayList<ArrayList<Tessera>> distruggiNave(Coordinate centroRamificazione, boolean isCentro){
    	Set<Coordinate> visitate = new HashSet<>();
    	Queue<Coordinate> daVisitare = new LinkedList<>();
    	

        daVisitare.add(centroRamificazione);
        visitate.add(centroRamificazione);

        while (!daVisitare.isEmpty()) {
       	    Coordinate corrente = daVisitare.poll(); //prende il primo elemento
			Tessera tesseraCorrente = nave.get(corrente.getX()).get(corrente.getY());
			
			for (TipoLato dir : TipoLato.values()) {
			    Coordinate adiacente = corrente.adiacente(dir);
			    Tessera tesseraAdiacente = nave.get(adiacente.getX()).get(adiacente.getY());
			
			    if (tesseraAdiacente != null &&tesseraAdiacente.getTipoTessera() != TipoTessera.VUOTA && !visitate.contains(adiacente)) {
			    	boolean condizione=false;
					switch (dir) {
					case UP: {
						if(this.controllaCollegamentoUP(tesseraCorrente, corrente)) {
							condizione=true;
						}
						break;
					}
					case LEFT: {
						if(this.controllaCollegamentoSX(tesseraCorrente, corrente)) {
							condizione=true;
						}
						break;		
							}
					case DOWN: {
						if(this.controllaCollegamentoDW(tesseraCorrente, corrente)) {
							condizione=true;
						}
						break;
						
					}
					case RIGHT: {
						if(this.controllaCollegamentoDX(tesseraCorrente, corrente)) {
							condizione=true;
						}
						break;
						
					}
					default:
						throw new IllegalArgumentException("direzione " + dir.toString()+" non valida");
					}
			    	if(condizione) {
			    		visitate.add(adiacente);
			            daVisitare.add(adiacente);
			    	}
			    }
			}
         }
        
        
        this.parteRestante=(ArrayList<ArrayList<Tessera>>) this.nave.clone();
        //sovrascrive con tessereVuote in nave le tessere che non sono state visitate
        for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				boolean check=true;
				for(Coordinate coordinateTessera : visitate) {
					if(coordinateTessera==tessera.getCoordinate()) {
						check=false;
					}
				}
				if(check) {
					
					tessera=new TesseraVuota(tessera.getCoordinate().getX(), tessera.getCoordinate().getY());
					
				}
			}
    	}
        
        //creazione parte restante
        if(isCentro) {
        	for(ArrayList<Tessera> colonne : this.parteRestante) {
    			for(Tessera tessera : colonne) {
    				for(Coordinate coordinateTessera : visitate) {
    					if(coordinateTessera==tessera.getCoordinate()) {
    						tessera=new TesseraVuota(tessera.getCoordinate().getX(), tessera.getCoordinate().getY());
    					}
    				}
    				
    			}
        	}
        }
		return nave; 
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
     * Metodo per il controllo della presenza del centro
     * 
     * @return true -> la nave ha ancora il centro | 
     *         false -> la nave non ha il centro
     */
    private boolean controllaPresenzaCentro(){

        if(TipoTessera.VUOTA == this.nave.get(centro.getX()).get(centro.getY()).getTipoTessera()){
            return false;
        }
        return true;
    }
    
    /**
     * Metodo per il controllo che la nave esista, ovvero abbia almeno una tessera diversa da TesseraVuota
     * @return false se non c'è più la nave (tutte le tessere = TesseraVuota)
     */
    private boolean controllaEsistenzaNave() {
    	for(ArrayList<Tessera> colonne : this.parteRestante) {
			for(Tessera tessera : colonne) {
				if(tessera.getTipoTessera()!=TipoTessera.VUOTA) {
					return true;
				}
				
			}
    	}
    	return false;
    	
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

        return controllaCollegamentoSX(tessera, coordinate) &&
                controllaCollegamentoDX(tessera, coordinate) &&
                controllaCollegamentoUP(tessera, coordinate) &&
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
        if(coordinate.getX() - 1 < 0 || TipoTessera.VUOTA == this.nave.get(coordinate.getX() - 1).get(coordinate.getY()).getTipoTessera()){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getX() - 1).get(coordinate.getY()).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getRight() == tessera.getLatiTessera().getLeft()){
            return true;
        }

        // controllo sei i lati sono compatibil
        if((latiTesseraNave.getRight() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO)){
                return true;
                
        }else if((latiTesseraNave.getRight() == TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.TRIPLO)){

            return true;
            
        } else{
        	
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
        if(coordinate.getX() + 1 > getRighe() || TipoTessera.VUOTA == this.nave.get(coordinate.getX() + 1).get(coordinate.getY()).getTipoTessera()){
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
                
        }else if((latiTesseraNave.getLeft() == TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.TRIPLO)){
            return true;
            
        }else {
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
        if(coordinate.getY() - 1 < 0 || TipoTessera.VUOTA == this.nave.get(coordinate.getX()).get(coordinate.getY() - 1).getTipoTessera()){
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
        else if((latiTesseraNave.getDown() == TipoConnettoriTessera.NULLO) && 
            (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.TRIPLO)){
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
        if(coordinate.getY() + 1 > getColonne() || TipoTessera.VUOTA == this.nave.get(coordinate.getX()).get(coordinate.getY() + 1).getTipoTessera()){
            return true;
        }

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getX()).get(coordinate.getY() + 1).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getUp() == tessera.getLatiTessera().getDown()){
            return true;
        }

        // controllo sei il lati sono compatibil
        if((latiTesseraNave.getUp() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO)){
                return true;
            }
        else if((latiTesseraNave.getUp() == TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.TRIPLO)){
            return true;
	        }
	    else{
        	
            return false;
        }
    }

    /**
     * Metodo per il conteggio dei connettori scoperti
     * Questo metodo va ad aggiornare this.numeroConnettoriScoperti
     * Per accedere al numero di connettori scoperti, utilizzare l'apposito metodo
     */
    public void connettoriScoperti(){ // TODO utilita?
        for(ArrayList<Tessera> colonna : this.nave){
            for(Tessera tessera : colonna){
                this.numeroConnettoriScoperti += conteggioConettoriEsposti(tessera);        
            }
        }
    }
    
    /**
     * metodo che controlla se ci sono ancora presenti cosmonauti
     * sulla nave
     * 
     * @return: if (cosmonauti > 0){ true
     * 				}else{ false }
     */
    public boolean controlloSonoPresentiCosmonauti() { // TODO da chiamare ogni volta che viene estratta una carta
    	
    	if(this.getCosmonauti() > 0) {
    		
    		return true;
    	}else {
    		
    		return false;
    	}
    }
    
    /**
     * metodo che controlla se la potenza motore è  superiore a 0
     * 
     * @return: if (potenza motore > 0){ true
     * 				}else{ false }
     */
    public boolean controlloPotenzaMotore() { //TODO da chiamare prima che venga estratta la carta "spazio aperto"
    	
    	if(this.getPotenzaMotori() > 0) {
    		
    		return true;
    	}else {
    		
    		return false;
    	}
    }

    /**
     * Metodo per il conteggio dei connettori scoperti
     * Verifica se la tessera e' collegata a qualche cosa
     * Vengono conteggiati tutti i lati che non hanno connessione
     * 
     * @param tessera
     * @return conteggio lati scopeti per tessera
     */
    private int conteggioConettoriEsposti(Tessera tessera) {
    	int conteggio = 0;
    	
    	if(!controllaCollegamentoSX(tessera, tessera.getCoordinate())) {
    		conteggio += 1;
    	}
    	
        if(!controllaCollegamentoDX(tessera, tessera.getCoordinate())) {
    		conteggio += 1;
    	}
    	
        if(!controllaCollegamentoUP(tessera, tessera.getCoordinate())) {
    		conteggio += 1;
    	}

    	if(!controllaCollegamentoDW(tessera, tessera.getCoordinate())) {
    		conteggio += 1;
    	}
    	
        return conteggio;
    }

    /**
     * Metodo per calcolare l'energia
     * dalle batterie sulla nave.
     * 
     * (da eseguire solo all'inizio della 
     * partita, indifferentemente dal livello)
     * 
     * @param tessera
     * @return totale energia nave
     */
    public int caloclaEnergia(){
        int energia = 0;

        for(ArrayList<Tessera> colonne : this.nave){
            for(Tessera tessera : colonne){
                if(tessera.getTipoTessera() == TipoTessera.BATTERIA){
                    energia += ((Batteria)tessera).getCapacity();
                }
            }
        }
        
        return energia;
    }

    /**
     * Metodo per 'utilizzare' (1 gemma) l'energia residua sulla nave. 
     * Viene generata l'eccezione ErroreRisorse quando si richiede + energia di 
     * quella disponibile
     *
     * @throws ErroreRisorse
     */
    public void utilizzaEnergia() throws ErroreRisorse{
        if(this.energiaResidua - 1 < 0){
            throw new ErroreRisorse("Energia insufficente");
        }
        else{
        	stampa.println("vuoi utilizzare una gemma di energia?");
        	if(stampa.conferma()) {
        		selezionaTesseraEnergia();
                this.caloclaEnergia();
        	}            
        }
    }
    
    /**
     * selezione e rimozione energia dalla tessera batteria selezionata
     * @return 
     */
    public void selezionaTesseraEnergia(){
    	stampa.println("Inserisci il numero corrispondente alla tessera a cui vuoi rimuovere energia:");
    	ArrayList<String> visual = new ArrayList<String>();
    	ArrayList<Tessera> Tessere = new ArrayList<Tessera>();
    	for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				
				if(tessera.getTipoTessera()==TipoTessera.BATTERIA) {
					Tessere.add(tessera);
					visual.add("posizione("+(this.nave.indexOf(colonne)+1)+";"+(colonne.indexOf(tessera)+1)+") "+tessera.toLegenda());
				}

			}
    	}
    	
    	boolean condizione = true;
		do{
    		stampa.visualizzaElenco(visual);
    		int indice = Integer.parseInt(stampa.consoleRead())-1;
        	
        	if(((Batteria)Tessere.get(indice)).decrese() && 
        			indice>=0 && indice<visual.size()) {
        		
        	}else {
        		condizione=false;
        	}
    	}while(condizione);
    }

    /**
     * Metodo per per inserire la merce nella stiva della nave
     * passando l'oggetto merce<
     * 
     * @param coordinate
     * @param merceDaInserire
     * @throws ErroreCoordinate
     * @throws ErroreRisorse
     * @throws ErroreTessera
     */
    public void inserisciMerce(Coordinate coordinate, Merce merceDaInserire)    
        throws ErroreCoordinate, ErroreRisorse, ErroreTessera{

        if(controllaCoordinate(coordinate)){
            Tessera tessera = this.nave.get(coordinate.getX()).get(coordinate.getY());
            if(tessera.getTipoTessera() == TipoTessera.PORTA_MERCI){
                ((Stiva)tessera).inserisciMerci(merceDaInserire);;
            }
            else{
                throw new ErroreTessera("La tessera selezionata non e' del tipo merce");
            }
        }
        else{
            throw new ErroreCoordinate("Coordinate immesse non valide");
        }
    }

    /**
     * Metodo per rimuovere una merce specifica date coordinate 
     * e l'oggetto merce
     * 
     * @param coordinate
     * @param tipoMerce
     * @throws ErroreCoordinate
     * @throws ErroreTessera
     * @throws ErroreRisorse
     */
    public void rimuoviMerce(Coordinate coordinate, Merce merce) 
        throws ErroreCoordinate, ErroreTessera, ErroreRisorse{
        
        if(controllaCoordinate(coordinate)){
            Tessera tessera = this.nave.get(coordinate.getX()).get(coordinate.getY());
            if(tessera.getTipoTessera() == TipoTessera.PORTA_MERCI){
                Stiva stiva = (Stiva)tessera;
                try{
                    stiva.rimuoviMerce(merce);
                }catch(ErroreRisorse er){
                    throw er;
                }
            }
            else{
                throw new ErroreTessera("La tessera selezionata non e' del tipo Stiva");
            }
        }
        else{
            throw new ErroreCoordinate("Coordinate immesse non valide");
        }
    }

    /**
     * Metodo per rimuovere l'equipaggio data una cella di coordinate conosciute
     * e numero di equipaggio da rimuovere
     * 
     * @param coordianteModulo
     * @param qta
     * @throws ErroreCoordinate, ErroreTessera
     */
    public void rimuoviEquipaggio(Coordinate coordianteModulo, int qta) 
        throws ErroreCoordinate, ErroreTessera{

        if(controllaCoordinate(coordianteModulo)){
            Tessera tessera = this.nave.get(coordianteModulo.getX()).get(coordianteModulo.getY());

            if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                ModuloPasseggeri moduloPasseggeri = (ModuloPasseggeri)tessera;
                moduloPasseggeri.rimuoviEquipaggio();
            }else if(tessera.getTipoTessera() == TipoTessera.CENTRO){
            	Centro centro = (Centro)tessera;
                centro.rimuoviPasseggeri(-1);
            }else{
                throw new ErroreTessera("La cella selezionata non e' del tipo modulo");
            }
        }
        else{
            throw new ErroreCoordinate("Coordinate immesse non valide");
        }
    }
    
    /**
     * Metodo per la visualizzazione degli elementi presenti 
     * in componenti prenotati
     * 
     * @return messaggio da stampare sulla console
     */
    public String tesserePrenotateToString(){
    	String temp = "";
    	temp += "Tessere Prenotate:\n";
    	for(int k=0; k<this.componentiPrenotati.size();k++) {
			temp=temp+"  "+(k+1)+"  ";
		}
		temp+="\n";
    	for(int i =0; i<5; i++) {
    		for(int j = 0; j < this.componentiPrenotati.size(); j += 1){
        		temp=temp + this.componentiPrenotati.get(j).getriga(i)+ " "; 
            }
    		temp += "\n";
    	}
        temp = temp + "\n";
        
		return temp;
    }
    
    /**
     * Stampa della nave
     * @return 
     */
    @Override
    public String toString(){
    	String temp = "";
    	temp += "Nave del giocatore: " + this.coloreNave.getname() + "\n";
        for(int i = 0; i < this.nave.size(); i += 1){
        	for(int k =0; k<5; k++) {
        		for(int j = 0; j < this.nave.get(i).size(); j += 1){
            		if(null != temp) {
                		temp=temp + this.nave.get(i).get(j).getriga(k)+ " "; 
                	}
	            }
        		if(k==2) {
        			temp+=""+(i+this.inizioNaveV)+"\n";
        		}else {
        			temp += "│\n";
        		}
        		
        	}
        	for(int j=this.inizioNaveO; j<this.fineNaveO;j++) {
        		temp += "      ";
        	}
            temp += "│\n";
        }
        for(int i=inizioNaveO; i<fineNaveO;i++) {
        	if(i>=10) {
        		temp = temp + "──"+i+"──";
        	}else {
        		temp = temp + "──"+i+"───";
        	}
        	
        	
        }
        temp+="┘\n";
        temp+=legenda();
		return temp;
    }
    
    
    public String legenda() {
    	ArrayList<String> Legenda = new ArrayList<String>();
    	
    	for(ArrayList<Tessera> colonne : this.nave) {
    		int contatore=0;
			for(Tessera tessera : colonne) {
				String temp = "posizione("+(tessera.getCoordinate().getX()+this.inizioNaveO)+";"+(tessera.getCoordinate().getY()+inizioNaveV)+") "+tessera.toLegenda();
				Legenda.add(temp);
	    		
			}
    	}
    	return stampa.visualizzaElenco(Legenda);
    }
   
    
    
    //per il set dei troncamenti della nave
    @Override
   	public int hashCode() {
   		return Objects.hash(coloreNave, nave);
   	}
   	@Override
   	public boolean equals(Object obj) {
   		if (this == obj)
   			return true;
   		if (obj == null)
   			return false;
   		if (getClass() != obj.getClass())
   			return false;
   		Nave other = (Nave) obj;
   		return coloreNave == other.coloreNave && Objects.equals(nave, other.nave);
   	}
   	
	//-------------------- SETTER - GETTER --------------------
    public ArrayList<ArrayList<Tessera>> getPlanciaDellaNave(){ return this.nave; }

   
	public Colori getColoreNave(){ return this.coloreNave; }

    public int getEnergiaResidua(){ return this.energiaResidua; }

    public int getNumeroConnettoriScoperti(){ return this.numeroConnettoriScoperti; }

    /**
     * Metodo che restituisce il numero totale dell'equipaggio presente sulla nave
     * (cosmonauti + alieni rossi + alieni marroni)
     * 
     * @return equipaggio totale (int)
     */
    public int getEquipaggio() {
		return getCosmonauti()+getAlieniMarrone()+getAlieniViola();
	}
    
    /**
     * Metodo che restituisce solo il numero di alini viola
     * 
     * @return numero di alini viola attualmente presenti sulla nave
     */
    public int getAlieniViola() {
    	int alieniViola = 0;
    	for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
					alieniViola += ((ModuloPasseggeri)tessera).getNumeroAlieniViola();
				}
			}
    	}
		return alieniViola;
    }

    /**
     * Metodo che restituisce solo il numero di alini marroni
     * 
     * @return numero di alini marroni attualmente presenti sulla nave
     */
    public int getAlieniMarrone() {
    	int alieniMarroni = 0;
    	for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
					alieniMarroni += ((ModuloPasseggeri)tessera).getNumeroAlieniMarroni();
				}
			}
    	}
		return alieniMarroni;
    }
    
    /**
     * Metodo che restituisce solo il numero di astronauti presenti sulla nave
     * 
     * @return numero di cosmonauti attualmenrte presenti sulla nave
     */
	public int getCosmonauti() {
		int cosmonauti=0;
		for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
					cosmonauti+=((ModuloPasseggeri)tessera).getNumeroCosmonauti();
				}
				if(tessera.getTipoTessera() == TipoTessera.CENTRO) {
					cosmonauti+=((Centro)tessera).getPasseggeriCorrenti();
				}
			}
		}
		return cosmonauti;
	}
	
    /**
     * Metodo che ritorna la potenza dei motori
     * Nel conteggio e' gia' presente il bust portato dagli alini
     * 
     * @return potenza motori
     */
	public int getPotenzaMotori() {
		int potenzaMotori = 0;

		for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				if(tessera.getTipoTessera() == TipoTessera.MOTORE) {
					if(((Motore)tessera).getTipoMotore() == TipoMotore.SINGOLO){
						potenzaMotori += 1;
					}
                    else{
                        // se il motore e' doppio
                    	potenzaMotori += 2;
                    	try {
							this.utilizzaEnergia();
						} catch (ErroreRisorse e) {
							potenzaMotori -= 2;
							e.printStackTrace();
						}
						
					}
				}
			}
		}

        // aggiunta del bust degli alieni marroni
        if(!(potenzaMotori == 0)){
            /*
             * Da regolamento: 
             * Gli alieni marroni sono ottimi meccanici. Se hai un alieno
             * marrone, ricevi +2 alla potenza motrice (se la tua potenza
             * motrice senza l’alieno è 0, non ricevi questo bonus. Non
             * scenderà a spingere).
             */
            potenzaMotori += getAlieniMarrone() * 2;
        }
		return potenzaMotori;
	}
	
    /**
     * Metodo che ritorna la potenza dei motori
     * Nel conteggio e' gia' presente il bust portato dagli alini
     * 
     * @return potenza cannoni
     */
	public float getPotenzaCannoni() {
		float potenzaCannoni = .0f;

        /*
         * Metodo per assegnare la potenza di fuoco
         * (recap di quello che fa <Cannone>.calcolaValore())
         * 
         * Cannoni singoli:
         * - Puntano verso l'alto: +1
         * - Puntano di lato: +1/2 (0.5)
         * 
         * Cannoni doppi:
         * - Puntano verso l'alto: +2
         * - puntano di lato: +1
         * 
         */

		for(ArrayList<Tessera> colonne : this.nave) {
			for(Tessera tessera : colonne) {
				if(tessera.getTipoTessera() == TipoTessera.CANNONE) {
					
					potenzaCannoni += ((Cannone)tessera).calcolaValore(); 
					
					if(((Cannone)tessera).getTipoCannone()==TipoCannone.DOPPIO) {
						try {
							this.utilizzaEnergia();
						} catch (ErroreRisorse e) {
							potenzaCannoni -= ((Cannone)tessera).calcolaValore();
							e.printStackTrace();
						}
					}
				}
			}
		}

        // aggiunta del bust degli alieni rossi
        if(!(potenzaCannoni == 0.f)){
            /*
             * Da regolamento: 
             * Gli alieni viola appartengono a una specie bellicosa. Se hai
             * un alieno viola, ricevi +2 alla potenza di fuoco (se la tua
             * potenza di fuoco senza l’alieno è 0, non ricevi questo bonus.
             * Non affronterà una battaglia spaziale a tentacoli nudi).
             */
            potenzaCannoni += getAlieniViola() * 2;
        }
		return potenzaCannoni;
	}
	
    public ArrayList<Coordinate> trova(int n, int scelta){
    	
    	int caso = n;
    	
    	ArrayList<Coordinate> crd = new ArrayList<Coordinate>();
    	crd = null;
		
		for(int x=0; x<this.nave.size(); x++) {
			
			for(int y=0; y<this.nave.get(x).size(); y++) {
				
				switch(scelta) {
				case 1: caso = StiveVuote(caso, crd, x, y); break;
					
				case 2: caso = StiveNonVuote(caso, crd, x, y); break;
				
				case 3: caso = StiveSpeciali(caso, crd, x, y); break;
				
				case 4: caso = Modulo(caso, crd, x, y); break;
				
				default:	break;
				}
			}
		}
    	
    	return crd;
    }

    private int StiveVuote(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
    	Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.PORTA_MERCI) {
		    if (((Stiva) tessera).getNumeroMerciAttuale() == 0) {
		        
		    	caso++;
		    	
		    	stampa.println(""+caso+") In posizione ("+x+";"+y+") :"+tessera.toLegenda());
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }
    
	private int StiveNonVuote(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
    	Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();
		
		if (tipo == TipoTessera.PORTA_MERCI) {
		    if (((Stiva) tessera).getNumeroMerciAttuale() > 0) {
		        
		    	caso++;
		    	
		    	stampa.println(""+caso+") In posizione ("+x+";"+y+") :"+tessera.toLegenda());
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }
    
	private int StiveSpeciali(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
    	Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.PORTA_MERCI) {
		    if (((Stiva) tessera).getTipoMerciGenerale() == TipoStiva.SPECIALI) {
		        
		    	caso++;
		    	
		    	stampa.println(""+caso+") In posizione ("+x+";"+y+") :"+tessera.toLegenda());
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }
    
	private int Modulo(int caso, ArrayList<Coordinate> crd, int x, int y){
    	
    	Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.MODULO_PASSEGGERI) {
		    if (((ModuloPasseggeri) tessera).getNumeroCosmonauti() > 0) {
		        
		    	caso++;
   	
		    	stampa.println(""+caso+") In posizione ("+x+";"+y+") :"+tessera.toLegenda());
		    	 
		    	crd.add(new Coordinate(x, y));
		    }
		} else if (tipo == TipoTessera.CENTRO) {
		    if (((Centro) tessera).getPasseggeriCorrenti() > 0) {

		    	caso++;
   	
		    	stampa.println(""+caso+") In posizione ("+x+";"+y+") :"+tessera.toLegenda());
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }
}
