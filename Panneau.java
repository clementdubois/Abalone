import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panneau extends JPanel {

public static final int NBCASES  =  9;
public static final int TAILLEIM = 62;

Plateau plateau;

Image neant,casevide,pionN,pionB,pionBS,pionNS;

	/**
	* Constructeur du panel.
	*/
	public Panneau(Plateau plateau){
		
			this.plateau = plateau;
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
		for (i=1; i<=NBCASES; i++){
			//pour chaque colonne
			for (j=1; j<=NBCASES; j++){
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
				 	g.drawImage(this.pionN,(j-1)*TAILLEIM,(i-1)*TAILLEIM,null);
					taillePlateau += 1;
				}
				
				else if(plateau.cases[taillePlateau].getContenu() == Bille.NOIR && i%2 == 0 && taillePlateau < 62){
				 	g.drawImage(this.pionN,(j-1)*TAILLEIM+(TAILLEIM/2),(i-1)*TAILLEIM,null);
					taillePlateau += 1;
				}
				
				else if(plateau.cases[taillePlateau].getContenu() == Bille.BLANC && i%2 == 1 && taillePlateau < 62){
				 	g.drawImage(this.pionB,(j-1)*TAILLEIM,(i-1)*TAILLEIM,null);
					taillePlateau += 1;
				}
				
				else if(plateau.cases[taillePlateau].getContenu() == Bille.BLANC && i%2 == 0 && taillePlateau < 62){
				 	g.drawImage(this.pionB,(j-1)*TAILLEIM+(TAILLEIM/2),(i-1)*TAILLEIM,null);
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
	}
	
}
