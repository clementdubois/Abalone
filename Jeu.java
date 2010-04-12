/** 
*  Classe principale.
*  Comment faire des tests dans le main (commentaires à faire évoluer avec l'avancement du projet)
*  Creer un nouveau plateau de jeu :   Plateau plateau = new Plateau();
*  Afficher l'etat du plateau avant le deplacement : plateau.afficher();
*  Tester un deplacement : plateau.deplacer(Plateau.DIRECTION, numBille1, numBille2, numBille3);   =>si moins de 3 billes concerné par le deplacement mettre les valeur de bille3 et bille2 a -1
*  Afficher l'etat du plateau après le deplacement : plateau.afficher();
*/

public class Jeu{
	public static void main(String argc[]){
			int valide;
			// Jeu de test du déplacement: 
		 			Plateau plateau = new Plateau();
					 plateau.afficher();
					valide = plateau.validerMouvement(Plateau.DH, 14,-1,-1);
					 plateau.deplacer(Plateau.DH, 14, -1, -1);
					plateau.afficher();
					 plateau.deplacer(Plateau.DB, 7, -1, -1);
					plateau.afficher();
					 plateau.deplacer(Plateau.GH, 55, -1, -1);
					plateau.afficher();
					 plateau.deplacer(Plateau.DH, 58, -1, -1);
					plateau.afficher();
					
					 plateau.deplacer(Plateau.GH, 48, -1, -1);
					plateau.afficher();
					 plateau.deplacer(Plateau.DB, 1, -1, -1);
					plateau.afficher();
					
					
					if(valide == Plateau.VALIDE)
						System.out.println("VALIDE");
					else
						System.out.println("INVALIDE");
	}
}