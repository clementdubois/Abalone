import java.lang.*;
import java.util.*;


/**
* Une partie est simplement un serveur qui attend des connexions puis attend les ordres de la part de joueurs et les redistribue a tous les clients (joueurs, ia, spectateurs) Le createur de la partie possede donc le serveur de la partie. Le serveur de partie possede son propre plateau de jeu sur lequel il effectue les modifications.
*/
public class Partie {
	protected ArrayList<Joueur> joueurs; // permet de gerer plus de 2 joueurs.
	public Plateau plateau; // 61 cases contenant soit une bille soit null (empty)
	/** Stocke le numero joueur qui doit jouer */
	protected int joueurActuel;
/**
 * terminee est effectivement utile car on peut changer les conditions de victoire a l'interieur d'une partie.	
 */
	protected boolean terminee;
	protected String variante;
	protected int numCoup;
	protected final byte NB_BILLES_EJECTER = 1; 
	/** Score actuel de chaque joueur*/
	protected int[] score;
	/** MODE SANS SERVEUR*/
	public FenetreJeu f;
	//
	public ClickAction listener;
	
	public Partie() {
		joueurActuel = 0;
		numCoup = 1;
		terminee = false;
		score = new int[2];
		score[0] = 0;
		score[1] = 0;
		plateau = new Plateau(); // initialise les valeurs des vecteurs
		listener = new ClickAction(this);
		f = new FenetreJeu(plateau,listener);
		
		
 		// int i = 0;
 		//  		joueurs = new ArrayList<Joueur>();
 		//  		for(String pseudocourant : pseudo) {
 		// 		joueurs.add(new Joueur(pseudocourant));//joueurs[i++] = new Joueur(Pseudocourant);
 		// 	}
 		// 
 		// 	//On envoie la partie en cours pour pouvoir la modifier
 		// 	
 		// 	i = 0;
 		// 	System.out.println();
 		// 
 		// 	while(!terminee) {
 		// 		
 		// 		this.coupActuel = this.joueurs.get(i).jouer(); // on attend que le joueur envoie son coup.
 		// 		System.out.println("Partie::J"+(i%joueurs.size())+" joue : "+coupActuel.getPremiere()+"-"+coupActuel.getDerniere()+"-"+coupActuel.getVecteur());
 		// 
 		// 		this.plateau.effectuer(coupActuel);
 		// 		
 		// 		//Dès qu'une bille est tombé on termine la partie
 		// 		if(this.plateau.cases[0].getContenu() != Case.VIDE) {
 		// 			if((this.joueurs.get(i).score += 1) == this.NB_BILLES_EJECTER) {
 		// 				this.terminee = true;
 		// 			}
 		// 			this.plateau.cases[0].setContenu(Case.VIDE);				
 		// 		}
 		// 		
 		// 		i = (i+1)%joueurs.size(); //On passe au jouer suivant
 		// 		
 		// 		this.plateau.afficher();//Affiche en console
 		// 		f.rafraichir(plateau); //Affiche en graphique
 		// 	}
 		// 	System.out.println("Partie terminee.");
	}
	
	/** Change le jouer actuel pour savoir qui doit jouer*/
	public void setJoueur(){
		if(joueurActuel == 0)
			joueurActuel = 1;
		else
			joueurActuel = 0;
	}
	
	/** Vérifie le plateau pour savoir si une bille est tombé au dernier coup et incremente le score*/
	public void setScore(){
		//Dès qu'une bille est tombé incremente le score
		if(this.plateau.cases[0].getContenu() != Case.VIDE) {
			if((this.score[joueurActuel] += 1) == this.NB_BILLES_EJECTER) {
				this.terminee = true;
			}
			//On revide la case trou comme le score à été pris en compte
			this.plateau.cases[0].setContenu(Case.VIDE);				
		}
	}
	
	/** Ajoute un coup au compteur*/
	public void setNumCoup(){
		numCoup++;
	}
	
	/** Affiche une partie en console avec le System*/
	public String toString(){
		String afficher="";
		
		afficher += "Joueur Actuel : "+joueurActuel+"\n";
		afficher += "Numero du coup : "+numCoup+"\n";
		afficher += "Score du joueur 0 : "+score[0]+"\n";
		afficher += "Score du joueur 1 : "+score[1]+"\n";
		
		if(terminee)
			afficher += "Partie terminée\n";
		
		return afficher;
	}
}