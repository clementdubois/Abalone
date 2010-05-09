import java.awt.event.*;
	//Cette classe permet de gerer les clicksouris sur le plateau 
public class ClickAction extends MouseAdapter {
	//Cette variable permet d'acceder a la fonction jouer de la classe joueur
	private Joueur joueur;
	//variable de fin de partie
	private boolean finPartie;
	/** compteur de click*/
	private int nbClick;
	/** Le plateau de jeu*/
	private Plateau plateau;
	/** La partie en cour*/
	private Partie partie;

	private int premiere,deuxieme,vecteur;
	
	/** On evoie la partie associé a la fenetre pour pouvoir la modifier*/
	public ClickAction(Partie p){
		//Initialisation des variables
		this.joueur=joueur;
		nbClick = 0;
		this.plateau = p.plateau;
		this.partie = p;
	}
	
	/**
	*Cette méthode envoie en parametre les coordonées du plateau afin de determiner le numero de la case selectionnee
	*/

	
	public void mouseRelease(MouseEvent event){
		if(nbClick == 2){
			deuxieme = transcription(event.getY(),event.getX());
			nbClick = 3;
		}
		
	}
	
	public void mousePressed(MouseEvent event){
		if(nbClick == 1){
			premiere = transcription(event.getY(),event.getX());
			nbClick = 2;
		}
		if(nbClick == 2){
			deuxieme = transcription(event.getY(),event.getX());
			nbClick = 3;
		}
		if(nbClick == 3){
			vecteur = transcription(event.getY(),event.getX());
			//ici il faut determiner le vecteur, envoyer le mouvement et reinitialiser nbClick
		}
		
	}
	
	/**
	*Cette fonction permet de passer des coordonnees graphiques ( sur le panel) en coordonees de tableau
	*/
	public int transcription(int xx,int yy){
		int caseSelected =0;
		int x = xx;
		int y = yy;
		
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
		
		if(x==0) caseSelected = y - 2;
		if(x==1) caseSelected = y + 5 - 2;
		if(x==2) caseSelected = y + 11 - 1; 
		if(x==3) caseSelected = y + 18 - 1;
		if(x==4) caseSelected = y + 26;
		if(x==5) caseSelected = y + 35 - 1;
		if(x==6) caseSelected = y + 43 - 1; 
		if(x==7) caseSelected = y + 50 - 2;
		if(x==8) caseSelected = y + 56 - 2; 
			
		return caseSelected;		
	}
	
	/** Gère le déroulement d'un mouvement à partir des coordonnées envoyé par l'interface
	*
	* @param premiere numero de case de la premiere bille
	* @param deuxieme numero de case de a deuxieme bille
	* @param vecteur sens du déplacement
	*/
	public void deroulementMouvement(int premiere, int deuxieme, int vecteur){
		boolean is_valid;
		
		Mouvement m = new Mouvement((byte)premiere, (byte)deuxieme, (byte)vecteur);
		
		// On vérifie le mouvement 
		is_valid = m.valider(plateau);
		//Si c'est valide on l'effectue
		if(is_valid){
			System.out.println("Listener mouv valide");
			//On effectue le mouvement
			plateau.effectuer(m);
			//On modifie l'état de la partie
				//On change le joueur courant
				partie.setJoueur();
				//On incremente le nombre de coups
				partie.setNumCoup();
				//On vérifie si il faut incrementer le score de la partie
				partie.setScore();
			//On affiche en console
			plateau.afficher();
			System.out.println(partie);
			//On rafraichie graphiquement
			partie.f.rafraichir(plateau);
		}else{
			System.out.println("Listener mouv invalide");
		}
		
	}
	
	
	/** 
	*Retourne le numero de la case selectionnee a la souris
	*/
	// public int getCaseSelected(){
	// 	return caseSelected;
	// }

}
