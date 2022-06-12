#include <QAbstractTableModel>
#include <QDebug>
#include <QFont>
#include <QBrush>
#include <vector>
#include "domain/listOffers.h"
#include <QMessageBox>
class CustomTableModel : public QAbstractTableModel
{
private:
    std::vector<Offer> offers;

public:
    CustomTableModel(const std::vector<Offer> &offers);
    int rowCount(const QModelIndex &parent = QModelIndex()) const override
    {
        return (int)(offers.size());
    }
    int columnCount(const QModelIndex &parent = QModelIndex()) const override
    {
        return 4;
    }
    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const override;
    QVariant headerData(int section, Qt::Orientation orientation, int role) const override;
    // bool setData(const QModelIndex &index, const QVariant &value, int role) override;
    // Qt::ItemFlags flags(const QModelIndex & /*index*/) const override;
    bool insertRows(int row, int count, const QModelIndex &parent = QModelIndex()) override;
    bool removeRows(int row, int count, const QModelIndex &parent = QModelIndex()) override;
    void setOffers(const std::vector<Offer> &offers)
    {
        if (rowCount() > offers.size())
        {
            this->removeRows(offers.size(), rowCount() - offers.size());
        }
        else if (rowCount() < offers.size())
        {
            this->insertRows(rowCount(), offers.size() - rowCount());
        }
        this->offers = offers;
        auto topLeft = createIndex(0, 0);
        auto bottomR = createIndex(rowCount(), columnCount());
        emit dataChanged(topLeft, bottomR);
    }
};