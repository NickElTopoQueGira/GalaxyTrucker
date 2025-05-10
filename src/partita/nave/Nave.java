package partita.nave;

import java.util.ArrayList;


import eccezioniPersonalizzate.ErroreCoordinate;
import eccezioniPersonalizzate.ErroreTessera;
import eccezioniPersonalizzate.ErroreRisorse;
import partita.giocatore.Colori;
import partita.oggetti.merci.Merce;
import partita.oggetti.merci.TipoMerce;
import tessera.Centro;
import tessera.Coordinate;
import tessera.LatiTessera;
import tessera.Tessera;
import tessera.TipoConnettoriTessera;
import tessera.TipoTessera;
import tessera.batteria.Batteria;
import tessera.cannone.Cannone;
import tessera.merce.Stiva;
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
     * (fa tante cose belle)
     * @param coloreNave
     */
    public Nave(Colori coloreNave){
        this.componentiPrenotati = new ArrayList<Tessera>();
        this.nave = new ArrayList<>();
        this.NAVE_DEF = getMATRIX();
        this.centro = getCoordinateCentro();
        this.coloreNave = coloreNave;

        this.energiaResidua = 0;
        this.numeroConnettoriScoperti = 0;
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

    /**
     * Metodo per il conteggio dei connettori scoperti
     * Questo metodo va ad aggiornare this.numeroConnettoriScoperti
     * Per accedere al numero di connettori scoperti, utilizzare l'apposito metodo
     */
    public void connettoriScoperti(){
        for(ArrayList<Tessera> colonna : this.nave){
            for(Tessera tessera : colonna){
                this.numeroConnettoriScoperti += conteggioConettoriEsposti(tessera);        
            }
        }
    }

    /**
     * Funzione per il conteggio dei connettori scoperti
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
     * Funzione per calcolare l'energia
     * dalle batterie sulla nave.
     * 
     * (da eseguire solo all'inizio della 
     * partita, indifferentemente dal livello)
     * 
     * @param tessera
     * @return
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
     * Funzione per 'utilizzare' l'energia residua sulla nave. 
     * Viene generata l'eccezione ErroreRisorse quando si richiede + energia di 
     * quella disponibile
     * 
     * @param quantita
     * @throws ErroreRisorse
     */
    public void utilizzaEnergia(int quantita) throws ErroreRisorse{
        if(this.energiaResidua - quantita < 0){
            throw new ErroreRisorse("Energia insufficente");
        }
        else{
            this.energiaResidua =- quantita;
        }
    }

    /**
     * Funzione per per inserire la merce nella stiva della nave
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
     * Funzione per rimuovere una merce specifica date coordinate 
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
     * Funzione per rimuovere l'equipaggio data una cella di coordinate conosciute
     * e numero di equipaggio da rimuovere
     * 
     * @param coordianteModulo
     * @param qta
     * @throws ErroreRisorse
     */
    public void rimuoviEquipaggio(Coordinate coordianteModulo, int qta) 
        throws ErroreCoordinate, ErroreRisorse, ErroreTessera{

        if(controllaCoordinate(coordianteModulo)){
            Tessera tessera = this.nave.get(coordianteModulo.getX()).get(coordianteModulo.getY());
            if(tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI){
                ModuloPasseggeri moduloPasseggeri = (ModuloPasseggeri)tessera;
                try{
                    rimuoviEquipaggio(moduloPasseggeri, qta);
                }catch(ErroreRisorse er){
                    throw er;
                }
            }
            else{
                throw new ErroreTessera("La cella selezionata non e' del tipo modulo");
            }
        }
        else{
            throw new ErroreCoordinate("Coordinate immesse non valide");
        }
    }

    /**
     * Funzione per rimuovere l'equipaggio in base al tipo di modulo passeggeri
     * 
     * @param modulo
     * @param qta
     * @return
     * @throws ErroreRisorse
     */
    private ModuloPasseggeri rimuoviEquipaggio(ModuloPasseggeri modulo, int qta) throws ErroreRisorse{
        switch (modulo.getTipoModuloPasseggeri()) {
            case MODULO_EQUIPAGGIO ->{
                if(qta > 2){
                    throw new ErroreRisorse("Non puoi toglire + di 2 cosmonauti");
                }
                if (modulo.getNumeroCosmonauti() - qta >= 0) {
                    modulo.setNumeroCosmonauti(-qta);
                } else {
                    throw new ErroreRisorse("Equipaggio insufficiente per la cella selezionata");
                }    
            }
            case MODULO_ALIENO_MARRONE ->{
                if(qta > 1){
                    throw new ErroreRisorse("Non puoi toglire + di 1 alieno marrone");
                }
                if (modulo.getNumeroAlieniMarroni() - qta >= 0) {
                    modulo.setNumeroAlieniMarroni(-qta);
                } else {
                    throw new ErroreRisorse("Equipaggio insufficiente per la cella selezionata");
                }
            }
            case MODULO_ALIENO_VIOLA ->{
                if(qta > 1){
                    throw new ErroreRisorse("Non puoi toglire + di 1 alieno rosso");
                }
                if (modulo.getNumeroAlieniViola() - qta >= 0) {
                    modulo.setNumeroAlieniViola(-qta);
                } else {
                    throw new ErroreRisorse("Equipaggio insufficiente per la cella selezionata");
                }
            }
        }
        return modulo;
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
        		temp += "\n";
        	}
            temp = temp + "\n";
        }
		return temp;
    }
    
    //-------------------- SETTER - GETTER --------------------
    public ArrayList<ArrayList<Tessera>> getPlanciaDellaNave(){ return this.nave; }

    public Colori getColoreNave(){ return this.coloreNave; }

    public int getEnergiaResidua(){ return this.energiaResidua; }

    public int getNumeroConnettoriScoperti(){ return this.numeroConnettoriScoperti; }

    /**
     * Funzione che restituisce il numero totale dell'equipaggio presente sulla nave
     * (cosmonauti + alieni rossi + alieni marroni)
     * 
     * @return equipaggio totale (int)
     */
    public int getEquipaggio() {
		return getCosmonauti()+getAlieniMarrone()+getAlieniViola();
	}
    
    /**
     * Funzione che restituisce solo il numero di alini viola
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
     * Funzione che restituisce solo il numero di alini marroni
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
     * Funzione che restituisce solo il numero di astronauti presenti sulla nave
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
     * Funzione che ritorna la potenza dei motori
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
     * Funzione che ritorna la potenza dei motori
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
}
