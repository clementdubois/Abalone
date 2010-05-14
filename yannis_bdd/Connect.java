
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect{

	
	 //URL de connection 
	private String url = "jdbc:postgresql://localhost:5432/Abalone";
	 //Nom du user
	private String user = "postgres";
	 //Mot de passe du user
	private String passwd = "postgres";
	private static Connection connect;
	//constructeur
	private Connect(){
		try {
			connect = DriverManager.getConnection(url, user, passwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Méthode qui va nous retourner notre instance et la créer si elle n'existe pas...
	 
public static Connection getInstance(){
			if(connect == null){
				new Connect();
				System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
			}
			else{
				System.out.println("CONNEXION SQL EXISTANTE ! ");
			}
			return connect;	
		}

}
