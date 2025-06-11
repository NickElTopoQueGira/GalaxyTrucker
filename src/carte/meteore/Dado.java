package carte.meteore;

import java.util.Random;

public class Dado {

    public Dado() {}
    
    /**
     * Metodo che replica il tito di un dado
     * @return valore di un dado
     */
    public int dadoSingolo() {

        Random random = new Random();

        int dado = random.nextInt(6) + 1;

        return dado;
    }
    
    /**
     * Metodo che replica il tito di due dadi
     * @return somma di due dadi
     */
    public int dadiDoppi() {

        int d1 = this.dadoSingolo();
        int d2 = this.dadoSingolo();

        return d1 + d2;
    }
}
