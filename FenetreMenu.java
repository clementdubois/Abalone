import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;

public class FenetreMenu extends JFrame{
	
	public static Partie partie;
	public FenetreJeu f;
	private Menu menu;
    private JPanel container = new JPanel();
	private final static int LARGEUR=558;
	private final static int HAUTEUR=675;
	private int x,y ;
	
	public FenetreMenu(Partie partie){
		super();
		this.partie = partie;
        this.setTitle("Abalone");
        this.setSize(LARGEUR,HAUTEUR);
		this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
		container.setLayout(new BorderLayout());
		this.menu = new Menu();
		
		menu.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent event){
		            x = event.getX();
					y = event.getY();
					
					if(x>114 && x<454 && y>227 && y<327){
						f = new FenetreJeu(FenetreMenu.partie);
						dispose(); 
					}	
		     }
		});
		
		container.add(menu,BorderLayout.CENTER);
		this.getContentPane().add(container);
        this.setContentPane(container);
		this.setVisible(true);
	}
	
}