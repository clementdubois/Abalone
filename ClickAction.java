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

	private int premiere,deuxieme,vecteur,balise,intermediaire;
	private int yb,yv,xb,xv;
	
	/** On evoie la partie associé a la fenetre pour pouvoir la modifier*/
	public ClickAction(Partie p){
		//Initialisation des variables
		this.joueur=joueur;
		nbClick = 1;
		this.plateau = p.plateau;
		this.partie = p;
	}
	
	
	// public void mousePressed(MouseEvent event){
		// if(nbClick == 1){
		// 	premiere = transcription(event.getY(),event.getX());
		// 	System.out.println("premiere-pressed" + premiere);
		// 	nbClick = 2;
		// }
		// else if(nbClick == 2){
		// 	deuxieme = transcription(event.getY(),event.getX());
		// 	System.out.println("deuxieme-pressed" + deuxieme);
		// 	balise = deuxieme;
		// 	System.out.println("balise-pressed" + balise);
		// 	nbClick = 3;
		// }
		// else if(nbClick == 3){
		// 	balise = transcription(event.getY(),event.getX());
		// 	System.out.println("balise-pressed" + deuxieme);
		// }
		
	// }
	// 
	// public void mouseRelease(MouseEvent event){
	// 	if(nbClick == 2){
	// 		deuxieme = transcription(event.getY(),event.getX());
	// 		System.out.println("deuxieme-release" + deuxieme);
	// 		nbClick = 3;
	// 	}
	// 	else if(nbClick == 3){
	// 		yy = event.getY();
	// 		vecteur = transcription(event.getY(),event.getX());
	// 		
	// 		if(vecteur == balise - 1) vecteur = 4; //deplacement a gauche
	// 		else if(vecteur == balise + 1) vecteur = 1; //deplacement a droite
	// 		else if(vecteur < balise && yy%Panneau.TAILLEIM > Panneau.TAILLEIM/2) vecteur = 0; //deplacement haut-droite 
	// 		else if(vecteur < balise && yy%Panneau.TAILLEIM < Panneau.TAILLEIM/2) vecteur = 5; //deplacement haut-gauche
	// 		else if(vecteur > balise && yy%Panneau.TAILLEIM > Panneau.TAILLEIM/2) vecteur = 2; //deplacement bas-droite
	// 		else if(vecteur > balise && yy%Panneau.TAILLEIM < Panneau.TAILLEIM/2) vecteur = 3; //deplacement bas-gauche
	// 		
	// 		System.out.println("vecteur-release" + vecteur);
	// 		
	// 		deroulementMouvement(premiere,deuxieme,vecteur);
	// 		
	// 		nbClick = 1;
	// 	}
	// 	
	// }
	
	public void mouseClicked(MouseEvent event){
		if(nbClick == 1){
			premiere = transcription(event.getY(),event.getX());
			System.out.println("premiere-clicked: " + premiere);
			partie.f.rafraichirBS1(premiere);
			nbClick = 2;
		}
		else if(nbClick == 2){
			deuxieme = transcription(event.getY(),event.getX());
			System.out.println("deuxieme-clicked: " + deuxieme);
			intermediaire = this.plateau.caseIntermediaire((byte)premiere, (byte)deuxieme);
			partie.f.rafraichirBS2(deuxieme,intermediaire);
			nbClick = 3;
		}
		else if(nbClick == 3){
			yb = event.getY();
			xb = event.getX();
			balise = transcription(event.getY(),event.getX());
			System.out.println("balise-clicked: " + balise);
			nbClick = 4;
		}
		else if(nbClick == 4){
			xv = event.getX();
			xv = (xv - (xv%Panneau.TAILLEIM)) + Panneau.TAILLEIM/2;
			yv = event.getY();
			yv = (yv - (yv%Panneau.TAILLEIM)) + Panneau.TAILLEIM/2;
			vecteur = transcription(event.getY(),event.getX());
			
			if(vecteur == balise - 1) vecteur = 4; //deplacement a gauche
			else if(vecteur == balise + 1) vecteur = 1; //deplacement a droite
			else if(vecteur < balise && xv > xb) vecteur = 0; //deplacement haut-droite 
			else if(vecteur < balise && xv < xb) vecteur = 5; //deplacement haut-gauche
			else if(vecteur > balise && xv > xb) vecteur = 2; //deplacement bas-droite
			else if(vecteur > balise && xv < xb) vecteur = 3; //deplacement bas-gauche
			
			
			System.out.println("vecteur-clicked: " + vecteur);
			
			System.out.println("xb: " + xb);
			System.out.println("xv: " + xv);
			System.out.println("yb: " + yb);
			System.out.println("yv: " + yv);
			if(xv - xb > Panneau.TAILLEIM * 1.9 || yv - yb > Panneau.TAILLEIM * 1.9){
				System.out.println("Mouvement invalide !\n");
				partie.f.rafraichir(plateau);
			}
			else if(vecteur<=5){
				deroulementMouvement(premiere,deuxieme,vecteur);
			}
			else{
				partie.f.rafraichir(plateau);
			}	
			System.out.println("nbClick avant: " + nbClick);
			nbClick = 1;
			System.out.println("nbClick apres: " + nbClick);
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
		
		if(x==0) caseSelected = y - 1;
		else if(x==1) caseSelected = y + 6 - 2;
		else if(x==2) caseSelected = y + 12 - 1; 
		else if(x==3) caseSelected = y + 19 - 1;
		else if(x==4) caseSelected = y + 27;
		else if(x==5) caseSelected = y + 36 - 1;
		else if(x==6) caseSelected = y + 44 - 1; 
		else if(x==7) caseSelected = y + 51 - 2;
		else if(x==8) caseSelected = y + 57 - 2; 
			
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
		}else{
			System.out.println("Listener mouv invalide");
		}
		
		partie.f.rafraichir(plateau);
		
		
	}
	

}
