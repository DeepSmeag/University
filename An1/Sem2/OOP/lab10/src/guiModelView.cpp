#include "guiModelView.h"

using namespace std;
CustomTableModel::CustomTableModel(const std::vector<Offer> &offers) : offers{offers} {}
QVariant CustomTableModel::data(const QModelIndex &index, int role) const
{
    int row = index.row();
    int col = index.column();

    if (role == Qt::DisplayRole)
    {
        if (col == 0)
        {
            return QString::fromStdString(offers[row].getName());
        }
        else if (col == 1)
        {
            return QString::fromStdString(offers[row].getDestination());
        }
        else if (col == 2)
        {
            return QString::fromStdString(offers[row].getType());
        }
        else if (col == 3)
        {
            return QString::fromStdString(offers[row].getPrice());
        }
    }
    if (role == Qt::FontRole)
    {
        if (row == 0 && col == 0)
        { // change font only for cell(0,0)
            QFont boldFont;
            boldFont.setBold(true);
            return boldFont;
        }
    }
    if (role == Qt::BackgroundRole)
    {
        if (row == 1 && col == 2) // change background only for cell(1,2)
            return QBrush(Qt::red);
    }
    if (role == Qt::TextAlignmentRole)
    {
        if (row == 1 && col == 1) // change text alignment only for cell(1,1)
            return int(Qt::AlignRight | Qt::AlignVCenter);
    }
    if (role == Qt::CheckStateRole)
    {
        if (row == 1 && col == 0) // add a checkbox to cell(1,0)
            return Qt::Checked;
    }
    return QVariant();
}

QVariant CustomTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    if (role == Qt::DisplayRole && orientation == Qt::Horizontal)
    {
        switch (section)
        {
        case 0:
            return QString("Name");
        case 1:
            return QString("Destination");
        case 2:
            return QString("Type");
        case 3:
            return QString("Price");
        default:
            return QString("EXTRA");
        }
    }
    return QVariant();
}
bool CustomTableModel::insertRows(int row, int count, const QModelIndex &parent)
{
    beginInsertRows(parent, row, row + count - 1);
    endInsertRows();
    return true;
}
bool CustomTableModel::removeRows(int row, int count, const QModelIndex &parent)
{
    beginRemoveRows(parent, row, row + count - 1);
    endRemoveRows();
    return true;
}