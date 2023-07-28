package contests.persistence;

import contests.model.CategorieVarsta;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import contests.persistence.interfaces.CategorieVarstaRepoInterface;
import contests.persistence.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieVarstaRepo implements CategorieVarstaRepoInterface {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<CategorieVarsta> getAll() {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"CategoriiVarsta\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<CategorieVarsta> categories = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                int ageMin = resultSet.getInt("min");
                int ageMax = resultSet.getInt("max");
                CategorieVarsta category = new CategorieVarsta(id, ageMin, ageMax);
                logger.info("Successfully retrieved category with id " + id + " from database.");
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            logger.error("Error while retrieving categories from database.");
            return null;
        }
    }

    @Override
    public CategorieVarsta getOne(Integer id) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"CategoriiVarsta\" WHERE id=" + String.valueOf(id) + ";")) {
//            ps.setInt(0, id);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            int ageMin = resultSet.getInt("min");
            int ageMax = resultSet.getInt("max");
            CategorieVarsta category = new CategorieVarsta(id, ageMin, ageMax);
//                Log.d("tag", "restul");


            return category;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public boolean saveEntity(CategorieVarsta entity) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"CategoriiVarsta\" (\"min\", \"max\") VALUES(?,?)")) {
            ps.setInt(0, entity.getVarstaMinima());
            ps.setInt(1, entity.getVarstaMaxima());
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"CategoriiVarsta\" WHERE id=?")) {
            ps.setInt(0, id);
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    @Override
    public boolean updateEntity(CategorieVarsta entity) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE \"CategoriiVarsta\" SET (\"min\"=?, \"max\"=?) WHERE id=?")) {
            ps.setInt(0, entity.getVarstaMinima());
            ps.setInt(1, entity.getVarstaMaxima());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    @Override
    public CategorieVarsta getCategorieVarstaByAgeGroup(int ageMin, int ageMax) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"CategoriiVarsta\" WHERE \"min\"=? AND \"max\"=?")) {
            ps.setInt(0, ageMin);
            ps.setInt(1, ageMax);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            int id = resultSet.getInt("id");
            CategorieVarsta category = new CategorieVarsta(id, ageMin, ageMax);
//                Log.d("tag", "restul");


            return category;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }
}
