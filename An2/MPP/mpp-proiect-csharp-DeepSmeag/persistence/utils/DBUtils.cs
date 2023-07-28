using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;
using Npgsql;
using System.Data.Common;

namespace mpp_proiect_csharp_DeepSmeag.persistence.utils
{
    public class DBUtils
    {
        private static ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings["dbConnectionString"];
        private static string DBString = settings.ConnectionString;

        public static NpgsqlConnection GetNewConnection()
        {
            return new NpgsqlConnection(DBString);
        }
    }
}
