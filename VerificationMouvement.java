/**
 * 
 * @author Clement
 * il faut que tu adaptes ta fonction :)
 */
public class VerificationMouvement {

	/**
	* Verifie qu'on mouvement est valide.
	*
	* @param p Le plateau de jeu sur lequel on vux verifier que le mouvement est possible.
	* @param joueurActuel La couleur du joueur actuel (NOIR ou BLANC)
	* @return true si le mouvement est autorise, false si le mouvement est interdit
	*/
	public boolean valider(Plateau p, short joueurActuel){
		int[][] plateau = p.getPlateau();
		int derniereBille = this.bille1;
		int contenuBille = plateau[bille1][Plateau.ETAT];
		short cptBilleMoi = 1; //Compteur du nombre de bille que je deplace
		short cptBilleLui = 0; //Compteur du nimbre de bille adverse qui vont être deplacees
		
		//On commence par vérifié que les biles déplacé sont bien de la bonne couleur
		if(plateau[this.bille1][Plateau.ETAT] != joueurActuel || 
			 (bille2 != -1 && plateau[this.bille2][Plateau.ETAT] != joueurActuel) ||
			 (bille3 != -1 && plateau[this.bille3][Plateau.ETAT] != joueurActuel)){
				System.out.println("Au moins une des billes n'est pas de la couleur du joueur");
				return false;
		}
		//Si c'est une poussee (si on ne touche qu'une bille)
		if(this.bille2 == -1){
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
				return false;
			}
			else if(cptBilleMoi > 3){//Plus de trois bille poussees
				System.out.println("Trop de bille poussee");
				return false;
			}
			else if (plateau[plateau[derniereBille][dir]][Plateau.ETAT] == Plateau.VIDE){//Si suivante vide OK
				System.out.println("VIDE : "+derniereBille+" "+cptBilleMoi);
				return true;
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
					return false;
				}
				else{
					System.out.print("Superiorité numérique ");
					//Si une de nos bille est juste derriere, elle bloque le mouvement
					if(plateau[plateau[derniereBille][dir]][Plateau.ETAT] == contenuBille){
						System.out.println("mais une bille bloque le passage");
						return false;
					}
					else
						return true;
				}
	
			}
			
		}else{//Deplacement lateral
			//Il suffit de verifier que toutes les cases visees sont vides
			System.out.println("Pas une poussee");
			if (plateau[plateau[this.bille1][dir]][Plateau.ETAT] != Plateau.VIDE)
				return false;
			else if (plateau[plateau[this.bille2][dir]][Plateau.ETAT] != Plateau.VIDE)
				return false;
			else if (this.bille3 != -1 && plateau[plateau[this.bille3][dir]][Plateau.ETAT] != Plateau.VIDE)
				return false;
			else
				return true;
		}
		
	}	
}