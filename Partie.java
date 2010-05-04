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
<<<<<<< HEAD

	public Partie(/*...*/) {

/**
 * Le premier mouvement est le numero 0		
 */

		joueurs[1] = new Joueur("pseudo");
		joueurs[2] = new IA("parametre"); 
/* le plateau crée un processus pour l'ia afin de répondre à ses requetes (liste des coups disponibles, par exemple). Tant que l'ia n'a pas reçu de réponse, elle peut peut-être utiliser une autre méthode de recherche par exemple ?...
*/
=======
	protected byte nbBillesAEjecter; 
	
	public Partie(String[] pseudo) {
		this.nbBillesAEjecter = 1; // pour faire les tests plus rapidement
		terminee = false;
 		int i = 0;
 		joueurs = new ArrayList<Joueur>();
 		for(String Pseudocourant : pseudo) {
			joueurs.add(new Joueur(Pseudocourant));//joueurs[i++] = new Joueur(Pseudocourant);
		}
>>>>>>> 58781f743e46dc042dd8d0d4aa73c40e9db21389
		plateau = new Plateau(); // initialise les valeurs des vecteurs
		
		FenetreJeu f = new FenetreJeu(plateau);
		i = 0;
		System.out.println();

		while(!terminee) {
			this.coupActuel = this.joueurs.get(i%(joueurs.size())).jouer(); // on attend que le joueur envoie son coup.
			System.out.println("Partie::J"+(i%joueurs.size())+" joue : "+coupActuel.getPremiere()+"-"+coupActuel.getDerniere()+"-"+coupActuel.getVecteur());
			this.plateau.effectuer(coupActuel);
			if(this.plateau.cases[0].getContenu() != Case.VIDE) {
				if((this.joueurs.get(i).score += 1) == this.nbBillesAEjecter) {
					this.terminee = true;
				}
				this.plateau.cases[0].setContenu(Case.VIDE);				
			}
			i++;
			this.plateau.afficher();
			f.rafraichir(plateau);
		}
		System.out.println("Partie terminee.");
	}
}