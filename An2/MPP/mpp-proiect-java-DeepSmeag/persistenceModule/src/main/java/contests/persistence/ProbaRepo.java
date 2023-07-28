package contests.persistence;

import contests.model.CategorieVarsta;
import contests.model.NumeProba;
import contests.model.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import contests.persistence.interfaces.ProbaRepoInterface;
import contests.persistence.utils.DBConnection;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Component
@Repository
public class ProbaRepo implements ProbaRepoInterface {
    private static final Logger logger = LogManager.getLogger();
    private CategorieVarstaRepo categorieVarstaRepo = new CategorieVarstaRepo();
    private NumeProbaRepo numeProbaRepo = new NumeProbaRepo();

    @Override
    public List<Proba> getAll() {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Probe\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Proba> probe = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                int idNumeProba = resultSet.getInt("idNumeProba");
                int idCategorieVarsta = resultSet.getInt("idCategorie");
                CategorieVarsta categorieVarsta = categorieVarstaRepo.getOne(idCategorieVarsta);
                NumeProba numeProba = numeProbaRepo.getOne(idNumeProba);

                Proba proba = new Proba(id, numeProba, categorieVarsta);
                probe.add(proba);
            }
            return probe;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Proba getOne(Integer id) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Probe\" WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int idNumeProba = resultSet.getInt("idNumeProba");
            int idCategorieVarsta = resultSet.getInt("idCategorie");
            CategorieVarsta categorieVarsta = categorieVarstaRepo.getOne(idCategorieVarsta);
            NumeProba numeProba = numeProbaRepo.getOne(idNumeProba);
            Proba proba = new Proba(id, numeProba, categorieVarsta);
            return proba;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean saveEntity(Proba entity) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"Probe\" (\"idNumeProba\", \"idCategorie\") VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getNumeProba().getId());
            ps.setInt(2, entity.getCategorieVarsta().getId());
            ps.executeUpdate();
            ResultSet result = ps.getGeneratedKeys();
            if(result.next()) {
                int id = result.getInt(1);
                entity.setId(id);
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"Probe\" WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateEntity(Proba entity) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE \"Probe\" SET \"idNumeProba\"=?, \"idCategorie\"=? WHERE id=?")) {
            ps.setInt(1, entity.getNumeProba().getId());
            ps.setInt(2, entity.getCategorieVarsta().getId());
            ps.setInt(3, entity.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Proba> getProbeByName(NumeProba numeProba) {
        try (Connection connection = DBConnection.getNewConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Probe\" WHERE \"idNumeProba\"=?")) {
            ps.setInt(0, numeProba.getId());
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Proba> probe = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                int idNumeProba = resultSet.getInt("idNumeProba");
                int idCategorieVarsta = resultSet.getInt("idCategorie");
                CategorieVarsta categorieVarsta = categorieVarstaRepo.getOne(idCategorieVarsta);
                Proba proba = new Proba(id, numeProba, categorieVarsta);
                probe.add(proba);
            }
            return probe;
        } catch (SQLException e) {
            return null;
        }
    }
}
