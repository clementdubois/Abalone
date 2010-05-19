import java.util.*;
import java.io.*;

public class Regles implements Serializable{
		/** S'agit-il d'un mode ou l'on peut naviguer dans la liste des coups*/
		private boolean navigeable;
		/** Combien de billes faut-il ejecter pour remporter la partie*/
		private byte nbBilleAEjecter;
		/**  S'agit t'il d'un mode d'édition (menu supplémentaire d'édition et possibilite d'avoir un plateau de depart vide)*/
		private boolean edition;
}