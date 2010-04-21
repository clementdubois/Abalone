/** 
 * 
*/

public class Jeu{
	static Partie p[];	// static ou pas ?
	public static void main(String args[]){
		boolean valide;
		
/**
 * Cela va creer le serveur Partie qui ecoutera les joueurs chacun leur tour.
 * D'ailleurs, ce systeme va surement permettre aux joueurs de definir un coup en fonction d'un autre.
 * (Si le gars joue ca, alors jouer automatiquement ca...)		
 */
		//p[1] = new Partie(/*"blitz", "2 joueurs", "pseudonymeJoueur", "ia", "marguerite belge"*/);
/** 
 * On remarquera que l'on a pas precise le nom du joueur puisque c'est gere avec un champ joueurCourant dans la classe Partie : permet le jeu a  + que 2. 
 * Autre remarque : le vecteur s'applique a la 1ere bille mais s'appliquera ensuite a chacune des billes qu'il rencontrera. 
 */

	
		//Jeu de test
		Plateau p = new Plateau();
		p.afficher();
		for(byte i = 0 ; i < 6 ; i++)
			System.out.println(p.cases[61].getAdjacent(i));
		Mouvement m = new Mouvement((byte)(1), (byte)(1), Mouvement.BG);
		valide = m.valider(p);
		
		
		if(valide)
			System.out.println("Mouvement Valide");
		else
			System.out.println("Mouvement Invalide");
		
	}
}