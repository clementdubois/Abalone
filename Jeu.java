/** 
 * 
*/

public class Jeu {
	/* Test des fonctions : 
	*    Afficher fonctionne
	*    Effectuer fonctionne
	*    GetAdjacent fonctionne
	*    Valider 
	*    Case Intermediaire fonctionne
	*    
	*/
	private Partie p[];	// static ou pas ?
	public static void main(String args[]){
		boolean valide;
		Plateau plateau = new Plateau();
		FenetreJeu f = new FenetreJeu(plateau);
		

	
		//Jeu de test
		Plateau p = new Plateau();
		p.afficher(); //OK
		//test case adjacente   OK
		for(byte i = 0 ; i < 6 ; i++)
			System.out.println(p.cases[55].getAdjacent(i));

		Mouvement m = new Mouvement((byte)(56), (byte)(55), Mouvement.HD);
		p.effectuer(m);
		p.afficher();

		//test caseIntermediaire
		System.out.println(Mouvement.DD);
		System.out.println("adjacent : "+m.getPremiere()+" vers : "+m.getVecteur()+" = "+p.cases[m.getPremiere()].getAdjacent(Mouvement.DD));
		byte inter = p.caseIntermediaire(m.getPremiere(), m.getDerniere());
		System.out.println("Inter = "+inter);
		
		//on rafraichit la fenetre
		f.rafraichir(p);
				
		// OH OUI CA MARCHE ON EST TROP PUISSANTS !!!!!!!!
		
		
		// if(valide)
		// 						System.out.println("Mouvement Valide");
		// 					else
		// 						System.out.println("Mouvement Invalide");
		
	}
}