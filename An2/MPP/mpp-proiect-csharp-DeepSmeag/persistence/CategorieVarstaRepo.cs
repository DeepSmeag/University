using mpp_proiect_csharp_DeepSmeag.persistence.interfaces;
using mpp_proiect_csharp_DeepSmeag.persistence.utils;
using mpp_proiect_csharp_DeepSmeag.models;
using Npgsql;

namespace mpp_proiect_csharp_DeepSmeag.persistence
{
    public class CategorieVarstaRepo : ICategorieVarstaRepoInterface
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public bool deleteEntity(int entityId)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "DELETE FROM \"CategoriiVarsta\" WHERE id=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityId);
                sqlCommand.Prepare();
                sqlCommand.ExecuteNonQuery();
                con.Close();
                return true;


            }
            catch (Exception)
            {
                return false;
            }
        }

        public List<CategorieVarsta>? getAll()
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"CategoriiVarsta\"";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                using NpgsqlDataReader rdr = sqlCommand.ExecuteReader();
                List<CategorieVarsta> categoriiVarsta = new List<CategorieVarsta>();
                while (rdr.Read())
                {
                    int id = rdr.GetInt32(0);
                    int ageMin = rdr.GetInt32(1);
                    int ageMax = rdr.GetInt32(2);
                    CategorieVarsta categorieVarsta = new CategorieVarsta(id, ageMin, ageMax);
                    categoriiVarsta.Add(categorieVarsta);
                    log.Info("Succes la adaugare");
                }
                return categoriiVarsta;
            }
            catch (Exception) {
                log.Error("Eroare la retrieval");
                return null; 
            }
        }

        public CategorieVarsta? getCategorieVarstaByAgeGroup(int minAge, int maxAge)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"CategoriiVarsta\" WHERE \"min\"=@ageMin AND \"max\"=@ageMax";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("ageMin", minAge);
                sqlCommand.Parameters.AddWithValue("ageMax", maxAge);
                sqlCommand.Prepare();
                using NpgsqlDataReader rdr = sqlCommand.ExecuteReader();

                rdr.Read();
                int id = rdr.GetInt32(0);
                CategorieVarsta categorieVarsta = new CategorieVarsta(id, minAge, maxAge);

                return categorieVarsta;
            }
            catch (Exception) { return null; }
        }

        public CategorieVarsta? getOne(int entityId)
        {
            try{
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"CategoriiVarsta\" WHERE id=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityId);
                sqlCommand.Prepare();
                using NpgsqlDataReader rdr = sqlCommand.ExecuteReader();

                rdr.Read();
                int id = rdr.GetInt32(0);
                int ageMin = rdr.GetInt32(1);
                int ageMax = rdr.GetInt32(2);
                CategorieVarsta categorieVarsta = new CategorieVarsta(id, ageMin, ageMax);

                return categorieVarsta;
            }
            catch (Exception) { return null; }
        }

        public bool saveEntity(CategorieVarsta entityType)
        {
            try {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "INSERT INTO \"CategoriiVarsta\" (\"min\", \"max\") VALUES (@ageMin, @ageMax)";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("ageMin", entityType.VarstaMinima);
                sqlCommand.Parameters.AddWithValue("ageMax", entityType.VarstaMaxima);
                sqlCommand.Prepare();
                sqlCommand.ExecuteNonQuery();
                con.Close();
                return true;
            }
            catch (Exception) { return false; }
        }

        public bool updateEntity(CategorieVarsta entityType)
        {
            try {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "UPDATE \"CategoriiVarsta\" SET \"min\"=@ageMin, \"max\"=@ageMax WHERE id=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityType.Id);
                sqlCommand.Parameters.AddWithValue("ageMin", entityType.VarstaMinima);
                sqlCommand.Parameters.AddWithValue("ageMax", entityType.VarstaMaxima);
                sqlCommand.Prepare();
                sqlCommand.ExecuteNonQuery();
                con.Close();
                return true;
            }
            catch (Exception) { return false; }
        }

        CategorieVarsta? IRepoInterface<CategorieVarsta, int>.getOne(int entityId)
        {
            throw new NotImplementedException();
        }
    }
}
