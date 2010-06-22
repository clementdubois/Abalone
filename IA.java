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
	

	private Mouvement[] combinaisonGagnante;


	private Plateau plateauInitial;
	
/*
				METHODES
*/





/*
				constructeurs
*/	
	public IA(int numJoueur) {
		this.profondeur 	= 2;

		this.coeffEjection	= 3;
		this.valeurMAX 		= 100;
		this.coeffPosition	= 1;
		
		this.numJoueur = numJoueur;
		this.iterator = 0;
		this.combinaisonGagnante = new Mouvement[this.profondeur+1];
//		this.tempsDeReflexion=0;		
	}
	

/*
				get et set
*/


/*
				jouer un coup
*/

    public Mouvement jouer(Plateau p) {
		this.plateauInitial = new Plateau(p);
		Plateau copie = new Plateau(p);
		this.meilleurScore = this.maximiser(copie, this.profondeur, -this.valeurMAX, this.valeurMAX);
		System.out.println(this.meilleurScore);
		for(int i = 0; i < this.combinaisonGagnante.length-1 ; i++)			
			System.out.println(this.combinaisonGagnante[i]);
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
			if(valeur == alpha) {
				// this.meilleurMouvement = mouvementsValides.get(i);
				
				return alpha;
			}
			if(valeur < beta && valeur > alpha) {
				this.combinaisonGagnante[this.profondeur-profondeur] = mouvementsValides.get(i);
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
				// try {
					// Thread.currentThread().sleep(2000);
				// } catch(InterruptedException e) {
				
				// }
				this.combinaisonGagnante[this.profondeur-profondeur] = mouvementsValides.get(i);
				this.meilleurMouvement = mouvementsValides.get(i);
				return beta;
			}
			if(valeur > alpha) { // il faut mettre la borne alpha a jour.
				this.combinaisonGagnante[this.profondeur-profondeur] = mouvementsValides.get(i);
				this.meilleurMouvement = mouvementsValides.get(i);
				alpha = valeur;
			}
		}
		return alpha;		
	}

	private float eval(Plateau p) {
		if(p.getScore(this.numJoueur) != this.plateauInitial.getScore(this.numJoueur))
			return this.valeurMAX;
		if(p.getScore(this.numJoueur==1?2:1) != this.plateauInitial.getScore(this.numJoueur==1?2:1))
			return -this.valeurMAX;
		
		return 0;//evaluerPosition(p);
	}
	
	private float evaluerPosition(Plateau p) {
		Plateau temporaire = new Plateau(p);
		float scorePositionnel 	= 0;
		float eloignementMAX 	= 0;
		float[] eloignement = new float[6];
		for(int i = 1 ; i <= temporaire.cases.length-1 ; i++) {
			if(temporaire.cases[i].getContenu() == this.numJoueur) {
				int k = i;
				while(temporaire.cases[k].getAdjacent((byte)0) != 0) {
						++eloignement[0];
						k = temporaire.cases[k].getAdjacent((byte)0);
				}
				eloignementMAX = eloignement[0];
//				System.out.println("eloignement[0]"+eloignement[0]);				
				for(int j = 1 ; j < 6 ; j++) {
					k = i;
					while(temporaire.cases[k].getAdjacent((byte)j) != 0) {
						++eloignement[j];
						k = temporaire.cases[k].getAdjacent((byte)j);
					}
					if(eloignement[j] > eloignementMAX)
						eloignementMAX = eloignement[j];
//					System.out.println("eloignement["+j+"]"+eloignement[j]);
				}
				scorePositionnel = eloignementMAX;
			}
		}
/*		try {
			Thread.currentThread().sleep(2000);
		} catch(InterruptedException e) {
		
		}
		System.out.println("scorePositionnel"+(scorePositionnel));
*/		return scorePositionnel;
	}	

}