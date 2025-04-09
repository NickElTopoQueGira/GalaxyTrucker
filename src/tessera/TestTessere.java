package tessera;

public class TestTessere {

	public static void main(String[] args) {
		
		
		FactoryTessera Factory=new FactoryTessera();
		Tessera t1= Factory.estraiTipo();
		Tessera t2= Factory.estraiTipo();
		System.out.println(t1.getTipoTessera().toString());
		System.out.println("\t"+t1.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t1.getLatiTessera().getLeft().toString()+"\t\t"+t1.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t1.getLatiTessera().getDown().toString()+"\t\n");
		
		System.out.println(Tessera.getCurrentSize());
	}
	
	

}
