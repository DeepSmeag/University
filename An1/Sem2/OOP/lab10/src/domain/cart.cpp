#include "cart.h"

Cart::Cart() {}
Cart::~Cart() {}

Cart::Cart(const Cart &other) : offers(other.offers) {}

void Cart::cartClear()
{
    offers.erase(offers.begin(), offers.end());
}
void Cart::addOffer(const Offer &offer)
{
    offers.push_back(offer);
}

std::vector<Offer> &Cart::getOffers()
{
    return offers;
}
bool Cart::isEmpty() const
{
    return offers.empty();
}
const int Cart::dim() const
{
    return (int)offers.size();
}

bool Cart::checkOfferExists(const Offer &offer) const
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