import java.util.*;
import java.io.*;

/** Objet contenant les regles choisi pour une partie grace à la fenetre choixRegles*/
public class Regles implements Serializable{
		/** S'agit-il d'un mode ou l'on peut naviguer dans la liste des coups*/
		private boolean isNavigable;
		/** Combien de billes faut-il ejecter pour remporter la partie*/
		private byte nbBilleAEjecter;
		/**  S'agit t'il d'un mode d'edition (menu supplementaire d'edition et possibilite d'avoir un plateau de depart vide)*/
		private boolean isEdition;
		/** Le type de partie*/
		private int typeJoueur;
		/** La position de depart*/
		private Case[] position;
		
		
		//---------------------------Constructeurs-----
		public Regles(){}
		public Regles(int type, String pos){
			this.typeJoueur = type;
			//On charge le fichier choisit
			File fichierPosition = new File("./positions/"+pos);
			//On charge les donnees du fichier
			try{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichierPosition));
				position = ((Case[])ois.readObject());
				ois.close();
				//On affiche pour voir si on ne  s'est pas trompe
				for(int i=0; i<62; i++){
					System.out.println(i+" :: "+position[i]);
				}
			}catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
			}
			
			
			
		}
		
		//---------------------------ToString---------
		public String toString(){
			String str;
			
			str = "typeJoueur : "+this.typeJoueur;
			
			return str;
		}
		
		//-------------------------Accesseurs---------
			public boolean getIsNavigable(){
				return isNavigable;
			}
			public void setIsNavigable(boolean b){
				isNavigable = b;
			}
			
			public byte getNbBilleAEjecter(){
				return nbBilleAEjecter;
			}
			public void setNavigable(byte b){
				nbBilleAEjecter = b;
			}
			
			public boolean getIsEdition(){
				return isEdition;
			}
			public void setIsEdition(boolean b){
				isEdition = b;
			}
			
				public int getTypeJoueur(){
					return typeJoueur;
				}
				public void setTypeJoueur(int b){
					typeJoueur = b;
				}
		//-------------------------FIN ACCESSEUR------
}