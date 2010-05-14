import java.util.Scanner;

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
		
		
		//Liste des adjacences
		for(byte i=1; i<62; i++){
			System.out.println("Case Adjacente de la CASE : "+i);
			for(byte j=0; j<6; j++){
				System.out.print(j+" : "+p.plateau.cases[i].getAdjacent(j)+"| ");
			}
			System.out.println();
			System.out.println();
			
			System.out.print("numLigne : "+p.plateau.cases[i].getNumLigne());

			
		}
		
		// try{Thread.sleep(3000);}catch(Exception e){}
		// p.listener.deroulementMouvement(1,1,3);
		// try{Thread.sleep(3000);}catch(Exception e){}
		// p.listener.deroulementMouvement(61,61,5);
		

	}
}