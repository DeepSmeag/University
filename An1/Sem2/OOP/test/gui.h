#include <QWidget>
#include <QLineEdit>
#include <QListWidget>
#include <QPushButton>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFormLayout>
#include <QLabel>

#include "service.h"

class GUI : public QWidget
{
private:
    // comentarii in fisierul cpp
    Service service;
    QHBoxLayout *mainLayout = new QHBoxLayout;
    QListWidget *listDevices = new QListWidget;
    QLineEdit *visualizer = new QLineEdit;
    QPushButton *btnSortModel = new QPushButton("Sortare Model");
    QPushButton *btnSortPret = new QPushButton("Sortare Pret");
    QPushButton *btnUnsort = new QPushButton("Nesortat");

public:
    GUI() : service{"devices.txt"}
    {
        initGui();
        connect_signals();
        reloadList(service.serviceGetDevices());
    }
    void connect_signals();
    void initGui();
    void reloadList(vector<Device> devices);
    void guiRefresh();
    void guiSortPret();
    void guiSortModel();
};