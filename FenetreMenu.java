import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;

public class FenetreMenu extends JFrame{
	
	private Menu menu;
    private JPanel container = new JPanel();
	private final static int LARGEUR=558;
	private final static int HAUTEUR=692;
	private int x,y,click;
	
	/** Pour charger une partie*/
			//Les partie seront chargees dans le dossier sauvegarde
			JFileChooser fileChooser = new JFileChooser("sauvegarde/");
			//Les filtres
			AbFileFilter filtre = new AbFileFilter(".ab", "Fichier Abalone");
			File file;
	
	public FenetreMenu(){
		super();
		//Filtre pour le chargement
		this.fileChooser.addChoosableFileFilter(filtre);
		
		// this.click = 0;
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
					
					//Demarrer une partie
					 if(x>113 && x<454 && y>146 && y<245 && event.getButton() == MouseEvent.BUTTON1){
						// if(click == 0){
						// 							menu.rafraichirMenu(1);
						// 							click = 1;
						// 						}	
						// 						else if(click == 1){
							Partie p = new Partie();
							// click = 0;
							menu.rafraichirMenu(0); 
							dispose(); 
						// }
					 }
					//Charger une partie
					if(x>113 && x<454 && y>280 && y<379 && event.getButton() == MouseEvent.BUTTON1){
						//File chooser pour choisir une partie à charger
						//On ouvre la fenetre de jeu avec la partie à charger
						if(fileChooser.showOpenDialog(null) ==JFileChooser.APPROVE_OPTION){
							file = fileChooser.getSelectedFile();
							if(fileChooser.getFileFilter().accept(file))
							{
								try {

									ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
									//On charge la partie
		
									Partie partie = new Partie((Partie)ois.readObject());
									ois.close();
									//On rafraichit pour voir la partie chargee
									partie.f.rafraichir(partie.plateau);
									partie.f.arbre.setModel(partie.coups);
									partie.coups.reload();


								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (IOException e1) {
									e1.printStackTrace();
								} catch (ClassNotFoundException e2) {
									e2.printStackTrace();
								}
							}
							else{
								JOptionPane alert = new JOptionPane();
								alert.showMessageDialog(null, "Erreur d'extension de fichier ! \nVotre chargement a échoué !", "Erreur", JOptionPane.ERROR_MESSAGE);
							}
						}
						
						menu.rafraichirMenu(0); 
						dispose();
					}
				
					
					//Quitter
					if(x>113 && x<454 && y>430 && y<529 && event.getButton() == MouseEvent.BUTTON1){
						// if(click == 0){
						// 							menu.rafraichirMenu(3);
						// 							click = 1;
						// 						}	
						// 						else if(click == 1){
							// click = 0;
							menu.rafraichirMenu(0);
							System.exit(0); 
						// }
					 }
					
					if(event.getButton() == MouseEvent.BUTTON3){	
						menu.rafraichirMenu(0);            	
			 			// click = 0;
					}	
		     }			
		
		});

		container.add(menu,BorderLayout.CENTER);
		this.getContentPane().add(container);
        this.setContentPane(container);
		this.setVisible(true);
	}
	
}