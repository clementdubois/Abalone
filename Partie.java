import java.lang.*;
import java.util.*;
import java.io.*;
import javax.swing.tree.*;


/**
* Une partie est simplement un serveur qui attend des connexions puis attend les ordres de la part de joueurs et les redistribue a tous les clients (joueurs, ia, spectateurs) Le createur de la partie possede donc le serveur de la partie. Le serveur de partie possede son propre plateau de jeu sur lequel il effectue les modifications.
*/
public class Partie implements Serializable{
	/** Contient la liste des coups joues(modele pour le JTree dans le JFrame)*/
	public DefaultTreeModel coups;
	/** Le code du coup en cours*/
	public DefaultMutableTreeNode dernierCoup;
	
	/** 61 cases contenant soit une bille soit null (empty)*/
	public Plateau plateau; 
/**
 * terminee est effectivement utile car on peut changer les conditions de victoire a l'interieur d'une partie.	
 */
	protected boolean terminee;
	
	protected final byte NB_BILLES_EJECTER = 6; 
	
	/** La fenetre de la partie*/
	public transient FenetreJeu f;
	
	/** Joueur 1 : le noir*/
	public final static int NOIR = 1;
	/** Joueur 2: le blanc*/
	public final static int BLANC = 2;
	
	/** Le gagnant de la partie, a 0 si personne n'a encore gagne*/
	protected int gagnant = 0;
	
	public Partie() {
		terminee = false;
		plateau = new Plateau(); 
		//On initialise l'arbre de coups avec le premier plateau (ca sera la racine)
		dernierCoup = new DefaultMutableTreeNode(new Codage(plateau));
		coups = new DefaultTreeModel(dernierCoup);

		//On lance la fenetre graphique
		f = new FenetreJeu(this);
	}
	
	/** Creer une partie avec une position de depart*/
	public Partie(Case[] position){
		terminee = false;
		//On créer un plateau avec une position de depart
		plateau = new Plateau(position); 
		//On initialise l'arbre de coups avec le premier plateau (ca sera la racine)
		dernierCoup = new DefaultMutableTreeNode(new Codage(plateau));
		coups = new DefaultTreeModel(dernierCoup);

		//On lance la fenetre graphique
		f = new FenetreJeu(this);
	}
	
	/** Un joueur abandonne la partie, la partie est terminee et l'autre joueur gagne
	* @param numJ le numero du joueur qui abandonne (1 ou 2)
	*/
	public void abandonner(int numJ){
		//La partie est finie
		this.terminee = true;
		
		//On indique le gagnant de la partie
		if(numJ == NOIR){this.gagnant = BLANC;}
		else if(numJ == BLANC){this.gagnant =  NOIR;}
	}	
	
	/** Verifie que la partie est terminee et renvoit le vainqueur
	* @return 0 si la partie n'est pas fini, NOIR si le joueur 1 gagne, BLANC si le joueur deux gagne
	*/	
	public int vainqueur(){
		if(plateau.getScore(NOIR) >= NB_BILLES_EJECTER){
			terminee = true;
			gagnant = NOIR;
		}
		else if(plateau.getScore(BLANC) >= NB_BILLES_EJECTER){
			terminee = true;
			gagnant = BLANC;
		}
		else{
			terminee = false;
			gagnant = 0;
		}
		
	  return gagnant;
	}
			
	
	
}