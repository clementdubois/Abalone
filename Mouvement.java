/** Code un mouvement
 * 
 * La premiere bille deplace et la derniere (s'il s'agit d'une poussee la premiere bille = la deuxieme bille).
 * La direction du mouvement.
 */

public class Mouvement {
	/** Determine s'il s'agit d'un mouvement réelle lors d'une partie (true) 
	* ou d'une bille placé lors d'une édition par exemple (false)*/
	private boolean estMouvement;
	/** Numero de la premiere bille*/
	private byte premiere;
	/** Numero de la bille d'arrivee*/
	private byte derniere;
	/** Vecteur de deplacement (direction) */
	private byte vecteur;
	
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
	
	/** Initialise un mouvement
	* @param prem Le premiere bille selectionner.
	* @param dern La derniere bille selectionner.
	* @param vect L direction du mouvement.
	*/
	public Mouvement(byte prem, byte dern, byte vect){
		this.premiere = prem;
		this.derniere = dern;
		this.vecteur = vect;
		this.estMouvement = true;
	}
	
	/** Renvoie vrai s'il s'agit d'un vrai deplacement et faux s'il s'agit d'une edition.
	*
	* @return true c'est un vrai deplacement
	* @return false c'est une edition
	*/
	public boolean getEstMouvement(){
		return estMouvement;
	}
	
	/** Renvoie la premiere bille du deplacement.
	*
	* @return la premiere bille du deplacement
	*/
	public byte getPremiere(){
		return premiere;
	}
	
	/** Renvoie la derniere bille du deplacement.
	*
	* @return la derniere bille du deplacement
	*/
	public byte getDerniere(){
		return derniere;
	}
	
	/** Renvoie la direction du deplacement.
	*
	* @return la direction du deplacement
	*/
	public byte getVecteur(){
		return vecteur;
	}
	
	/**
	* Verifie qu'on mouvement est valide.
	* plateau.cases[x]  ==> la BILLE numero x du plateau.
	* plateau.cases[x].vecteur ==> le NUMERO de la bille adjacente a la bille x et de direction vecteur.
	* plateau.cases[plateau.cases[x].vecteur] ==> la BILLE adjacente a la bille x et de direction vecteur.
	*
	* @param p Le plateau de jeu sur lequel on veux verifier que le mouvement est possible.
	* @return true si le mouvement est autorise, false si le mouvement est interdit
	*/
	public boolean valider(Plateau p){
		Plateau plateau = p;
		/* La derniere bille testé*/
		byte derniereBille = this.premiere;
		/* Le contenu de la derniere bille testé*/
		byte contenuBille = plateau.cases[premiere].getContenu();
		/* La couleur de la bille adverse adjacente qui va peut etre etre deplacee*/
		byte couleurAdverse;
		//La bille du mileu s'il s'agit d'un mouvement latéral de trois billes 
		byte milieu = -10;//Initialisation a un nombre quelconque pour éviter qu'il ne soit pas initialiser (surtout pas mettre 0)
		byte cptBilleMoi; //Compteur du nombre de bille que je deplace
		byte cptBilleLui; //Compteur du nombre de bille adverse qui vont etre deplacees
		
		//On calcul le nombre de bille poussées et on regarde la derniere de nos billes poussee, derniereBille vaudra la derniere de nos billes poussée
		for(cptBilleMoi = 1; plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu() == contenuBille; cptBilleMoi++)
			derniereBille = plateau.cases[derniereBille].getAdjacent(vecteur);
		
		//Si c'est une poussee (si on ne touche qu'une bille) et si on est bien sur une bille
		if(this.derniere == this.premiere ){	
			//On teste la case juste apres nos billes
			//Si suivante vide et je pousse maximum trois de mes billes : OK
			System.out.println("DERNIERE BILLE : "+derniereBille+" ");
			System.out.println("vecteur : "+vecteur+" ");
			System.out.println("BILLE ADJ : "+plateau.cases[derniereBille].getAdjacent(vecteur)+" ");
			
			System.out.println("PREMIER : "+plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu()+" ");
			
			if (plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu() == Case.VIDE &&
			         cptBilleMoi <= 3){
				return true;
			}
			// Si on fait face a une bille du camp adverse (suivante ni VIDE ni TROU)
			else if(plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu() != Case.NEANT &&
							plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu() != Case.VIDE &&
			        cptBilleMoi <= 3 ){ 
				//On regarde la couleur de la bille adverse a pousser
				couleurAdverse = plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu();
				//On calcule le nombre de billes adverse qui vont être deplacées
				for(cptBilleLui = 0; plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu() == couleurAdverse; cptBilleLui++)
					derniereBille = plateau.cases[derniereBille].getAdjacent(vecteur);
				
				
			  //Je dois etre en superiorite numerique pour valider le mouvement
				if(cptBilleMoi > cptBilleLui){
					//Aucune autre bille ne doit se trouver derriere les billes poussees car elle bloquerai le passage
					if(plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu() == Case.VIDE ||
					   plateau.cases[plateau.cases[derniereBille].getAdjacent(vecteur)].getContenu() == Case.NEANT ){
						return true;
					}
				}
			}
		}else{//Deplacement lateral
			byte numCaseIntermediaire = plateau.caseIntermediaire(this.premiere, this.derniere);
			
			//On vérfie que des billes ont bien été séléctionnées
			if(plateau.cases[this.premiere].getContenu() != Case.VIDE &&
			  plateau.cases[this.derniere].getContenu() != Case.VIDE ){
					//On vérifie que la distance entre les deux billes est valide (invalide si case intermediaire renvoi -1)
					if (numCaseIntermediaire != -1){
						//Si il y a effectivement une case intermediaire et que cette case n'est pas vide...
						if(numCaseIntermediaire != 0 )
							// milieu = la case adjacente a la case intermediaire
							milieu = plateau.cases[plateau.cases[numCaseIntermediaire].getAdjacent(vecteur)].getContenu();

						//Il suffit de verifier que toutes les cases visees sont vides et que la bille intermediaire existe
						if (plateau.cases[plateau.cases[this.premiere].getAdjacent(vecteur)].getContenu() == Case.VIDE &&
						    plateau.cases[plateau.cases[this.derniere].getAdjacent(vecteur)].getContenu() == Case.VIDE &&
								( milieu == -10 || (milieu == Case.VIDE && plateau.cases[numCaseIntermediaire].getContenu() != Case.VIDE)) ){
							return true;
						}

					}	
			}
			

		}
		
		//Si on arrive la c'est que c'est un mauvais mouvement
		return false;
	}
	
	/** */
	public char traduction() {
		char temp = 0xa000;
		char temp2 = 0x0000;
		return temp;
	}

	/** Pour afficher en console un mouvement*/
	public String toString(){
		String res="";
		
		res = res+"Premiere : "+this.premiere+"\n"+"Deuxieme : "+this.derniere+"\n"+"Vecteur : "+this.vecteur+"\n";

		return res;
	}
}