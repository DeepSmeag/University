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

class guiMain : public QWidget
{

private:
  Service service;
  Valid valid;
  QHBoxLayout *mainly = (new QHBoxLayout);
  QLineEdit *txtName = (new QLineEdit);
  QLineEdit *txtType = (new QLineEdit);
  QLineEdit *txtPrice = (new QLineEdit);
  QLineEdit *txtDestination = (new QLineEdit);
  QListWidget *list = (new QListWidget);
  QPushButton *btnAdd = (new QPushButton("Add"));
  QPushButton *btnModify = (new QPushButton("Modify"));
  QPushButton *btnFind = (new QPushButton("Search"));
  QPushButton *btnRemove = (new QPushButton("Remove"));
  QPushButton *btnUndo = (new QPushButton("Undo"));

  QPushButton *btnFilterBy = (new QPushButton("FilterBy"));
  QPushButton *btnGenerate = (new QPushButton("Generate Offers"));

  QPushButton *btnSortBy = (new QPushButton("SortBy"));
  QPushButton *btnOpenCart = (new QPushButton("Cart"));
  QPushButton *btnRefresh = (new QPushButton("Refresh"));
  friend class guiCart;

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

public:
  guiMain()
  {
    initGUI();
    connect_signals();
    reloadList(service.serviceGetAll().getOffers());
  }
};

class guiCart : public QWidget
{
private:
  guiMain &mainui;
  QHBoxLayout *mainly = (new QHBoxLayout);
  QListWidget *list = (new QListWidget);
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
  guiCart(guiMain &mainui) : mainui{mainui}
  {
    initGUI();
    connect_signals();
    reloadList(mainui.service.serviceCartGetAll().getOffers());
  }
};

#endif // GUI_H
