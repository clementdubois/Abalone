/** Effectue la gestion d'une partie
*
*
*/

public class Partie{
	/** Le joueur noir */
	public static final short NOIR = 2;
	/** Le joueur blanc */
	public static final short BLANC = 1;
	/** Le joueur qui doit jouer le prochain coup*/
	protected short joueurActuel;
	/** Le score en cours du joueur Noir*/
	protected short scoreNoir;
	/** Le score en cours du joueur Blanc*/
	protected short scoreBlanc;
	/** Numero du coup jouee.
	* Le premier mouvement est le numero 0
	*/
	protected int numCoup;
	/** Tous les mouvements sont stockes dans ce tableau*/
	protected Mouvement [] listeCoups;
	/** Le plateau de jeu associe a la partie*/
	protected Plateau plateau;


		
	/** Constructeur sans plateau, un nouveau plateau de jeu est initialisee au debut de la partie.
	*
	* @param nbJ Nombre de joueur dans la partie
	*/
	public Partie(){
		this.scoreBlanc = 0;
		this.scoreNoir = 0;
		this.numCoup = 0;
		this.plateau = new Plateau();
		this.joueurActuel = NOIR;
	}
	
	/** Lance une partie */
	public void lancerPartie(){
		
	}
	
	/** Renvoyer le joueur actuel (BLANC ou NOIR)
	*
	* @return le joueur actuel
	*/
	public short getJoueurActuel(){
		return this.joueurActuel;
	}
	
	/** Met a jour le score si une bille a ete ejectee
	*
	* @param p le plateau de jeu sur lequel ou joue la partie
	*/
	public void actualiserScore(Plateau p){
		if (p.tomber() == Plateau.NOIR)
			scoreBlanc++;
		else if (p.tomber() == Plateau.BLANC)
			scoreNoir++;
	}
	
	/** Change le joueurActuel*/
	public void changerJoueur(){
		if (joueurActuel == Partie.NOIR)
			joueurActuel = Partie.BLANC;
		else
			joueurActuel = Partie.NOIR;
	}
	
	/** A MODIFIER: affiche le résultat en console*/
	public boolean terminer(){
		if (scoreBlanc == 6){
			System.out.println("Le blanc gagne");
			return true;
		}
		else if (scoreNoir == 6){
			System.out.println("Le noir gagne");
			return true;
		}
		else
			return false;
			
	}
	
}