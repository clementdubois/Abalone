

/** 
*	Gerer le plateau de jeu. 
* Cette classe permet de mofifier le plateau de jeu en l'initialisant ou en faisant un deplacement.<br />
* Les deplacements sont geres de la même façon qu'une humain jouerai physiquement :
* <ul>
* 	<li>quand on fait une poussee on ne touche qu'une bille dans une direction, le mouvement se fera donc en indiquant seulement la direction et la première bille.</li>
* 	<li>quand on fait un mouvement lateral il faut prendre toutes (deux ou trois) les billes, le mouvement se fera donc en indiquant la direction ainsi que toutes les billes deplacees.</li>
* </ul>
*/
public class Plateau{	
	/** 
	* Plateau de jeu. 
	* Ce plateau est defini comme plateau[62][8] :<br />
	*   <ul>
	*    <li>plateau[0..61] => Les differentes case du plateau (0 => hors du plateau)</li>
	*    <li>plateau[TROU][X] = Hors du plateau (pour une bille capturee)</li>
	*    <li>plateau[x][ETAT] = BLANC ou NOIR ou VIDE</li>
	*   </ul>
	* Les cases adjacente a la cases courantes (utile pour la direction d'un mouvement) : 
	*   <ul>
	*    	<li>plateau[x][DH] = numero DH</li>
	*    	<li>plateau[x][GB] = numero GB</li>
	*   	<li>plateau[x][DD] = numero DD</li>
	*   	<li>plateau[x][GG] = numero GG</li>
	*    	<li>plateau[x][DB] = numero DB</li>
	*   	<li>plateau[x][GH] = numero GH</li>
	*   </ul>
	*/
	protected int plateau[][];
	    
	/** 
	* Pour connaitre l'etat d'une case (BLANC, NOIR ou VIDE).<br />
	* ex : plateau[12][0] = NOIR   => la 12eme bille du plateau est une bille noir
	*/
	public static final int ETAT = 0;
	/** Une bille blanche.*/
	public static final int BLANC = 1;
	/** Une bille noir.*/
	public static final int NOIR = 2;
	/** Une case vide.*/
	public static final int VIDE = 0;
	/** En dehors du plateau.
	* Utile pour determiner si une bille a ete sortie du plateau
	*/
	public static final int TROU = 0;
	/** Direction droite haut.*/
	public static final int DH = 1; 
	/** Direction gauche bas.*/
 	public static final int GB = 2; 
	/** Direction droite.*/
	public static final int DD = 3; 
	/** Direction gauche.*/
	public static final int GG = 4; 
	/** Direction droite bas.*/
	public static final int DB = 5; 
	/** Direction gauche haut.*/
	public static final int GH = 6; 
	/** Indique un mouvement autorise */
	public static final int VALIDE = 1;
	/** Indique un mouvement interdit */
	public static final int INVALIDE = 0;

	/**
	* Constructeur pour une nouvelle partie
	*
	*/
	public Plateau(){
		initialiser();
	}
	
	/**
	* Creeer le plateau de jeu avec les positions de depart
	*
	*/
	public void initialiser(){
		plateau = new int[62][8];
		
		//On place les 14 pions de chaques couleurs sur le plateau et on indique les cases vides
		for (int i = 1; i <= 61; i++){
       if (i < 17 && i != 12 && i != 13)
          plateau[i][ETAT] = BLANC;
       else if (i > 45 && i != 50 && i != 49)
          plateau[i][ETAT] = NOIR;
       else
          plateau[i][ETAT] = VIDE;
		}
		
		//Pour chaque pion on enregistre ces points adjacents
		caseAdjacente();
	}
	
	/**
	* Effectuer un deplacement. <br />
	* C'est ici qu'on determine si une boule est tombee.<br />
	* Les verifications pour savoir si un coup est reglementaire ne sont pas effectuees dans cette fonction. <br />
	* S'il s'agit d'une poussee, il faut simplement indiquer la première bille car les autres seront poussees en même temps (comme en jeu reel);
	* par contre s'il s'agit d'un mouvement lateral il est necessaire de connaitre les billes 2 et 3 si deux ou trois billes sont deplacees.
	*
	* @param direction La direction du coups joue.
	* @param bille La premiere bille deplacee.
	* @param bille2 La deuxieme bille deplacee.
	* @param bille3 La troisieme bille deplacee.
	*/
	public void deplacer(int direction, int bille, int bille2, int bille3){
		int caseActuel = bille;
		int caseSuivante;
		int contenuActuel = plateau[bille][ETAT];
		int contenuSuivant;
		
		// Si on ne pousse qu'une bille ou si la deuxieme bille est dans la direction du déplacement c'est qu'il s'agit d'une poussée 
		if((bille2 == -1) || (plateau[bille][direction] == bille2)){
			System.out.println("Poussee deplacement");
			/*On POUSSE toute la ligne d'une case*/
			caseSuivante = plateau[caseActuel][direction]; //Il y a forcement au moins une case suivante sinon il s'agirai d'un suicide
			contenuSuivant = plateau[caseSuivante][ETAT];
			plateau[bille][ETAT] = VIDE;
		
			while(contenuActuel != VIDE && caseActuel != TROU){			
				plateau[caseSuivante][ETAT] = contenuActuel;
			
				caseActuel = caseSuivante;
				caseSuivante = plateau[caseActuel][direction];
				contenuActuel = contenuSuivant;
				contenuSuivant = plateau[caseSuivante][ETAT];
			}
		}//Sinon, il ya a au moins deux billes et la direction du mouvement est différent de l'alignement des bille, c'est un mouvement lateral
		else{
			System.out.println("PAS poussee deplacement");
			
			/* on prend la bille 1 et on la deplace sur la case vide,
			 on fait pareil avec la deuxième et la troisième si elle existe.
			 Les billes deplacées sont forcément de la même couleur, il est donc inutile de redefinir le contenu pour chaque bille
			 Les cases destinations sont forcément vide.*/
			
			plateau[bille][0] = VIDE;
		 	plateau[bille2][0] = VIDE;
			plateau[plateau[bille][direction]][ETAT] = contenuActuel;
			plateau[plateau[bille2][direction]][ETAT] = contenuActuel;
			if (bille3 != -1){
				plateau[bille3][0] = VIDE;
				plateau[plateau[bille3][direction]][ETAT] = contenuActuel;
			}
		}
		
	}
	
	/**
	* Verifie qu'on mouvement est valide.
	*
	* @param direction La direction du coups joue.
	* @param bille La premiere bille deplacee.
	* @param bille2 La deuxieme bille deplacee.
	* @param bille3 La troisieme bille deplacee.
	* @return VALIDE si le mouvement est autorise, INVALIDE si le mouvement est interdit
	*/
	public int validerMouvement(int direction, int bille, int bille2, int bille3){
		int derniereBille = bille;
		int contenuBille = plateau[bille][ETAT];
		short cptBilleMoi = 1; //Compteur du nombre de bille que je déplace
		short cptBilleLui = 0; //Compteur du nimbre de bille adverse qui vont être déplacées
		
		
		
		//Si c'est une poussee (si on ne touche qu'une bille)
		if(bille2 == -1){
			//On regarde la dernière de nos billes poussee
			System.out.println("BILLE : "+direction+" "+derniereBille+" "+plateau[derniereBille][direction]);
			while(plateau[plateau[derniereBille][direction]][ETAT] == contenuBille){
				cptBilleMoi++;
				derniereBille = plateau[derniereBille][direction];
			}
			
			System.out.println("Une poussee");
			
			//On teste la case juste après nos billes
			if(plateau[derniereBille][direction] == TROU){//Si suivante trou PAS OK
				System.out.println("Trou");
				return INVALIDE;
			}
			else if(cptBilleMoi > 3){//Plus de trois bille poussees
				System.out.println("Trop de bille poussee");
				return INVALIDE;
			}
			else if (plateau[plateau[derniereBille][direction]][ETAT] == VIDE){//Si suivante vide OK
				System.out.println("VIDE : "+derniereBille+" "+cptBilleMoi);
				return VALIDE;
			}
			else{ // Si on fait face à une bille du camp adverse
				//On calcule le nombre de billes adverse qui vont être déplacé
				while(plateau[plateau[derniereBille][direction]][ETAT] != contenuBille &&
				      plateau[plateau[derniereBille][direction]][ETAT] != VIDE &&
						  plateau[plateau[derniereBille][direction]][ETAT] != TROU){
					cptBilleLui++;
					derniereBille = plateau[derniereBille][direction];
				}
				
				if(cptBilleMoi <= cptBilleLui){// Si les billes adverses sont plus nombreuses ou égal, le mouvement est impossible
					System.out.println("Inferiorité numérique");
					return INVALIDE;
				}
				else{
					System.out.print("Superiorité numérique ");
					//Si une de nos bille est juste derriere, elle bloque le mouvement
					if(plateau[plateau[derniereBille][direction]][ETAT] == contenuBille){
						System.out.println("mais une bille bloque le passage");
						return INVALIDE;
					}
					else
						return VALIDE;
				}
	
			}
			
		}else{//Déplacement lateral
			//Il suffit de vérifier que toutes les cases visées sont vides
			System.out.println("Pas une poussee");
			if (plateau[plateau[bille][direction]][ETAT] != VIDE)
				return INVALIDE;
			else if (plateau[plateau[bille2][direction]][ETAT] != VIDE)
				return INVALIDE;
			else if (bille3 != -1 && plateau[plateau[bille3][direction]][ETAT] != VIDE)
				return INVALIDE;
			else
				return VALIDE;
		}
		
	}
	
	/**
	* Afficher le plateau de jeu a la console
	*
	*/
	public void afficher(){
		int numCase = 1;
		
		for (int i = 0; i < 9; i++){
			if(i==0 || i==8)
				System.out.print("      ");
			else if(i==1 || i==7)
				System.out.print("      ");
			else if(i==2 || i==6)
				System.out.print("  ");
			else if(i==3 || i==5)
				System.out.print("  ");
				
			if(i%2 == 0 && i!= 4)
				System.out.print("  ");
			for(int j=0; (j< (5+i) && i<5) || (j < (13-i) && i>=5 ) ; j++ ){
				
				if (plateau[numCase][0] == NOIR)
					System.out.print("N   ");
				else if (plateau[numCase][0] == BLANC)
					System.out.print("B   ");
				else
					System.out.print("V   ");
				
				numCase++;
			}	
			
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
		
	}
	
	/**
	* Ajoute a chaque case du plateau ces 6 cases adjacentes.<br />
	* Cela sera pratique pour savoir les deplacements possibles ainsi que pour effectuer ces deplacements.
	*/
	protected void caseAdjacente(){
		int i;
	   for (i = 1; i <= 61; i++) {
			
				// Case Haut Droite
	      if ((i >= 6 && i <= 10) || (i >= 57 && i <= 61))
	         plateau[i][DH] = i-5;
	      else if ((i >= 12 && i <= 17) || (i >= 51 && i <= 56))
	         plateau[i][DH] = i-6;
	      else if ((i >= 19 && i <= 25) || (i >= 44 && i <= 50))
	         plateau[i][DH] = i-7;
	      else if ((i >= 27 && i <= 34) || (i >= 36 && i <= 43))
	         plateau[i][DH] = i-8;
	      else
	         plateau[i][DH] = TROU;
	
				// Case Bas Gauche
	      if ((i >= 1 && i <= 5) || (i >= 52 && i <= 56))
	         plateau[i][GB] = i+5;
	      else if ((i >= 6 && i <= 11) || (i >= 45 && i <= 50))
           plateau[i][GB] = i+6;
        else if ((i >= 12 && i <= 18) || (i >= 37 && i <= 43))
           plateau[i][GB] = i+7;
        else if ((i >= 19 && i <= 26) || (i >= 28 && i <= 35))
           plateau[i][GB] = i+8;
        else
           plateau[i][GB] = TROU;
				
				// Case Droite
	      if (i == 5 || i == 11 || i == 18 || i == 26 || i == 35
	          || i == 43 || i == 50 || i == 56 || i == 61)
	         plateau[i][DD] = TROU;
	      else
	         plateau[i][DD] = i+1;
	
				// Cases Gauche
	      if (i == 1 || i == 6 || i == 12 || i == 19 || i == 27
	          || i == 36 || i == 44 || i == 51 || i == 57)
	         plateau[i][GG] = TROU;
	      else
	         plateau[i][GG] = i-1;
				
				//Case Bas Droite
	      if ((i >= 1 && i <= 5) || (i >= 51 && i <= 55))
	         plateau[i][DB] = i+6;
	      else if ((i >= 6 && i <= 11) || (i >= 44 && i <= 49))
           plateau[i][DB] = i+7;
        else if ((i >= 12 && i <= 18) || (i >= 36 && i <= 42))
           plateau[i][DB] = i+8;
        else if ((i >= 19 && i <= 26) || (i >= 27 && i <= 34))
           plateau[i][DB] = i+9;
        else
           plateau[i][DB] = TROU;
	
				// Case Haut Gauche
	      if ((i >= 7 && i <= 11) || (i >= 57 && i <= 61))
	         plateau[i][GH] = i-6;
	      else if ((i >= 13 && i <= 18) || (i >= 51 && i <= 56))
           plateau[i][GH] = i-7;
        else if ((i >= 20 && i <= 26) || (i >= 44 && i <= 50))
           plateau[i][GH] = i-8;
        else if ((i >= 28 && i <= 35) || (i >= 36 && i <= 43))
           plateau[i][GH] = i-9;
        else
           plateau[i][GH] = TROU;
	   }
	}
	
}