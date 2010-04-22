import java.lang.*;
import java.util.*;

public class Case {
/**
 * On va initialiser une bille sans aucune caracteristique sur chaque case, s'il n'est pas possible de faire autrement.
 * Ce seront de fausses billes, des billes qu'on utilisera pas.
 * D'ou l'utilite de la propriete contientBille.
 */
	private Bille bille;
/**
 * contientBille permet de savoir si la case contient effectivement une bille jouable.
 * C'est lui qui doit etre mis à jour.	
 */
	private boolean contientBille;
/**
 * 	le numero de cette case
 */
	private byte numero;

	/**
	 * 	le numero de la ligne de cette case
	 */
	private byte numLigne;	
	
	/** Si une case est vide (contenu de la case)*/
	public final static byte VIDE = 0;
	
	/** Vecteur en haut a gauche*/
	public static final byte HD = 0;
	/** Vecteur a droite*/
	public static final byte DD = 1;
	/** Vecteur en bas a droite*/
	public static final byte BD = 2;
	/** Vecteur en bas a gauche*/
	public static final byte BG = 3;
	/** Vecteur a gauche*/
	public static final byte GG = 4;
	/** Vecteur en haut a gauche*/
	public static final byte HG = 5;	
	
	/**
	 *on garde en memoire les coordonnees des cases adjacentes	
	 */	
	byte[] vecteurs;

	
 /** Constructeur par defaut*/	
	public Case(byte num){
		this.numero = num;
		this.contientBille = false;
		this.vecteurs = new byte[6];
		this.calculerNumLigne(num);
	}
	
/**	fausse bille : false*/
	public Case(byte num, boolean placerBille) {
		this.numero = num;
		this.contientBille = placerBille;
		this.vecteurs = new byte[6];
		this.calculerNumLigne(num);		
		if(placerBille) {
			contientBille = true;
			bille = new Bille(this.numero);
		}
		else {
			contientBille = false;
			bille = new Bille(this.numero); // fausse bille
		}
	}

	
	/** Récupère le contenu d'une case (vide ou bille blanche ou bille noire)*/
	public byte getContenu(){
		if (!contientBille)
			return this.VIDE;
		else 
			return this.bille.getCouleur();
	}
	
	/** Change le contenu d'une case (vide, bille blanche, bille noire)
	* Pas de parametre: on met une case vide
	*/
	public void setContenu(byte etat){
		if(etat == VIDE)
			this.contientBille = false;
		else{//On change la bille sur la case
			this.contientBille = true;
			this.setBille(new Bille(etat));
		}
			
	}
	
	public void setBille(Bille b) {
		this.bille = b;
		this.contientBille = true;
	}
	
	public Bille getBille() {
			return this.bille;
	}
	
	public byte getNumero() {
		return this.numero;
	}
	
	public void setNumero(byte num) {
		this.numero = num;
	}
	
	public void setContientBille(boolean contient) {
		this.contientBille = contient;
	}
	
	public boolean getContientBille() {
		return this.contientBille;
	}
	
	/** Retourne le numero de la case qui est adjacente a this par le vecteur envoyee.
	*
	* @param vecteur de quelle case adjacente on veut savoir le numero
	* @return le numero de la bonne case adjacente
	*/
	public byte getAdjacent(byte direction){
		return vecteurs[direction];
	}

	
	public byte getNumLigne() {
		return this.numLigne;
	}
/*	
	public void appliquerVecteur(Mouvement m) {
// il faut traduire le Mouvement : on applique donc un vecteur sur une coordonnee : c'est une simple recherche de la caseAdjacente correspondante
		bille.setCoordonnees(1); // il suffit ensuite d'inscrire les coordonnees qu'on a recupere dans caseAdjacente.
	}
*/	
	
	
	/**
	 * @return int le numero de la case opposee.
	 */
	private byte getNumOpposee(byte numCase) {
		if(numCase > 0 && numCase < 62)
			return (byte)(62 - numCase);
		return 0; // ou throws OutOfPlateauException ?
	}	
	
	private byte getVecteurOppose(byte v) {
		if(v<=2)
			return (byte) (v+3);
		return (byte) (v-3);
	}	
	
	public void setVecteursNuls(byte[] v) { // length = 2 => bord. length = 3 => coin
		for(byte b : v) {
			vecteurs[b] = 0;
		}
	}
	
	public void setVecteursNulsOpposes(byte[] v) {
		for(byte b : v) {
			vecteurs[getVecteurOppose(b)] = 0;
		}	
	}
	
	/**
	 * 
	 * @param num
	 * Modifie this.numLigne
	 */
	private void calculerNumLigne (byte num) {
		if(num > 0 && num < 5)
			this.numLigne = 0;
		else if(num <= (5+6))
			this.numLigne = 1;
		else if(num <= (5+6+7))
			this.numLigne = 2;
		else if(num <= (5+6+7+8))
			this.numLigne = 3;
		else if(num <= (5+6+7+8+9))
			this.numLigne = 4;
		else if(num <= (5+6+7+8+9+8))
			this.numLigne = 5;
		else if(num <= (5+6+7+8+9+8+7))
			this.numLigne = 6;
		else if(num <= (5+6+7+8+9+8+7+6))
			this.numLigne = 7;
		else if(num <= 61)
			this.numLigne = 8;
	}
	
}