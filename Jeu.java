

public class Jeu {
	// Test des fonctions : 
	//     Afficher fonctionne
	//     Effectuer fonctionne
	//    GetAdjacent fonctionne
	//    Valider 
	//     Case Intermediaire fonctionne
	   
	
	private static Partie[] p;
	
	public static void main(String args[])throws Exception{

		
		Partie p = new Partie();
		Plateau pla = p.plateau;
		System.out.println("--------------"+pla);
		Codage cod = new Codage(pla);
		System.out.println(cod.decodage());
		// try{Thread.sleep(3000);}catch(Exception e){}
		// p.listener.deroulementMouvement(1,1,3);
		// try{Thread.sleep(3000);}catch(Exception e){}
		// p.listener.deroulementMouvement(61,61,5);
		

	}
}