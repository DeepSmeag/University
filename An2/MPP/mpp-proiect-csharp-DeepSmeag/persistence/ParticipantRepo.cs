using mpp_proiect_csharp_DeepSmeag.models;
using mpp_proiect_csharp_DeepSmeag.persistence.interfaces;
using mpp_proiect_csharp_DeepSmeag.persistence.utils;
using Npgsql;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.persistence
{
    public class ParticipantRepo : IParticipantRepoInterface
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public bool deleteEntity(int entityId)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "DELETE FROM \"Participanti\" WHERE \"id\"=@id";
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

        public List<Participant>? getAll()
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Participanti\"";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                using var reader = sqlCommand.ExecuteReader();
                var participanti = new List<Participant>();
                while (reader.Read())
                {
                    var participant = new Participant(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2));
                    participanti.Add(participant);
                }
                con.Close();
                return participanti;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public Participant? getOne(int entityId)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Participanti\" WHERE \"id\"=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("id", entityId);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                if (reader.Read())
                {
                    var participant = new Participant(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2));
                    con.Close();
                    return participant;
                }
                con.Close();
                return null;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public Participant? getParticipantByName(string name)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Participanti\" WHERE \"nameP\"=@nume";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nume", name);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                if (reader.Read())
                {
                    var participant = new Participant(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2));
                    con.Close();
                    return participant;
                }
                con.Close();
                return null;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public List<Participant>? getParticipantsByAgeCategory(int ageMin, int ageMax)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT * FROM \"Participanti\" WHERE \"age\" BETWEEN @ageMin AND @ageMax";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("ageMin", ageMin);
                sqlCommand.Parameters.AddWithValue("ageMax", ageMax);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                var participanti = new List<Participant>();
                while (reader.Read())
                {
                    var participant = new Participant(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2));
                    participanti.Add(participant);
                }
                con.Close();
                return participanti;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public bool saveEntity(Participant entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "INSERT INTO \"Participanti\" (\"nameP\", \"age\") VALUES (@nameP, @age)";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nameP", entityType.Nume);
                sqlCommand.Parameters.AddWithValue("age", entityType.Varsta);
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

        public bool updateEntity(Participant entityType)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "UPDATE \"Participanti\" SET \"nameP\"=@nameP, \"age\"=@age WHERE \"id\"=@id";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("nameP", entityType.Nume);
                sqlCommand.Parameters.AddWithValue("age", entityType.Varsta);
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
        public List<Participant> getParticipantsByProba(int idProba)
        {
            try
            {
                using var con = DBUtils.GetNewConnection();
                con.Open();
                var sqlString = "SELECT \"P\".\"id\", \"P\".\"nameP\", \"P\".age FROM \"Participanti\" \"P\" INNER JOIN \"Inscrieri\" ON \"P\".\"id\"=\"Inscrieri\".\"idParticipant\" WHERE \"Inscrieri\".\"idProba\"=@idProba";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("idProba", idProba);
                sqlCommand.Prepare();
                using var reader = sqlCommand.ExecuteReader();
                var participanti = new List<Participant>();
                while (reader.Read())
                {
                    var participant = new Participant(reader.GetInt32(0), reader.GetString(1), reader.GetInt32(2));
                    participanti.Add(participant);
                }
                con.Close();
                return participanti;
            }
            catch (Exception e)
            {
                return null;
            }
        }

      
    }
}
