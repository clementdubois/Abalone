/** Gestion d'une partie en console (idem que locale mais pas de partie graphique). */
// public class PartieConsole extends Partie{
	/** @param nbJ nombre de joueur 
	public PartieConsole() {
		super();
	}*/
	
	/** Gere la partie du debut a la fin
	public void lancerPartie(){
		Mouvement mouv = new Mouvement(Mouvement.GH, 61, plateau);
		
		do{
				plateau.afficher();
			do{
				this.inviteMouvement(); //demande a la console le mouvement
				//mouv = joueurActuel.faireMouvement(); //C'est le que le joueur (ou l'IA)vas indiquez son mouvement 
			}while (!mouv.valider(plateau, joueurActuel));//Verification de la validitée du mouvement
			
			mouv.effectuer(plateau); //On rend effectif le deplacement sur le plateau de jeu
			this.numCoup++;
			actualiserScore(plateau); //On regarde si une bille a etee sortie du plateau et on actualise le score en conséquence
			changerJoueur();
			
		}while (!this.terminer()); //Tant que la partie n'est pas terminee
		
		plateau.afficher();
	}*/
	
	/** Invite console pour demander au joueur de jouer
	public void inviteMouvement(){
		System.out.println("Veuillez indiquez un mouvement : ");
	}*/
// }