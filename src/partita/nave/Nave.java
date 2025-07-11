package partita.nave;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import eccezioniPersonalizzate.ErroreCoordinate;
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
	private Troncamento parteRestante;
	private Troncamento naveOriginaria;
	private int inizioNaveO;
	private int fineNaveO;
	private int inizioNaveV;
	private int fineNaveV;
	private int numeroPezziNaveDaRipagare;

	/**
	 * Metodi astratti, implementati nelle sotto classi
	 */
	protected abstract int[][] getMATRIX();

	public abstract Coordinate getCoordinateCentro();

	public abstract int getRighe();

	public abstract int getColonne();

	public abstract int getInizioNaveX();

	public abstract int getInizioNaveY();

	public abstract int getConfineNaveX();

	public abstract int getConfineNaveY();

	/**
	 * Costruttore della Nave (fa tante cose belle):D
	 * 
	 * @param coloreNave Colori
	 */
	public Nave(Colori coloreNave) {
		stampa = ComunicazioneConUtente.getIstanza();
		this.componentiPrenotati = new ArrayList<>();
		this.coloreNave = coloreNave;
		this.energiaResidua = 0;
		this.numeroConnettoriScoperti = 0;
		this.numeroPezziNaveDaRipagare = 0;
		this.inizializzaNave();
		this.nave = new Troncamento(inizioNaveV, inizioNaveO, fineNaveO);
		this.parteRestante = new Troncamento(inizioNaveV, inizioNaveO, fineNaveO);
	}

	/**
	 * Metodo per inizializzare la nave da chiamare dopo il costruttore
	 */
	protected void inizializzaNave() {
		this.centro = getCoordinateCentro();
		this.fineNaveO = getConfineNaveX();
		this.fineNaveV = getConfineNaveY();
		this.inizioNaveO = getInizioNaveX();
		this.inizioNaveV = getInizioNaveY();
	}

	// ---------------------------- TESSERE PRENOTATE ----------------------------

	/**
	 * Metodo per prenotare le tessere da mettere. Si possono prenotare al massimo 2
	 * tessere. Viene generato un errore se si vogliono prenotare piu' di 2 tessere
	 * 
	 * @param t Tessera
	 * @throws ErroreTessera limite massimo di tessere raggiunto
	 */
	public void prenotaTessera(Tessera t) throws ErroreTessera {
		if (this.componentiPrenotati.size() >= 2) {
			throw new ErroreTessera("Limite massimo di tessere prenotato raggiunto!!");
		} else {
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
	public Tessera togliTesseraPrenotata(int index) throws ErroreTessera {
		Tessera tesseraRimossa = null;
		try {
			if (this.componentiPrenotati.contains(this.componentiPrenotati.get(index))) {
				tesseraRimossa = this.componentiPrenotati.get(index);
				this.componentiPrenotati.remove(index);
			}
		} catch (IndexOutOfBoundsException ioobe) {
			throw new ErroreTessera("Tessera specificata non presente!");
		}

		return tesseraRimossa;
	}

	/**
	 * Metodo per sapere se la collezione di componenti prenotati e' vuota o e'
	 * piena
	 * 
	 * @return true -> vuota false -> piena
	 */
	public boolean isComponentiPrenotatiEmpty() {
		return this.componentiPrenotati.isEmpty();
	}

	// ---------------------------- INSERIMENTO TESSERE NELLA NAVE
	// ----------------------------

	/**
	 * Metodo per inserire una tessera nella nave durante la fase di creazione della
	 * nave
	 * 
	 * @param coordinata Coordinate
	 * @param tessera    Tessera
	 * @throws ErroreTessera    errore di posizione della tessera
	 * @throws ErroreCoordinate coordinate immesse non valide
	 */
	public void inserisciTessera(Coordinate coordinata, Tessera tessera) throws ErroreTessera, ErroreCoordinate {

		if (controllaCoordinate(coordinata)) {
			// Verifica se la tessera viene su una tessera gia esistente
			if (this.nave.get(coordinata.getY()).get(coordinata.getX()).getTipoTessera() != TipoTessera.VUOTA) {
				throw new ErroreTessera("Posizione non valida!");
			}

			// verifica se il pezzo lo si vuole mettere in una posizione non valida
			// RICORDA: coordinate lavorano al contrario nelle matrici
			if (0 == this.getMATRIX()[coordinata.getY()][coordinata.getX()]) {
				throw new ErroreTessera("Non puoi posizionare il pezzo in questa posizione");
			}

			/*
			 * Controlli speciali sulle tessere del tipo: - Cannone: non puo' avere pezzi
			 * subito davanti - Motore: non puo' avere pezzi subito dietro
			 */
			if (tessera.getTipoTessera() == TipoTessera.CANNONE) {
				if (false == verificaInserimentoCannone(coordinata, tessera)) {
					throw new ErroreTessera("Impossibile aggiungere il cannone in questa posizione");
				}
			}

			if (tessera.getTipoTessera() == TipoTessera.MOTORE) {
				if (false == verificaInserimentoMotore(coordinata, tessera)) {
					throw new ErroreTessera("Impossibile aggiungere il motore in questa posizione");
				}
			}

			// verifica se il pezzo e' collegato a qualche cosa
			if (!controllaCollegamento(tessera, coordinata)) {
				throw new ErroreTessera("Questo pezzo non puo' essere piazzato cosi' come e'");
			} else {
				stampa.println("tessera inserita correttamente");
			}

			// inserimento del pezzo nella nave
			tessera.setCoordinate(coordinata);
			this.nave.get(coordinata.getY()).set(coordinata.getX(), tessera);
		} else {
			throw new ErroreCoordinate("Coordinate immesse non valide");
		}
	}

	/**
	 * Metodo per controllare se il modulo, nel caso sia un cannone, si possa
	 * mettere nella posizione indicata. I cannoni possono essere messi in una cella
	 * se e solo se la cella nella direzione in cui punta il cannone e' libera
	 * 
	 * @param coordinate Coordinate della tessera
	 * @param tessera    Tessera
	 * @return vero -> il cannone puo' essere posizionato | falso -> il cannone non
	 *         puo' essere posizionato
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
			// se viene eseguita questa e' perche' sto facendo un controllo ai margini della
			// nave
			// quindi di default, posso mettere il pezzo
			return true;
		}
		return false;
	}

	/**
	 * Metodo per controllare se il modulo, nel caso sia un motore, si possa mettere
	 * nella posizione indicata. I motori possono essere messi in una cella se e
	 * solo se la cella subito sotto e' libera
	 * 
	 * @param coordinate Coordinate della tessera
	 * @param tessera    Tessera
	 * @return vero -> il motore puo' essere posizionato | falso -> il motore non
	 *         puo' essere posizionato
	 */
	private boolean verificaInserimentoMotore(Coordinate coordinate, Tessera tessera) {

		// controllo se il pezzo subito sotto e' libero
		try {
			if (this.nave.get(coordinate.getY() + 1).get(coordinate.getX()).getTipoTessera() == TipoTessera.VUOTA) {
				return true;
			}
		} catch (IndexOutOfBoundsException iobx) {
			// se viene eseguita questa parte e' perche' sto facendo il controllo in fondo
			// alla nave
			// quindi il motore puo' essere sempre piazzato
			return true;
		}
		return false;
	}

	/**
	 * !NELLE ALTRE CLASSI BISOGNA CHIAMARE QUESTO METODO! Metodo per rimuovere una
	 * tessera dalla nave durante la fase di volo Se la tessera eliminata è l'ultima
	 * della nave, genera eccezione FinePartita. Attua anche il conteggio delle
	 * tessere distrutte tramite il metodo setNumeroPezziNaveDaRipagare()
	 * 
	 * @param coordinate della tessera da rimuovere
	 * @throws ErroreTessera se la poszione non è esistente
	 * @throws FinePartita   se lanave viene distrutta totalmente
	 */
	public void rimuoviTessera(Coordinate coordinate) throws ErroreTessera, FinePartita {
		Tessera vuota = new TesseraVuota(coordinate.getX(), coordinate.getY(), Posizione.INTERNA);

		// Verifica delle coordinate
		if (!controllaCoordinate(coordinate)) {
			throw new ErroreTessera("Posizione non valida");
		}

		// errore rimozione tessera
		if (vuota.getTipoTessera() == this.nave.get(coordinate.getY()).get(coordinate.getX()).getTipoTessera()) {
			throw new ErroreTessera("Impossibile rimuovere la tessera nella posizione specificata");
		}

		// rimozione della tessera
		this.nave.get(coordinate.getY()).set(coordinate.getX(), vuota);
		vuota.setDistrutta(true);

		// copia dellA NAVE post rimozione della tessera
		this.naveOriginaria = new Troncamento(inizioNaveV, inizioNaveO, fineNaveO);
		this.naveOriginaria = (Troncamento) this.nave.clone(this.inizioNaveV, this.inizioNaveO, this.fineNaveO);

		// controlla se esiste ancora la nave e in caso chiama getTroncamento
		if (this.controllaEsistenzaNave()) {
			this.nave = this.getTroncamentoNave();
			setNumeroPezziNaveDaRipagare();
		} else {
			setNumeroPezziNaveDaRipagare();
			throw new FinePartita("La nave è stata totalmete distrutta");

		}
	}

	/**
	 * Metodo che crea una lista di troncamenti Nave tramite distruggiNave() e fa
	 * scegliere all'utente quale tenere
	 *
	 * @return troncamento di nave scelta
	 */
	private Troncamento getTroncamentoNave() {
		Set<Troncamento> troncamentiNave = new LinkedHashSet<Troncamento>();

		// scorre naveOrginaria e utilizza la prima tessera non vuota come
		// centroRamificazione in distruggiNave
		for (ArrayList<Tessera> colonne : this.naveOriginaria) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() != TipoTessera.VUOTA) {
					Troncamento tronc = this.distruggiNave(tessera.getCoordinate());
					if (tronc != null) {
						troncamentiNave.add(tronc);
					}
					break;
				}

			}
		}

		// scorre parteRestante e utilizza ogni tessera come centroRamificazione in
		// distruggiNave e poi mette i tronconi nel set
		for (ArrayList<Tessera> colonne : this.parteRestante) {
			for (Tessera tesseraR : colonne) {
				if (tesseraR.getTipoTessera() != TipoTessera.VUOTA) {
					Troncamento tronc = this.distruggiNave(tesseraR.getCoordinate());
					if (tronc != null) {
						troncamentiNave.add(tronc);
					}

				}
			}
		}

		// trasforma troncamentiNave in un arrayList
		ArrayList<Troncamento> opzioni = new ArrayList<>(troncamentiNave);

		// se c'è solo un troncamento ritorna quello in automatico
		if (opzioni.size() == 1) {
			return opzioni.getFirst();
		}

		return (Troncamento) opzioni.get(scegliTroncamenti(opzioni));

	}

	/**
	 * Metodo di distruzione nave. Distrugge le tessere non collegate al
	 * centroRamificazione e le rimpiazza con oggetti TesseraVuota in nave. Se il
	 * parametro isCentro==true genera anche una parte restante sovrascrivendola
	 * nell'attributo parteRestante di nave
	 * 
	 * @param centroRamificazione Coordinate del centroRamificazione
	 * @return nave (tipo: Troncamento) post distruzione
	 */
	private Troncamento distruggiNave(Coordinate centroRamificazione) {
		this.nave = (Troncamento) this.naveOriginaria.clone(this.inizioNaveV, this.inizioNaveO, this.fineNaveO);
		if (this.controllaEsistenzaNave()) {
			Set<Coordinate> visitate = new HashSet<>();
			Queue<Coordinate> daVisitare = new LinkedList<>();

			daVisitare.add(centroRamificazione);
			visitate.add(centroRamificazione);

			while (!daVisitare.isEmpty()) {
				Coordinate corrente = daVisitare.poll(); // prende il primo elemento (testa) FIFO
				Tessera tesseraCorrente = naveOriginaria.get(corrente.getY()).get(corrente.getX());

				for (TipoLato dir : TipoLato.values()) {
					Coordinate adiacente = corrente.adiacente(dir);

					if (this.controllaCoordinate(adiacente)) {
						Tessera tesseraAdiacente = this.naveOriginaria.get(adiacente.getY()).get(adiacente.getX());

						if (tesseraAdiacente != null && tesseraAdiacente.getTipoTessera() != TipoTessera.VUOTA
								&& !visitate.contains(adiacente)) {
							boolean condizione = false;
							switch (dir) {
							case UP: {
								if (this.controllaCollegamentoUP(tesseraCorrente, corrente) == 1) {
									condizione = true;
								}
								break;
							}
							case LEFT: {
								if (this.controllaCollegamentoSX(tesseraCorrente, corrente) == 1) {
									condizione = true;
								}
								break;
							}
							case DOWN: {
								if (this.controllaCollegamentoDW(tesseraCorrente, corrente) == 1) {
									condizione = true;
								}
								break;

							}
							case RIGHT: {
								if (this.controllaCollegamentoDX(tesseraCorrente, corrente) == 1) {
									condizione = true;
								}
								break;

							}
							default:
								throw new IllegalArgumentException("direzione " + dir.toString() + " non valida");
							}
							if (condizione) {
								visitate.add(adiacente);
								daVisitare.add(adiacente);
							}
						}
					}

				}
			}

			this.parteRestante = (Troncamento) this.naveOriginaria.clone(this.inizioNaveV, this.inizioNaveO,
					this.fineNaveO);

			// sovrascrive con tessereVuote in nave le tessere che non sono state visitate
			for (ArrayList<Tessera> colonne : naveOriginaria) {
				for (Tessera tessera : colonne) {
					boolean check = true;
					for (Coordinate coordinateTessera : visitate) {
						if (coordinateTessera.equals(tessera.getCoordinate())
								|| tessera.getTipoTessera() == TipoTessera.VUOTA) {
							check = false;
						}
					}
					if (check) {
						if (this.getMATRIX()[tessera.getCoordinate().getY()][tessera.getCoordinate().getX()] == 1
								|| this.getMATRIX()[tessera.getCoordinate().getY()][tessera.getCoordinate()
										.getX()] == 2) {

							tessera = new TesseraVuota(tessera.getCoordinate().getX(), tessera.getCoordinate().getY(),
									Posizione.INTERNA);
							tessera.setDistrutta(true);
							this.nave.get(tessera.getCoordinate().getY()).set(tessera.getCoordinate().getX(), tessera);

						} else {
							tessera = new TesseraVuota(tessera.getCoordinate().getX(), tessera.getCoordinate().getY(),
									Posizione.ESTRENA);
							tessera.setDistrutta(true);
							this.nave.get(tessera.getCoordinate().getY()).set(tessera.getCoordinate().getX(), tessera);

						}

					}
				}
			}

			// creazione parte restante

			for (ArrayList<Tessera> colonne : this.naveOriginaria) {
				for (Tessera tessera : colonne) {
					for (Coordinate coordinateTessera : visitate) {
						if (coordinateTessera.equals(tessera.getCoordinate())
								&& tessera.getTipoTessera() != TipoTessera.VUOTA) {

							if (this.getMATRIX()[tessera.getCoordinate().getY()][tessera.getCoordinate().getX()] == 1
									|| this.getMATRIX()[tessera.getCoordinate().getY()][tessera.getCoordinate()
											.getX()] == 2) {

								tessera = new TesseraVuota(tessera.getCoordinate().getX(),
										tessera.getCoordinate().getY(), Posizione.INTERNA);
								tessera.setDistrutta(true);
								this.parteRestante.get(tessera.getCoordinate().getY())
										.set(tessera.getCoordinate().getX(), tessera);

							} else {
								tessera = new TesseraVuota(tessera.getCoordinate().getX(),
										tessera.getCoordinate().getY(), Posizione.ESTRENA);
								tessera.setDistrutta(true);
								this.parteRestante.get(tessera.getCoordinate().getY())
										.set(tessera.getCoordinate().getX(), tessera);

							}
						}
					}

				}
			}
			this.naveOriginaria = (Troncamento) this.parteRestante.clone(this.inizioNaveV, this.inizioNaveO,
					this.fineNaveO);

			return nave;
		}
		return null;

	}

	/**
	 * Metodo per la scelta e visualizzazione dei troncamenti
	 * 
	 * @param opzioni ArrayList<Troncamento>, lista di troncamenti da scegliere
	 * @return intero corrispondente alla scelta troncamento
	 */
	private int scegliTroncamenti(ArrayList<Troncamento> opzioni) {
		ArrayList<String> temp = new ArrayList<>();
		int scelta = 0;
		boolean pass = false;
		do {
			stampa.println("Scegli il Troncamento di nave con cui vuoi proseguire la trasvolata:");
			for (int i = 0; i < opzioni.size(); i++) {
				temp.add(opzioni.get(i).toString());
			}
			stampa.println(stampa.visualizzaElenco(temp));
			try {
				scelta = Integer.parseInt(this.stampa.consoleRead());
				scelta = scelta - 1;

				if (scelta < 0 || scelta >= opzioni.size()) {
					this.stampa.erroreImmissioneValore();
				} else {
					pass = true;
				}
			} catch (NumberFormatException nfe) {
				this.stampa.erroreImmissioneValore();
				pass = false;
			}
		} while (!pass);

		return scelta;
	}

	/**
	 * Metodo per il controllo sulle coordinate immesse dell'utente sono valide
	 * 
	 * @param coordinate Coordinate
	 * @return vero -> le coordinate sono accettabili | falso -> le coordinate non
	 *         sono accettabili
	 */
	public boolean controllaCoordinate(Coordinate coordinate) {
		if ((coordinate.getX() >= 0 && coordinate.getX() < getColonne())
				&& (coordinate.getY() >= 0 && coordinate.getY() < getRighe())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo per il controllo della presenza del centro
	 * 
	 * @return true -> la nave ha ancora il centro | false -> la nave non ha il
	 *         centro
	 */
	public boolean controllaPresenzaCentro() {
		this.centro = this.getCoordinateCentro();
		if (TipoTessera.VUOTA == this.nave.get(this.centro.getY()).get(this.centro.getX()).getTipoTessera()) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo per il controllo che la nave esista, ovvero abbia almeno una tessera
	 * diversa da TesseraVuota
	 * 
	 * @return false se non c'è più la nave (tutte le tessere = TesseraVuota)
	 */
	public boolean controllaEsistenzaNave() {
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() != TipoTessera.VUOTA) {
					return true;
				}

			}
		}
		return false;

	}

	/**
	 * Metodo per il controllo sui collegamenti
	 *
	 * @param tessera    Tessera
	 * @param coordinate Coordinate
	 * @return true -> il pezzo lo si puo' collegare senza problemi | false -> il
	 *         pezzo non lo si puo' collegare
	 */
	private boolean controllaCollegamento(Tessera tessera, Coordinate coordinate) {
		// true --> il pezzo e' collegato correttamente
		// false --> il pezzo non e' collegato correttamente
		/*
		 * Casi controlli UP SX ( k - 1 ) DX (i - 1) |Tessera| (i + 1) ( k + 1 ) DW
		 */
		int sx, dx, up, dw;

		sx = controllaCollegamentoSX(tessera, coordinate);
		if (sx == -1) {
			return false;
		}
		dx = controllaCollegamentoDX(tessera, coordinate);
		if (sx == -1) {
			return false;
		}
		up = controllaCollegamentoUP(tessera, coordinate);
		if (sx == -1) {
			return false;
		}
		dw = controllaCollegamentoDW(tessera, coordinate);
		if (sx == -1) {
			return false;
		}
		if (sx + dx + up + dw > 0) {

			return true;
		} else {
			stampa.println("non si collega da nessuna parte");
			return false;
		}
	}

	/**
	 * Metodo per controllare se il pezzo lo si pu' collegare a SX
	 * 
	 * @param tessera    Tessera
	 * @param coordinate Coordinate
	 * @return si, no
	 */
	private int controllaCollegamentoSX(Tessera tessera, Coordinate coordinate) {
		int y = coordinate.getY();
		int x = coordinate.getX();

		if (y - 1 < 0 || y - 1 >= nave.size()) {
			return 0;
		}
		if (x < 0 || x >= nave.get(y - 1).size()) {
			return 0;
		}
		if (TipoTessera.VUOTA == nave.get(y).get(x - 1).getTipoTessera()) {
			return 0;
		}
		LatiTessera latiTesseraNave = this.nave.get(coordinate.getY()).get(coordinate.getX() - 1).getLatiTessera();

		// controllo se i lati sono uguali
		if (latiTesseraNave.getRight() == tessera.getLatiTessera().getLeft()) {

			if (tessera.getLatiTessera().getLeft() == TipoConnettoriTessera.NULLO) {

				return 0;
			} else {

				return 1;
			}

		} else

		// controlla se uno dei due lati è nullo e l'altro no
		if ((latiTesseraNave.getRight() == TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO)) {
			return -1;
		} else if ((latiTesseraNave.getRight() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getLeft() == TipoConnettoriTessera.NULLO)) {
			return -1;
		} else

		// controllo sei i lati sono compatibili con il connettore triplo
		if ((latiTesseraNave.getRight() == TipoConnettoriTessera.TRIPLO)
				&& (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO)) {
			return 1;

		} else if ((latiTesseraNave.getRight() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getLeft() == TipoConnettoriTessera.TRIPLO)) {

			return 1;

		} else {

			return -1;
		}
	}

	/**
	 * Metodo per controllare se il pezzo lo si pu' collegare a DX
	 * 
	 * @param tessera    Tessera
	 * @param coordinate Coordinate
	 * @return si, no
	 */
	private int controllaCollegamentoDX(Tessera tessera, Coordinate coordinate) {
		int y = coordinate.getY();
		int x = coordinate.getX();

		if (y < 0 || y >= nave.size()) {
			return 0;
		}
		if (x + 1 >= nave.get(y).size()) {
			return 0;
		}
		if (TipoTessera.VUOTA == nave.get(y).get(x + 1).getTipoTessera()) {
			return 0;
		}
		LatiTessera latiTesseraNave = this.nave.get(coordinate.getY()).get(coordinate.getX() + 1).getLatiTessera();

		// controllo se i lati sono uguali
		if (latiTesseraNave.getLeft() == tessera.getLatiTessera().getRight()) {

			if (tessera.getLatiTessera().getRight() == TipoConnettoriTessera.NULLO) {

				return 0;
			} else {

				return 1;
			}

		} else

		// controlla se uno dei due lati è nullo e l'altro no
		if ((latiTesseraNave.getLeft() == TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO)) {
			return -1;
		} else if ((latiTesseraNave.getLeft() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getRight() == TipoConnettoriTessera.NULLO)) {
			return -1;
		} else

		// controllo sei i lati sono compatibili con il connettore triplo
		if ((latiTesseraNave.getLeft() == TipoConnettoriTessera.TRIPLO)
				&& (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO)) {
			return 1;

		} else if ((latiTesseraNave.getLeft() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getRight() == TipoConnettoriTessera.TRIPLO)) {
			return 1;

		} else {
			return -1;
		}
	}

	/**
	 * Metodo per controllare se il pezzo lo si pu' collegare SOPRA
	 * 
	 * @param tessera    Tessera
	 * @param coordinate Coordinate
	 * @return si, no
	 */
	private int controllaCollegamentoUP(Tessera tessera, Coordinate coordinate) {
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
		LatiTessera latiTesseraNave = this.nave.get(coordinate.getY() - 1).get(coordinate.getX()).getLatiTessera();

		// controllo se i lati sono uguali
		if (latiTesseraNave.getDown() == tessera.getLatiTessera().getUp()) {

			if (tessera.getLatiTessera().getUp() == TipoConnettoriTessera.NULLO) {

				return 0;
			} else {

				return 1;
			}

		} else

		// controlla se uno dei due lati è nullo e l'altro no
		if ((latiTesseraNave.getDown() == TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO)) {
			return -1;
		} else if ((latiTesseraNave.getDown() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getUp() == TipoConnettoriTessera.NULLO)) {
			return -1;
		} else

		// controllo sei il ati sono compatibili con il connettore triplo
		if ((latiTesseraNave.getDown() == TipoConnettoriTessera.TRIPLO)
				&& (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO)) {
			return 1;
		} else if ((latiTesseraNave.getDown() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getUp() == TipoConnettoriTessera.TRIPLO)) {
			return 1;
		} else {
			return -1;
		}
	}

	/**
	 * Metodo per controllare se il pezzo lo si pu' collegare a SOTTO
	 * 
	 * @param tessera    Tessera
	 * @param coordinate Coordinate
	 * @return si, no
	 */
	private int controllaCollegamentoDW(Tessera tessera, Coordinate coordinate) {
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
		LatiTessera latiTesseraNave = this.nave.get(coordinate.getY() + 1).get(coordinate.getX()).getLatiTessera();

		// controllo se i lati sono uguali
		if (latiTesseraNave.getUp() == tessera.getLatiTessera().getDown()) {

			if (tessera.getLatiTessera().getDown() == TipoConnettoriTessera.NULLO) {

				return 0;
			} else {

				return 1;
			}

		} else

		// controlla se uno dei due lati è nullo e l'altro no
		if ((latiTesseraNave.getUp() == TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO)) {
			return -1;
		} else if ((latiTesseraNave.getUp() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getDown() == TipoConnettoriTessera.NULLO)) {
			return -1;
		} else

		// controllo sei il lati sono compatibili con il connettore triplo
		if ((latiTesseraNave.getUp() == TipoConnettoriTessera.TRIPLO)
				&& (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO)) {
			return 1;
		} else if ((latiTesseraNave.getUp() != TipoConnettoriTessera.NULLO)
				&& (tessera.getLatiTessera().getDown() == TipoConnettoriTessera.TRIPLO)) {
			return 1;
		} else {

			return -1;
		}
	}

	/**
	 * Metodo per il conteggio dei connettori scoperti Questo metodo va ad
	 * aggiornare this.numeroConnettoriScoperti Per accedere al numero di connettori
	 * scoperti, utilizzare l'apposito metodo
	 */
	public void connettoriScoperti() {

		this.numeroConnettoriScoperti = 0;

		for (ArrayList<Tessera> colonna : this.nave) {
			for (Tessera tessera : colonna) {
				this.numeroConnettoriScoperti += conteggioConnettoriEsposti(tessera);
			}
		}
	}

	/**
	 * Metodo che controlla se ci sono ancora presenti cosmonauti sulla nave
	 * 
	 * @return if (cosmonauti > 0){ true }else{ false }
	 */
	public boolean controlloSonoPresentiCosmonauti() { // TODO da chiamare ogni volta che viene estratta una carta
		return (this.getCosmonauti() > 0);
	}

	/**
	 * Metodo che controlla se la potenza motore e' superiore a 0
	 * 
	 * @return if (potenza motore > 0){ true }else{ false }
	 */
	public boolean controlloPotenzaMotore() { // TODO da chiamare prima che venga estratta la carta "spazio aperto"
		return (this.getPotenzaMotori() > 0);
	}

	/**
	 * Metodo che controlla se sono presenti stive nella nave
	 * 
	 * @return if (numero stive > 0){ true }else{ false }
	 */
	public boolean controlloPresenzaStive() {
		for (int x = 0; x < this.nave.size(); x++) {

			for (int y = 0; y < this.nave.get(x).size(); y++) {

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
	 * @return if (numero stive con merce > 0){ true }else{ false }
	 */
	public boolean controlloPresenzaStiveNonVuote() {

		if (this.trova(0, 2).size() > 0) {

			return true;
		}
		return false;
	}

	/**
	 * Metodo che controlla se sono presenti stive nella nave
	 * 
	 * @return if (numero stive > 0){ true }else{ false }
	 */
	public boolean controlloPresenzaModuli() {

		for (int x = 0; x < this.nave.size(); x++) {

			for (int y = 0; y < this.nave.get(x).size(); y++) {

				Tessera tessera = this.nave.get(x).get(y);
				TipoTessera tipo = tessera.getTipoTessera();

				if (tipo == TipoTessera.MODULO_PASSEGGERI) {
					return true;
				} else if (tipo == TipoTessera.CENTRO) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Metodo per controllare i connettori di una tessera
	 * 
	 * @param dir     TipoLato
	 * @param tessera Tessera
	 * @return true, false
	 */
	private boolean controlloConnettore(TipoLato dir, Tessera tessera) {
		Coordinate adiacente = tessera.getCoordinate().adiacente(dir);
		
		if((adiacente.getY() > 0 && adiacente.getY() < this.fineNaveV) && (adiacente.getX() > 0 && adiacente.getX() < this.fineNaveO)) {
			
			switch (dir) {
			case UP: {
				if (tessera.getLatiTessera().getUp() != TipoConnettoriTessera.NULLO
						&& this.nave.get(adiacente.getY()).get(adiacente.getX()).getTipoTessera() == TipoTessera.VUOTA) {
					return true;
				}
				break;
			}
			case LEFT: {
				if (tessera.getLatiTessera().getLeft() != TipoConnettoriTessera.NULLO
						&& this.nave.get(adiacente.getY()).get(adiacente.getX()).getTipoTessera() == TipoTessera.VUOTA) {
					return true;
				}
				break;
			}
			case DOWN: {
				if (tessera.getLatiTessera().getDown() != TipoConnettoriTessera.NULLO
						&& this.nave.get(adiacente.getY()).get(adiacente.getX()).getTipoTessera() == TipoTessera.VUOTA) {
					return true;
				}
				break;
			}
			case RIGHT: {
				if (tessera.getLatiTessera().getRight() != TipoConnettoriTessera.NULLO
						&& this.nave.get(adiacente.getY()).get(adiacente.getX()).getTipoTessera() == TipoTessera.VUOTA) {
					return true;
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + dir);
			}
		}
		

		return false;
	}

	/**
	 * Metodo per il conteggio dei connettori scoperti Verifica se la tessera e'
	 * collegata a qualche cosa Vengono conteggiati tutti i lati che non hanno
	 * connessione
	 * 
	 * @param tessera Tessera
	 * @return conteggio lati scoperti per tessera
	 */
	private int conteggioConnettoriEsposti(Tessera tessera) {
		int conteggio = 0;

		if (controlloConnettore(TipoLato.UP, tessera)) {
			conteggio += 1;
		}

		if (controlloConnettore(TipoLato.LEFT, tessera)) {
			conteggio += 1;
		}

		if (controlloConnettore(TipoLato.DOWN, tessera)) {
			conteggio += 1;
		}

		if (controlloConnettore(TipoLato.RIGHT, tessera)) {
			conteggio += 1;
		}

		return conteggio;
	}

	/**
	 * Metodo per calcolare l'energia dalle batterie sulla nave.
	 * 
	 * (da eseguire solo all'inizio della partita, indifferentemente dal livello
	 * 
	 * @return totale energia nave
	 */
	public int calcolaEnergia() {
		int energia = 0;

		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() == TipoTessera.BATTERIA) {
					energia += ((Batteria) tessera).getEnergiaAttuale();
				}
			}
		}

		return energia;
	}

	/**
	 * Metodo per 'utilizzare' (1 gemma) l'energia residua sulla nave. Viene
	 * generata l'eccezione ErroreRisorse quando si richiede + energia di quella
	 * disponibile
	 *
	 * @throws ErroreRisorse
	 */
	public void utilizzaEnergia() throws ErroreRisorse {
		this.energiaResidua = this.calcolaEnergia();
		if (this.energiaResidua <= 0) {
			throw new ErroreRisorse("Energia insufficiente");
		} else {
			stampa.println("vuoi utilizzare una gemma di energia?");
			if (stampa.conferma()) {
				selezionaTesseraEnergia();

			} else {
				throw new ErroreRisorse("Energia non consumata");
			}
		}
	}

	/**
	 * Metodo per selezionare e rimuovere energia dalla tessera batteria selezionata
	 */
	private void selezionaTesseraEnergia() {

		ArrayList<String> visual = new ArrayList<String>();
		ArrayList<String> visualTessere = new ArrayList<String>();
		ArrayList<Tessera> Tessere = new ArrayList<Tessera>();
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {

				if (tessera.getTipoTessera() == TipoTessera.BATTERIA) {
					Tessere.add(tessera);
					visualTessere.add("\n" + tessera.toString());
					visual.add("posizione(" + (this.nave.indexOf(colonne) + this.inizioNaveO) + ";"
							+ (colonne.indexOf(tessera) + this.inizioNaveV) + ") " + tessera.toLegenda());
				}

			}
		}

		boolean condizione = true;
		do {
			stampa.println("Inserisci il numero corrispondente alla tessera a cui vuoi rimuovere energia:");
			stampa.print(stampa.visualizzaElenco(visualTessere));
			stampa.print(stampa.visualizzaElenco(visual));

			try {
				int indice = Integer.parseInt(this.stampa.consoleRead());
				indice = indice - 1;

				if (indice >= 0 && indice < Tessere.size()) {
					if (((Batteria) Tessere.get(indice)).decrese()) {
						condizione = false;
					} else {
						this.stampa.printError("energia insufficiente in questo modulo");
					}
				}
			} catch (NumberFormatException nfe) {
				this.stampa.erroreImmissioneValore();
			}
		} while (condizione);
	}

	/**
	 * Metodo per inserire la merce nella stiva della nave passando l'oggetto merce<
	 * 
	 * @param coordinate      Coordinate
	 * @param merceDaInserire Merce
	 * @throws ErroreCoordinate coordinate non valide
	 * @throws ErroreRisorse    risorse non valide
	 * @throws ErroreTessera    tessera non del giusto tipo
	 */
	public void inserisciMerce(Coordinate coordinate, Merce merceDaInserire)
			throws ErroreCoordinate, ErroreRisorse, ErroreTessera {

		if (controllaCoordinate(coordinate)) {
			Tessera tessera = this.nave.get(coordinate.getX()).get(coordinate.getY());
			if (tessera.getTipoTessera() == TipoTessera.PORTA_MERCI) {
				((Stiva) tessera).inserisciMerci(merceDaInserire);
			} else {
				throw new ErroreTessera("La tessera selezionata non e' del tipo merce");
			}
		} else {
			throw new ErroreCoordinate("Coordinate immesse non valide");
		}
	}

	/**
	 * Metodo per rimuovere una merce specifica date coordinate e l'oggetto merce
	 * 
	 * @param coordinate
	 * @throws ErroreCoordinate
	 * @throws ErroreTessera
	 * @throws ErroreRisorse
	 */
	public void rimuoviMerce(Coordinate coordinate, Merce merce) throws ErroreCoordinate, ErroreTessera, ErroreRisorse {

		if (controllaCoordinate(coordinate)) {
			Tessera tessera = this.nave.get(coordinate.getY()).get(coordinate.getX());
			if (tessera.getTipoTessera() == TipoTessera.PORTA_MERCI) {
				Stiva stiva = (Stiva) tessera;
				try {
					stiva.rimuoviMerce(merce);
				} catch (ErroreRisorse er) {
					throw er;
				}
			} else {
				throw new ErroreTessera("La tessera selezionata non e' del tipo Stiva");
			}
		} else {
			throw new ErroreCoordinate("Coordinate immesse non valide");
		}
	}

	/**
	 * Metodo per rimuovere l'equipaggio data una cella di coordinate conosciute e
	 * numero di equipaggio da rimuovere
	 * 
	 * @param coordianteModulo
	 * @param qta
	 * @throws ErroreCoordinate, ErroreTessera
	 */
	public void rimuoviEquipaggio(Coordinate coordianteModulo, int qta) throws ErroreCoordinate, ErroreTessera {

		if (controllaCoordinate(coordianteModulo)) {
			Tessera tessera = this.nave.get(coordianteModulo.getX()).get(coordianteModulo.getY());

			if (tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
				ModuloPasseggeri moduloPasseggeri = (ModuloPasseggeri) tessera;
				moduloPasseggeri.rimuoviEquipaggio();
			} else if (tessera.getTipoTessera() == TipoTessera.CENTRO) {
				Centro centro = (Centro) tessera;
				centro.rimuoviPasseggeri(-1);
			} else {
				throw new ErroreTessera("La cella selezionata non e' del tipo modulo");
			}
		} else {
			throw new ErroreCoordinate("Coordinate immesse non valide");
		}
	}

	/**
	 * Metodo per la visualizzazione degli elementi presenti in componenti prenotati
	 * 
	 * @return messaggio da stampare sulla console
	 */
	public String tesserePrenotateToString() {
		String temp = "";
		ArrayList<String> componentiPrenotatiLegenda = new ArrayList<>();
		temp += "Tessere Prenotate:\n";
		for (int k = 0; k < this.componentiPrenotati.size(); k++) {
			temp = temp + "  " + (k + 1) + "   ";
			if (!this.componentiPrenotati.isEmpty()) {
				componentiPrenotatiLegenda.add(this.componentiPrenotati.get(k).toLegenda());
			}

		}
		temp += "\n";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < this.componentiPrenotati.size(); j += 1) {
				temp = temp + this.componentiPrenotati.get(j).getriga(i) + " ";

			}
			temp += "\n";

		}
		temp = temp + this.stampa.visualizzaElenco(componentiPrenotatiLegenda) + "\n";
		this.stampa.visualizzaElenco(componentiPrenotatiLegenda);

		return temp;
	}

	/**
	 * toString della nave
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return this.nave.toString();
	}

	// -------------------- SETTER - GETTER --------------------
	/**
	 * Metodo per acquisire la plancia della nave
	 *
	 * @return ArrayList<ArrayList<Tessera>>> nave
	 */
	public ArrayList<ArrayList<Tessera>> getPlanciaDellaNave() {
		return this.nave;
	}

	/**
	 * Metodo per sapere quanti pezzi della nave il giocatore deve ripagare
	 *
	 * @return int numero di pezzi da ripagare
	 */
	public int getNumeroPezziNaveDaRipagare() {
		int val = this.numeroPezziNaveDaRipagare + this.componentiPrenotati.size();
		return val;
	}

	/**
	 * Metodo per gettare il colore della nave
	 *
	 * @return Colori colore della nave
	 */
	public Colori getColoreNave() {
		return this.coloreNave;
	}

	/**
	 * Metodo che chiama calcolaEnergia e restituisce quanta energia c'è sulla nave
	 * 
	 * @return energiaResidua
	 */
	public int getEnergiaResidua() {
		this.energiaResidua = this.calcolaEnergia();
		return this.energiaResidua;
	}

	/**
	 * Metodo per sapere il numero di connettori scoperti
	 *
	 * @return int numero di connettori scoperti
	 */
	public int getNumeroConnettoriScoperti() {
		return this.numeroConnettoriScoperti;
	}

	/**
	 * Metodo il quale restituisce la tessera fornite le coordinate
	 *
	 * @param coordinate Coordinate coordinate della tessera
	 * @return tessera alla coordinata specificata
	 */
	public Tessera getTessera(Coordinate coordinate) {
		return this.nave.get(coordinate.getY()).get(coordinate.getX());
	}

	/**
	 * Metodo che restituisce il numero totale dell'equipaggio presente sulla nave
	 * (cosmonauti + alieni rossi + alieni marroni)
	 * 
	 * @return equipaggio totale (int)
	 */
	public int getEquipaggio() {
		return getCosmonauti() + getAlieniMarrone() + getAlieniViola();
	}

	/**
	 * Metodo che restituisce solo il numero di alieni viola
	 * 
	 * @return numero di alieni viola attualmente presenti sulla nave
	 */
	public int getAlieniViola() {
		int alieniViola = 0;
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
					alieniViola += ((ModuloPasseggeri) tessera).getNumeroAlieniViola();
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
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
					alieniMarroni += ((ModuloPasseggeri) tessera).getNumeroAlieniMarroni();
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
		int cosmonauti = 0;
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() == TipoTessera.MODULO_PASSEGGERI) {
					cosmonauti += ((ModuloPasseggeri) tessera).getNumeroCosmonauti();
				}
				if (tessera.getTipoTessera() == TipoTessera.CENTRO) {
					cosmonauti += ((Centro) tessera).getPasseggeriCorrenti();
				}
			}
		}
		return cosmonauti;
	}

	/**
	 * Metodo per contare i pezzi distrutti confrontando il nuovo troncamento di
	 * nave guardando quali componenti sono stati distrutti.
	 */
	private void setNumeroPezziNaveDaRipagare() {
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.isDistrutta()) {
					this.numeroPezziNaveDaRipagare = this.numeroPezziNaveDaRipagare + 1;
				}
			}
		}
	}

	/**
	 * Metodo che ritorna la potenza dei motori Nel conteggio e' gia' presente il
	 * boost portato dagli alieni
	 * 
	 * @return potenza motori
	 */
	public int getPotenzaMotori() {
		int potenzaMotori = 0;
		this.stampa.println("Calcolo potenza Motori:\n");
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() == TipoTessera.MOTORE) {
					if (((Motore) tessera).getTipoMotore() == TipoMotore.SINGOLO) {
						potenzaMotori += 1;
					} else {
						// se il motore e' doppio
						potenzaMotori += 2;
						try {
							this.stampa.println("Utilizzo di un motore doppio:");
							this.utilizzaEnergia();
						} catch (ErroreRisorse e) {
							potenzaMotori -= 2;
							stampa.printError("Energia non consumata per la tessera speciale in posizione: ("
									+ (tessera.getCoordinate().getX() + this.inizioNaveO) + ";"
									+ (tessera.getCoordinate().getY() + this.inizioNaveV) + ")\n");
						}

					}
				}
			}
		}

		// aggiunta del boost degli alieni marroni
		if (!(potenzaMotori == 0)) {
			/*
			 * Da regolamento: Gli alieni marroni sono ottimi meccanici. Se hai un alieno
			 * marrone, ricevi +2 alla potenza motrice (se la tua potenza motrice senza
			 * l’alieno è 0, non ricevi questo bonus. Non scenderà a spingere).
			 */
			potenzaMotori += getAlieniMarrone() * 2;
		}
		return potenzaMotori;
	}

	/**
	 * Metodo che ritorna la potenza dei motori Nel conteggio e' gia' presente il
	 * boost portato dagli alieni
	 * 
	 * @return potenza cannoni
	 */
	public float getPotenzaCannoni() {
		float potenzaCannoni = .0f;

		/*
		 * Metodo per assegnare la potenza di fuoco (recap di quello che fa
		 * <Cannone>.calcolaValore())
		 * 
		 * Cannoni singoli: - Puntano verso l'alto: +1 - Puntano di lato: +1/2 (0.5)
		 * 
		 * Cannoni doppi: - Puntano verso l'alto: +2 - puntano di lato: +1
		 * 
		 */
		this.stampa.println("\nFASE CALCOLO POTENZA DELLA TUA NAVE: \n");
		for (ArrayList<Tessera> colonne : this.nave) {
			for (Tessera tessera : colonne) {
				if (tessera.getTipoTessera() == TipoTessera.CANNONE) {

					potenzaCannoni += ((Cannone) tessera).calcolaValore();

					if (((Cannone) tessera).getTipoCannone() == TipoCannone.DOPPIO) {
						try {
							this.stampa.println("Utilizzo di un cannone doppio:");
							this.utilizzaEnergia();
						} catch (ErroreRisorse e) {
							potenzaCannoni -= ((Cannone) tessera).calcolaValore();
							stampa.printError("Energia non consumata per la tessera speciale in posizione: ("
									+ (tessera.getCoordinate().getX() + this.inizioNaveO) + ";"
									+ (tessera.getCoordinate().getY() + this.inizioNaveV) + ")\n");
						}
					}
				}
			}
		}

		// aggiunta del bust degli alieni rossi
		if (!(potenzaCannoni == 0.f)) {
			/*
			 * Da regolamento: Gli alieni viola appartengono a una specie bellicosa. Se hai
			 * un alieno viola, ricevi +2 alla potenza di fuoco (se la tua potenza di fuoco
			 * senza l’alieno è 0, non ricevi questo bonus. Non affronterà una battaglia
			 * spaziale a tentacoli nudi).
			 */
			potenzaCannoni += getAlieniViola() * 2;
		}
		return potenzaCannoni;
	}

	/**
	 * Metodo che in base a quale tipologia di tessere stai cercando: 1: tessera
	 * tipologia stiva (che sia vuota) 2: tessera tipologia stiva (che sia non
	 * vuota) 3: tessera tipologia stiva (che di tipologia speciale) 4: tessera
	 * tipologia modulo (comprendendo anche il centro) (e con un equipaggio al suo
	 * intento>0)
	 * 
	 * @param n      int
	 * @param scelta int
	 * @return Arraylist delle coordinate di tutte le tessere cercate
	 */
	public ArrayList<Coordinate> trova(int n, int scelta) {

		int caso = n;

		ArrayList<Coordinate> crd = new ArrayList<Coordinate>();

		for (int x = 0; x < this.nave.size(); x++) {

			for (int y = 0; y < this.nave.get(x).size(); y++) {

				switch (scelta) {
				case 1:
					caso = StiveVuote(caso, crd, x, y);
					break;

				case 2:
					caso = StiveNonVuote(caso, crd, x, y);
					break;

				case 3:
					caso = StiveSpeciali(caso, crd, x, y);
					break;

				case 4:
					caso = Modulo(caso, crd, x, y);
					break;

				default:
					break;
				}
			}
		}

		return crd;
	}

	/**
	 * Metodo che trova tutte le tessere stive vuote
	 * 
	 * @param caso
	 * @param crd
	 * @param x
	 * @param y
	 * @return arraylist con le stive vuote
	 */
	private int StiveVuote(int caso, ArrayList<Coordinate> crd, int x, int y) {

		Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.PORTA_MERCI) {
			if (((Stiva) tessera).getNumeroMerciAttuale() == 0) {

				caso++;

				stampa.println("" + caso + ") In posizione (" + (x + this.inizioNaveO + 1) + ";"
						+ (y + this.inizioNaveV + 1) + ") :" + tessera.toLegenda());

				crd.add(new Coordinate(x, y));
			}
		}
		return caso;
	}

	/**
	 * Metodo che trova tutte le tessere stive non vuote
	 * 
	 * @param caso
	 * @param crd
	 * @param x
	 * @param y
	 * @return arraylist con le stive non vuote
	 */
	private int StiveNonVuote(int caso, ArrayList<Coordinate> crd, int x, int y) {

		Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.PORTA_MERCI) {
			if (((Stiva) tessera).getNumeroMerciAttuale() > 0) {

				caso++;

				stampa.println("" + caso + ") In posizione (" + (x + this.inizioNaveO + 1) + ";"
						+ (y + this.inizioNaveV + 1) + ") :" + tessera.toLegenda());

				crd.add(new Coordinate(x, y));
			}
		}
		return caso;
	}

	/**
	 * Metodo che trova tutte le tessere stive speciali
	 * 
	 * @param caso
	 * @param crd
	 * @param x
	 * @param y
	 * @return arraylist con le stive speciali
	 */
	private int StiveSpeciali(int caso, ArrayList<Coordinate> crd, int x, int y) {

		Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.PORTA_MERCI) {
			if (((Stiva) tessera).getTipoMerciGenerale() == TipoStiva.SPECIALI) {

				caso++;

				stampa.println("" + caso + ") In posizione (" + (x + this.inizioNaveO + 1) + ";"
						+ (y + this.inizioNaveV + 1) + ") :" + tessera.toLegenda());

				crd.add(new Coordinate(x, y));
			}
		}
		return caso;
	}

	/**
	 * Metodo che trova tutte le tessere modulo non vuote centro compreso
	 * 
	 * @param caso
	 * @param crd
	 * @param x
	 * @param y
	 * @return arraylist con i moduli
	 */
	private int Modulo(int caso, ArrayList<Coordinate> crd, int x, int y) {

		Tessera tessera = this.nave.get(x).get(y);
		TipoTessera tipo = tessera.getTipoTessera();

		if (tipo == TipoTessera.MODULO_PASSEGGERI) {
			if (((ModuloPasseggeri) tessera).getEquipaggio() > 0) {

				caso++;

				stampa.println("" + caso + ") In posizione (" + (x + this.inizioNaveO + 1) + ";"
						+ (y + this.inizioNaveV + 1) + ") :" + tessera.toLegenda());

				crd.add(new Coordinate(x, y));
			}
		} else if (tipo == TipoTessera.CENTRO) {
			if (((Centro) tessera).getPasseggeriCorrenti() > 0) {

				caso++;

				stampa.println(caso + ") In posizione (" + (x + this.inizioNaveO + 1) + ";" + (y + this.inizioNaveV + 1)
						+ ") :" + tessera.toLegenda());

				crd.add(new Coordinate(x, y));
			}
		}
		return caso;
	}
}
