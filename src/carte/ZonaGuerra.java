package carte;

import carte.meteore.*;
import eccezioniPersonalizzate.ErroreGiocatore;
import eccezioniPersonalizzate.ErroreTessera;
import gioco.ComunicazioneConUtente;
import java.util.ArrayList;
import java.util.Random;
import partita.Pedina;
import partita.nave.Nave;
import tessera.*;

public class ZonaGuerra extends Carta {

    private static String[] penalita;
    private static String[] eventi;
    private String[][] valori;
    private ArrayList<Meteorite> colpi;
    private int equipaggiPersi, giorniPersi, merciPersi;
    private final ComunicazioneConUtente stampa;

    /**
     * Costruttore ZonaGuerra super -> gli passiamo il lvl della carta e il tipo
     * metodo: GeneraValori() per generare i attributi della carta
     *
     * @param lvl
     */
    public ZonaGuerra(int lvl) {

        super(lvl, TipoCarta.ZONA_GUERRA);
        stampa = ComunicazioneConUtente.getIstanza();
        colpi = new ArrayList<>();

        eventi = new String[]{"CANNONI", "EQUIPAGGI", "RAZZI"};
        penalita = new String[]{"PERDITA_EQUIPAGGIO", "PERDITA_GIORNI", "PERDITA_MERCE", "CANNONATE"};

        valori = new String[3][2]; // PRIMA COLONNA (x 1)= EVENTI / SECONDA COLONNA (x 0)= PENALITA'

        GeneraValori();
    }

    /**
     * metodo che genera random le sfide e le penalità
     */
    private void GeneraValori() {

        Random random = new Random();

        valori[2][1] = penalita[3];

        int x1 = random.nextInt(3) + 1; 
        ///// IMPOSTO LE PRIME CHELLANGE E PENALITA'
		valori[0][0] = eventi[x1 - 1];
        int x2 = random.nextInt(3) + 1;
        valori[0][1] = penalita[x2 - 1];

        do {
            ///// IMPOSTO LE SECONDE CHELLANGE E PENALITA' (CON CONTROLLO DI NON RIPETIZIONE)
			x1 = random.nextInt(3) + 1;
            valori[1][0] = eventi[x1 - 1];

        } while (valori[0][0] == eventi[x1 - 1]);

        do {
            x2 = random.nextInt(3) + 1;
            valori[1][1] = penalita[x2 - 1];

        } while (valori[0][1] == penalita[x2 - 1]);

        int controllo = -1;
        ///// IMPOSTO LA TERZA CHELLANGE(ULTIMO VALORE RIMANENTE), PENALITA' GIA IMPOSTATA DI DEFAULT 
		do {
            controllo++;

            if (controllo >= 3) {
                stampa.printError("ERROR: assegnazione ulrimo valore disponibile alla carta (errorTipe: do_while) (class: ZonaGuerra)");
            }
            valori[2][0] = eventi[controllo];

        } while (valori[0][0] == eventi[controllo] || valori[1][0] == eventi[controllo]);

        GeneraPerdite();
        GeneraColpi();
    }

    /**
     * metodo che in base a quale tipo di penalità è genera random il numero di
     * perdite anche in base al lvl
     */
    private void GeneraPerdite() {

        Random random = new Random();

        for (int i = 0; i < 3; i++) {

            switch (valori[i][1]) {
                case "PERDITA_EQUIPAGGIO" -> {
                    equipaggiPersi = random.nextInt(2) + this.lvl + 1;
                }
                case "PERDITA_GIORNI" -> {
                    giorniPersi = random.nextInt(2) + this.lvl + 1;
                }
                case "PERDITA_MERCE" -> {
                    merciPersi = random.nextInt(2) + this.lvl + 1;
                }
                default -> {

                }
            }
        }
    }

    /**
     * metodo che genera i colpi (avviene sempre siccomelultima sfida avviene
     * sempre la pioggia di colpi) anchesso in base al lvl della carta e anche
     * il tipo del colpo
     */
    private void GeneraColpi() {

        Random random = new Random();

        int ncolpi, grandezza;

        switch (this.lvl) {
            case 1 -> {
                ncolpi = random.nextInt(2) + 1; // MINIMO 1 MAX 2

                for (int i = 0; i < ncolpi; i++) {

                    grandezza = random.nextInt(3) + 1;

                    if (grandezza == 1) {
                        colpi.add(new ColpoGrande());
                    } else {
                        colpi.add(new ColpoPiccolo());
                    }
                }
            }
            case 2 -> {
                ncolpi = random.nextInt(2) + 3;  // MINIMO 3 MAX 4

                for (int i = 0; i < ncolpi; i++) {

                    grandezza = random.nextInt(3) + 1;

                    if (grandezza == 1) {
                        colpi.add(new ColpoGrande());
                    } else {
                        colpi.add(new ColpoPiccolo());
                    }
                }
            }
            case 3 -> {
                ncolpi = random.nextInt(2) + 5;  // MINIMO 5 MAX 6

                for (int i = 0; i < ncolpi; i++) {

                    grandezza = random.nextInt(3) + 1;

                    if (grandezza == 1) {
                        colpi.add(new ColpoGrande());
                    } else {
                        colpi.add(new ColpoPiccolo());
                    }
                }
            }
            default -> {
                stampa.printError("ERROR: numerazione colpi (errorTipe: switch) (class: ZonaGuerra)");
            }
        }

    }

    public static String[] getPenalita() {
        return penalita;
    }

    public static void setPenalita(String[] penalita) {
        ZonaGuerra.penalita = penalita;
    }

    public static String[] getEventi() {
        return eventi;
    }

    public static void setEventi(String[] eventi) {
        ZonaGuerra.eventi = eventi;
    }

    public String[][] getValori() {
        return valori;
    }

    public void setValori(String[][] valori) {
        this.valori = valori;
    }
    public String toString() {
        String temp = "";
        temp = temp + "\nLivello carta:" + this.lvl
                + "\nTipo carta:" + this.tipo
                + "\n - SFIDE -   - PENALITA' - \n" ;
        for (int i = 0; i < 3; i++) {
            temp = temp + this.valori[i][0] + " - "+ this.valori[i][1];
            
            if(i != 2) {
            	
                switch (valori[i][1]) {
                
	                case "PERDITA_EQUIPAGGIO" -> {
	                	temp = temp + " -"+ this.equipaggiPersi + " componenti dell'equipaggio";
	                }
	                case "PERDITA_GIORNI" -> {
	                	temp = temp + " -"+ this.giorniPersi + " giorni di viaggio";
	                }
	                case "PERDITA_MERCE" -> {
	                	temp = temp + " -"+ this.merciPersi + " merci della nave";
	                }
                }
            }else {
            	temp = temp + " " + this.colpi.size() +" colpi \n";
            }
        }
        temp = temp + "\n";

        return temp;
    }
    /**
     * metodo che trova il giocatore con il parametro della sfida più basso e in
     * seguito esegue la penitenza in base alla fase
     */
    @Override
    public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {

        if (elencoPedine.size() <= 1) {

            stampa.println("Il giocatore è da solo, la carta Zona di guerra non viene eseguita");

        } else {
            for (int i = 0; i < 3; i++) {

                int pedinaSubisce;

                switch (valori[i][0]) {
                    case "EQUIPAGGI" -> {

                        pedinaSubisce = selezionaMinorEquipaggio(elencoPedine);
                        
                        stampa.println("Il giocatore con il minor numero di EQUIPAGGIO è: "+elencoPedine.get(pedinaSubisce).getGiocatore().getNome());

                        penalitaCarta(elencoPedine.get(pedinaSubisce), i);

                    }
                    case "RAZZI" -> {

                        pedinaSubisce = selezionaMinorMotore(elencoPedine);
                        
                        stampa.println("Il giocatore con il minor potenza MOTORI è: "+elencoPedine.get(pedinaSubisce).getGiocatore().getNome());

                        penalitaCarta(elencoPedine.get(pedinaSubisce), i);
                    }
                    case "CANNONI" -> {

                        pedinaSubisce = selezionaMinorCannone(elencoPedine);
                        
                        stampa.println("Il giocatore con il minor potenza CANNONI è: "+elencoPedine.get(pedinaSubisce).getGiocatore().getNome());
                        
                        penalitaCarta(elencoPedine.get(pedinaSubisce), i);
                    }
                    default -> {

                    }
                }
            }
        }
        return elencoPedine;
    }

    /**
     * metodo che in base a quale penitenza si tratta esegue la penitenza a
     * quella pedina
     *
     * @param pedina
     * @param i
     * @return
     */
    private Pedina penalitaCarta(Pedina pedina, int i) {

        switch (valori[i][1]) {
            case "PERDITA_EQUIPAGGIO" -> {
                pedina.selezionaEquipaggioDaEliminare(equipaggiPersi);
            }
            case "PERDITA_GIORNI" -> {
                pedina.setPosizioneSulTabellone(-giorniPersi);
            }
            case "PERDITA_MERCE" -> {
                pedina.selezionaMerceDaEliminare(merciPersi);
            }
            case "CANNONATE" -> {
                int j = 0;
                do {
                	if(controlloColpoIsDentroDallaNave(this.colpi.get(j), pedina.getGiocatore().getNave())) {
                		this.colpi.get(j).setRisultatoDado(adattaDadiAllArray(this.colpi.get(j)));
	                    boolean sceltaFermareColpo = false;
	
	                    Tessera colpito = trovaTesseraColpita(this.colpi.get(j), pedina.getGiocatore().getNave());
	
	                    if (colpito != null) {
	
	                        if (this.colpi.get(j).getType() == TypeMeteora.COLPO_PICCOLO) {
	
	                            if (pedina.sceltaEpossibilitaUtilizzoScudi()) {
	
	                                stampa.println("METEORITE FERMATO DALLO SCUDO");
	                                sceltaFermareColpo = true;
	                            }
	                        }
	
	                        if (!sceltaFermareColpo) {
	
	                            try {
	                                try {
	                                    pedina.getGiocatore().getNave().rimuoviTessera(colpito.getCoordinate());
	                                } catch (ErroreGiocatore e) {
	
	                                    e.printStackTrace();
	                                }
	
	                            } catch (ErroreTessera e) {
	
	                                e.printStackTrace();
	                            }
	                        }
	
	                    } else {
	
	                        stampa.println("COLPO HA MANCATO LA NAVE");
	                    }
                	}else {
                		stampa.println("COLPO HA MANCATO LA NAVE");
                	}
                    //TODO controllo integrita nave
                    j++;
                } while (j < this.colpi.size());
            }
            default -> {

            }
        }

        return pedina;
    }
	private boolean controlloColpoIsDentroDallaNave(Meteorite meteorite, Nave n) {
		
		switch(meteorite.getDirezione()) {
			case SUD , NORD ->{
				
				if(meteorite.getDado() < n.getConfineNaveX() && meteorite.getDado() > n.getInizioNaveX()) {
					
					return true;
				}
			}	
			case OVEST, EST ->{
				
				if(meteorite.getDado() < n.getConfineNaveY() && meteorite.getDado() > n.getInizioNaveY()) {
					
					return true;
				}
			}
			default->{}
		}
		
		return false;
	}
	private int adattaDadiAllArray(Meteorite meteorite) {
		
		switch(meteorite.getDirezione()) {
		case SUD , NORD ->{

			return meteorite.getDado() - 3;
		}	
		case OVEST, EST ->{
			return meteorite.getDado() - 4;
		}
		default->{}
		}
		return 0;
	}
    /**
     * *
     * metodo che trova il giocatore con il minor numero di equipaggio
     *
     * @param elencoPedine
     * @return pedina
     */
    private int selezionaMinorEquipaggio(ArrayList<Pedina> elencoPedine) {

        int giocatoreMinorEquipaggio = 0;

        for (int i = 1; i < elencoPedine.size(); i++) {
            if (elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() < elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getEquipaggio()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO

                giocatoreMinorEquipaggio = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO

            } else if (elencoPedine.get(i).getGiocatore().getNave().getEquipaggio() == elencoPedine.get(giocatoreMinorEquipaggio).getGiocatore().getNave().getEquipaggio()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO

                if (elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(giocatoreMinorEquipaggio).getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE

                    giocatoreMinorEquipaggio = i;//IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
                }
            }
        }
        return giocatoreMinorEquipaggio;
    }

    /**
     * *
     * metodo che trova il giocatore con il minor potenza motori
     *
     * @param elencoPedine
     * @return pedina
     */
    private int selezionaMinorMotore(ArrayList<Pedina> elencoPedine) {

        int minorPotenzaMotore = 0;

        for (int i = 1; i < elencoPedine.size(); i++) {
            if (elencoPedine.get(i).getGiocatore().getNave().getPotenzaMotori() < elencoPedine.get(minorPotenzaMotore).getGiocatore().getNave().getPotenzaMotori()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO

                minorPotenzaMotore = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO

            } else if (elencoPedine.get(i).getGiocatore().getNave().getPotenzaMotori() == elencoPedine.get(minorPotenzaMotore).getGiocatore().getNave().getPotenzaMotori()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO

                if (elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(minorPotenzaMotore).getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE

                    minorPotenzaMotore = i;//IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
                }
            }
        }
        return minorPotenzaMotore;
    }

    /**
     * *
     * metodo che trova il giocatore con il minor potenza cannone
     *
     * @param elencoPedine
     * @return pedina
     */
    private int selezionaMinorCannone(ArrayList<Pedina> elencoPedine) {

        int minorPotenzaCannone = 0;

        for (int i = 1; i < elencoPedine.size(); i++) {
            if (elencoPedine.get(i).getGiocatore().getNave().getPotenzaCannoni() < elencoPedine.get(minorPotenzaCannone).getGiocatore().getNave().getPotenzaCannoni()) { // SCEGLO QUALE NAVE HA IL MINOR NUMERO DI EQUIPAGGIO

                minorPotenzaCannone = i; //IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO

            } else if (elencoPedine.get(i).getGiocatore().getNave().getPotenzaCannoni() == elencoPedine.get(minorPotenzaCannone).getGiocatore().getNave().getPotenzaCannoni()) { //SE HANNO LO STESSO NUMERO DI EQUIPAGGIO

                if (elencoPedine.get(i).getPosizioneSulTabellone() > elencoPedine.get(minorPotenzaCannone).getPosizioneSulTabellone()) { //SCELGO QUELLO CHE è PIU AVANTI DI POSIZIONE

                    minorPotenzaCannone = i;//IMPOSTO NUOVO GIOCATORE CON MINOR EQUIPAGGIO
                }
            }
        }
        return minorPotenzaCannone;
    }

    /**
     * metodo che trova quale tessera viene colpita dal meteorite
     *
     * @param colpo
     * @param nave
     * @return tessera colpita
     */
	private Tessera trovaTesseraColpita(Meteorite colpo, Nave nave) {
		
		switch(colpo.getDirezione()) {
			case NORD->{
				for(int i=0; i<nave.getRighe(); i++) {
					if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(i).get(colpo.getDado());
					}
				}
			}
			case SUD->{
				for(int i=nave.getRighe()-1; i>=0; i--) {
					if(nave.getPlanciaDellaNave().get(i).get(colpo.getDado()).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(i).get(colpo.getDado());
					}
				}
			}
			case EST->{
				for(int i=nave.getColonne()-1; i>=0; i--) {
					if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(colpo.getDado()).get(i);
					}
				}
			}
			case OVEST->{
				for(int i=0; i<nave.getColonne(); i++) {
					if(nave.getPlanciaDellaNave().get(colpo.getDado()).get(i).getTipoTessera() != TipoTessera.VUOTA) {
						
						return nave.getPlanciaDellaNave().get(colpo.getDado()).get(i);
					}
				}
			}
			default->{
				return null;
			}
		
		}
		
		return null;
	}
}
