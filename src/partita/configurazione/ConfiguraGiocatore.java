package partita.configurazione;

import gioco.ComunicazioneConUtente;
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
        return new Giocatore(this.nome, this.colorePedina);
    }

    private void configuraGiocatore(){
        this.nome = nome();
        this.colorePedina = colorePedina(); 
        com.println("Nome scelto: "+this.colorePedina.getCodiceColore()+this.nome+"\u001B[0m");
    }

    private String nome(){
        String temp = "";
        com.print("Inserisci il nome del giocatore (25 caratteri max): ");
        temp = com.consoleRead();
        if(temp.length() <= 25){
            return temp;
        }
        else{
            com.erroreImmissioneValore();
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
            com.println(i+1 + ") " + c.getname());
            i += 1;
        }
    }   
}