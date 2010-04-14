

/** 
*	Gerer le plateau de jeu. 
* Cette classe sert à initialiser le plateau de jeu et a le modifier (via la methode setPlateau(int [][] p) );
* On ne peut modifier le plateau qu'a partir de cette methode; on peut recuperer le plateau via la methode int [][] getPlateau().
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
	/** Case adjacente droite haut.*/
	public static final int DH = 1; 
	/** Case adjacente gauche bas.*/
 	public static final int GB = 2; 
	/** Case adjacente droite.*/
	public static final int DD = 3; 
	/** Case adjacente gauche.*/
	public static final int GG = 4; 
	/** Case adjacente droite bas.*/
	public static final int DB = 5; 
	/** Case adjacente gauche haut.*/
	public static final int GH = 6; 

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
		System.out.println("Bille Ejecte lors du mouvement : "+this.plateau[Plateau.TROU][Plateau.ETAT]);
		
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
	/** Renvoi le plateau de jeu 
	*
	* @return plateau Le plateau de jeu de l'objet Plateau.
	*/
	public int[][] getPlateau(){
		return plateau;
	}
	/** Modifier Le plateau de jeu.
	* 
	* @param p Le plateau de jeu qui sera copie dans plateau
	*/
	public void setPlateau(int [][] p){
		plateau = p;
	}
	
	public int tomber(){
		if (this.plateau[Plateau.TROU][Plateau.ETAT] == Plateau.BLANC)
			return Plateau.BLANC;
		else if (this.plateau[Plateau.TROU][Plateau.ETAT] == Plateau.NOIR)
			return Plateau.NOIR;
		else
			return Plateau.VIDE;
	}
	
	
}