package partita.configurazione;

import gioco.ComunicazioneConUtente;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class ConfiguraGiocatore{
    private final ComunicazioneConUtente com;
    private String nome;
    private Colori colorePedina;

    public ConfiguraGiocatore(){
        com = ComunicazioneConUtente.getIstanza();
    }

    /**
     * Metodo per creare un giocatore
     * @return nuovo Giocatore
     * */
    public Giocatore creaGiocatore(){
        configuraGiocatore();
        Giocatore g=new Giocatore(this.nome, this.colorePedina);
        return g;
    }

    /**
     * Metodo per configurare il giocatore
     * */
    private void configuraGiocatore(){
        this.nome = nome();
        this.colorePedina = colorePedina(); 
        com.println("Nome scelto: "+this.colorePedina.getCodiceColore()+this.nome+"\u001B[0m");
    }

    /**
     * Metodo per chiedere all'utente il suo nome
     * @return nome utente
     * */
    private String nome(){
        String temp;
        boolean pass = false;
        do{
            com.print("Inserisci il nome del giocatore (25 caratteri max): ");
            temp = com.consoleRead();
            if(temp.isBlank() == false && temp.length() <= 25){
                pass = true;
            }else{
                com.erroreImmissioneValore();
            }
        }while(!pass);
        return temp;
    }

    /**
     * Metodo per chiedere all'utente di che colore vuole la pedina
     * @return colore pedina
     * */
    private Colori colorePedina(){
        Colori c = null;
        visualizzaColori();
        boolean pass = false;
        do{
            com.print("Inserisci il numero del colore: ");
            String val = this.com.consoleRead();
            if(val.isBlank() || val.isEmpty()){
                this.com.erroreImmissioneValore();
            }else{
                try{
                    int t = Integer.parseInt(val);
                    c = Colori.coloreSelezionato(t);
                    if(null != c){
                        pass = true;
                    }
                }catch(NumberFormatException nfe){
                    this.com.erroreImmissioneValore();
                }catch(IllegalArgumentException iax){
                    this.com.printError(iax.getMessage());
                }
            }

        }while(!pass);

        return c;
    }

    /**
     * Metodo per visualizzare i colori disponibili
     * */
    public void visualizzaColori(){
        com.println("Colori disponibili: ");
        int i = 0;
        for(Colori c : Colori.values()){
            com.println(i+1 + ") " + c.getname());
            i += 1;
        }
    }   
}