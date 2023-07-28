package contests.persistence;

import contests.model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import contests.persistence.interfaces.ParticipantRepoInterface;
import contests.persistence.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipantRepo implements ParticipantRepoInterface {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public boolean saveEntity(Participant entity) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Participanti\" (\"nameP\", \"age\") VALUES(?, ?)")) {
            ps.setString(1, entity.getNume());
            ps.setInt(2, entity.getVarsta());
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
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"Participanti\" WHERE \"id\" = ?")) {
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
    public boolean updateEntity(Participant entity) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE \"Participanti\" SET \"nameP\" = ?,  \"age\" = ? WHERE \"id\" = ?")) {
            ps.setString(0, entity.getNume());
            ps.setInt(1, entity.getVarsta());
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
    public List<Participant> getAll() {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Participanti\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nameP");
                int age = resultSet.getInt("age");
                Participant participant = new Participant(id, name, age);
//                Log.d("tag", "restul");
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public Participant getOne(Integer id) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Participanti\" WHERE id=?")) {
            ps.setInt(0, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            String name = resultSet.getString("nameP");
            int age = resultSet.getInt("age");
            Participant participant = new Participant(id, name, age);
//                Log.d("tag", "restul");


            return participant;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public Participant getParticipantByName(String name) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Participanti\" WHERE \"nameP\"=?")) {
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            Integer id = resultSet.getInt("id");
            int age = resultSet.getInt("age");
            Participant participant = new Participant(id, name, age);
//                Log.d("tag", "restul");


            return participant;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public List<Participant> getParticipantsByAgeCategory(int ageMin, int ageMax) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Participanti\" WHERE \"age\"<=? AND \"age\">=?")) {
            ps.setInt(0, ageMin);
            ps.setInt(1, ageMax);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nameP");
                int age = resultSet.getInt("age");
                Participant participant = new Participant(id, name, age);
//                Log.d("tag", "restul");
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    public List<Participant> getParticipantsByProba(int idProba) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT \"P\".\"id\", \"P\".\"nameP\", \"P\".age FROM \"Participanti\" \"P\" INNER JOIN \"Inscrieri\" ON \"P\".\"id\"=\"Inscrieri\".\"idParticipant\" WHERE \"Inscrieri\".\"idProba\"=?")) {
            ps.setInt(1, idProba);
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Participant> participants = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("nameP");
                int age = resultSet.getInt("age");
                Participant participant = new Participant(id, name, age);
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            logger.error("Error", e);
        }
        return null;
    }
}