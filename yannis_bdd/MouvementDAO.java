import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MouvementDAO extends DAO<Mouvement> {
	
	 String requete;
	
    public MouvementDAO(Connection conn,String request) {
        super(conn);
        this.requete = request;
}

public boolean create(Mouvement obj) {
        return false;
}

public boolean delete(Mouvement obj) {
        return false;
}

public Mouvement find(int partie_id,branche_id) {
        
        Mouvement mouvement = new Mouvement();                
        
        try {
                ResultSet result = this.connect        .createStatement(
                                                                                                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                                                                ResultSet.CONCUR_READ_ONLY
                                                                                ).executeQuery(this.requete);
                if(result.first())
                        mouvement = new Mouvement(result.getString("Mouvement_nom"), result.getString("Mouvement_password"));
                
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return mouvement;
}

public boolean update(Mouvement obj) {
        return false;
}


}
