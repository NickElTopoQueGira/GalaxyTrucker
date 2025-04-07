package tessera;

public class Centro extends Tessera{
    private static final int NUMERO_MASSIMO = 4;
        

        public Centro(){
            this.x = 0;
            this.y = 0;
        };

        public Centro(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX(){ return this.x; }
        public int getY(){ return this.y; }

        @Override
        public String toString(){
            return "X: " + this.x + " " + "Y: " + this.y;
        }
        
        
        
        
        
        
}
