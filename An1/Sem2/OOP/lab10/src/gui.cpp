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

  QPalette palText;
  palText.setColor(QPalette::Base, Qt::GlobalColor::yellow);
  txtName->setPalette(palText);
  QLabel *x = (new QLabel("Name/Criterion:"));
  formLayout->addRow(x, txtName);

  x = (new QLabel("Destination/Field/Order:"));
  formLayout->addRow(x, txtDestination);

  x = (new QLabel("Type:"));
  formLayout->addRow(x, txtType);

  x = (new QLabel("Price:"));
  formLayout->addRow(x, txtPrice);

  x = (new QLabel("Generate no.:"));
  formLayout->addRow(x, txtGen);
  // butoane de sub form
  // butoane pt introducere txt
  QWidget *formbtn1 = (new QWidget);
  QWidget *formbtn2 = (new QWidget);
  QHBoxLayout *formbtn1Layout = (new QHBoxLayout);
  QHBoxLayout *formbtn2Layout = (new QHBoxLayout);

  formbtn1->setLayout(formbtn1Layout);

  formbtn1Layout->addWidget(btnAdd);
  formbtn1Layout->addWidget(btnModify);
  formbtn1Layout->addWidget(btnFind);

  formbtn2->setLayout(formbtn2Layout);

  formbtn2Layout->addWidget(btnRemove);
  formbtn2Layout->addWidget(btnUndo);

  formLayout->addRow(formbtn1);
  formLayout->addRow(formbtn2);

  // butoane pentru filtrare + generare
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
  btnCartLayout->addWidget(btnOpenCartReadOnly);
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
  QHBoxLayout *cartLayout = new QHBoxLayout;
  cartLayout->addWidget(btnAddCart);
  cartLayout->addWidget(btnGenerateCart);
  cartLayout->addWidget(btnClearCart);
  ListLayout->addLayout(cartLayout);
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
  QObject::connect(btnAddCart, &QPushButton::clicked, this, &guiMain::addCart);
  QObject::connect(btnClearCart, &QPushButton::clicked, this, &guiMain::clearCart);
  QObject::connect(btnGenerateCart, &QPushButton::clicked, this, &guiMain::generateCart);
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
    guiCart *x = (new guiCart{service.serviceCartGetAll(), *this});
    // x->setParent(this);
    setEditable(false, this->txtName);
    btnOpenCart->hide();
    x->show(); });
  QObject::connect(btnOpenCartReadOnly, &QPushButton::clicked, this, [&]()
                   {
    guiCartReadOnly *x = (new guiCartReadOnly{service.serviceCartGetAll(),*this});
    // x->setParent(this);
    setEditable(false, this->txtName);
    // btnOpenCartReadOnly->hide();
    x->show(); });
}

void guiMain::addCart()
{
  try
  {
    auto selected = this->list->selectedItems();
    if (selected.isEmpty())
    {
      QMessageBox::warning(this, "Warning", "Nu este nimic selectat");
      return;
    }
    auto item = selected.at(0);

    std::string name = item->data(Qt::UserRole).toString().toStdString();
    service.serviceCartAdd(name);
    cart->notify();
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

void guiMain::generateCart()
{
  const int genNo = 10;
  QString val(QString::number(genNo));
  service.serviceCartGenerate(val.toStdString());
  // reloadList(service.serviceCartGetAll().getOffers());
  cart->notify();
}

void guiMain::clearCart()
{

  service.serviceCartClear();
  // reloadList(service.serviceCartGetAll().getOffers());
  cart->notify();
}
void guiMain::setEditable(bool editable, QLineEdit *widget)
{
  widget->setReadOnly(!editable);
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
  setEditable(true, this->txtName);
  reloadList(service.serviceGetAll().getOffers());
}
void guiMain::generate()
{
  service.serviceGenerate(txtGen->text().toStdString());
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
  ListLayout->addWidget(table);

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
  cart.addObserver(this);
  QObject::connect(btnAdd, &QPushButton::clicked, this,
                   &guiCart::addCart);

  QObject::connect(btnGenerate, &QPushButton::clicked, this,
                   &guiCart::generateCart);

  QObject::connect(btnClear, &QPushButton::clicked, this,
                   &guiCart::clearCart);

  QObject::connect(btnExport, &QPushButton::clicked, this,
                   &guiCart::exportCart);
  // QObject::connect(mainui.btnAddCart, &QPushButton::clicked, this, &guiCart::addCart);
  // QObject::connect(mainui.btnClearCart, &QPushButton::clicked, this, [&]()
  //                  { mainui.service.serviceCartClear(); });
  // QObject::connect(mainui.btnGenerateCart, &QPushButton::clicked, this, [&]()
  //                  { mainui.service.serviceCartGenerate(std::to_string(this->spinGenerate->value())); });
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
    cart.notify();
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
  cart.notify();
}

void guiCart::clearCart()
{

  mainui.service.serviceCartClear();
  reloadList(mainui.service.serviceCartGetAll().getOffers());
  cart.notify();
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
  tableModel->setOffers(list);
  // this->table->clear();
  // table->setRowCount((int)(list.size()));
  // table->setColumnCount(4);

  // QTableWidgetItem *nameCol = new QTableWidgetItem("Name");
  // QTableWidgetItem *destCol = new QTableWidgetItem("Destination");
  // QTableWidgetItem *typeCol = new QTableWidgetItem("Type");
  // QTableWidgetItem *priceCol = new QTableWidgetItem("Price");
  // table->setHorizontalHeaderItem(0, nameCol);
  // table->setHorizontalHeaderItem(1, destCol);
  // table->setHorizontalHeaderItem(2, typeCol);
  // table->setHorizontalHeaderItem(3, priceCol);
  // table->setSelectionMode(QAbstractItemView::SingleSelection);
  // table->setSelectionBehavior(QAbstractItemView::SelectRows);

  // int ind = 0;
  // for (const auto &x : list)
  // {
  //   std::string name = x.getName();
  //   std::string destination = x.getDestination();
  //   std::string type = x.getType();
  //   std::string price = x.getPrice();

  // table->setItem(ind, 0, new QTableWidgetItem(QString::fromStdString(name)));
  // table->setItem(ind, 1, new QTableWidgetItem(QString::fromStdString(destination)));
  // table->setItem(ind, 2, new QTableWidgetItem(QString::fromStdString(type)));
  // table->setItem(ind, 3, new QTableWidgetItem(QString::fromStdString(price)));

  // ind++;
  // }
}

void guiCart::closeEvent(QCloseEvent *event)
{
  mainui.btnOpenCart->show();
  event->accept();
}

void guiCartReadOnly::initGUI()
{

  setLayout(mainly);

  // // construiere fereastra
  // mainly->addWidget(widList);
  // mainly->addWidget(widBtn);
}

void guiCartReadOnly::connect_signals()
{
  cart.addObserver(this);
}

// void guiCartReadOnly::reloadList(const std::vector<Offer> &list)
// {
//   this->list->clear();
//   for (const auto &x : list)
//   {
//     QListWidgetItem *item = (new QListWidgetItem);

//     std::string txt =
//         x.getName() + " | " + x.getDestination() + " | " + x.getType() + " | " + x.getPrice();

//     item->setText(QString::fromStdString(txt));
//     item->setData(Qt::UserRole, QString::fromStdString(x.getName()));
//     this->list->addItem(item);
//   }
// }