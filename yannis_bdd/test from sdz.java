import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

public class Connect {

	public static void main(String[] args) {
		
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("DRIVER OK ! ");
			
			String url = "jdbc:postgresql://localhost:5432/abalone";
			String user = "postgres";
			String passwd = "postgres";
			
			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connection effective !");			
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
