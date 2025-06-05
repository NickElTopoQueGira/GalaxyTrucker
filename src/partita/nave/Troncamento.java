package partita.nave;

import java.util.ArrayList;
import java.util.Objects;

import gioco.ComunicazioneConUtente;
import tessera.Posizione;
import tessera.Tessera;

public class Troncamento extends ArrayList<ArrayList<Tessera>>{
	


	private final static ComunicazioneConUtente stampa= ComunicazioneConUtente.getIstanza();
	private ArrayList<ArrayList<Tessera>> troncamento;
	
	
	/**
	 * costruttore
	 * @return elemento ArrayList<ArrayList<Tessera>>
	 */
	public ArrayList<ArrayList<Tessera>> Troncamento() {
		return this.troncamento=new ArrayList<ArrayList<Tessera>>();
		
	}

	
	
	/**
	 * Metodo che serve per la creazione della stringa per stampare il Trocamento con
	 * annessi tutti i dettagli di legende e descrizioni varie
	 * @param opzione
	 * @param inizioNaveV
	 * @param inizioNaveO
	 * @param fineNaveO
	 * @param troncamento 
	 * @return stringa toString del troncamento
	 */
	public static String TroncamentiToString(int inizioNaveV, int inizioNaveO, int fineNaveO, ArrayList<ArrayList<Tessera>> troncamento) {
		ArrayList<String> output = new ArrayList<>();
        ArrayList<String> tutteDescrizioni = new ArrayList<>();
        
        stampa.println("\n"+Troncamento.legenda());

        // Popola descrizioni solo una volta
        for (ArrayList<Tessera> riga : troncamento) {
            for (Tessera tessera : riga) {
                if (tessera.getPosizione() == Posizione.INTERNA) {
                    tutteDescrizioni.add("posizione(" + 
                        (tessera.getCoordinate().getX() + inizioNaveO) + ";" + 
                        (tessera.getCoordinate().getY() + inizioNaveV) + ") " + 
                        tessera.toLegenda());
                }
            }
        }
        
        StringBuilder numeri = new StringBuilder();
        output.add(numeri.toString());
        for (int i = inizioNaveO; i < fineNaveO; i++) {
            numeri.append(i < 10 ? "──" + i + "───" : "──" + i + "──");
        }

        numeri.append("┐");
        output.add(numeri.toString());
        
        int descrIndex = 0;
        for (int i = 0; i < troncamento.size(); i++) {
            for (int k = 0; k < 5; k++) {

                StringBuilder riga = new StringBuilder();
                for (int j = 0; j < troncamento.get(i).size(); j++) {
                    riga.append(troncamento.get(i).get(j).getriga(k)).append(" ");
                }

                if (k == 2) {
                    riga.append(i + inizioNaveV); // numero riga
                } else {
                    riga.append("│");
                }

                // Descrizione se disponibile
                if (descrIndex < tutteDescrizioni.size()) {
                    riga.append(" \t│ ").append(tutteDescrizioni.get(descrIndex));
                    descrIndex++;
                }

                output.add(riga.toString());
            }
            if((i+1 <troncamento.size())) {
	            StringBuilder riga = new StringBuilder();
	            for (int j = 0; j < troncamento.get(1).size(); j++) {
	                riga.append("      ");
	            }
	            riga.append("│");
	            // Descrizione se disponibile
	            if (descrIndex < tutteDescrizioni.size()) {
	                riga.append(" \t│ ").append(tutteDescrizioni.get(descrIndex));
	                descrIndex++;
	            }
	            
	            output.add(riga.toString());
            }
        }

        // Riga finale numeri colonna
        numeri = new StringBuilder();
        for (int i = inizioNaveO; i < fineNaveO; i++) {
            numeri.append(i < 10 ? "──" + i + "───" : "──" + i + "──");
        }
        if(descrIndex < tutteDescrizioni.size()) {
        	numeri.append("┘").append(" \t│ ").append(tutteDescrizioni.get(descrIndex));
        	descrIndex++;
        }else {
        	numeri.append("┘");
        }
        output.add(numeri.toString());

        // aggiunta eventuali descrizioni rimaste
        while (descrIndex < tutteDescrizioni.size()) {
        	StringBuilder riga = new StringBuilder();
            for (int j = 0; j < troncamento.get(1).size(); j++) {
                riga.append("      ");
            }
            
            riga.append(" ").append(" \t│ ").append(tutteDescrizioni.get(descrIndex));
            descrIndex++;

            output.add(riga.toString());
        }

        return String.join("\n", output);
    }
	
	
	
	
	/**
     * Metodo per generare la legenda combinata con colonne allineate
     * @return stringa con la legenda formattata in due colonne
     */
    private static String legenda() {
        String temp1 = legendaConnettori();
        String temp2 = legendaSimboli();

        String[] dati1 = temp1.split(",");
        String[] dati2 = temp2.split(",");

        int dimensioneMax = Math.max(dati1.length, dati2.length);
        dati1 = modificaSize(dati1, dimensioneMax);
        dati2 = modificaSize(dati2, dimensioneMax);

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < dimensioneMax; i++) {
            // "%-40s" = stringa a sinistra larga 40 caratteri
            result.append(String.format("%-40s %s\n", dati1[i], dati2[i]));
        }

        return result.toString();
    }

    /**
     * Metodo per allungare l array con stringhe vuote se necessario
     * @return stringhe vuote String[]
     */
    private static String[] modificaSize(String[] dati, int dimMax) {
        String[] nuoviDati = new String[dimMax];
        for (int i = 0; i < dimMax; i++) {
            nuoviDati[i] = (i < dati.length) ? dati[i] : "";
        }
        return nuoviDati;
    }

    /**
     * Metodo che restituisce la legenda dei connettori
     * @return legenda connettori String
     */
    public static String legendaConnettori() {
        return("Legenda Connettori:,"+
               "-) # connettore universale,"+
               "-) | connettore singolo,"+
               "-) v connettore doppio");
    }
    
    /**
     * Metodo per visualizzare la legenda dei connettori
     * @return legenda connettori String
     */
    public static String legendaSimboli() {
        return ("Legenda Simboli:,"+
               "-) [] merce," +
               "-) \033[0;31m!\033[0m  canna del cannone,"+
               "-) \033[0;31m§\033[0m  lato propulsore del motore,"+
               "-) @  lato scudo");
    }
    
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(troncamento);
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Troncamento))
			return false;
		Troncamento other = (Troncamento) obj;
		return Objects.equals(troncamento, other.troncamento);
	}
}
