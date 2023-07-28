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
    internal class NumeProbaRepo : INumeProbaRepoInterface
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public bool deleteEntity(int entityId)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "DELETE FROM \"NumeProba\" WHERE \"id\"=@id";
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

        public List<NumeProba>? getAll()
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"NumeProba\"";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                using var reader = sqlCommand.ExecuteReader();
                var numeProbe = new List<NumeProba>();
                while (reader.Read())
                {
                    var numeProba = new NumeProba(reader.GetInt32(0), reader.GetString(1));
                    numeProbe.Add(numeProba);
                }
                con.Close();
                return numeProbe;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public NumeProba? getNumeProbaByName(string name)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"NumeProba\" WHERE \"nume\"=@nume";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nume", name);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                if (reader.Read())
                {
                    var numeProba = new NumeProba(reader.GetInt32(0), reader.GetString(1));
                    con.Close();
                    return numeProba;
                }
                return null;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public NumeProba? getOne(int entityId)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"NumeProbe\" WHERE \"id\"=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityId);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                if (reader.Read())
                {
                    var numeProba = new NumeProba(reader.GetInt32(0), reader.GetString(1));
                    con.Close();
                    return numeProba;
                }
                log.Info("Am ajuns dar nu am gasit dupa id");
                con.Close();
                return null;
            }
            catch (Exception e)
            {
                log.Info("Am ajuns la eroare la nume proba");
                log.Error(e.Message);
                return null;
            }
        }

        public bool saveEntity(NumeProba entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "INSERT INTO \"NumeProba\" (\"nume\") VALUES (@nume)";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nume", entityType.Nume);
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

        public bool updateEntity(NumeProba entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "UPDATE \"NumeProba\" SET \"nume\"=@nume WHERE \"id\"=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nume", entityType.Nume);
                sqlCommand.Parameters.AddWithValue("id", entityType.Id);
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
