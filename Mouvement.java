/** Code un mouvement
 * 
 * 
 * 
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
	public final byte HD = 0;
	/** Vecteur a droite*/
	public final byte DD = 1;
	/** Vecteur en bas a droite*/
	public final byte BD = 2;
	/** Vecteur en bas a gauche*/
	public final byte BG = 3;
	/** Vecteur a gauche*/
	public final byte GG = 4;
	/** Vecteur en haut a gauche*/
	public final byte HG = 5;
	
	/** Est ce qu'il s'agit d'un vrai mouvement (ou d'une edition)*/
	public boolean getEstMouvement(){
		return estMouvement;
	}
	
	public byte getPremiere(){
		return premiere;
	}
	
	public byte getDerniere(){
		return derniere;
	}
	
	public byte getVecteur(){
		return vecteur;
	}
	
}