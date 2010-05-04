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
	private static Partie[] p;
	
	public static void main(String args[]){
		String[] pseudo = new String[2];
		pseudo[0] = "Vincent";
		pseudo[1] = "Bidule";
		Partie[] p = new Partie[1];
		p[0] = new Partie(pseudo);
		
		
/*		
		boolean valide;
		Plateau plateau = new Plateau();
	
		//Jeu de test
		Plateau p = new Plateau();
		p.afficher(); //OK
		//test case adjacente   OK
		for(byte i = 0 ; i < 6 ; i++)
			System.out.println(p.cases[55].getAdjacent(i));

		Mouvement m = new Mouvement((byte)(14), (byte)(15), Mouvement.BD);

		p.effectuer(m);
		m = ia.;
		p.effectuer(m);
		p.afficher();


		//test caseIntermediaire
		System.out.println(Mouvement.DD);
		System.out.println("adjacent : "+m.getPremiere()+" vers : "+m.getVecteur()+" = "+p.cases[m.getPremiere()].getAdjacent(Mouvement.DD));
		byte inter = p.caseIntermediaire(m.getPremiere(), m.getDerniere());
		System.out.println("Inter = "+inter);
		
		try{
		  //do what you want to do before sleeping
		  Thread.currentThread().sleep(4000);//sleep for 1000 ms
		  //do what you want to do after sleeptig
		}
		catch(InterruptedException ie){
		//If this thread was intrrupted by nother thread 
		}		//on rafraichit la fenetre
		f.rafraichir(p);
				
		// OH OUI CA MARCHE ON EST TROP PUISSANTS !!!!!!!!
*/		
		
		// if(valide)
		// 						System.out.println("Mouvement Valide");
		// 					else
		// 						System.out.println("Mouvement Invalide");
		
	}
}