package tessera;

import tessera.motore.Motore;

public class TestTessere {

	public static void main(String[] args) {
		
		
		FactoryTessera Factory=new FactoryTessera();
		Tessera t1= Factory.estraiTipo();
		Tessera t2= Factory.estraiTipo();
		
		//stampa connettori di t1
		System.out.println(t1.getTipoTessera().toString());
		System.out.println("\t"+t1.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t1.getLatiTessera().getLeft().toString()+"\t\t"+t1.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t1.getLatiTessera().getDown().toString()+"\t\n");
		
		t1.ruota(); //ruota di 90 gradi a dx
		//stampa connettori di t1
		System.out.println(t1.getTipoTessera().toString());
		System.out.println("\t"+t1.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t1.getLatiTessera().getLeft().toString()+"\t\t"+t1.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t1.getLatiTessera().getDown().toString()+"\t\n");
		
		
		
		
		//stampa centro con connettori
		Tessera t3 = new Centro();
		System.out.println(t3.getTipoTessera().toString());
		System.out.println("\t"+t3.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t3.getLatiTessera().getLeft().toString()+"\t\t"+t3.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t3.getLatiTessera().getDown().toString()+"\t\n");
		
		Tessera t4 = new Motore();
		System.out.println(t4.getTipoTessera().toString());
		System.out.println("\t"+t4.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t4.getLatiTessera().getLeft().toString()+"\t\t"+t4.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t4.getLatiTessera().getDown().toString()+"\t\n");
		System.out.println(((Motore)t4).getLatoMotore().toString());
		t4.ruota();
		System.out.println(t4.getTipoTessera().toString());
		System.out.println("\t"+t4.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t4.getLatiTessera().getLeft().toString()+"\t\t"+t4.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t4.getLatiTessera().getDown().toString()+"\t\n");
		System.out.println(((Motore)t4).getLatoMotore().toString());
		t4.ruota();
		System.out.println(t4.getTipoTessera().toString());
		System.out.println("\t"+t4.getLatiTessera().getUp().toString()+"\n");
		System.out.println(t4.getLatiTessera().getLeft().toString()+"\t\t"+t4.getLatiTessera().getRight().toString()+"\n");
		System.out.println("\t"+t4.getLatiTessera().getDown().toString()+"\t\n");
		System.out.println(((Motore)t4).getLatoMotore().toString());
		
		
		System.out.println(Tessera.getCurrentSize());
	}
	
	

}
