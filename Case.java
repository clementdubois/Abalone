Stana Katicpublic class Case {
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
	
	/** Si une case est vide (contenu de la case)*/
	public final static byte VIDE = 0;
	
	
/**
 *on garde en memoire les coordonnees des cases adjacentes	
 */
	private byte hd, dd, bd, bg, gg, hg;
	
/**
 * 	fausse bille : false
 */
	
	public Case(byte numero, boolean placerBille) {
		this.numero = numero;
		this.contientBille = placerBille;
		if(placerBille) {
			contientBille = true;
			bille = new Bille(this.numero);
		}
		else {
			contientBille = false;
			bille = new Bille(this.numero); // fausse bille
		}
	}
/**
 * Ce constructeur permet d'attribuer les cases adjacentes a une case lors de sa creation..	
 */
	public Case(byte numero, byte HG, byte GG, byte BG, byte HD, byte DD, byte BD) {
		this.numero = numero;
		this.hg 	= HG;
		this.gg 	= GG;
		this.bg 	= BG;
		this.hd 	= HD;
		this.dd 	= DD;
		this.bd 	= BD;
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
	public void setContient(){
			this.contientBille = false;
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
		if(direction == Mouvement.HD)
			return hd;
		else if(direction == Mouvement.DD)
			return dd;
		else if(direction == Mouvement.BD)
			return bd;
		else if(direction == Mouvement.BG)
			return bg;
		else if(direction == Mouvement.GG)
			return gg;
		else
			return hg;
	}

/*	
	public void appliquerVecteur(Mouvement m) {
// il faut traduire le Mouvement : on applique donc un vecteur sur une coordonnee : c'est une simple recherche de la caseAdjacente correspondante
		bille.setCoordonnees(1); // il suffit ensuite d'inscrire les coordonnees qu'on a recupere dans caseAdjacente.
	}
*/	
}