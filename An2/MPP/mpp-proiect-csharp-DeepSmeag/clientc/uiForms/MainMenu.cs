using mpp_proiect_csharp_DeepSmeag.models;

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
using Grpc.Net.Client;
using Grpc.Core;
using Rpcprotocol;
using Microsoft.VisualBasic;
using System.Threading.Channels;
using Channel = Grpc.Core.Channel;

namespace mpp_proiect_csharp_DeepSmeag.uiForms
{
    public partial class MainMenu : Form
    {
        //private static ServiceFacade serviceFacade = ServiceFacade.getInstance();
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        private List<InscrieriProba> modelInscrieri = new List<InscrieriProba>();
        private List<models.Participant> modelParticipanti = new List<models.Participant>();
        PersoanaOficiu user;
        RpcWorkerService.RpcWorkerServiceClient server;
        Channel channel;
        Task listenForUpdatesTask;
        

        private int currentContest = -1;
        public MainMenu()
        {
            InitializeComponent();

        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            LoginMenu loginMenu = new LoginMenu();
            loginMenu.setServer(server);
            Console.WriteLine(user.GetHashCode());
            LogoutResponse response = server.Logout(new LogoutRequest { Username = user.username, Password = user.password });
            if (response.Response.Equals("ok"))
            {

                //channel.ShutdownAsync();
                listenForUpdatesTask.Dispose();
                loginMenu.Show();
                this.Hide();
                return;

            }
            MessageBox.Show("ERROR on logout");
        }

        private void MainMenu_Load(object sender, EventArgs e)
        {
            //updateParticipantsModel();
            //updateContestsModel();
            channel = new Channel("localhost:50051", ChannelCredentials.Insecure);
            //initListener();
            listenForUpdatesTask = Task.Run(async () =>
            {
                using (var call = server.ListenForUpdates(new LoginRequest { Password=user.password,Username=user.username}))
                {
                    // Read the server updates
                    while (await call.ResponseStream.MoveNext())
                    {
                        UpdateResponse update = call.ResponseStream.Current;
                        //MessageBox.Show("UPDATE");
                        updateParticipantsModel();
                        updateContestsModel();
                        Thread.Sleep(300);
                    }

                }
            });
            
        }
        //private async void initListener()
        //{
        //    using (var call = server.ListenForUpdates())
        //    {
        //        //// Notify the server that the client is ready to receive updates
        //        //await call.RequestStream.WriteAsync(new Request());

        //        //// Finish the request
        //        //await call.RequestStream.CompleteAsync();

        //        // Read the server updates
        //        while (await call.ResponseStream.MoveNext())
        //        {
        //            UpdateResponse update = call.ResponseStream.Current;
                    
        //        }
        //    }
            


        //}

        private void contestsDataGridClick(object sender, DataGridViewCellEventArgs e)
        {
            //Get row of clicked cell
            var row = contestsDataGrid.Rows[e.RowIndex];
            Console.WriteLine(row);
            log.Info(row);
            //Get id of that contest
            currentContest = modelInscrieri.ElementAt(e.RowIndex).Id;
            updateParticipantsModel();
        }

        private void registerButton_Click(object sender, EventArgs e)
        {
            string participantName = nameTextBox.Text;
            int age = int.Parse(ageTextBox.Text);

            try
            {
                InscrieriProba contest = null;
                foreach (InscrieriProba inscriere in modelInscrieri)
                {
                    if (inscriere.Id == currentContest)
                    {
                        contest = inscriere;
                    }
                }
                Contest contestRpc = new Contest { Id = contest.Id, NumeProba = contest.numeProba, CategorieVarsta = contest.categorieVarsta, NrParticipanti = contest.nrParticipanti };

                //server.attemptRegisterParticipant(participantName, age, inscrieriProba);
                Response response = server.AttemptRegisterParticipant(new RegisterParticipantRequest { Name = participantName, Age = age, Contest = contestRpc });
                if (response.OkResponse != null)
                {
                    MessageBox.Show("Registered successfully", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);

                }
                else
                {
                    throw new Exception();
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("You did something wrong", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

        }
        public void setServer(RpcWorkerService.RpcWorkerServiceClient server)
        {
            this.server = server;
        }
        public void setUser(PersoanaOficiu user)
        {
            this.user = user;
        }
        public void updateContestsModel()
        {
            contestsDataGrid.BeginInvoke(delegate
            {
                GetContestsResponse response = server.GetContests(new GetContestsRequest());
                modelInscrieri.Clear();
                foreach (var contest in response.Contests)
                {
                    InscrieriProba inscriere = new InscrieriProba(contest.Id, contest.NrParticipanti, contest.NumeProba, contest.CategorieVarsta);
                    modelInscrieri.Add(inscriere);
                }

                var contestsDS = modelInscrieri.Select(inscriereProba => new
                {
                    NumeProba = inscriereProba.numeProba,
                    CatVarsta = inscriereProba.categorieVarsta,
                    Inscrisi = inscriereProba.nrParticipanti
                }).ToList();
                contestsDataGrid.DataSource = contestsDS;
            });
            //contestsDataGrid.DataSource = contestsDS;

            //This will assign the datasource. All the columns you listed will show up, and every row
            //of data in the list will populate into the DataGridView.

        }

        public void updateParticipantsModel()
        {

            participantsDataGrid.BeginInvoke(delegate
            {
                modelParticipanti.Clear();
                if (currentContest == -1)
                {
                    GetParticipantsRequest request = new GetParticipantsRequest();
                    GetParticipantsResponse response = server.GetParticipants(request);
                    foreach(var participantRpc in response.Participants)
                    {
                        models.Participant participant = new models.Participant(participantRpc.Id, participantRpc.Name, participantRpc.Age);
                        modelParticipanti.Add(participant);
                    }
                }
                else
                {
                    GetParticipantsByProbaRequest request = new GetParticipantsByProbaRequest { ProbaId = currentContest};
                    GetParticipantsResponse response = server.GetParticipantsByProba(request);
                    foreach (var participantRpc in response.Participants)
                    {
                        models.Participant participant = new models.Participant(participantRpc.Id, participantRpc.Name, participantRpc.Age);
                        modelParticipanti.Add(participant);
                    }
                }


                var participantsDS = modelParticipanti.Select(participant => new
                {
                    Nume = participant.Nume,
                    Varsta = participant.Varsta
                }).ToList();
                participantsDataGrid.DataSource = participantsDS;
            });
            //participantsDataGrid.DataSource = participantsDS;
        }
    }
}
