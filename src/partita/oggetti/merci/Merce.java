package partita.oggetti.merci;

public class Merce{
    private final TipoMerce tipoMerce;
    
    public Merce(TipoMerce tipoMerce){
        this.tipoMerce = tipoMerce;
    }

    public TipoMerce getTipoMerce(){
        return this.tipoMerce;
    }
}
