

public class Jeu{
	public static void main(String argc[]){
		
			// Jeu de test du déplacement: 
		 			Plateau plateau = new Plateau();
					 plateau.afficher();
					 plateau.deplacer(plateau.GG, 14, 15, -1);
					 plateau.afficher();
	}
}