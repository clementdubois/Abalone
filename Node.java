import java.util.*;
import java.io.*;
import java.lang.*;
/*
	Classe utilisee par l'IA.
	Permet de garder le lien entre mouvement et score
*/
class Node implements Serializable{
	private double score;
	private Node parent;
	private Vector<Node> fils;	
	private Node root;
	private Mouvement mouvement;
	private Plateau plateau;
	private boolean isRoot;
	
	public boolean real;

	
	public Node(Mouvement m, Plateau p) {
		this.score 		= 0;
		this.root		= new Node(false);
		this.fils 		= new Vector<Node>();
		this.mouvement 	= m;
		this.plateau 	= p;
		this.isRoot		= true;
		this.parent		= new Node(false);;
	}
		
	public Node(Node n) {
		this.score 		= n.getScore();
		this.parent 	= n;
//		this.root 		= n.getRoot();
		this.isRoot		= n.isRoot();
		this.fils 		= n.getFils();
		this.mouvement 	= n.getMouvement();
		this.plateau 	= new Plateau(n.getPlateau());
	}

/**
 * Permet d'instancier des Nodes n'existant pas.
 */ 
	public Node(boolean faux) {
		this.real = faux;
	}
	
	/*
		SET & GET
	*/
	
	public void setScore(double s) {
		this.score = s;
	}
	public double getScore() {
		return this.score;
	}
	
	public void setMouvement(Mouvement m) {
		this.mouvement = m;
	}
	public Mouvement getMouvement() {
		return this.mouvement;
	}
	
	public void setPlateau(Plateau p) {
		this.plateau = new Plateau(p);
	}
	public Plateau getPlateau() {
		return this.plateau;
	}
	
	public void setParent(Node p) {
		this.parent = p;
		p.addFils(this);
	}
	public Node getParent() {
		return this.parent;
	}
	
	public void addFils(Node n) {
		this.fils.add(n);
		n.parent = this;
	}
	public void delFils(Node n) {
		this.fils.remove(n);
		n.parent = new Node(false);
	}
	public Vector<Node> getFils() {
		return this.fils;
	}
/* il faut gerer les ancetres.	
	public void setRoot(boolean isRoot) {
		this.root = isRoot;
	}
*/
	public boolean isRoot() {
		return this.isRoot;
	}
	
	public boolean isLeaf() {
		if(this.fils.size() > 0)
			return false;
		return true;
	}
	
	/*

	*/
	public Node getRoot() {
		Node root = new Node(this.getParent());
		if(!root.isRoot())
			return root.getRoot();
		return root;
	}
	
	public String toString() {
		this.plateau.afficher();
		return "isRoot"+this.isRoot+"\n"+"score:"+this.score+"\n"+"mouvement"+this.mouvement+"\n";
	}
	
	/* 
		IA
		
	public Node negamaxv1(int profondeur, Node n) {
		System.out.println("negamaxv1");
		if(profondeur == 0) {
			System.out.println("->avant fonctionEvaluation : "+this.score);
			return n.fonctionEvaluation(Math.random());
//			System.out.println("->apres fonctionEvaluation : "+this.score);
		}
		else {
			System.out.println("->avant negamax : "+this.score);
			for(int i = 0; i<10; i++)
				return(max(n, -negamaxv1(profondeur-1, new Node(this))));
		}
		System.out.println("->node renvoye : "+n.score);
		System.out.println("->this : "+this.score);
		return n;
	}
	
	


	public Node negamax(int profondeur) { // utiliser NegaScout si j'arrive à classer les meilleurs coups en premier
		System.out.println("negamax()");	
		
		if(profondeur == 0) {
			this.fonctionEvaluation();
		}
		
		Vector<Mouvement> mouvementsValides = new Vector<Mouvement>();
		mouvementsValides = this.plateau.mouvementsValides(this.plateau.getJoueurActuel());
		for(Mouvement m : mouvementsValides) {
//			Plateau p = new Plateau(this.plateau);
			this.plateau.effectuer(m);
			this.plateau.setJoueur();
			this.plateau.setNumCoup();
			this.plateau.setScore();
			this.max(negamax(profondeur-1));
		}
//		return this;
	}
	
	private Node max(Node a, Node b) {
		System.out.println("max()");	
		if(this.score > -n.getScore())
			;
		this.score 		= n.getScore();
		this.parent 	= n.getParent();
		this.root 		= n.getRoot();
		this.fils 		= n.getFils();
		this.mouvement 	= n.getMouvement();
		this.plateau 	= n.getPlateau();
	}

	private Node fonctionEvaluation(double random) {
		System.out.println("fonctionEvaluation()");
		Mouvement m = this.mouvement;
		Plateau p 	= new Plateau(this.plateau);
		this.score = 1.0*random;
		return this;
	}
*/
}