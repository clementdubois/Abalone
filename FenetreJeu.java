import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FenetreJeu extends JFrame{

	Plateau plateau;
	ClickAction listener;
	private Panneau pan;
    private JPanel container = new JPanel();
    // private int compteur = 0;
    // private boolean animated = true;
    // private boolean backX, backY;
    private int x,y ;
    // private Thread t;

	//Les deux variables de taille de fenetre
	private final static int LARGEUR=558;
	private final static int HAUTEUR=650;

    // La declaration pour le menu de la JMenuBar.    
	private JMenuBar menuBar = new JMenuBar();

    private JMenu partie = new JMenu("Partie"),
    		forme     	 = new JMenu("Niveaux"),
    		difficulte   = new JMenu("Difficulte"),
    		aPropos      = new JMenu("Info");

    private JMenuItem 	lancer 		= new JMenuItem("Lancer la partie"),
	    				arreter 	= new JMenuItem("Arreter la partie"),
	    				quitter 	= new JMenuItem("Quitter"),
	    				aProposItem = new JMenuItem("?");


    private JRadioButtonMenuItem 	facile    = new JRadioButtonMenuItem("Facile"),
    			    				moyen     = new JRadioButtonMenuItem("Moyen"),
    			    				difficile = new JRadioButtonMenuItem("Difficile"),
    			    				maitre    = new JRadioButtonMenuItem("Maitre");

    private ButtonGroup bg = new ButtonGroup();

    // La declaration pour le menu contextuel.
	private JPopupMenu jpm   = new JPopupMenu();

    private JMenuItem launch = new JMenuItem("Lancer la partie");	            		
    private JMenuItem stop 	 = new JMenuItem("Arreter la partie");


    // creation des listener globaux.
    private StopPartieListener  stopPartie  = new StopPartieListener();
    private StartPartieListener startPartie = new StartPartieListener();


    // Création de notre barre d'outils.
    private JToolBar toolBar = new JToolBar();

    //Les boutons
    private JButton 	play   = new JButton(new ImageIcon("images/play.jpg")),
    					cancel = new JButton(new ImageIcon("images/stop.jpg")),
    					cpu    = new JButton(new ImageIcon("images/ordinateur.jpg")),
    					player = new JButton(new ImageIcon("images/joueur.jpg")),
    					stat   = new JButton(new ImageIcon("images/stat.jpg"));

    private Color fondBouton = Color.white;
	private Color couleurFond = new Color(63,92,106);

	// public static void main(String[] args){
	// 	FenetreJeu f = new FenetreJeu();
	// 	f.setVisible(true);	
	// }

	/**
	* C'est le constructeur de la fenetre
	*/
    public FenetreJeu(Plateau plateau,ClickAction listener){
			super();
			this.plateau = plateau;
            this.setTitle("Abalone");
            this.setSize(LARGEUR,HAUTEUR);
			this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);
			container.setBackground(couleurFond);
            container.setLayout(new BorderLayout());
			this.pan = new Panneau(plateau,listener);
			pan.repaint();

            //On initialise le menu stop
            stop.setEnabled(false);
            //On affecte les écouteurs
            stop.addActionListener(stopPartie);
    		launch.addActionListener(startPartie);

			/*
    		* On cree et on passe l'écouteur pour afficher le menu contextuel
    		* Creation d'une implementation de MouseAdapter
    		* avec redefinition de la methode adequate
            */
			pan.addMouseListener(new MouseAdapter(){
            	public void mouseReleased(MouseEvent event){
            		//Seulement s'il s'agit d'un clic droit
					if(event.getButton() == MouseEvent.BUTTON3)	
            		{	            	
	            		jpm.add(launch);
	            		jpm.add(stop);

						/**
	            		* La methode qui va afficher le menu.
						*/
	            		jpm.show(pan, event.getX(), event.getY());
            		}
            	}
            });

            container.add(pan, BorderLayout.CENTER);
			this.getContentPane().add(container);
            this.setContentPane(container);
            this.initMenu();
            this.initToolBar();
            this.setVisible(true);            

    }

	/**
	* methode pour rafrachir le plateau avec le nouveau tableau
	*/
	public void rafraichir(Plateau plateau){
		pan.rafraichir(plateau);
		pan.repaint();
        this.initMenu();
	}
	
	/**
	* methode pour rafrachir le plateau avec le nouveau tableau avec billes selectionnees
	*/
	public void rafraichirBS1(int bille1){
		pan.rafraichirBS1(bille1);
		pan.repaint();
        this.initMenu();
	}
	public void rafraichirBS2(int bille2){
		pan.rafraichirBS2(bille2);
		pan.repaint();
        this.initMenu();
	}


	/**
	* methode pour initialiser la barre de raccourcis
	*/
    private void initToolBar(){

    	this.cancel.setEnabled(false);
    	this.cancel.addActionListener(stopPartie);
    	this.cancel.setBackground(fondBouton);
    	this.play.addActionListener(startPartie);
    	this.play.setBackground(fondBouton);

    	this.toolBar.add(play);
    	this.toolBar.add(cancel);
    	this.toolBar.addSeparator();

    	//Ajout des Listeners
    	this.player.setBackground(fondBouton);
    	this.toolBar.add(player);

    	this.cpu.setBackground(fondBouton);
    	this.toolBar.add(cpu);

    	this.stat.setBackground(fondBouton);
    	this.toolBar.add(stat);

    	this.add(toolBar, BorderLayout.NORTH);

    }


	/**
	* methode pour initialiser la barre de menu
	*/
    private void initMenu(){

    	//Menu partie
    	//Ajout du listener pour lancer la partie
    	//Attention, le listener est global
    	lancer.addActionListener(startPartie);
    	//On attribue l'accélerateur c
    	lancer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
    												KeyEvent.CTRL_MASK));
    	partie.add(lancer);


    	// Ajout du listener pour arrêter la partie
    	// listener a changer ici aussi

    	arreter.addActionListener(stopPartie);
    	arreter.setEnabled(false);
    	arreter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
    												  KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
    	partie.add(arreter);

    	partie.addSeparator();
    	quitter.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			System.exit(0);
    		}
    	});
    	partie.add(quitter);

		bg.add(facile);
    	bg.add(difficile);
    	bg.add(moyen);
    	bg.add(maitre);

    	difficulte.add(facile);
    	difficulte.add(moyen);    	
    	difficulte.add(difficile);
    	difficulte.add(maitre);
    	
    	moyen.setSelected(true);
    	
    	forme.add(difficulte);

    	//menu à propos

    	//Ajout de ce que doit faire le "?"
    	aProposItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane jop = new JOptionPane();
				ImageIcon img = new ImageIcon("images/avatar.jpg");

				String mess = "Salut ! \nIci c'est le projet Abalone !\n";

				jop.showMessageDialog(null, mess, "Info", JOptionPane.INFORMATION_MESSAGE, img);

			}    		    		
    	});
    	aPropos.add(aProposItem);

    	// Ajout des menus dans la barre de menus.

    	partie.setMnemonic('P');
    	menuBar.add(partie);

    	forme.setMnemonic('N');
    	menuBar.add(forme);

    	aPropos.setMnemonic('I');
    	menuBar.add(aPropos);


    	// Ajout de la barre de menus sur la fenêtre.

    	this.setJMenuBar(menuBar);
    }

	// private void go(){
	// 	 
	// }

	 /**
	 * Écouteur du menu Lancer.
	*/
	public class StartPartieListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {

			JOptionPane jop = new JOptionPane();			
			int option = jop.showConfirmDialog(null, "Voulez-vous lancer la partie ?", "Lancement de la partie", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

			if(option == JOptionPane.OK_OPTION)
			{
				lancer.setEnabled(false);
				arreter.setEnabled(true);

				//instruction pour le menu contextuel
				launch.setEnabled(false);
				stop.setEnabled(true);

				play.setEnabled(false);
				cancel.setEnabled(true);

				// animated = true;
				// t = new Thread(new PlayPartie());
				// t.start();			
			}
		}		
	}

	 /**
	 * Écouteur du menu Quitter.
	 */
	class StopPartieListener  implements ActionListener{

		public void actionPerformed(ActionEvent e) {


			JOptionPane jop = new JOptionPane();			
			int option = jop.showConfirmDialog(null, "Voulez-vous arreter la partie ?", "Arret de la partie", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if(option != JOptionPane.NO_OPTION && option != JOptionPane.CANCEL_OPTION && option != JOptionPane.CLOSED_OPTION)
			{
				// animated = false;


				// On remplace nos bouton par nous MenuItem
				lancer.setEnabled(true);
				arreter.setEnabled(false);

				// instruction pour le menu contextuel
				launch.setEnabled(true);
				stop.setEnabled(false);

				play.setEnabled(true);
				cancel.setEnabled(false);

			}
		}		
	}	


	 // Lance le thread.
	// class PlayPartie implements Runnable{
	// 	public void run() {
	// 		go();			
	// 	}		
	// }	


}

