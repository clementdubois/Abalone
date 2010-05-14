import java.sql.Connection;
import Connect.java;

public abstract class DAO<T> {
        
        protected Connection connect = null;
        
        /**
         * Constructeur
         * @param conn
         */
        public DAO(Connection conn){
                this.connect = conn;
        }
        
        /**
         * M�thode de cr�ation
         * @param obj
         * @return
         */
        public abstract boolean create(T obj);
        /**
         * M�thode pour effacer
         * @param obj
         * @return
         */
        public abstract boolean delete(T obj);
        /**
         * M�thode de mise � jour
         * @param obj
         * @return
         */
        public abstract boolean update(T obj);
        /**
         * M�thode de recherche des informations
         * @param id
         * @return
         */
        public abstract T find(int id);
}
