package tessera;

public class Test {

	public static void main(String[] args) {
		
		
		FactoryTessera Factory=new FactoryTessera();
		Tessera t1= Factory.estraiTipo();

		System.out.println(t1.getTipoTessera().toString());

	}
	
	

}
