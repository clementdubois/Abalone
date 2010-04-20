
/**
* une classe Bille est-elle vraiment utile finalement, dans le sens ou les vecteurs sont appliques a un tableau et non a des billes ?
*/
public class Bille {
	protected int HG, GG, BG, HD, DD, BD; // permettrait de se placer du point de vue d'une bille : qu'y a-t-il autour d'elle ? (probablement utile pour ia)
	//protected int numeroBille;
	protected int couleur;
	public final static int NOIR = 1;
	public final static int BLANC = 2;
	public final static int BLEU = 3;
	public final static int ROUGE = 4;
	//protected int coordonnees;
/**
 * permet de savoir si la bille instanciee sur une case est une vraie.
 */
	protected boolean vraieBille;

/**
 * 	permet d'instancier une fausse bille : une vraie bille a besoin de coordonnees
 */
	public Bille(int c){
		this.couleur = c;
		this.vraieBille = false;
		//Indiquez la couleur de la bille a la création via le joueur
	}

/**
 * 	le constructeur d'une vraie bille
 */
	public Bille(int coordonnees) {
		this.vraieBille = true;
		this.coordonnees = coordonnees;
	}
	
	public int getCoordonnees() {
		return this.coordonnees;
	}

	public void setCoordonnees(int coo) {
		this.coordonnees = coo;
	}
	
	public int getCouleur(){
		return couleur;
	}
}