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
		
		Partie p = new Partie();
		
		// try{Thread.sleep(3000);}catch(Exception e){}
		// p.listener.deroulementMouvement(1,1,3);
		// try{Thread.sleep(3000);}catch(Exception e){}
		// p.listener.deroulementMouvement(61,61,5);
	
		p.plateau.mouvementsValides(1);
		

	}
}