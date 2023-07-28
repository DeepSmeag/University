using Microsoft.Data.SqlClient;

namespace LabsSGBD
{
    internal class Program
    {
        static void Main(string[] args)
        {
            string connectionString = @"Server=LAPTOP\SQLEXPRESS;Database=MusicShop;
                Integrated Security=true;TrustServerCertificate=true;";
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                Console.WriteLine("Starea conexiunii: {0}", connection.State);
                connection.Open();
                Console.WriteLine("Starea conexiunii: {0}", connection.State);
                // Do stuff here
                SqlCommand selectCommand = new SqlCommand("SELECT * FROM Admins", connection);
                SqlDataReader reader = selectCommand.ExecuteReader();
                while (reader.Read())
                {
                    Console.WriteLine("{0} {1}", reader["name"], reader["email"]);
                    
                }
                reader.Close();
                SqlCommand readerStockOrder = new SqlCommand("SELECT * FROM StockOrders", connection);
                SqlDataReader readerOrder = readerStockOrder.ExecuteReader();
                while (readerOrder.Read())
                {
                    Console.WriteLine("{0} {1} {2}", readerOrder["id"], readerOrder["dateCreated"], readerOrder["adminId"]);
                }
                readerOrder.Close();
                connection.Close();


                //SqlCommand updateCommand = new SqlCommand("UPDATE Bauturi SET pret=@pret WHERE nume=@nume;",
                //        connection);
                //updateCommand.Parameters.AddWithValue("@pret", 60.26F);
                //updateCommand.Parameters.AddWithValue("@nume", "Ballantine's");
                //int updateRowCount = updateCommand.ExecuteNonQuery();
                Console.WriteLine("Starea conexiunii: {0}", connection.State);
            }
        }
    }
}