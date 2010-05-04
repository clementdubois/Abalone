import java.lang.*;
import java.util.*;


/**
* Une partie est simplement un serveur qui attend des connexions puis attend les ordres de la part de joueurs et les redistribue a tous les clients (joueurs, ia, spectateurs) Le createur de la partie possede donc le serveur de la partie. Le serveur de partie possede son propre plateau de jeu sur lequel il effectue les modifications.
*/
public class Partie {
	protected ArrayList<Joueur> joueurs; // permet de gerer plus de 2 joueurs.
	protected Plateau plateau; // 61 cases contenant soit une bille soit null (empty)
	protected Mouvement[] coup;
	protected Mouvement coupActuel;
/**
 * terminee est effectivement utile car on peut changer les conditions de victoire a l'interieur d'une partie.	
 */
	protected boolean terminee;
	protected String variante;
	protected int numCoup;
	protected final byte NB_BILLES_EJECTER = 1; 
	
	public Partie(String[] pseudo) {
		terminee = false;
 		int i = 0;
 		joueurs = new ArrayList<Joueur>();
 		for(String pseudocourant : pseudo) {
			joueurs.add(new Joueur(pseudocourant));//joueurs[i++] = new Joueur(Pseudocourant);
		}
		plateau = new Plateau(); // initialise les valeurs des vecteurs
		
		FenetreJeu f = new FenetreJeu(plateau);
		i = 0; //Pour le changement de joueur
		System.out.println();

		while(!terminee) {
			this.coupActuel = this.joueurs.get(i).jouer(); // on attend que le joueur envoie son coup.
			System.out.println("Partie::J"+(i)+" joue : "+coupActuel.getPremiere()+"-"+coupActuel.getDerniere()+"-"+coupActuel.getVecteur());
			this.plateau.effectuer(coupActuel);
			
			//Dès qu'une bille est tombé on termine la partie
			if(this.plateau.cases[0].getContenu() != Case.VIDE) {
				if((this.joueurs.get(i).score += 1) == this.NB_BILLES_EJECTER) {
					this.terminee = true;
				}
				this.plateau.cases[0].setContenu(Case.VIDE);				
			}
			
			i = (i+1)%joueurs.size(); //On passe au jouer suivant
			
			this.plateau.afficher();//Affiche en console
			f.rafraichir(plateau); //Affiche en graphique
		}
		System.out.println("Partie terminee.");
	}
}