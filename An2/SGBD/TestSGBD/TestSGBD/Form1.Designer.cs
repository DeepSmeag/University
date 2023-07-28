namespace TestSGBD
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            masterDataGridView = new DataGridView();
            detailDataGridView = new DataGridView();
            idPolitistTextBox = new TextBox();
            idSectorTextBox = new TextBox();
            dateTimePicker = new DateTimePicker();
            addButton = new Button();
            editButton = new Button();
            deleteButton = new Button();
            idProgramareTextBox = new TextBox();
            timePicker = new DateTimePicker();
            label1 = new Label();
            label2 = new Label();
            label3 = new Label();
            label4 = new Label();
            label5 = new Label();
            label6 = new Label();
            label7 = new Label();
            ((System.ComponentModel.ISupportInitialize)masterDataGridView).BeginInit();
            ((System.ComponentModel.ISupportInitialize)detailDataGridView).BeginInit();
            SuspendLayout();
            // 
            // masterDataGridView
            // 
            masterDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            masterDataGridView.Location = new Point(46, 60);
            masterDataGridView.Name = "masterDataGridView";
            masterDataGridView.RowHeadersWidth = 51;
            masterDataGridView.RowTemplate.Height = 29;
            masterDataGridView.Size = new Size(353, 381);
            masterDataGridView.TabIndex = 0;
            masterDataGridView.CellClick += masterDataGridView_CellClick;
            // 
            // detailDataGridView
            // 
            detailDataGridView.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            detailDataGridView.Location = new Point(441, 60);
            detailDataGridView.Name = "detailDataGridView";
            detailDataGridView.RowHeadersWidth = 51;
            detailDataGridView.RowTemplate.Height = 29;
            detailDataGridView.Size = new Size(338, 381);
            detailDataGridView.TabIndex = 1;
            // 
            // idPolitistTextBox
            // 
            idPolitistTextBox.Location = new Point(838, 127);
            idPolitistTextBox.Name = "idPolitistTextBox";
            idPolitistTextBox.Size = new Size(125, 27);
            idPolitistTextBox.TabIndex = 2;
            // 
            // idSectorTextBox
            // 
            idSectorTextBox.Location = new Point(838, 199);
            idSectorTextBox.Name = "idSectorTextBox";
            idSectorTextBox.Size = new Size(125, 27);
            idSectorTextBox.TabIndex = 3;
            // 
            // dateTimePicker
            // 
            dateTimePicker.Location = new Point(796, 269);
            dateTimePicker.Name = "dateTimePicker";
            dateTimePicker.Size = new Size(251, 27);
            dateTimePicker.TabIndex = 4;
            // 
            // addButton
            // 
            addButton.Location = new Point(741, 450);
            addButton.Name = "addButton";
            addButton.Size = new Size(79, 33);
            addButton.TabIndex = 5;
            addButton.Text = "add";
            addButton.UseVisualStyleBackColor = true;
            addButton.Click += addButton_Click;
            // 
            // editButton
            // 
            editButton.Location = new Point(826, 450);
            editButton.Name = "editButton";
            editButton.Size = new Size(94, 29);
            editButton.TabIndex = 6;
            editButton.Text = "edit";
            editButton.UseVisualStyleBackColor = true;
            editButton.Click += editButton_Click;
            // 
            // deleteButton
            // 
            deleteButton.Location = new Point(939, 447);
            deleteButton.Name = "deleteButton";
            deleteButton.Size = new Size(81, 31);
            deleteButton.TabIndex = 7;
            deleteButton.Text = "delete";
            deleteButton.UseVisualStyleBackColor = true;
            deleteButton.Click += deleteButton_Click;
            // 
            // idProgramareTextBox
            // 
            idProgramareTextBox.Location = new Point(833, 41);
            idProgramareTextBox.Name = "idProgramareTextBox";
            idProgramareTextBox.Size = new Size(127, 27);
            idProgramareTextBox.TabIndex = 8;
            // 
            // timePicker
            // 
            timePicker.Location = new Point(797, 341);
            timePicker.Name = "timePicker";
            timePicker.Size = new Size(250, 27);
            timePicker.TabIndex = 9;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(833, 18);
            label1.Name = "label1";
            label1.Size = new Size(108, 20);
            label1.TabIndex = 10;
            label1.Text = "id programare:";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(833, 104);
            label2.Name = "label2";
            label2.Size = new Size(75, 20);
            label2.TabIndex = 11;
            label2.Text = "id politist:";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(845, 176);
            label3.Name = "label3";
            label3.Size = new Size(69, 20);
            label3.TabIndex = 12;
            label3.Text = "id sector:";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(810, 246);
            label4.Name = "label4";
            label4.Size = new Size(42, 20);
            label4.TabIndex = 13;
            label4.Text = "data:";
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(810, 318);
            label5.Name = "label5";
            label5.Size = new Size(34, 20);
            label5.TabIndex = 14;
            label5.Text = "ora:";
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Location = new Point(46, 27);
            label6.Name = "label6";
            label6.Size = new Size(109, 20);
            label6.TabIndex = 15;
            label6.Text = "Tabel Sectoare:";
            // 
            // label7
            // 
            label7.AutoSize = true;
            label7.Location = new Point(441, 27);
            label7.Name = "label7";
            label7.Size = new Size(125, 20);
            label7.TabIndex = 16;
            label7.Text = "Tabel Programari:";
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1089, 572);
            Controls.Add(label7);
            Controls.Add(label6);
            Controls.Add(label5);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(timePicker);
            Controls.Add(idProgramareTextBox);
            Controls.Add(deleteButton);
            Controls.Add(editButton);
            Controls.Add(addButton);
            Controls.Add(dateTimePicker);
            Controls.Add(idSectorTextBox);
            Controls.Add(idPolitistTextBox);
            Controls.Add(detailDataGridView);
            Controls.Add(masterDataGridView);
            Name = "Form1";
            Text = "Form1";
            Load += Form1_Load;
            ((System.ComponentModel.ISupportInitialize)masterDataGridView).EndInit();
            ((System.ComponentModel.ISupportInitialize)detailDataGridView).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private DataGridView masterDataGridView;
        private DataGridView detailDataGridView;
        private TextBox idPolitistTextBox;
        private TextBox idSectorTextBox;
        private DateTimePicker dateTimePicker;
        private Button addButton;
        private Button editButton;
        private Button deleteButton;
        private TextBox idProgramareTextBox;
        private DateTimePicker timePicker;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private Label label5;
        private Label label6;
        private Label label7;
    }
}