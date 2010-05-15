import java.io.*;

/** Utile pour la sauvegarde et le chargement de partie.*/
class Enregistrement{
		
	/** Les operation de sauvegardes s'effectuent sur une partie*/
	public Enregistrement(){
		
	}
	
	public void sauvegarder(Partie p){
		//flux pour sauvegarder une objet
		ObjectOutputStream oos;

		try {
			oos = new ObjectOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(
								new File("game.txt"))));
			//On sauvegarde la partie dans le fichier
			oos.writeObject(p);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}