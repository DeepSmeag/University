#include <unordered_map>

class ReportItem
{
private:
    std::string type;
    int count;

public:
    ReportItem() = default;
    ReportItem(std::string type, int count);
    void incrementCount();
    void decrementCount();
    std::string getFirst();
    int getSecond();
};

typedef std::unordered_map<std::string, ReportItem> reportType;

class Report
{
private:
    reportType rep;

public:
    Report() = default;
    ~Report() = default;
    Report(Report &rep) = default;
    Report &operator=(const Report &other) = default;

    Report(Report &&other) = default;

    Report &operator=(Report &&other) = default;
    reportType getReport();
    void incrementCount(std::string key);
    void decrementCount(std::string key);
    void deletekey(std::string key);
    void assignKey(std::string key);
    bool keyExists(std::string key);
    std::string getFirst(ReportItem &item);
    int getSecond(ReportItem &item);
};
