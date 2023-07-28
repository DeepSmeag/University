using mpp_proiect_csharp_DeepSmeag.service;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.DirectoryServices.ActiveDirectory;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Formats.Asn1.AsnWriter;

namespace mpp_proiect_csharp_DeepSmeag.uiForms
{
    public partial class LoginMenu : Form
    {
        private static ServiceFacade serviceFacade = ServiceFacade.getInstance();
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
            if (serviceFacade.attemptLogin(user, pass))
            {
                // create new Window to login
                //try
                //{
                //    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../mainMenu.fxml"));
                //    MainController mainMenuController = new MainController();
                //    fxmlLoader.setController(mainMenuController);
                //    Scene mainMenuScene = new Scene(fxmlLoader.load(), 800, 750);


                //    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                //    alert.setTitle("Login");
                //    alert.setHeaderText("Login successful");
                //    alert.setContentText("You have successfully logged in");
                //    alert.showAndWait();
                //    //                close this scene
                //    Stage stageClose = (Stage)loginButton.getScene().getWindow();
                //    stageClose.close();

                //    Stage stage = new Stage();
                //    stage.setTitle("Main Menu");
                //    stage.setScene(mainMenuScene);
                //    stage.show();
                //}
                //catch (Exception e)
                //{
                //    e.printStackTrace();
                //}
                MainMenu mainMenu = new MainMenu();

                MessageBox.Show("Login successful", "Info", MessageBoxButtons.OK, MessageBoxIcon.Information);
                mainMenu.Show();
                this.Hide();

            }
            else
            {
                //Show error dialog

                MessageBox.Show("Wrong credentials", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }

        }
    }
}
