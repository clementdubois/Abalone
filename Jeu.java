/** 
 * 
*/

public class Jeu {
	private Partie p[];	// static ou pas ?
	public static void main(String args[]){
		boolean valide;
		Plateau plateau = new Plateau();
		FenetreJeu f = new FenetreJeu(plateau);
		
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
		//test case adjacente
		for(byte i = 0 ; i < 6 ; i++)
			System.out.println(p.cases[55].getAdjacent(i));

		Mouvement m = new Mouvement((byte)(1), (byte)(1), Mouvement.BG);
		//test valider mouvement
		//valide = m.valider(p);
		//test effectuer mouvement
		p.effectuer(m);
		p.afficher();
		
		//test caseIntermediaire
		System.out.println(Mouvement.DD);
		System.out.println("adjacent : "+m.getPremiere()+" vers : "+m.getVecteur()+" = "+p.cases[m.getPremiere()].getAdjacent(Mouvement.DD));
		byte inter = p.caseIntermediaire(m.getPremiere(), m.getDerniere());
		System.out.println("Inter = "+inter);
		
		
		// OH OUI CA MARCHE ON EST TROP PUISSANTS !!!!!!!!
		
		
		// if(valide)
		// 			System.out.println("Mouvement Valide");
		// 		else
		// 			System.out.println("Mouvement Invalide");
		
	}
}