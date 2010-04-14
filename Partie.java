/** Effectue la gestion d'une partie
*
*
*/

public class Partie{
	/** Nombre de joueur dans une partie */ 
	protected int nbJoueur;
	/** Numero du coup jouee.
	* Le premier mouvement est le numero 0
	*/
	protected int numCoup;
	/** Joueur qui doit jouer */
	protected int joueurActuel;
	/** Tous les mouvements sont stockes dans ce tableau*/
	protected Mouvement [] listeCoups;
	/** Le plateau de jeu associe a la partie*/
	protected Plateau plateau;
	
	/** Constructeur sans plateau, un nouveau plateau de jeu est initialisee au debut de la partie
	*
	* @param nbJ Nombre de joueur dans la partie
	*/
	public Partie(int nbJ){
		nbJoueur = nbJ;
		numCoup = 0;
		plateau = new Plateau();
	}
	
	
	
	
	
}