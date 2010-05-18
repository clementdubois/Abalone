import java.util.*;
import java.io.*;


public class Plateau implements Serializable, Cloneable{
	/** la case trou (0) dans la liste des cases, la numero 0 est celle reservee au trou */
	public final static int TROU = 0;

	/** La liste des cases du plateau (avec leur ocntenu)*/
	public Case[] cases; 
	
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
	/** Joueur 1 : le noir*/
	public final static int NOIR = 1;
	/** Joueur 2: le blanc*/
	public final static int BLANC = 2;
	
	/** Stocke le numero joueur qui doit jouer (1 = NOIR, 2 = BLANC) */
	protected int joueurActuel;
	/** Le numero du coup*/
	protected int numCoup;
	/** Score actuel de chaque joueur*/
	protected int[] score;
	
	
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
		//Initialisation du joueur en cour
		joueurActuel = 1;
		//Initialisation du num de coup
		numCoup = 0;
		//Initialisation du score
		score = new int[2];
		score[NOIR-1] = 0;
		score[BLANC-1] = 0;
		
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
	
	/** Constructeur de chargement de plateau
	* @param c cases
	* @param ja joueurActuel
	* @param nc numCoup
	* @param s score
	*/
	public Plateau(Case[] c, int ja, int nc, int[] s){
		cases = c;
		joueurActuel = ja;
		numCoup = nc;
		score = s;
		associerNotations();
		//Pour chaque pion on enregistre ses points adjacents
		casesAdjacentes();
	}
	//--------------------------------------ACCESSEURS-----------------------------------
			/** Renvoie le numero du joueur en cour
			* @return le numero du jouer actuel (1 pour le J1 NOIR et 2 pour le J2 BLANC)
			*/
			public int getJoueurActuel(){
				return joueurActuel;
			}
			/** Change le jouer actuel pour savoir qui doit jouer*/
			public void setJoueur(){
				if(joueurActuel == NOIR)
					joueurActuel = BLANC;
				else
					joueurActuel = NOIR;
			}
	
			/** Renvoie le score du joueur demande
			* @param numJ le numero du joueur dont l'on veut connaitre le score
			* @return le score du joueur demande
			*/
			public int getScore(int numJ){
				if(numJ == NOIR)
					return score[NOIR-1];
				else
					return score[BLANC-1];
			}
			/** Vérifie le plateau pour savoir si une bille est tombe au dernier coup et incremente le score*/
			public void setScore(){
				//Des qu'une bille est tombee incremente le score
				//Si une bille blanche est dans le trou, on incremente le score du joueur noir
				if(this.cases[TROU].getContenu() == Case.BLANC) {
					this.score[NOIR-1] += 1;
							
				}else	if(this.cases[TROU].getContenu() == Case.NOIR) {
					this.score[BLANC-1] += 1;
				}
				
				//On revide la case trou comme le score a ete pris en compte
				this.cases[TROU].setContenu(Case.NEANT);
			}
		
			/**@return Le numero du coup en cour*/
			public int getNumCoup(){
				return numCoup;
			}
			/** Ajoute un coup au compteur*/
			public void setNumCoup(){
				numCoup++;
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
	//------------------------------------------------------------------------------------------------
	
	/** Affiche une partie en console avec le System*/
	public String toString(){
		String afficher="";
		
		afficher += "Joueur Actuel : "+joueurActuel+"\n";
		afficher += "Numero du coup : "+numCoup+"\n";
		afficher += "Score du joueur 1 : "+score[NOIR-1]+"\n";
		afficher += "Score du joueur 2 : "+score[BLANC-1]+"\n";
		
		return afficher;
	}

	/**  Initialiser le plateau de jeu en posant les billes de depart
	*
	*/
	public void initialiser(){
		//On place les 14 pions de chaques couleurs sur le plateau et on indique les cases vides
		for (byte i = 1; i < NB_CASES ; i++){
		       if (i < 17 && i != 12 && i != 13){
		          cases[i].setContenu(Case.BLANC);
						}		       
						else if (i > 45 && i != 50 && i != 49)
		          cases[i].setContenu(Case.NOIR);
		       else
		          cases[i].setContenu(Case.VIDE);//Met la case a Case.VIDE
		}
		
		//On initialise la case TROU
		cases[TROU].setContenu(Case.NEANT);
	}

	/** Effectuer un mouvement sur le plateau
	* @param m Le mouvement a effctuer
	*/
	public void effectuer(Mouvement m){
		byte caseActuel = m.getPremiere();
		byte caseSuivante;
		byte contenuActuel = cases[caseActuel].getContenu();
		byte contenuSuivant;

		// Si on ne pousse qu'une bille c'est qu'il s'agit d'une poussee
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
	* @return le numero de la bille au milieu ou 0 si il n'y a pas de case intermediaire ou -1 si la distance entre les deux billes est trop grande
	*/
	public byte caseIntermediaire(byte premiere, byte derniere){
		// Si c'est la meme bille, il n'y a pas d'intermediaire
		if(premiere == derniere){
			return 0;
		}
		//Si premiere est directement adjacent a deuxieme, il n'y a pas d'intermediaire
		else if( cases[premiere].getAdjacent(HG) == derniere ||
						 cases[premiere].getAdjacent(HD) == derniere ||
						 cases[premiere].getAdjacent(DD) == derniere ||
						 cases[premiere].getAdjacent(BD) == derniere ||
						 cases[premiere].getAdjacent(BG) == derniere ||
						 cases[premiere].getAdjacent(GG) == derniere
						){
			return 0;
		}
		//Il y a forcement une case intermediaire, attention a ne pas comparer la case vide
		else{
			if(cases[premiere].getAdjacent(HG) == cases[derniere].getAdjacent(BD) && cases[premiere].getAdjacent(HG) != TROU)
				return cases[premiere].getAdjacent(HG);
				
			else if(cases[premiere].getAdjacent(HD) == cases[derniere].getAdjacent(BG) && cases[premiere].getAdjacent(HD) != TROU )
				return cases[premiere].getAdjacent(HD);
			
			else if(cases[premiere].getAdjacent(DD) == cases[derniere].getAdjacent(GG) && cases[premiere].getAdjacent(DD) != TROU )
				return cases[premiere].getAdjacent(DD);
				
			else if(cases[premiere].getAdjacent(BD) == cases[derniere].getAdjacent(HG) && cases[premiere].getAdjacent(BD) != TROU )
				return cases[premiere].getAdjacent(BD);
				
			else if(cases[premiere].getAdjacent(BG) == cases[derniere].getAdjacent(HD) && cases[premiere].getAdjacent(BG) != TROU )
				return cases[premiere].getAdjacent(BG);
				
			else if(cases[premiere].getAdjacent(GG) == cases[derniere].getAdjacent(DD) && cases[premiere].getAdjacent(GG) != TROU )
				return cases[premiere].getAdjacent(GG);
			
			else{
				//La séparation entre les deux billes est de plus d'une bille intermediaire
				return -1;
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
				
				if (this.cases[numCase].getContenu() == Case.NOIR)
					System.out.print("N   ");
				else if (this.cases[numCase].getContenu() == Case.BLANC)
					System.out.print("B   ");
				else
					System.out.print("V   ");
				
				numCase++;
			}
			System.out.println();
			
		}
		
		System.out.println("Bille Ejecte lors du mouvement : "+this.cases[TROU].getContenu());

		System.out.println();
		System.out.println();

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
				k++;
				assOfficielleVersByte.put((lettreLigne[i]+""+(longueurLigne[i]-i*(longueurLigne[i]-lastLength)+j-i)), k); // on associe la notation officielle aux numeros de billes
				assByteVersOfficielle.put(k, (lettreLigne[i]+""+(longueurLigne[i]-i*(longueurLigne[i]-lastLength)+j-i))); // et vice versa
			}
			lastLength = longueurLigne[i];
		}
		// la valeur de k est conservée !
		for(int i=5;i<9;i++) { // de d à a
			for(int j=0;j<longueurLigne[i];j++) {
				k++;
				assOfficielleVersByte.put((lettreLigne[i]+""+(j+1)), k); // on associe la notation officielle aux numeros de billes
				assByteVersOfficielle.put(k, (lettreLigne[i]+""+(j+1))); // et vice versa	
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
		cases[(Byte)assOfficielleVersByte.get("e1")].setVecteursNuls(tempVecteurs3); // 27
		cases[getNumCaseOpposee((Byte)assOfficielleVersByte.get("e1"))].setVecteursNulsOpposes(tempVecteurs3); // son opposé		
		
		byte i = 1;
		for(byte numCase = 6 ; numCase <= 19 ; numCase += longueurLigne[i++]) { // le bord haut gauche
			byte[] tempVecteurs4 = {HG, GG};
			cases[numCase].setVecteursNuls(tempVecteurs4);
			cases[getNumCaseOpposee(numCase)].setVecteursNulsOpposes(tempVecteurs4); // son opposé
		}
		i = 1;
		for(byte numCase = 11 ; numCase <= 26 ; numCase += (longueurLigne[++i])) { // le bord haut droit
			System.out.println(numCase);
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
	
	private byte multiplicateurVecteur(byte numVecteur, byte numLigne) {
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
	
	/** Calcul tous les mouvements possibles a partir d'un etat du plateau
	* @param joueurActuel le numero du joueur en cours pour obtenir la liste des coups qu'il peut jouer
	* @return la liste des mouvements que je joueur peut faire
	*/
	public Vector<Mouvement> mouvementsValides(int joueurActuel){
		//Liste des mouvements valide a retourner
		Vector<Mouvement> mouvements = new Vector<Mouvement>();
		//Mouvement en cours de test de validite
		Mouvement m;
		//Le numero de la bille du joueur teste
		Byte numCase;
		//Le numero des cases adjacente
		Case adjacent, adjacent1, adjacent2;
		
		//On cherche les billes du joueur
		Vector<Byte> billes = chercheBilles(joueurActuel);
		
		//Pour chaque bille on teste tous les mouvements de poussés possible
		Iterator itr = billes.iterator();
		while(itr.hasNext()){
			numCase = (Byte)itr.next();
			
			//Pour chaque billes 6 directions possibles à tester
			for(byte i=0; i<6; i++){
				//--------------------Mouvements de poussees---------------------
					m = new Mouvement(numCase, numCase, i);
				
					//On teste la validité du mouvement, s'il est valide on l'ajoute à la liste des mouv renvoyés
					if(m.valider(this))
						mouvements.add(m);
				//-----------------Mouvements lateraux a deux billes-------
					//Si la case adjacente via le vecteur i est une autre bille de la meme couleur alors on peut tester les mouvements correspondants
					adjacent = cases[cases[numCase].getAdjacent(i)];
					if(adjacent.getContenu() == joueurActuel ){
						//On teste toutes les directions
						for(byte j=0; j<6; j++){
							//On ne teste pas les mouvements de poussees deja fait auparavent (qui sont en fait le vecteur i et son oppose)
							if(j != i && j != getVecteurOppose(i)){
								m = new Mouvement(numCase, adjacent.getNumero(), j);
								if(m.valider(this))
									mouvements.add(m);
							}
						}
					}
				
			}
			
			//---------------Mouvements lateraux a trois billes------------
				//On regarde autour de la bille
				for(byte i=0; i<3; i++){
					//Si la bille est entre deux autres on peut vérifier les mouvements possibles avec ces trois billes
					adjacent1 = cases[cases[numCase].getAdjacent(i)];
					adjacent2 = cases[cases[numCase].getAdjacent(getVecteurOppose(i))];
					
					if( adjacent1.getContenu() == joueurActuel && adjacent2.getContenu() == joueurActuel ){
						//On teste toutes les directions pour le mouvement
						for(byte j=0; j<6; j++){
							//On ne teste pas les mouvements de poussees deja fait auparavent (qui sont en fait le vecteur j et son oppose)
							if(j != i && j != getVecteurOppose(i)){
								m = new Mouvement(adjacent1.getNumero(), adjacent2.getNumero(), j);
								if(m.valider(this))
									mouvements.add(m);
							}
						}
						
					}
				}
				//On regarde si la bille est au mileu de deux autres
				
				//On teste le mouvements pour tous les vecteurs lateraux
		}
		
		
		return mouvements;
	}
	
	/** Cherche la liste des numero de cases ou sont placees les billes d'un joueur.
	* @param joueurActuel le numero du joueur en cours.
	* @return un vecteur de billes qui contient le numero de toutes les billes du joueur demande.
	*/
	public Vector<Byte> chercheBilles(int joueurActuel){
	 	Vector<Byte> billes = new Vector<Byte>();
		
		for(byte i=1; i<NB_CASES; i++){
			if(cases[i].getContenu() == joueurActuel){
				billes.add(i);
			}          
		}
				
		return billes;
	}
	
 }

/** Representation codee d'un plateau*/
class Codage{
	/** Les 32 premieres cases du plateau (64 bits)*/
	private long cases32;
	/** Les cases 33 a 61 du plateau (58bits cases + 3bits score J1 + 3bits score J2 )*/
	private long cases61;
	/** Le numero du coup (7bits numCoup + 1bit joueurActuel)*/
	private byte numCoup;
	
	/** Creer la version codee du plateau en argument*/
	public Codage(Plateau p){
		cases32 = 0;
		cases61 = 0;
		numCoup = 0;
		
		//On commence par les 32 premiere cases (2bits par case)
		for(int i=1; i<=31; i++){
			cases32 += p.cases[i].getContenu() & 3;
			cases32 <<= 2;
		}
		cases32 += p.cases[32].getContenu() & 3;
		//Ensuite les dernieres cases
		for(int i=33; i<=61; i++){
			cases61 += p.cases[i].getContenu() & 3;
			cases61 <<= 2;
		}
		//On ajoute le score du joueur 1 (decalage de 1 car deja un decalage de 2 avant)
		cases61 <<= 1;
		cases61 += p.getScore(1) & 7;
		cases61 <<= 3;
		cases61 += p.getScore(2) & 7;
		//On ajoute le score actuel
		numCoup += p.getNumCoup() & 127;
		//On ajoute le joueur actuel (on decale de 1 pour tenir sur 1 bit)
		numCoup <<= 1;
		numCoup += (p.getJoueurActuel()-1) & 1;
	}
	
	/** Decode un plateau*/
	public Plateau decodage(){
		byte contenu;
		
		//Apres transcription
		Case[] cases;
		int joueurActuel;
		int nbCoup;
		int[] score;
		
		//On commence par recuperer les cases
	  cases = new Case[Plateau.NB_CASES];
			//La case 0 est le trou
			cases[0] = new Case((byte)0);
			//Les autres cases sont chargees depuis les attributs de codage
			for(byte i=32; i>=1; i--){
				contenu = (byte)((cases32 >> 2*( -(i-32) )) & 3);
				cases[i] = new Case(i, contenu);
			}
			for(byte i=61; i>=33; i--){
				contenu = (byte)((cases61 >> (2*(-(i-61)) + 6 ) ) & 3);
				cases[i] = new Case(i, contenu);
			}
		//Puis le score
		score = new int[2];
			score[1] = (int)(cases61 & 3);
			score[0] = (int)((cases61 >> 3) & 3);
		//Puis le numero du coup
		nbCoup = (numCoup >> 1) & 7;
		//Enfin le joueur Actuel
		joueurActuel = numCoup & 1; //On récupere 0 ou 1
		joueurActuel++; //On ajoute un pour faire correspondre au fonctionnement de joueurActuel du plateau
			
		Plateau p = new Plateau(cases, joueurActuel, nbCoup, score);
		return p;
	}
	
	/** L'affichage du titre*/
	public String toString(){
		return ""+(numCoup>>1);
	}
}