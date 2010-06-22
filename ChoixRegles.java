import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



/** Fenetre de dialogue au lancement de la partie pour determiner les regles de la partie*/
class ChoixRegles extends JDialog{
	/** Les regles qui vont etre choisies*/
	private Regles regles = new Regles();
	/** Savoir si l'utilisateur a annuler ou a valider*/
	private boolean sendData;
	/** L'icone de la fenetre*/
	JLabel icon, posLabel, ejectLabel;
	/** Bouton de choix du type de partie*/
	private JRadioButton type1, type2, type3, type4;
	/** Bouton de choix de mode d'édition*/
	private JRadioButton edition1, edition2;
	/** Liste*/
	private JComboBox pos, eject;
	
	
	public ChoixRegles(JFrame parent, String title, boolean modal){
			//On appelle le construteur de JDialog correspondant
			super(parent, title, modal);
			//On specifie une taille
			this.setSize(550, 350);
			//La position
			this.setLocationRelativeTo(null);
			//La boîte ne devra pas être redimensionnable
			this.setResizable(false);
			this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			this.initComponent();
			//A l'ouverture de la fenetre l'utilisateur n'a rien envoye
			this.sendData = false;
			
      
	}
	
	/**
		 * Methode appelee pour utiliser la boîte 
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
				panType.setPreferredSize(new Dimension(200, 150));
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
		
		//La partie est elle editable?
				JPanel panEdition = new JPanel();
				panEdition.setBackground(Color.white);
				panEdition.setBorder(BorderFactory.createTitledBorder("Edition de partie"));
				panEdition.setPreferredSize(new Dimension(200, 100));
				edition1 = new JRadioButton("Partie editable");
				edition1.setSelected(true);
				edition2 = new JRadioButton("Partie non editable");
				ButtonGroup gb = new ButtonGroup();
				gb.add(edition1);
				gb.add(edition2);
				panEdition.add(edition1);
				panEdition.add(edition2);
		
		//La position de depart
				JPanel panPos = new JPanel();
				panPos.setBackground(Color.white);
				panPos.setPreferredSize(new Dimension(220, 60));
				panPos.setBorder(BorderFactory.createTitledBorder("Position de depart de la partie"));
				pos = new JComboBox();
				//Liste des fichiers de positions disponibles
					File repertoire = new File("./positions");
					String [] listefichiers;

					listefichiers=repertoire.list();
					for(int i=0;i<listefichiers.length;i++){
						if(listefichiers[i].endsWith(".pos")==true){
							String item = listefichiers[i].substring(0,listefichiers[i].length()-4);
							pos.addItem(item);
							if(item.equals("defaut"))
							  pos.setSelectedItem(item); 
						}
					} 
				// posLabel = new JLabel("Position");
				// 				panPos.add(posLabel);
				panPos.add(pos);
				
				
			//Le nombre de bille à ejecter
				JPanel panEject = new JPanel();
				panEject.setBackground(Color.white);
				panEject.setPreferredSize(new Dimension(220, 60));
				panEject.setBorder(BorderFactory.createTitledBorder("Nombre de bille a ejecter"));
				eject = new JComboBox();
				
				for(int i= 1; i<= 10; i++){
					eject.addItem(i+"");
				}
				
				eject.setSelectedItem("6"); 
				
							// 	
							// ejectLabel = new JLabel("Position");
							// panEject.add(ejectLabel);
				panEject.add(eject);
		
				JPanel content = new JPanel();
				content.setBackground(Color.white);
				content.add(panType);
				content.add(panPos);
				content.add(panEdition);
				content.add(panEject);

				JPanel control = new JPanel();
				JButton okBouton = new JButton("Valider");

				okBouton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {				
						regles = new Regles(getType(), (String)pos.getSelectedItem()+".pos", getEdition(), (String)eject.getSelectedItem());
						sendData = true;
						setVisible(false);
					}

					public int getType(){
						return  (type1.isSelected()) ? 0 : (type2.isSelected()) ? 1 : (type3.isSelected()) ? 2 : (type4.isSelected()) ? 3 : 0;
					}
					
					public int getEdition(){
						return (edition1.isSelected()) ? 0 : (edition2.isSelected()) ? 1 : 0;
					}

				});

				JButton cancelBouton = new JButton("Annuler");
				cancelBouton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						sendData = false;
						setVisible(false);
					}			
				});

				control.add(okBouton);
				control.add(cancelBouton);

				this.getContentPane().add(panIcon, BorderLayout.WEST);
				this.getContentPane().add(content, BorderLayout.CENTER);
				this.getContentPane().add(control, BorderLayout.SOUTH);
		
	}
	
	/** Permet de savoir si l'utilisateur a valide*/
	public boolean aChoisi(){
		return this.sendData;
	}
	
}