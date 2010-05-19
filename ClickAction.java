import java.awt.event.*;
	/**Cette classe permet de gerer les clicksouris sur le plateau */
public class ClickAction extends MouseAdapter {
	/** compteur de click*/
	private int nbClick;
	/** La fenetre de jeu sur lequel s'applique le click action*/
	private FenetreJeu fenetre; 
  /** Les billes selectionner lors de clique*/
	private int premiere,deuxieme,vecteur,intermediaire;
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
	*/
	public void mouseClicked(MouseEvent event){
		if(nbClick == 1){
			premiere = transcription(event.getY(),event.getX());
			if(fenetre.partie.plateau.chercheBilles(fenetre.partie.plateau.getJoueurActuel()).contains((byte)premiere)){
				System.out.println("premiere-clicked: " + premiere);
				fenetre.rafraichirBS1(premiere);
				nbClick = 2;
			}
			else{
				System.out.println("Attention, ne cliquez que sur vos billes ! \n");
			}	
		}
		else if(nbClick == 2){
			yb = event.getY();
			xb = event.getX();
			deuxieme = transcription(event.getY(),event.getX());
			//On verifie que le joueur ne selectionne pas les billes adverses
			if(fenetre.partie.plateau.chercheBilles(fenetre.partie.plateau.getJoueurActuel()).contains((byte)deuxieme)){
				System.out.println("deuxieme-clicked: " + deuxieme);
				//on recupere la bille intermediaire aux 2 billes selectionnees
				intermediaire = fenetre.partie.plateau.caseIntermediaire((byte)premiere, (byte)deuxieme);
				fenetre.rafraichirBS2(deuxieme,intermediaire);
				nbClick = 3;
			}
			else{
				System.out.println("Attention, ne cliquez que sur vos billes ! \n");
			}	
		}
		else if(nbClick == 3){
			xv = event.getX();
			yv = event.getY();
			vecteur = transcription(event.getY(),event.getX());
			
			x = (xb - (xb % Panneau.TAILLEIM));
			y = (yb - (yb % Panneau.TAILLEIM));
			
			//Le plateau d'abalone a des lignes decales, ils faut donc verifier les coordonnees pour tester le vecteur avec les bonnes donnees 
			if((y/Panneau.TAILLEIM)%2 == 1){
				//dans un soucis de precision, on prend comme coordonnee le centre de la bille
				if(xb % Panneau.TAILLEIM <= Panneau.TAILLEIM/2)
					xb = x;
				else
					xb = x + Panneau.TAILLEIM;
			}
			else{
				//et donc, le decalage a effectuer est different pour une ligne pair ou impair
				xb = x + Panneau.TAILLEIM/2;
			}
			yb = y + Panneau.TAILLEIM/2;	
				
			//En comparant la position du 2eme et 3eme clique, on peut savoir le numero du vecteur engendre
			if(vecteur == deuxieme - 1) vecteur = 4; //deplacement a gauche
			else if(vecteur == deuxieme + 1) vecteur = 1; //deplacement a droite
			else if(vecteur < deuxieme && xv > xb) vecteur = 0; //deplacement haut-droite 
			else if(vecteur < deuxieme && xv < xb) vecteur = 5; //deplacement haut-gauche
			else if(vecteur > deuxieme && xv > xb) vecteur = 2; //deplacement bas-droite
			else if(vecteur > deuxieme && xv < xb) vecteur = 3; //deplacement bas-gauche
			
			
			System.out.println("vecteur-clicked: " + vecteur);
			
			System.out.println("xb: " + xb);
			System.out.println("xv: " + xv);
			System.out.println("yb: " + yb);
			System.out.println("yv: " + yv);
			
			//Le mouvement est effectuee seulement si la position et le numero du vecteur est valide
			int difX = xv - xb;
			int difY = yv - yb;
			
			if(difX <= 0) difX = - difX;
			if(difY <= 0) difY = - difY;
			

			if(difX > Panneau.TAILLEIM * 1.9 || difY > Panneau.TAILLEIM * 1.9){
				System.out.println("\nMouvement invalide, ne cliquez pas trop loin de vos billes !\n");
				fenetre.rafraichir(fenetre.partie.plateau);
			}
			else if(vecteur<=5){
				deroulementMouvement(premiere,deuxieme,vecteur);
			}
			else{
				System.out.println("Mouvement invalide !\n");
				fenetre.rafraichir(fenetre.partie.plateau);

			}	
			nbClick = 1;
		}
		
		//Un clique droit reinitialise la selection des billes
		if(event.getButton() == MouseEvent.BUTTON3)	
		{	            	
 			nbClick = 1;
			fenetre.rafraichir(fenetre.partie.plateau);
			System.out.println("Mouvement reinitialise !");
		}
		
	}
	
	
	/**
	*Cette fonction permet de passer des coordonnees graphiques ( sur le panel) et de recuperer le numero de case correspondant
	* @param xx L'abcisse du clique.
	* @param yy L'ordonne du clique.
	* @return Le numero de la case correspondant a l'endroit du clique.
	*/
	public int transcription(int xx,int yy){
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
			
		return caseSelected;		
	}
	
	/** Gere le deroulement d'un mouvement a� partir des coordonnees envoyees par l'interface
	*
	* @param premiere numero de case de la premiere bille
	* @param deuxieme numero de case de a deuxieme bille
	* @param vecteur sens du deplacement
	*/

	public void deroulementMouvement(int premiere, int deuxieme, int vecteur){
		boolean is_valid;
		
		Mouvement m = new Mouvement((byte)premiere, (byte)deuxieme, (byte)vecteur);
		
		// On verifie le mouvement 
		is_valid = m.valider(fenetre.partie.plateau);
		//Si c'est valide on l'effectue
		if(is_valid){
			System.out.println("Mouvement Valide");
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
			System.out.println(fenetre.partie.plateau);
		}else{
			System.out.println("Mouvement Ivalide");
		}
		
		//On rafraichie graphiquement
		fenetre.rafraichir(fenetre.partie.plateau);
		
		
	}
	

}
