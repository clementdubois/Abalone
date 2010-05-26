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
					 			 if(!fenetre.partie.estHumain) {
					 					Mouvement m = IA.jouer(this.fenetre.partie.plateau, 1, 1);
					 			 		deroulementMouvement(m.getPremiere(), m.getDerniere, m.getVecteur());
					 					return;
					 			 }
/*			
	fin ia
*/	
	
		//Les cliques sont actifs seulement si ce n'est pas à l'IA de jouer
		if(fenetre.partie.estHumain()){
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
						fenetre.partie.deroulementMouvement(premiere,deuxieme,vecteur);
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
