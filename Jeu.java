/** 
 * 
*/

public class Jeu {
	/* Test des fonctions : 
	*    Afficher fonctionne
	*    Effectuer fonctionne
	*    GetAdjacent fonctionne
	*    Valider 
	*    Case Intermediaire fonctionne
	*    
	*/
	private static Partie[] p;
	
	public static void main(String args[]){
		String[] pseudo = new String[2];
		pseudo[0] = "Vincent";
		pseudo[1] = "Bidule";
		Partie[] p = new Partie[1];
		p[0] = new Partie(pseudo);
		
		
/*		
		boolean valide;
		Plateau plateau = new Plateau();
	
		//Jeu de test
		Plateau p = new Plateau();
		p.afficher(); //OK
		
		Mouvement m = new Mouvement((byte)(56), (byte)(55), Mouvement.HD);
		p.effectuer(m);
		m = ia.;
		p.effectuer(m);
		p.afficher();
*/		
		
		// if(valide)
		// 						System.out.println("Mouvement Valide");
		// 					else
		// 						System.out.println("Mouvement Invalide");
		
	}
}