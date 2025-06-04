package partita.nave;

public class GestioneEquipaggio{
    private int numeroModuliCosmonauti; 
    private int numeroModuliAlieniViola;
    private int numeroModuliAlieniMarroni;

    private int numeroCosmonauti; 
    
    public GestioneEquipaggio(){
        this.numeroModuliCosmonauti = 0; 
        this.numeroModuliAlieniViola = 0;
        this.numeroModuliAlieniMarroni = 0;
    
        this.numeroCosmonauti = 0;
    }

    public void setNumeroModuliCosmonauti(int numeroModuliCosmonauti){ this.numeroModuliCosmonauti = numeroModuliCosmonauti; }
    public void setNumeroModuliAlieniViola(int numeroModuliAlieniViola){ this.numeroModuliAlieniViola = numeroModuliAlieniViola; }
    public void setNumeroModuliAlieniMarroni(int numeroModuliAlieniMarroni){ this.numeroModuliAlieniMarroni = numeroModuliAlieniMarroni; }

    public int getNumeroModuliCosmonauti(){ return this.numeroModuliCosmonauti; }
    public int getNumeroModuliAlieniViola(){ return this.numeroModuliAlieniViola; }
    public int getNumeroModuliAlieniMarroni(){ return this.numeroModuliAlieniMarroni; }

    public int getNumeroModuliAlini(){
        return this.numeroModuliAlieniMarroni + this.numeroModuliAlieniViola;
    }

    public void setNumeroCosmonauti(int numeroCosmonauti){ this.numeroCosmonauti = numeroCosmonauti; }

    public int getNumeroCosmonauti(){ return this.numeroCosmonauti; }

}
