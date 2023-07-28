namespace mpp_proiect_csharp_DeepSmeag.uiForms
{
    partial class MainMenu
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
            logoutButton = new Button();
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            label4 = new Label();
            nameTextBox = new TextBox();
            ageTextBox = new TextBox();
            registerButton = new Button();
            participantsDataGrid = new DataGridView();
            contestsDataGrid = new DataGridView();
            ((System.ComponentModel.ISupportInitialize)participantsDataGrid).BeginInit();
            ((System.ComponentModel.ISupportInitialize)contestsDataGrid).BeginInit();
            SuspendLayout();
            // 
            // logoutButton
            // 
            logoutButton.Font = new Font("Segoe UI", 14F, FontStyle.Bold, GraphicsUnit.Point);
            logoutButton.Location = new Point(1027, 16);
            logoutButton.Margin = new Padding(3, 4, 3, 4);
            logoutButton.Name = "logoutButton";
            logoutButton.Size = new Size(145, 72);
            logoutButton.TabIndex = 0;
            logoutButton.Text = "Logout";
            logoutButton.UseVisualStyleBackColor = true;
            logoutButton.Click += logoutButton_Click;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Segoe UI", 16F, FontStyle.Bold, GraphicsUnit.Point);
            label1.Location = new Point(24, 16);
            label1.Name = "label1";
            label1.Size = new Size(102, 37);
            label1.TabIndex = 1;
            label1.Text = "Search";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Segoe UI", 16F, FontStyle.Bold, GraphicsUnit.Point);
            label2.Location = new Point(24, 467);
            label2.Name = "label2";
            label2.Size = new Size(123, 37);
            label2.TabIndex = 2;
            label2.Text = "Register";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Font = new Font("Segoe UI", 14F, FontStyle.Bold, GraphicsUnit.Point);
            label3.Location = new Point(93, 552);
            label3.Name = "label3";
            label3.Size = new Size(81, 32);
            label3.TabIndex = 3;
            label3.Text = "Name";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Font = new Font("Segoe UI", 14F, FontStyle.Bold, GraphicsUnit.Point);
            label4.Location = new Point(112, 637);
            label4.Name = "label4";
            label4.Size = new Size(59, 32);
            label4.TabIndex = 4;
            label4.Text = "Age";
            // 
            // nameTextBox
            // 
            nameTextBox.Font = new Font("Segoe UI", 14F, FontStyle.Regular, GraphicsUnit.Point);
            nameTextBox.Location = new Point(191, 552);
            nameTextBox.Margin = new Padding(3, 4, 3, 4);
            nameTextBox.Name = "nameTextBox";
            nameTextBox.Size = new Size(146, 39);
            nameTextBox.TabIndex = 5;
            // 
            // ageTextBox
            // 
            ageTextBox.Font = new Font("Segoe UI", 14F, FontStyle.Regular, GraphicsUnit.Point);
            ageTextBox.Location = new Point(191, 637);
            ageTextBox.Margin = new Padding(3, 4, 3, 4);
            ageTextBox.Name = "ageTextBox";
            ageTextBox.Size = new Size(146, 39);
            ageTextBox.TabIndex = 6;
            // 
            // registerButton
            // 
            registerButton.Font = new Font("Segoe UI", 14F, FontStyle.Bold, GraphicsUnit.Point);
            registerButton.Location = new Point(159, 737);
            registerButton.Margin = new Padding(3, 4, 3, 4);
            registerButton.Name = "registerButton";
            registerButton.Size = new Size(129, 56);
            registerButton.TabIndex = 7;
            registerButton.Text = "Register";
            registerButton.UseVisualStyleBackColor = true;
            registerButton.Click += registerButton_Click;
            // 
            // participantsDataGrid
            // 
            participantsDataGrid.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            participantsDataGrid.Location = new Point(606, 77);
            participantsDataGrid.Margin = new Padding(3, 4, 3, 4);
            participantsDataGrid.Name = "participantsDataGrid";
            participantsDataGrid.RowHeadersWidth = 51;
            participantsDataGrid.RowTemplate.Height = 25;
            participantsDataGrid.Size = new Size(279, 363);
            participantsDataGrid.TabIndex = 8;
            // 
            // contestsDataGrid
            // 
            contestsDataGrid.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            contestsDataGrid.Location = new Point(35, 77);
            contestsDataGrid.Margin = new Padding(3, 4, 3, 4);
            contestsDataGrid.Name = "contestsDataGrid";
            contestsDataGrid.RowHeadersWidth = 51;
            contestsDataGrid.RowTemplate.Height = 25;
            contestsDataGrid.Size = new Size(392, 363);
            contestsDataGrid.TabIndex = 9;
            contestsDataGrid.CellClick += contestsDataGridClick;
            // 
            // MainMenu
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1186, 868);
            Controls.Add(contestsDataGrid);
            Controls.Add(participantsDataGrid);
            Controls.Add(registerButton);
            Controls.Add(ageTextBox);
            Controls.Add(nameTextBox);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(logoutButton);
            Margin = new Padding(3, 4, 3, 4);
            Name = "MainMenu";
            Text = "MainMenu";
            Load += MainMenu_Load;
            ((System.ComponentModel.ISupportInitialize)participantsDataGrid).EndInit();
            ((System.ComponentModel.ISupportInitialize)contestsDataGrid).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button logoutButton;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private TextBox nameTextBox;
        private TextBox ageTextBox;
        private Button registerButton;
        private DataGridView participantsDataGrid;
        private DataGridView contestsDataGrid;
    }
}