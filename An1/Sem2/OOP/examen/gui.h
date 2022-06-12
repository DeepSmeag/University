#include <QWidget>
#include <QHBoxLayout>
#include <QLineEdit>
#include <QTableWidget>
#include <QLabel>
#include <QPushButton>
#include <QFormLayout>
#include <QMessageBox>
#include <QCloseEvent>
#include <QPalette>
#include <QFont>
#include <QBrush>
#include "service.h"
#include "valid.h"
#include <vector>
#include <string>
#include <QString>
#include <algorithm>
class Tabla;
class guiMain : public QWidget
{
private:
    Service service;
    Valid valid;
    QHBoxLayout *mainLayout = new QHBoxLayout;

    QLineEdit *idtxt = new QLineEdit;
    QLineEdit *dimtxt = new QLineEdit;
    QLineEdit *tablatxt = new QLineEdit;
    QLineEdit *jucatortxt = new QLineEdit;
    QLineEdit *staretxt = new QLineEdit;

    QPushButton *addGame = new QPushButton("Adaugare");
    QPushButton *modifyGame = new QPushButton("Modificare");
    QPushButton *createTable = new QPushButton("Creeaza tabla");
    Tabla *tablaJoc;

    QTableWidget *table = new QTableWidget;

    void initGUI();
    void connectSignals();
    void reloadList(std::vector<XO> games);

public:
    guiMain() : service("f.in")
    {
        initGUI();
        connectSignals();
        reloadList(service.getGames());
    }
    void uiAddGame();
    void uiModifyGame();
    // void uiCreateTable();
    friend class Tabla;
};

class Tabla : public QWidget
{
    guiMain *gui;
    Service *service;
    XO *game;
    QHBoxLayout *mainLayout = new QHBoxLayout;
    QTableWidget *table = new QTableWidget;
    // initiere
    void initGUI();
    // conectare semnale
    void connectSignals();

public:
    Tabla(guiMain *gui) : gui{gui}
    {
        initGUI();
        connectSignals();
        // reloadList(*game);
    }
    // eveniment inchidere
    void closeEvent(QCloseEvent *event) override;
    // update lista
    void reloadList(XO &game);
    friend class guiMain;
};