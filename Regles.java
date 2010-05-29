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
		
		
		//---------------------------Constructeurs-----
		public Regles(){}
		public Regles(int type){
			this.typeJoueur = type;
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