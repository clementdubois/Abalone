import java.io.*;

/** Utile pour la sauvegarde et le chargement de partie.*/
public class Enregistrement{
		
	/** Les operations de sauvegardes s'effectuent sur une partie*/
	public Enregistrement(){
		
	}
	
	public void sauvegarder(Partie p){
		//flux pour sauvegarder une objet
		ObjectOutputStream oos;

		try {
			oos = new ObjectOutputStream(
						new BufferedOutputStream(
							new FileOutputStream(
								new File("abalone.ab"))));
			//On sauvegarde la partie dans le fichier
			oos.writeObject(p);
			// On ferme le flux
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Partie charger() throws Exception{
		//flux pour charger une objet
		ObjectInputStream ois;
		Partie p;
	
			ois = new ObjectInputStream(
							new BufferedInputStream(
								new FileInputStream(
									new File("abalone.ab"))));

	
				
				p = (Partie)ois.readObject();
								
			ois.close();
			
			return p;
		
	}
	
}