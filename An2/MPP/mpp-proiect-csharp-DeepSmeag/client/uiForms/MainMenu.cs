using mpp_proiect_csharp_DeepSmeag.domain;
using mpp_proiect_csharp_DeepSmeag.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace mpp_proiect_csharp_DeepSmeag.uiForms
{
    public partial class MainMenu : Form
    {
        private static ServiceFacade serviceFacade = ServiceFacade.getInstance();
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private List<InscrieriProba> modelInscrieri = new List<InscrieriProba>();
        private List<Participant> modelParticipanti = new List<Participant>();

        private int currentContest = -1;
        public MainMenu()
        {
            InitializeComponent();
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            LoginMenu loginMenu = new LoginMenu();
            loginMenu.Show();
            this.Hide();
        }

        private void refreshContests()
        {
            modelInscrieri = serviceFacade.getInscrieriMerged();

            var contestsDS = modelInscrieri.Select(inscriereProba => new
            {
                NumeProba = inscriereProba.numeProba,
                CatVarsta = inscriereProba.categorieVarsta,
                Inscrisi = inscriereProba.nrParticipanti
            }).ToList();

            contestsDataGrid.DataSource = contestsDS;
            //This will assign the datasource. All the columns you listed will show up, and every row
            //of data in the list will populate into the DataGridView.

        }
        private void refreshParticipants()
        {
            if (currentContest == -1)
            {
                modelParticipanti = serviceFacade.getAllParticipants();
            }
            else
            {
                modelParticipanti = serviceFacade.getByProba(currentContest);
            }


            var participantsDS = modelParticipanti.Select(participant => new
            {
                Nume = participant.Nume,
                Varsta = participant.Varsta
            }).ToList();

            participantsDataGrid.DataSource = participantsDS;
        }
        private void MainMenu_Load(object sender, EventArgs e)
        {
            refreshContests();
            refreshParticipants();
        }

        private void contestsDataGridClick(object sender, DataGridViewCellEventArgs e)
        {
            //Get row of clicked cell
            var row = contestsDataGrid.Rows[e.RowIndex];
            Console.WriteLine(row);
            log.Info(row);
            //Get id of that contest
            currentContest = modelInscrieri.ElementAt(e.RowIndex).Id;
            refreshParticipants();
        }

        private void registerButton_Click(object sender, EventArgs e)
        {
            string participantName = nameTextBox.Text;
            int age = int.Parse(ageTextBox.Text);

            Participant participant = serviceFacade.getByName(participantName);

            List<Inscriere> inscrieri = null;
            int numInscrieri = 0;
            bool needsRegistering = false;
            int criteriuVarsta;
            if (participant == null)
            {
                needsRegistering = true;
                criteriuVarsta = age;
            }
            else
            {
                criteriuVarsta = participant.Varsta;
                inscrieri = serviceFacade.getByParticipantId(participant.Id);
                numInscrieri += inscrieri.Count;
            }
            numInscrieri += contestsDataGrid.SelectedRows.Count;
            if (numInscrieri > 2)
            {
                MessageBox.Show("Too many registrations", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }
            var selectedIndexes = new List<int>();

            //for each row in the list of selected rows, get their index and store in this list
            foreach (DataGridViewRow row in contestsDataGrid.SelectedRows)
            {
                selectedIndexes.Add(modelInscrieri[row.Index].Id);
            }
            foreach (int index in selectedIndexes)
            {
                bool isClashing = false;
                if (inscrieri != null)
                {
                    foreach (Inscriere inscriere in inscrieri)
                    {
                        if (index == inscriere.IdProba)
                        {
                            isClashing = true;
                        }
                    }
                }
                string[] criterii = modelInscrieri[index - 1].categorieVarsta.Trim().Split('-');
                if (criteriuVarsta < int.Parse(criterii[0]) || criteriuVarsta > int.Parse(criterii[1]))
                {
                    isClashing = true;
                }
                if (!isClashing)
                {
                    if (needsRegistering)
                    {
                        serviceFacade.addParticipant(participantName, age);
                        participant = serviceFacade.getByName(participantName);
                    }
                    serviceFacade.addInscriere(participant.Id, index);
                    MessageBox.Show("Registered participant");
                    refreshParticipants();
                }
                else
                {
                    MessageBox.Show("Cannot register participant", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            }

        }
    }
}
