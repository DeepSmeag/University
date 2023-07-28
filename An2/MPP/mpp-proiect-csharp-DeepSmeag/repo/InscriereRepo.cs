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
    internal class InscriereRepo : IInscriereRepoInterface
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public bool deleteEntity(int idParticipant, int idProba)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "DELETE FROM \"Inscrieri\" WHERE \"idParticipant\"=@idParticipant AND \"idProba\"=@idProba";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("idParticipant", idParticipant);
                sqlCommand.Parameters.AddWithValue("idProba", idProba);
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
        public bool deleteEntity(int id)
        {
            return false;
        }

        public List<Inscriere>? getAll()
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Inscrieri\"";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                using var reader = sqlCommand.ExecuteReader();
                var inscrieri = new List<Inscriere>();
                while (reader.Read())
                {
                    var inscriere = new Inscriere(reader.GetInt32(0), reader.GetInt32(1));
                    inscrieri.Add(inscriere);
                }
                con.Close();
                return inscrieri;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public Inscriere? getInscriereByParticipantAndProba(int idParticipant, int idProba)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Inscrieri\" WHERE \"idParticipant\"=@idParticipant AND \"idProba\"=@idProba";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("idParticipant", idParticipant);
                sqlCommand.Parameters.AddWithValue("idProba", idProba);
                using var reader = sqlCommand.ExecuteReader();
                if (reader.Read())
                {
                    var inscriere = new Inscriere(reader.GetInt32(0), reader.GetInt32(1));
                    con.Close();
                    return inscriere;
                }
                con.Close();
                return null;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public Inscriere? getOne(int entityId)
        {
            return null;
        }

        public bool saveEntity(Inscriere entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "INSERT INTO \"Inscrieri\" VALUES (@idParticipant, @idProba)";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("idParticipant", entityType.IdParticipant);
                sqlCommand.Parameters.AddWithValue("idProba", entityType.IdProba);
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

        public bool updateEntity(Inscriere entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "UPDATE \"Inscrieri\" SET \"idParticipant\"=@idParticipant, \"idProba\"=@idProba WHERE \"idParticipant\"=@idParticipant AND \"idProba\"=@idProba";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("idParticipant", entityType.IdParticipant);
                sqlCommand.Parameters.AddWithValue("idProba", entityType.IdProba);
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

        internal int getNumRegistrations(int idProba)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT COUNT(\"idParticipant\") FROM \"Inscrieri\" WHERE \"idProba\"=@idProba";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("idProba", idProba);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                reader.Read();
                int num = reader.GetInt32(0);

                con.Close();
                return num;
            }
            catch (Exception e)
            {
                return -1;
            }
        }

        internal List<Inscriere> getInscrieriByParticipantId(int idParticipant)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Inscrieri\" WHERE \"idParticipant\"=@idParticipant";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("idParticipant", idParticipant);
                using var reader = sqlCommand.ExecuteReader();
                var inscrieri = new List<Inscriere>();
                while (reader.Read())
                {
                    var inscriere = new Inscriere(reader.GetInt32(0), reader.GetInt32(1));
                    inscrieri.Add(inscriere);
                }
                con.Close();
                return inscrieri;
            }
            catch (Exception e)
            {
                log.Error(e);
                return null;
            }
        }
    }
}
