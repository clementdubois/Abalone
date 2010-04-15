import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Panneau extends JPanel {

	private int posX = -50;
    private int posY = -50;
    private int drawSize = 50;

    private Color couleurFond = Color.white;
    
    //Le compteur de rafraîchissement
    private int increment = 0;


    public void paintComponent(Graphics g){
        //affectation de la couleur de fond    
    		g.setColor(couleurFond);
    		g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
    }  
    
    public int getDrawSize(){
    	return drawSize;
    }
    
    public int getPosX() {
            return posX;
    }

    public void setPosX(int posX) {
            this.posX = posX;
    }

    public int getPosY() {
            return posY;
    }

    public void setPosY(int posY) {
            this.posY = posY;
    }
    
}
