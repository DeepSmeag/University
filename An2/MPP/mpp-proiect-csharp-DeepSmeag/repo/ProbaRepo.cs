using mpp_proiect_csharp_DeepSmeag.domain;
using mpp_proiect_csharp_DeepSmeag.repo.interfaces;
using mpp_proiect_csharp_DeepSmeag.repo.utils;
using Npgsql;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.repo
{
    internal class ProbaRepo : IProbaRepoInterface
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        CategorieVarstaRepo CategorieVRepo;
        NumeProbaRepo NumePRepo;
        public ProbaRepo() { 
            CategorieVRepo = new CategorieVarstaRepo();
            NumePRepo = new NumeProbaRepo();
        }
        public bool deleteEntity(int entityId)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "DELETE FROM \"Probe\" WHERE \"id\"=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityId);
                sqlCommand.Prepare();
                sqlCommand.ExecuteNonQuery();
                con.Close();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }

        public List<Proba>? getAll()
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Probe\"";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                using var reader = sqlCommand.ExecuteReader();
                var probe = new List<Proba>();
                while (reader.Read())
                {
                    int categorieVarstaId = reader.GetInt32(1);
                    int numeProbaId = reader.GetInt32(2);
                    NumeProba numeProba = NumePRepo.getOne(numeProbaId);
                    //log.Info(numeProba.ToString());
                    CategorieVarsta categorieVarsta = CategorieVRepo.getOne(categorieVarstaId);
                    log.Info(categorieVarsta.ToString());
                    var proba = new Proba(reader.GetInt32(0), numeProba, categorieVarsta);
                    probe.Add(proba);
                }
                con.Close();
                return probe;
            }
            catch (Exception e)
            {
                log.Error(e.Message);
                return null;
            }
        }

        public Proba? getOne(int entityId)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Probe\" WHERE \"id\"=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityId);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                Proba? proba = null;
                while (reader.Read())
                {
                    int categorieVarstaId = reader.GetInt32(1);
                    int numeProbaId = reader.GetInt32(2);
                    NumeProba numeProba = NumePRepo.getOne(numeProbaId);
                    CategorieVarsta categorieVarsta = CategorieVRepo.getOne(categorieVarstaId);
                    proba = new Proba(reader.GetInt32(0), numeProba, categorieVarsta);
                    con.Close();
                    return proba;
                }
                con.Close();
                return proba;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public List<Proba>? getProbeByAgeCategory(CategorieVarsta categorieVarsta)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Probe\" WHERE \"categorie_varsta\"=@categorie_varsta";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("categorie_varsta", categorieVarsta.Id);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                var probe = new List<Proba>();
                while (reader.Read())
                {
                    int categorieVarstaId = reader.GetInt32(1);
                    int numeProbaId = reader.GetInt32(2);
                    NumeProba numeProba = NumePRepo.getOne(numeProbaId);
                    var proba = new Proba(reader.GetInt32(0), numeProba, categorieVarsta);
                    
                    probe.Add(proba);
                }
                con.Close();
                return probe;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public List<Proba>? getProbeByName(NumeProba numeProba)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Probe\" WHERE \"nume_proba\"=@nume_proba";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nume_proba", numeProba.Id);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                var probe = new List<Proba>();
                while (reader.Read())
                {
                    int categorieVarstaId = reader.GetInt32(1);
                    int numeProbaId = reader.GetInt32(2);
                    CategorieVarsta categorieVarsta = CategorieVRepo.getOne(categorieVarstaId);
                    var proba = new Proba(reader.GetInt32(0), numeProba, categorieVarsta);
                    probe.Add(proba);
                }
                con.Close();
                return probe;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public bool saveEntity(Proba entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "INSERT INTO \"Probe\" (\"nume_proba\", \"categorie_varsta\") VALUES (@nume_proba, @categorie_varsta)";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nume_proba", entityType.numeProba.Id);
                sqlCommand.Parameters.AddWithValue("categorie_varsta", entityType.categorieVarsta.Id);
                sqlCommand.Prepare();
                sqlCommand.ExecuteNonQuery();
                con.Close();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }

        public bool updateEntity(Proba entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "UPDATE \"Probe\" SET \"nume_proba\"=@nume_proba, \"categorie_varsta\"=@categorie_varsta WHERE \"id\"=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityType.Id);
                sqlCommand.Parameters.AddWithValue("nume_proba", entityType.numeProba.Id);
                sqlCommand.Parameters.AddWithValue("categorie_varsta", entityType.categorieVarsta.Id);
                sqlCommand.Prepare();
                sqlCommand.ExecuteNonQuery();
                con.Close();
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
}
