
/**
* Une partie est simplement un serveur qui attend des connexions puis attend les ordres de la part de joueurs et les redistribue a tous les clients (joueurs, ia, spectateurs) Le createur de la partie possede donc le serveur de la partie. Le serveur de partie possede son propre plateau de jeu sur lequel il effectue les modifications.
*/
public class Partie {
	protected Joueur[] joueurs; // permet de gerer plus de 2 joueurs.
	protected Plateau plateau; // 61 cases contenant soit une bille soit null (empty)
	protected Mouvement[] coup;
/**
 * terminee est effectivement utile car on peut changer les conditions de victoire a l'interieur d'une partie.	
 */
	protected boolean terminee;
	protected String variante;
	protected int numCoup;

	public Partie(/*...*/) {

/**
 * Le premier mouvement est le numero 0		
 */


		joueurs[1] = new Joueur("pseudo");
		joueurs[2] = new IA("parametre"); 
/* le plateau crée un processus pour l'ia afin de répondre à ses requetes (liste des coups disponibles, par exemple). Tant que l'ia n'a pas reçu de réponse, elle peut peut-être utiliser une autre méthode de recherche par exemple ?...
*/
		plateau = new Plateau(); // initialise les valeurs des vecteurs
		
			while(!(terminee)) {
				coup=lireCoup(); // on attend que le joueur envoie son coup. (cense etre bloquant)
				// maintenant qu'on a bien recu le coup, il faut recuperer la liste des Billes et le vecteur (pas besoin de verifier la validite du coup)
				//plateau.joue(coup);
				// il faut maintenant envoyer le coup a tous les clients pour qu'ils le traduisent et l'appliquent.
			}
	}

	private Mouvement[] lireCoup() {
// ici il faut recuperer le string envoye par le client : listeBillesABouger;vecteur
// comme chaque element de listeBillesABouger est code sur 2 caracteres, on doit diviser par 2 pour obtenir le nombre de billes, et donc la longueur du tableau
// on utilise la correspondance de la Hashtable pour traduire en notation int l'emplacement de la bille, puis on applique le vecteur.
		int nombreBilles = 5;
		Mouvement[] m;
		m = new Mouvement[nombreBilles];
// on doit maintenant initialiser les proprietes du mouvement de cette bille pour que plateau.joue(coup) puisse fonctionner.
		return m;
	}
}