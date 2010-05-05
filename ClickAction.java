import java.awt.event.*;
	//Cette classe permet de gerer les clicksouris sur le plateau 
public class ClickAction extends MouseAdapter {
	//Cette variable permet d'acceder a la fonction jouer de la classe joueur
	private Joueur joueur;
	//variable de fin de partie
	private boolean finPartie;
	/** compteur de click*/
	private int nbClick;
	
	public ClickAction(){
		//Initialisation des variables
		this.joueur=joueur;
		nbClick = 0;
	}
	
	/**
	*Cette méthode envoie en parametre les coordonées du plateau afin de determiner le numero de la case selectionnee
	*/
	public void mousePressed(MouseEvent event){
		int premiere,deuxieme,vecteur;
		
		transcription(event.getY(),event.getX());
		nbClick++;
	}
	
	/**
	*Cette fonction permet de passer des coordonnees graphiques ( sur le panel) en coordonees de tableau
	*/
	public int transcription(int x,int y){
		int caseSelected ;
		
		x = (x-(x%Panneau.TAILLEIM))/Panneau.TAILLEIM;
		
		if(x%2 = 1){
			if(y%Panneau.TAILLEIM > Panneau.TAILLEIM/2)
				y = ((y-(y%Panneau.TAILLEIM))/Panneau.TAILLEIM)+1;
			else
				y = (y-(y%Panneau.TAILLEIM))/Panneau.TAILLEIM;
		}
		else{
			y = (y-(y%Panneau.TAILLEIM))/Panneau.TAILLEIM;
		}
		
		if(x==0) caseSelected = y - 2;
		if(x==1) caseSelected = y + 5 - 2;
		if(x==2) caseSelected = y + 11 - 1; 
		if(x==3) caseSelected = y + 18 - 1;
		if(x==4) caseSelected = y + 26;
		if(x==5) caseSelected = y + 35 - 1;
		if(x==6) caseSelected = y + 43 - 1; 
		if(x==7) caseSelected = y + 50 - 2;
		if(x==8) caseSelected = y + 56 - 2; 
					
	}
	
	
	/** 
	*Retourne le numero de la case selectionnee a la souris
	*/
	public int getCaseSelected(){
		return caseSelected
	}
	
}
