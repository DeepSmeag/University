package contests.persistence;

import contests.model.PersoanaOficiu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import contests.persistence.interfaces.PersoanaOficiuInterface;
import contests.persistence.utils.DBConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersoanaOficiuRepo implements PersoanaOficiuInterface {
    private static final Logger logger = LogManager.getLogger();

    public void testing() {

    }

    @Override
    public PersoanaOficiu getOneByUsername(String username) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                PersoanaOficiu admin = em.createNamedQuery("PersoanaOficiu.findPersoanaOficiu", PersoanaOficiu.class)
                        .setParameter("username", username)
                        .getSingleResult();
                et.commit();
                return admin;
            } catch (Exception e) {
                et.rollback();
                e.printStackTrace();
            } finally {
                em.close();
                emf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            System.out.println("GOT HERE3");
        }
        return null;
    }
//    @Override
//    public PersoanaOficiu getOneByUsername(String username) {
//        try {
//            try (Connection connection = DBConnection.getNewConnection();
//                 PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"PersoanaOficiu\" WHERE \"userName\"='" + username + "'")) {
//                ResultSet resultSet = ps.executeQuery();
//                resultSet.next();
//                Integer id = resultSet.getInt("id");
//                String password = resultSet.getString("password");
//                PersoanaOficiu psOf = new PersoanaOficiu(id, username, password);
//
//
//                return psOf;
//            } catch (SQLException e) {
////            throw new RuntimeException(e);
//                logger.error(e);
//                System.out.println("GOT HERE2");
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e);
//            System.out.println("GOT HERE3");
//            return null;
//        }
//    }

    @Override
    public List<PersoanaOficiu> getAll() {
        return null;
    }

    @Override
    public PersoanaOficiu getOne(Integer integer) {
        return null;
    }

    @Override
    public boolean saveEntity(PersoanaOficiu entity) {
        return false;
    }

    @Override
    public boolean deleteEntity(Integer integer) {
        return false;
    }

    @Override
    public boolean updateEntity(PersoanaOficiu entity) {
        return false;
    }
}
