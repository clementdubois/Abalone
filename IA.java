import java.util.*;
import java.io.*;
import java.lang.*;

public class IA {
	/**
	*	Permet de connaitre le nombre d'appel a la fonction d'evaluation.
	*/
	private long iterator;

	
/*
				coefficients
 */ 
	/**
	* le coefficient associe a la valeur (sera utile lorsque le coeffPosition sera utilise)
	*/
	private float coeffEjection;
	/**
	*	inutilise pour le moment
	*/
	private float coeffPosition;	

	
	
	

/*
				valeurs associees au plateau
*/	
	private float valeurEjection;
	private float valeurPosition;

	/**
	*	determine la valeur correspondant a une partie gagnee.
	*/
	private float valeurMAX;
	
	
	/**
	*	
	*/
	
	
/*
				autres proprietes utiles
*/	
	
	/**
	 * 	numero de joueur
	 */ 
	public int numJoueur;

	/**
	* 	le nombre de coups a jouer avant qu'un plateau soit evalue
	*/
	private int profondeur;	
	
	/**
	*	le meilleur mouvement est renvoye lors de l'evaluation de l'ensemble des plateaux de jeu
	*/
	private Mouvement meilleurMouvement;
	
	/**
	*	le score associe au meilleur mouvement
	*/
	private float meilleurScore;
	

	private Vector<Mouvement> combinaisonGagnante;

	
/*
				METHODES
*/





/*
				constructeurs
*/	
	public IA(int numJoueur) {
		this.profondeur 	= 4;

		this.coeffEjection	= 3;
		this.valeurMAX 		= 100;
		this.coeffPosition	= 1;
		
		this.numJoueur = numJoueur;
		this.iterator = 0;
		this.combinaisonGagnante = new Vector<Mouvement>();
//		this.tempsDeReflexion=0;		
	}
	

/*
				get et set
*/


/*
				jouer un coup
*/

    public Mouvement jouer(Plateau p) {
		this.meilleurScore = this.maximiser(p, this.profondeur, -this.valeurMAX, this.valeurMAX);
		System.out.println(this.meilleurScore);
		System.out.println(this.combinaisonGagnante.size());
		// for(int i = 0; i < this.combinaisonGagnante.size() ; i++)
			// System.out.println(this.combinaisonGagnante.get(i));
		return this.meilleurMouvement;
    }

	
/*
				algorithmes de parcours : attention, avec alpha beta il faut commencer avec un noeud qu'on est cense maximiser puisque sinon ?
*/	

	/**
	*	fonction utilisee par l'adversaire puisqu'il choisira normalement le pire de nos coups.
	*/
// alpha = -l'infini, beta = +l'infini ainsi ils seront mis a jour
	private float minimiser(Plateau p, int profondeur, float alpha, float beta) { // on peut utiliser this.vectorMeilleurChemin pour stocker les meilleurs coups ? (permet d'expliquer pourquoi)
		Vector<Mouvement> mouvementsValides = new Vector<Mouvement>();
		if(profondeur == 0)
			return eval(p);

		int JoueurCourant = (this.numJoueur==1?2:1);
		mouvementsValides = p.mouvementsValides(JoueurCourant);
		if(mouvementsValides.size()==0) {
			System.out.println("size = 0!");				
			if(JoueurCourant == this.numJoueur)
				return this.valeurMAX; // il a ete sorti ou bloque.
			else
				return -this.valeurMAX;
		}
		
		for(int i = 0 ; i < mouvementsValides.size() ; i++) {
			Plateau temporaire = new Plateau(p);
			temporaire.effectuer(mouvementsValides.get(i));
			temporaire.setJoueur();
			temporaire.setNumCoup();
			temporaire.setScore();
			float valeur = maximiser(temporaire, profondeur-1, alpha, beta);	
			System.out.println("mini"+profondeur+" valeur="+valeur+" alpha="+alpha+" beta="+beta);
			if(valeur <= alpha) {
				// this.meilleurMouvement = mouvementsValides.get(i);
				return alpha;
			}
			if(valeur < beta && valeur > alpha) {
				// this.meilleurMouvement = mouvementsValides.get(i);
				beta = valeur;
			}
		}
		
		return beta;
	}
	
	/**
	*	fonction utilisee par l'ia puisqu'elle choisira le meilleur des coups possibles.
	*/	
	private float maximiser(Plateau p, int profondeur, float alpha, float beta) {
		Vector<Mouvement> mouvementsValides = new Vector<Mouvement>();	
		if(profondeur == 0) // normalement on est pas cense appeler la fonction d'evaluation ici car on souhaite evaluer les mouvements possibles de l'IA.
			return eval(p);

			
		int JoueurCourant = this.numJoueur;
		mouvementsValides = p.mouvementsValides(JoueurCourant);
		if(mouvementsValides.size()==0) {
			System.out.println("size = 0!");		
			if(JoueurCourant == this.numJoueur)
				return this.valeurMAX; // il a ete sorti ou bloque.
			else
				return -this.valeurMAX;
		}
		
		for(int i = 0 ; i < mouvementsValides.size() ; i++) {		
			Plateau temporaire = new Plateau(p);
			temporaire.effectuer(mouvementsValides.get(i));
			temporaire.setJoueur();
			temporaire.setNumCoup();
			temporaire.setScore();
			float valeur = minimiser(temporaire, profondeur-1, alpha, beta);
			System.out.println("maxi"+profondeur+" valeur="+valeur+" alpha="+alpha+" beta="+beta);			
			// alpha = alpha<valeur?alpha:valeur;
			if (valeur == beta) { // on ne peut trouver mieux que beta
				System.out.println("on a trouve une expulsion");
				this.combinaisonGagnante.add(mouvementsValides.get(i));
				this.meilleurMouvement = mouvementsValides.get(i);
				return beta;
			}
			if(valeur > alpha) { // il faut mettre la borne alpha a jour.
				this.meilleurMouvement = mouvementsValides.get(i);
				alpha = valeur;
			}
		}
		return alpha;		
	}

	private float eval(Plateau p) {
		if(p.getScore(this.numJoueur) != 0)
			return this.valeurMAX;
		else
			return 0;
	}

}