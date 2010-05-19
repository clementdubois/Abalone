import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;

public class Menu extends JPanel {

Image menu,select1,select2,select3;
int curseur;


	public Menu(){
			//initialisation des variables
			this.curseur = 0;
			
			//chargement des images
			Toolkit kit=Toolkit.getDefaultToolkit();
			MediaTracker tracker=new MediaTracker(this);
			this.menu = kit.getImage("images/menu.jpg");
			this.select1 = kit.getImage("images/select1.jpg");
			this.select3 = kit.getImage("images/select3.jpg");
			//les trackers c'est la vie !
			tracker.addImage(menu,0);
			tracker.addImage(select1,0);
			tracker.addImage(select3,0);
			try {tracker.waitForID(0);}
			catch(InterruptedException e){}
	}


	public void paintComponent(Graphics g){
		g.drawImage(this.menu,0,0,null);
		if(curseur == 1) g.drawImage(this.select1,114,148,null);
		else if(curseur == 3){ g.drawImage(this.select3,114,432,null);
		}
	}
	
	public void rafraichirMenu(int curseur){
		this.curseur = curseur;
		repaint(); 
	}
	
}