using mpp_proiect_csharp_DeepSmeag.models;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics.CodeAnalysis;
using System.DirectoryServices.ActiveDirectory;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Formats.Asn1.AsnWriter;
using Grpc.Net.Client;
using Rpcprotocol;

namespace mpp_proiect_csharp_DeepSmeag.uiForms
{
    public partial class LoginMenu : Form
    {
        //private static ServiceFacade serviceFacade = ServiceFacade.getInstance();

        RpcWorkerService.RpcWorkerServiceClient server;
        public LoginMenu()
        {
            InitializeComponent();
            

        }

        private void LoginMenu_Load(object sender, EventArgs e)
        {

        }

        private void loginMenu_Load(object sender, EventArgs e)
        {
        }

        private void loginButton_Click(object sender, EventArgs e)
        {

            string user = usernameTextbox.Text;
            string pass = passwordTextbox.Text;
            try
            {
                PersoanaOficiu persoanaOficiu = new PersoanaOficiu(0, user, pass);
                MainMenu mainMenu = new MainMenu();
                mainMenu.setServer(server);
                mainMenu.setUser(persoanaOficiu);
                LoginRequest loginRequest = new LoginRequest { Username = user, Password = pass };

                LoginResponse response = server.Login(loginRequest);
                if (response.Response.Equals("ok"))
                {
                    MessageBox.Show("Login successful", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    mainMenu.Show();

                    this.Hide();
                    return;
                }
                throw new Exception();

            }
            catch (Exception ex)
            {
                MessageBox.Show("Wrong credentials", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }


        }
        public void setServer(RpcWorkerService.RpcWorkerServiceClient server)
        {
            this.server = server;
        }
    }
}
