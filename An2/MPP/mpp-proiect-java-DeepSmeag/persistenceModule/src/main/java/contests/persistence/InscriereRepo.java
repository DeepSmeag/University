package contests.persistence;

import contests.model.Inscriere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import contests.persistence.interfaces.InscriereRepoInterface;
import contests.persistence.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscriereRepo implements InscriereRepoInterface {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public List<Inscriere> getAll() {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Inscrieri\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Inscriere> inscrieri = new ArrayList<>();
            while (resultSet.next()) {
                int idParticipant = resultSet.getInt("idParticipant");
                int idProba = resultSet.getInt("idProba");
                Inscriere inscriere = new Inscriere(idParticipant, idProba);
                inscrieri.add(inscriere);
            }
            return inscrieri;
        } catch (SQLException e) {
            return null;

        }
    }

    @Override
    public Inscriere getOne(Integer integer) {
        return null;
    }

    @Override
    public boolean saveEntity(Inscriere entity) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Inscrieri\" VALUES (?,?)")) {
            ps.setInt(1, entity.getIdParticipant());
            ps.setInt(2, entity.getIdProba());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer integer) {
        return false;
    }


    public boolean deleteEntity(int idParticipant, int idProba) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"Inscrieri\" WHERE idParticipant=? AND idProba=?")) {
            ps.setInt(0, idParticipant);
            ps.setInt(1, idProba);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateEntity(Inscriere entity) {
        return false;
    }

    @Override
    public Inscriere getInscriereByParticipantAndProba(int idParticipant, int idProba) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Inscrieri\" WHERE \"idParticipant\" = ? AND \"idProba\"= ? ")) {
            ps.setInt(0, idParticipant);
            ps.setInt(1, idProba);
            ResultSet resultSet = ps.executeQuery();


            Inscriere inscriere = new Inscriere(idParticipant, idProba);
            return inscriere;
        } catch (SQLException e) {
            return null;
        }
    }

    public int getNumRegistrations(Integer idProba) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(\"idParticipant\") FROM \"Inscrieri\" WHERE \"idProba\"= " + String.valueOf(idProba))) {
//            ps.setInt(0, idProba.intValue());
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return -1;
        }
    }

    public List<Inscriere> getInscrieriByParticipantId(Integer idParticipant) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Inscrieri\" WHERE \"idParticipant\"= ?")){
            ps.setInt(1, idParticipant.intValue());
            ResultSet resultSet = ps.executeQuery();
            List<Inscriere> inscrieri = new ArrayList<>();
            while(resultSet.next()){
                int idProba = resultSet.getInt("idProba");
                Inscriere inscriere = new Inscriere(idParticipant, idProba);
                inscrieri.add(inscriere);
            }
            return inscrieri;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
