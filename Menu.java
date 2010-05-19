import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JPanel {

Image menu,case1,case2,case3;
int select1,select2,select3;


	public Menu(){
			//initialisation des variables
			this.select1 = 0;
			this.select2 = 0;
			this.select3 = 0;
			//permet de gérer la gestion des click dans la classe ClickAction
			// addMouseListener(listener);
			//chargement des images
			Toolkit kit=Toolkit.getDefaultToolkit();
			MediaTracker tracker=new MediaTracker(this);
			this.menu = kit.getImage("images/menu.jpg");
			//les trackers c'est la vie !
			tracker.addImage(menu,0);
			try {tracker.waitForID(0);}
			catch(InterruptedException e){}

	}

	public void paintComponent(Graphics g){
		g.drawImage(this.menu,0,0,null);
	}
	
	
}