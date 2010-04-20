
/*
* une classe Bille est-elle vraiment utile finalement, dans le sens ou les vecteurs sont appliques a un tableau et non a des billes ?
*/
public class Bille {
	protected byte HG, GG, BG, HD, DD, BD; // permettrait de se placer du point de vue d'une bille : qu'y a-t-il autour d'elle ? (probablement utile pour ia)
	//protected int numeroBille;
	protected byte couleur;
	public final static byte NOIR = 1;
	public final static byte BLANC = 2;
	public final static byte BLEU = 3;
	public final static byte ROUGE = 4;
	//protected int coordonnees;
/**
 * permet de savoir si la bille instanciee sur une case est une vraie.
 */
	protected boolean vraieBille;

/**
 * 	permet d'instancier une fausse bille : une vraie bille a besoin de coordonnees
 */
	public Bille(byte couleur){
		this.couleur = couleur;
		this.vraieBille = false;
		//Indiquez la couleur de la bille a la création via le joueur
	}

/**
 * 	le constructeur d'une vraie bille
*	@param couleur la couleur de la bille
* @param coordonnees le numero de case de la bille
 
	public Bille(int couleur, int coordonnees) {
		this.vraieBille = true;
		this.coordonnees = coordonnees;
	}*/
	
	/*public int getCoordonnees() {
		return this.coordonnees;
	}*/

	/*public void setCoordonnees(int coo) {
		this.coordonnees = coo;
	}*/
	
	public byte getCouleur(){
		return couleur;
	}
}