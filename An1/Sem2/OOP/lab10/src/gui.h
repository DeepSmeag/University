#ifndef GUI_H
#define GUI_H

#include "service/service.h"
#include "valid/valid.h"
#include <QCloseEvent>
#include <QHBoxLayout>
#include <QLineEdit>
#include <QListWidget>
#include <QPushButton>
#include <QSpinBox>
#include <QWidget>
#include <QTableWidget>
#include <QPainter>
#include <stdlib.h>
#include "observer.h"
#include "guiModelView.h"

class guiMain : public QWidget, public Observer
{

private:
  Service service;
  Valid valid;
  Cart *cart;
  QHBoxLayout *mainly = (new QHBoxLayout);
  QLineEdit *txtName = (new QLineEdit);
  QLineEdit *txtType = (new QLineEdit);
  QLineEdit *txtPrice = (new QLineEdit);
  QLineEdit *txtDestination = (new QLineEdit);
  QLineEdit *txtGen = (new QLineEdit);
  QListWidget *list = (new QListWidget);
  // QTableWidget *tableWidget = new QTableWidget;

  QPushButton *btnAdd = (new QPushButton("Add"));
  QPushButton *btnModify = (new QPushButton("Modify"));
  QPushButton *btnFind = (new QPushButton("Search"));
  QPushButton *btnRemove = (new QPushButton("Remove"));
  QPushButton *btnUndo = (new QPushButton("Undo"));

  QPushButton *btnFilterBy = (new QPushButton("FilterBy"));
  QPushButton *btnGenerate = (new QPushButton("Generate Offers"));

  QPushButton *btnSortBy = (new QPushButton("SortBy"));
  QPushButton *btnOpenCart = (new QPushButton("Cart"));
  QPushButton *btnOpenCartReadOnly = new QPushButton("Cart ReadOnly");
  QPushButton *btnRefresh = (new QPushButton("Refresh"));

  QPushButton *btnAddCart = (new QPushButton("Add Cart"));
  QPushButton *btnGenerateCart = (new QPushButton("Generate Cart"));
  QSpinBox *spinGenerateCart = (new QSpinBox);
  QPushButton *btnClearCart = (new QPushButton("Clear Cart"));
  friend class guiCart;
  friend class guiCartReadOnly;

  void initGUI();
  void connect_signals();
  void reloadList(const std::vector<Offer> &list);

  void addOffer();
  void modifyOffer();
  void removeOffer();
  void findOffer();
  void undoOffer();
  void filterBy();

  void sortBy();

  void refresh();
  void generate();
  void setEditable(bool editable, QLineEdit *widget);

  void addCart();
  void generateCart();
  void clearCart();

public:
  guiMain() : service(), valid()
  {
    cart = &service.serviceCartGetAll();
    initGUI();
    cart->addObserver(this);
    connect_signals();
    reloadList(service.serviceGetAll().getOffers());
  }
  void update() override
  {
    // reloadList(cart->getOffers());
  }
  ~guiMain()
  {
    cart->removeObserver(this);
  }
};

class guiCart : public QWidget, public Observer
{
private:
  guiMain &mainui;
  Cart &cart;
  QHBoxLayout *mainly = (new QHBoxLayout);

  // QListWidget *list = (new QListWidget);
  // QTableWidget *table = new QTableWidget;
  QTableView *table = new QTableView;
  CustomTableModel *tableModel;
  QPushButton *btnAdd = (new QPushButton("Add"));
  QPushButton *btnGenerate = (new QPushButton("Generate"));
  QSpinBox *spinGenerate = (new QSpinBox);
  QPushButton *btnClear = (new QPushButton("Clear"));
  QPushButton *btnExport = (new QPushButton("Export"));
  QLineEdit *txtExport = (new QLineEdit);

  void initGUI();
  void connect_signals();
  void reloadList(const std::vector<Offer> &list);

  void addCart();
  void generateCart();
  void clearCart();
  void exportCart();

  void closeEvent(QCloseEvent *event) override;

public:
  guiCart(Cart &cart, guiMain &mainui) : cart{cart}, mainui{mainui}
  {
    initGUI();
    tableModel = new CustomTableModel(cart.getOffers());
    table->setModel(tableModel);
    connect_signals();
    // reloadList(cart.getOffers());
  }
  void update() override
  {
    reloadList(cart.getOffers());
  }
  ~guiCart()
  {
    cart.removeObserver(this);
  }
};
class guiCartReadOnly : public QWidget, public Observer
{
private:
  guiMain &mainui;
  Cart &cart;
  QHBoxLayout *mainly = (new QHBoxLayout);
  QListWidget *list = (new QListWidget);

  void initGUI();
  void connect_signals();
  // void reloadList(const std::vector<Offer> &list);

  // void closeEvent(QCloseEvent *event) override;
  void paintEvent(QPaintEvent *ev) override
  {
    QPainter p{this};
    p.setPen(Qt::blue);
    // p.drawLine(0, 0, width(), height());
    for (auto &it : cart.getOffers())
    {
      p.drawLine(random() % width(), random() % height(), random() % width(), random() % height());
    }
    // p.drawImage(x, 0, QImage("sky.jpg"));
  }

public:
  guiCartReadOnly(Cart &cart, guiMain &mainui) : cart{cart}, mainui{mainui}
  {
    // initGUI();
    cart.addObserver(this);
    // reloadList(cart.getOffers());
  }
  void update() override
  {
    this->repaint();
  }
  ~guiCartReadOnly()
  {
    cart.removeObserver(this);
  }
};
#endif // GUI_H
