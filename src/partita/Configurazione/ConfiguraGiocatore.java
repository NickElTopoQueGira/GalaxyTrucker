package partita.Configurazione;

import gioco.ComunicazioneConUtente;
import partita.Pedina;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class ConfiguraGiocatore{
    private ComunicazioneConUtente com;
    private String nome;
    private Colori colorePedina;

    public ConfiguraGiocatore(){
        com = ComunicazioneConUtente.getIstanza();
    }

    public Giocatore craGiocatore(){
        configuraGiocatore();
        Pedina pedina = new Pedina(colorePedina);
        return new Giocatore(this.nome, pedina);
    }

    private void configuraGiocatore(){
        this.nome = nome();
        this.colorePedina = colorePedina(); 
    }

    private String nome(){
        String temp = "";
        com.print("Inserisci il nome del giocatore (25 caratteri max): ");
        temp = com.consoleRead();
        if(temp.length() <= 25){
            return temp;
        }
        else{
            com.println("Nome troppo lungo!!");
            return nome();
        }
    }

    private Colori colorePedina(){
        Colori c;
        visualizzaColori();
        com.print("Inserisci il numero del colore: ");
        int t = Integer.parseInt(com.consoleRead());
        try{
            c = Colori.coloreSelezionato(t); 
        }catch(IllegalArgumentException iax){
            com.println(iax.getMessage().toString());
            return colorePedina();
        }
        return c;
    }

    public void visualizzaColori(){
        com.println("Colori disponibili: ");
        int i = 0;
        for(Colori c : Colori.values()){
            com.println(i + ") " + c);
            i += 1;
        }
    }   
}