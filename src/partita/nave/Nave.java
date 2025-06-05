package partita.nave;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import eccezioniPersonalizzate.ErroreCoordinate;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreTessera;
import eccezioniPersonalizzate.FinePartita;
import gioco.ComunicazioneConUtente;
import eccezioniPersonalizzate.ErroreRisorse;
import partita.giocatore.Colori;
import tessera.Centro;
import tessera.Coordinate;
import tessera.LatiTessera;
import tessera.Posizione;
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
import tessera.modulo_passeggeri.ModuloAttraccoAlieni;
import tessera.modulo_passeggeri.ModuloPasseggeri;
import tessera.motore.Motore;
import tessera.motore.TipoMotore;

public abstract class Nave {
    protected Troncamento nave;
    private final ArrayList<Tessera> componentiPrenotati;
    protected Coordinate centro;
    private final Colori coloreNave;
    private int energiaResidua;
    private int numeroConnettoriScoperti;
    private final ComunicazioneConUtente stampa;
    protected Troncamento parteRestante;
    private int inizioNaveO;
    private int fineNaveO;
    private int inizioNaveV;
    private int fineNaveV;
    
    /**
     * Metodi astratti, implementati nelle sotto classi
     */
    protected abstract int[][] getMATRIX();
    protected abstract Coordinate getCoordinateCentro();
    public abstract int getRighe();
    public abstract int getColonne();
    public abstract int getInizioNaveX();
    public abstract int getInizioNaveY();
    public abstract int getConfineNaveX();
    public abstract int getConfineNaveY();
	

    /**
     * Costruttore della Nave
     * (fa tante cose belle):D
     * @param coloreNave Colori
     */
    public Nave(Colori coloreNave){
        stampa= ComunicazioneConUtente.getIstanza();
        this.componentiPrenotati = new ArrayList<>();
        this.coloreNave = coloreNave;
        this.energiaResidua = 0;
        this.numeroConnettoriScoperti = 0;
        this.nave=new Troncamento();
        this.parteRestante=new Troncamento();
		this.inizializzaNave();
    }

    /**
     * Metodo per inizializzare la nave da chiamare dopo il costruttore
     */
     protected void inizializzaNave(){
        this.centro = getCoordinateCentro(); 
        this.fineNaveO = getConfineNaveX();
        this.fineNaveV= getConfineNaveY();
        this.inizioNaveO = getInizioNaveX();
        this.inizioNaveV = getInizioNaveY();
    }
    
    // ---------------------------- TESSERE PRENOTATE ---------------------------- 
    
    /**
     * Metodo per prenotare le tessere da mettere.
     * Si possono prenotare al massimo 2 tessere
     * Viene generato un'errore se si vogliono prenotare piu' di 2 tessere
     * 
     * @param t Tessera
     * @throws ErroreTessera limite massimo di tessere raggiunto
     */
    public void prenotaTessera(Tessera t) throws ErroreTessera{
        if(this.componentiPrenotati.size() >= 2){
            throw new ErroreTessera("Limite massimo di tessere prenotato raggiunto!!");
        }else{
            this.componentiPrenotati.add(t);
        }
    }

    /**
     * Metodo per rimuovere una tessera dalle prenotate
     * 
     * @param index di posizione della tessera che si vuole usare
     * @return tessera selezionata
     * @throws ErroreTessera se si immette un valore non esistente
     */
    public Tessera togliTesseraPrenotata(int index) throws ErroreTessera{
        Tessera tesseraRimossa = null;
        try{
            if(this.componentiPrenotati.contains(this.componentiPrenotati.get(index))){
                tesseraRimossa = this.componentiPrenotati.get(index);
                this.componentiPrenotati.remove(index);
            }    
        }catch(IndexOutOfBoundsException ioobe){
            throw new ErroreTessera("Tessera specificata non presente!"); 
        }

        return tesseraRimossa;
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
     * Metodo per stampare la nave
     */
    private void stampaNavetta() {
        for (int i = 0; i < this.getRighe(); i++) {
            for (int j = 0; j < this.getColonne(); j++) {
                if (this.nave.get(i).get(j).getTipoTessera() != TipoTessera.VUOTA) {
                    stampa.print(" 1");
                } else {
                    stampa.print(" 0");
                }
            }
            stampa.println("");
        }
        stampa.println("{fine debug}");
    }
    
    /**
     * Metodo per inserire una tessera nella nave durante la fase di creazione della nave
     * 
     * @param coordinata Coordinate
     * @param tessera Tessera
     * @throws ErroreTessera  errore di posizione della tessera
     * @throws ErroreCoordinate coordinate immesse non valide
     */
    public void inserisciTessera(Coordinate coordinata, Tessera tessera) throws ErroreTessera, ErroreCoordinate{
    	
        stampa.println("{inizio debug} coordinate controllata in: ("+coordinata.getX()+", "+coordinata.getY()+")");
        stampaNavetta();
        if(controllaCoordinate(coordinata)){
            // Verifica se la tessera viene su una tessera gia esistente
            if(this.nave.get(coordinata.getY()).get(coordinata.getX()).getTipoTessera() != TipoTessera.VUOTA){
                stampa.println("{debug} tessera inserita piazzata su tessera già esistente (Nave r.172)");
                throw new ErroreTessera("Posizione non valida!");
            }
 
            // verifica se il pezzo lo si vuole mettere in una posizione non valida
            // RICORDA: coordinate lavorano al contrario nelle matrici
            if(0 == this.getMATRIX()[coordinata.getY()][coordinata.getX()]){
            	stampa.println("{debug} tessera inserita piazzata fuori dalla nave (Nave r.178)");
                throw new ErroreTessera("Non puoi posizionare il pezzo in questa posizione");
            }

            /*
             * Controlli speciali sulle tessere del tipo:
             * - Cannone: non puo' avere pezzi subito davanti
             * - Motore: non puo' avere pezzi subito dietro
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
            }else {
            	stampa.println("tessera inserita correttamente");
            }

            // inserimento del pezzo nella nave
            tessera.setCoordinate(coordinata);
            stampa.println("{debug} coordinate nave piazzata in: ("+coordinata.getY()+", "+coordinata.getX()+")");
            this.nave.get(coordinata.getY()).set(coordinata.getX(), tessera);
        }
        else{
            throw new ErroreCoordinate("Coordinate immesse non valide");
        }
    }

    /**
     * Metodo per controllare se il modulo, nel caso sia un cannone, si possa mettere
     * nella posizione indicata.
     * I cannoni possono essere messi in una cella se e solo se la cella nella direzione
     * in cui punta il cannone e' libera
     * 
     * @param coordinate Coordinate
     * @param tessera Tessera
     * @return vero -> il cannone puo' essere posizionato |
     *         falso -> il cannone non puo' essere posizionato
     */
    private boolean verificaInserimentoCannone(Coordinate coordinate, Tessera tessera) {
        
    	Cannone cannone = (Cannone) tessera;
		
        // controllo se la cella subito dopo il cannone e' presente un blocco
        try {
            switch (cannone.getLatoCannone()) {
                case UP -> {
                    if (nave.get(coordinate.getY() - 1).get(coordinate.getX()).getTipoTessera() == TipoTessera.VUOTA) {
                        return true;
                    }
                }
                case LEFT -> {
                    if (nave.get(coordinate.getY()).get(coordinate.getX() - 1).getTipoTessera() == TipoTessera.VUOTA) {
                        return true;
                    }
                }
                case RIGHT -> {
                    if (nave.get(coordinate.getY()).get(coordinate.getX() + 1).getTipoTessera() == TipoTessera.VUOTA) {
                        return true;
                    }
                }
                case DOWN -> {
                    if (nave.get(coordinate.getY() + 1).get(coordinate.getX()).getTipoTessera() == TipoTessera.VUOTA) {
                        return true;
                    }
                }
            }
        } catch (IndexOutOfBoundsException iobx) {
            // se viene eseguita questa e' perche' sto facendo un controllo ai margini della nave
            // quindi di default, posso mettere il pezzo
            return true;
        }
        return false;
    }

    /**
     * Metodo per controllare se il modulo, nel caso sia un motore, si possa mettere
     * nella posizione indicata.
     * I motori possono essere messi in una cella se e solo se la cella subito sotto e' libera
     * 
     * @param coordinate Coordinate
     * @param tessera Tessera
     * @return vero -> il motore puo' essere posizionato |
     *         falso -> il motore non puo' essere posizionato
     */
    private boolean verificaInserimentoMotore(Coordinate coordinate, Tessera tessera){
    	
        // controllo se il pezzo subito sotto e' libero
        try{
            if(this.nave.get(coordinate.getY() + 1).get(coordinate.getX()).getTipoTessera() == TipoTessera.VUOTA){
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
     * @throws FinePartita 
     * @throws ErroreGiocatore 
     */
    public void rimuoviTessera(Coordinate coordinate) throws ErroreTessera, FinePartita{
    	Tessera vuota=new TesseraVuota(coordinate.getX(), coordinate.getY(),Posizione.INTERNA);
    	
    	// Verifica delle coordinate
        if(!controllaCoordinate(coordinate)){
            throw new ErroreTessera("Posizione non valida");
        }

        // rimozione tessera
        if(vuota == this.nave.get(coordinate.getY()).get(coordinate.getX())){
            throw new ErroreTessera("Impossibile rimuovere la tessera nella posizoine specificata");
        }

        // rimozione della tessera
        
        this.nave.get(coordinate.getY()).set(coordinate.getX(), vuota);
        
        //controlla se esiste ancora la nave e in caso chiama getTroncamento
        if(this.controllaEsistenzaNave()) {
        	this.nave = this.getTroncamentoNave();
        }else {
        	throw new FinePartita("La nave è stata totalmete distrutta");
            
        }
    }
    
    /**
     * Metodo che crea una lista di tronconiNave e fa scegliere all'utente quale tenere
     * @return troncone di nave scelta
     */
    private Troncamento getTroncamentoNave() {
    	Set<Troncamento> troncamentiNave =new LinkedHashSet<Troncamento>();
    	
    	
    	
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
		
    	Troncamento[] opzioni=(Troncamento[]) troncamentiNave.toArray();
    	
		return (Troncamento) opzioni[this.scegliTroncamenti(opzioni)];
		
	}
    
	/**
     * Metodo di distruzione nave. Distrugge le tessere non collegate al centroRamificazione e le rimpiazza
     * con oggetti TesseraVuota in nave. Se centroRamificazione=centro genera anche ParteRestante
	 * @param isCentro boolean
	 * @param centroRamificazione Coordinate
     * @return nave
     */
    public Troncamento distruggiNave(Coordinate centroRamificazione, boolean isCentro){
    	Set<Coordinate> visitate = new HashSet<>();
    	Queue<Coordinate> daVisitare = new LinkedList<>();
    	

        daVisitare.add(centroRamificazione);
        visitate.add(centroRamificazione);

        while (!daVisitare.isEmpty()) {
       	    Coordinate corrente = daVisitare.poll(); //prende il primo elemento (testa) FIFO
			Tessera tesseraCorrente = nave.get(corrente.getX()).get(corrente.getY());
			
			for (TipoLato dir : TipoLato.values()) {
			    Coordinate adiacente = corrente.adiacente(dir);
			    Tessera tesseraAdiacente = nave.get(adiacente.getX()).get(adiacente.getY());
			
			    if (tesseraAdiacente != null &&tesseraAdiacente.getTipoTessera() != TipoTessera.VUOTA && !visitate.contains(adiacente)) {
			    	boolean condizione=false;
					switch (dir) {
					case UP: {
						if(this.controllaCollegamentoUP(tesseraCorrente, corrente) == 1) {
							condizione=true;
						}
						break;
					}
					case LEFT: {
						if(this.controllaCollegamentoSX(tesseraCorrente, corrente) == 1) {
							condizione=true;
						}
						break;		
							}
					case DOWN: {
						if(this.controllaCollegamentoDW(tesseraCorrente, corrente) == 1) {
							condizione=true;
						}
						break;
						
					}
					case RIGHT: {
						if(this.controllaCollegamentoDX(tesseraCorrente, corrente) == 1) {
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
        
        
        this.parteRestante=(Troncamento) this.nave.clone();
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
					tessera=new TesseraVuota(tessera.getCoordinate().getX(), tessera.getCoordinate().getY(),Posizione.INTERNA);
					
					
				}
			}
    	}
        
        //creazione parte restante
        if(isCentro) {
        	for(ArrayList<Tessera> colonne : this.parteRestante) {
    			for(Tessera tessera : colonne) {
    				for(Coordinate coordinateTessera : visitate) {
    					if(coordinateTessera==tessera.getCoordinate()) {
    						
    						tessera=new TesseraVuota(tessera.getCoordinate().getX(), tessera.getCoordinate().getY(),Posizione.INTERNA);
							
    					}
    				}
    				
    			}
        	}
        }
		return nave; 
    }
    
    /**
     * Metodo per la scelta e visualizzazione dei troncamenti
     * @param opzioni Object[]
     * @return intero della scelta
     */
    private int scegliTroncamenti(Troncamento[] opzioni) {
		ArrayList<String> temp = new ArrayList<>();
		int scelta;
		stampa.println("Scegli il Troncamento di nave con cui vuoi proseguire la trasvolata:");
		for(int i=0; i< opzioni.length; i++){
			temp.add(Troncamento.TroncamentiToString(this.inizioNaveV, this.inizioNaveO, this.fineNaveO, opzioni[i]));
		}
		stampa.println(stampa.visualizzaElenco(temp));

		scelta = stampa.consoleReadInt()-1;
		if(scelta<1 || scelta>opzioni.length) {
			return scegliTroncamenti(opzioni);
		}
		return scelta;
	}
    
    

	/**
     * Metodo per il controllo sulle coordinate immesse dell'utente sono valide
     * 
     * @param coordinate Coordinate
     * @return vero -> le coordinate sono accettabili |
     *         falso -> le coordinate non sono accettabili
     */
    private boolean controllaCoordinate(Coordinate coordinate){
        if(
            (coordinate.getX() >= 0 && coordinate.getX() < getColonne()) &&
            (coordinate.getY() >= 0 && coordinate.getY() <  getRighe())
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
    	this.centro=this.getCoordinateCentro();
        if(TipoTessera.VUOTA == this.nave.get(centro.getY()).get(centro.getX()).getTipoTessera()){
            return false;
        }
        return true;
    }
    
    /**
     * Metodo per il controllo che la nave esista, ovvero abbia almeno una tessera diversa da TesseraVuota
     * @return false se non c'è più la nave (tutte le tessere = TesseraVuota)
     */
    private boolean controllaEsistenzaNave() {
    	for(ArrayList<Tessera> colonne : this.nave) {
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
     *  TODO: da ripensare
     * 
     * @param tessera Tessera
     * @param coordinate Coordinate
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
    	stampa.println("{debug} controllo collegamento");
    	int sx, dx, up, dw;
    	
    	sx = controllaCollegamentoSX(tessera, coordinate);
    	if(sx == -1) {
    		return false;
    	}
    	dx = controllaCollegamentoDX(tessera, coordinate);
    	if(sx == -1) {
    		return false;
    	}
    	up = controllaCollegamentoUP(tessera, coordinate);
    	if(sx == -1) {
    		return false;
    	}
    	dw = controllaCollegamentoDW(tessera, coordinate);
    	if(sx == -1) {
    		return false;
    	}
    	if(sx+dx+up+dw > 0) {

            return true;
    	}else {
    		stampa.println("{debug} non si collega da nessuna parte");
            return false;
    	}
    }
    
    /**
     * Metodo per controllare se il pezzo lo si pu' collegare a SX
     * 
     * @param tessera Tessera
     * @param coordinate Coordinate
     * @return si, no
     */
    private int controllaCollegamentoSX(Tessera tessera, Coordinate coordinate){
    	int y = coordinate.getY();
    	int x = coordinate.getX();

    	if (y - 1 < 0 || y - 1 >= nave.size()) {
    	    return 0;
    	}
    	if (x < 0 || x >= nave.get(y - 1).size()) {
    	    return 0;
    	}
    	if (TipoTessera.VUOTA == nave.get(y).get(x-1).getTipoTessera()) {
    	    return 0;
    	}
        stampa.println("{inizio debug} la tessere a sinistra è occupata da qualcosa");
        stampa.println(this.nave.get(coordinate.getY()).get(coordinate.getX()-1).toLegenda());
        
        LatiTessera latiTesseraNave = this.nave.get(coordinate.getY()).get(coordinate.getX() - 1).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getRight() == tessera.getLatiTessera().getLeft() ){
            return 0;
            
        } else 
        	
        // controlla se uno dei due lati è nullo e l'altro no
        if((latiTesseraNave.getRight() == TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO)) {
        	return -1;
        } else if((latiTesseraNave.getRight() != TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getLeft() == TipoConnettoriTessera.NULLO)) {
        	return -1;
        }else
        
        // controllo sei i lati sono compatibili con il connettore triplo
        if((latiTesseraNave.getRight() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO)){
                return 1;
                
        }else if((latiTesseraNave.getRight() != TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getLeft() == TipoConnettoriTessera.TRIPLO)){

            return 1;
            
        } else {
        	
            return -1;
        }
    }

    /**
     * Metodo per controllare se il pezzo lo si pu' collegare a DX
     * 
     * @param tessera Tessera
     * @param coordinate Coordinate
     * @return si, no
     */
    private int controllaCollegamentoDX(Tessera tessera, Coordinate coordinate){
    	int y = coordinate.getY();
    	int x = coordinate.getX();
    	
    	if (y < 0 || y >= nave.size()) {
    	    return 0;
    	}
    	if (x + 1 >= nave.get(y).size()) {
    	    return 0;
    	}
    	if (TipoTessera.VUOTA == nave.get(y).get(x+1).getTipoTessera()) {
    	    return 0;
    	}
        stampa.println("{inizio debug} la tessere a destra è occupata da qualcosa");
        stampa.println(this.nave.get(coordinate.getY()).get(coordinate.getX()+1).toLegenda());

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getY()).get(coordinate.getX() + 1).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getLeft() == tessera.getLatiTessera().getRight() && latiTesseraNave.getLeft()!=TipoConnettoriTessera.NULLO ){
            return 1;
        } else 
        	
        // controlla se uno dei due lati è nullo e l'altro no
        if((latiTesseraNave.getLeft() == TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO)) {
        	return -1;
        } else if((latiTesseraNave.getLeft() != TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getRight() == TipoConnettoriTessera.NULLO)) {
        	return -1;
        }else

        // controllo sei i lati sono compatibili con il connettore triplo
        if((latiTesseraNave.getLeft() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO)){
                return 1;
                
        }else if((latiTesseraNave.getLeft() != TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getRight() == TipoConnettoriTessera.TRIPLO)){
            return 1;
            
        }else {
            return -1;
        }
    }

    /**
     * Metodo per controllare se il pezzo lo si pu' collegare SOPRA
     * 
     * @param tessera Tessera
     * @param coordinate Coordinate
     * @return si, no
     */
    private int controllaCollegamentoUP(Tessera tessera, Coordinate coordinate){
    	int y = coordinate.getY();
    	int x = coordinate.getX();

    	if (y - 1 < 0 || y - 1 >= nave.size()) {
    	    return 0;
    	}
    	if (x < 0 || x >= nave.get(y - 1).size()) {
    	    return 0;
    	}
    	if (TipoTessera.VUOTA == nave.get(y - 1).get(x).getTipoTessera()) {
    	    return 0;
    	}
        stampa.println("{inizio debug} la tessere a su è occupata da qualcosa");
        stampa.println(this.nave.get(coordinate.getY() - 1).get(coordinate.getX()).toLegenda());

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getY() - 1).get(coordinate.getX()).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getDown() == tessera.getLatiTessera().getUp()&& latiTesseraNave.getDown()!=TipoConnettoriTessera.NULLO ){
            return 1;
        } else 
        	
        // controlla se uno dei due lati è nullo e l'altro no
        if((latiTesseraNave.getDown() == TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO)) {
        	return -1;
        } else if((latiTesseraNave.getDown() != TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getUp() == TipoConnettoriTessera.NULLO)) {
        	return -1;
        }else

        // controllo sei il ati sono compatibili con il connettore triplo
        if((latiTesseraNave.getDown() == TipoConnettoriTessera.TRIPLO) && 
                (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO)){
                    return 1;
            }
        else if((latiTesseraNave.getDown() != TipoConnettoriTessera.NULLO) && 
            (tessera.getLatiTessera().getUp() == TipoConnettoriTessera.TRIPLO)){
                return 1;
            }
        else{
            return -1;
        }
    }

    /**
     * Metodo per controllare se il pezzo lo si pu' collegare a SOTTO
     * 
     * @param tessera Tessera
     * @param coordinate Coordinate
     * @return si, no
     */
    private int controllaCollegamentoDW(Tessera tessera, Coordinate coordinate){
    	int y = coordinate.getY();
    	int x = coordinate.getX();

    	if (y + 1 >= nave.size()) {
    	    return 0;
    	}
    	if (x < 0 || x >= nave.get(y + 1).size()) {
    	    return 0;
    	}
    	if (TipoTessera.VUOTA == nave.get(y + 1).get(x).getTipoTessera()) {
    	    return 0;
    	}
        stampa.println("{inizio debug} la tessere a giu è occupata da qualcosa");
        stampa.println(this.nave.get(coordinate.getY() + 1).get(coordinate.getX()).toLegenda());

        LatiTessera latiTesseraNave = this.nave.get(coordinate.getY() + 1).get(coordinate.getX()).getLatiTessera();

        // controllo se i lati sono uguali
        if(latiTesseraNave.getUp() == tessera.getLatiTessera().getDown() && latiTesseraNave.getUp()!=TipoConnettoriTessera.NULLO ){
            return 1;
        } else 
        	
        // controlla se uno dei due lati è nullo e l'altro no
        if((latiTesseraNave.getUp() == TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO)) {
        	return -1;
        } else if((latiTesseraNave.getUp() != TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getDown() == TipoConnettoriTessera.NULLO)) {
        	return -1;
        }else
        
        // controllo sei il lati sono compatibili con il connettore triplo
        if((latiTesseraNave.getUp() == TipoConnettoriTessera.TRIPLO) && 
            (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO)){
                return 1;
            }
        else if((latiTesseraNave.getUp() != TipoConnettoriTessera.NULLO) && 
                (tessera.getLatiTessera().getDown() == TipoConnettoriTessera.TRIPLO)){
            return 1;
	        }
	    else{
        	
            return -1;
        }
    }

    /**
     * Metodo per il conteggio dei connettori scoperti
     * Questo metodo va ad aggiornare this.numeroConnettoriScoperti
     * Per accedere al numero di connettori scoperti, utilizzare l'apposito metodo
     */
    public void connettoriScoperti(){ 
        for(ArrayList<Tessera> colonna : this.nave){
            for(Tessera tessera : colonna){
                this.numeroConnettoriScoperti += conteggioConnettoriEsposti(tessera);
            }
        }
    }
    
    /**
     * Metodo che controlla se ci sono ancora presenti cosmonauti
     * sulla nave
     * 
     * @return if (cosmonauti > 0){ true
     * 				}else{ false }
     */
    public boolean controlloSonoPresentiCosmonauti() { // TODO da chiamare ogni volta che viene estratta una carta
        return (this.getCosmonauti() > 0);
    }
    
    /**
     * Metodo che controlla se la potenza motore è  superiore a 0
     * 
     * @return if (potenza motore > 0){ true
     * 				}else{ false }
     */
    public boolean controlloPotenzaMotore() { //TODO da chiamare prima che venga estratta la carta "spazio aperto"
    	return (this.getPotenzaMotori() > 0);
    }
    
    /**
     * Metodo che controlla se sono presenti stive nella nave
     * 
     * @return if (numero stive > 0){ true
     * 				}else{ false }
     */
    public boolean controlloPresenzaStive(){
		for(int x=0; x<this.nave.size(); x++) {
			
			for(int y=0; y<this.nave.get(x).size(); y++) {
				
				Tessera tessera = this.nave.get(x).get(y);
				TipoTessera tipo = tessera.getTipoTessera();
				
				if (tipo == TipoTessera.PORTA_MERCI) {
				    return true;
				}
			}
		}
		return false;
    }
    
    /**
     * Metodo che controlla se sono presenti stive non vuote nella nave
     * 
     * @return if (numero stive con merce > 0){ true
     * 				}else{ false }
     */
    public boolean controlloPresenzaStiveNonVuote() {
    	
		if(this.trova(0, 2).size() > 0) {

			return true;
		}
		return false;
    }
   
    /**
     * Metodo che controlla se sono presenti stive nella nave
     * 
     * @return if (numero stive > 0){ true
     * 				}else{ false }
     */
    public boolean controlloPresenzaModuli() {
    	
		for(int x=0; x<this.nave.size(); x++) {
			
			for(int y=0; y<this.nave.get(x).size(); y++) {
				
				Tessera tessera = this.nave.get(x).get(y);
				TipoTessera tipo = tessera.getTipoTessera();
				
				if (tipo == TipoTessera.MODULO_PASSEGGERI) {
				    return true;
				}else if(tipo == TipoTessera.CENTRO) {
					return true;
				}
			}
		}
		return false;
    }

    /**
     * Metodo per controllare i connettori di una tessera
     * 
     * @param dir TipoLato
     * @param tessera Tessera
     * @return true, false
     */
    private boolean controlloConnettore(TipoLato dir, Tessera tessera) {
    	
    	//this.nave.get(adiacente.getX()).get(adiacente.getY())
    	
    	Coordinate adiacente = tessera.getCoordinate().adiacente(dir);
    	
    	switch (dir) {
		case UP: {
			if(tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO && 
					this.nave.get(adiacente.getX()).get(adiacente.getY()).getTipoTessera() == TipoTessera.VUOTA) {
				return true;
			}
			break;
		}
		case LEFT: {
			if(tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO && 
					this.nave.get(adiacente.getX()).get(adiacente.getY()).getTipoTessera() == TipoTessera.VUOTA) {
				return true;
			}
			break;		
				}
		case DOWN: {
			if(tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO && 
					this.nave.get(adiacente.getX()).get(adiacente.getY()).getTipoTessera() == TipoTessera.VUOTA) {
				return true;
			}
			break;
		}
		case RIGHT: {
			if(tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO && 
					this.nave.get(adiacente.getX()).get(adiacente.getY()).getTipoTessera() == TipoTessera.VUOTA) {
				return true;
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + dir);
		}
    	
    	return false;
    }
    
    /**
     * Metodo per il conteggio dei connettori scoperti
     * Verifica se la tessera e' collegata a qualche cosa
     * Vengono conteggiati tutti i lati che non hanno connessione
     * 
     * @param tessera Tessera
     * @return conteggio lati scoperti per tessera
     */
    private int conteggioConnettoriEsposti(Tessera tessera) {
    	int conteggio = 0;
    	
    	if(controlloConnettore(TipoLato.UP, tessera)) {
    		conteggio += 1;
    	}
    	
        if(controlloConnettore(TipoLato.LEFT, tessera)) {
    		conteggio += 1;
    	}
    	
        if(controlloConnettore(TipoLato.DOWN, tessera)) {
    		conteggio += 1;
    	}

    	if(controlloConnettore(TipoLato.RIGHT, tessera)) {
    		conteggio += 1;
    	}
    	
        return conteggio;
    }

    /**
     * Metodo per calcolare l'energia
     * dalle batterie sulla nave.
     * 
     * (da eseguire solo all'inizio della 
     * partita, indifferentemente dal livello
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
    		int indice = stampa.consoleReadInt()-1;
        	
        	if(((Batteria)Tessere.get(indice)).decrese() && 
        			indice>=0 && indice<visual.size()) {
        		
        	}else {
        		condizione=false;
        	}
    	}while(condizione);
    }

    /**
     * Metodo per inserire la merce nella stiva della nave
     * passando l'oggetto merce<
     * 
     * @param coordinate Coordinate
     * @param merceDaInserire Merce
     * @throws ErroreCoordinate coordinate non valide
     * @throws ErroreRisorse risorse non valide
     * @throws ErroreTessera tessera non del giusto tipo
     */
    public void inserisciMerce(Coordinate coordinate, Merce merceDaInserire)    
        throws ErroreCoordinate, ErroreRisorse, ErroreTessera{

        if(controllaCoordinate(coordinate)){
            Tessera tessera = this.nave.get(coordinate.getY()).get(coordinate.getX());
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
     * @throws ErroreCoordinate
     * @throws ErroreTessera
     * @throws ErroreRisorse
     */
    public void rimuoviMerce(Coordinate coordinate, Merce merce) 
        throws ErroreCoordinate, ErroreTessera, ErroreRisorse{
        
        if(controllaCoordinate(coordinate)){
            Tessera tessera = this.nave.get(coordinate.getY()).get(coordinate.getX());
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
     * toString della nave
     * @return 
     */
    @Override
    public String toString() {
    	return Troncamento.TroncamentiToString(this.inizioNaveV, this.inizioNaveO, this.fineNaveO, this.nave);
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
     * Metodo che restituisce solo il numero di alieni viola
     * 
     * @return numero di alieni viola attualmente presenti sulla nave
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
     * Metodo che restituisce solo il numero di alieni marroni
     * 
     * @return numero di alieni marroni attualmente presenti sulla nave
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
     * Metodo per aggiungere solo i cosmonauti alla nave
     * di default ne vengono messi 2 per ogni modulo 
     * In questo metodo NON si tiene conto dell'esistenza degli alieni
     * NON e' questo metodo a occuparsene. CHIARO!
     */
    public void setCosmonauti(){
        for(ArrayList<Tessera> riga : this.nave){
            for(Tessera tessera : riga){
                if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                    ((ModuloPasseggeri)tessera).setNumeroCosmonauti(2);;
                }
            }
        }
    }

    /**
     * Metodo per aggiungere gli alieni marroni alla nave
     * @param numero
     */
    public void setAlieniMarroni(int numero){
        int count  = 0;
        for(ArrayList<Tessera> riga : this.nave){
            for(Tessera tessera : riga){
                if(count < numero){
                    if(tessera.getTipoTessera() == TipoTessera.MODULO_ATTRACCO_ALIENI){
                        if(((ModuloAttraccoAlieni)tessera).isAbitabile()){
                            // se e' abitabile
                            ((ModuloPasseggeri) tessera).setNumeroAlieniMarroni(1);
                            
                            Coordinate coordinateTesseraPasseggeriAdiacente = verificaValiditaModulo(tessera)
                                    .getCoordinateModuloPasseggeri();
                            
                                    ((ModuloPasseggeri) (this.nave
                                    .get(coordinateTesseraPasseggeriAdiacente.getX())
                                    .get(coordinateTesseraPasseggeriAdiacente.getY()))).setNumeroCosmonauti(-2);

                            count += 1;
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo per aggiungere gli alieni viola alla nave
     * @param numero
     */
    public void setAlieniViola(int numero){
        int count  = 0;
        for(ArrayList<Tessera> riga : this.nave){
            for(Tessera tessera : riga){
                if(count < numero){
                    if(tessera.getTipoTessera() == TipoTessera.MODULO_ATTRACCO_ALIENI){
                        if(((ModuloAttraccoAlieni)tessera).isAbitabile()){
                            // se e' abitabile
                            ((ModuloPasseggeri) tessera).setNumeroAlieniViola(1);
                            
                            Coordinate coordinateTesseraPasseggeriAdiacente = verificaValiditaModulo(tessera)
                                    .getCoordinateModuloPasseggeri();
                            
                                    ((ModuloPasseggeri) (this.nave
                                    .get(coordinateTesseraPasseggeriAdiacente.getX())
                                    .get(coordinateTesseraPasseggeriAdiacente.getY()))).setNumeroCosmonauti(-2);

                            count += 1;
                        }
                    }
                }
            }
        }
    }

    /**
     * Metodo che restituisce il numero dei moduli equipaggio differenziati per tipo
     * @return gestioneEqipaggio 
     */
    public GestioneEquipaggio getModuliEquipaggio(){
        GestioneEquipaggio ge = new GestioneEquipaggio();

        int modEquipaggio = 0, modViola = 0, modMarroni = 0;
        for(ArrayList<Tessera> riga : this.nave){
            for(Tessera tessera : riga){
                if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                    switch(((ModuloPasseggeri)tessera).getTipoModuloPasseggeri()){
                        case MODULO_ALIENO_MARRONE ->{
                            if(verificaValiditaModulo(tessera).getAbitabile()){
                                modMarroni += 1;
                                ((ModuloAttraccoAlieni)tessera).isAbitabile();
                            } 
                        }
                        case MODULO_ALIENO_VIOLA ->{
                            if(verificaValiditaModulo(tessera).getAbitabile()){ 
                                modViola += 1;
                                ((ModuloAttraccoAlieni)tessera).isAbitabile();
                            }
                        }
                        case MODULO_EQUIPAGGIO ->{
                            modEquipaggio += 1;
                        }
                    }
                }
            }
        }

        ge.setNumeroModuliCosmonauti(modEquipaggio);
        ge.setNumeroModuliAlieniMarroni(modMarroni);
        ge.setNumeroModuliAlieniViola(modViola);
        
        return ge;
    }
	
    /**
     * Metodo per verificare se il modulo vicino e' un modulo equipaggio. 
     * 
     * Questo metodo serve nel validare il modulo alieno
     * @param t Tessera
     * @return abi Abitabile -> classe fantasma a uso interno per gestire l'abitabilita' delle tessere
     */
    private Abitabile verificaValiditaModulo(Tessera t){
        Abitabile abi = new Abitabile();
        Coordinate c = t.getCoordinate();
        
        Tessera tesseraAdiacente;

        // sopra 
        if(controllaCoordinate(new Coordinate(c.getX() + 1, c.getY()))){
            tesseraAdiacente = this.nave.get(c.getX() + 1).get(c.getY()); 
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                abi.setAbitabile(true);
                abi.setCoordinateModuloPasseggeri(new Coordinate(c.getX() + 1, c.getY()));
                abi.setCoordinateTessera(t.getCoordinate());
                return abi;
            }
        }

        // sotto
        if(controllaCoordinate(new Coordinate(c.getX() - 1, c.getY()))){
            tesseraAdiacente = this.nave.get(c.getX() - 1).get(c.getY()); 
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                abi.setAbitabile(true);
                abi.setCoordinateModuloPasseggeri(new Coordinate(c.getX() + 1, c.getY()));
                abi.setCoordinateTessera(t.getCoordinate());
                return abi;
            }
        }

        // destra
        if(controllaCoordinate(new Coordinate(c.getX(), c.getY() + 1))){
            tesseraAdiacente = this.nave.get(c.getX()).get(c.getY() + 1); 
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                abi.setAbitabile(true);
                abi.setCoordinateModuloPasseggeri(new Coordinate(c.getX() + 1, c.getY()));
                abi.setCoordinateTessera(t.getCoordinate());
                return abi;
            }
        }

        // sinistra
        if(controllaCoordinate(new Coordinate(c.getX(), c.getY() - 1))){
            tesseraAdiacente = this.nave.get(c.getX()).get(c.getY() - 1); 
            if(tesseraAdiacente.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                abi.setAbitabile(true);
                abi.setCoordinateModuloPasseggeri(new Coordinate(c.getX() + 1, c.getY()));
                abi.setCoordinateTessera(t.getCoordinate());
                return abi;
            }
        }

        return abi;
    }

    /**
     * Ghost class a uso interno per gestire l' abitabilita' delle tessere
     */
    protected class Abitabile{
        private boolean abitabile; 
        private Coordinate coordinateTessera;
        private Coordinate coordinateModuloPasseggeri; 

        public Abitabile(){
            this.abitabile = false;
            this.coordinateTessera = null;
            this.coordinateModuloPasseggeri = null; 
        }

        public void setAbitabile(boolean status){ this.abitabile = status; }
        public void setCoordinateTessera(Coordinate coordinateTessera){ this.coordinateTessera = coordinateTessera; }
        public void setCoordinateModuloPasseggeri(Coordinate coordinateModuloPasseggeri){ this.coordinateModuloPasseggeri = coordinateModuloPasseggeri;}

        public boolean getAbitabile(){ return this.abitabile; }
        public Coordinate getCoordinateTessera(){ return this.coordinateTessera; }
        public Coordinate getCoordinateModuloPasseggeri(){ return this.coordinateModuloPasseggeri; }
    }

    /**
     * Metodo che ritorna la potenza dei motori
     * Nel conteggio e' gia' presente il bust portato dagli alieni
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
     * Nel conteggio e' gia' presente il bust portato dagli alieni
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
   	
		    	stampa.println(caso+") In posizione ("+x+";"+y+") :"+tessera.toLegenda());
		    	
		    	crd.add(new Coordinate(x, y));
		    }
		}
		return caso;
    }
}
