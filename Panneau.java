import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panneau extends JPanel {

public static final int NBCASES  =  9;
public static final int TAILLEIM = 62;

Image neant,casevide,pionN,pionB,pionBS,pionNS;

	public Panneau(){
	
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
	
	
	public void paintComponent(Graphics g){
		int i,j;
		//Pour chaque ligne
		for (i=0;i<NBCASES;i++){
			//pour chaque colonne
			for (j=0;j<NBCASES;j++){
				if (false){
					g.drawImage(this.neant,j*TAILLEIM,i*TAILLEIM,null);
				}

				else if(i >= 3 && i < 6 && i%2 == 0){
					g.drawImage(this.casevide,j*TAILLEIM,i*TAILLEIM,null);
				}
				
				else if(i >= 3 && i < 6 && i%2 == 1){
					g.drawImage(this.casevide,j*TAILLEIM+(TAILLEIM/2),i*TAILLEIM,null);
				}

				else if(i < 3 && i%2 == 0){
					g.drawImage(this.pionB,j*TAILLEIM,i*TAILLEIM,null);
				}
				else if(i < 3 && i%2 == 1){
					g.drawImage(this.pionB,j*TAILLEIM+(TAILLEIM/2),i*TAILLEIM,null);
				}

				else if(i >= 6 && i%2 == 0){
					g.drawImage(this.pionN,j*TAILLEIM,i*TAILLEIM,null);
				}
				
				else if(i >= 6 && i%2 == 1){
					g.drawImage(this.pionN,j*TAILLEIM+(TAILLEIM/2),i*TAILLEIM,null);
				}

				else if(false){
					g.drawImage(this.pionBS,j*TAILLEIM,i*TAILLEIM,null);
				}

				else if(false){
					g.drawImage(this.pionNS,j*TAILLEIM,i*TAILLEIM,null);
				}
			}
		}
		
		repaint();	
	
	}
	
}
