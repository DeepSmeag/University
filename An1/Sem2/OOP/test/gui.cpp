#include "gui.h"
using namespace std;
// initializam gui ca sa fisam prima data
void GUI::initGui()
{
    setLayout(mainLayout);

    // left
    QWidget *left = new QWidget;
    QHBoxLayout *leftLayout = new QHBoxLayout;
    left->setLayout(leftLayout);

    leftLayout->addWidget(listDevices);
    // right
    QWidget *right = new QWidget;
    QVBoxLayout *rightLayout = new QVBoxLayout;
    right->setLayout(rightLayout);

    QFormLayout *form = new QFormLayout;
    QLabel *label = new QLabel(QString::fromStdString("model + an la selectie:"));
    form->addRow(label, visualizer);
    visualizer->setReadOnly(true);
    QHBoxLayout *buttonsLayout = new QHBoxLayout;

    buttonsLayout->addWidget(btnSortPret);
    buttonsLayout->addWidget(btnSortModel);
    buttonsLayout->addWidget(btnUnsort);
    // layout pe dreapta cu butoane si text edit;
    rightLayout->addLayout(form);
    rightLayout->addLayout(buttonsLayout);

    mainLayout->addWidget(left);
    mainLayout->addWidget(right);
}
// conectam semnalele la sloturi
void GUI::connect_signals()
{
    QObject::connect(btnSortPret, &QPushButton::clicked, this, &GUI::guiSortPret);
    QObject::connect(btnSortModel, &QPushButton::clicked, this, &GUI::guiSortModel);
    QObject::connect(btnUnsort, &QPushButton::clicked, this, &GUI::guiRefresh);
    QObject::connect(listDevices, &QListWidget::itemSelectionChanged, this, [&]()
                     {
                         auto selected = listDevices->selectedItems();
                         if (selected.size() > 0)
                         {
                             auto item = selected[0];
                             auto txtItem = item->text().toStdString();
                             vector<Device> devices = service.serviceGetDevices();
                             int indice = 0;
                             for (auto &it : devices)
                             {
                                 string txt = it.getModel() + " " + it.getCuloare() + " " + it.getPret();
                                 if (txt == txtItem)
                                 {
                                     break;
                                 }
                                 indice++;
                             }
                             auto rez = devices[indice].getModel() + " " + devices[indice].getAn();
                             visualizer->setText(QString::fromStdString(rez));
                         }
                         else
                         {
                             visualizer->setText(QString::fromStdString(""));
                         } });
}
// reincarcam lista
void GUI::reloadList(vector<Device> devices)
{
    this->listDevices->clear();
    for (auto &it : devices)
    {
        QListWidgetItem *item = new QListWidgetItem;
        if (it.getCuloare() == "rosu")
        {
            item->setBackgroundColor(Qt::GlobalColor::red);
        }
        else if (it.getCuloare() == "albastru")
        {
            item->setBackgroundColor(Qt::GlobalColor::blue);
        }
        else if (it.getCuloare() == "negru")
        {
            item->setBackgroundColor(Qt::GlobalColor::black);
            item->setForeground(QBrush(Qt::GlobalColor::white));
        }
        else if (it.getCuloare() == "galben")
        {
            item->setBackgroundColor(Qt::GlobalColor::yellow);
        }
        string txt = it.getModel() + " " + it.getCuloare() + " " + it.getPret();
        item->setText(QString::fromStdString(txt));
        item->setData(Qt::UserRole, QString::fromStdString(it.getModel()));
        this->listDevices->addItem(item);
    }
}
// refresh
void GUI ::guiRefresh()
{
    reloadList(service.serviceGetDevices());
}
// sort dupa pret
void GUI::guiSortPret()
{
    guiRefresh();
    vector<Device> vect = service.serviceSortPret();
    reloadList(vect);
}
// sortam dupa model alfabetic
void GUI::guiSortModel()
{
    guiRefresh();
    vector<Device> vect = service.serviceSortModel();
    reloadList(vect);
}