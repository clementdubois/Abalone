import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FenetreJeu extends JFrame{

	Plateau plateau;
	Partie partie;
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
	private final static int HAUTEUR=675;

    // La declaration pour le menu de la JMenuBar.    
	private JMenuBar menuBar = new JMenuBar();

    private JMenu lancement  = new JMenu("Lancement"),
    		      forme      = new JMenu("Niveaux"),
    			  difficulte = new JMenu("Difficulte"),
    		      aPropos    = new JMenu("Info");

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
	//fenetres des scores
	JTextField scoreJ1,scoreJ2;
	//scores
	int entScoreJ1,entScoreJ2;
	//joueur actuel
	int joueurActuel;
	JTextField text1,text2;

	/**
	* C'est le constructeur de la fenetre
	*/
    public FenetreJeu(Partie partie,ClickAction listener){
			super();
			this.partie = partie;
			this.plateau = partie.plateau;
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

			joueurActuel = partie.getJoueurActuel();

			Box scoreBox = Box.createHorizontalBox();
			
			text1 = new JTextField("Score Joueur 1 (N):");
			if(joueurActuel == 1){
				text1.setForeground(Color.GREEN);
			}
			text1.setEditable(false);

			entScoreJ1 = partie.getScore(1);
			String score1 = Integer.toString(entScoreJ1); 
			scoreJ1 = new JTextField(score1);
			scoreJ1.setEditable(false);

			text2 = new JTextField("Score Joueur 2 (B):");
			if(joueurActuel == 2){
				text1.setForeground(Color.GREEN);
			}
			text2.setEditable(false);

			entScoreJ2 = partie.getScore(2);
			String score2 = Integer.toString(entScoreJ2); 
			scoreJ2 = new JTextField(score2);
			scoreJ2.setEditable(false);

			scoreBox.add(text1);
			scoreBox.add(scoreJ1);
			scoreBox.add(text2);
			scoreBox.add(scoreJ2);

			//initialisation finale de la fenetre
			container.add(scoreBox,BorderLayout.SOUTH);
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
		joueurActuel = partie.getJoueurActuel();
		if(joueurActuel == 1){
			text1.setForeground(Color.GREEN);
		}else{
			text1.setForeground(Color.BLACK);
		}
		if(joueurActuel == 2){
			text2.setForeground(Color.GREEN);
		}else{
			text2.setForeground(Color.BLACK);
		}
		entScoreJ1 = partie.getScore(1);
		entScoreJ2 = partie.getScore(2);
		scoreJ1.setText(Integer.toString(entScoreJ1));
		scoreJ2.setText(Integer.toString(entScoreJ2));
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
	public void rafraichirBS2(int bille2, int bille3){
		pan.rafraichirBS2(bille2,bille3);
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
    	lancement.add(lancer);


    	// Ajout du listener pour arrêter la partie
    	// listener a changer ici aussi

    	arreter.addActionListener(stopPartie);
    	arreter.setEnabled(false);
    	arreter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
    												  KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
    	lancement.add(arreter);

    	lancement.addSeparator();
    	quitter.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent event){
    			System.exit(0);
    		}
    	});
    	lancement.add(quitter);

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

    	lancement.setMnemonic('L');
    	menuBar.add(lancement);

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


				// On remplace nos boutons par nous MenuItem
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

