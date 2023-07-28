namespace mpp_proiect_csharp_DeepSmeag.uiForms
{
    partial class LoginMenu
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            usernameLabel = new Label();
            passwordLabel = new Label();
            usernameTextbox = new TextBox();
            passwordTextbox = new TextBox();
            loginButton = new Button();
            SuspendLayout();
            // 
            // usernameLabel
            // 
            usernameLabel.AutoSize = true;
            usernameLabel.Font = new Font("Segoe UI", 14F, FontStyle.Bold, GraphicsUnit.Point);
            usernameLabel.Location = new Point(235, 84);
            usernameLabel.Name = "usernameLabel";
            usernameLabel.Size = new Size(106, 25);
            usernameLabel.TabIndex = 0;
            usernameLabel.Text = "Username:";
            // 
            // passwordLabel
            // 
            passwordLabel.AutoSize = true;
            passwordLabel.Font = new Font("Segoe UI", 14F, FontStyle.Bold, GraphicsUnit.Point);
            passwordLabel.Location = new Point(235, 175);
            passwordLabel.Name = "passwordLabel";
            passwordLabel.Size = new Size(102, 25);
            passwordLabel.TabIndex = 1;
            passwordLabel.Text = "Password:";
            // 
            // usernameTextbox
            // 
            usernameTextbox.Font = new Font("Segoe UI", 12F, FontStyle.Regular, GraphicsUnit.Point);
            usernameTextbox.Location = new Point(378, 84);
            usernameTextbox.Name = "usernameTextbox";
            usernameTextbox.Size = new Size(152, 29);
            usernameTextbox.TabIndex = 2;
            // 
            // passwordTextbox
            // 
            passwordTextbox.Font = new Font("Segoe UI", 12F, FontStyle.Regular, GraphicsUnit.Point);
            passwordTextbox.Location = new Point(378, 175);
            passwordTextbox.Name = "passwordTextbox";
            passwordTextbox.Size = new Size(152, 29);
            passwordTextbox.TabIndex = 3;
            // 
            // loginButton
            // 
            loginButton.Font = new Font("Segoe UI", 14F, FontStyle.Bold, GraphicsUnit.Point);
            loginButton.Location = new Point(322, 340);
            loginButton.Name = "loginButton";
            loginButton.Size = new Size(135, 56);
            loginButton.TabIndex = 4;
            loginButton.Text = "Login";
            loginButton.UseVisualStyleBackColor = true;
            loginButton.Click += loginButton_Click;
            // 
            // LoginMenu
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(loginButton);
            Controls.Add(passwordTextbox);
            Controls.Add(usernameTextbox);
            Controls.Add(passwordLabel);
            Controls.Add(usernameLabel);
            Name = "LoginMenu";
            Text = "loginMenu";
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Label usernameLabel;
        private Label passwordLabel;
        private TextBox usernameTextbox;
        private TextBox passwordTextbox;
        private Button loginButton;
    }
}