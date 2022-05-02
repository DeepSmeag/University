#include "repo.h"

Repository::Repository() : offerList() {}
Repository::~Repository() {}

void Repository::repoAdd(const std::string &name, const std::string &destination, const std::string &type, const std::string &price)
{
    Offer offer{name, destination, type, price};
    if (repoOfferExists(offer))
    {
        throw RepoException("Offer already exists!");
    }

    offerList.addOffer(offer);
}

void Repository::repoModify(const std::string &name, const std::string &newName, const std::string &newDestination, const std::string &newType, const std::string &newPrice)
{
    std::string emptyString = "";
    Offer offer{name, emptyString, emptyString, emptyString};
    Offer newOffer{newName, newDestination, newType, newPrice};
    // if (!repoOfferExists(offer))
    // {
    //     throw RepoException("Offer doesn't exist!");
    // }
    offerList.modifyOffer(offer, newOffer);
}

void Repository::repoRemove(const std::string &name)
{
    std::string emptyString = "";
    Offer offer(name, emptyString, emptyString, emptyString);
    // if (!repoOfferExists(offer))
    // {
    //     throw RepoException("Offer doesn't exist!");
    // }

    offerList.removeOffer(offer);
}

const Offer &Repository::repoSearchByName(const std::string &name)
{
    auto offers = offerList.getOffers();
    // std::string emptyString = "";
    // Offer offer{name, emptyString, emptyString, emptyString};
    // if (!repoOfferExists(offer))
    // {
    //     throw RepoException("Offer doesn't exist!");
    // }
    const auto offerIt = std::find_if(offers.begin(), offers.end(), [name](const Offer &off)
                                      { return off.getName().compare(name) == 0; });
    if (offerIt == offers.end())
    {
        throw RepoException("Offer doesn't exist!");
    }
    // return *offerIt;
    auto &it = offerList.searchOffer(name);
    // const Offer &it2(*offerIt);
    return it;
}

const bool Repository::repoOfferExists(const Offer &offer) const
{
    return offerList.checkOfferExists(offer);
}

listOffers &Repository::getOffersListObject()
{
    return offerList;
}
Cart &Repository::getCartOffersListObject()
{
    return cartList;
}
listOffers Repository::getOffersListObjectCopy()
{
    listOffers offers = offerList;
    // offerList.getOffers();
    // for (auto it = offerList.getOffers().begin(); it != offerList.getOffers().end(); it++)
    // {
    //     offers.getOffers().push_back((*it));
    // }
    return offers;
}

const int Repository::repoLen() const
{
    return this->offerList.dim();
}

const bool Repository::repoCartOfferExists(const Offer &offer) const
{
    return cartList.checkOfferExists(offer);
}

void Repository::repoCartAdd(const Offer &offer)
{
    if (repoCartOfferExists(offer))
    {
        throw RepoException("Offer already exists in cart!");
    }

    cartList.addOffer(offer);
}
void Repository::repoCartClear()
{
    cartList.cartClear();
}

void Repository::repoExportCart(std::string fileName)
{
    if (getCartOffersListObject().isEmpty())
    {
        throw RepoException("Cart is empty! Nothing to export!\n\n");
    }
    RepositoryFile repoFile(*this, fileName);
    repoFile.ExportCart();
}

void RepositoryFile::ExportCart()
{
    auto cartObject = repo.getCartOffersListObject();
    std::ofstream g(fName);
    for (auto &it : cartObject.getOffers())
    {
        g << it.getName() << "," << it.getDestination() << "," << it.getType() << "," << it.getPrice() << "\n";
    }
}