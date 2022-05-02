#include "gui.h"
#include <QCloseEvent>
#include <QFormLayout>
#include <QLabel>
#include <QMessageBox>

void guiMain::initGUI()
{
  setLayout(mainly);

  // formul din dreapta
  QWidget *widForm = (new QWidget);
  QFormLayout *formLayout = (new QFormLayout);

  widForm->setLayout(formLayout);

  QLabel *x = (new QLabel("Name/Criterion:"));
  formLayout->addRow(x, txtName);

  x = (new QLabel("Destination/Field/Order:"));
  formLayout->addRow(x, txtDestination);

  x = (new QLabel("Type:"));
  formLayout->addRow(x, txtType);

  x = (new QLabel("Price:"));
  formLayout->addRow(x, txtPrice);

  // butoane de sub form
  // butoane pt introducere txt
  QWidget *formbtn1 = (new QWidget);
  QWidget *formbtn2 = (new QWidget);
  QHBoxLayout *formbtnLayout1 = (new QHBoxLayout);
  QHBoxLayout *formbtnLayout2 = (new QHBoxLayout);

  formbtn1->setLayout(formbtnLayout1);

  formbtnLayout1->addWidget(btnAdd);
  formbtnLayout1->addWidget(btnModify);
  formbtnLayout1->addWidget(btnFind);

  formbtn2->setLayout(formbtnLayout2);

  formbtnLayout2->addWidget(btnRemove);
  formbtnLayout2->addWidget(btnUndo);

  formLayout->addRow(formbtn1);
  formLayout->addRow(formbtn2);

  // butoane pentru filtrare
  QWidget *formbtnfilter = (new QWidget);
  QHBoxLayout *formbtnfilterLayout = (new QHBoxLayout);

  formbtnfilter->setLayout(formbtnfilterLayout);

  formbtnfilterLayout->addWidget(btnFilterBy);
  formbtnfilterLayout->addWidget(btnGenerate);
  formLayout->addRow(formbtnfilter);

  // buton pentru contract
  QWidget *btnCart = (new QWidget);
  QHBoxLayout *btnCartLayout = (new QHBoxLayout);

  btnCart->setLayout(btnCartLayout);

  btnCartLayout->addWidget(btnOpenCart);

  formLayout->addRow(btnCart);
  // lista cu date
  QWidget *widList = (new QWidget);
  QVBoxLayout *ListLayout = (new QVBoxLayout);
  widList->setLayout(ListLayout);
  ListLayout->addWidget(list);

  // butoane de sub lista
  QWidget *listbtn = (new QWidget);
  QHBoxLayout *listbtnLayout = (new QHBoxLayout);

  listbtn->setLayout(listbtnLayout);

  listbtnLayout->addWidget(btnSortBy);
  listbtnLayout->addWidget(btnRefresh);
  ListLayout->addWidget(listbtn);

  // construire fereastra
  mainly->addWidget(widList);
  mainly->addWidget(widForm);
}

void guiMain::connect_signals()
{
  QObject::connect(btnAdd, &QPushButton::clicked, this,
                   &guiMain::addOffer);

  QObject::connect(btnModify, &QPushButton::clicked, this,
                   &guiMain::modifyOffer);

  QObject::connect(btnRemove, &QPushButton::clicked, this,
                   &guiMain::removeOffer);

  QObject::connect(btnFind, &QPushButton::clicked, this,
                   &guiMain::findOffer);

  QObject::connect(btnUndo, &QPushButton::clicked, this,
                   &guiMain::undoOffer);

  QObject::connect(btnFilterBy, &QPushButton::clicked, this,
                   &guiMain::filterBy);

  QObject::connect(btnSortBy, &QPushButton::clicked, this,
                   &guiMain::sortBy);
  QObject::connect(btnRefresh, &QPushButton::clicked, this,
                   &guiMain::refresh);
  QObject::connect(btnGenerate, &QPushButton::clicked, this,
                   &guiMain::generate);

  QObject::connect(list, &QListWidget::itemSelectionChanged, this, [&]()
                   {
    auto selected = list->selectedItems();
    if (selected.isEmpty()) {
      txtName->setText("");
      txtDestination->setText("");
      txtType->setText("");
      txtPrice->setText("");
    } else {
      auto item = selected.at(0);
      auto name = item->data(Qt::UserRole).toString().toStdString();
      Offer x = service.serviceSearch(name);
      txtName->setText(QString::fromStdString(x.getName()));
      txtDestination->setText(QString::fromStdString(x.getDestination()));
      txtType->setText(QString::fromStdString(x.getType()));
      txtPrice->setText(QString::fromStdString(x.getPrice()));
    } });

  QObject::connect(btnOpenCart, &QPushButton::clicked, this, [&]()
                   {
    guiCart *x = (new guiCart{*this});
    // x->setParent(this);
    btnOpenCart->hide();
    x->show(); });
}

void guiMain::addOffer()
{
  try
  {
    std::string errors = "";
    std::string name = txtName->text().toStdString();
    if (valid.validStrIsEmpty(name))
    {
      errors += "Name cannot be empty\n";
    }
    std::string destination = txtDestination->text().toStdString();
    if (valid.validStrIsEmpty(destination))
    {
      errors += "Destination cannot be empty\n";
    }
    std::string type = txtType->text().toStdString();
    if (valid.validStrIsEmpty(type))
    {
      errors += "Type cannot be empty\n";
    }
    std::string price = txtPrice->text().toStdString();
    if (!valid.validStrIsPositiveInt(price))
    {
      errors += "Price is invalid!\n";
    }
    if (!errors.empty())
    {
      throw ValidException(errors);
    }
    service.serviceAdd(txtName->text().toStdString(), txtDestination->text().toStdString(),
                       txtType->text().toStdString(), txtPrice->text().toStdString());
    reloadList(service.serviceGetAll().getOffers());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiMain::modifyOffer()
{
  try
  {
    std::string errors = "";
    std::string name = txtName->text().toStdString();
    if (valid.validStrIsEmpty(name))
    {
      errors += "Name cannot be empty\n";
    }
    std::string destination = txtDestination->text().toStdString();
    if (valid.validStrIsEmpty(destination))
    {
      errors += "Destination cannot be empty\n";
    }
    std::string type = txtType->text().toStdString();
    if (valid.validStrIsEmpty(type))
    {
      errors += "Type cannot be empty\n";
    }
    std::string price = txtPrice->text().toStdString();
    if (!valid.validStrIsPositiveInt(price))
    {
      errors += "Price is invalid!\n";
    }
    if (!errors.empty())
    {
      throw ValidException(errors);
    }
    service.serviceModify(txtName->text().toStdString(), txtName->text().toStdString(), txtDestination->text().toStdString(),
                          txtType->text().toStdString(),
                          txtPrice->text().toStdString());
    reloadList(service.serviceGetAll().getOffers());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiMain::removeOffer()
{
  try
  {
    std::string errors = "";
    std::string name = txtName->text().toStdString();
    if (valid.validStrIsEmpty(name))
    {
      errors += "Name cannot be empty\n";
    }
    if (!errors.empty())
    {
      throw ValidException(errors);
    }
    service.serviceRemove(txtName->text().toStdString());
    reloadList(service.serviceGetAll().getOffers());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiMain::findOffer()
{
  try
  {
    std::string errors = "";
    std::string name = txtName->text().toStdString();
    if (valid.validStrIsEmpty(name))
    {
      errors += "Name cannot be empty\n";
    }
    if (!errors.empty())
    {
      throw ValidException(errors);
    }
    reloadList(service.serviceGetAll().getOffers());
    Offer const &x =
        service.serviceSearch(txtName->text().toStdString());

    auto items = list->findItems(QString::fromStdString(x.getName()),
                                 Qt::MatchContains);
    items[0]->setData(Qt::BackgroundRole, QBrush{Qt::green, Qt::SolidPattern});
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiMain::undoOffer()
{
  try
  {
    service.serviceUndo();
    reloadList(service.serviceGetAll().getOffers());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiMain::filterBy()
{
  try
  {
    std::string errors;
    std::string criterion = txtName->text().toStdString();
    std::string field = txtDestination->text().toStdString();
    if (this->valid.validStrIsEmpty(criterion))
    {
      errors += "Criterion cannot be empty\n";
    }
    if (this->valid.validStrIsEmpty(field))
    {
      errors += "Field cannot be empty\n";
    }
    if (criterion.compare("destination") != 0 && criterion.compare("price") != 0)
    {
      errors += "Invalid criterion!\n";
    }
    if (!errors.empty())
    {
      throw ValidException(errors);
    }
    reloadList(service.serviceFilter(txtName->text().toStdString(), txtDestination->text().toStdString()).getOffers());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiMain::sortBy()
{
  try
  {
    std::string errors;
    std::string criterion = txtName->text().toStdString();
    std::string order = txtDestination->text().toStdString();
    if (this->valid.validStrIsEmpty(criterion))
    {
      errors += "Criterion cannot be empty\n";
    }
    if (criterion.compare("destination") != 0 && criterion.compare("price") != 0 && criterion.compare("name") != 0 && criterion.compare("type") != 0)
    {
      errors += "Invalid criterion!\n";
    }
    if (this->valid.validStrIsEmpty(criterion))
    {
      errors += "Order cannot be empty\n";
    }
    if (order.compare("asc") != 0 && order.compare("desc") != 0)
    {
      errors += "Invalid order!\n";
    }
    if (!errors.empty())
    {
      throw ValidException(errors);
    }
    reloadList(service.serviceSort(txtName->text().toStdString(), txtDestination->text().toStdString()).getOffers());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiMain::refresh()
{
  reloadList(service.serviceGetAll().getOffers());
}
void guiMain::generate()
{
  service.serviceGenerate("10");
  reloadList(service.serviceGetAll().getOffers());
}

void guiMain::reloadList(const std::vector<Offer> &list)
{
  this->list->clear();
  for (const auto &x : list)
  {
    QListWidgetItem *item = (new QListWidgetItem);

    std::string txt =
        x.getName() + " | " + x.getDestination() + " | " + x.getType() + " | " + x.getPrice();

    item->setText(QString::fromStdString(txt));
    item->setData(Qt::UserRole, QString::fromStdString(x.getName()));
    this->list->addItem(item);
  }
}

void guiCart::initGUI()
{

  setLayout(mainly);

  // lista cu date
  QWidget *widList = (new QWidget);
  QVBoxLayout *ListLayout = (new QVBoxLayout);
  widList->setLayout(ListLayout);
  ListLayout->addWidget(list);

  // butoane din dreapta
  QWidget *widBtn = (new QWidget);
  QFormLayout *btnLayout = (new QFormLayout);

  widBtn->setLayout(btnLayout);

  btnLayout->addRow(btnAdd);
  btnLayout->addRow(btnClear);

  btnLayout->addRow(btnGenerate, spinGenerate);
  btnLayout->addRow(btnExport, txtExport);

  // construiere fereastra
  mainly->addWidget(widList);
  mainly->addWidget(widBtn);
}

void guiCart::connect_signals()
{

  QObject::connect(btnAdd, &QPushButton::clicked, this,
                   &guiCart::addCart);

  QObject::connect(btnGenerate, &QPushButton::clicked, this,
                   &guiCart::generateCart);

  QObject::connect(btnClear, &QPushButton::clicked, this,
                   &guiCart::clearCart);

  QObject::connect(btnExport, &QPushButton::clicked, this,
                   &guiCart::exportCart);
}

void guiCart::addCart()
{
  try
  {
    auto selected = mainui.list->selectedItems();
    if (selected.isEmpty())
    {
      QMessageBox::warning(this, "Warning", "Nu este nimic selectat");
      return;
    }
    auto item = selected.at(0);

    std::string name = item->data(Qt::UserRole).toString().toStdString();

    mainui.service.serviceCartAdd(name);
    reloadList(mainui.service.serviceCartGetAll().getOffers());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
  catch (ValidException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiCart::generateCart()
{
  QString val(QString::number(spinGenerate->value()));
  mainui.service.serviceCartGenerate(val.toStdString());
  reloadList(mainui.service.serviceCartGetAll().getOffers());
}

void guiCart::clearCart()
{

  mainui.service.serviceCartClear();
  reloadList(mainui.service.serviceCartGetAll().getOffers());
}

void guiCart::exportCart()
{
  try
  {
    if (txtExport->text() == "")
    {
      QMessageBox::warning(this, "Warning", "Need a file name");
    }
    else
      mainui.service.serviceExportCart(txtExport->text().toStdString());
  }
  catch (RepoException &ex)
  {
    QMessageBox::warning(this, "Warning", QString::fromStdString(ex.what()));
  }
}

void guiCart::reloadList(const std::vector<Offer> &list)
{
  this->list->clear();
  for (const auto &x : list)
  {
    QListWidgetItem *item = (new QListWidgetItem);

    std::string txt =
        x.getName() + " | " + x.getDestination() + " | " + x.getType() + " | " + x.getPrice();

    item->setText(QString::fromStdString(txt));
    item->setData(Qt::UserRole, QString::fromStdString(x.getName()));
    this->list->addItem(item);
  }
}

void guiCart::closeEvent(QCloseEvent *event)
{
  mainui.btnOpenCart->show();
  event->accept();
}
