import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;



public class membreDAO extends DAO<Membre>
{

        public membreDAO(Connection conn) {
                super(conn);
        }

        public boolean create(Membre obj) {
                return false;
        }

        public boolean delete(Membre obj) {
                return false;
        }

        public Membre find(int id) {
                
                Membre membre = new Membre();                
                
                try {
                        ResultSet result = this.connect        .createStatement(
                                                                                                        ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                                                                        ResultSet.CONCUR_READ_ONLY
                                                                                        ).executeQuery(
                                                                                                        "SELECT * FROM membre WHERE membre_id = " + id
                                                                                        );
                        if(result.first())
                                Membre = new Membre(id, result.getString("membre_nom"), result.getString("membre_password"));
                        
                } 
                catch (SQLException e) {
                        e.printStackTrace();
                }
                return Membre;
        }

        public boolean update(Membre obj) {
                return false;
        }

}
