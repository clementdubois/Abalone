import java.util.Hashtable;


public class Plateau {
	/** la case trou (0) dans la liste des cases, la numero 0 est celle reservee au trou */
	public final static int TROU = 0;

	public Case[] cases; // dans Case on retrouvera les cases adjacentes

	/** Vecteur en haut a gauche*/
	public static final byte HD = 0;
	/** Vecteur a droite*/
	public static final byte DD = 1;
	/** Vecteur en bas a droite*/
	public static final byte BD = 2;
	/** Vecteur en bas a gauche*/
	public static final byte BG = 3;
	/** Vecteur a gauche*/
	public static final byte GG = 4;
	/** Vecteur en haut a gauche*/
	public static final byte HG = 5;
	
	
	
	/**
	 * l'association entre notation officielle et notation byte
	 */
		private static Hashtable<String, Byte> assOfficielleVersByte;
	/**
	 * l'association entre notation byte et notation officielle
	 */
		private static Hashtable<Byte, String> assByteVersOfficielle;		
	/**	
	 * la longueur de chacune des 9 lignes
	 */		
		private static byte[] longueurLigne;
	/** 
	 * les lettres associées aux lignes
	 */			
		private static String[] lettreLigne;
	/**
	 * il y a 61 cases sur le plateau de jeu, + le trou	(la case 0)
	 */	
		public static final int NB_CASES = 62;	

	public Plateau() {
		//On initialise le tableau de cases
		this.cases = new Case[NB_CASES];
		//On indique un numero a chaque case (en deux etapes obligatoire car sinon cases = NULL et cases[i] est donc impossible)
		for(byte i=0; i<NB_CASES; i++)
			this.cases[i] = new Case(i);
			
		//On remplit les cases
		initialiser();
		associerNotations();
		
		//Pour chaque pion on enregistre ses points adjacents
		casesAdjacentes();
	}

	/**  Initialiser le plateau de jeu en posant les billes de depart
	*
	*/
	

	public void initialiser(){
		//On place les 14 pions de chaques couleurs sur le plateau et on indique les cases vides
		for (byte i = 1; i < NB_CASES ; i++){
		       if (i < 17 && i != 12 && i != 13){
		          cases[i].setBille(new Bille(Bille.BLANC));
						}		       
						else if (i > 45 && i != 50 && i != 49)
		          cases[i].setBille(new Bille(Bille.NOIR));
		       else
		          cases[i].setContenu(Case.VIDE);//Met la case a Case.VIDE
		}
	}
	
	/** Rendre effectif un mouvement sur le plateau.
	* 
	* @param m le mouvement a effectuer
	* 
	 */
	/*public boolean joue(Mouvement m) {
		int i = 0;
		while(i < m.length) { // on veut appliquer le mouvement a chaque bille.
			cases[m[i].getCoordonneesArrivee()].contiensBille();
			cases[m[i].getCoordonneesArrivee()].setBille(cases[m[i].getCoordonnesDepart()].getBille());
			cases[m[i].getCoordonnesDepart()].contiensVide();
		}
// inutile : le traitement juste au-dessus gere tous les mouvements.
// this.mettreAJour(); // permet de gerer n'importe quel type de deplacement : bille.appliquerCoordonnees() change le champ position de la bille et mettreAJour() va chercher les positions de chaque bille pour se mettre a jour.		
		return true;
	}*/

	/** Effectuer un mouvement */
	public void effectuer(Mouvement m){
		byte caseActuel = m.getPremiere();
		byte caseSuivante;
		byte contenuActuel = cases[caseActuel].getContenu();
		byte contenuSuivant;

		// Si on ne pousse qu'une bille c'est qu'il s'agit d'une poussÃ©e
		if(m.getPremiere() == m.getDerniere()){
			/*On POUSSE toute la ligne d'une case*/
			caseSuivante = cases[caseActuel].getAdjacent(m.getVecteur()); //Il y a forcement au moins une case suivante sinon il s'agirai d'un suicide
			contenuSuivant = cases[caseSuivante].getContenu();
			cases[m.getPremiere()].setContenu(Case.VIDE);
			
			while(contenuActuel != Case.VIDE && caseActuel != TROU){
				cases[caseSuivante].setContenu(contenuActuel);

				caseActuel = caseSuivante;
				caseSuivante = cases[caseActuel].getAdjacent(m.getVecteur());
				contenuActuel = contenuSuivant;
				contenuSuivant = cases[caseSuivante].getContenu();				
			}
		}//Sinon, il ya a au moins deux billes et la direction du mouvement est different de l'alignement des bille, c'est un mouvement lateral
		else{
			/* on prend la bille 1 et on la deplace sur la case vide,
				on fait pareil avec la deuxieme et la troisieme si elle existe.
				Les billes deplacees sont forcement de la meme couleur, il est donc inutile de redefinir le contenu pour chaque bille
				Les cases destinations sont forcement vide.*/
			
			cases[m.getPremiere()].setContenu(Case.VIDE);	
			cases[m.getDerniere()].setContenu(Case.VIDE);	
			if(caseIntermediaire(m.getPremiere(), m.getDerniere()) != 0){
				cases[caseIntermediaire(m.getPremiere(), m.getDerniere())].setContenu(Case.VIDE);
				cases[cases[caseIntermediaire(m.getPremiere(), m.getDerniere())].getAdjacent(m.getVecteur())].setContenu(contenuActuel);
			}
			cases[cases[m.getPremiere()].getAdjacent(m.getVecteur())].setContenu(contenuActuel); 			
			cases[cases[m.getDerniere()].getAdjacent(m.getVecteur())].setContenu(contenuActuel);
		}

	}
	
	/** Renvoie le numero de la case intermediaire entre deux billes si cette case existe.
	*
	* @param premiere la premiere bille.
	* @param derniere la derniere bille.
	* @return le numero de la bille au milieu ou VIDE si il n'y a pas de case intermediaire
	*/
	public byte caseIntermediaire(byte premiere, byte derniere){
		// Si c'est la meme bille, il n'y a pas d'intermediaire
		if(premiere == derniere)
			return 0;
		//Si deuxieme est directement adjacent a deuxieme, il n'y a pas d'intermediaire
		else if( cases[premiere].getAdjacent(HG) == derniere ||
						 cases[premiere].getAdjacent(HD) == derniere ||
						 cases[premiere].getAdjacent(DD) == derniere ||
						 cases[premiere].getAdjacent(BD) == derniere ||
						 cases[premiere].getAdjacent(BG) == derniere ||
						 cases[premiere].getAdjacent(GG) == derniere
						)
			return 0;
		//Il y a forcement une case intermediaire
		else{
			if(cases[premiere].getAdjacent(HG) == cases[derniere].getAdjacent(BD) )
				return cases[premiere].getAdjacent(HG);
				
			else if(cases[premiere].getAdjacent(HD) == cases[derniere].getAdjacent(BG) )
				return cases[premiere].getAdjacent(HD);
			
			else if(cases[premiere].getAdjacent(DD) == cases[derniere].getAdjacent(GG) )
				return cases[premiere].getAdjacent(DD);
				
			else if(cases[premiere].getAdjacent(BD) == cases[derniere].getAdjacent(HG) )
				return cases[premiere].getAdjacent(BD);
				
			else if(cases[premiere].getAdjacent(BG) == cases[derniere].getAdjacent(HD) )
				return cases[premiere].getAdjacent(BG);
				
			else if(cases[premiere].getAdjacent(GG) == cases[derniere].getAdjacent(DD) )
				return cases[premiere].getAdjacent(GG);
			
			else{
				System.out.println("Erreur de caseIntermediaire, elle ne devrait jamais entrer dans ce else");
				return 0;
			}
			
		}
		
	}
	
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
			System.out.println();
			
		}
		
		System.out.println("Bille Ejecte lors du mouvement : "+this.cases[0].getContenu());

		System.out.println();
		System.out.println();

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
	
	private void associerNotations() {
		longueurLigne = new byte[9];
		lettreLigne = new String[9];
		
		for(byte i = 0; i < 5; i++) {
			longueurLigne[i] = (byte)(i+5);
		}
		longueurLigne[5] = 8;
		longueurLigne[6] = 7;
		longueurLigne[7] = 6;
		longueurLigne[8] = 5;
	
		lettreLigne[0] = "i";		
		lettreLigne[1] = "h";
		lettreLigne[2] = "g";
		lettreLigne[3] = "f";	
		lettreLigne[4] = "e";	
		lettreLigne[5] = "d";	
		lettreLigne[6] = "c";	
		lettreLigne[7] = "b";
		lettreLigne[8] = "a";

		/*
		 * i5->i9
		 * h4->h9
		 * g3->g9
		 * f2->f9
		 * e1->e9
		 * 
		 *  2nd round : 
		 * 
		 * d1->d8
		 * c1->c7
		 * b1->b6
		 * a1->a5
		 */
		char letter;
		int decalage;
		byte k 					= 0;
		int lastLength			= 5;		
		assOfficielleVersByte 	= new Hashtable<String, Byte>(NB_CASES); 
		assByteVersOfficielle 	= new Hashtable<Byte, String>(NB_CASES); 
		for(byte i=0;i<5;i++) { // de i à e
			for(byte j=0;j<longueurLigne[i];j++) {
				assOfficielleVersByte.put((lettreLigne[i]+""+(longueurLigne[i]-i*(longueurLigne[i]-lastLength)+j-i)), k); // on associe la notation officielle aux numeros de billes
				assByteVersOfficielle.put(k++, (lettreLigne[i]+""+(longueurLigne[i]-i*(longueurLigne[i]-lastLength)+j-i))); // et vice versa
			}
			lastLength = longueurLigne[i];
		}
		// la valeur de k est conservée !
		for(int i=5;i<9;i++) { // de d à a
			for(int j=0;j<longueurLigne[i];j++) {
				assOfficielleVersByte.put((lettreLigne[i]+""+(j+1)), k); // on associe la notation officielle aux numeros de billes
				assByteVersOfficielle.put(k++, (lettreLigne[i]+""+(j+1))); // et vice versa				
			}
			lastLength = longueurLigne[i];
		}			
	}

/**
 *	Associe a chaque case du plateau le numero de ses cases adjacentes.
 */ 
	private void casesAdjacentes() {
		// on commence par initialiser tous les vecteurs avec l'automate.
		for(byte i = 1 ; i < 62 ; i++)
			for(byte j = 0 ; j < 6 ; j++)
				this.cases[i].vecteurs[j] = (byte)(i+multiplicateurVecteur(j, this.cases[i].getNumLigne()));
		
	
		// il faut maintenant initialiser les vecteurs menant au TROU 
		byte[] tempVecteurs = {HG, HD, GG};
		cases[1].setVecteursNuls(tempVecteurs); // a1
		cases[this.getNumCaseOpposee((byte)1)].setVecteursNulsOpposes(tempVecteurs); // son opposé
		
		byte[] tempVecteurs2 = {HG, HD, DD};
		cases[5].setVecteursNuls(tempVecteurs2); // a5
		cases[this.getNumCaseOpposee((byte)5)].setVecteursNulsOpposes(tempVecteurs2); // son opposé
		
		byte[] tempVecteurs3 = {HG, GG, BG};
		cases[(Byte)assOfficielleVersByte.get("e1")].setVecteursNuls(tempVecteurs3); // e1
		cases[this.getNumCaseOpposee((Byte)assOfficielleVersByte.get("e1"))].setVecteursNulsOpposes(tempVecteurs3); // son opposé		
		
		byte i = 2;
		for(byte numCase = 6 ; numCase < 19 ; numCase += longueurLigne[i++]) { // le bord haut gauche
			byte[] tempVecteurs4 = {HG, GG};
			cases[numCase].setVecteursNuls(tempVecteurs4);
			cases[getNumCaseOpposee(numCase)].setVecteursNulsOpposes(tempVecteurs4); // son opposé
		}
		i = 2;
		for(byte numCase = 11 ; numCase < 26 ; numCase += (longueurLigne[++i])) { // le bord haut droit
			byte[] tempVecteurs4 = {HD, DD};
			cases[numCase].setVecteursNuls(tempVecteurs4);
			cases[getNumCaseOpposee(numCase)].setVecteursNulsOpposes(tempVecteurs4); // son opposé
		}
		for(byte numCase = 1 ; numCase < 5 ; numCase++) { // le bord haut
			byte[] tempVecteurs4 = {HD, HG};
			cases[numCase].setVecteursNuls(tempVecteurs4);
			cases[getNumCaseOpposee(numCase)].setVecteursNulsOpposes(tempVecteurs4); // son opposé
		}		
	}
	
	private byte getVecteurOppose(byte v) {
		if(v<=2)
			return (byte) (v+3);
		return (byte) (v-3);
	}
	
	private byte getNumCaseOpposee(byte v) {
		return (byte)(62 - v);
	}
	
	private byte multiplicateurVecteur (byte numVecteur, byte numLigne) {
		if(numVecteur == DD)
			return 1;
		if(numVecteur == GG)
			return (byte)(-1);
		if(numVecteur == HD)
			return (numLigne<5) ? (byte)(-(longueurLigne[numLigne])+1) : (byte)(-(longueurLigne[numLigne])) ;
		if(numVecteur == HG)
			return (numLigne<5) ? (byte)(-(longueurLigne[numLigne])) : (byte)(-(longueurLigne[numLigne])-1) ;
		if(numVecteur == BD)
			return (numLigne<4) ? (byte)((longueurLigne[numLigne])+1) : (byte)((longueurLigne[numLigne])) ;
		else // BG
			return (numLigne<4) ? (byte)(longueurLigne[numLigne]) : (byte)(longueurLigne[numLigne]-1) ;
	}
 }