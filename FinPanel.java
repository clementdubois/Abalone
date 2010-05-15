import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FinPanel extends JPanel{
	int gagnant;
	public FinPanel(int gagnant){
		super();
		this.gagnant=gagnant;
	}
	public void paintComponent(Graphics g){
		g.setFont(new Font("SansSerif",Font.PLAIN,25));
		g.drawString("La partie est terminee.",10,50);
		if(gagnant==1){ 
			g.drawString("Le joueur 2 abandonne lachement,",10,80);
			g.drawString("Le joueur 1 remporte la victoire !",10,110);
		}
		else if(gagnant==2){ 
			g.drawString("Le joueur 1 abandonne lachement,",10,80);
			g.drawString("Le joueur 2 remporte la victoire !",10,110);
		}
	}

}
