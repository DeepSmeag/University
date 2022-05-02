#include "report.h"

reportType Report::getReport()
{
    return rep;
}
void Report::incrementCount(std::string key)
{
    rep.find(key)->second.incrementCount();
}
void Report::decrementCount(std::string key)
{
    rep.find(key)->second.incrementCount();
}
void Report::deletekey(std::string key)
{
    rep.erase(key);
}
void Report::assignKey(std::string key)
{
    if (keyExists(key))
    {
        incrementCount(key);
    }
    else
    {
        auto repItem = ReportItem(key, 1);
        rep.insert(make_pair(key, repItem));
    }
    rep.begin();
}
std::string Report::getFirst(ReportItem &item)
{
    return item.getFirst();
}
int Report::getSecond(ReportItem &item)
{
    return item.getSecond();
}
bool Report::keyExists(std::string key)
{
    return rep.find(key) != rep.end();
}
ReportItem::ReportItem(std::string type, int count) : type(type), count(count) {}
void ReportItem::incrementCount()
{
    count++;
}
void ReportItem::decrementCount()
{
    count--;
}
std::string ReportItem::getFirst()
{
    return type;
}
int ReportItem::getSecond()
{
    return count;
}