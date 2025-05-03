package partita.Configurazione;

import partita.Input;
import partita.Pedina;
import partita.giocatore.Colori;
import partita.giocatore.Giocatore;

public class ConfiguraGiocatore extends Input{
    private String nome;
    private Colori colorePedina;

    public ConfiguraGiocatore(){
        super();
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
        System.out.print("Inserisci il nome del giocatore (25 caratteri max): ");
        temp = super.input.nextLine();
        if(temp.length() <= 25){
            return temp;
        }
        else{
            System.out.println("Nome troppo lungo!!");
            return nome();
        }
    }

    private Colori colorePedina(){
        Colori c;
        visualizzaColori();
        System.out.print("Inserisci il numero del colore: ");
        int t = Integer.parseInt(super.input.nextLine());
        try{
            c = Colori.coloreSelezionato(t); 
        }catch(IllegalArgumentException iax){
            System.out.println(iax.getMessage().toString());
            return colorePedina();
        }
        return c;
    }

    public void visualizzaColori(){
        System.out.println("Colori disponibili: ");
        int i = 0;
        for(Colori c : Colori.values()){
            System.out.println(i + ") " + c);
            i += 1;
        }
    }   
}