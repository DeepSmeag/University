namespace LabSGBDforms
{
    partial class Form1
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
            this.components = new System.ComponentModel.Container();
            this.stockOrdersBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.dataGridViewParent = new System.Windows.Forms.DataGridView();
            this.adminsBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.musicShopDataSet = new LabSGBDforms.MusicShopDataSet();
            this.dataGridViewChild = new System.Windows.Forms.DataGridView();
            this.stockOrdersBindingSource1 = new System.Windows.Forms.BindingSource(this.components);
            this.adminsTableAdapter = new LabSGBDforms.MusicShopDataSetTableAdapters.AdminsTableAdapter();
            this.buttonAdd = new System.Windows.Forms.Button();
            this.buttonUpdate = new System.Windows.Forms.Button();
            this.buttonDelete = new System.Windows.Forms.Button();
            this.fieldsPanel = new System.Windows.Forms.Panel();
            this.masterLabel = new System.Windows.Forms.Label();
            this.detailLabel = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.stockOrdersBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.adminsBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.musicShopDataSet)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.stockOrdersBindingSource1)).BeginInit();
            this.SuspendLayout();
            // 
            // stockOrdersBindingSource
            // 
            this.stockOrdersBindingSource.DataMember = "StockOrders";
            // 
            // dataGridViewParent
            // 
            this.dataGridViewParent.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewParent.Location = new System.Drawing.Point(16, 98);
            this.dataGridViewParent.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.dataGridViewParent.Name = "dataGridViewParent";
            this.dataGridViewParent.RowHeadersWidth = 51;
            this.dataGridViewParent.Size = new System.Drawing.Size(859, 367);
            this.dataGridViewParent.TabIndex = 0;
            this.dataGridViewParent.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridViewParent_CellClick);
            // 
            // adminsBindingSource
            // 
            this.adminsBindingSource.DataMember = "Admins";
            this.adminsBindingSource.DataSource = this.musicShopDataSet;
            // 
            // musicShopDataSet
            // 
            this.musicShopDataSet.DataSetName = "MusicShopDataSet";
            this.musicShopDataSet.SchemaSerializationMode = System.Data.SchemaSerializationMode.IncludeSchema;
            // 
            // dataGridViewChild
            // 
            this.dataGridViewChild.AutoGenerateColumns = false;
            this.dataGridViewChild.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewChild.DataSource = this.stockOrdersBindingSource1;
            this.dataGridViewChild.Location = new System.Drawing.Point(16, 525);
            this.dataGridViewChild.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.dataGridViewChild.Name = "dataGridViewChild";
            this.dataGridViewChild.RowHeadersWidth = 51;
            this.dataGridViewChild.Size = new System.Drawing.Size(859, 233);
            this.dataGridViewChild.TabIndex = 1;
            this.dataGridViewChild.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.dataGridViewChild_CellClick);
            // 
            // stockOrdersBindingSource1
            // 
            this.stockOrdersBindingSource1.DataMember = "StockOrders";
            // 
            // adminsTableAdapter
            // 
            this.adminsTableAdapter.ClearBeforeFill = true;
            // 
            // buttonAdd
            // 
            this.buttonAdd.Location = new System.Drawing.Point(971, 606);
            this.buttonAdd.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonAdd.Name = "buttonAdd";
            this.buttonAdd.Size = new System.Drawing.Size(100, 28);
            this.buttonAdd.TabIndex = 10;
            this.buttonAdd.Text = "Add";
            this.buttonAdd.UseVisualStyleBackColor = true;
            this.buttonAdd.Click += new System.EventHandler(this.buttonAdd_Click);
            // 
            // buttonUpdate
            // 
            this.buttonUpdate.Location = new System.Drawing.Point(1091, 606);
            this.buttonUpdate.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonUpdate.Name = "buttonUpdate";
            this.buttonUpdate.Size = new System.Drawing.Size(100, 28);
            this.buttonUpdate.TabIndex = 11;
            this.buttonUpdate.Text = "Update";
            this.buttonUpdate.UseVisualStyleBackColor = true;
            this.buttonUpdate.Click += new System.EventHandler(this.buttonUpdate_Click);
            // 
            // buttonDelete
            // 
            this.buttonDelete.Location = new System.Drawing.Point(1199, 606);
            this.buttonDelete.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.Size = new System.Drawing.Size(100, 28);
            this.buttonDelete.TabIndex = 12;
            this.buttonDelete.Text = "Delete";
            this.buttonDelete.UseVisualStyleBackColor = true;
            this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
            // 
            // fieldsPanel
            // 
            this.fieldsPanel.Location = new System.Drawing.Point(933, 42);
            this.fieldsPanel.Margin = new System.Windows.Forms.Padding(4, 4, 4, 4);
            this.fieldsPanel.Name = "fieldsPanel";
            this.fieldsPanel.Size = new System.Drawing.Size(417, 482);
            this.fieldsPanel.TabIndex = 13;
            // 
            // masterLabel
            // 
            this.masterLabel.AutoSize = true;
            this.masterLabel.Location = new System.Drawing.Point(405, 60);
            this.masterLabel.Name = "masterLabel";
            this.masterLabel.Size = new System.Drawing.Size(82, 16);
            this.masterLabel.TabIndex = 14;
            this.masterLabel.Text = "masterLabel";
            // 
            // detailLabel
            // 
            this.detailLabel.AutoSize = true;
            this.detailLabel.Location = new System.Drawing.Point(396, 490);
            this.detailLabel.Name = "detailLabel";
            this.detailLabel.Size = new System.Drawing.Size(74, 16);
            this.detailLabel.TabIndex = 15;
            this.detailLabel.Text = "detailLabel";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1420, 828);
            this.Controls.Add(this.detailLabel);
            this.Controls.Add(this.masterLabel);
            this.Controls.Add(this.fieldsPanel);
            this.Controls.Add(this.buttonDelete);
            this.Controls.Add(this.buttonUpdate);
            this.Controls.Add(this.buttonAdd);
            this.Controls.Add(this.dataGridViewChild);
            this.Controls.Add(this.dataGridViewParent);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.stockOrdersBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.adminsBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.musicShopDataSet)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.stockOrdersBindingSource1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private MusicShopDataSet musicShopDataSet;
        private System.Windows.Forms.BindingSource adminsBindingSource;
        private MusicShopDataSetTableAdapters.AdminsTableAdapter adminsTableAdapter;
        private System.Windows.Forms.BindingSource stockOrdersBindingSource;
        private System.Windows.Forms.DataGridView dataGridViewParent;
        private System.Windows.Forms.DataGridView dataGridViewChild;
        private System.Windows.Forms.BindingSource stockOrdersBindingSource1;
        private System.Windows.Forms.DataGridViewTextBoxColumn idDataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn dateCreatedDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn adminIdDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn itemCodeDataGridViewTextBoxColumn;
        private System.Windows.Forms.DataGridViewTextBoxColumn quantityDataGridViewTextBoxColumn;
        private System.Windows.Forms.Button buttonAdd;
        private System.Windows.Forms.Button buttonUpdate;
        private System.Windows.Forms.Button buttonDelete;
        private System.Windows.Forms.Panel fieldsPanel;
        private System.Windows.Forms.Label masterLabel;
        private System.Windows.Forms.Label detailLabel;
    }
}

