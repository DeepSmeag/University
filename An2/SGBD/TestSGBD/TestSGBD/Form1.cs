using Microsoft.Data.SqlClient;
using System.Data;

namespace TestSGBD
{
    public partial class Form1 : Form
    {
        private SqlConnection connection = new SqlConnection(@"Server = LAPTOP\SQLEXPRESS; Database = politie; Integrated Security = true; TrustServerCertificate = true;");
        private SqlDataAdapter manufacturerDataAdapter;
        private SqlDataAdapter cookieDataAdapter;
        private DataSet dataSet;
        private DataGridViewCellEventArgs lastEvent;
        public Form1()
        {
            InitializeComponent();
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            timePicker.Format = DateTimePickerFormat.Time;
            timePicker.ShowUpDown = true;

            if (connection.State == ConnectionState.Closed)
            {
                connection.Open();

            }

            // Create a DataSet to hold the data
            dataSet = new DataSet();



            manufacturerDataAdapter = new SqlDataAdapter("SELECT * FROM Sectoare", connection);
            manufacturerDataAdapter.Fill(dataSet, "Sectoare");


            cookieDataAdapter = new SqlDataAdapter("SELECT * FROM Programari", connection);
            cookieDataAdapter.Fill(dataSet, "Programari");

            // Set up the master-detail relationship between the tables
            DataRelation relation = new DataRelation("SectoareProgramari", dataSet.Tables["Sectoare"].Columns["id"], dataSet.Tables["Programari"].Columns["id_sector"]);
            dataSet.Relations.Add(relation);


            masterDataGridView.AutoGenerateColumns = true;
            masterDataGridView.DataSource = dataSet.Tables["Sectoare"];


            detailDataGridView.AutoGenerateColumns = true;
            detailDataGridView.DataSource = dataSet.Tables["Programari"];
        }
        private void refreshDetail()
        {
            if (lastEvent == null)
            {
                return;
            }
            // Clear the existing data in the Cookies table of the DataSet
            dataSet.Tables["Programari"].Clear();

            // Reload the data from the Cookies table into the DataSet
            cookieDataAdapter.Fill(dataSet, "Programari");

            // Rebind the detail DataGridView to the refreshed Cookies table
            detailDataGridView.DataSource = dataSet.Tables["Programari"];
            masterDataGridView_CellClick(null, lastEvent);
        }
        private void masterDataGridView_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            // Update the detail DataGridView based on the clicked cell in the master DataGridView
            if (e.RowIndex >= 0 && e.ColumnIndex >= 0) // Ensure a valid cell is clicked
            {
                lastEvent = e;
                int selectedManufacturerId = (int)masterDataGridView.Rows[e.RowIndex].Cells["id"].Value;
                DataRow[] matchingRows = dataSet.Tables["Programari"].Select($"id_sector = {selectedManufacturerId}");
                DataTable programariTable = matchingRows.CopyToDataTable();
                detailDataGridView.DataSource = programariTable;
            }
        }
        private void addButton_Click(object sender, EventArgs e)
        {
            // Get the values from the text fields
            int idPolitist = int.Parse(idPolitistTextBox.Text);
            int idSector = int.Parse(idSectorTextBox.Text);
            string date = dateTimePicker.Value.ToShortDateString();
            string time = timePicker.Value.ToLongTimeString();

            // Create an SQL INSERT statement
            string insertQuery = $"INSERT INTO Programari (id_politist, id_sector, data, ora) VALUES ({idPolitist}, {idSector}, '{date}', '{time}')";

            // Execute the INSERT statement
            try
            {
                using (SqlCommand command = new SqlCommand(insertQuery, connection))
                {
                    command.ExecuteNonQuery();
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            //refreshTables();
            refreshDetail();

        }

        private void editButton_Click(object sender, EventArgs e)
        {
            // Get the selected row from the detailDataGridView
            // Get the idcookie value to update
            int idProgramare = int.Parse(idProgramareTextBox.Text);

            int idPolitist = int.Parse(idPolitistTextBox.Text);
            int idSector = int.Parse(idSectorTextBox.Text);
            string date = dateTimePicker.Value.ToShortDateString();
            string time = timePicker.Value.ToLongTimeString();

            // Create an SQL UPDATE statement
            string updateQuery = $"UPDATE Programari SET id_politist = {idPolitist}, id_sector = {idSector}, data = '{date}', ora='{time}' WHERE id = {idProgramare}";

            // Execute the UPDATE statement
            using (SqlCommand command = new SqlCommand(updateQuery, connection))
            {
                command.ExecuteNonQuery();
            }
            refreshDetail();
        }

        private void deleteButton_Click(object sender, EventArgs e)
        {
            // Get the idcookie value to delete
            int idProgramare = int.Parse(idProgramareTextBox.Text);

            // Create an SQL DELETE statement
            string deleteQuery = $"DELETE FROM Programari WHERE id = {idProgramare}";

            // Execute the DELETE statement
            using (SqlCommand command = new SqlCommand(deleteQuery, connection))
            {
                command.ExecuteNonQuery();
            }
            refreshDetail();
        }
    }
}