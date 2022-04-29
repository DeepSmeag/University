#include "listOffers.h"

listOffers::listOffers() {}
listOffers::~listOffers() {}

std::vector<Offer> &listOffers::getOffers()
{
    return this->offers;
}

void listOffers::addOffer(const Offer &offer)
{
    offers.push_back(offer);
}

void listOffers::modifyOffer(const Offer &oldOffer, const Offer &newOffer)
{
    for (auto it = offers.begin(); it != offers.end(); ++it)
    {
        if ((*it).getName().compare(oldOffer.getName()) == 0)
        {
            (*it).setName(newOffer.getName());
            (*it).setDestination(newOffer.getDestination());
            (*it).setType(newOffer.getType());
            (*it).setPrice(newOffer.getPrice());
            break;
        }
    }
    // for (auto &&it : offers)
    // {
    //     if (it.getName().compare(oldOffer.getName()) == 0)
    //     {
    //         it.setName(newOffer.getName());
    //         it.setDestination(newOffer.getDestination());
    //         it.setType(newOffer.getType());
    //         it.setPrice(newOffer.getPrice());
    //         break;
    //     }
    // }
}

void listOffers::removeOffer(const Offer &offer)
{
    // VectorDinamic<Offer> &offerList = offers;
    // auto it = offerList.begin();
    // while (it <= offerList.end())
    // {
    //     if ((it).getName().compare(offer.getName()) == 0)
    //     {
    //         offerList.erase(it);
    //     }
    //     it++;
    // }
    // for (auto it = offers.begin(); it != offers.end(); ++it)
    // {
    //     if ((*it).getName().compare(offer.getName()) == 0)
    //     {
    //         offers.erase(it);
    //         break;
    //     }
    // }
    // std::remove_if(offers.begin(), offers.end(), [offer](const Offer &other)
    //                { return offer.getName().compare(other.getName()) == 0; });
    offers.erase(std::remove(offers.begin(), offers.end(), offer), offers.end());
}

// const bool listOffers::compareOffers(const Offer &offer1, const Offer &offer2) const
// {
//     // const std::string &name1 = offer1.getName();
//     // const std::string &name2 = offer2.getName();
//     // if (name1.compare(name2) == 0)
//     //     return true;
//     // return false;
//     return offer1 == offer2;
// }

const Offer &listOffers::searchOffer(const std::string &name) const
{
    Offer off(name, "", "", "");
    // for (auto it = offers.begin(); it != offers.end(); ++it)
    // {
    //     if ((*it).getName().compare(name) == 0)
    //     {
    //         return *it;
    //         break;
    //     }
    // }
    // const auto it = offers.end();
    // return *it;
    auto it = std::find(offers.begin(), offers.end(), off);
    return *it;
}

bool listOffers::checkOfferExists(const Offer &offer) const
{
    auto it = std::find(offers.begin(), offers.end(), offer);
    // for (auto it = offers.begin(); it != offers.end(); ++it)
    // {
    //     if (compareOffers(offer, *it))
    //     {
    //         return true;
    //     }
    // }
    if (it != offers.end())
        return true;

    return false;
}

bool listOffers::isEmpty() const
{
    return offers.empty();
}

const int listOffers::dim() const
{
    return (int)offers.size();
}