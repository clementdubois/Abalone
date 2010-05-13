import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panneau extends JPanel {

public static final int NBCASES  =  9;
public static final int TAILLEIM = 62;

Plateau plateau;
ClickAction listener;
Image neant,casevide,pionN,pionB,pionBS,pionNS;
int bille1,bille2,bille3;

	/**
	* Constructeur du panel.
	*/
	public Panneau(Plateau plateau,ClickAction listener){
			//initialisation des variables
			this.plateau = plateau;
			this.bille1 = 0;
			this.bille2 = 0;
			//permet de gérer la gestion des click dans la classe ClickAction
			addMouseListener(listener);
			//chargement des images
			Toolkit kit=Toolkit.getDefaultToolkit();
			MediaTracker tracker=new MediaTracker(this);
			this.neant	  = kit.getImage("images/neant.jpg");
			this.casevide = kit.getImage("images/casevide.jpg");
			this.pionB    = kit.getImage("images/pionB.jpg");
			this.pionN    = kit.getImage("images/pionN.jpg");
			this.pionBS   = kit.getImage("images/pionBS.jpg");
			this.pionNS   = kit.getImage("images/pionNS.jpg");
			tracker.addImage(neant,0);
			tracker.addImage(casevide,0);
			tracker.addImage(pionB,0);
			tracker.addImage(pionN,0);
			try {tracker.waitForID(0);}
			catch(InterruptedException e){}


	}

	/**
	* Methode pour afficher le plateau et les billes.
	*/
	public void paintComponent(Graphics g){
		byte i,j;
		int taillePlateau = 1;
		//Pour chaque ligne
		for (i=1; i<=9; i++){
			//pour chaque colonne
			for (j=1; j<=9; j++){
				//on s'occupe d'abord d'afficher le pourtour du plateau
				if ( i == 1 && j == 1 || i == 1 && j == 2 || i == 1 && j == 8 || i == 1 && j == 9 ){
					g.drawImage(this.neant,j*TAILLEIM,i*TAILLEIM,null);
				}

				else if ( i == 2 && j == 1 || i == 2 && j == 8 || i == 2 && j == 9 || i == 3 && j == 1 || i == 3 && j == 9){
					g.drawImage(this.neant,j*TAILLEIM,i*TAILLEIM,null);
				}

				else if ( i == 4 && j == 9 || i == 6 && j == 9 || i == 7 && j == 1 || i == 7 && j == 9){
					g.drawImage(this.neant,j*TAILLEIM,i*TAILLEIM,null);
				}

				else if ( i == 8 && j == 1 || i == 8 && j == 8 || i == 8 && j == 9 || i == 9 && j == 1 || i == 9 && j == 2 || i == 9 && j == 8 || i == 9 && j == 9){
					g.drawImage(this.neant,j*TAILLEIM,i*TAILLEIM,null);
				}
				
				else if(plateau.cases[taillePlateau].getContenu() == Bille.NOIR && i%2 == 1 && taillePlateau < 62){
					//si une bille est selectionnée, on l'a met en surbrillance
					if(plateau.cases[taillePlateau].getNumero() == bille1 || plateau.cases[taillePlateau].getNumero() == bille2 || plateau.cases[taillePlateau].getNumero() == bille3){
						g.drawImage(this.pionNS,(j-1)*TAILLEIM,(i-1)*TAILLEIM,null);
					//sinon on l'affiche normalement	
					}else{
						g.drawImage(this.pionN,(j-1)*TAILLEIM,(i-1)*TAILLEIM,null);
					}	
					taillePlateau += 1;
				}

				else if(plateau.cases[taillePlateau].getContenu() == Bille.NOIR && i%2 == 0 && taillePlateau < 62){
					if(plateau.cases[taillePlateau].getNumero() == bille1 || plateau.cases[taillePlateau].getNumero() == bille2 || plateau.cases[taillePlateau].getNumero() == bille3){
						g.drawImage(this.pionNS,(j-1)*TAILLEIM+(TAILLEIM/2),(i-1)*TAILLEIM,null);
					}else{
						g.drawImage(this.pionN,(j-1)*TAILLEIM+(TAILLEIM/2),(i-1)*TAILLEIM,null);
					}
					taillePlateau += 1;
				}
				
				else if(plateau.cases[taillePlateau].getContenu() == Bille.BLANC && i%2 == 1 && taillePlateau < 62){
					if(plateau.cases[taillePlateau].getNumero() == bille1 || plateau.cases[taillePlateau].getNumero() == bille2 || plateau.cases[taillePlateau].getNumero() == bille3){
						g.drawImage(this.pionBS,(j-1)*TAILLEIM,(i-1)*TAILLEIM,null);
					}else{
						g.drawImage(this.pionB,(j-1)*TAILLEIM,(i-1)*TAILLEIM,null);
					}
					taillePlateau += 1;
				}

				else if(plateau.cases[taillePlateau].getContenu() == Bille.BLANC && i%2 == 0 && taillePlateau < 62){
					if(plateau.cases[taillePlateau].getNumero() == bille1 || plateau.cases[taillePlateau].getNumero() == bille2 || plateau.cases[taillePlateau].getNumero() == bille3){
						g.drawImage(this.pionBS,(j-1)*TAILLEIM+(TAILLEIM/2),(i-1)*TAILLEIM,null);
					}else{
						g.drawImage(this.pionB,(j-1)*TAILLEIM+(TAILLEIM/2),(i-1)*TAILLEIM,null);
					}
					taillePlateau += 1;
				}

				else if(plateau.cases[taillePlateau].getContientBille() == false && i%2 == 1 && taillePlateau < 62){
				 	g.drawImage(this.casevide,(j-1)*TAILLEIM,(i-1)*TAILLEIM,null);
					taillePlateau += 1;
				}

				else if(plateau.cases[taillePlateau].getContientBille() == false && i%2 == 0 && taillePlateau < 62){
				 	g.drawImage(this.casevide,(j-1)*TAILLEIM+(TAILLEIM/2),(i-1)*TAILLEIM,null);
					taillePlateau += 1;
				}
				
			}
		}

		repaint();	

	}

	/**
	* methode pour reinitialiser la variable de plateau
	*/
	public void rafraichir(Plateau plateau){
		this.plateau = plateau;
		this.bille1 = 0;
		this.bille2 = 0;
		this.bille3 = 0;
	}
	
	/**
	* methode pour connaitre le numero de la 1ere bille selectionnee
	*/
	public void rafraichirBS1(int bille1){
		this.bille1 = bille1;
	}
	
	/**
	* methode pour connaitre le numero de la 2eme bille selectionnee
	*/
	public void rafraichirBS2(int bille2, int bille3){
		this.bille2 = bille2;
		this.bille3 = bille3;
	} 

}

