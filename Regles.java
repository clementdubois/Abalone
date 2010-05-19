class Regles implements Serializable{
	private:
		/** S'agit-il d'un mode ou l'on peut naviguer dans la liste des coups*/
		boolean navigeable;
		/** Combien de billes faut-il ejecter pour remporter la partie*/
		byte nbBilleAEjecter;
		/**  S'agit t'il d'un mode d'édition (menu supplémentaire d'édition et possibilite d'avoir un plateau de depart vide)*/
		boolean edition;
}