using mpp_proiect_csharp_DeepSmeag.models;
using mpp_proiect_csharp_DeepSmeag.persistence.interfaces;
using mpp_proiect_csharp_DeepSmeag.persistence.utils;
using Npgsql;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace mpp_proiect_csharp_DeepSmeag.persistence
{
    public class PersoanaOficiuRepo : IPersoanaOficiuRepoInterface
    {
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private static NpgsqlConnection? con;
        public PersoanaOficiuRepo()
        {
            con = DBUtils.GetNewConnection();
        }
        public bool deleteEntity(int entityId)
        {
            throw new NotImplementedException();
        }

        public List<PersoanaOficiu>? getAll()
        {
            throw new NotImplementedException();
        }

        public PersoanaOficiu? getOne(int entityId)
        {
            throw new NotImplementedException();
        }

        public PersoanaOficiu? getOneByUsername(string username)
        {
            try
            {
                //using var con = DBUtils.GetNewConnection();
                if (con!.State == ConnectionState.Closed)
                {
                    con.Open();
                }
                var sqlString = "SELECT * FROM \"PersoanaOficiu\" WHERE \"userName\"=@username";
                using var sqlCommand = new NpgsqlCommand(sqlString, con);
                sqlCommand.Parameters.AddWithValue("username", username);
                using var reader = sqlCommand.ExecuteReader();
                if (!reader.HasRows)
                {
                    return null;
                }
                reader.Read();

                var persOficiu = new PersoanaOficiu(reader.GetInt32(0), username, reader.GetString(2));
                log.Info("Persoana oficiu gasita cu succes");
                return persOficiu;
            }
            catch (Exception e)
            {
                log.Error("Eroare la gasirea persoanei oficiu");
                return null;
            }
        }

        public bool saveEntity(PersoanaOficiu entityType)
        {
            throw new NotImplementedException();
        }

        public bool updateEntity(PersoanaOficiu entityType)
        {
            throw new NotImplementedException();
        }
    }
}
