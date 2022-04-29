#include "service.h"

Service::Service() : repo(), undoActions() {}

Service::~Service() {}

void Service::serviceAdd(const std::string &name, const std::string &destination, const std::string &type, const std::string &price)
{
    repo.repoAdd(name, destination, type, price);
    undoActions.push_back(std::make_unique<UndoAdd>(repo, Offer(name, destination, type, price)));
}

void Service::serviceModify(const std::string &name, const std::string &newName, const std::string &newDestination, const std::string &newType, const std::string &newPrice)
{
    Offer oldOffer = serviceSearch(name);
    repo.repoModify(name, newName, newDestination, newType, newPrice);

    undoActions.push_back(std::make_unique<UndoModify>(repo, oldOffer, Offer(newName, newDestination, newType, newPrice)));
}

void Service::serviceRemove(const std::string &name)
{
    Offer offer = serviceSearch(name);
    repo.repoRemove(name);
    undoActions.push_back(std::make_unique<UndoRemove>(repo, offer));
}

const Offer &Service::serviceSearch(const std::string &name)
{
    // listOffers offersObject = repo.getOffersListObjectCopy();
    // std::vector<Offer> &offers = offersObject.getOffers();
    // const auto offerIt = std::find_if(offers.begin(), offers.end(), [name](const Offer &off)
    //                                   { return off.getName().compare(name) == 0; });
    return repo.repoSearchByName(name);
    // return *offerIt;
}

// bool serviceFilterDestination(Offer &offer, const std::string &field)
// {
//     return offer.getDestination().compare(field) == 0;
// }

int convertStrToInt(std::string s)
{
    int n = 0;
    const int base = 10;
    for (auto &it : s)
    {
        n += (int)(it - '0');
        n *= base;
    }
    return n / base;
}

// bool serviceFilterPrice(Offer &offer, const std::string &field)
// {
//     int n1 = convertStrToInt(offer.getPrice());
//     int n2 = convertStrToInt(field);
//     return n1 < n2;
// }

listOffers Service::serviceFilter(const std::string &criterion, const std::string &field)
{
    // auto passesFilter = (criterion.compare("destination") == 0) ? &serviceFilterDestination : &serviceFilterPrice;
    // auto passesFilter = (criterion.compare("destination") == 0) ? ([field](const Offer &offer)
    //                                                                { return offer.getDestination().compare(field) == 0; })
    //                                                             : ([field](const Offer &offer)
    //                                                                { return convertStrToInt(offer.getPrice()) < convertStrToInt(field); });
    auto passesFilterDestination = [field](const Offer offer)
    { return offer.getDestination().compare(field) == 0; };
    auto passesFilterPrice = [field](const Offer offer)
    { return convertStrToInt(offer.getPrice()) < convertStrToInt(field); };

    listOffers offersObject = repo.getOffersListObjectCopy();
    std::vector<Offer> &offers = offersObject.getOffers();
    offers.clear();
    std::vector<Offer> offerList = repo.getOffersListObject().getOffers();
    // for (auto it = offers.begin(); it != offers.end(); ++it)
    // {
    //     if (!(this->*passesFilter)(*it, field))
    //     {
    //         offers.erase(it);
    //         break;
    //         // int tmp = it.getCurent();
    //         // it.setCurent(tmp - 1);
    //     }
    // }
    if (criterion.compare("destination") == 0)
    {
        std::copy_if(offerList.begin(), offerList.end(), std::back_inserter(offers), [field](const Offer &offer)
                     { return offer.getDestination().compare(field) == 0; });
    }
    else
    {
        int priceInt = convertStrToInt(field);
        std::copy_if(offerList.begin(), offerList.end(), std::back_inserter(offers), [priceInt](const Offer &offer)
                     { return convertStrToInt(offer.getPrice()) < priceInt; });
    }
    return offersObject;
}

bool serviceSortName(Offer &offer1, Offer &offer2)
{
    return offer1.getName().compare(offer2.getName()) < 0;
}
bool serviceSortDestination(Offer &offer1, Offer &offer2)
{
    return offer1.getDestination().compare(offer2.getDestination()) < 0;
}

bool serviceSortType(Offer &offer1, Offer &offer2)
{
    return offer1.getType().compare(offer2.getType()) < 0;
}
bool serviceSortPrice(Offer &offer1, Offer &offer2)
{
    int n1 = convertStrToInt(offer1.getPrice());
    int n2 = convertStrToInt(offer2.getPrice());
    return n1 < n2;
}
bool serviceSortNull(Offer &offer1, Offer &offer2)
{
    return 0;
}

listOffers Service::serviceSort(const std::string &criterion, const std::string &order)
{
    auto sortFunc = (criterion.compare("name") == 0) ? &serviceSortName : (criterion.compare("destination") == 0) ? &serviceSortDestination
                                                                      : (criterion.compare("type") == 0)          ? &serviceSortType
                                                                      : (criterion.compare("price") == 0)         ? &serviceSortPrice
                                                                                                                  : &serviceSortNull;
    int orderint = order.compare("asc") == 0 ? 1 : -1;
    listOffers offersObject = repo.getOffersListObjectCopy();
    std::vector<Offer> &offers = offersObject.getOffers();

    std::sort(offers.begin(), offers.end(), sortFunc);
    if (orderint == -1)
    {
        std::reverse(offers.begin(), offers.end());
    }
    return offersObject;
}

listOffers &Service::serviceGetAll()
{
    return repo.getOffersListObject();
}
Cart &Service::serviceCartGetAll()
{
    return repo.getCartOffersListObject();
}
const int Service::serviceLen() const
{
    return repo.repoLen();
}

std::string generateRandomString(int length)
{
    // const std::array<char, 53> table = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    const std::array<char, 4> table = {'a', 'b', 'c'};
    std::string str("");
    int size = sizeof(table) - 1;
    for (int i = 0; i < length; ++i)
    {
        str += table.at(rand() % size);
    }

    return str;
}
std::string generateRandomNumberString(int length)
{
    const std::array<char, 10> table = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    std::string str("");
    int size = sizeof(table) - 1;
    str += table.at(rand() % size + 1);
    for (int i = 1; i < length; i++)
    {
        str += table.at(rand() % size);
    }

    return str;
}
int Service::serviceGenerate(std::string number)
{
    int successes = 0;
    int numberInt = convertStrToInt(number);
    while (numberInt > 0)
    {
        // generate offer
        const int MAXLEN = 3;
        const int length = rand() % MAXLEN + 1;
        std::string name = generateRandomString(length);
        std::string destination = generateRandomString(length);
        std::string type = generateRandomString(length);
        std::string price = generateRandomNumberString(length);
        // attempt to add it
        try
        {
            repo.repoAdd(name, destination, type, price);
            successes++;
        }
        catch (RepoException &exception)
        {
            // catch exception if already exists
            // this means we've generated something that already exists
            // we don't really want to do anything here it's just to catch an error
            exception.what();
        }

        // go to next offer and decrement numberInt
        numberInt--;
    }
    return successes;
}
Offer Service::serviceRandomOffer()
{
    auto offers = serviceGetAll().getOffers();
    return offers[rand() % offers.size()];
}
int Service::serviceCartGenerate(std::string number)
{
    if (serviceGetAll().dim() == 0)
    {
        return 0;
    }
    int successes = 0;
    int numberInt = convertStrToInt(number);
    while (numberInt > 0)
    {
        // generate offer
        auto offer = serviceRandomOffer();
        // attempt to add it
        try
        {
            repo.repoCartAdd(offer);
            successes++;
        }
        catch (RepoException &exception)
        {
            // catch exception if already exists
            // this means we've generated something that already exists
            // we don't really want to do anything here it's just to catch an error
            exception.what();
        }

        // go to next offer and decrement numberInt
        numberInt--;
    }
    return successes;
}
void Service::serviceCartAdd(std::string name)
{
    auto offer = repo.repoSearchByName(name);
    repo.repoCartAdd(offer);
}
void Service::serviceCartClear()
{
    repo.repoCartClear();
}

Report Service::createReport()
{
    Report report;
    auto offers = serviceGetAll().getOffers();
    for (auto &it : offers)
    {
        report.assignKey(it.getType());
    }
    return report;
}
void Service::serviceUndo()
{
    if (undoActions.empty())
    {
        throw RepoException("There are no more undos to be done!\n\n");
    }
    undoActions.back()->doUndo();
    undoActions.pop_back();
}
void Service::serviceExportCart(std::string fileName)
{
    repo.repoExportCart(fileName);
}