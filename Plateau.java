/** 
*	Gerer le plateau de jeu. 
*
* @attr plateau Plateau de jeu avec les positions des pions
*/

class Plateau{
	//Le plateau de jeu
	protected int plateau[][];
	     /*
						plateau[x][0] = BLANC ou NOIR ou VIDE
						Les cases adjacente a la cases courantes : 
						plateau[x][1] = numero DH
						plateau[x][2] = numero GB
						plateau[x][3] = numero DD
						plateau[x][4] = numero GG
						plateau[x][5] = numero DB
						plateau[x][6] = numero GH
						
						plateau[0][X] = Hors du plateau (pour une bille capturée)
				*/
	// Que contient la case?
	public static final int ETAT = 0;
	public static final int BLANC = 1;
	public static final int NOIR = 2;
	public static final int VIDE = 0;
	// En dehors du plateau de jeu
	public static final int TROU = 0;
	// Liste des directions possibles
	public static final int DH = 1; // Droite Haut
 	public static final int GB = 2; // Gauche Bas
	public static final int DD = 3; // Droite 
	public static final int GG = 4; // Gauche 
	public static final int DB = 5; // Droite Bas
	public static final int GH = 6; // Gauche Haut

	/**
	* Constructeur pour une nouvelle partie
	*
	*/
	public Plateau(){
		initialiser();
	}
	
	/**
	* Créeer le plateau de jeu avec les positions de départ
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
	* Effectuer un déplacement. 
	* Les vérifications pour savoir si un coup est reglementaire ne sont pas effectuées dans cette fonction. 
	* C'est ici qu'on determine si une boule est tombée.
	*
	* @param direction La direction du coups joué
	* @param bille La première bille déplacé
	*/
	public void deplacer(int direction, int bille){
		int caseActuel = bille;
		int caseSuivante;
		int contenuActuel = plateau[bille][ETAT];
		int contenuSuivant;
		
		/*On pousse toute la ligne d'une case*/
		caseSuivante = plateau[caseActuel][direction]; //Il y a forcément au moins une case suivante sinon il s'agirai d'un suicide
		contenuSuivant = plateau[caseSuivante][ETAT];
		plateau[bille][ETAT] = VIDE;
		
		while(contenuActuel != VIDE && caseActuel != TROU){			
			plateau[caseSuivante][ETAT] = contenuActuel;
			
			caseActuel = caseSuivante;
			caseSuivante = plateau[caseActuel][direction];
			contenuActuel = contenuSuivant;
			contenuSuivant = plateau[caseSuivante][ETAT];
			
		}
	}
	
	/**
	* Afficher le plateau de jeu à la console
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
	* Ajoute à chaque case du plateau ces 6 cases adjacentes.
	* Cela sera pratique pour savoir les déplacements possibles ainsi que pour effectuer ces déplacements.
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