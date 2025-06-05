package carte;

import gioco.ComunicazioneConUtente;
import java.util.ArrayList;
import java.util.Random;
import partita.Pedina;
import tessera.merce.Merce;
import tessera.merce.TipoMerce;

public class StazioneAbbandonata extends Carta {

    private int giocatorinecessari;
    private int penalitagiorni;
    private final ArrayList<Merce> merci;

	private ComunicazioneConUtente stampa= ComunicazioneConUtente.getIstanza();;

    /**
     * Costruttore StazioneAbbandonata super -> gli passiamo il lvl della carta
     * e il tipo inizializza la lista merci e genera gli attributi della carta
     * metodo: GeneraValori() per creare i valori iniziali
     *
     * @param lvl
     */
    public StazioneAbbandonata(int lvl) {

        super(lvl, TipoCarta.STAZIONE_ABBANDONATA);
        merci = new ArrayList<>();
        
        GeneraValori();
    }

    /**
     * Metodo per riodinare le varie creazioni dei attributi e chiama i metodi:
     * -GeneraGiocatoriNecessari() -SceltaGiorniPenalita() -GeneraMerce()
     */
    private void GeneraValori() {
        GeneraGiocatoriNecessari();
        SceltaGiorniPenalita();
        GeneraMerce();
    }

    /**
     * Metodo che in base al livello della carta genera in maniera random
     * l'attribbuto numero di membri dell'equipaggio necessari per completare la
     * carta
     *
     * this.giocatorinecessari -> il numero minimo di membri dell'equipaggio
     * richiesti sulla nave del giocatore
     */
    private void GeneraGiocatoriNecessari() {

        Random random = new Random();

        int scelta = random.nextInt(2) + 0;

        switch (this.lvl) {
            case 1:
                this.giocatorinecessari = 5 + scelta;
                break;
            case 2:
                this.giocatorinecessari = 7 + scelta;
                break;
            case 3:
                this.giocatorinecessari = 9 + scelta;
                break;
            default:
                System.out.println("ERROR: scelta casuale numero equipaggio necessario al completamento (errorTipe: switch) (class: StazioneAbbandonata)");
                break;
        }
    }

    /**
     * Metodo che in base al numero di 'this.giocatorinecessari' genera in
     * maniera determinata il numero dei giorni di penalità
     *
     * this.penalitagiorni -> al completamento della carta il giocatore perderà
     * 'this.penalitagiorni' giorni di volo
     */
    private void SceltaGiorniPenalita() {

        if (this.giocatorinecessari < 8) {

            this.penalitagiorni = 1;
        } else {
            this.penalitagiorni = 2;
        }
    }

    /**
     * Metodo che in base al livello della carta genera in maniera randomica un
     * numero di merci (rosse, gialle, verdi, blu) da assegnare al giocatore
     *
     * this.merci -> lista di oggetti 'Merce' che rappresentano le risorse
     * ottenute al completamento della carta
     */
    private void GeneraMerce() {

        Random random = new Random();
        int vtdp = 0, numMerci = 0;
        int r = 0, g = 0, v = 0, b = 0;

        switch (this.lvl) {
            case 1 -> {
                do {
                    r = random.nextInt(2) + 0;
                    g = random.nextInt(3) + 0;
                    v = random.nextInt(4) + 0;
                    b = random.nextInt(5) + 0;

                    vtdp = r * 4 + g * 3 + v * 2 + b;

                    numMerci = r + g + v + b;

                } while ((vtdp < 5 || vtdp > 8) || numMerci > 5);
            }
            case 2 -> {
                do {
                    r = random.nextInt(2) + 0;
                    g = random.nextInt(3) + 0;
                    v = random.nextInt(3) + 0;
                    b = random.nextInt(7) + 0;

                    vtdp = r * 4 + g * 3 + v * 2 + b;

                    numMerci = r + g + v + b;

                } while ((vtdp < 7 || vtdp > 10) || numMerci > 5);
            }
            case 3 -> {
                do {
                    r = random.nextInt(3) + 0;
                    g = random.nextInt(4) + 0;
                    v = random.nextInt(5) + 0;
                    b = random.nextInt(7) + 0;

                    vtdp = r * 4 + g * 3 + v * 2 + b;

                    numMerci = r + g + v + b;

                } while ((vtdp < 9 || vtdp > 12) || numMerci > 5);
            }
            default -> {
                System.out.println("ERROR: scelta randomica delle merci (errorTipe: switch) (class: Conmtrabandieri)");
            }
        }

        for (int i = 0; i < r; i++) {
            merci.add(new Merce(TipoMerce.MERCE_ROSSA));
        }
        for (int i = 0; i < g; i++) {
            merci.add(new Merce(TipoMerce.MERCE_GIALLA));
        }
        for (int i = 0; i < v; i++) {
            merci.add(new Merce(TipoMerce.MERCE_VERDE));
        }
        for (int i = 0; i < b; i++) {
            merci.add(new Merce(TipoMerce.MERCE_BLU));
        }
    }

    @Override
    public String toString() {
        String temp = "";
        temp = temp + "\nLivello carta:" + this.lvl
                + "\nTipo carta:" + this.tipo
                + "\nGiorni Penalità:" + this.penalitagiorni
                + "\nEquipaggio Necessario:" + this.giocatorinecessari + "\n le seguenti merci: ";
        for (int i = 0; i < this.merci.size(); i++) {
            temp = temp + this.merci.get(i).getTipoMerce().toString() + " - ";
        }
        temp = temp + "\n";

        return temp;
    }

    /**
     * esegue la carta:
     *
     * fa sceliere uno ad uno se vuole completare la carta (solo se ha i
     * parametri corretti) e consegna la ricompensa tgliendo i giorni di volo
     */
    @Override
    public ArrayList<Pedina> eseguiCarta(ArrayList<Pedina> elencoPedine) {

        boolean isCartaCompletata = false;
        int elenco = 0;

        while (!isCartaCompletata && elenco < elencoPedine.size()){
            
            if (elencoPedine.get(elenco).getGiocatore().getNave().getEquipaggio() >= this.giocatorinecessari) {

                if (elencoPedine.get(elenco).sceltaScambioMerciConGiorni(penalitagiorni, merci)) {

                    stampa.println("LA NAVE DI " + elencoPedine.get(elenco).getGiocatore().getNome() + "AL COSTO DI " + penalitagiorni + " GIORNO HA RICEVO:");
                    for (int i = 0; i < this.merci.size(); i++) {

                        stampa.println("->" + this.merci.get(i).getTipoMerce());
                    }

                    elencoPedine.get(elenco).distribuzioneMerce(this.merci);

                    elencoPedine.get(elenco).getTabellone().muoviPedina(elencoPedine.get(elenco), -penalitagiorni);

                    isCartaCompletata = true;
                }
            }else {
            	stampa.println("La nave di: "+elencoPedine.get(elenco).getGiocatore().getNome()+" non ha abbastanza equipaggio per completare la carta");
            }
            elenco++;
        }

        return elencoPedine;
    }

}
