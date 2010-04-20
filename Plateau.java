
public class Plateau {
	public Case[] cases; // dans Case on retrouvera les cases adjacentes
	/** la case trou (0) dans la liste des cases, la numero 0 est celle reservee au trou */
	public final static int TROU = 0;
	
	public Plateau() {
		initialiser();
		// ici on utilisera mon algorithme pour initialiser les valeurs des cases[] adjacentes : cf schema
	}

	/**  Initialiser le plateau de jeu en posant les billes de depart
	*
	*/
	
	public void initialiser(){
		 cases = new Case[62];

		//On place les 14 pions de chaques couleurs sur le plateau et on indique les cases vides
		for (int i = 1; i <= 61; i++){
		       if (i < 17 && i != 12 && i != 13)
		          cases[i].setBille(new Bille(Bille.BLANC));
		       else if (i > 45 && i != 50 && i != 49)
		          cases[i].setBille(new Bille(Bille.NOIR));
		       else
		          cases[i].setContient();//Met la case a Case.VIDE
		}

		//Pour chaque pion on enregistre ces points adjacents
		//caseAdjacente();
	}
	
	/** Rendre effectif un mouvement sur le plateau.
	* 
	* @param m le mouvement a effectuer
	* 
	 */
	public boolean joue(Mouvement m) {
		int i = 0;
		while(i < m.length) { // on veut appliquer le mouvement a chaque bille.
			cases[m[i].getCoordonneesArrivee()].contiensBille();
			cases[m[i].getCoordonneesArrivee()].setBille(cases[m[i].getCoordonnesDepart()].getBille());
			cases[m[i].getCoordonnesDepart()].contiensVide();
		}
// inutile : le traitement juste au-dessus gere tous les mouvements.
// this.mettreAJour(); // permet de gerer n'importe quel type de deplacement : bille.appliquerCoordonnees() change le champ position de la bille et mettreAJour() va chercher les positions de chaque bille pour se mettre a jour.		
		return true;
	}
	/**
	* Afficher le plateau de jeu a la console
	* @deprecated
	*/

	
	/** Affiche l'etat du plateau en console*/
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
				
				if (this.cases[numCase].getContenu() == Bille.NOIR)
					System.out.print("N   ");
				else if (this.cases[numCase].getContenu() == Bille.BLANC)
					System.out.print("B   ");
				else
					System.out.print("V   ");
				
				numCase++;
			}
			
			System.out.println("Bille Ejecte lors du mouvement : "+this.cases[0].getContenu());

			System.out.println();
			System.out.println();
			
		}

	}
	/**
	* Ajoute a chaque case du plateau ces 6 cases adjacentes.<br />
	* Cela sera pratique pour savoir les deplacements possibles ainsi que pour effectuer ces deplacements.
	*/
	public void caseAdjacente(){
		
/*
 * Ce qui suit sera remplace par un algorithme plus performant et plus style !.
 */
/*		
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
*/
	}
	
	/** Modifier Le plateau de jeu.
	* 
	* @param p Le plateau de jeu qui sera copie dans plateau
	*/
	public boolean setPlateau(Plateau p){
		for(int courante = 0; courante <= 61; courante++) {
			this.cases[courante] = p.cases[courante];
		}
		
		return true;
	}
/*	
	private void mettreAJour() {
		for(Case courante : this.cases) {
			if(!courante.contientBille()) {
				courante = new Case(courante.getNumero(), false); // fausse bille.
			}
		}
	}
*/	
}