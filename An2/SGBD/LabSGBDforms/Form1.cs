using System.Data.SqlClient;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Runtime.Remoting.Contexts;
using System.Xml;
using System.Configuration;

namespace LabSGBDforms
{
    public partial class Form1 : Form
    {
        static string connectionString = ConfigurationManager.ConnectionStrings["connection"].ConnectionString;
        SqlConnection connection = new SqlConnection(connectionString);

        SqlDataAdapter dataAdptMaster = new SqlDataAdapter();
        SqlDataAdapter dataAdptDetail = new SqlDataAdapter();
        DataSet datasetMaster = new DataSet();
        DataSet datasetDetail = new DataSet();


        readonly string masterTableName = ConfigurationManager.AppSettings["MasterTableName"];
        readonly string detailTableName = ConfigurationManager.AppSettings["DetailTableName"];
        readonly string detailId = ConfigurationManager.AppSettings["DetailId"];
        readonly int detailNoColumns = Int32.Parse(ConfigurationManager.AppSettings["DetailNumberOfColumns"]);
        readonly int masterNoColumns = Int32.Parse(ConfigurationManager.AppSettings["MasterNumberOfColumns"]);

        readonly List<string> detailColumnNames = ConfigurationManager.AppSettings["DetailColumnNames"].Split(',').ToList();
        readonly List<string> detailColumnInsertParameters = ConfigurationManager.AppSettings["DetailColumnNamesInsertParameters"].Split(',').ToList();
        readonly List<string> masterColumnNames = ConfigurationManager.AppSettings["MasterColumnNames"].Split(',').ToList();

        string lastIndexSelected = "-1";

        public Form1()
        {
            InitializeComponent();
            InitializePanel();
            //Connect to SqlServer through code
            //Form1_load(new object(), new EventArgs());
            masterLabel.Text = masterTableName;
            detailLabel.Text = detailTableName;
        }

        private void InitializePanel()
        {
            int yPos = 0; // Starting y position for the first label and textbox
            int xPos = 10; // Starting x position for the labels

            foreach (string item in detailColumnNames)
            {
                if (item.Equals("id"))
                {
                    continue;
                }
                // Create a new label for the item
                Label label = new Label();
                label.Text = item;
                label.Location = new Point(xPos, yPos);

                // Create a new textbox for the item
                TextBox textBox = new TextBox();
                textBox.Location = new Point(xPos + label.Width + 1, yPos);
                textBox.Text = item;
                textBox.Name = item;

                // Add the label and textbox to the panel
                fieldsPanel.Controls.Add(label);
                fieldsPanel.Controls.Add(textBox);

                yPos += 30;
            }
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            dataGridViewChild.AutoGenerateColumns = true;
            dataGridViewParent.AutoGenerateColumns = true;
            refreshGeneric();

            //refreshTable(masterTable, "tableMaster", dataGridViewParent);
            //refreshTable(detailTable, "tableDetail", dataGridViewChild);
            //addTableRelation(masterTable, detailTable, xmlTables.SelectSingleNode($"//tableMasterPrimary").Attributes["name"].Value, xmlTables.SelectSingleNode($"//tableDetailForeign").Attributes["name"].Value);

        }

        private void dataGridViewChild_CellClick(object sender, DataGridViewCellEventArgs e)
        {




        }


        private void refresh()
        {
            try
            {

                //Deschide conexiunea
                connection.Open();
                //MessageBox.Show("Starea conexiunii: " + connection.State.ToString());


                DataSet dataset = new DataSet();
                SqlDataAdapter parentAdapter = new SqlDataAdapter("SELECT * FROM Admins;", connection);
                SqlDataAdapter childAdapter = new SqlDataAdapter("SELECT * FROM StockOrders;", connection);

                parentAdapter.Fill(dataset, "Admins");
                childAdapter.Fill(dataset, "StockOrders");

                BindingSource parentBS = new BindingSource();
                BindingSource childBS = new BindingSource();

                parentBS.DataSource = dataset.Tables["Admins"];
                childBS.DataSource = dataset.Tables["StockOrders"];
                dataGridViewParent.DataSource = parentBS;
                dataGridViewChild.DataSource = childBS;

                DataColumn parentPK = dataset.Tables["Admins"].Columns["id"];
                DataColumn childFK = dataset.Tables["StockOrders"].Columns["adminId"];

                DataRelation relation = new DataRelation("fk_parent_child", parentPK, childFK);
                dataset.Relations.Add(relation);

                childBS.DataSource = parentBS;
                childBS.DataMember = "fk_parent_child";

                dataGridViewChild.DataSource = childBS;

                connection.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message.ToString());
            }

        }

        private void refreshGeneric()
        {
            dataAdptMaster.SelectCommand = new SqlCommand("SELECT * FROM " + masterTableName, connection);
            datasetMaster.Reset();
            
            dataAdptMaster.Fill(datasetMaster);
            
            dataGridViewParent.DataSource = datasetMaster.Tables[0];

        }

        private void dataGridViewParent_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            try
            {
                int rowIndex = dataGridViewParent.CurrentCell.RowIndex;
                lastIndexSelected = (string) dataGridViewParent.Rows[rowIndex].Cells[0].Value.ToString();
                string id = detailColumnNames[0];
                datasetDetail.Reset();
                string sql = "SELECT * FROM " + detailTableName + " WHERE " + ConfigurationManager.AppSettings["DetailFKName"] + " = " + lastIndexSelected;
                dataAdptDetail.SelectCommand = new SqlCommand(sql, connection);
                datasetDetail.Reset();
                if (dataAdptDetail.SelectCommand != null)
                {
                    dataAdptDetail.Fill(datasetDetail);
                    dataGridViewChild.DataSource = datasetDetail.Tables[0];
                }
                //refreshGeneric();


            }
            catch (Exception)
            {
                MessageBox.Show("Select only one row");
                connection.Close();
            }
        }
        private void buttonAdd_Click(object sender, EventArgs e)
        {
            try
            {

                string sql = ConfigurationManager.AppSettings["DetailInsertQuery"];
                dataAdptDetail.InsertCommand = new SqlCommand(sql, connection);

                for (int i = 1; i < detailNoColumns; i++)
                {
                    TextBox textBox = (TextBox)fieldsPanel.Controls[detailColumnNames[i]];
                    dataAdptDetail.InsertCommand.Parameters.Add(detailColumnInsertParameters[i], SqlDbType.VarChar).Value = textBox.Text;
                }
                //int id = lastIndexSelected;
                //dataAdptDetail.InsertCommand.Parameters.Add(detailColumnNames[0], SqlDbType.Int).Value = id;
                connection.Open();
                dataAdptDetail.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Inserted Succesfull to the Database");
                connection.Close();

                //refreshGeneric();
                dataGridViewParent_CellClick(new object(), new DataGridViewCellEventArgs(0, 0));

            }
            catch (Exception ex)
            {
                if (connection.State == ConnectionState.Open)
                {
                    connection.Close();
                }
                MessageBox.Show(ex.Message);

            }
        }
        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            try
            {
                int rowIndex = dataGridViewChild.CurrentCell.RowIndex;
                String sql = ConfigurationManager.AppSettings["DetailUpdateQuery"] + dataGridViewChild.Rows[rowIndex].Cells[0].Value.ToString();
                dataAdptDetail.UpdateCommand = new SqlCommand(sql, connection);

                for (int i = 1; i < detailNoColumns; i++)
                {
                    TextBox textBox = (TextBox)fieldsPanel.Controls[detailColumnNames[i]];
                    dataAdptDetail.UpdateCommand.Parameters.Add(detailColumnInsertParameters[i], SqlDbType.VarChar).Value = textBox.Text;
                }

                connection.Open();
                dataAdptDetail.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Updated Succesfull to the Database");
                
                connection.Close();

                //refreshGeneric();
                dataGridViewParent_CellClick(new object(), new DataGridViewCellEventArgs(0, 0));
            }
            catch (Exception ex)
            {
                if (connection.State == ConnectionState.Open)
                {
                    connection.Close();
                }
                MessageBox.Show(ex.Message);
            }
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            try
            {
                int rowIndex = dataGridViewChild.CurrentCell.RowIndex;
                string sql = "DELETE FROM " + detailTableName + " WHERE " + ConfigurationManager.AppSettings["DetailPKName"] + " = " + dataGridViewChild.Rows[rowIndex].Cells[0].Value.ToString();
                dataAdptDetail.DeleteCommand = new SqlCommand(sql, connection);
                connection.Open();
                dataAdptDetail.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Deleted Succesfull to the Database");
                connection.Close();
                //refreshGeneric();
                dataGridViewParent_CellClick(new object(), new DataGridViewCellEventArgs(0, 0));
            }
            catch (Exception ex)
            {
                if (connection.State == ConnectionState.Open)
                {
                    connection.Close();
                }
                MessageBox.Show(ex.Message);
            }
        }
    }
}
