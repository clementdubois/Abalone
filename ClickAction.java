import java.awt.event.*;
	//Cette classe permet de gerer les clicksouris sur le plateau 
public class ClickAction extends MouseAdapter {
	/** compteur de click*/
	private int nbClick;
	/** Le plateau de jeu*/
	private Plateau plateau;
	/** La partie en cour*/
	private Partie partie;
  /** Les billes selectionner lors de clique*/
	private int premiere,deuxieme,vecteur,intermediaire;
	/** Les coordonnees des cliques*/
	private int yb,yv,xb,xv;
	
	/** On evoie la partie associee a la fenetre pour pouvoir la modifier 
	* @param p La partie sur laquel se deroule les actions.
	*/
	public ClickAction(Partie p){
		nbClick = 1;
		this.plateau = p.plateau;
		this.partie = p;
	}
	
	/**
	* On surcharge la methode mouseClicked pour quelle recupere et envoie le numero des billes selectionnees.
	*/
	public void mouseClicked(MouseEvent event){
		if(nbClick == 1){
			premiere = transcription(event.getY(),event.getX());
			if(plateau.chercheBilles(partie.getJoueurActuel()).contains((byte)premiere)){
				System.out.println("premiere-clicked: " + premiere);
				partie.f.rafraichirBS1(premiere);
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
			if(plateau.chercheBilles(partie.getJoueurActuel()).contains((byte)deuxieme)){
				System.out.println("deuxieme-clicked: " + deuxieme);
				//on recupere la bille intermediaire aux 2 billes selectionnees
				intermediaire = this.plateau.caseIntermediaire((byte)premiere, (byte)deuxieme);
				partie.f.rafraichirBS2(deuxieme,intermediaire);
				nbClick = 3;
			}
			else{
				System.out.println("Attention, ne cliquez que sur vos billes ! \n");
			}	
		}
		else if(nbClick == 3){
			xv = event.getX();
			xv = (xv - (xv % Panneau.TAILLEIM)) + Panneau.TAILLEIM/2;
			yv = event.getY();
			yv = (yv - (yv % Panneau.TAILLEIM)) + Panneau.TAILLEIM/2;
			vecteur = transcription(event.getY(),event.getX());
			
			//En comparant la position du 2eme et 3eme clique, on peut savoir le numero du vecteur engendre
			if(vecteur == deuxieme - 1) vecteur = 4; //deplacement a gauche
			else if(vecteur == deuxieme + 1) vecteur = 1; //deplacement a droite
			else if(vecteur < deuxieme && xv > xb) vecteur = 0; //deplacement haut-droite 
			else if(vecteur < deuxieme && xv < xb) vecteur = 5; //deplacement haut-gauche
			else if(vecteur > deuxieme && xv > xb) vecteur = 2; //deplacement bas-droite
			else if(vecteur > deuxieme && xv < xb) vecteur = 3; //deplacement bas-gauche
			
			
			System.out.println("vecteur-clicked: " + vecteur);
			
			// System.out.println("xb: " + xb);
			// System.out.println("xv: " + xv);
			// System.out.println("yb: " + yb);
			// System.out.println("yv: " + yv);
			
			//Le mouvement est effectuee seulement si la position et le numero du vecteur est valide
			int difX = xv - xb;
			int difY = yv - yb;
			
			if(difX <= 0) difX = - difX;
			if(difY <= 0) difY = - difY;
			
			if(difX > Panneau.TAILLEIM * 1.9 || difY > Panneau.TAILLEIM * 1.9){
				deroulementMouvement(premiere,deuxieme,vecteur); // ICI ON PEUT VOIR QUE LE MOUVEMENT SERA CORRECTEMENT EFFECTUE SI JE LE FORCE A LE JOUER (je n'ai pas encore regardé pourquoi...) !
				System.out.println(premiere+"-"+deuxieme+"\nMouvement invalide !\n");
				partie.f.rafraichir(plateau);
			}
			else if(vecteur<=5){
				deroulementMouvement(premiere,deuxieme,vecteur);
			}
			else{
				System.out.println(premiere+""+deuxieme+"\nMouvement invalide !\n");
				partie.f.rafraichir(plateau);
			}	
			nbClick = 1;
			System.out.println(premiere+"-"+deuxieme);
		}
		
		//Un clique droit reinitialise la selection des billes
		if(event.getButton() == MouseEvent.BUTTON3)	
		{	            	
 			nbClick = 1;
			partie.f.rafraichir(plateau);
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
	
	/** Gere le deroulement d'un mouvement a  partir des coordonnees envoyees par l'interface
	*
	* @param premiere numero de case de la premiere bille
	* @param deuxieme numero de case de a deuxieme bille
	* @param vecteur sens du deplacement
	*/

	public void deroulementMouvement(int premiere, int deuxieme, int vecteur){
		boolean is_valid;
		
		Mouvement m = new Mouvement((byte)premiere, (byte)deuxieme, (byte)vecteur);
		
		// On verifie le mouvement 
		is_valid = m.valider(plateau);
		//Si c'est valide on l'effectue
		if(is_valid){
			System.out.println("Mouvement Valide");
			//On effectue le mouvement
			plateau.effectuer(m);
			//On affiche en console
			plateau.afficher();
			//On modifie l'etat de la partie
				//On change le joueur courant
				partie.setJoueur();
				//On incremente le nombre de coups
				partie.setNumCoup();
				//On vÃ©rifie si il faut incrementer le score de la partie
				partie.setScore();
			System.out.println(partie);
		}else{
			System.out.println("Mouvement Ivalide");
		}
		
		//On rafraichie graphiquement
		partie.f.rafraichir(plateau);
		
		
	}
	

}
