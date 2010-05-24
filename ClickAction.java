import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.*;


	/**Cette classe permet de gerer les clicksouris sur le plateau */
public class ClickAction extends MouseAdapter {
	/** compteur de click*/
	private int nbClick;
	/** La fenetre de jeu sur lequel s'applique le click action*/
	private FenetreJeu fenetre; 
  /** Les billes selectionner lors de clique*/
	private byte premiere,deuxieme,vecteur, troisieme, intermediaire;
	/** Les coordonnees des cliques*/
	private int yb,yv,xb,xv;
	int x,y;
	
	/** On evoie la partie associee a la fenetre pour pouvoir la modifier 
	* @param p La partie sur laquel se deroule les actions.
	*/
	public ClickAction(FenetreJeu fen){
		nbClick = 1;
		//La fenetre sur lequel s'applique le clickAction
		this.fenetre = fen;
	}
	
	private void deroulementMouvement(Plateau p, Mouvement m) {
		
		// On verifie le mouvement 
		boolean is_valid = m.valider(fenetre.partie.plateau);
		//Si c'est valide on l'effectue
		if(is_valid){
			//On effectue le mouvement
			fenetre.partie.plateau.effectuer(m);
			//On affiche en console
			fenetre.partie.plateau.afficher();
			//On modifie l'etat de la partie
				//On change le joueur courant
				fenetre.partie.plateau.setJoueur();
				
				//On incremente le nombre de coups
				fenetre.partie.plateau.setNumCoup();
				//On verifie si il faut incrementer le score de la partie
				fenetre.partie.plateau.setScore();
			
			//On ajoute le nouveau plateau comme fils
			DefaultMutableTreeNode last = new DefaultMutableTreeNode(new Codage(fenetre.partie.plateau));
			fenetre.partie.dernierCoup.add(last);
			fenetre.partie.coups.reload();
			fenetre.partie.dernierCoup = last;
			
			//On teste pour voir le parent et lui meme
			// System.out.println("Nouveau Fils = "+((Codage)(noeuFils.getUserObject())).decodage() );
			// 			System.out.println("Le parent = "+((Codage)(((DefaultMutableTreeNode)(noeuFils.getParent())).getUserObject())).decodage());
			
		}
		
		//On rafraichie graphiquement
		fenetre.rafraichir(fenetre.partie.plateau);
		
					
	}

	/**
	* On surcharge la methode mouseClicked pour quelle recupere et envoie le numero des billes selectionnees.
	* -Clique gauche => selection de bille														(0)
	* -Clique droit => annuler selection															(0)
	* -CTRL + Clique gauche => supprimer une bille (édition)    			(128)
	* -ALT + Clique gauche => Ajouter une bille noir (édition)				(512)
	* -ALT + Clique droit => Ajouter une bille blanche (édition)  	  (512)
	* -CTRL + ALT + Clique gauche => Marquer une bille (édition)      (640)
	*/
	public void mouseClicked(MouseEvent event){
		int numCaseSelectionner = transcription(event.getY(),event.getX());
/* 
	on doit cliquer pour faire jouer l'ia
*/
			 int profondeur = 1;
			 if(this.fenetre.partie.plateau.getJoueurActuel() == 1) {
			 	deroulementMouvement(fenetre.partie.plateau, IA.jouer(this.fenetre.partie.plateau, 1, 1));
				return;
			 }
/*			
	fin ia
*/	

		//Si on à juste appuyé sur le clique c'est qu'on veut selectionner une bille pour un mouvement
		if(event.getButton() == MouseEvent.BUTTON1 && event.getModifiersEx() == 0){
			//C'est le premier clique pour la sélection en vue d'un mouvement
			if(nbClick == 1){
				premiere = transcription(event.getY(),event.getX());
				if(fenetre.partie.plateau.chercheBilles(fenetre.partie.plateau.getJoueurActuel()).contains((byte)premiere)){
					System.out.println("premiere bille: " + premiere);
					fenetre.rafraichirBS1(premiere);
					nbClick = 2;
				}
				else{
					System.out.println("Attention, ne cliquez que sur vos billes ! \n");
				}	
			}//C'est le deuxieme clique pour selectionner les autres billes du mouvement
			else if(nbClick == 2){
				yb = event.getY();
				xb = event.getX();
				deuxieme = transcription(event.getY(),event.getX());
				//On verifie que le joueur ne selectionne pas les billes adverses
				if(fenetre.partie.plateau.chercheBilles(fenetre.partie.plateau.getJoueurActuel()).contains((byte)deuxieme)){
					System.out.println("deuxieme bille: " + deuxieme);
					//on recupere la bille intermediaire aux 2 billes selectionnees
					intermediaire = fenetre.partie.plateau.caseIntermediaire((byte)premiere, (byte)deuxieme);
					fenetre.rafraichirBS2(deuxieme,intermediaire);
					nbClick = 3;
				}
				else{
					System.out.println("Attention, ne cliquez que sur vos billes ! \n");
				}	
			}//C'est le clique qui va determiner dans quel direction on veut déplacer la bille (avant => 53 lignes, après => 11)
			else if(nbClick == 3){
				troisieme = transcription(event.getY(),event.getX());

				//On vérifie que la deuxieme selectionnee a un vecteur par rapport a la troisieme
			  if((vecteur = fenetre.partie.plateau.cases[deuxieme].vecteurAdjacent(troisieme) )!= -1){
					deroulementMouvement(premiere,deuxieme,vecteur);
				}
				else{
					fenetre.rafraichir(fenetre.partie.plateau);
				}	
				nbClick = 1;
			}
			
		}
		//Un clique droit reinitialise la selection des billes
		else if(event.getButton() == MouseEvent.BUTTON3 && event.getModifiersEx() == 0){	            	
 			nbClick = 1;
			fenetre.rafraichir(fenetre.partie.plateau);
		}//Supprimer bille
		else if ((event.getModifiersEx())  == 128 && event.getButton() == MouseEvent.BUTTON1) {
					//On efface la bille du plateau
		       fenetre.partie.plateau.supprimerBille(numCaseSelectionner);
					//On gere les modifications
					modifications();
							
		}//Ajouter bille noire
		else if ((event.getModifiersEx())  == 512 && event.getButton() == MouseEvent.BUTTON1) {
		        //On remplace la case selectionnée par une bille noire
			       fenetre.partie.plateau.cases[numCaseSelectionner].setContenu(Case.NOIR);
						//On gere les modifications
						modifications();
		}//Ajouter bille blanche
		else if ((event.getModifiersEx())  == 512 && event.getButton() == MouseEvent.BUTTON3) {
		        //On remplace la case selectionnée par une bille noire
			       fenetre.partie.plateau.cases[numCaseSelectionner].setContenu(Case.BLANC);
					  //On gere les modifications
						modifications();
		}//Ajouter marquage
		else if ((event.getModifiersEx())  == 640 && event.getButton() == MouseEvent.BUTTON1) {
		        System.out.println("marquer");
		}
		
	}
	
	
	/**
	*Cette fonction permet de passer des coordonnees graphiques ( sur le panel) et de recuperer le numero de case correspondant
	* @param xx L'abcisse du clique.
	* @param yy L'ordonne du clique.
	* @return Le numero de la case correspondant a l'endroit du clique.
	*/
	public byte transcription(int xx,int yy){
		int caseSelected =0;
		x = xx;
		y = yy;
		
		x = (x-(x%Panneau.TAILLEIM))/Panneau.TAILLEIM;
		
		if(x%2 == 1){
			if(y%Panneau.TAILLEIM > Panneau.TAILLEIM/2)
				y = ((y-(y%Panneau.TAILLEIM))/Panneau.TAILLEIM)+1;
			else
				y = (y-(y%Panneau.TAILLEIM))/Panneau.TAILLEIM;
		}
		else{
			y = (y-(y%Panneau.TAILLEIM))/Panneau.TAILLEIM;
		}
		
		if(x==0) caseSelected = y - 1;
		else if(x==1) caseSelected = y + 6 - 2;
		else if(x==2) caseSelected = y + 12 - 1; 
		else if(x==3) caseSelected = y + 19 - 1;
		else if(x==4) caseSelected = y + 27;
		else if(x==5) caseSelected = y + 36 - 1;
		else if(x==6) caseSelected = y + 44 - 1; 
		else if(x==7) caseSelected = y + 51 - 2;
		else if(x==8) caseSelected = y + 57 - 2; 
			
		return (byte)caseSelected;		
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
		is_valid = m.valider(fenetre.partie.plateau);
		//Si c'est valide on l'effectue
		if(is_valid){
			//On effectue le mouvement
			fenetre.partie.plateau.effectuer(m);
			//On affiche en console
			fenetre.partie.plateau.afficher();
			//On modifie l'etat de la partie
				//On change le joueur courant
				fenetre.partie.plateau.setJoueur();
				
				//On incremente le nombre de coups
				fenetre.partie.plateau.setNumCoup();
				//On verifie si il faut incrementer le score de la partie
				fenetre.partie.plateau.setScore();
				//Il ne s'agit pas d'une edition
				fenetre.partie.plateau.setEdited(false);
			
			//On ajoute le nouveau plateau comme fils
			fenetre.partie.changementPlateau();
			
		}
		
		//On rafraichie graphiquement
		fenetre.expandAll(fenetre.arbre);
		fenetre.rafraichir(fenetre.partie.plateau);
		
		gagnant = fenetre.partie.vainqueur();
		if(gagnant != 0){
			JOptionPane jop = new JOptionPane();
			String message = "La partie se termine par K.O.\n";
			if(gagnant == Partie.NOIR)
				message+="Le joueur NOIR remporte la partie";
			else
				message+="Le joueur BLANC remporte la partie";
			
			jop.showMessageDialog(null, message); 	
			Partie p = new Partie();
			fenetre.dispose();	
		}
		
	}
	
	public void modifications(){
		//Si il ne s'agissait pas d'un plateau résultant d'une édition alors on creer un noeud frere qui est une edition
		if(!fenetre.partie.plateau.isEdition()){
			//On met à jour le noeud modifié
			fenetre.partie.changementPlateau();
			//On indique que le plateau est le résultat d'une édition
			fenetre.partie.plateau.setEdited(true);
		}//Sinon on rafraichit juste le noeud courant
		else{
			//On met à jour le noeud modifié
			fenetre.partie.dernierCoup.setUserObject(new Codage(fenetre.partie.plateau));
		}
		//On rafraichit graphiquement
		fenetre.expandAll(fenetre.arbre);
		fenetre.rafraichir(fenetre.partie.plateau);
	}
	
	

}
