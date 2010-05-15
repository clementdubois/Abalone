/** Demarre les graphisme et lance le moteur de la partie*/
class DemarrerPartie{
	//
	public ClickAction listener;
	/** MODE SANS SERVEUR*/
	public FenetreJeu f;
	/** La partie a lancer*/
	public Partie partie;
	
	public DemarrerPartie(){
		//On lance le moteur de la partie
		partie = new Partie();
		//On lance l'ecouteur de jeu 
		listener = new ClickAction(this);
		//On lance la fenetre graphique
		f = new FenetreJeu(partie,listener);
	}
}