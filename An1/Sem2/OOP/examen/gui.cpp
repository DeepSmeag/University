#include "gui.h"

using namespace std;
void guiMain::initGUI()
{

    this->setLayout(mainLayout);

    QWidget *mainTable = new QWidget;
    QHBoxLayout *tableLayout = new QHBoxLayout;
    mainTable->setLayout(tableLayout);
    tableLayout->addWidget(table);
    QWidget *rightCol = new QWidget;
    QVBoxLayout *rightLayout = new QVBoxLayout;
    rightCol->setLayout(rightLayout);
    rightLayout->addWidget(addGame);
    rightLayout->addWidget(modifyGame);
    rightLayout->addWidget(createTable);

    QWidget *forms = new QWidget;
    QFormLayout *formLayout = new QFormLayout;
    forms->setLayout(formLayout);
    QLabel *x = new QLabel("id: ");
    formLayout->addRow(x, idtxt);
    x = new QLabel("dim: ");
    formLayout->addRow(x, dimtxt);
    x = new QLabel("tabla: ");
    formLayout->addRow(x, tablatxt);
    x = new QLabel("jucator: ");
    formLayout->addRow(x, jucatortxt);
    x = new QLabel("stare: ");
    formLayout->addRow(x, staretxt);

    const int colCount = 5;
    table->setColumnCount(colCount);
    mainLayout->addWidget(mainTable);
    mainLayout->addWidget(forms);
    mainLayout->addWidget(rightCol);
}
void guiMain::connectSignals()
{
    QObject::connect(addGame, &QPushButton::clicked, this, &guiMain::uiAddGame);
    QObject::connect(modifyGame, &QPushButton::clicked, this, &guiMain::uiModifyGame);
    QObject::connect(createTable, &QPushButton::clicked, this, [&]()
                     {
        tablaJoc = new Tabla(this);
        tablaJoc->show();
        createTable->hide(); });
    QObject::connect(table, &QTableWidget::itemSelectionChanged, this, [&]()
                     {
                         auto items = table->selectedItems();
                         std::string id = items.at(0)->text().toStdString();
                         int i = 0;
                         for (i = 0; i < (int)(service.getGames().size()); i++)
                         {
                             if (service.getGames().at(i).getId() == id)
                             {
                                 tablaJoc->reloadList(service.getGames().at(i));
                                 return;
                             }
                         } });
}
void guiMain::reloadList(vector<XO> games)
{
    std::sort(games.begin(), games.end(), [&](XO &obj1, XO &obj2)
              { return obj1.getStare() < obj2.getStare(); });
    table->setRowCount((int)(games.size()));
    for (int i = 0; i < (int)(games.size()); i++)
    {
        QTableWidgetItem *item = new QTableWidgetItem;
        item->setText(QString::fromStdString(games.at(i).getId()));
        table->setItem(i, 0, item);
        item = new QTableWidgetItem;
        item->setText(QString::fromStdString(games.at(i).getDim()));
        table->setItem(i, 1, item);
        item = new QTableWidgetItem;
        item->setText(QString::fromStdString(games.at(i).getTabla()));
        table->setItem(i, 2, item);
        item = new QTableWidgetItem;
        item->setText(QString::fromStdString(games.at(i).getJucator()));
        table->setItem(i, 3, item);
        item = new QTableWidgetItem;
        item->setText(QString::fromStdString(games.at(i).getStare()));
        table->setItem(i, 4, item);
    }
}
void guiMain::uiAddGame()
{
    std::string id = idtxt->text().toStdString();
    std::string dim = dimtxt->text().toStdString();
    std::string tabla = tablatxt->text().toStdString();
    std::string jucator = jucatortxt->text().toStdString();
    // std::string stare = staretxt->text().toStdString();
    service.addGame(id, dim, tabla, jucator, "Neinceput");
    reloadList(service.getGames());
}
void guiMain::uiModifyGame()
{
    std::string id = idtxt->text().toStdString();
    std::string dim = dimtxt->text().toStdString();
    std::string tabla = tablatxt->text().toStdString();
    std::string jucator = jucatortxt->text().toStdString();
    std::string stare = staretxt->text().toStdString();
    if (valid.checkAll(dim, tabla, jucator, stare) == 0)
    {
        QMessageBox::warning(this, "Eroare", "Date invalide");
        return;
    }
    service.modifyGame(id, dim, tabla, jucator, stare);
    reloadList(service.getGames());
}

void Tabla::initGUI()
{
    this->setLayout(mainLayout);
    mainLayout->addWidget(table);
    table->setColumnCount(4);
    table->setColumnCount(4);
}
void Tabla::connectSignals()
{
    // QObject::connect(tablaJoc, &QTableWidget::itemSelectionChanged, this, [&]() {

    // });
}
void Tabla::reloadList(XO &game)
{
    int dimInt = stoi(game.getDim());
    table->setColumnCount(dimInt);
    table->setRowCount(dimInt);
    std::string tabla = game.getTabla();
    for (int i = 0; i < dimInt * dimInt; i++)
    {
        QTableWidgetItem *item = new QTableWidgetItem;
        char x = tabla.at(i);
        string s("1");
        s.at(0) = x;
        item->setText(QString::fromStdString(s));
        table->setItem(i / dimInt, i % dimInt, item);
    }
}
void Tabla::closeEvent(QCloseEvent *event)
{
    gui->createTable->show();
    event->accept();
}