/** Classe gerant les mouvements (creation, validation, faire le mouvement).
* Les deplacements sont geres de la meme façon qu'un humain jouerai physiquement :
* <ul>
* 	<li>quand on fait une poussee on ne touche qu'une bille dans une direction, le mouvement se fera donc en indiquant seulement la direction, la premiere bille et le plateau sur lequel le mouvement va etre effectue.</li>
* 	<li>quand on fait un mouvement lateral il faut prendre toutes (deux ou trois) les billes, le mouvement se fera donc en indiquant la direction ainsi que toutes les billes deplacees et le plateau.</li>
* </ul>
*/
public class Mouvement{
	/** Contient la direction du mouvement*/
	private int dir;
	/** Premiere bille concernee par le deplacement*/
	private int bille1;
	/** Deuxieme bille concernee par le deplacement*/
	private int bille2;
	/** Troisieme bille concernee par le deplacement*/
	private int bille3;
	/** Le plateau sur lequel est creer le mouvement*/
	private Plateau plateau;
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
	
	/** Constructeur pour un mouvement de poussee.
	* Une seule bille en parametre, les autres billes qui vont etre deplacees seront calculees automatiquement.
	*
	* @param direction la direction du mouvement (voir les constantes de direction).
	* @param b la bille poussee.
	* @param p le plateau sur lequel s'effectuera le deplacement.
	*/
	public Mouvement(int direction, int b, Plateau p){
		dir = direction;
		bille1 = b;
		bille2 = -1;
		bille3 = -1;
		plateau = p;
	}
	/** Constructeur pour un mouvement lateral.
	* Deux billes en parametre donc il s'agit d'un mouvement lateral.
	*
	* @param direction la direction du mouvement (voir les constantes de direction).
	* @param b1 la premiere bille deplacee.
	* @param b2 la deuxieme bille deplacee
	* @param p le plateau sur lequel s'effectuera le deplacement.
	*/
	public Mouvement(int direction, int b1, int b2, Plateau p){
		dir = direction;
		bille1 = b1;
		bille2 = b2;
		bille3 = -1;
		plateau = p;
	}
	/** Constructeur pour un mouvement lateral.
	* Trois billes en parametre donc il s'agit d'un mouvement lateral.
	*
	* @param direction la direction du mouvement (voir les constantes de direction).
	* @param b1 la premiere bille deplacee.
	* @param b2 la deuxieme bille deplacee.
	* @param b3 la troisieme bille deplacee.
	* @param p le plateau sur lequel s'effectuera le deplacement.
	*/
	public Mouvement(int direction, int b1, int b2, int b3, Plateau p){
		dir = direction;
		bille1 = b1;
		bille2 = b2;
		bille3 = b3;
		plateau = p;
	}
	
	/** Effectuer un le mouvements sur un plateau.
	*
	* @param p Le plateau de jeu sur lequel va etre effectue le deplacement
	*/
	public void effectuer(Plateau p){
		int [][] plateau = p.getPlateau();
		int caseActuel = bille1;
		int caseSuivante;
		int contenuActuel = plateau[bille1][Plateau.ETAT];
		int contenuSuivant;
		
		// Si on ne pousse qu'une bille ou si la deuxieme bille est dans la direction du deplacement c'est qu'il s'agit d'une poussee 
		if((bille2 == -1) || (plateau[bille1][dir] == bille2)){
			System.out.println("Poussee deplacement");
			/*On POUSSE toute la ligne d'une case*/
			caseSuivante = plateau[caseActuel][dir]; //Il y a forcement au moins une case suivante sinon il s'agirai d'un suicide
			contenuSuivant = plateau[caseSuivante][Plateau.ETAT];
			plateau[bille1][Plateau.ETAT] = Plateau.VIDE;
		
			while(contenuActuel !=Plateau.VIDE && caseActuel != Plateau.TROU){			
				plateau[caseSuivante][Plateau.ETAT] = contenuActuel;
			
				caseActuel = caseSuivante;
				caseSuivante = plateau[caseActuel][dir];
				contenuActuel = contenuSuivant;
				contenuSuivant = plateau[caseSuivante][Plateau.ETAT];
			}
		}//Sinon, il ya a au moins deux billes et la dir du mouvement est different de l'alignement des bille, c'est un mouvement lateral
		else{
			System.out.println("PAS poussee deplacement");
			
			/* on prend la bille 1 et on la deplace sur la case vide,
			 on fait pareil avec la deuxieme et la troisieme si elle existe.
			 Les billes deplacees sont forcement de la même couleur, il est donc inutile de redefinir le contenu pour chaque bille
			 Les cases destinations sont forcement vide.*/
			
			plateau[bille1][0] = Plateau.VIDE;
		 	plateau[bille2][0] = Plateau.VIDE;
			plateau[plateau[bille1][dir]][Plateau.ETAT] = contenuActuel;
			plateau[plateau[bille2][dir]][Plateau.ETAT] = contenuActuel;
			
			if (bille3 != -1){
				plateau[bille3][0] = Plateau.VIDE;
				plateau[plateau[bille3][dir]][Plateau.ETAT] = contenuActuel;
			}
			
		}
		
		//On modifie le plateau reel
		p.setPlateau(plateau);
	}
	
	/**
	* Verifie qu'on mouvement est valide.
	*
	* @param p Le plateau de jeu sur lequel on vux verifier que le mouvement est possible.
	* @return VALIDE si le mouvement est autorise, INVALIDE si le mouvement est interdit
	*/
	public int valider(Plateau p){
		int[][] plateau = p.getPlateau();
		int derniereBille = bille1;
		int contenuBille = plateau[bille1][Plateau.ETAT];
		short cptBilleMoi = 1; //Compteur du nombre de bille que je deplace
		short cptBilleLui = 0; //Compteur du nimbre de bille adverse qui vont être deplacees
		
		//Si c'est une poussee (si on ne touche qu'une bille)
		if(bille2 == -1){
			//On regarde la derniere de nos billes poussee
			System.out.println("BILLE : "+dir+" "+derniereBille+" "+plateau[derniereBille][dir]);
			while(plateau[plateau[derniereBille][dir]][Plateau.ETAT] == contenuBille){
				cptBilleMoi++;
				derniereBille = plateau[derniereBille][dir];
			}
			
			System.out.println("Une poussee");
			
			//On teste la case juste apres nos billes
			if(plateau[derniereBille][dir] == Plateau.TROU){//Si suivante trou PAS OK
				System.out.println("Trou");
				return INVALIDE;
			}
			else if(cptBilleMoi > 3){//Plus de trois bille poussees
				System.out.println("Trop de bille poussee");
				return INVALIDE;
			}
			else if (plateau[plateau[derniereBille][dir]][Plateau.ETAT] == Plateau.VIDE){//Si suivante vide OK
				System.out.println("VIDE : "+derniereBille+" "+cptBilleMoi);
				return VALIDE;
			}
			else{ // Si on fait face a une bille du camp adverse
				//On calcule le nombre de billes adverse qui vont être deplace
				while(plateau[plateau[derniereBille][dir]][Plateau.ETAT] != contenuBille &&
				      plateau[plateau[derniereBille][dir]][Plateau.ETAT] != Plateau.VIDE &&
						  plateau[plateau[derniereBille][dir]][Plateau.ETAT] != Plateau.TROU){
					cptBilleLui++;
					derniereBille = plateau[derniereBille][dir];
				}
				
				if(cptBilleMoi <= cptBilleLui){// Si les billes adverses sont plus nombreuses ou egal, le mouvement est impossible
					System.out.println("Inferiorité numérique");
					return INVALIDE;
				}
				else{
					System.out.print("Superiorité numérique ");
					//Si une de nos bille est juste derriere, elle bloque le mouvement
					if(plateau[plateau[derniereBille][dir]][Plateau.ETAT] == contenuBille){
						System.out.println("mais une bille bloque le passage");
						return INVALIDE;
					}
					else
						return VALIDE;
				}
	
			}
			
		}else{//Deplacement lateral
			//Il suffit de verifier que toutes les cases visees sont vides
			System.out.println("Pas une poussee");
			if (plateau[plateau[bille1][dir]][Plateau.ETAT] != Plateau.VIDE)
				return INVALIDE;
			else if (plateau[plateau[bille2][dir]][Plateau.ETAT] != Plateau.VIDE)
				return INVALIDE;
			else if (bille3 != -1 && plateau[plateau[bille3][dir]][Plateau.ETAT] != Plateau.VIDE)
				return INVALIDE;
			else
				return VALIDE;
		}
		
	}
	
	/** Renvoie la bille1
	*
	* @return bille1 la premiere bille deplacee du mouvement
	*/
	public int getBille1(){
		return bille1;
	}
	/** Renvoie la bille2
	*
	* @return bille2 la deuxieme bille deplacee du mouvement
	*/
	public int getBille2(){
		return bille2;
	}
	/** Renvoie la bille3
	*
	* @return bille1 la troisieme bille deplacee du mouvement
	*/
	public int getBille3(){
		return bille3;
	}
	/** Renvoie la direction du mouvement
	*
	* @return dir la direction du mouvement.
	*/
	public int getDirection(){
		return dir;
	}
	
}