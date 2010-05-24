import java.util.*;


public class IA {
	// temps : java.util.*; <= Timer	
	// vkaenemi@ee.ethz.ch - ssilvan@ee.ethz.ch
	
	byte numJoueur;
	
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
		this.deep = 1;
		this.nom  = "neuneu";
		this.numJoueur = 0;
	}
/*
	public IA(String nom) {
		this.nom = nom;
	}
*/
	
	public static double fonctionEvaluation(Plateau p, Mouvement m) {	
		double 	position	= 1.1;
		
		
		
		// correspond au score cumule de toutes les billes.
		// le score positionnel d'une bille correspond au minimum des 6 distances vis a vis du bord : un deplacement correspond a score+=1.
		
/*		
		int typePartie = 0; // 0 => debut de partie, 1 => midgame, 2 => fin de partie
		
		if (typePartie == 0) {
			
		}
*/
		Plateau temporaire = new Plateau(p);
		temporaire.effectuer(m);
		temporaire.setScore();
		if(temporaire.score[0] != p.score[0])
			return 10.0;
		
		
		return 1.0;
		
	}

	private double evaluerSumito(Mouvement m) {
	/*
		if(
	
		// determine si l'adversaire pourra s'en sortir au prochain coup.
		if() {
			scoreSumito *= 2;
		}
	
	*/
		return 1.0;
	}
	
	private double evaluerSecurite() {
		return 1.0;
	}
	
	private double trouverBillesInutiles() {
		return 1.0;
	}
	
/*	
	private regroupementAuCentre() {
		double position = 5.0; // on augmente de façon considérable le coefficient associe a la position.
	}
*/	
	
	
/**
 * rechercher les billes en danger (lorsque l'adversaire n'a qu'une seule bille a ejecter on regarde d'abord si il a des billes a ejecter pour ne pas perdre de temps)
 */ 

	private Vector billesEnDanger (Plateau p) {
		Vector billesEnDanger = new Vector();
/*
	ici on doit simplement ajouter au vecteur toute bille impliquée par un mouvement de l'adversaire qui amene une bille dans cases[0]
*/		
		return billesEnDanger;
	}
	
	
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
