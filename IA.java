
public class IA {
	// temps : java.util.*; <= Timer	
	// vkaenemi@ee.ethz.ch - ssilvan@ee.ethz.ch
	
/**
 * son petit nom
 */
	private String nom;
/**
 * sur quelle profondeur l'ia effectuera-t-elle ses recherches.
 */
	private byte deep;
/**
 * la strategie a appliquer : on stockera des coefficient (des poids) associes a chacun des facteurs.
 */
	private byte heuristiques[];	
/**
* facteurs permettant d'attribuer un score a un coup : grace a ces facteurs on pourra evaluer un plateau.
 *   
 *	connectivite
 *	ejection : le poids de ejection augmente avec le nombre de billes qu'on a deja ejectees
 *   	
 *   
 *   
 *
 *    
 */	
 	float[] facteursDeterminants;
 /**
  * il faut une fonction qui permet de recuperer l'ensemble des mouvements possibles par rapport a un Plateau envoye en parametre : on utilisera la fonction d'evaluation dessus.
  */
 	Mouvement[] mouvementsPossibles;
	Plateau plateau;
 	

/**
 * evaluation du joueurs ? permet de savoir s'il est plutot du genre defensif ou offensif afin de determiner une heuristique.
 */
	
	
	
	public IA() {
		
	}
/*
	public IA(String nom) {
		this.nom = nom;
	}
*/	
	
/**
 * fonction d'evaluation
 *
 	public float evaluerPlateau() {
 		
 	}
 */
 
/**
 * alpha-beta
 */

/**
 * minamax
 */

/**
 * genetique : on garde les poids (associes aux facteurs) du vainqueur et on genere son nouveau challenger en prenant ses poids comme base (on applique une tres legere evolution)
 * il existe differentes manieres d'evoluer donc il nous faudra differentes branches d'evolution (une partie est declaree nulle au bout de 300 coups, et est consideree comme une defaite pour l'ia : ainsi on aura une approche offensive)
 */ 
 
/**
 * recherche du coup gagnant sur une certaine profondeur
 */
 
 
/**
 * strategies de jeu : heuristique.
 *   defini les coefficients associes aux facteurs.
 *  
 * algos :
 * 1/ tente de trouver le meilleur moyen de regrouper les billes au centre (cela peut passer par une pseudo-attaque si le style du joueur!="defenseur" ou par un déplacement simple)
 * 2/ 
 *
 *	
 * chercher la victoire en x coups : explore des branches en mettant ses parametres concentres sur la recherche de sortie de bille (utile en fin de partie).
 * 
 * 
 */	
/* 
	private score setHeuristique(byte h) {
		this.heuristique = h;
	}
*/	

	
	
	
	
/**
 * methodes AVANT une partie.
 */
/* 
	public preparerPartie() {
		// les infos de la partie : variante, (blitz), .	
	}
	
	public creerPartie() {
		// commme un joueur : on attend	une connexion à la partie.
		
	}
	
	public chercherPartie() {
		// comme un joueur : on veut récupérer la liste des parties en attente de joueur.	
	}
*/	
/**
 * opening : lorsque la fonction d'evaluation sera pas mal il serait interessant de generer les ouvertures sur une profondeur importante.
 */
 
 
/**
 * il faut une methode pour forcer l'ia a jouer si elle est consideree trop lente : la methode est donc public
 * on va simplement parcourir la liste des resultats des evaluations et
 */
 /*
 	public joue() {
 		foreach(JTree) {
 			
 		}
 	}
 */
}
