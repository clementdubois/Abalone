import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



/** Fenetre de dialogue au lancement de la partie pour déterminer les règles de la partie*/
class ChoixRegles extends JDialog{
	/** Les reglès qui vont être choisies*/
	private Regles regles = new Regles();
	/** Savoir si l'utilisateur à annuler ou a valider*/
	private boolean sendData;
	/** L'icone de la fenetre*/
	JLabel icon;
	/** Bouton de choix du type de partie*/
	private JRadioButton type1, type2, type3, type4;
	
	
	public ChoixRegles(JFrame parent, String title, boolean modal){
			//On appelle le construteur de JDialog correspondant
			super(parent, title, modal);
			//On spécifie une taille
			this.setSize(550, 270);
			//La position
			this.setLocationRelativeTo(null);
			//La boîte ne devra pas être redimensionnable
			this.setResizable(false);
			this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			this.initComponent();
	}
	
	/**
		 * Méthode appelée pour utiliser la boîte 
		 * @return Regles
		 */
		public Regles showChoixRegles(){
			this.sendData = false;
			this.setVisible(true);		
			return this.regles;		
		}
	
	/**
	* Initialise le contenu de la boîte
	*/
	private void initComponent(){
		//Icone
				icon = new JLabel(new ImageIcon("images/play.jpg"));
				JPanel panIcon = new JPanel();
				panIcon.setBackground(Color.white);
				panIcon.setLayout(new BorderLayout());
				panIcon.add(icon, BorderLayout.NORTH);
		
		//Le type de partie
				JPanel panType = new JPanel();
				panType.setBackground(Color.white);
				panType.setBorder(BorderFactory.createTitledBorder("Type de partie"));
				panType.setPreferredSize(new Dimension(440, 60));
				type1 = new JRadioButton("Humain vs Humain");
				type1.setSelected(true);
				type2 = new JRadioButton("Humain vs IA");
				type3 = new JRadioButton("IA vs Humain");
				type4 = new JRadioButton("IA vs IA");
				ButtonGroup bg = new ButtonGroup();
				bg.add(type1);
				bg.add(type2);
				bg.add(type3);
				bg.add(type4);
				panType.add(type1);
				panType.add(type2);
				panType.add(type3);
				panType.add(type4);
		
				
				JPanel content = new JPanel();
				content.setBackground(Color.white);
				content.add(panType);

				JPanel control = new JPanel();
				JButton okBouton = new JButton("Valider");

				okBouton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {				
						regles = new Regles(getType());
						setVisible(false);
					}

					public int getType(){
						return  (type1.isSelected()) ? 0 : (type2.isSelected()) ? 1 : (type3.isSelected()) ? 2 : (type4.isSelected()) ? 3 : 0;
					
					}

				});

				JButton cancelBouton = new JButton("Annuler");
				cancelBouton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}			
				});

				control.add(okBouton);
				control.add(cancelBouton);

				this.getContentPane().add(panIcon, BorderLayout.WEST);
				this.getContentPane().add(content, BorderLayout.CENTER);
				this.getContentPane().add(control, BorderLayout.SOUTH);
		
	}
	
	
	
}