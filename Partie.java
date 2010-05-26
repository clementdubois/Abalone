import java.lang.*;
import java.util.*;
import java.io.*;
import javax.swing.tree.*;
import javax.swing.*;


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
	
	protected final byte NB_BILLES_EJECTER = 2; 
	
	/** La fenetre de la partie*/
	public transient FenetreJeu f;
		
	/** Joueur 1 : le noir*/
	public final static int NOIR = 1;
	/** Joueur 2: le blanc*/
	public final static int BLANC = 2;
	
	/** Le gagnant de la partie, a 0 si personne n'a encore gagne*/
	protected int gagnant = 0;
	
	/** Le type des joueur.
	*
	*  J vs J => 0
	*  J vs IA => 1
	*  IA vs J => 2
	*  IA vs IA => 3
	*/
	public int typeJoueur = 0;
	
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
	
	/** Pour le chargement d'une partie*/
	public Partie(Partie p){
		terminee = p.terminee;
		gagnant = p.gagnant;
		plateau = new Plateau(p.plateau);
		dernierCoup = p.dernierCoup;
		coups = new DefaultTreeModel(dernierCoup);
		coups = p.coups;
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
	
	/** Savoir quel est le type du joueur en cours.
	* return true si c'est un joueur humain, false si c'est l'IA*/
	public boolean estHumain(){
		if(typeJoueur == 3 || (typeJoueur == 1 && plateau.getJoueurActuel() == BLANC) ||
		    (typeJoueur == 2 && plateau.getJoueurActuel() == NOIR))
						return false;
		else
						return true;
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
			
		/** Modifie les données liées à l'arbre lors d'un changement de l'etat du plateau
		* @param frere indique s'il faut creer un noeud frere (true) ou fils (false)
		*
		*/
		public void changementPlateau( ){
			//Creer un nouveau noeud a partie du plateau
			DefaultMutableTreeNode last = new DefaultMutableTreeNode(new Codage(plateau));
			//On ajoute last comme dernier coups
			dernierCoup.add(last);
			
			//On recharge le modele
			coups.reload();
			//Le dernier coup change de position
			dernierCoup = last;
			
		}
	
	public byte getNbBillesAEjecter() {
		return this.NB_BILLES_EJECTER;
	}
	
	/** Gere le deroulement d'un mouvement a  partir des coordonnees envoyees par l'interface
	*
	* @param premiere numero de case de la premiere bille
	* @param deuxieme numero de case de a deuxieme bille
	* @param vecteur sens du deplacement
	*/

	public void deroulementMouvement(byte premiere, byte deuxieme, byte vecteur){
		boolean is_valid;
		int gagnant = 0;
		
		Mouvement m = new Mouvement(premiere, deuxieme, vecteur);
		
		// On verifie le mouvement 
		is_valid = m.valider(plateau);
		//Si c'est valide on l'effectue
		if(is_valid){
			//On effectue le mouvement
			plateau.effectuer(m);
			//On affiche en console
			plateau.afficher();
			//On modifie l'etat de la partie
				//On change le joueur courant
				plateau.setJoueur();
				
				//On incremente le nombre de coups
				plateau.setNumCoup();
				//On verifie si il faut incrementer le score de la partie
				plateau.setScore();
				//Il ne s'agit pas d'une edition
				plateau.setEdited(false);
			
			//On ajoute le nouveau plateau comme fils
			changementPlateau();
			
		}
		
		//On rafraichie graphiquement
		f.expandAll(f.arbre);
		f.rafraichir(plateau);
		
		//On regarde si quelqu'un à gagné
		gagnant = vainqueur();
		if(gagnant != 0){
			JOptionPane jop = new JOptionPane();
			String message = "La partie se termine par K.O.\n";
			if(gagnant == NOIR)
				message+="Le joueur NOIR remporte la partie";
			else
				message+="Le joueur BLANC remporte la partie";
			
			jop.showMessageDialog(null, message); 	
			f.dispose();	
		}
		
	}
	
}