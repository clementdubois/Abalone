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
	private int numero;
	
/**
 *on garde en memoire les coordonnees des cases adjacentes	
 */
	private int hg, gg, bg, hd, dd, bd;
	
/**
 * 	fausse bille : false
 */
	
	public Case(int numero, boolean placerBille) {
		this.numero = numero;
		this.contientBille = placerBille;
		if(placerBille) {
			contientBille = true;
			bille = new Bille(this.numero);
		}
		else {
			contientBille = false;
			bille = new Bille(); // fausse bille
		}
	}
/**
 * Ce constructeur permet d'attribuer les cases adjacentes a une case lors de sa creation..	
 */
	public Case(int numero, int HG, int GG, int BG, int HD, int DD, int BD) {
		this.numero = numero;
		this.hg 	= HG;
		this.gg 	= GG;
		this.bg 	= BG;
		this.hd 	= HD;
		this.dd 	= DD;
		this.bd 	= BD;
	}
	
	public boolean setBille(Bille b) {
		this.bille = b;
		return true;
	}
	
	public Bille getBille() {
		return bille;
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public void contiensBille() {
		contientBille = true;
	}
	
	public void contiensVide() {
		contientBille = false;
	}	
	
	public boolean contientBille() {
		return contientBille;
	}

/*	
	public void appliquerVecteur(Mouvement m) {
// il faut traduire le Mouvement : on applique donc un vecteur sur une coordonnee : c'est une simple recherche de la caseAdjacente correspondante
		bille.setCoordonnees(1); // il suffit ensuite d'inscrire les coordonnees qu'on a recupere dans caseAdjacente.
	}
*/	
}