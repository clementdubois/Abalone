import java.util.*;


/** L'arbre qui va servir a stocker une partie avec des variations*/
public class Arbre {
	/** Le premier noeud de l'arbre*/
	public Element racine;
	
	public Arbre(Plateau p){
		//Le noeud racine est forcement la partie a coup 1 (au lancement de la partie).
		racine = new Element(p);
	}
	
	/** Methode d'affichage de l'arbre*/
	public String toString(){
		String message="";
		//On affiche les numCoup de chaque partie
		message += racine.contenu.getNumCoup();
		
		return message;
	}
	
}

/** Les elements de l'arbre qui represente une partie(peut etre un noeud ou une feuille)*/
class Element{
	/** Un noeud contient un etat de la partie en cours*/
	public Plateau contenu;
	/** Permet de savoir s'il s'agit d'un noeud ou d'une feuille*/
	private boolean estFeuille;
	/** s'il s'agit d'un noeud, l'element a un ou plusieur element fils()*/
	public Vector<Element> fils;
	
	public Element(Plateau p){
		estFeuille = true;
		contenu = p;
	}
	
	/** Ajoute un fils a l'element courant et renvoit ce fils
	* @param p la partie a mettre comme element fils
	* @return l'element fils creer
	*/
	public Element ajouterFils(Plateau p){
		//Si c'est le premier fils qu'on ajoute, on indique que ce n'est plus une feuille, on initialise le vector
		if(estFeuille){
			estFeuille = false;
			fils = new Vector<Element>();
		}
		//On ajoute le fils
		fils.add(new Element(p));
		
		return fils.lastElement();
	}
	
}



