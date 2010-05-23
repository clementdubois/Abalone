import java.lang.*;
import java.util.*;
import java.io.*;

public class Case implements Serializable{
/**
 * contientBille permet de savoir si la case contient effectivement une bille jouable.
 * C'est lui qui doit etre mis à jour.	
 */
	//private boolean contientBille;
/**
 * 	le numero de cette case
 */
	private byte numero;
	
 /** La couleur de la bille, NOIR ou BLANC ou VIDE*/
	protected byte contenu;
	
	/** Constante pour une bille noir*/
	public final static byte NOIR = 1;
	/** Constante pour une bille blanche*/
	public final static byte BLANC = 2;

	/**
	 * 	le numero de la ligne de cette case
	 */
	private byte numLigne;	
	
	/** Si une case est vide (contenu de la case)*/
	public final static byte VIDE = 0;
	/** Si la case est le trou elle doit avoir une valeur particuliere */
	public final static byte NEANT = -1;
	/** Vecteur en haut a droite*/
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
	 *on garde en memoire les coordonnees des cases adjacentes	de la case
	 */	
	byte[] vecteurs;

	
 /** Initialisation de la case*/	
	public Case(byte num){
		this.numero = num;
		//this.contientBille = false;
		this.vecteurs = new byte[6];
		this.calculerNumLigne(num);
	}
	
	/** Chargement d'une case */
	public Case(byte num, byte cont){
		this.numero = num;
		this.contenu = cont;
		this.vecteurs = new byte[6];
		this.calculerNumLigne(num);
	}
	


	//-------------------------------------------ACCESSEURS----------------------------------------
			/** Recupere le contenu d'une case (vide ou bille blanche ou bille noire)
			* @return VIDE si la case est vide, NOIR si c'est une bille noir, BLANC si c'est une bille blanche
			*/
			public byte getContenu(){
					return this.contenu;
			}
	
			/** Change le contenu d'une case (vide, bille blanche, bille noire)
			* Pas de parametre: on met une case vide
			*/
			public void setContenu(byte etat){
				this.contenu = etat;
			
			}
	    
	    /** @return le numero de la case*/
			public byte getNumero() {
				return this.numero;
			}
	  
			/** Retourne le numero de la case qui est adjacente a this par le vecteur envoyee.
			*
			* @param direction de quelle case adjacente on veut savoir le numero
			* @return le numero de la bonne case adjacente
			*/
			public byte getAdjacent(byte direction){
				return vecteurs[direction];
			}
			
			/** Retourne vrai si le parametre est une case adjacente a this*/
			public boolean estAdjacent(byte numAdjacent){
				//On teste tous les vecteurs
				for(byte i=0; i<6; i++){
					//Si la case adjacente est numAdjacent alors on renvoi true
					if(getAdjacent(i) == numAdjacent)
						return true;
				}
				
				//On a pas trouvé la case adjacente
				return false;
			}

	    /** @return Le numero de la ligne ou est situee la case*/
			public byte getNumLigne() {
				return this.numLigne;
			}
	
	/**
	 * Retourne le numero de case oppose a celui donne en parametre
	 * @param numCase Le numero de la case dont on veut obtenir son numero oppose
	 * @return le numero de la case opposee.
	 */
	private byte getNumOpposee(byte numCase) {
		if(numCase > 0 && numCase < 62)
			return (byte)(62 - numCase);
		return 0; // ou throws OutOfPlateauException ?
	}	
	/** Retourne le vecteur oppose (de sens inverse) a celui passe en parametre
	* @param v le vecteur de reference.
	*  @return le vecteur oppose a v.
	*/
	private byte getVecteurOppose(byte v) {
		if(v<=2)
			return (byte) (v+3);
		return (byte) (v-3);
	}	
	/** Initialise les cases adjacentes a une case qui sont en fait le trou 
	* @param v La liste des vecteur qui menent au trou
	*/
	public void setVecteursNuls(byte[] v) { // length = 2 => bord. length = 3 => coin
		for(byte b : v) {
			vecteurs[b] = 0;
		}
	}
	
	/** Initialise les cases adjacentes a une case qui sont en fait le trou. 
	* @param v La liste des vecteur dont les opposes menent au trou.
	*/
	public void setVecteursNulsOpposes(byte[] v) {
		for(byte b : v) {
			vecteurs[getVecteurOppose(b)] = 0;
		}
	}
	
	/**
	 * Calcul le numero d'une ligne a partir du numero d'une case.
	 * @param num Le numero de la case
	 */
	private void calculerNumLigne (byte num) {
		if(num > 0 && num <= 5)
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
	
	public String toString(){
		String message="";
		
		message += "Numero : "+numero+" ;;";
		message += "Contenu : "+contenu;
		
		return message;
	}
	
}