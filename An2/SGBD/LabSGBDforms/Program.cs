using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LabSGBDforms
{
    internal static class Program
    {
        static string connectionString = "Server=LAPTOP\\SQLEXPRESS;Database=MusicShop;Integrated Security=true;TrustServerCertificate=true;";

        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            //Application.EnableVisualStyles();
            //Application.SetCompatibleTextRenderingDefault(false);
            //Application.Run(new Form1());

            Thread thread1 = new Thread(() => ExecuteStoredProcedure("SP_Deadlock1"));
            Thread thread2 = new Thread(() => ExecuteStoredProcedure("SP_Deadlock2"));

            thread1.Start();
            thread2.Start();

            thread1.Join();
            thread2.Join();
        }

        static void ExecuteStoredProcedure(string storedProcedureName)
        {
            int retryCount = 5;

            while (retryCount > 0)
            {
                try
                {
                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {
                        SqlCommand command = new SqlCommand(storedProcedureName, connection);
                        command.CommandType = System.Data.CommandType.StoredProcedure;

                        connection.Open();
                        command.ExecuteNonQuery();
                    }

                    Console.WriteLine($"Executed {storedProcedureName} successfully.");
                    break;
                }
                catch (SqlException ex)
                {
                    if (ex.Number == 1205) // SQL Server error code for deadlock
                    {
                        retryCount--;
                        Console.WriteLine($"{storedProcedureName} failed due to a deadlock. Retrying... Remaining attempts: {retryCount}");
                    }
                    else
                    {
                        Console.WriteLine($"An error occurred: {ex.Message}");
                        break;
                    }
                }
            }
        }
    }
}
