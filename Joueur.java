import java.io.*;
import java.lang.*;

public class Joueur {
	String pseudonyme;
	BufferedReader br;
	public byte score;
	Mouvement coupChoisi;
	
	public Joueur() {
		
	}
	
	public Joueur(String pseudo) {
		this.pseudonyme = pseudo;
		System.out.println(pseudo+" a rejoint la partie.");
		br = new BufferedReader(new InputStreamReader(System.in));
	}
	public Mouvement jouer(/*Mouvement[] coupsPossibles*/) {
		System.out.println("Liste des coups possibles : ");
		/*
		for(Mouvement[] coupsPossibles : coupPossible)
			System.out.println("1ere:"+coupPossible.getPremiere()+" 2e:"+coupPossible.getDerniere()+" vecteur:"+coupPossible.getVecteur());
		*/
		try {
			System.out.print("Premiere bille : ");
			byte premiere = new Byte(br.readLine());
			System.out.print("Seconde bille : ");
			byte seconde = new Byte(br.readLine());
			System.out.print("Vecteur : ");
			byte vecteur = new Byte(br.readLine());
			System.out.println("mouvement choisi : ");
			coupChoisi = new Mouvement(premiere, seconde, vecteur);
			return coupChoisi;		
		}
		catch (IOException e) {
			System.err.println("Joueur::jouer() -> erreur de lecture du mouvement a la ligne de cmd.");	
		}
		return new Mouvement((byte)1, (byte)1, (byte)1);
	}
}
