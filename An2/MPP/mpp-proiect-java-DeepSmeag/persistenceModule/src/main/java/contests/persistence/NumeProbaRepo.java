package contests.persistence;

import contests.model.NumeProba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import contests.persistence.interfaces.NumeProbaInterface;
import contests.persistence.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NumeProbaRepo implements NumeProbaInterface {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public List<NumeProba> getAll() {
        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"NumeProbe\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<NumeProba> numeProbe = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                NumeProba numeProba = new NumeProba(id, nume);
                numeProbe.add(numeProba);
            }
            return numeProbe;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public NumeProba getOne(Integer id) {
        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"NumeProbe\" WHERE id=" + String.valueOf(id) + ";")) {
//            ps.setInt(0, id.intValue());
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            String nume = resultSet.getString("nume");
            NumeProba numeProba = new NumeProba(id, nume);

            return numeProba;
        } catch (SQLException e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean saveEntity(NumeProba entity) {
        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO \"NumeProbe\" (nume) VALUES (?)")) {
            ps.setString(0, entity.getNumeProba());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM \"NumeProbe\" WHERE id=?")) {
            ps.setInt(0, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateEntity(NumeProba entity) {
        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE \"NumeProbe\" SET nume=? WHERE id=?")) {
            ps.setString(0, entity.getNumeProba());
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public NumeProba getNumeProbaByName(String name) {
        try(Connection connection = DBConnection.getNewConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"NumeProbe\" WHERE nume=?")) {
            ps.setString(0, name);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            Integer id = resultSet.getInt("id");
            NumeProba numeProba = new NumeProba(id, name);

            return numeProba;
        } catch (SQLException e) {
            return null;
        }
    }
}
